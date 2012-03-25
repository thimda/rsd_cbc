package nc.uap.portal.ctrl.office.data.sign;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;


public class InfPubListController{
	
	public void onInfTitleLink(ScriptEvent e) {
		String pk_signlog = AppLifeCycleContext.current().getParameter("pk_signlog");
		
		WindowContext winCtx = getCurAppContext().getWindowContext();
		ViewContext viewCtx =  winCtx.getViewContext("showLinkContent");
		winCtx.popView("showLinkContent", "600", "430", "œÍœ∏–≈œ¢");
		
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_signlog", pk_signlog);
	}

	private AppLifeCycleContext getCurAppContext() {
		return AppLifeCycleContext.current();
	}

	private Dataset getDataSet(String dsID) {
		return getCurAppContext().getViewContext().getView().getViewModels()
				.getDataset(dsID);
	}


}
