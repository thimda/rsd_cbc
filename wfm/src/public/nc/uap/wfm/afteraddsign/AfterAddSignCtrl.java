package nc.uap.wfm.afteraddsign;
import java.util.Map;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.comp.TextAreaComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.AppUtil;
/**
 * @author chouhl
 */
public class AfterAddSignCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void pluginplugin_afteraddsign(Map<Object, Object> keys) {}
	public void btn_ok_onclick(MouseEvent<ButtonComp> mouseEvent) {
		LfwWidget widget = AppUtil.getWidget(WfmConstants.WfmPubView_AfterAddSign);
		ReferenceComp textComp = (ReferenceComp) widget.getViewComponents().getComponent("text_addsignuser");
		String userValue = textComp.getValue();
		AppUtil.addAppAttr(WfmConstants.WfmAfterAddSignUserValue, userValue);
		String userName = textComp.getShowValue();
		AppUtil.addAppAttr(WfmConstants.WfmAfterAddSignUserNames, userName);
		TextAreaComp opinionComp = (TextAreaComp) widget.getViewComponents().getComponent("text_opinion");
		String afterAddSignOpinion = opinionComp.getValue();
		AppUtil.addAppAttr(WfmConstants.WfmAfterAddSignOpinion, afterAddSignOpinion);
		new UifPlugoutCmd(WfmConstants.WfmPubView_AfterAddSign, "plugout_afteraddsign").execute();
		AppUtil.getCntWindowCtx().closeView(WfmConstants.WfmPubView_AfterAddSign);
	}
}
