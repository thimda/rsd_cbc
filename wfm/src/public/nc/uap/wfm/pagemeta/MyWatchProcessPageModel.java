package nc.uap.wfm.pagemeta;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.wfm.constant.WfmConstants;

public class MyWatchProcessPageModel extends PageModel {

	@Override
	protected void initPageMetaStruct() {
		// TODO Auto-generated method stub
		getClientSession().setAttribute(WfmConstants.ProDefPk, LfwRuntimeEnvironment.getWebContext().getParameter(WfmConstants.ProDefPk), true);
		
	}

}
