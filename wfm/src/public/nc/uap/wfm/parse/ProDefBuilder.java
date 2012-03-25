package nc.uap.wfm.parse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Activity;
import nc.uap.wfm.model.Event;
import nc.uap.wfm.model.GateWay;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.SequenceFlow;
public class ProDefBuilder {
	/**
	 * 组装流程定义
	 * @param proDef
	 * @return
	 * @throws WfmServiceException
	 */
	public static ProDef assembleProDef(ProDef proDef) throws WfmServiceException {
		Map<String, SequenceFlow> sequenceFlows = proDef.getSquFlws();
		Map<String, IPort> ports = proDef.getPorts();
		Set<String> keys = ports.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			IPort port = ports.get(key);
			IEdge[] inEdges = getInEdgesByPort(sequenceFlows, port);
			IEdge[] outEdges = getOutEdgesByPort(sequenceFlows, port);
			if (port instanceof Event) {
				Event event = (Event) port;
				event.setProdef_id(proDef.getId());
				if (inEdges != null && inEdges.length == 1) {
					event.setInEdge(inEdges[0]);
					inEdges[0].setTarget(port);
				}
				if (outEdges != null && outEdges.length == 1) {
					event.setOutEdge(outEdges[0]);
					outEdges[0].setSource(port);
				}
				continue;
			}
			if (port instanceof Activity) {
				Activity activity = (Activity) port;
				activity.setProdef_id(proDef.getId());
				if (inEdges != null && inEdges.length == 1) {
					activity.setInEdge(inEdges[0]);
					inEdges[0].setTarget(port);
				}
				if (outEdges != null && outEdges.length == 1) {
					activity.setOutEdge(outEdges[0]);
					outEdges[0].setSource(port);
				}
				continue;
			}
			if (port instanceof GateWay) {
				GateWay gateway = (GateWay) port;
				gateway.setProdef_id(proDef.getId());
				if (inEdges != null) {
					for (int i = 0; i < inEdges.length; i++) {
						if (inEdges[i] != null) {
							gateway.addInEdge(inEdges[i]);
							inEdges[i].setTarget(port);
						}
					}
				}
				if (outEdges != null) {
					for (int i = 0; i < outEdges.length; i++) {
						if (outEdges[i] != null) {
							gateway.addOutEdge(outEdges[i]);
							outEdges[i].setSource(port);
						}
					}
				}
				continue;
			}
			if (port instanceof ProDef) {
				ProDef sub = (ProDef) port;
				sub.setParent_id(proDef.getId());
				if (proDef.getRoot_id() == null || proDef.getRoot_id().length() == 0) {
					sub.setRoot_id(proDef.getId());
				} else {
					sub.setRoot_id(proDef.getRoot_id());
				}
				if (inEdges != null && inEdges.length == 1) {
					sub.setInEdge(inEdges[0]);
					inEdges[0].setTarget(port);
				}
				if (outEdges != null && outEdges.length == 1) {
					sub.setOutEdge(outEdges[0]);
					outEdges[0].setSource(port);
				}
				assembleProDef(sub);
				continue;
			}
		}
		return proDef;
	}
	/**
	 * 获取出去线
	 * @param sequenceFlows
	 * @param source
	 * @return
	 * @throws WfmServiceException
	 */
	private static IEdge[] getOutEdgesByPort(Map<String, SequenceFlow> sequenceFlows, IPort port) throws WfmServiceException {
		List<IEdge> list = new ArrayList<IEdge>();
		Set<String> keys = sequenceFlows.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			SequenceFlow sf = sequenceFlows.get(key);
			if (sf.getSourceRef().equalsIgnoreCase(port.getId())) {
				list.add(sf);
			}
		}
		return (IEdge[]) list.toArray(new SequenceFlow[0]);
	}
	/**
	 * 获取进去的线
	 * @param sequenceFlows
	 * @param target
	 * @return
	 * @throws WfmServiceException
	 */
	private static IEdge[] getInEdgesByPort(Map<String, SequenceFlow> sequenceFlows, IPort port) throws WfmServiceException {
		List<SequenceFlow> list = new ArrayList<SequenceFlow>();
		Set<String> keys = sequenceFlows.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			SequenceFlow sf = sequenceFlows.get(key);
			if (sf.getTargetRef().equalsIgnoreCase(port.getId())) {
				list.add(sf);
			}
		}
		return (IEdge[]) list.toArray(new SequenceFlow[0]);
	}
}
