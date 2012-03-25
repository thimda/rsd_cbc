package nc.uap.wfm.cmd;
import nc.uap.wfm.context.AddSignTaskInfoCtx;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.scratchpad.TaskScratchpadUtil;
import nc.uap.wfm.trans.WfmTransUtil;
public abstract class CreateAddSignTaskCmd extends AbstractCommand implements ICommand<Void> {
	public CreateAddSignTaskCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		this.createAddSignTasks();
		this.updateTask();
		return null;
	}
	protected String genScratchPad(Task newTask, AddSignTaskInfoCtx addSignFlwInfo) {
		String scratchPad = addSignFlwInfo.getScratchpad();
		if (scratchPad == null || scratchPad.length() == 0) {
			scratchPad = "";
			return scratchPad;
		}
		String userName = WfmTransUtil.getUserNameByUserPk(newTask.getPk_creater());
		String startDate = newTask.getStartDate().toString();
		scratchPad = TaskScratchpadUtil.getScratchpad(userName, startDate, scratchPad);
		return scratchPad;
	}
	abstract protected void updateTask();
	abstract protected void createAddSignTasks();
}
