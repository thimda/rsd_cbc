package nc.uap.wfm.gui;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nc.uap.wfm.render.FlowImgRender;
/**
 * 
 * 2011-5-27 ����11:33:01 limingf
 */
public class ProInsParse {
	/**
	 * 
	 * @param proInsPk
	 */
	public static void parseState(DrawBoard d, String proInsPk) {
		if (proInsPk == null || "".equals(proInsPk))
			return;
		// �������ͻ
		Map<String, Map<String, String>> taskTipMap = FlowImgRender.getTaskTipMap(proInsPk);
		List<nc.uap.wfm.model.Route> routeList = new ArrayList<nc.uap.wfm.model.Route>();
		List<nc.uap.wfm.model.Node> nodeList = new ArrayList<nc.uap.wfm.model.Node>();
		FlowImgRender.setNodeListAndRoutList(nodeList, routeList, taskTipMap, proInsPk);
		Map<String, Element> eleMap = d.getEleMap();
		// �ڵ�ִ��״̬,0,δ����Ĭ��ֵ����1,���죬2���ڰ�,3,�Ѱ죬4��ͣ�죬5���˻أ�
		// _state+8,��ǰ״̬��_state+16,��ǩ��_state+24������
		for (nc.uap.wfm.model.Node node : nodeList) {
			int state = 0;
			if (node.isNotPending())
				state = 1;
			if (node.isNotExe())
				state = 2;
			if (node.isNotPas())
				state = 3;
			if (node.isNotReject())
				state = 5;
			if (node.isNotCntNode())
				state += 8;
			if (node.isNotAddSign())
				state += 16;
			if (node.isNotTran())
				state += 24;
			String id = node.getId();
			eleMap.get(id).setState(state);
		}
		setRouteState(d);
	}
	public static void setRouteState(DrawBoard d) {
		Map<String, Element> eleMap = d.getEleMap();
		List<Route> routeList = d.getRouteList();
		for (Route route : routeList) {
			Element source = eleMap.get(route.getSource());
			Element target = eleMap.get(route.getTarget());
			if (source.getState() == 1 || source.getState() == 2 || source.getState() == 3)
				route.setState(3);
			if (target.getState() == 5)
				route.setState(5);
		}
	}
}
