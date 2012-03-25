package nc.uap.wfm.utils;
import java.io.Serializable;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ApplicationContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.Application;
public class AppUtil {
	public static Serializable getAppAttr(String key) {
		Serializable obj = (Serializable) AppUtil.getCntAppCtx().getAppAttribute(key);
		return (Serializable) obj;
	}
	public static void addAppAttr(String key, Serializable value) {
		AppUtil.getCntAppCtx().addAppAttribute(key, value);
	}
	public static ApplicationContext getCntAppCtx() {
		return AppLifeCycleContext.current().getApplicationContext();
	}
	public static Application getCntApplication() {
		return AppUtil.getCntAppCtx().getApplication();
	}
	public static WindowContext getCntWindowCtx() {
		return AppUtil.getCntAppCtx().getCurrentWindowContext();
	}
	public static PageMeta getCntWindow() {
		return AppUtil.getCntWindowCtx().getWindow();
	}
	public static ViewContext getCntViewCtx() {
		return AppUtil.getCntWindowCtx().getCurrentViewContext();
	}
	public static LfwWidget getCntView() {
		return AppUtil.getCntViewCtx().getView();
	}
	public static LfwWidget getWidget(String name) {
		return  AppUtil.getCntWindowCtx().getWindow().getWidget(name);
	}
}
