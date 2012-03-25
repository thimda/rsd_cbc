package nc.uap.wfm.comm.cmd;
import java.util.List;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.exception.LfwRuntimeException;
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
	WfmFlowInfoCtx flwInfoCtx = null;
	public void execute() {
		formInfoCtx = this.builderWfmFormInfoCtx();
		taskPk = WfmTaskUtil.getTaskPk();
		flowTypePk = WfmTaskUtil.getFlowTypePk();
		operator = WfmTaskUtil.getOperator();
		if (WfmConstants.Str_Agree.equalsIgnoreCase(operator) || WfmConstants.Str_UnAgree.equalsIgnoreCase(operator)) {
			this.handlerStartAndNext();
		}
		if (WfmConstants.Str_Reject.equalsIgnoreCase(operator)) {
			this.handlerReject();
		}
		if (WfmConstants.Str_Transmit.equalsIgnoreCase(operator)) {
			this.handlerTransmit();
		}
	}
	protected void afterAddSign() {
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
	protected void handlerStartAndNext() {
		List<HumActInfoPageCtx> nextInfos = null;
		if (taskPk == null || taskPk.length() == 0) {
			nextInfos = new NextHumActInfoUtil().getStartNextHumActInfo(flowTypePk, formInfoCtx);
		} else {
			nextInfos = new NextHumActInfoUtil().getNextHumActInfo(taskPk, formInfoCtx);
		}
		if (nextInfos.size() == 1 && !nextInfos.get(0).isAssign()) {
			if (taskPk == null || taskPk.length() == 0) {
				flwInfoCtx = new WfmCommitFlowInfoCtxBuilder(flowTypePk, taskPk).builderCommitFlwInfoCtx();
			} else {
				flwInfoCtx = new WfmNextFlowInfoCtxBuilder(flowTypePk, taskPk).builderNextFlwInfoCtx();
			}
			IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flwInfoCtx);
			IFlowResponse response = BizProcessServer.createFlowResponse();
			BizProcessServer.exe(request, response);
		} else {
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.PUBVIEW_ASSIGN, "600", "430", "请指派参与者");
			return;
		}
		if (taskPk == null || taskPk.length() == 0) {
			AppInteractionUtil.showMessageDialog("启动成功");
		} else {
			AppInteractionUtil.showMessageDialog("执行成功");
		}
	}
	protected void handlerReject() {
		Task task = WfmTaskUtil.getTaskFromSessionCache(taskPk);
		HumAct humAct = task.getHumActIns().getHumAct();
		if (!humAct.isNotReject()) {
			throw new LfwRuntimeException("该活动不能退回");
		}
		List<HumActInfoPageCtx> rejectInfos = new RejectHumActInfoUtil().getRejectHumActInfo(task);
		if (rejectInfos.size() == 1) {
			HumActInfoPageCtx oneInfo = rejectInfos.get(0);
			if (oneInfo.getUserPks() == null || oneInfo.getUserPks().length() == 0) {
				throw new LfwRuntimeException("请选择要驳回的人");
			}
			if (oneInfo.getUserPks().indexOf(",") > -1) {
				AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.PUBVIEW_ASSIGN, "600", "430", "请指派参与者");
				return;
			}
			flwInfoCtx = new WfmRejectFlowInfoCtxBuilder(flowTypePk, taskPk).builderRejectFlwInfoCtx();
			IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flwInfoCtx);
			IFlowResponse response = BizProcessServer.createFlowResponse();
			BizProcessServer.exe(request, response);
		} else {
			AppUtil.getCntAppCtx().getCurrentWindowContext().popView(WfmConstants.PUBVIEW_ASSIGN, "600", "430", "请指派参与者");
			return;
		}
		AppInteractionUtil.showMessageDialog("执行成功");
	}
	protected void handlerTransmit() {
		flwInfoCtx = new WfmTransmitFlowInfoCtxBuilder(flowTypePk, taskPk).builderTransmitFlowInfoCtx();
		IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flwInfoCtx);
		IFlowResponse response = BizProcessServer.createFlowResponse();
		BizProcessServer.exe(request, response);
		AppInteractionUtil.showMessageDialog("执行成功");
	}
	public WfmFormInfoCtx builderWfmFormInfoCtx() {
		return WfmTaskUtil.getWfmFormInfoCtx();
	}
	public WfmFlowInfoCtx builderWfmFlowInfoCtx() {
		String operator = WfmTaskUtil.getOperator();
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator) || WfmConstants.WfmOperator_UnAgree.equalsIgnoreCase(operator)) {
			String taskPk = WfmTaskUtil.getTaskPk();
			if (taskPk == null || taskPk.length() == 0) {
				flowInfoCtx = new WfmCommitFlowInfoCtxBuilder(WfmTaskUtil.getFlowTypePk(), WfmTaskUtil.getTaskPk()).builderCommitFlwInfoCtx();
			} else {
				flowInfoCtx = new WfmNextFlowInfoCtxBuilder(WfmTaskUtil.getFlowTypePk(), WfmTaskUtil.getTaskPk()).builderNextFlwInfoCtx();
			}
		}
		if (WfmConstants.WfmOperator_Reject.equalsIgnoreCase(operator)) {
			flowInfoCtx = new WfmRejectFlowInfoCtxBuilder(WfmTaskUtil.getFlowTypePk(), WfmTaskUtil.getTaskPk()).builderRejectFlwInfoCtx();
		}
		return flowInfoCtx;
	}
}
