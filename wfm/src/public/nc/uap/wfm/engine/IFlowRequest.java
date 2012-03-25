package nc.uap.wfm.engine;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public interface IFlowRequest extends IDynamicAttribute {
	WfmFormInfoCtx getFormInfoCtx();
	WfmFlowInfoCtx getFlowInfoCtx();
	void setFormInfoCtx(WfmFormInfoCtx frmInfoCtx);
	void setFlowInfoCtx(WfmFlowInfoCtx flwInfoCtx);
}
