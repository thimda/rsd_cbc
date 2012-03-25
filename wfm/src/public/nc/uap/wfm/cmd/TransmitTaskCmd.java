package nc.uap.wfm.cmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.vo.pub.lang.UFDate;
public class TransmitTaskCmd extends AbstractCommand implements ICommand<Void> {
	public TransmitTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		String transmitUserPk = PwfmContext.getCurrentBpmnSession().getTransmitUserPk();
		Task newTask = task.clone();
		newTask = taskExe.initTask(newTask);
		newTask.setPk_owner(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		newTask.setPk_owner(transmitUserPk);
		newTask.setCreateType(Task.CreateType_Tramsmit);
		newTask.setPk_task(null);
		newTask.setStartDate(new UFDate());
		newTask.asyn();
		taskExe.completeTask(task);
		task.setFinishType(Task.FinishType_Tramsmit);
		task.asyn();
		// /**
		// * 任务转发时，将任务处理人的待办邮件标记为已读状态
		// */
		// String pk_user = task.getPk_owner();
		// String subject = task.getExt1();
		// if (subject == null) {
		// subject = task.getHumActIns().getHumAct().getName();
		// }
		// MailConstant.AddReturnMailTask(pk_user, subject);
		return null;
	}
}
