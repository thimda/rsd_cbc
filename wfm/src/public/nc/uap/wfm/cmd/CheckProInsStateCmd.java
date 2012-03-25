package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsStateQry;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
/**
 * 完成任务命令
 * @author tianchw
 *
 */
public class CheckProInsStateCmd extends AbstractCommand implements ICommand<Void> {
	public CheckProInsStateCmd() {
		super();
	}
	public Void execute() throws WfmServiceException {
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		String proInsPk = task.getProIns().getPk_proins();
		String state = NCLocator.getInstance().lookup(IWfmProInsStateQry.class).getProInsState(proInsPk);
		if (ProIns.Suspended.equalsIgnoreCase(state)) {
			throw new WfmServiceException("对不起，该流程已经被挂起");
		} else if (ProIns.End.equalsIgnoreCase(state)) {
			throw new WfmServiceException("对不起，该流程已经被终止");
		}
		return null;
	}
}
