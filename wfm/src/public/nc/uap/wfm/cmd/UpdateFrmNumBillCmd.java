package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.IFrmNumBillGen;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.DefaultFormVO;
public class UpdateFrmNumBillCmd extends AbstractCommand implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		DefaultFormVO formVOs = (DefaultFormVO) PwfmContext.getCurrentBpmnSession().getFormVo();
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		String frmDefPk = null;
		String runnum = null;
		if (task == null) {
			frmDefPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.WfmAppAttr_FolwTypePk);
		} else {
			frmDefPk = task.getPk_frmdef();
		}
		String frmNumBill = formVOs.getProp().get(DblConstants.MakeFormNumb);
		if (frmNumBill == null || frmNumBill.length() == 0) {
			runnum = NCLocator.getInstance().lookup(IFrmNumBillGen.class).genFrmNumBillByFrmDefPk_RequiresNew(frmDefPk);
			formVOs.getProp().put(DblConstants.MakeFormNumb, runnum);
		}
		return null;
	}
}
