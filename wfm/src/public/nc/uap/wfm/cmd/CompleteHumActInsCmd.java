package nc.uap.wfm.cmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.Task;
/**
 * 完成活动实例命令
 * @author tianchw
 *
 */
public class CompleteHumActInsCmd extends AbstractCommand implements ICommand<HumActIns> {
	public CompleteHumActInsCmd() {
		super();
	}
	public HumActIns execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		HumActIns humActIns = task.getHumActIns();
		humActIns = humActInsExe.completeHumActIns(humActIns);
		humActIns.setTasks(null);
		humActIns.asyn();
		return humActIns;
	}
}
