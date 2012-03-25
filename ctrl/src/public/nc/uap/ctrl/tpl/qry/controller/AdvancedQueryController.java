package nc.uap.ctrl.tpl.qry.controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTranslator;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeUtils;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeVO;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Parameter;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.ncadapter.billtemplate.ref.LfwNCRefUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.vo.pub.BusinessException;

public class AdvancedQueryController {
	public void onQueryDsLoad(DataLoadEvent e){
		Dataset ds = e.getSource();
		CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
		CpQueryTemplateTotalVO totalVO = loader.getQueryTotalVO(null, "1019020201");
		loader.loadData(totalVO);
		fillQueryDs(ds, loader.getAllCandidateMetas());
	}
	
	public void onSavedConditionLoad(DataLoadEvent e){
		Dataset ds = e.getSource();
		try {
			String pk_template = null;
			String pk_org = null;
			String pk_user = null;
			QuerySchemeVO[] vos = NCLocator.getInstance().lookup(ICpQryTemplateInnerQryService.class).getQuerySchemeVOsBy(pk_org, pk_template, pk_user);
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
		} 
		catch (BusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
		}		
	}
	
	public void onQueryConditionLoad(DataLoadEvent e){
		Dataset ds = (Dataset) e.getSource();
		CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
		CpQueryTemplateTotalVO totalVO = loader.getQueryTotalVO(null, "1019020201");
		loader.loadData(totalVO);
		addDataToQueryCDs(ds, loader.getSortedFiltemetas(), loader.getFieldCodeFilterMap());
	}
	
	private void addDataToQueryCDs(Dataset querycds, List<FilterMeta> sortedFiltemetas, Map<String, IFilter> fieldCodeFilterMap) {
		Iterator<FilterMeta> it = sortedFiltemetas.iterator();
		while (it.hasNext()) {
			FilterMeta meta = it.next();
			QuerySchemeUtils.setMetaToDs(meta, querycds, fieldCodeFilterMap);
		}
	}
	
	public void onAdvConditionLoad(DataLoadEvent e){
		CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
		CpQueryTemplateTotalVO totalVO = loader.getQueryTotalVO(null, "1019020201");
		loader.loadData(totalVO);
		Dataset ds = e.getSource();
		addDataToQueryCTreeDs(ds, loader.getSortedFiltemetas(), loader.getFieldCodeFilterMap());
	}
	
	private void addDataToQueryCTreeDs(Dataset queryctreeds, List<FilterMeta> sortedFiltemetas, Map<String, IFilter> fieldCodeFilterMap) {
		queryctreeds.setCurrentKey("0");
		
		Iterator<FilterMeta> it = sortedFiltemetas.iterator();
		while (it.hasNext()) {
			FilterMeta meta = it.next();
			IFilter filter = fieldCodeFilterMap.get(meta.getFieldCode());
			if (!meta.isCondition() || meta.isDefault()) {
				Row row = queryctreeds.getEmptyRow();
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
				//String operatorValue = filter == null ? meta.getOperators()[0].getOperatorCode() : filter.getOperator().getOperatorCode();
//				if(operatorValue.equals("<"))
//					operatorValue = "&lt;";
//				else if(operatorValue.equals("<="))
//					operatorValue = "&lt;=";
				row.setString(2, operatorValue);
				String defaultValue = meta.getDefaultValue();
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
				Row newRow = (Row) row.clone();
				newRow.setString(4, DatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
				queryctreeds.addRow(newRow);
			}
		}
		/*高级树的根节点*/
		Row rootRow = queryctreeds.getEmptyRow();
		rootRow.setValue(0, DatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
		rootRow.setValue(1,"and");
		rootRow.setValue(3,"and");
		queryctreeds.addRow((Row) rootRow.clone());
	}
	
	protected String getQueryNode() {
		return null;
	}
	
	private void fillQueryDs(Dataset queryds, List<FilterMeta> allCandidateMetas) {
		// 获取所有的items
		List<FilterMeta> metaSet = new ArrayList<FilterMeta>();
		metaSet.addAll(allCandidateMetas);
		String parentPageId = LfwRuntimeEnvironment.getWebContext()
				.getParameter(WebConstant.OTHER_PAGE_UNIQUE_ID);
		/* 将被查询的目标Datasetid作为应答参数放到response-parameter中。 */
		queryds.getResParameters().addParameter(new Parameter(DatasetConstant.QUERY_TEMPLATE_TARGET_PAGEID, parentPageId));
		queryds.setCurrentKey("0");
		Iterator<FilterMeta> it = metaSet.iterator();
		while (it.hasNext()) {
			FilterMeta meta = it.next();
			Row row = null;
			String id = meta.getFieldCode().replaceAll("\\.", "_");
			String dynamicDefaultValue = null;
			if (meta.isCondition()) {
				row = queryds.getEmptyRow();
				row.setString(0, id);
				row.setString(1, meta.getFieldName());
				row.setString(2, String.valueOf(meta.getDataType()));
				row.setString(3, meta.getFieldCode());
				//设置默认值
				dynamicDefaultValue = getDynamicDefaultValue(meta.getFieldCode());
				row.setString(6, dynamicDefaultValue);

				queryds.addRow(row);
			}
			if (meta.isCondition()) {
				switch (meta.getDataType()) {
				case IQueryConstants.UFREF:
					row.setString(4, EditorTypeConst.REFERENCE);
					row.setString(5, id + "_refNode");
					if(dynamicDefaultValue != null){
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
							model.setBlurValue(dynamicDefaultValue);
							Vector dataV = model.matchBlurData(dynamicDefaultValue);
							if(dataV != null && dataV.size() > 0){
								int index = model.getFieldIndex(threeEle[2]);
								String showValue = (String) ((Vector)dataV.get(0)).get(index);
								if(showValue != null)
									row.setString(11, showValue);
							}
							//row.setString(13, defaultValue);
						}
					}
					break;
				case IQueryConstants.DATE:
					row.setString(4, EditorTypeConst.DATETEXT);
					break;
				case IQueryConstants.TIME:
					row.setString(4, EditorTypeConst.DATETEXT);
					break;
				case IQueryConstants.LITERALDATE:
					row.setString(4, EditorTypeConst.DATETEXT);
					break;
				case IQueryConstants.BOOLEAN:
					row.setString(4, EditorTypeConst.COMBODATA);
					break;
				case IQueryConstants.USERCOMBO:
					row.setString(4, EditorTypeConst.COMBODATA);
					break;
				}
			}
		}
	}

	public void onOk(MouseEvent e) {
	
	}
	
	public void onCancel(MouseEvent e){
		
	}
	
	public void onSavedCondition(){
		
	}
	
	
	protected String getDynamicDefaultValue(String fieldCode) {
		return null;
	}
}
