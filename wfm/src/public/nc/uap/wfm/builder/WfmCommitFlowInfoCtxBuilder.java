package nc.uap.wfm.builder;
import java.util.List;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.CommitInfoCtx;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmCommitFlowInfoCtxBuilder extends WfmFlowInfoCtxBuilder {
	public WfmCommitFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super(flowTypePk, taskPk);
	}
	@Override public WfmFlowInfoCtx builder() {
		return this.builderCommitFlwInfoCtx();
	}
	/**
	 * 构造提交的流程信息
	 * 
	 * @return
	 */
	protected CommitInfoCtx builderCommitFlwInfoCtx() {
		CommitInfoCtx ctx = new CommitInfoCtx();
		this.builder(ctx);
		List<HumActInfoPageCtx> nextInfos = null;
		nextInfos = new NextHumActInfoUtil().getStartNextHumActInfo(flowTypePk, WfmTaskUtil.getWfmFormInfoCtx());
		if (nextInfos == null || nextInfos.size() == 0) {
			return ctx;
		}
		if (nextInfos.size() == 1 && !nextInfos.get(0).isAssign()) {
			this.builderCommitNoAssignFlowInfoCtx(ctx, nextInfos.get(0));
		} else {
			this.builderCommitAssignFlowInfoCtx(ctx);
		}
		return ctx;
	}
	/**
	 * 构造提交的不需要指派信息
	 * 
	 * @param ctx
	 */
	protected void builderCommitNoAssignFlowInfoCtx(CommitInfoCtx ctx, HumActInfoPageCtx nextInfo) {
		HumActInfoEngCtx humActInfoEngCtx = new HumActInfoEngCtx();
		humActInfoEngCtx.setPortId(nextInfo.getPortId());
		humActInfoEngCtx.setUserPks(nextInfo.getUserPks().split(","));
		ctx.setNextInfo(new HumActInfoEngCtx[] { humActInfoEngCtx });
	}
	/**
	 * 构造提交的指派信息
	 * 
	 * @param ctx
	 */
	protected void builderCommitAssignFlowInfoCtx(CommitInfoCtx ctx) {
		List<HumActInfoEngCtx> nextInfo = new WfmAssginFlowInfoCtxBuilder(flowTypePk, taskPk).getAssginCtx(WfmTaskUtil.getAssignView().getViewModels().getDataset(WfmConstants.WfmDataset_DsHUMACT));
		ctx.setNextInfo(nextInfo.toArray(new HumActInfoEngCtx[0]));
	}
}
