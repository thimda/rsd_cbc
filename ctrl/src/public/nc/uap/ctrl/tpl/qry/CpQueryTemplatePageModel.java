package nc.uap.ctrl.tpl.qry;

import nc.uap.lfw.core.event.AppRequestProcessor;
import nc.uap.lfw.core.model.PageModel;
import nc.uap.lfw.core.page.IUIMeta;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UIWidget;

public class CpQueryTemplatePageModel extends PageModel {

	private static final String MAIN_WIDGET = "main";

	@Override
	protected IUIMeta createUIMeta(PageMeta pm) {
		UIMeta pageUm = new UIMeta();
		pageUm.setLfwIncludejs("cpqry/QueryTemplate.js,cpqry/QueryTemplatePanel.js,cpqry/QueryTemplateProcessor.js");
		pageUm.setId("pageUm");
		
		LfwWidget widget = pm.getWidget(MAIN_WIDGET);
		AdvancedQueryWidgetUIProvider uip = new AdvancedQueryWidgetUIProvider();
		UIMeta um = uip.getDefaultUIMeta(widget);
		
		UIWidget uiWidget = new UIWidget();
		uiWidget.setId("main");
		uiWidget.addPanel(um);
		pageUm.setElement(uiWidget);
		return pageUm;
	}

	@Override
	protected PageMeta createPageMeta() {
		PageMeta pm = new PageMeta();
		pm.setProcessorClazz(AppRequestProcessor.class.getName());
		WidgetConfig wconf = new WidgetConfig();
		wconf.setId(MAIN_WIDGET);
		pm.addWidgetConf(wconf);
		LfwWidget widget = new LfwWidget();
		widget.setId(MAIN_WIDGET);
		AdvancedQueryWidgetProvider p = new AdvancedQueryWidgetProvider();
		widget = p.buildWidget(pm, widget, null, MAIN_WIDGET);
		pm.addWidget(widget);
		return pm;
	}
	
}
