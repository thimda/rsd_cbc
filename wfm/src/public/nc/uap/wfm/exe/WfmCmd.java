package nc.uap.wfm.exe;
import java.util.List;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.builder.WfmBeforeFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmCommitFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmNextFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmRejectFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmTransmitFlowInfoCtxBuilder;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.AddSignUserInfoCtx;
import nc.uap.wfm.context.AfterAddSignTaskInfoCtx;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.runtime.RejectHumActInfoUtil;
import nc.uap.wfm.server.BizProcessServer;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class WfmCmd extends UifCommand {
	private WfmFormInfoCtx formInfoCtx;
	private WfmFlowInfoCtx flowInfoCtx;
	private String taskPk = null;
	private String flowTypePk = null;
	private String operator = null;
	public void execute() {
		formInfoCtx = this.builderWfmFormInfoCtx();
		taskPk = WfmTaskUtil.getTaskPk();
		flowTypePk = WfmTaskUtil.getFlowTypePk();
		operator = WfmTaskUtil.getOperator();
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator) || WfmConstants.WfmOperator_UnAgree.equalsIgnoreCase(operator)) {
			this.handlerStartAndNext();
		}
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator)) {
			this.handlerReject();
		}
		if (WfmConstants.WfmOperator_Transmit.equalsIgnoreCase(operator)) {
			this.handlerTransmit();
		}
		if (WfmConstants.WfmOperator_BeforeAddSign.equalsIgnoreCase(operator)) {
			this.handlerBeforeAddSign();
		}
		if (WfmConstants.WfmOperator_AfterAddSign.equalsIgnoreCase(operator)) {
			this.handlerAfterAddSign();
		}
		if (WfmConstants.WfmOperator_Deliver.equalsIgnoreCase(operator)) {
			this.handlerDeliver();
		}
	}
	protected void handlerStartAndNext() {
		List<HumActInfoPageCtx> nextInfos = null;
		if (taskPk == null || taskPk.length() == 0) {
			nextInfos = new NextHumActInfoUtil().getStartNextHumActInfo(flowTypePk, formInfoCtx);
		} else {
			nextInfos = new NextHumActInfoUtil().getNextHumActInfo(taskPk, formInfoCtx);
		}
		if (nextInfos.size() == 1 && !nextInfos.get(0).isAssign()) {
			if (taskPk == null || taskPk.length() == 0) {
				flowInfoCtx = new WfmCommitFlowInfoCtxBuilder(flowTypePk, taskPk).builder();
			} else {
				flowInfoCtx = new WfmNextFlowInfoCtxBuilder(flowTypePk, taskPk).builder();
			}
			IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flowInfoCtx);
			IFlowResponse response = BizProcessServer.createFlowResponse();
			BizProcessServer.exe(request, response);
		} else {
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.WfmPubView_ExeTask, "600", "430", "��ָ�ɲ�����");
			return;
		}
		if (taskPk == null || taskPk.length() == 0) {
			AppInteractionUtil.showMessageDialog("�����ɹ�");
		} else {
			AppInteractionUtil.showMessageDialog("ִ�гɹ�");
		}
	}
	protected void handlerReject() {
		Task task = WfmTaskUtil.getTaskFromSessionCache(taskPk);
		HumAct humAct = task.getHumActIns().getHumAct();
		if (!humAct.isNotReject()) {
			throw new LfwRuntimeException("�û�����˻�");
		}
		List<HumActInfoPageCtx> rejectInfos = new RejectHumActInfoUtil().getRejectHumActInfo(task);
		if (rejectInfos.size() == 1) {
			HumActInfoPageCtx oneInfo = rejectInfos.get(0);
			if (oneInfo.getUserPks() == null || oneInfo.getUserPks().length() == 0) {
				throw new LfwRuntimeException("��ѡ��Ҫ���ص���");
			}
			if (oneInfo.getUserPks().indexOf(",") > -1) {
				AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.WfmPubView_ExeTask, "600", "430", "��ָ�ɲ�����");
				return;
			}
			flowInfoCtx = new WfmRejectFlowInfoCtxBuilder(flowTypePk, taskPk).builder();
			IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flowInfoCtx);
			IFlowResponse response = BizProcessServer.createFlowResponse();
			BizProcessServer.exe(request, response);
		} else {
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.WfmPubView_ExeTask, "600", "430", "��ָ�ɲ�����");
			return;
		}
		AppInteractionUtil.showMessageDialog("ִ�гɹ�");
	}
	protected void handlerTransmit() {
		flowInfoCtx = new WfmTransmitFlowInfoCtxBuilder(flowTypePk, taskPk).builder();
		IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flowInfoCtx);
		IFlowResponse response = BizProcessServer.createFlowResponse();
		BizProcessServer.exe(request, response);
		AppInteractionUtil.showMessageDialog("ִ�гɹ�");
	}
	protected void handlerBeforeAddSign() {
		flowInfoCtx = new WfmBeforeFlowInfoCtxBuilder(flowTypePk, taskPk).builder();
		IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flowInfoCtx);
		IFlowResponse response = BizProcessServer.createFlowResponse();
		BizProcessServer.exe(request, response);
		AppInteractionUtil.showMessageDialog("ִ�гɹ�");
	}
	protected void handlerAfterAddSign() {
		AfterAddSignTaskInfoCtx flowInfoCtx = new AfterAddSignTaskInfoCtx();
		flowInfoCtx.setTaskPk(WfmTaskUtil.getTaskPk());
		flowInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		flowInfoCtx.setOpinion(WfmTaskUtil.getOpinion());
		flowInfoCtx.setAfterAddSignOpinion("");
		AddSignUserInfoCtx userInfo = new AddSignUserInfoCtx();
		userInfo.setUserPk("");
		flowInfoCtx.setAddSingUsers(new AddSignUserInfoCtx[] { userInfo });
		IFlowRequest request = BizProcessServer.createFlowRequest(formInfoCtx, flowInfoCtx);
		IFlowResponse response = BizProcessServer.createFlowResponse();
		BizProcessServer.exe(request, response);
	}
	protected void handlerDeliver() {}
	public WfmFormInfoCtx builderWfmFormInfoCtx() {
		return WfmTaskUtil.getWfmFormInfoCtx();
	}
}
