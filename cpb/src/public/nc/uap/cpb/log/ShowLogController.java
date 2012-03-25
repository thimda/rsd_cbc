package nc.uap.cpb.log;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.ScriptEvent;

public class ShowLogController {

	public void onDataLogLink(ScriptEvent e) {
		String pk_businesslog = AppLifeCycleContext.current().getParameter("pk_businesslog");
		
		WindowContext winCtx = getCurAppContext().getWindowContext();
		//ViewContext viewCtx =  winCtx.getViewContext("showLinkContent");
		winCtx.popView("showLinkContent", "600", "430", "详细信息");
		
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_businesslog", pk_businesslog);
	}
	public void onLoginLogLink(ScriptEvent e) {
		String pk_loginlog = AppLifeCycleContext.current().getParameter("pk_loginlog");
		
		WindowContext winCtx = getCurAppContext().getWindowContext();
		//ViewContext viewCtx =  winCtx.getViewContext("showLinkContent");
		winCtx.popView("showLinkContent", "600", "430", "详细信息");
		
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_loginlog", pk_loginlog);
	}
	public void onOperatorLogLink(ScriptEvent e) {
		String pk_funcoperlog = AppLifeCycleContext.current().getParameter("pk_funcoperlog");
		
		WindowContext winCtx = getCurAppContext().getWindowContext();
		//ViewContext viewCtx =  winCtx.getViewContext("showLinkContent");
		winCtx.popView("showLinkContent", "600", "430", "详细信息");
		
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_funcoperlog", pk_funcoperlog);
	}
	private AppLifeCycleContext getCurAppContext() {
		return AppLifeCycleContext.current();
	}

	private Dataset getDataSet(String dsID) {
		return getCurAppContext().getViewContext().getView().getViewModels()
				.getDataset(dsID);
	}
}
