package nc.uap.wfm.pagemeta;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.wfm.constant.WfmConstants;
/**
 * 我发起的流程pagemodel
 * @author zhangxya
 *
 */
public class MyStartProcessPageModel extends PageModel{
	protected void initPageMetaStruct() {
		getClientSession().setAttribute(WfmConstants.ProDefPk, LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk), true);
	}
}
