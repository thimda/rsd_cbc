package nc.uap.ctrl.tpl.qry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTranslator;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeUtils;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.ncadapter.billtemplate.ref.LfwNCRefUtil;
import nc.ui.bd.ref.AbstractRefModel;

public class SimpleQueryController {
	public void loadSimpleQueryData(DataLoadEvent e){
		Dataset ds = e.getSource();
		ICpQryTemplateInnerQryService qryService = NCLocator.getInstance().lookup(ICpQryTemplateInnerQryService.class);
		CpQueryTemplateTotalVO totalVO = null;//qryService.getQueryTotalVO(whereSql);
		
		CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
		loader.loadData(totalVO);
		List<FilterMeta> defaultMeta = loader.getDefaultMetas();
		if(defaultMeta == null){
			
		}
		addDataToQueryCDs(ds, loader.getSortedFiltemetas(), loader.getFieldCodeFilterMap());
	}
	
	public void onQryBtOk(MouseEvent<ButtonComp> e){
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(SimpleQueryWidgetProvider.MAINDS);
		Row row = ds.getSelectedRow();
		Field[] fs = ds.getFieldSet().getFields();
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			String value = (String) row.getValue(ds.nameToIndex(f.getId()));
			if(value == null || value.equals(""))
				continue;
			if(!first){
				buf.append(" and ");
			}
			buf.append(f.getId())
			   .append(" like '%")
			   .append(value)
			   .append("%'");
			first = false;			
		}
		FromWhereSQL whereSql = new FromWhereSQLImpl(null, buf.toString());//new QueryTool().getFromWhere(null, null);
		Map paramMap = new HashMap();
		paramMap.put("whereSql", whereSql);
		
		CmdInvoker.invoke(new UifPlugoutCmd(viewCtx.getId(), "qryout", paramMap));
	}
	
	public void onCleanBtOk(MouseEvent<ButtonComp> e){
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(SimpleQueryWidgetProvider.MAINDS);
		ds.removeRow(0);
		ds.addRow(ds.getEmptyRow());
		ds.setRowSelectIndex(0);
	}
	
	private void addDataToQueryCDs(Dataset querycds, List<FilterMeta> sortedFiltemetas, Map<String, IFilter> fieldCodeFilterMap) {
		Iterator<FilterMeta> it = sortedFiltemetas.iterator();
		while (it.hasNext()) {
			FilterMeta meta = it.next();
			QuerySchemeUtils.setMetaToDs(meta, querycds, fieldCodeFilterMap);
			IFilter filter = fieldCodeFilterMap.get(meta.getFieldCode());
			if (!meta.isCondition() || meta.isDefault()) {
				Row row = querycds.getEmptyRow();
				String id = meta.getFieldCode().replaceAll("\\.", "_");
				row.setString(0, id);
				row.setString(1, meta.getFieldName());
				String operatorValue = null;
				if(filter != null)
					operatorValue = filter.getOperator().getOperatorCode();
				else{
					IOperator[] operators = meta.getOperators();
					operatorValue = operators[0].getOperatorCode();
					for(int i = 0; i < operators.length; i++) {
						if(operators[i].getOperatorCode().equals("=")){
							operatorValue = "=";
							break;
						}
					}
				}
				row.setString(2, operatorValue);
				String dynamicDefaultValue = null;//getDynamicDefaultValue(meta.getFieldCode());
				String defaultValue = dynamicDefaultValue == null ? meta.getDefaultValue() : dynamicDefaultValue;
				//存显示值
				row.setString(3, defaultValue);
				String editorType;
				StringBuffer extInfo = new StringBuffer();
				switch(meta.getDataType()){
					case IQueryConstants.STRING:
						editorType = EditorTypeConst.STRINGTEXT;
						break;
					case IQueryConstants.INTEGER:
						editorType = EditorTypeConst.INTEGERTEXT;
						break;
					case IQueryConstants.DECIMAL:
						editorType = EditorTypeConst.DECIMALTEXT;
						break;
					case IQueryConstants.DATE:
						editorType = EditorTypeConst.DATETEXT;
						break;
					case IQueryConstants.LITERALDATE:
						editorType = EditorTypeConst.DATETEXT;
						break;
					case IQueryConstants.BOOLEAN:
						editorType = EditorTypeConst.COMBODATA;
						break;
					case IQueryConstants.UFREF:
						editorType = EditorTypeConst.REFERENCE;
						String refId = meta.getFieldCode().replaceAll("\\.", "_") + "_refNode";
						extInfo.append(refId);
						String refCode = meta.getValueEditorDescription();
						AbstractRefModel model = LfwNCRefUtil.getRefModel(refCode);
						if(model != null){
							//LfwRefGenUtil refUtil = new String[]{model.getRefPkField(), model.getRefCodeField(), model.getRefNameField()};
							//new LfwRefGenUtil(model);
							//设置pk_group的值,为了把查询参照数据
							model.setPk_group(LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
							//取出参照三要素对应的字段名称(pk, code, name)
							String[] threeEle = new String[]{model.getPkFieldCode(), model.getRefCodeField(), model.getRefNameField()};
							model.setBlurFields(threeEle);
							model.setBlurValue(defaultValue);
							Vector dataV = model.matchBlurData(defaultValue);
							if(dataV != null && dataV.size() > 0){
								int index = model.getFieldIndex(threeEle[2]);
								String showValue = (String) ((Vector)dataV.get(0)).get(index);
								if(showValue != null)
									row.setString(3, showValue);
							}
							//row.setString(13, defaultValue);
						}
						//存真实值
						row.setString(13, defaultValue);
						break;
						
					case IQueryConstants.USERCOMBO:
						editorType = EditorTypeConst.COMBODATA;
						break;
					default:
						editorType = EditorTypeConst.STRINGTEXT;
				}
				
				row.setString(5, editorType);
				row.setString(6, "String");
				row.setString(7, extInfo.toString());
				//row.setString(10, extInfo.toString());
				row.setString(8, meta.getFieldCode());
				//存放真实ID
				row.setString(10, id);
				String fieldType = "3";
				String logicType = "0";
				if (!meta.isCondition())
					logicType = "1";
				if (meta.isRequired())
					fieldType = "0";
				else if (meta.isFixCondition())
					fieldType = "1";
				else if (meta.isDefault())
					fieldType = "2";
				row.setString(9, fieldType);
				row.setString(14, logicType);
				querycds.addRow(row);
			}
		}
		
	}
}
