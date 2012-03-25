package nc.uap.ctrl.tpl.qry;

import java.util.Map;

import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.model.IWidgetContentProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

public class QueryPlanWidgetProvider implements IWidgetContentProvider {

	@Override
	public LfwWidget buildWidget(PageMeta pm, LfwWidget conf,
			Map<String, Object> paramMap, String currWidgetId) {
		LfwWidget widget = new LfwWidget();
		widget.setId(currWidgetId);
		
		LabelComp label = new LabelComp();
		label.setId("label");
		label.setText("没有配置查询方案");
		widget.getViewComponents().addComponent(label);
		
		return widget;
	}

}
