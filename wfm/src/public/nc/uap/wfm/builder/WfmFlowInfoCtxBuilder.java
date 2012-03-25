package nc.uap.wfm.builder;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.StartedProInsInfoCtx;
import nc.uap.wfm.context.UnStartProInsInfoCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmFlowInfoCtxBuilder {
	private String flowTypePk = null;
	private String taskPk = null;
	public WfmFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	/**
	 * 构造流程基本信息
	 * 
	 * @param ctx
	 */
	public void initFlowInfoCtx(WfmFlowInfoCtx ctx) {
		Task task = null;
		ProDef proDef = null;
		if (taskPk == null || taskPk.length() == 0) {
			proDef = ProDefsContainer.getProDefByFlowTypePk(flowTypePk);
		} else {
			task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			proDef = task.getProDef();
		}
		if (ctx instanceof StartedProInsInfoCtx) {
			StartedProInsInfoCtx startedFlwInfoCtx = (StartedProInsInfoCtx) ctx;
			startedFlwInfoCtx.setTaskPk(taskPk);
		}
		if (ctx instanceof UnStartProInsInfoCtx) {
			UnStartProInsInfoCtx unstartFlwInfoCtx = (UnStartProInsInfoCtx) ctx;
			unstartFlwInfoCtx.setFlowTypePk(flowTypePk);
		}
		String opinion = WfmTaskUtil.getOpinion();
		if (opinion == null || opinion.length() == 0) {
			if (task != null && Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
				throw new LfwRuntimeException("意见为必填项");
			}
			opinion = "";
			HumAct humAct = null;
			if (task == null) {
				humAct = (HumAct) WfmTaskUtil.getStratPort(proDef);
			} else {
				humAct = task.getHumActIns().getHumAct();
			}
			if (humAct.isNotNeedOpinion()) {
				throw new LfwRuntimeException("意见为必填项");
			}
		}
		ctx.setOpinion(opinion);
		String cntUserPk = CpbUtil.getCntUserPk();
		ctx.setCntUserPk(cntUserPk);
	}
}
