package nc.uap.wfm.builder;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.TransmitTaskInfoCtx;
import nc.uap.wfm.utils.AppUtil;
public class WfmTransmitFlowInfoCtxBuilder {
	private String flowTypePk = null;
	private String taskPk = null;
	public WfmTransmitFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	/**
	 * 构造下一步信息
	 * 
	 * @return
	 */
	public TransmitTaskInfoCtx builderTransmitFlowInfoCtx() {
		TransmitTaskInfoCtx ctx = new TransmitTaskInfoCtx();
		new WfmFlowInfoCtxBuilder(flowTypePk, taskPk).initFlowInfoCtx(ctx);
		ctx.setTransimgUserPk((String) AppUtil.getAppAttr(WfmConstants.TransmitUserPk));
		return ctx;
	}
}
