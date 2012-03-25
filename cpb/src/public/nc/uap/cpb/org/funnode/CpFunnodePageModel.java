package nc.uap.cpb.org.funnode;

import nc.uap.lfw.core.model.PageModel;

public class CpFunnodePageModel extends PageModel{

	public static String APPTYPE = "type";
	public CpFunnodePageModel() {
		super();
	}
	
	protected void initPageMetaStruct() {
		super.initPageMetaStruct();
		String type = getWebContext().getParameter(APPTYPE);
		getWebContext().getWebSession().setAttribute(APPTYPE, type);
	}
}
