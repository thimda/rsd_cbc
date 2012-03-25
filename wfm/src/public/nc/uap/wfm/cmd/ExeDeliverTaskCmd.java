package nc.uap.wfm.cmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
/**
 * 完成任务命令
 * 
 * @author tianchw
 * 
 */
public class ExeDeliverTaskCmd extends AbstractCommand implements ICommand<Task> {
	public ExeDeliverTaskCmd() {
		super();
	}
	public Task execute() throws WfmServiceException {
		Task task=PwfmContext.getCurrentBpmnSession().getTask();
		task.setState(Task.State_Readed);
		task.setIsnotexe(new UFBoolean(true));
		task.setIsnotpas(new UFBoolean(true));
		task.setSignDate(new UFDate());
		task.setEndDate(null);
		task.setPk_executer(PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
		task.setFinishType(Task.FinishType_Normal);
		task.setHandlepiece(Task.HandlerPiece_Readed);
		task.asyn();
		return task;
	}
}
