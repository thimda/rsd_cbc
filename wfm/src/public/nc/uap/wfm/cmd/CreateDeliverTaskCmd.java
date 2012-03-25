package nc.uap.wfm.cmd;
import nc.uap.wfm.context.DeliverInfoCtx;
import nc.uap.wfm.context.DeliverUserInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.scratchpad.TaskScratchpadUtil;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.vo.pub.lang.UFDate;
public class CreateDeliverTaskCmd extends AbstractCommand implements ICommand<Void> {
	public CreateDeliverTaskCmd() {}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		DeliverInfoCtx flwInfoCtx = (DeliverInfoCtx) PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		DeliverUserInfoCtx[] deliverUserInfo = PwfmContext.getCurrentBpmnSession().getDeliverUserInfo();
		if (deliverUserInfo == null) {
			return null;
		}
		Task newTask = null;
		for (int i = 0; i < deliverUserInfo.length; i++) {
			newTask = task.clone();
			newTask = taskExe.initTask(newTask);
			newTask.setPk_owner(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
			newTask.setStartDate(new UFDate());
			newTask.setPk_owner(deliverUserInfo[i].getUserPk());
			newTask.setPk_ownerdept(deliverUserInfo[i].getDeptPk());
			newTask.setState(Task.State_UnRead);
			newTask.setCreateType(Task.CreateType_Deliver);
			newTask.setScratchpad(this.genScratchPad(newTask, flwInfoCtx));
			newTask.asyn();
		}
		return null;
	}
	protected String genScratchPad(Task newTask, DeliverInfoCtx deliverFlwInfo) {
		String scratchPad = deliverFlwInfo.getScratchpad();
		if (scratchPad == null || scratchPad.length() == 0) {
			scratchPad = "";
			return scratchPad;
		}
		String userName = WfmTransUtil.getUserNameByUserPk(newTask.getPk_creater());
		String startDate = newTask.getStartDate().toString();
		scratchPad = TaskScratchpadUtil.getScratchpad(userName, startDate, scratchPad);
		return scratchPad;
	}
}
