package nc.uap.ctrl.tpl.qry;

import nc.uap.lfw.core.model.IWidgetUIProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UIButton;
import nc.uap.lfw.jsp.uimeta.UIComponent;
import nc.uap.lfw.jsp.uimeta.UIFlowhLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowhPanel;
import nc.uap.lfw.jsp.uimeta.UIFlowvLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;
import nc.uap.lfw.jsp.uimeta.UIFormComp;
import nc.uap.lfw.jsp.uimeta.UILabelComp;
import nc.uap.lfw.jsp.uimeta.UILinkComp;
import nc.uap.lfw.jsp.uimeta.UIMeta;

public class SimpleQueryWidgetUIProvider implements IWidgetUIProvider {

	@Override
	public UIMeta getDefaultUIMeta(LfwWidget widget) {
		String widgetId = widget.getId();
		UIMeta um = new UIMeta();
		um.setId(widgetId + "_um");
		um.setFlowmode(Boolean.TRUE);
		
		if(widget.getExtendAttribute("mock") != null){
			mockNullWidget(widgetId, um);
		}
		else{
			UIFlowvLayout layout = new UIFlowvLayout();
			layout.setId("flowvlayout");
			layout.setWidgetId(widgetId);
			um.setElement(layout);

			
			UIFlowvPanel p2 = new UIFlowvPanel();
			p2.setId("p2");
			layout.addPanel(p2);
			
			
			UIFormComp form = new UIFormComp();
			form.setId("mainform");
			form.setLabelPosition("top");
			form.setWidgetId(widgetId);
			p2.setElement(form);
			
			UIFlowvPanel p3 = new UIFlowvPanel();
			p3.setId("p3");
			p3.setHeight("40");
			p3.setTopPadding("14");
			layout.addPanel(p3);
			
			UIFlowhLayout hLayout = new UIFlowhLayout();
			hLayout.setId("flowhlayout");
			p3.setElement(hLayout);
			
			
			UIButton bt = new UIButton();
			bt.setId("queryBt");
			bt.setAlign(UIComponent.ALIGN_LEFT);
			bt.setWidth("70");//bt.setClassName("dddddd");
			UIFlowhPanel hP2 = new UIFlowhPanel();
			hP2.setId("hp2");
			hP2.setWidth("70");
			hP2.setElement(bt);
			hLayout.addPanel(hP2);
			
			UILinkComp clean = new UILinkComp();
			clean.setId("advlink");
			clean.setAlign(UIComponent.ALIGN_RIGHT);
			clean.setTop("5");
			clean.setWidth("50");
			UIFlowhPanel hP3 = new UIFlowhPanel();
			hP3.setId("hp3");
			hP3.setElement(clean);
			hLayout.addPanel(hP3);
			
//			UIFlowhPanel hP1 = new UIFlowhPanel();
//			hP1.setId("hp1");
//			hLayout.addPanel(hP1);
			
			
		}
		um.adjustUI(widgetId);
		return um;
	}

	private void mockNullWidget(String widgetId, UIMeta um) {
		UIFlowvLayout layout = new UIFlowvLayout();
		layout.setId("flowvlayout");
		layout.setWidgetId(widgetId);
		um.setElement(layout);
		
//		UIFlowvPanel p1 = new UIFlowvPanel();
//		p1.setId("p1");
//		p1.setHeight("30");
//		layout.addPanel(p1);
		
		UIFlowvPanel p2 = new UIFlowvPanel();
		p2.setId("p2");
		layout.addPanel(p2);
		
		
		
		UILabelComp label = new UILabelComp();
		label.setId("label");
		label.setWidgetId(widgetId);
		p2.setElement(label);
		
		
//		UIFlowvPanel p3 = new UIFlowvPanel();
//		p3.setId("p3");
//		p3.setHeight("40");
//		layout.addPanel(p3);
	}

}
