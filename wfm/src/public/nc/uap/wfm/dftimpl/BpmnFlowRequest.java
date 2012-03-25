package nc.uap.wfm.dftimpl;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class BpmnFlowRequest extends ExtendPropertySupport implements IFlowRequest {
	protected WfmFlowInfoCtx flowInfoCtx;
	protected WfmFormInfoCtx formInfoCtx;
	public WfmFlowInfoCtx getFlowInfoCtx() {
		return flowInfoCtx;
	}
	public WfmFormInfoCtx getFormInfoCtx() {
		return formInfoCtx;
	}
	public void setFlowInfoCtx(WfmFlowInfoCtx flowInfoCtx) {
		this.flowInfoCtx = flowInfoCtx;
	}
	public void setFormInfoCtx(WfmFormInfoCtx formInfoCtx) {
		this.formInfoCtx = formInfoCtx;
	}
}
