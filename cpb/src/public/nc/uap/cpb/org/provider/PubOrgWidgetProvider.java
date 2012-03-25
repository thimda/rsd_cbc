package nc.uap.cpb.org.provider;

import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.TextListener;
import nc.uap.lfw.core.model.IWidgetContentProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.core.page.PlugoutDescItem;
import nc.uap.lfw.core.refnode.NCRefNode;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.util.LfwRefUtil;

public class PubOrgWidgetProvider implements IWidgetContentProvider {
	public static String ORG_REF_CODE_KEY = "org_ref_code";
	@Override
	public LfwWidget buildWidget(PageMeta pm, LfwWidget conf,
			Map<String, Object> paramMapString, String currWidgetId) {
		
		String refCode = (String) conf.getExtendAttributeValue(ORG_REF_CODE_KEY);
		if(refCode == null || refCode.equals(""))
			refCode = "��֯������ȫ�֣�(����)";
		
		LfwWidget widget = new LfwWidget();
		
		widget.setControllerClazz(PubOrgController.class.getName());
		
		widget.setId(currWidgetId);
		
//		LabelComp label = new LabelComp();
//		label.setId("label");
//		label.setText("��֯");
//		widget.getViewComponents().addComponent(label);
		
		NCRefNode rfn = new NCRefNode();
		rfn.setId("nc_org_refnode");
		rfn.setRefcode(refCode);
		widget.getViewModels().addRefNode(rfn);
		
		// ��ȡ��ʾֵ����ʵֵ
		ILfwRefModel rm = LfwRefUtil.getRefModel(rfn);
		String pkField = rm.getRefPkField();
		String nameField = rm.getRefNameField();
		rfn.setReadFields(pkField + "," + nameField);

		ReferenceComp refcomp = new ReferenceComp();
		refcomp.setId("refcomp");
		refcomp.setRefcode(rfn.getId());
		widget.getViewComponents().addComponent(refcomp);
		
		EventConf event = new EventConf();
		event.setName(TextListener.VALUE_CHANGED);
		event.setMethodName("textValueChange");
		event.setJsEventClaszz(TextListener.class.getName());
		refcomp.addEventConf(event);
		
		PlugoutDesc out = new PlugoutDesc();
		out.setId("orgout");
		widget.addPlugoutDescs(out);
		
		return widget;
	}
}
