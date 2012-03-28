package nc.uap.ctrl.tpl.qry;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTranslator;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeUtils;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.lfw.app.plugin.AppControlPlugin;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.comp.LinkComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.MouseListener;
import nc.uap.lfw.core.model.IWidgetContentProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PlugoutDesc;

public class SimpleQueryWidgetProvider implements IWidgetContentProvider {
	public static final String MAINDS = "mainds";
	public static final String MAINFORM = "mainform";

	@Override
	public LfwWidget buildWidget(PageMeta pm, LfwWidget conf,
			Map<String, Object> paramMap, String currWidgetId) {
		LfwWidget widget = new LfwWidget();
		widget.setId(currWidgetId);
		widget.setControllerClazz(SimpleQueryController.class.getName());
		String queryNode = null;

		PlugoutDesc plugoutDesc = new PlugoutDesc();
		plugoutDesc.setId("qryout");
		widget.addPlugoutDescs(plugoutDesc);
		
		boolean designMode = LfwRuntimeEnvironment.getMode().equals(WebConstant.MODE_DESIGN) || LfwRuntimeEnvironment.getLfwSessionBean() == null;
		if(designMode){
			mockNullWidget(widget);
			return widget;
		}
		
		if (pm != null) {
			if (conf.getExtendAttribute(PageMeta.$QUERYTEMPLATE) != null)
				queryNode = (String) conf.getExtendAttribute(
						PageMeta.$QUERYTEMPLATE).getValue();
			if (queryNode == null) {
				if (pm.getExtendAttribute(PageMeta.$QUERYTEMPLATE) != null) {
					queryNode = (String) pm.getExtendAttribute(
							PageMeta.$QUERYTEMPLATE).getValue();
				}
			}
			if((queryNode == null || "".equals(queryNode) ) && AppLifeCycleContext.current() != null){
				queryNode = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(AppControlPlugin.NODECODE);
			}
		}
		CpQueryTemplateTotalVO totalVO = getQueryTotalVO(queryNode);
		if (totalVO == null) {
			mockNullWidget(widget);
		} 
		else {
			CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
			loader.loadData(totalVO);
			List<FilterMeta> defaultMetas = loader.getDefaultMetas();
			if (defaultMetas == null || defaultMetas.size() == 0) {
				mockNullWidget(widget);
			} 
			else {
				Dataset ds = new Dataset();
				ds.setId(MAINDS);
				widget.getViewModels().addDataset(ds);
				FormComp form = new FormComp();
				form.setId(MAINFORM);
				form.setDataset(MAINDS);
				widget.getViewComponents().addComponent(form);
				Iterator<FilterMeta> it = defaultMetas.iterator();
				int i = 0;
				while (it.hasNext() && (i++ < 4)) {
					FilterMeta meta = it.next();
					Field f = new Field();
					f.setId(meta.getFieldCode());
					f.setText(meta.getFieldCode());
					ds.getFieldSet().addField(f);
					FormElement fme = QuerySchemeUtils.setMetaToFormEle(meta, widget);
					if (fme == null)
						continue;
					form.addElement(fme);
				}
				ds.setCurrentKey(Dataset.MASTER_KEY);
				ds.setLazyLoad(true);
				ds.addRow(ds.getEmptyRow());
				ds.setRowSelectIndex(0);
				ds.setEnabled(true);

				ButtonComp qryBt = new ButtonComp();
				qryBt.setId("queryBt");
				qryBt.setText("查询");
				widget.getViewComponents().addComponent(qryBt);
				EventConf okClickConf = new EventConf();
				okClickConf.setMethodName("onQryBtOk");
				okClickConf.setName("onclick");
				okClickConf.setJsEventClaszz(MouseListener.class.getName());
				qryBt.addEventConf(okClickConf);
				
				LinkComp advLink = new LinkComp();
				advLink.setId("advlink");
				advLink.setI18nName("高级查询");
				widget.getViewComponents().addComponent(advLink);
				// EventConf cleanClickConf = new EventConf();
				// cleanClickConf.setMethodName("onCleanBtOk");
				// cleanClickConf.setName("onclick");
				// cleanClickConf.setJsEventClaszz(MouseListener.class.getName());
				// cleanBt.addEventConf(cleanClickConf);
			}
		}
		return widget;
	}


	private CpQueryTemplateTotalVO getQueryTotalVO(String queryNode) {
		CpQueryTemplateTranslator t = new CpQueryTemplateTranslator();
		return t.getQueryTotalVO(null, queryNode);
	}
	
	private CpQueryTemplateTotalVO getQueryTotalVOByPk(String pk_template) {
		CpQueryTemplateTranslator t = new CpQueryTemplateTranslator();
		return t.getQueryTotalVOByPk(pk_template);
	}


	private void mockNullWidget(LfwWidget widget) {
		widget.setExtendAttribute("mock", "1");
		LabelComp label = new LabelComp();
		label.setId("label");
		label.setText("没有配置查询项");
		widget.getViewComponents().addComponent(label);
	}
}
