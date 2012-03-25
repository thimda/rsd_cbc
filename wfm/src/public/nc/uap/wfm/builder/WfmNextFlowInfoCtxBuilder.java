package nc.uap.wfm.builder;
import java.util.List;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.HumActInfoEngCtx;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.context.NextTaskInfoCtx;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmNextFlowInfoCtxBuilder {
	private String flowTypePk = null;
	private String taskPk = null;
	public WfmNextFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	/**
	 * 构造下一步信息
	 * 
	 * @return
	 */
	public NextTaskInfoCtx builderNextFlwInfoCtx() {
		NextTaskInfoCtx ctx = new NextTaskInfoCtx();
		new WfmFlowInfoCtxBuilder(flowTypePk, taskPk).initFlowInfoCtx(ctx);
		List<HumActInfoPageCtx> nextInfos = new NextHumActInfoUtil().getNextHumActInfo(taskPk, WfmTaskUtil.getWfmFormInfoCtx());
		if (nextInfos == null || nextInfos.size() == 0) {
			return ctx;
		}
		if (nextInfos.size() == 1 && !nextInfos.get(0).isAssign()) {
			this.builderNextNoAssignFlowInfoCtx(ctx, nextInfos.get(0));
		} else {
			this.builderNextAssignFlowInfoCtx(ctx);
		}
		return ctx;
	}
	/**
	 * 构造下一步的不需要指派信息
	 * 
	 * @param ctx
	 */
	protected void builderNextNoAssignFlowInfoCtx(NextTaskInfoCtx ctx, HumActInfoPageCtx nextInfo) {
		HumActInfoEngCtx humActInfoEngCtx = new HumActInfoEngCtx();
		humActInfoEngCtx.setPortId(nextInfo.getPortId());
		Task task = WfmTaskUtil.getTaskFromSessionCache(taskPk);
		ProDef proDef = task.getProDef();
		IPort port = proDef.getPorts().get(nextInfo.getPortId());
		if (port instanceof HumAct) {
			String userPks = nextInfo.getUserPks();
			if (userPks == null || userPks.length() == 0) {
				throw new LfwRuntimeException("请提供下一步的活动参与者");
			}
			humActInfoEngCtx.setUserPks(nextInfo.getUserPks().split(","));
		}
		ctx.setNextInfo(new HumActInfoEngCtx[] { humActInfoEngCtx });
	}
	/**
	 * 构造下一步的指派信息
	 * 
	 * @param ctx
	 */
	protected void builderNextAssignFlowInfoCtx(NextTaskInfoCtx ctx) {
		LfwWidget view_assign = WfmTaskUtil.getAssignView();
		List<HumActInfoEngCtx> nextInfo = new WfmAssginCtxBuilder(flowTypePk, taskPk).getAssginCtx(view_assign.getViewModels().getDataset(WfmConstants.DsHUMACT));
		ctx.setNextInfo(nextInfo.toArray(new HumActInfoEngCtx[0]));
	}
}
