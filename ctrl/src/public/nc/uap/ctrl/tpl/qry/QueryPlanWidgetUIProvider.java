package nc.uap.ctrl.tpl.qry;

import nc.uap.lfw.core.model.IWidgetUIProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UILabelComp;
import nc.uap.lfw.jsp.uimeta.UIMeta;

public class QueryPlanWidgetUIProvider implements IWidgetUIProvider {
	@Override
	public UIMeta getDefaultUIMeta(LfwWidget widget) {
		String widgetId = widget.getId();
		UIMeta um = new UIMeta();
		um.setId(widgetId + "_um");
		um.setFlowmode(Boolean.TRUE);
		
//		UIFlowvLayout layout = new UIFlowvLayout();
//		layout.setId("flowvlayout");
//		layout.setWidgetId(widgetId);
//		um.addElement(layout);
		
//		UIFlowvPanel p1 = new UIFlowvPanel();
//		p1.setId("p1");
//		p1.setHeight("30");
//		layout.addPanel(p1);
		
//		UIFlowvPanel p2 = new UIFlowvPanel();
//		p2.setId("p2");
//		layout.addPanel(p2);
		
		
		
		UILabelComp label = new UILabelComp();
		label.setId("label");
		label.setWidgetId(widgetId);
		um.setElement(label);
		
		
		
//		UIFlowvPanel p3 = new UIFlowvPanel();
//		p3.setId("p3");
//		p3.setHeight("40");
//		layout.addPanel(p3);
		
		um.adjustUI(widgetId);
		return um;
	}

}
