package nc.uap.wfm.cmd;
import nc.uap.wfm.context.ProInsStateSetFlowInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.vo.WfmProInsStateVO;
import nc.vo.pub.lang.UFDate;
public class ProInsStateSetCmd implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		ProInsStateSetFlowInfoCtx flwInfoCtx = (ProInsStateSetFlowInfoCtx) PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		WfmProInsStateVO proInsStateVo = new WfmProInsStateVO();
		proInsStateVo.setPk_proins(flwInfoCtx.getProInsPk());
		proInsStateVo.setPk_user(flwInfoCtx.getUserPk());
		proInsStateVo.setReason(flwInfoCtx.getReason());
		proInsStateVo.setState(flwInfoCtx.getState());
		proInsStateVo.setActiondate(new UFDate());
		WfmServiceFacility.getProInsStateBill().saveProInsState(proInsStateVo);
		return null;
	}
}
