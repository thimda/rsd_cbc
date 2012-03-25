package nc.uap.wfm.builder;
import java.util.List;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.context.RejectTaskInfoCtx;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.runtime.RejectHumActInfoUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmRejectFlowInfoCtxBuilder {
	protected String flowTypePk;
	protected String taskPk;
	public WfmRejectFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	/**
	 * 构造驳回信息
	 * 
	 * @return
	 */
	public RejectTaskInfoCtx builderRejectFlwInfoCtx() {
		RejectTaskInfoCtx ctx = new RejectTaskInfoCtx();
		new WfmFlowInfoCtxBuilder(flowTypePk, taskPk).initFlowInfoCtx(ctx);
		Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
		RejectHumActInfoUtil rejectHumActInfo = new RejectHumActInfoUtil();
		List<HumActInfoPageCtx> rejectInfos = rejectHumActInfo.getRejectHumActInfo(task);
		if (rejectInfos == null || rejectInfos.size() == 0) {
			return ctx; 
		}
		if (rejectInfos.size() == 1 && !rejectInfos.get(0).isAssign()) {
			this.builderNoRejectAssignFlowInfoCtx(ctx, rejectInfos.get(0));
		} else {
			this.builderRejectAssignFlowInfoCtx(ctx);
		}
		return ctx;
	}
	/**
	 * 构造驳回的指派信息
	 * 
	 * @param ctx
	 */
	protected void builderRejectAssignFlowInfoCtx(RejectTaskInfoCtx ctx) {
		LfwWidget view_assign = WfmTaskUtil.getAssignView();
		List<HumActInfoEngCtx> rejectInfo = new WfmAssginCtxBuilder(flowTypePk, taskPk).getAssginCtx(view_assign.getViewModels().getDataset(WfmConstants.DsHUMACT));
		ctx.setRejectInfo(rejectInfo.get(0));
	}
	/**
	 * 构造驳回的不需要指派信息
	 * 
	 * @param ctx
	 */
	protected void builderNoRejectAssignFlowInfoCtx(RejectTaskInfoCtx ctx, HumActInfoPageCtx rejectInfo) {
		HumActInfoEngCtx humActInfoEngCtx = new HumActInfoEngCtx();
		humActInfoEngCtx.setPortId(rejectInfo.getPortId());
		humActInfoEngCtx.setUserPks(rejectInfo.getUserPks().split(","));
		ctx.setRejectInfo(humActInfoEngCtx);
	}
}
