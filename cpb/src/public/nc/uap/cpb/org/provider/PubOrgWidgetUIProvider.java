package nc.uap.cpb.org.provider;

import nc.uap.lfw.core.model.IWidgetUIProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UITextField;

public class PubOrgWidgetUIProvider implements IWidgetUIProvider {

	@Override
	public UIMeta getDefaultUIMeta(LfwWidget widget) {
		String widgetId = widget.getId();
		UIMeta um = new UIMeta();
		um.setId(widgetId);
		um.setFlowmode(Boolean.TRUE);
		
//		UIFlowhLayout hlayout = new UIFlowhLayout();
//		hlayout.setId("flowhlayout");
//		hlayout.setWidgetId(widgetId);
//		um.setElement(hlayout);
//		
//		UIFlowhPanel hp1 = new UIFlowhPanel();
//		hp1.setId("hp1");
//		hp1.setWidth("50");
//		hlayout.addPanel(hp1);
//		
//		UILabelComp label = new UILabelComp();
//		label.setId("label");
//		label.setWidgetId(widgetId);
//		hp1.setElement(label);
		
//		UIFlowhPanel hp2 = new UIFlowhPanel();
//		hp2.setId("hp2");
//		hlayout.addPanel(hp2);
		
		UITextField text = new UITextField();
		text.setId("refcomp");
		text.setWidth("140");
		text.setWidgetId(widgetId);
		um.setElement(text);
		
		return um;
	}

}
