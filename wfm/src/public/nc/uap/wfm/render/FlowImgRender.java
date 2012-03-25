package nc.uap.wfm.render;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.Node;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Route;
import nc.uap.wfm.vo.WfmHumActInsVO;
import nc.uap.wfm.vo.WfmTaskVO;
import nc.vo.jcom.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class FlowImgRender {
	public static Document getRenderProcessXml(String rootProInsPk) {
		Map<String, Map<String, String>> taskTipMap = FlowImgRender.getTaskTipMap(rootProInsPk);
		List<Route> routeList = new ArrayList<Route>();
		List<Node> nodeList = new ArrayList<Node>();
		FlowImgRender.setNodeListAndRoutList(nodeList, routeList, taskTipMap, rootProInsPk);
		Element xmlNode = null;
		Route route = null;
		Node node = null;
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Nodes");
		doc.appendChild(root);
		int size = nodeList.size();
		for (int i = 0; i < size; i++) {
			xmlNode = doc.createElement("Node");
			node = nodeList.get(i);
			xmlNode.setAttribute("id", node.getId());
			xmlNode.setAttribute("pid", node.getPid());
			xmlNode.setAttribute("isPending", String.valueOf(node.isNotPending()));
			xmlNode.setAttribute("isExe", String.valueOf(node.isNotExe()));
			xmlNode.setAttribute("isPas", String.valueOf(node.isNotPas()));
			xmlNode.setAttribute("isCntNode", String.valueOf(node.isNotCntNode()));
			xmlNode.setAttribute("isAddSign", String.valueOf(node.isNotAddSign()));
			xmlNode.setAttribute("isBack", String.valueOf(node.isNotReject()));
			xmlNode.setAttribute("tooltip", node.getTooltip());
			root.appendChild(xmlNode);
		}
		size = routeList.size();
		for (int i = 0; i < size; i++) {
			xmlNode = doc.createElement("Route");
			route = routeList.get(i);
			if (route.getSrc() == null || route.getSrc().length() == 0) {
				continue;
			}
			xmlNode.setAttribute("src", route.getSrc());
			xmlNode.setAttribute("target", route.getTarget());
			xmlNode.setAttribute("isBack", String.valueOf(route.isNotBack()));
			xmlNode.setAttribute("tooptip", route.getTooltip());
			root.appendChild(xmlNode);
		}
		return doc;
	}
	public static Node getNodeById(List<Node> list, String id) {
		if (list == null || list.size() == 0) {
			return null;
		}
		Node node = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			node = list.get(i);
			if (node.getId() == null || node.getId().length() == 0) {
				continue;
			}
			if (node.getId().equalsIgnoreCase(id)) {
				return node;
			}
		}
		return null;
	}
	public static Route getRouteBySrcAndTarget(List<Route> list, String src, String target) {
		if (list == null || list.size() == 0) {
			return null;
		}
		Route route = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			route = list.get(i);
			if (route.getSrc() == null || route.getSrc().length() == 0 || route.getTarget() == null || route.getTarget().length() == 0) {
				continue;
			}
			if (route.getSrc().equalsIgnoreCase(src) && route.getTarget().equalsIgnoreCase(target)) {
				return route;
			}
		}
		return null;
	}
	public static void setNodeListAndRoutList(List<Node> nodeList, List<Route> routeList, Map<String, Map<String, String>> taskTipMap, String rootProInsPk) {
		WfmHumActInsVO[] humActInsVos = null;
		ProIns proIns = null;
		ProDef proDef = null;
		Map<String, IPort> ports = null;
		try {
			humActInsVos = NCLocator.getInstance().lookup(IWfmHumActInsQry.class).getHumActInsesByRootProInsPk(rootProInsPk);
			if (humActInsVos == null || humActInsVos.length == 0) {
				return;
			}
			proIns = NCLocator.getInstance().lookup(IWfmProInsQry.class).getProInsByPk(humActInsVos[0].getPk_proins());
			proDef = proIns.getProDef();
			ports = proDef.getPorts();
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		Map<String, String> humActInsTipMap = null;
		WfmHumActInsVO humActInsVo = null;
		String humActId = null;
		String pHumActId = null;
		Route route = null;
		Node node = null;
		IPort port = null;
		for (int i = 0; i < humActInsVos.length; i++) {
			humActInsVo = humActInsVos[i];
			humActId = humActInsVo.getHumact_id();
			pHumActId = humActInsVo.getPhumact_id();
			node = FlowImgRender.getNodeById(nodeList, humActId);
			route = FlowImgRender.getRouteBySrcAndTarget(routeList, pHumActId, humActId);
			port = ports.get(humActId);
			if (node == null) {
				node = new Node();
				nodeList.add(node);
			}
			if (route == null) {
				route = new Route();
				routeList.add(route);
			}
			// 待办
			if (HumActIns.Run.equalsIgnoreCase(humActInsVo.getState())) {
				node.setNotCntNode(true);
				node.setNotPending(true);
			}
			// 在办
			else if (HumActIns.Exe.equalsIgnoreCase(humActInsVo.getState())) {
				node.setNotCntNode(true);
				node.setNotExe(true);
			}
			// 已办
			else if (HumActIns.End.equalsIgnoreCase(humActInsVo.getState())) {
				node.setNotPas(true);
			}
			// 驳回
			if (humActInsVo.getIsnotreject() != null) {
				if (humActInsVo.getIsnotreject().booleanValue()) {
					node.setNotPending(false);
					route.addReject(1);
					route.setNotBack(true);
					if (humActInsVo.getIsnotexe().booleanValue()) {
						node.setNotCntNode(false);
						node.setNotReject(false);
					} else {
						node.setNotCntNode(true);
						if (HumActIns.Run.equalsIgnoreCase(humActInsVo.getState())) {
							node.setNotReject(true);
						}
					}
				} else {
					route.addSubmit(1);
				}
			} else {
				route.addSubmit(1);
			}
			if (port instanceof HumAct) {
				HumAct humAct = (HumAct) port;
				if (humAct.isNotAddSign()) {
					try {
						WfmTaskVO[] vos = NCLocator.getInstance().lookup(IWfmTaskQry.class).getAddSignTaskByHumActInsPk(humActInsVo.getPk_humactins());
						if (vos != null && vos.length != 0) {
							node.setNotAddSign(true);
						}
					} catch (WfmServiceException e) {
						LfwLogger.error(e.getMessage(), e);
						throw new LfwRuntimeException(e.getMessage());
					}
				}
			}
			node.setId(humActId);
			node.setPid(pHumActId);
			route.setSrc(pHumActId);
			route.setTarget(humActId);
			humActInsTipMap = taskTipMap.get(humActId);
			if (humActInsTipMap == null) {
				continue;
			}
			node.setTooltip(HumActTip.getHumActInsTip(humActInsTipMap));
		}
	}
	/**
	 * Map<节点ID,Map<活动实列Pk,Lis<提示信息>>>;
	 */
	public static Map<String, Map<String, String>> getTaskTipMap(String rootProInsPk) {
		WfmTaskVO[] taskVos = null;
		try {
			taskVos = NCLocator.getInstance().lookup(IWfmTaskQry.class).getTasksConAddSignStopByRootProInsPk(rootProInsPk);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (taskVos == null || taskVos.length == 0) {
			return null;
		}
		WfmTaskVO taskVo = null;
		Map<String, Map<String, String>> taskTipMap = new HashMap<String, Map<String, String>>();
		Map<String, String> humActInsTipMap = null;
		String tipStr = "";
		String portId = null;
		String humActInsPk = null;
		for (int i = 0; i < taskVos.length; i++) {
			taskVo = taskVos[i];
			humActInsPk = taskVo.getPk_humactins();
			portId = taskVo.getPort_id();
			humActInsTipMap = taskTipMap.get(portId);
			if (humActInsTipMap == null || humActInsTipMap.size() == 0) {
				humActInsTipMap = new HashMap<String, String>();
				taskTipMap.put(portId, humActInsTipMap);
			}
			tipStr = humActInsTipMap.get(humActInsPk);
			if (tipStr == null) {
				tipStr = "";
			}
			if (tipStr.length() == 0) {
				tipStr = TaskTipUtil.getTipStrByTaskVo(taskVo);
			} else {
				tipStr = tipStr + "\n" + TaskTipUtil.getTipStrByTaskVo(taskVo);
			}
			humActInsTipMap.put(humActInsPk, tipStr);
			tipStr = "";
		}
		return taskTipMap;
	}
}
