package nc.uap.ctrl.tpl.qry.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.uap.ctrl.tpl.qry.AdvancedQueryWidgetProvider;
import nc.uap.ctrl.tpl.qry.FromWhereSQLImpl;
import nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService;
import nc.uap.ctrl.tpl.qry.SimpleQueryWidgetProvider;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTranslator;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeUtils;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeVO;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.common.StringDataTypeConst;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebTreeNode;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Parameter;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TreeNodeEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.lfw.ncadapter.billtemplate.ref.LfwNCRefUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

public class AdvancedQueryController {
	
	private final String defaultSql = " 1=1 ";
	
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
	
	/**
	 * 普通页签下的数据加载
	 * @param e
	 */
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
	
	private final String QUERY_TEMPLATE_DEFAULT_ROOTPARENTID = "queryTemplateSpecialParentId";

	/**
	 * 树双击事件
	 * @param e
	 */
	public void ondbclick(TreeNodeEvent e) {
		Dataset queryds = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.QUERY_DS_ID);
		Row selectedRow = queryds.getCurrentRowData().getSelectedRow();
		
		if (null != selectedRow) {  // 不是根节点
			Dataset conditionDataset = null;
			// 根据当前tab页签选中状态决定对应的Dataset
			//TODO
			String currentItem = ((UITabComp)getCurrentUIMeta().findChildById("sqlTab")).getCurrentItem();
			if ("normalTab".equals(currentItem))  // 普通
				conditionDataset = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.QUERY_CDS_ID);
			else if ("adbTab".equals(currentItem))  // 高级
				conditionDataset = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.QUERY_CT_DS_ID);
			
			// 如果有一行数据了，再添加时，需要先增加AND标识符
			if ("adbTab".equals(currentItem)) {  // 高级
				//TODO 翻译JS代码
//				if(rows == null || rows.length == 0) 
//	  			parentId = IDatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID;
//	  			else{
//	  			if(rows != null && rows.length == 1){
//					var andRow = new DatasetRow();
////				andRow.setCellValue(0, AND_ID);
//					andRow.setCellValue(0,IDatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
//					andRow.setCellValue(1, "$#$");
//					andRow.setCellValue(3, "and");
//					andRow.setCellValue(4, null);
//					//hold on the position
//					andRow.setCellValue(12, null);
//					rightDs.addRow(andRow);
//					//将原来的一个node的父节点改变为顶层AND节点
////  				rightDs.setValueAt(0, 4, AND_ID);
//					rightDs.setValueAt(0, 4, IDatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
//	  			}
////  			parentId = AND_ID;
//	  			parentId = IDatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID;
			}
			
			// 为了防止同一个字段在条件中出现多次而是id无法分辨，因此添加时间作区分
			long time = new Date().getTime();
			Row newRow = conditionDataset.getEmptyRow();
			newRow.setValue(0, selectedRow.getValue(0) + String.valueOf(time));
			newRow.setValue(1, selectedRow.getValue(1));
			ComboData combData =  getCurrentWidget().getViewModels().getComboData("comb_" + selectedRow.getValue(0));
			if(combData != null){
				CombItem comboItem = combData.getAllCombItems()[0];
				newRow.setValue(2, comboItem.getValue());
				}
			newRow.setValue(3, selectedRow.getValue(6));
			String parentId = QUERY_TEMPLATE_DEFAULT_ROOTPARENTID;
			newRow.setValue(4, parentId);
			newRow.setValue(5, selectedRow.getValue(4));
			newRow.setValue(6, selectedRow.getValue(2));
			newRow.setValue(7, selectedRow.getValue(5));
			newRow.setValue(8, selectedRow.getValue(3));
			newRow.setValue(10, selectedRow.getValue(0));
			newRow.setValue(11, selectedRow.getValue(9));
			newRow.setValue(12, selectedRow.getValue(10));
			newRow.setValue(14, "0");
			
			conditionDataset.addRow(newRow);
		}
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

	/**
	 * 点“确定”时，触发plugout事件
	 * @param e
	 */
	public void onOk(MouseEvent e) {
		String sql = getQuerySql();
		FromWhereSQL whereSql = new FromWhereSQLImpl(null, sql);//new QueryTool().getFromWhere(null, null);
		Map paramMap = new HashMap();
		paramMap.put("queryNormalwhereSql", whereSql);
		//触发plugout事件
		CmdInvoker.invoke(new UifPlugoutCmd(getCurrentWidget().getId(), "queryNormalout", paramMap));
	}
	
	public void onCancel(MouseEvent e){
		
	}
	
	public void onSavedCondition(){
		
	}
	
	
	protected String getDynamicDefaultValue(String fieldCode) {
		return null;
	}
	
	
	public static LfwWidget getCurrentWidget() {
		return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getCurrentViewContext().getView();
	}
	
	public static UIMeta getCurrentUIMeta() {
		return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getCurrentViewContext().getUIMeta();
	}
	
	public String getQuerySql() {
		String sql = null;
		String currentItem = ((UITabComp)getCurrentUIMeta().findChildById("sqlTab")).getCurrentItem();
		if (currentItem.equals("normalTab"))  // 普通
			sql = getNormalQuerySql();
		else if (currentItem.equals("adbTab"))  // 高级
			sql = getAdvancedQuerySql();
		if (sql == null || sql == "")
			sql = this.defaultSql;
		return sql;
	}

	private String getNormalQuerySql() {
		Row[] rows = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.QUERY_CDS_ID)
				.getCurrentRowSet().getCurrentRowData().getRows();
		String sql = "";
		if (rows != null) {
			int validIndex = -1;
			for (int i = 0; i < rows.length; i++) {
				if (null == rows[i].getString(14) || rows[i].getString(14).equals("1"))
					continue;
				String rowSql = processRowToSql(rows[i]);
				if (rowSql == null)
					continue;
				validIndex++;
				if (validIndex == 0)
					
					sql += (" " + rowSql);
				else {
					sql += (" AND " + rowSql + " ");
				}
			}
		}
		return sql;
		
	}
	
	
	
	/**
	 * 保存查询方案时执行逻辑
	 * @param e
	 */
	public void saveTreeNode(MouseEvent<MenuItem> e) {
		Dataset savedQueryDs = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.SAVED_QC_DS_ID);
		Dataset queryConditionDs = getCurrentWidget().getViewModels().getDataset(AdvancedQueryWidgetProvider.QUERY_CDS_ID);
		String saveName = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("save_name");
		String oldName = "";
		if (savedQueryDs.getSelectedRow() != null)
			oldName = (String)savedQueryDs.getSelectedRow().getValue(savedQueryDs.nameToIndex("name"));
		//修改
		if (saveName.equals(oldName)){
			editSavedQuery(getCurrentWidget().getId(), savedQueryDs, queryConditionDs);
		}
		//新增
		else{
			newSavedQuery(getCurrentWidget().getId(), savedQueryDs, queryConditionDs);
		}
	}
	
	
	private void newSavedQuery(String widgetId, Dataset savedQueryDs, Dataset queryConditionDs){
		String saveName =  (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("save_name");
		Row r = savedQueryDs.getEmptyRow();
//		r.setValue(savedQueryDs.nameToIndex("name"), saveName);
//		r.setValue(savedQueryDs.nameToIndex("cuserid"), templateInfo.getUserid());
//		r.setValue(savedQueryDs.nameToIndex("pk_org"), templateInfo.getPk_Org());
//		r.setValue(savedQueryDs.nameToIndex("pk_template"), templateInfo.getTemplateId());
//		r.setValue(savedQueryDs.nameToIndex("funcode"), templateInfo.getFunNode());
		savedQueryDs.addRow(r);
		if (savedQueryDs.getCurrentKey() == null)
			savedQueryDs.setCurrentKey(Dataset.MASTER_KEY);
		savedQueryDs.setRowSelectIndex(savedQueryDs.getRowIndex(r));
		
//		SavedTreeSaveDelegator saveDelegator = new SavedTreeSaveDelegator(widgetId, savedQueryDs.getId(), queryConditionDs.getId()); 
//		//saveDelegator.setGlobalContext(getGlobalContext());
//		saveDelegator.execute();
		
		
//		Dataset2SuperVOSerializer ser = new Dataset2SuperVOSerializer();
////		AggregatedValueObject[] aggVos = ser.serialize(masterDs, aggVoClazz);
//		SuperVO[]  superVO = ser.serialize(savedQueryDs,savedQueryDs.getSelectedRow());
//		QuerySchemeVO vo = (QuerySchemeVO)superVO[0];
//		QuerySchemeObject qsobject = QuerySchemeUtils.fetchQuerySchemeFromDs(queryConditionDs);
//		vo.setQSObject4Blob(qsobject);
//		saveQuerySchemeVo(vo);
//		DynamicCompUtil dcu = new DynamicCompUtil(getGlobalContext(),getGlobalContext().getWidgetContext(widgetId));
//		dcu.refreshDataset(savedQueryDs);
		
		
	}
	
	private void editSavedQuery(String widgetId, Dataset savedQueryDs, Dataset queryConditionDs){
//		String saveName = getGlobalContext().getParameter("save_name");
//		
//		Row row =savedQueryDs.getSelectedRow();
//		row.setValue(savedQueryDs.nameToIndex("name"), saveName);
//		
//		SavedTreeEditDelegator editDelegator = new SavedTreeEditDelegator(widgetId, savedQueryDs.getId(), queryConditionDs.getId()); 
//		editDelegator.setGlobalContext(getGlobalContext());
//		editDelegator.execute();
	}
	
	
	
	/**
	 * 该方法对一行数据进行分析，生成sql片段。
	 * 
	 * @private
	 */
	private String processRowToSql(Row row) {
		String field = row.getString(8);
		String dataType = row.getString(6);
		String condition = row.getString(2);
		String value = null;

		// 如果是参照类型的一行数据，则取pk从extend_field中得对应是在LfwQtConditionWrapper的126设置
		if (row.getString(5) != null && row.getString(5).equals("Reference")) {
			value = row.getString(13);
		} else
			value = row.getString(3);

		if (value == null || "".equals(value))
			return null;
		boolean isStringType = isStringType(dataType);
		// TODO 性能问题
		String sqlSpatch = "";
		if (condition.equals(">") || condition.equals("<")
				|| condition.equals("<=") || condition.equals(">=")
				|| condition.equals("<>")) {
			
			if("DateText".equals(row.getString(5))){
				TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
				if(condition.equals("<") || condition.equals("<="))
					value =  new UFDate(value, timeZone).asBegin(timeZone).toString();
				else if(condition.equals(">") || condition.equals(">="))
					value = new UFDate(value, timeZone).asEnd(timeZone).toString();
			}
			
			sqlSpatch += (field + " " + condition + " ");
			if (isStringType)
				sqlSpatch += ("'" + value + "' ");
			else
				sqlSpatch += (value + " ");
		} else if (condition.equals("between")) {
			String smallValue = null;
			String bigValue = null;
			String[] values = value.split(",");
			if (values.length > 0){
				smallValue = values[0];
//				bigValue = values[0];
				if(values.length == 2)
					bigValue = values[1];
			}
			if ((bigValue == null || "".equals(bigValue))
					&& (smallValue == null || "".equals(smallValue)))
				return null;
			
			if("DateText".equals(row.getString(5))){
				TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
				if(bigValue != null && !"".equals(bigValue))
					bigValue = new UFDate(bigValue, timeZone).asEnd(timeZone).toString();
				if(smallValue != null && !"".equals(smallValue))
					smallValue = new UFDate(smallValue, timeZone).asBegin(timeZone).toString();
			}
			
			if (bigValue == null || "".equals(bigValue)) {
				sqlSpatch += (field + " >= ");
				if (isStringType)
					sqlSpatch += ("'" + smallValue + "' ");
				else
					sqlSpatch += (smallValue + " ");
			} else if (smallValue == null || "".equals(smallValue)) {
				sqlSpatch += (field + " <= ");
				if (isStringType)
					sqlSpatch += ("'" + bigValue + "' ");
				else
					sqlSpatch += (bigValue + " ");
			} else {
					if (isStringType)
						sqlSpatch += (field + " >= '" + smallValue + "' and "
								+ field + " <= '" + bigValue + "' ");
					else
						sqlSpatch += (field + " >= '" + smallValue + "' and "
								+ field + " <= '" + bigValue + "' ");
			}
		} else if (condition.equals("=")) {
			String[] values = value.split(",");
			if (values.length == 1) {
				//
				if("DateText".equals(row.getString(5))){
					TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
					String smallValue =  new UFDate(values[0], timeZone).asBegin(timeZone).toString();
					String bigValue =  new UFDate(values[0], timeZone).asEnd(timeZone).toString();
					sqlSpatch += (field + " >= '" + smallValue + "' and "
							+ field + " <= '" + bigValue + "' ");
				}
				else{
					sqlSpatch += (field + " = ");
					if (isStringType)
						sqlSpatch += ("'" + values[0] + "' ");
					else
						sqlSpatch += ("'" + values[0] + "'");
				}
			} else {
				sqlSpatch += (field + " in (");
				for (int i = 0; i < values.length; i++) {
					if (isStringType)
						sqlSpatch += ("'" + values[i] + "'");
					else
						sqlSpatch += values[i];
					if (i != values.length - 1)
						sqlSpatch += ",";
					else
						sqlSpatch += ")";
				}
			}
		} else if (condition.equals("like")) {
			sqlSpatch += (field + " like '%");
			sqlSpatch += (value + "%'");
		}

		//左包含
		else if (condition.equals("left like")) {
			sqlSpatch += (field + " like '" + value + "%'");
		}
		//右包含
		else if (condition.equals("right like")) {
			sqlSpatch += (field + " like '%" + value + "'");
		}
		if (row.getString(11) != null && !"".equals(row.getString(11))) {
			String parentField = row.getString(11);
			String tableName = row.getString(12);
			sqlSpatch = parentField + " in (select " + parentField + " from "
					+ tableName + " where " + sqlSpatch + ")";
		}
		if (sqlSpatch.equals(""))
			LfwLogger.debug("空条件行：" + row);
		return sqlSpatch;
	}
	
	
	/**
	 * @private 判断是否是需加 ‘ 的字符串类型
	 */
	private boolean isStringType(String dataType) {
		if (StringDataTypeConst.STRING.equals(dataType)
				|| StringDataTypeConst.BOOLEAN.equals(dataType)
				|| StringDataTypeConst.bOOLEAN.equals(dataType)
				|| StringDataTypeConst.UFBOOLEAN.equals(dataType)
				|| StringDataTypeConst.CHAR.equals(dataType)
				|| StringDataTypeConst.CHARACTER.equals(dataType)
				|| StringDataTypeConst.UFDATE.equals(dataType)
				|| StringDataTypeConst.UFDATETIME.equals(dataType)
				|| StringDataTypeConst.UFTIME.equals(dataType))
			return true;
		else
			return false;
	}
	
	private String getAdvancedQuerySql() {
		TreeViewComp advanceTree = (TreeViewComp) getCurrentWidget().getViewComponents()
				.getComponent("advanceTree");
		WebTreeNode rootNode = advanceTree.getTreeModel().getRootNode();
		List<WebTreeNode> nodes = rootNode.getChildNodeList();
		if (nodes.size() == 0)
			return null;
		Dataset ds = getCurrentWidget().getViewModels().getDataset(
				AdvancedQueryWidgetProvider.QUERY_CT_DS_ID);
		return generateSql(ds, nodes.get(0));
	}
	
	private String generateSql(Dataset ds, WebTreeNode parentNode) {
		String cond = ds.getRowById(parentNode.getRowId()).getString(3);
		List<WebTreeNode> nodes = parentNode.getChildNodeList();
		String sql = null;
		// 有子节点，表示必定为组合条件 AND/OR
		if (nodes != null && nodes.size() > 0) {
			String[] tmpSqls = new String[nodes.size()];
			List<String> filteredSqls = new ArrayList<String>();
			for (int i = 0; i < nodes.size(); i++) {
				Row row = ds.getRowById(nodes.get(i).getRowId());
				tmpSqls[i] = this.processRowToSql(row);
			}
			// 确定有几个sql块，多余一个，需要加AND/OR 和括号
			for (int i = 0; i < nodes.size(); i++) {
				if (tmpSqls[i] != null && tmpSqls[i] != "") {
					filteredSqls.add(tmpSqls[i]);
				}
			}
			if (filteredSqls.size() != 0) {
				if (filteredSqls.size() == 1)
					sql = filteredSqls.get(0);
				else {
					sql = "(";
					for (int i = 0; i < filteredSqls.size(); i++) {
						sql += filteredSqls.get(i);
						if (i != filteredSqls.size() - 1)
							sql += " " + cond + " ";
					}
					sql += ")";
				}
			}
		} else {
			Row row = ds.getRowById(parentNode.getRowId());
			sql = this.processRowToSql(row);
		}
		return sql;
	}

	
}
