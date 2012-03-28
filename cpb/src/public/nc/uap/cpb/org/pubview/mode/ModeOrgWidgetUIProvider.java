package nc.uap.cpb.org.pubview.mode;

import nc.uap.lfw.core.model.IWidgetUIProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UITextField;

public class ModeOrgWidgetUIProvider implements IWidgetUIProvider {

	@Override
	public UIMeta getDefaultUIMeta(LfwWidget widget) {
		String widgetId = widget.getId();
		UIMeta um = new UIMeta();
		um.setId(widgetId);
		um.setFlowmode(Boolean.TRUE);
		
		UITextField text = new UITextField();
		text.setId("refcomp");
		text.setWidth("140");
		text.setWidgetId(widgetId);
		um.setElement(text);
		
		return um;
	}

}
