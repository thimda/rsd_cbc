package nc.uap.wfm.builder;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.TransmitTaskInfoCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.utils.AppUtil;
public class WfmTransmitFlowInfoCtxBuilder extends WfmFlowInfoCtxBuilder {
	public WfmTransmitFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super(flowTypePk, taskPk);
	}
	/**
	 * 构造下一步信息
	 * 
	 * @return
	 */
	public TransmitTaskInfoCtx builderTransmitFlowInfoCtx() {
		TransmitTaskInfoCtx ctx = new TransmitTaskInfoCtx();
		this.builder(ctx);
		ctx.setTransimgUserPk((String) AppUtil.getAppAttr(WfmConstants.WfmAppAttr_TransmitUserPk));
		return ctx;
	}
	@Override public WfmFlowInfoCtx builder() {
		return this.builderTransmitFlowInfoCtx();
	}
}
