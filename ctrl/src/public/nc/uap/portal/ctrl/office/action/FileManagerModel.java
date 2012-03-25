package nc.uap.portal.ctrl.office.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.model.PageModel;

public class FileManagerModel extends PageModel {
	/**
	 * ≥ı ºªØ“≥√Ê
	 */
	@Override
	protected void initPageMetaStruct() {

		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		Enumeration enu = request.getParameterNames();
		WebSession ses = getWebContext().getWebSession();
		if(enu != null){
			while(enu.hasMoreElements()){
				String en = (String) enu.nextElement();
				String val = request.getParameter(en);
				ses.setAttribute(en, val);
			}
		}
		super.initPageMetaStruct();
	}
}
