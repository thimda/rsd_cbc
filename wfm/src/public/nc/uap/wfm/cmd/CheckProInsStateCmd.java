package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsStateQry;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
/**
 * �����������
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
			throw new WfmServiceException("�Բ��𣬸������Ѿ�������");
		} else if (ProIns.End.equalsIgnoreCase(state)) {
			throw new WfmServiceException("�Բ��𣬸������Ѿ�����ֹ");
		}
		return null;
	}
}
