package nc.uap.wfm.flowimg;
import nc.uap.lfw.core.comp.IFrameComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.url.WfmFlowImgUrlUtil;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
/**
 * @author chouhl
 */
public class FlowImgCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void show_beforeShow(DialogEvent dialogEvent) {
		LfwWidget widget = AppUtil.getWidget("pubview_flowimg");
		IFrameComp frameComp = (IFrameComp) widget.getViewComponents().getComponent("iframe_flowimg");
		frameComp.setSrc(this.getProcessImageUrl());
	}
	protected String getProcessImageUrl() {
		String flowTypePk = (String) AppUtil.getAppAttr(WfmConstants.FlwTypePk);
		String taskPk = (String) AppUtil.getAppAttr(WfmConstants.TaskPk);
		if (taskPk == null) {
			return WfmFlowImgUrlUtil.getProcessImageUrlByFrmDefPk(flowTypePk);
		} else {
			Task task = WfmTaskUtil.getTaskFromSessionCache(taskPk);
			return WfmFlowImgUrlUtil.getProcessImageUrlByTask(task);
		}
	}
}
