package nc.uap.wfm.builder;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.AddSignUserInfoCtx;
import nc.uap.wfm.context.BeforeAddSignTaskInfoCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmBeforeFlowInfoCtxBuilder extends WfmFlowInfoCtxBuilder {
	public WfmBeforeFlowInfoCtxBuilder(String flowTypePk, String taskPk) {
		super(flowTypePk, taskPk);
	}
	public WfmFlowInfoCtx builder() {
		BeforeAddSignTaskInfoCtx flowInfoCtx = new BeforeAddSignTaskInfoCtx();
		flowInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
		LfwWidget widget = AppUtil.getWidget(WfmConstants.WfmPubView_ExeTask);
		TextComp textComp = (TextComp) widget.getViewComponents().getComponent(WfmConstants.WfmComp_Logic);
		flowInfoCtx.setLogic(textComp.getValue());
		flowInfoCtx.setTaskPk(WfmTaskUtil.getTaskPk());
		ReferenceComp refComp = (ReferenceComp) widget.getViewComponents().getComponent(WfmConstants.WfmComp_BeforeAddSignUser);
		String userPks = refComp.getValue();
		String[] userPkArry = userPks.split(",");
		AddSignUserInfoCtx[] userInfos = new AddSignUserInfoCtx[userPkArry.length];
		for (int i = 0; i < userPkArry.length; i++) {
			AddSignUserInfoCtx userInfo = new AddSignUserInfoCtx();
			userInfo.setUserPk(userPkArry[i]);
			userInfos[i] = userInfo;
		}
		flowInfoCtx.setAddSingUsers(userInfos);
		return flowInfoCtx;
	}
}
