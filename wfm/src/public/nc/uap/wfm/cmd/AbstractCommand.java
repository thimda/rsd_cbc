package nc.uap.wfm.cmd;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.ILogicDecision;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.execution.HumActInsExecution;
import nc.uap.wfm.execution.ProInsExecution;
import nc.uap.wfm.execution.TaskExecution;
import nc.uap.wfm.handler.EndEventHandler;
import nc.uap.wfm.handler.GateWayLogicHandler;
import nc.uap.wfm.handler.HumActInsHandler;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.handler.ProDefHandler;
import nc.uap.wfm.model.Activity;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.Event;
import nc.uap.wfm.model.ExcGat;
import nc.uap.wfm.model.GateWay;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IEdge;
import nc.uap.wfm.model.IGraphElement;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.IncGat;
import nc.uap.wfm.model.ManAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.RecAct;
import nc.uap.wfm.model.ScrAct;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import org.apache.commons.lang.StringUtils;
/**
 * ������������Ҫ����ע�������ִ����
 * 
 * @author tianchw
 * 
 */
public abstract class AbstractCommand {
	protected ProInsExecution proInsExe = ProInsExecution.getInstance();
	protected HumActInsExecution humActInsExe = HumActInsExecution.getInstance();
	protected TaskExecution taskExe = TaskExecution.getInstance();
	/**
	 * ��������������
	 * 
	 * @param actIns
	 * @throws WfmServiceException
	 */
	public void signal(ProIns pProIns, ProIns rProIns, HumActIns pHumActIns, IPort[] nextPorts, IGraphElement[] ges, Task pTask) throws WfmServiceException {
		for (int i = 0; i < ges.length; i++) {
			IGraphElement o = ges[i];
			if (o instanceof IPort) {
				IPort port = (IPort) o;
				if (port instanceof GateWay) {
					if (port instanceof ExcGat) {// Ψһ����
						ExcGat gateWay = (ExcGat) port;
						if (GateWayLogicHandler.gateWayLogicHandler(gateWay) && GateWayLogicHandler.checkExcGatCondition(gateWay)) {
							IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
							this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
						}
					} else if (port instanceof IncGat) {// ��������
						IncGat gateWay = (IncGat) port;
						if (GateWayLogicHandler.gateWayLogicHandler(gateWay)) {
							IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
							this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
						}
					}
				} else if (port instanceof Activity) {
					if (port instanceof HumAct) {// �˹��
						HumActIns newHumActIns = new HumActInsHandler((HumAct) port, nextPorts, pProIns, rProIns, pHumActIns, pTask).handler();
						if (StringUtils.isNotBlank(((HumAct) port).getActionType()) && WfmConstants.ActionType_Cirulated.equalsIgnoreCase(((HumAct) port).getActionType())) {
							IEdge[] outEdges = PortAndEdgeHandler.getOutEdges((HumAct) port);
							this.signal(pProIns, rProIns, newHumActIns, nextPorts, outEdges, pTask);
						}
					} else if (port instanceof ManAct) {// �ֹ��
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
					} else if (port instanceof ScrAct) {// �ű��
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
					} else if (port instanceof RecAct) {// ���ջ
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
						continue;
					}
				} else if (port instanceof Event) {
					if (port instanceof StartEvent) {// ��ʼ�ڵ�
						IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(port);
						this.signal(pProIns, rProIns, pHumActIns, nextPorts, outEdges, pTask);
						continue;
					} else if (port instanceof EndEvent) {// �����ڵ�
						if (PortAndEdgeHandler.isContainPort(nextPorts, port)) {
							new EndEventHandler(pTask, port, pHumActIns, pProIns, rProIns).handler();
							if (StringUtils.isNotBlank(pProIns.getProDef().getParent_id())) {
								IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(pProIns.getProDef());
								this.signal(pProIns.getParent(), rProIns, pHumActIns, null, outEdges, pTask);
							}
						}
					}
				} else if (port instanceof ProDef) {// �ڵ����������̶���
					ProDef subProDef = (ProDef) port;
					ProIns subProIns = new ProDefHandler(subProDef, pProIns).handler();
					IEdge[] outEdges = PortAndEdgeHandler.getOutEdges(subProDef.getStart());
					this.signal(subProIns, rProIns, pHumActIns, null, outEdges, pTask);
				}
			}
			if (o instanceof IEdge) {// ���ߴ���
				SequenceFlow sf = (SequenceFlow) o;
				ILogicDecision logic = (ILogicDecision) WfmClassUtil.loadClass(sf.getSelfDefClass());
				WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
				if (logic.judge(pTask, sf, formVo)) {
					this.signal(pProIns, rProIns, pHumActIns, nextPorts, PortAndEdgeHandler.getTargetPorts(sf), pTask);
				}
			}
		}
	}
}
