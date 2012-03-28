package nc.uap.wfm.pagemodel;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.AppUtil;
public class FlwDataDispPageModel extends PageModel {
	private String taskPk = null;
	protected void initPageMetaStruct() {
		this.initPara();
		this.doAfter();
	}
	protected void doAfter() {}
	protected void initPara() {
		taskPk = LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.WfmUrlConst_TaskPk);
		AppUtil.addAppAttr(WfmConstants.WfmAppAttr_TaskPk, taskPk);
	}
}
