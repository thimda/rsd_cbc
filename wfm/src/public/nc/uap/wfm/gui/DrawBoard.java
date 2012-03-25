package nc.uap.wfm.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.model.Activity;
import nc.uap.wfm.model.Event;
import nc.uap.wfm.model.EventShape;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProcessDiagram;
import nc.uap.wfm.model.SequenceFlowConnector;
import nc.uap.wfm.model.TaskShape;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 2011-5-25 下午02:37:58 limingf
 */
public class DrawBoard extends Node {
	private Map<String, Node> taskMap = new HashMap<String, Node>();
	private Map<String, Node> eventMap = new HashMap<String, Node>();
	private Map<String, Route> routeMap = new HashMap<String, Route>();
	private Map<String, GateWay> gatewayMap = new HashMap<String, GateWay>();
	private List<Node> taskList = new ArrayList<Node>();
	private List<Node> eventList = new ArrayList<Node>();
	private List<Route> routeList = new ArrayList<Route>();
	private List<GateWay> gatewayList = new ArrayList<GateWay>();
	private Map<String, Element> eleMap = new HashMap<String, Element>();
	private ProDef proDef = null;
	private final int ARCWIDTH = 5;
	private final int ARCHEIGHT = 5;
	private void parseProDef(String wfpk) {
		if (wfpk == null || "".equals(wfpk))
			return;
		ProDef proDef = ProDefsContainer.getByProDefPkAndId(wfpk, null);
		if (proDef == null)
			throw new LfwRuntimeException("获取流程定义失败！");
		this.proDef = proDef;
		ProcessDiagram di = proDef.getProcessDiagram();
		this.setWidth(Integer.parseInt(di.getWidth()));
		this.setHeight(Integer.parseInt(di.getHeight()));
	}
	public BufferedImage getWorkflowImage(String wfpk, String proInsPk) {
		parseProDef(wfpk);
		ProcessDiagram di = proDef.getProcessDiagram();
		// 对象转换
		convertObject(di);
		// 设置状态(避免类冲突，单独写一个类)
		if (proInsPk != null && !"".equals(proInsPk))
			ProInsParse.parseState(this, proInsPk);
		return buildWorkflowImage(proDef);
	}
	public BufferedImage buildWorkflowImage(ProDef proDef) {
		// 在内存中创建图象
		// 根据节点实际位置多少设置界面宽高
		validateSize();
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics2D g = (Graphics2D) image.getGraphics();
		//		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// 设置背景
		setBackgroud(image, g);
		// 进行缩放(如果界面放不下，就把图标缩小)
		// setScale(width,height,g);
		// 重构
		refactor(this);
		drawRoutes(routeList, g);
		drawNode(taskList, eventList, g);
		// 图象生效
		g.dispose();
		return image;
	}
	/**
	 * 按边界大小比例进行缩放
	 * 
	 * @param boundWidth
	 * @param boundHeight
	 * @param g
	 */
	protected void setScale(int boundWidth, int boundHeight, Graphics2D g) {
		// 按宽高比例小的缩放
		Map<String, Element> eleMap = this.getEleMap();
		Set<String> ids = eleMap.keySet();
		int maxWidth = 0;
		int maxHeight = 0;
		for (String id : ids) {
			Node node = (Node) eleMap.get(id);
			if (maxWidth < (node.getX() + node.getWidth()))
				maxWidth = node.getX() + node.getWidth();
			if (maxHeight < (node.getY() + node.getHeight()))
				maxHeight = node.getY() + node.getHeight();
		}
		double wRadio = 1;
		double hRadio = 1;
		if (maxWidth > boundWidth)
			wRadio = boundWidth * 1.0 / maxWidth;
		if (maxHeight > boundHeight)
			hRadio = boundHeight * 1.0 / maxHeight;
		double ratio = Math.min(wRadio, hRadio);
		g.scale(ratio, ratio);
	}
	/**
	 * 验证宽高
	 */
	private void validateSize() {
		Map<String, Element> eleMap = this.getEleMap();
		Set<String> ids = eleMap.keySet();
		int maxWidth = 0;
		int maxHeight = 0;
		for (String id : ids) {
			Node node = (Node) eleMap.get(id);
			if (maxWidth < (node.getX() + node.getWidth()))
				maxWidth = node.getX() + node.getWidth();
			if (maxHeight < (node.getY() + node.getHeight()))
				maxHeight = node.getY() + node.getHeight();
		}
		if (maxWidth > this.getWidth())
			this.setWidth(maxWidth + 5);
		if (maxHeight > this.getHeight())
			this.setHeight(maxHeight + 5);
	}
	/**
	 * 设置背景
	 * 
	 * @param image
	 * @param g
	 */
	private void setBackgroud(BufferedImage image, Graphics2D g) {
		int width = image.getWidth();
		int height = image.getHeight();
		int x0 = 0;
		int y0 = 0;
		int spa = 25;
		int hnum = width / spa;
		int vnum = height / spa;
		float[] lineW = new float[] { 5, 2 };
		// float[] lineH = new float[]{5,2};
		BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, lineW, 0.0f);
		g.setStroke(stroke);
		g.setColor(new Color(195, 229, 215, 200));// 0xC3E5D7
		for (int i = 0; i <= vnum; i++) {
			g.drawLine(x0, i * spa, x0 + width, i * spa);
		}
		for (int j = 0; j <= hnum; j++) {
			g.drawLine(j * spa, y0, j * spa, y0 + height);
		}
		// 设置背景图片
		// this.setStyle("backgroundImage","images/pic/lv.gif");
	}
	// 重构,1,去除网关节点
	public void refactor(DrawBoard drawBoard) {
		List<Route> routes = new ArrayList<Route>();
		for (int i = 0; i < gatewayList.size(); i++) {
			GateWay gateway = gatewayList.get(i);
			Route[] fromroutes = this.getFromRoutesByNode(gateway);
			List<String> toElements = new ArrayList<String>();
			toElements = getToElement(gateway, toElements);
			for (int j = 0; j < fromroutes.length; j++) {
				Route tmproute = fromroutes[j];
				Element source = eleMap.get(tmproute.getSource());
				if (!(source instanceof GateWay)) {
					for (int k = 0; k < toElements.size(); k++) {
						Route route = new Route();
						route.setSource(tmproute.getSource());
						route.setTarget((eleMap.get(toElements.get(k)).getId()));
						route.setId(UUID.randomUUID().toString());
						routeList.remove(tmproute);
						routeList.add(route);
						routes.add(route);
					}
				}
			}
			// 递归
			// if(childs[i] is DrawBoard){
			// refactor(childs[i] as DrawBoard);
			// }
		}
		// 删除网关和网关上的线
		List<Route> delRoutes = new ArrayList<Route>();
		for (int m = 0; m < gatewayList.size(); m++) {
			List<Route> gateroutes = getRoutesByNode(gatewayList.get(m));
			delRoutes.addAll(gateroutes);
		}
		routeList.removeAll(delRoutes);
		gatewayList.clear();
	}
	// 得到某个节点上的所有关联线(包括进，出)
	public List<Route> getRoutesByNode(Node node) {
		String id = node.getId();
		List<Route> list = new ArrayList<Route>();
		for (int i = 0; i < routeList.size(); i++) {
			Route tmp = routeList.get(i);
			if (id.equals(tmp.getTarget()) || id.equals(tmp.getSource()))
				list.add(tmp);
		}
		return list;
	}
	// 得到某个节点上的所有进入关联线
	public Route[] getFromRoutesByNode(Node node) {
		String id = node.getId();
		List<Route> list = new ArrayList<Route>();
		for (int i = 0; i < routeList.size(); i++) {
			Route tmp = routeList.get(i);
			if (id.equals(tmp.getTarget()))
				list.add(tmp);
		}
		return list.toArray(new Route[list.size()]);
	}
	// 得到某个节点上的所有出去关联线
	public Route[] getOutRoutesByNode(Node node) {
		String id = node.getId();
		List<Route> list = new ArrayList<Route>();
		for (int i = 0; i < routeList.size(); i++) {
			Route tmp = routeList.get(i);
			if (id.equals(tmp.getSource()))
				list.add(tmp);
		}
		return list.toArray(new Route[list.size()]);
	}
	// 得到drawBoard中由gateway出发的所有非GateWayNode节点。
	private List<String> getToElement(GateWay gateway, List<String> toElements) {
		Route[] outRoutes = this.getOutRoutesByNode(gateway);
		for (int i = 0; i < outRoutes.length; i++) {
			Route route = outRoutes[i];
			Element target = eleMap.get(route.getTarget());
			if (!(target instanceof GateWay) && toElements.indexOf(target.getId()) == -1)
				toElements.add(target.getId());
			else if (target instanceof GateWay) {
				getToElement((GateWay) target, toElements);
			}
		}
		return toElements;
	}
	private void convertObject(ProcessDiagram di) {
		List<TaskShape> tsList = di.getTaskShapeList();
		List<EventShape> esList = di.getEventShapeList();
		List<SequenceFlowConnector> sfList = di.getSequenceFlowList();
		for (int i = 0; i < tsList.size(); i++) {
			TaskShape task = tsList.get(i);
			IPort port = proDef.getPorts().get(task.getActivityRef());
			String name = "";
			if (port instanceof Activity) {
				Activity humAct = (Activity) port;
				name = humAct.getName();
			}
			if (port instanceof Event) {
				Event event1 = (Event) port;
				name = event1.getName();
			}
			Node node = new Node();
			node.setId(task.getActivityRef());
			node.setName(name);
			node.setX(Integer.parseInt(task.getRx()));
			node.setY(Integer.parseInt(task.getRy()));
			node.setWidth(Integer.parseInt(task.getWidth()));
			node.setHeight(Integer.parseInt(task.getHeight()));
			this.addTask(node);
		}
		for (int i = 0; i < esList.size(); i++) {
			EventShape task = esList.get(i);
			IPort port = proDef.getPorts().get(task.getEventRef());
			String name = "";
			if (port instanceof Activity) {
				Activity humAct = (Activity) port;
				name = humAct.getName();
			}
			if (port instanceof Event) {
				Event event1 = (Event) port;
				name = event1.getName();
			}
			if (port instanceof GateWay) {
				GateWay node = new GateWay();
				node.setId(task.getEventRef());
				node.setName(name);
				node.setX(Integer.parseInt(task.getRx()));
				node.setY(Integer.parseInt(task.getRy()));
				node.setWidth(Integer.parseInt(task.getWidth()));
				node.setHeight(Integer.parseInt(task.getHeight()));
				this.addGateway(node);
			} else {
				Node node = new Node();
				node.setId(task.getEventRef());
				node.setName(name);
				node.setX(Integer.parseInt(task.getRx()));
				node.setY(Integer.parseInt(task.getRy()));
				// node.setWidth(Integer.parseInt(task.getWidth()==null?"100":task.getWidth()));
				// node.setHeight(Integer.parseInt(task.getHeight()==null?"50":task.getWidth()));
				node.setWidth(40);
				node.setHeight(40);
				this.addEvent(node);
			}
		}
		for (int i = 0; i < sfList.size(); i++) {
			SequenceFlowConnector task = sfList.get(i);
			Route route = new Route();
			route.setId(task.getSequenceFlowRef());
			route.setSource(task.getSourceRef());
			route.setTarget(task.getTargetRef());
			this.addRoute(route);
		}
		// 把节点放入全局
		setEleMap();
	}
	private void drawNode(List<Node> tsList, List<Node> esList, Graphics2D g) {
		g.setColor(new Color(255, 255, 255));
		for (int i = 0; i < tsList.size(); i++) {
			Node node = tsList.get(i);
			setLineColor(node, g);
			g.drawRoundRect(node.getX(), node.getY(), node.getWidth(), node.getHeight(), ARCWIDTH, ARCHEIGHT);
			setFillColor(node, g);
			g.fillRoundRect(node.getX(), node.getY(), node.getWidth(), node.getHeight(), ARCWIDTH, ARCHEIGHT);
			g.setColor(new Color(0x000000));
			// g.drawString(node.getName(), node.getX(),
			// node.getY()+node.getHeight()/2+5);
			drawString(node, g);
		}
		for (int i = 0; i < esList.size(); i++) {
			Node node = esList.get(i);
			setLineColor(node, g);
			g.drawOval(node.getX(), node.getY(), node.getHeight(), node.getHeight());
			setFillColor(node, g);
			g.fillOval(node.getX(), node.getY(), node.getHeight(), node.getHeight());
			g.setColor(new Color(0x000000));
			// g.drawString(node.getName(), node.getX(),
			// node.getY()+node.getHeight()/2+5);
			drawString(node, g);
		}
		g.setStroke(new BasicStroke(1));
	}
	private void drawString(Node node, Graphics2D g) {
		FontMetrics fontMetirc = g.getFontMetrics();
		// 一个汉字的像素宽度(当前字体)
		int wordW = fontMetirc.stringWidth("中");// 12;
		// 一个汉字的像素高度(当前字体)
		int wordH = fontMetirc.getHeight() - 3;// 15;（-3是为了显示的紧凑一点）
		String name = node.getName();
		// 一行能显示多个字
		int lineNum = node.getWidth() / wordW;
		int line = name.length() / lineNum + 1;
		String[] words = new String[line];
		for (int i = 0; i < words.length; i++) {
			int end = (i + 1) * lineNum < name.length() ? (i + 1) * lineNum : name.length();
			words[i] = name.substring(i * lineNum, end);
		}
		for (int i = 0; i < words.length; i++) {
			g.drawString(words[i], node.getX() + node.getWidth() / 2 - (int) (wordW * (words[i].length()) / 2), node.getY() + node.getHeight() / 2
					- (int) (wordH * ((words.length + 1) / 2.0 - (i + 1))) + 8);
		}
	}
	private void drawRoutes(List<Route> rList, Graphics2D g) {
		for (int i = 0; i < rList.size(); i++) {
			Route route = rList.get(i);
			try {
				drawRoute(route, g);
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e);
			}
		}
	}
	private void setLineColor(Node node, Graphics2D g) {
		int runState = node.getState() % 8;
		int elseState = node.getState() / 8;
		if (runState == 0) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		} else if (runState == 1) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		} else if (runState == 2) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		} else if (runState == 3) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		} else if (runState == 4) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		} else if (runState == 5) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0x036999));
		}
		// 当前活动
		if (elseState == 1) {
			g.setStroke(new BasicStroke(4));
			g.setColor(new Color(0x0000ff));
		}
	}
	private void setFillColor(Node node, Graphics2D g) {
		int runState = node.getState() % 8;
		if (runState == 0) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0x8bdef9));
			g.setPaint(gradient);
			// g.rotate(Math.PI/4.0);
		} else if (runState == 1) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0xdfff07));
			g.setPaint(gradient);
		} else if (runState == 2) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0x00ff00));
			g.setPaint(gradient);
		} else if (runState == 3) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0x007eff));
			g.setPaint(gradient);
		} else if (runState == 4) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0xff00ae));
			g.setPaint(gradient);
		} else if (runState == 5) {
			GradientPaint gradient = new GradientPaint(node.getX(), 0, new Color(0xffffff), node.getX() + node.getWidth(), 0, new Color(0xff0000));
			g.setPaint(gradient);
		}
		// 渐变色旋转
		// AffineTransform affineTransform=new AffineTransform();
		// affineTransform.setToTranslation(0, 0);
		// affineTransform.setToRotation(Math.PI/4);//旋转
		// g.transform(affineTransform);
		// 当前活动
		// if(elseState==1){
		// var gradientGlow:GradientGlowFilter = new GradientGlowFilter();
		// gradientGlow.distance = 0;
		// gradientGlow.angle = 0;
		// gradientGlow.colors = [0x007eff, 0x007eff];
		// gradientGlow.alphas = [0, 1];
		// gradientGlow.ratios = [0, 255];
		// gradientGlow.blurX = 10;
		// gradientGlow.blurY = 10;
		// gradientGlow.strength = 2;
		// gradientGlow.quality = BitmapFilterQuality.HIGH;
		// gradientGlow.type = BitmapFilterType.OUTER;
		//			
		// this.filters = new Array(gradientGlow);
		// }
	}
	/**
	 * 画线算法
	 * 
	 * @param source
	 * @param target
	 * @param routeStyle
	 * @param g
	 * @throws Exception
	 */
	private void drawRoute(Route route, Graphics2D g) throws Exception {
		Node source = (Node) eleMap.get(route.getSource());
		Node target = (Node) eleMap.get(route.getTarget());
		g.setColor(new Color(0x000000));
		g.setStroke(new BasicStroke(1));
		if (route.getState() == 3) {
			g.setColor(new Color(0x0000ff));
		}
		if (route.getState() == 5) {
			g.setColor(new Color(0xff0000));
		}
		if (source == null) {
			throw new Exception("Route中非法的source");
		}
		if (target == null) {
			throw new Exception("Route中非法的target");
		}
		float fromMidX = source.getX() + source.getWidth() / 2;
		float fromMidY = source.getY() + source.getHeight() / 2;
		float fromX = source.getX() + source.getWidth() / 2;
		float fromY = source.getY() + source.getHeight() / 2;
		float toX = -1;
		float toY = -1;
		if (target != null) {
			float toMidX = target.getX() + target.getWidth() / 2;
			float toMidY = target.getY() + target.getHeight() / 2;
			double rDistance = Math.sqrt(Math.pow((toMidX - fromMidX), 2) + Math.pow((toMidY - fromMidY), 2));
			float vDistance = toMidY - fromMidY;
			float hDistance = toMidX - fromMidX;
			double halfFromCrossDistance = Math.sqrt(Math.pow(source.getWidth(), 2) + Math.pow(source.getHeight(), 2)) / 2;
			double f_tv_crossDistance = halfFromCrossDistance * Math.abs(hDistance) / (source.getWidth() / 2.0);
			int location = 0;
			if (f_tv_crossDistance <= rDistance)
				location = 1;
			else
				location = 2;
			{
				if (location == 1) {
					float y1 = source.getHeight() / 2;
					if (target.getY() < fromMidY)
						y1 = -y1;
					fromX = hDistance * y1 / vDistance + fromMidX;
					fromY = fromMidY + y1;
				} else if (location == 2) {
					float x1 = source.getWidth() / 2;
					if (target.getX() < fromMidX)
						x1 = -x1;
					fromX = fromMidX + x1;
					fromY = vDistance * x1 / hDistance + fromMidY;
				}
			}
		}
		// toElement的连接点，根据不同的位置画不同的折线
		String toLocation = "left";
		// 如果终点是非结束节点
		if (target != null) {
			// 计算终点
			double minDistance;
			double distance1 = Math.sqrt(Math.pow(fromMidX - target.getX(), 2) + Math.pow(fromMidY - (target.getY() + target.getHeight() / 2), 2)); // 左
			double distance2 = Math.sqrt(Math.pow(fromMidX - (target.getX() + target.getWidth()), 2) + Math.pow(fromMidY - (target.getY() + target.getHeight() / 2), 2)); // 右
			double distance3 = Math.sqrt(Math.pow(fromMidX - (target.getX() + target.getWidth() / 2), 2) + Math.pow(fromMidY - target.getY(), 2));// 上
			double distance4 = Math.sqrt(Math.pow(fromMidX - (target.getX() + target.getWidth() / 2), 2) + Math.pow(fromMidY - (target.getY() + target.getHeight()), 2));// 下
			// 取最小距离
			minDistance = Math.min(Math.min(distance1, distance2), Math.min(distance3, distance4));
			if (minDistance == distance1) {
				toX = target.getX();
				toY = target.getY() + target.getHeight() / 2;
				toLocation = "left";
			}
			if (minDistance == distance2) {
				toX = (target.getX() + target.getWidth());
				toY = (target.getY() + target.getHeight() / 2);
				toLocation = "right";
			}
			if (minDistance == distance3) {
				toX = (target.getX() + target.getWidth() / 2);
				toY = target.getY();
				toLocation = "top";
			}
			if (minDistance == distance4) {
				toX = (target.getX() + target.getWidth() / 2);
				toY = (target.getY() + target.getHeight());
				toLocation = "bottom";
			}
		}
		// 设置线上的label
		// setLabel(fromX,fromY,toX,toY);
		// 画折线
		if ("0".equals(route.getRouteStyle())) {
			Point point = drawCurveLine((int) fromX, (int) fromY, (int) toX, (int) toY, toLocation, g);
			// 箭头
			drawArrowLine((int) point.x, (int) point.y, (int) toX, (int) toY, g);
		}
		// 直线
		else if ("1".equals(route.getRouteStyle())) {
			g.drawLine((int) fromX, (int) fromY, (int) toX, (int) toY);
			drawArrowLine((int) fromX, (int) fromY, (int) toX, (int) toY, g);
		}
	}
	private Point drawCurveLine(int fromX, int fromY, int toX, int toY, String toLocation, Graphics2D g) {
		int midX = fromX + (toX - fromX) / 2;
		int midY = fromY + (toY - fromY) / 2;
		if (toLocation == "left" || toLocation == "right") {
			g.drawLine(fromX, fromY, midX, fromY);
			g.drawLine(midX, fromY, midX, toY);
			g.drawLine(midX, toY, toX, toY);
			return new Point(midX, toY);
		} else {
			g.drawLine(fromX, fromY, fromX, midY);
			g.drawLine(fromX, midY, toX, midY);
			g.drawLine(toX, midY, toX, toY);
			return new Point(toX, midY);
		}
	}
	private void drawArrowLine(int fromX, int fromY, int toX, int toY, Graphics2D g) {
		double slopy;
		double cosy;
		double siny;
		double Par = 15;
		slopy = Math.atan2((fromY - toY), (fromX - toX));
		cosy = Math.cos(slopy);
		siny = Math.sin(slopy);
		// g.drawLine(toX,toY,toX + (int)( Par * cosy - ( Par / 3.0 * siny ) ),
		// toY + (int)( Par * siny + ( Par / 3.0 * cosy ) ));
		// g.drawLine(toX + (int)( Par * cosy - ( Par / 3.0 * siny ) ), toY +
		// (int)( Par * siny + ( Par / 3.0 * cosy ) ) ,
		// toX + (int)( Par * cosy + Par / 3.0 * siny ),toY - (int)( Par / 3.0 *
		// cosy - Par * siny ));
		// g.drawLine(toX + (int)( Par * cosy + Par / 3.0 * siny ),toY - (int)(
		// Par / 3.0 * cosy - Par * siny ),toX,toY);
		GeneralPath path = new GeneralPath();
		path.moveTo(toX, toY);
		path.lineTo(toX + (int) (Par * cosy - (Par / 3.0 * siny)), toY + (int) (Par * siny + (Par / 3.0 * cosy)));
		path.lineTo(toX + (int) (Par * cosy + Par / 3.0 * siny), toY - (int) (Par / 3.0 * cosy - Par * siny));
		path.closePath();
		g.draw(path);
		g.fill(path);
	}
	protected WfmProdefVO getWfXmlByPk(String pk) {
		IWfmProDefQry qry = NCLocator.getInstance().lookup(IWfmProDefQry.class);
		WfmProdefVO vo = null;
		try {
			vo = qry.getProDefVOByProDefPk(pk);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		return vo;
	}
	public Map<String, Node> getTaskMap() {
		return taskMap;
	}
	public Map<String, Node> getEventMap() {
		return eventMap;
	}
	public Map<String, Route> getRouteMap() {
		return routeMap;
	}
	public List<Node> getTaskList() {
		return taskList;
	}
	public List<Node> getEventList() {
		return eventList;
	}
	public List<Route> getRouteList() {
		return routeList;
	}
	public Map<String, GateWay> getGatewayMap() {
		return gatewayMap;
	}
	public List<GateWay> getGatewayList() {
		return gatewayList;
	}
	public void setGatewayList(List<GateWay> gatewayList) {
		this.gatewayList = gatewayList;
	}
	public void addRoute(Route route) {
		this.routeMap.put(route.getId(), route);
		routeList.add(route);
	}
	public void addEvent(Node node) {
		this.eventMap.put(node.getId(), node);
		eventList.add(node);
	}
	public void addTask(Node node) {
		this.taskMap.put(node.getId(), node);
		taskList.add(node);
	}
	public void addGateway(GateWay gateway) {
		this.gatewayMap.put(gateway.getId(), gateway);
		gatewayList.add(gateway);
	}
	public void setEleMap() {
		for (int i = 0; i < taskList.size(); i++) {
			Node tmp = taskList.get(i);
			this.eleMap.put(tmp.getId(), tmp);
		}
		for (int i = 0; i < eventList.size(); i++) {
			Node tmp = eventList.get(i);
			this.eleMap.put(tmp.getId(), tmp);
		}
		for (int i = 0; i < gatewayList.size(); i++) {
			Node tmp = gatewayList.get(i);
			this.eleMap.put(tmp.getId(), tmp);
		}
	}
	public Map<String, Element> getEleMap() {
		return eleMap;
	}
}
