package nc.uap.wfm.builder;
import nc.uap.lfw.core.comp.TextAreaComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.context.DeliverInfoCtx;
import nc.uap.wfm.context.DeliverUserInfoCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.utils.AppUtil;
public class WfmDeliverFlowInfoCtxBuilder {
	private String flowTypePk;
	private String taskPk;
	public WfmDeliverFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super();
		this.flowTypePk = flowTypePk;
		this.taskPk = taskPk;
	}
	public WfmFlowInfoCtx builderDeliverFlowInfoCtx() {
		LfwWidget widgetMain = AppUtil.getWidget("pubview_deliver");
		TextComp textComp = (TextComp) widgetMain.getViewComponents().getComponent("text_deliveruser");
		String userPks = textComp.getValue();
		if (userPks == null || userPks.length() == 0) {
			throw new LfwRuntimeException("请选择要转发的用户");
		}
		String[] userPkArray = userPks.split(",");
		DeliverUserInfoCtx[] userInfos = new DeliverUserInfoCtx[userPkArray.length];
		DeliverUserInfoCtx userInfo = null;
		for (int i = 0; i < userPkArray.length; i++) {
			userInfo = new DeliverUserInfoCtx();
			userInfo.setUserPk(userPkArray[i]);
			userInfos[i] = userInfo;
		}
		DeliverInfoCtx ctx = new DeliverInfoCtx();
		ctx.setDeliverUserInfo(userInfos);
		new WfmFlowInfoCtxBuilder(flowTypePk, taskPk).initFlowInfoCtx(ctx);
		TextAreaComp textAreaComp = (TextAreaComp) widgetMain.getViewComponents().getComponent("text_opinion");
		String opinion = textAreaComp.getValue();
		ctx.setOpinion(opinion);
		return ctx;
	}
}
