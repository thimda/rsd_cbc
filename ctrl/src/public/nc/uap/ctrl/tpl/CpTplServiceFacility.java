package nc.uap.ctrl.tpl;

import nc.bs.framework.common.NCLocator;
import nc.uap.ctrl.tpl.systemplate.ICpSystemplateQryService;
import nc.uap.ctrl.tpl.systemplate.ICpSystemplateService;

public class CpTplServiceFacility {
	public static ICpSystemplateQryService getSystemplateQryService(){
		return NCLocator.getInstance().lookup(ICpSystemplateQryService.class);
	}
	
	public static ICpSystemplateService getSystemplateService() {
		return NCLocator.getInstance().lookup(ICpSystemplateService.class);
	}
	
//	public ICpQueryTemplateQryService getQueryTemplateQryService() {
//		return NCLocator.getInstance().lookup(ICpQueryTemplateQryService.class);
//	}
}
