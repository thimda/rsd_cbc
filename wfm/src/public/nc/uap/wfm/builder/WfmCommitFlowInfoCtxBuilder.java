package nc.uap.wfm.builder;
import java.util.List;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.CommitInfoCtx;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmCommitFlowInfoCtxBuilder {
	protected String flowTypePk = null;
	protected String taskPk = null;
	public WfmCommitFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	/**
	 * �����ύ��������Ϣ
	 * 
	 * @return
	 */
	public CommitInfoCtx builderCommitFlwInfoCtx() {
		CommitInfoCtx ctx = new CommitInfoCtx();
		new WfmFlowInfoCtxBuilder(flowTypePk, taskPk).initFlowInfoCtx(ctx);
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
	 * �����ύ�Ĳ���Ҫָ����Ϣ
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
	 * �����ύ��ָ����Ϣ
	 * 
	 * @param ctx
	 */
	protected void builderCommitAssignFlowInfoCtx(CommitInfoCtx ctx) {
		List<HumActInfoEngCtx> nextInfo = new WfmAssginCtxBuilder(flowTypePk, taskPk).getAssginCtx(WfmTaskUtil.getAssignView().getViewModels().getDataset(WfmConstants.DsHUMACT));
		ctx.setNextInfo(nextInfo.toArray(new HumActInfoEngCtx[0]));
	}
}
