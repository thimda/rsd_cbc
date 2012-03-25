package nc.uap.wfm.deliver;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.wfm.builder.WfmDeliverFlowInfoCtxBuilder;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.server.BizProcessServer;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
/**
 * @author chouhl
 */
public class DeliverCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void btnok_onclick(MouseEvent<ButtonComp> mouseEvent) {
		String taskPk = (String) AppUtil.getAppAttr(WfmConstants.TaskPk);
		String flowTypePk = (String) AppUtil.getAppAttr(WfmConstants.FlwTypePk);
		WfmFlowInfoCtx flowInfoCtx = new WfmDeliverFlowInfoCtxBuilder(flowTypePk, taskPk).builderDeliverFlowInfoCtx();
		IFlowRequest flowReuqest = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flowInfoCtx);
		IFlowResponse flowResponse = BizProcessServer.createFlowResponse();
		BizProcessServer.exe(flowReuqest, flowResponse);
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeView("pubview_deliver");
	}
}
