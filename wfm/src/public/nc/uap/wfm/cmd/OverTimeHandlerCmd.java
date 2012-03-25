package nc.uap.wfm.cmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.overtimesgy.IOverTimeSgy;
import nc.uap.wfm.overtimesgy.OverTimeSgyManager;
public class OverTimeHandlerCmd extends AbstractCommand implements ICommand<Void>, IOverTimeSgy {
	private Task task;
	public OverTimeHandlerCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		task=PwfmContext.getCurrentBpmnSession().getTask();
		if (OverTimeSgyManager.isNotOverTime(task)) {
			if (OverTimeSgyManager.isNotRemind(task)) {
				int remindType = OverTimeSgyManager.getRemindType(task);
				switch (remindType) {
				case OverTimeRemiand_Email:
					break;
				case OverTimeRemiand_Message:
					break;
				}
				int action = OverTimeSgyManager.getOverTimeAction(task);
				HumActIns humActIns = task.getHumActIns();
				ProIns proIns = humActIns.getProIns();
				switch (action) {
				case OverTimeSgy_Wait:
					break;
				case OverTimeSgy_Rollback:
					break;
				case OverTimeSgy_Cancel:
					proInsExe.endProIns(proIns);
					break;
				case OverTimeSgy_Suspend:
					proInsExe.suspendedProIns(proIns);
					break;
				case OverTimeSgy_Transrer:
					break;
				}
			}
		}
		return null;
	}
}
