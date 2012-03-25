package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.context.InterimTaskInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmAssignActorsVO;
/**
 *暂存流程
 * 
 * @author tianchw
 * 
 */
public class InterimTaskCmd extends AbstractCommand implements ICommand<Task> {
	public InterimTaskCmd() {}
	public Task execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		/**
		 * 暂存任务的意见和便签
		 */
		InterimTaskInfoCtx flwInfoCtx = (InterimTaskInfoCtx) PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		task.setOpinion(flwInfoCtx.getOpinion());
		task.setScratchpad(flwInfoCtx.getScratchpad());
		task.asyn();
		/**
		 * 暂存任务的指派信息
		 */
		WfmAssignActorsVO[] assignActors = flwInfoCtx.getAssignActors();
		NCLocator.getInstance().lookup(IWfmAssignActorsBill.class).saveAssignActors(assignActors);
		return task;
	}
}
