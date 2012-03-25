package nc.uap.wfm.cmd;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.AddSignUserInfoCtx;
import nc.uap.wfm.context.AfterAddSignTaskInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.model.Task;
public class CreateAfterAddSignTaskCmd extends CreateAddSignTaskCmd {
	protected void createAddSignTasks() {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		AfterAddSignTaskInfoCtx flwInfoCtx = (AfterAddSignTaskInfoCtx) PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		AddSignUserInfoCtx[] userInfo = flwInfoCtx.getAddSingUsers();
		if (userInfo == null || userInfo.length == 0) {
			throw new LfwRuntimeException("请指定后加签的用户");
		}
		int length = userInfo.length;
		Task newTask = null;
		AddSignUserInfoCtx tmpUserInfo = null;
		for (int i = 0; i < length; i++) {
			tmpUserInfo = userInfo[i];
			newTask = task.clone();
			newTask = taskExe.initTask(newTask);
			newTask.setPk_owner(tmpUserInfo.getUserPk());
			newTask.setPk_ownerdept(tmpUserInfo.getDeptPk());
			newTask.setCreateType(Task.CreateType_AfterAddSign);
			newTask.setParent(task);
			newTask.setScratchpad(this.genScratchPad(newTask, flwInfoCtx));
			newTask.asyn();
		}
	}
	protected void updateTask() {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
	    taskExe.completeTask(task);
	    task.asyn();
	}
}
