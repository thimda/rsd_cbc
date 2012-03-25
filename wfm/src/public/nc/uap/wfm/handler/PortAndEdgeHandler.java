package nc.uap.wfm.handler;
import java.util.ArrayList;
import java.util.List;
import nc.uap.wfm.model.Activity;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.Event;
import nc.uap.wfm.model.ExcGat;
import nc.uap.wfm.model.GateWay;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.IGraphElement;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.IncGat;
import nc.uap.wfm.model.ManAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.RecAct;
import nc.uap.wfm.model.ScrAct;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.StartEvent;
public class PortAndEdgeHandler {
	public static IPort[] getNextHumActs(IPort port) {
		ArrayList<IPort> ports = new ArrayList<IPort>();
		PortAndEdgeHandler.getNextHumActs(ports, PortAndEdgeHandler.getOutEdges(port));
		return ports.toArray(new IPort[0]);
	}
	public static IEdge[] getInEdges(IPort port) {
		if (port instanceof Event) {
			IEdge edge = ((Event) port).getInEdge();
			return new IEdge[] { edge };
		}
		if (port instanceof Activity) {
			IEdge edge = ((Activity) port).getInEdge();
			return new IEdge[] { edge };
		}
		if (port instanceof GateWay) {
			IEdge[] edges = ((GateWay) port).getInEdges();
			return edges;
		}
		if (port instanceof ProDef) {
			IEdge edge = ((ProDef) port).getInEdge();
			return new IEdge[] { edge };
		}
		return null;
	}
	public static IEdge[] getOutEdges(IPort port) {
		if (port instanceof Event) {
			IEdge edge = ((Event) port).getOutEdge();
			return new IEdge[] { edge };
		}
		if (port instanceof Activity) {
			IEdge edge = ((Activity) port).getOutEdge();
			return new IEdge[] { edge };
		}
		if (port instanceof GateWay) {
			IEdge[] edges = ((GateWay) port).getOutEdges();
			return edges;
		}
		if (port instanceof ProDef) {
			IEdge edge = ((ProDef) port).getOutEdge();
			return new IEdge[] { edge };
		}
		return null;
	}
	public static IPort[] getSourcePorts(IEdge edge) {
		return new IPort[] { edge.getSource() };
	}
	public static IPort[] getTargetPorts(IEdge edge) {
		return new IPort[] { edge.getTarget() };
	}
	public static boolean isContainPort(IPort ports[], IPort port) {
		int count = 0;
		if (ports == null || ports.length == 0) {
			return false;
		}
		for (int k = 0; k < ports.length; k++) {
			if (port.getId().equalsIgnoreCase(ports[k].getId())) {
				break;
			} else {
				count++;
			}
		}
		if (ports.length > count) {
			return true;
		} else {
			return false;
		}
	}
	private static void getNextHumActs(List<IPort> ports, IGraphElement[] ges) {
		for (int i = 0; i < ges.length; i++) {
			IGraphElement o = ges[i];
			if (o instanceof IPort) {
				IPort port = (IPort) o;
				if (port instanceof GateWay) {
					/**
					 * 唯一网关
					 */
					if (port instanceof ExcGat) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
						continue;
					}
					/**
					 * 包含网关
					 */
					if (port instanceof IncGat) {
						PortAndEdgeHandler.getNextHumActs(ports, PortAndEdgeHandler.getOutEdges(port));
						continue;
					}
				}
				if (port instanceof Activity) {
					/**
					 * 人工活动
					 */
					if (port instanceof HumAct) {
						HumAct humAct = (HumAct) port;
						ports.add(humAct);
					}
					/**
					 * 手工活动
					 */
					if (port instanceof ManAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
						continue;
					}
					/**
					 * 脚本活动
					 */
					if (port instanceof ScrAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
						continue;
					}
					/**
					 * 接收活动
					 */
					if (port instanceof RecAct) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
						continue;
					}
				}
				if (port instanceof Event) {
					/**
					 * 开始节点
					 */
					if (port instanceof StartEvent) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
						continue;
					}
					/**
					 * 结束节点
					 */
					if (port instanceof EndEvent) {
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						ports.add(port);
						PortAndEdgeHandler.getNextHumActs(ports, outEdges);
					}
				}
				if (port instanceof ProDef) {
					ports.add(port);
				}
			}
			if (o instanceof IEdge) {
				/**
				 * 连线处理
				 */
				SequenceFlow sf = (SequenceFlow) o;
				// ILogicDecision logic = (ILogicDecision)
				// PwfmUtil.loadClass(sf.getSelfDefClass());
				// if (logic.judge(null, sf)) {
				PortAndEdgeHandler.getNextHumActs(ports, PortAndEdgeHandler.getTargetPorts(sf));
				// }
			}
		}
	}
}
