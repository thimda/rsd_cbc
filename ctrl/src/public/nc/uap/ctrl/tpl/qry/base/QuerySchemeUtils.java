package nc.uap.ctrl.tpl.qry.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.qry.SimpleQueryWidgetProvider;
import nc.uap.ctrl.tpl.qry.meta.AndRule;
import nc.uap.ctrl.tpl.qry.meta.ContainerRule;
import nc.uap.ctrl.tpl.qry.meta.DefaultConstEnum;
import nc.uap.ctrl.tpl.qry.meta.DefaultFilter;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IRule;
import nc.uap.ctrl.tpl.qry.meta.QueryRule;
import nc.uap.ctrl.tpl.qry.meta.RefValueObject;
import nc.uap.ctrl.tpl.qry.operator.AndOperator;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.ctrl.tpl.qry.operator.OperatorFactory;
import nc.uap.ctrl.tpl.qry.operator.OrOperator;
import nc.uap.ctrl.tpl.qry.value.DefaultFieldValue;
import nc.uap.ctrl.tpl.qry.value.DefaultFieldValueElement;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.StaticComboData;
import nc.uap.lfw.core.common.CompIdGenerator;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.refnode.NCRefNode;
import nc.uap.lfw.ncadapter.billtemplate.gen.RefNodeGenerator;
import nc.uap.lfw.ncadapter.billtemplate.ref.LfwNCRefUtil;
import nc.uap.lfw.reference.app.AppLfwRefGenUtil;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.nc.NcAdapterGridRefModel;
import nc.uap.lfw.reference.nc.NcAdapterTreeGridRefModel;
import nc.uap.lfw.reference.nc.NcAdapterTreeRefModel;
import nc.ui.bd.ref.AbstractRefGridTreeModel;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.bd.ref.AbstractRefTreeModel;
import nc.ui.bd.ref.IRefConst;
import nc.ui.bd.ref.RefValueVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.pub.query.IQueryConstants;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.RefResultVO;
import nc.vo.querytemplate.QueryTemplateUtils;
import nc.vo.querytemplate.queryscheme.QuerySchemeObject;
import nc.vo.querytemplate.queryscheme.QuerySchemeVO;
/**
 * 查询方案vo工具类
 * 
 */

public class QuerySchemeUtils {

	/**
	 * 模板查询条件VO 转换为 FilterMeta
	 * 
	 * @param vo
	 * @return
	 */
	public static FilterMeta queryCondition2Meta(QueryConditionVO vo){  
		FilterMeta meta = new FilterMeta();
		meta.setFieldCode(vo.getFieldCode());
		meta.setFieldName(vo.getFieldName());
		meta.setOperators(OperatorFactory.getInstance().getOperators(vo.getOperaCode()));
		meta.setTableCode(vo.getTableCode());
		meta.setTableName(vo.getTableName());
		meta.setIfAutoCheck(vo.getIfAutoCheck()==null?false:vo.getIfAutoCheck().booleanValue());
		meta.setIsEditable(vo.getIfImmobility()==null?false:vo.getIfImmobility().booleanValue());
		meta.setDispType(vo.getDispType());
		meta.setReturnType(vo.getReturnType());
		meta.setValueEditorDescription(vo.getConsultCode());
		meta.setDataType(vo.getDataType());
		meta.setDefaultValue(vo.getValue());
		meta.setFixCondition(vo.getIfImmobility()==null?false:vo.getIfImmobility().booleanValue());
		meta.setRequired(vo.getIfMust()==null?false:vo.getIfMust().booleanValue());
		meta.setDefault(vo.getIfDefault()==null?false:vo.getIfDefault().booleanValue());
		meta.setDataPower(vo.getIfDataPower().booleanValue());
		meta.setUserDef(vo.getUserDef());
		meta.setCondition(vo.getIsCondition() == null ? true : vo.getIsCondition().booleanValue());
		meta.setInstrumentedsql(vo.getInstrumentedsql());
		meta.setOrder(vo.getIfOrder().booleanValue());
		return meta;
	}

	/**
	 * 查询条件数据集 转换为 DefaultFilter
	 * 
	 * @param ds
	 * @return
	 */
	public static List<DefaultFilter> queryConditionDs2Filter(Dataset ds){
		Row[] rows = ds.getCurrentRowData().getRows();
		List<DefaultFilter> filters = new ArrayList<DefaultFilter>();
		for (Row row : rows){
			DefaultFilter filter = new DefaultFilter();
			FilterMeta meta = new FilterMeta();
			String code = (String)row.getValue(ds.nameToIndex("query_condition_field"));
			meta.setFieldCode(code);
			String name = (String)row.getValue(ds.nameToIndex("query_condition_label"));
			meta.setFieldName(name);
			String operatorValue = (String)row.getValue(ds.nameToIndex("query_condition_condition"));
			meta.setOperators(OperatorFactory.getInstance().getOperators(operatorValue));
			String defaultValue = (String)row.getValue(ds.nameToIndex("query_condition_value"));
			meta.setDefaultValue(defaultValue);
			String dataType = (String)row.getValue(ds.nameToIndex("query_condition_editorType"));
			int type = 0;
			if (dataType.equals(EditorTypeConst.STRINGTEXT))
				type = IQueryConstants.STRING;
			else if (dataType.equals(EditorTypeConst.INTEGERTEXT))
				type = IQueryConstants.INTEGER;
			else if (dataType.equals(EditorTypeConst.DECIMALTEXT))
				type = IQueryConstants.DECIMAL;
			else if (dataType.equals(EditorTypeConst.DATETEXT))
				type = IQueryConstants.DATE;
			else if (dataType.equals(EditorTypeConst.DATETEXT))
				type = IQueryConstants.LITERALDATE;
			else if (dataType.equals(EditorTypeConst.COMBODATA))
				type = IQueryConstants.BOOLEAN;
			else if (dataType.equals(EditorTypeConst.REFERENCE))
				type = IQueryConstants.UFREF;
			meta.setDataType(type);
			if  (dataType == EditorTypeConst.REFERENCE){
				defaultValue = (String)row.getValue(ds.nameToIndex("refpk"));
				meta.setDefaultValue(defaultValue);
			}
			String conditionType = (String)row.getValue(ds.nameToIndex("query_condition_type"));
			if (conditionType.equals("0")){
				meta.setRequired(true);
			}
			else if (conditionType.equals("1")){
				meta.setFixCondition(true);
			}
			else if (conditionType.equals("2")){
				meta.setDefault(true);
			}
			String logicType = (String)row.getValue(ds.nameToIndex("query_condition_is"));
			if (logicType.equals("1")){
				meta.setCondition(false);
			}
//			meta.setTableCode(vo.getTableCode());
//			meta.setTableName(vo.getTableName());
//			meta.setIfAutoCheck(vo.getIfAutoCheck()==null?false:vo.getIfAutoCheck().booleanValue());
//			meta.setIsEditable(vo.getIfImmobility()==null?false:vo.getIfImmobility().booleanValue());
//			meta.setDispType(vo.getDispType());
//			meta.setReturnType(vo.getReturnType());
//			meta.setValueEditorDescription(vo.getConsultCode());
//			meta.setDataPower(vo.getIfDataPower().booleanValue());
//			meta.setUserDef(vo.getUserDef());
//			meta.setInstrumentedsql(vo.getInstrumentedsql());
//			meta.setOrder(vo.getIfOrder().booleanValue());
			filter.setFilterMeta(meta);
			IOperator operator = OperatorFactory.getInstance().getOperator(operatorValue);
			if(operator!=null)
				filter.setOperator(operator);
			else{
				filter.setOperator(meta.getOperators()[0]);
			}
//			String strValue = vo.getValue();
//			RefResultVO refVO = vo.getRefResult();
//			DefaultFieldValue value = createDefaultFieldValue(meta, strValue, refVO);				
//			filter.setFieldValue(value);
			filters.add(filter);
		}
		return filters;
	}
	
	/**
	 * 生成默认DefaultFilter
	 * @param meta
	 * @return
	 */
	public static IFilter createDefaultFilter(FilterMeta meta){
		String strValue = meta.getDefaultValue();
		if(strValue==null || strValue.trim().length() == 0)
			return null;
		else{
			DefaultFilter filter = new DefaultFilter();
			filter.setFilterMeta(meta);
			filter.setOperator(meta.getOperators()[0]);
			DefaultFieldValue fieldValue = createDefaultFieldValue(meta, strValue, null);
			filter.setFieldValue(fieldValue);
			return filter;
		}
	}
	
	/**
	 * 生成DefaultFilter
	 * 
	 * @param meta
	 * @param vo
	 * @return
	 */
	public static IFilter createFilterByConditionVo(FilterMeta meta, ConditionVO vo){
		DefaultFilter filter = new DefaultFilter();
		filter.setFilterMeta(meta);
		IOperator operator = OperatorFactory.getInstance().getOperator(vo.getOperaCode());
		if(operator!=null)
			filter.setOperator(operator);
		else{
			filter.setOperator(meta.getOperators()[0]);
		}
		String strValue = vo.getValue();
		RefResultVO refVO = vo.getRefResult();
		DefaultFieldValue value = createDefaultFieldValue(meta, strValue, refVO);				

		filter.setFieldValue(value);
		return filter;
	}
	
	/**
	 * 从数据集得到QuerySchemeObject
	 * 
	 * @param dataset
	 * @return
	 */
	public static QuerySchemeObject fetchQuerySchemeFromDs(Dataset dataset){
		QuerySchemeObject qsobject = new QuerySchemeObject();
		
		fetchCriteria(qsobject, dataset);							

		fetchQueryTypeInfo(qsobject);

		fetchNormalCondition(qsobject);
		
		fetchLogicalCondtion(qsobject);
		
		return qsobject;
	}
	
	/**
	 * 设置查询条件到数据集中
	 * @param qsobject
	 * @param dataset
	 */
	public static void setQuerySchemeObjectToDs(QuerySchemeObject qsobject, Dataset dataset){
		QueryResult queryResult = (QueryResult)QueryTemplateUtils.readFromXML(qsobject.getQueryResult());
		IRule resultRule = queryResult.getResultRule();
		if (resultRule instanceof ContainerRule) {
			//只处理并且
			if (resultRule instanceof AndRule)
				AndOperator.getInstance();
			else
				OrOperator.getInstance();

			dataset.clear();
			dataset.setCurrentKey("0");
			IRule[] rules = ((ContainerRule) resultRule).getChildrenRules();
			if (rules != null) {
				//只处理一层条件
				for (IRule rule : rules) {
					DefaultFilter filter = (DefaultFilter)((QueryRule) rule).getFormatObject();
					FilterMeta meta = (FilterMeta)filter.getFilterMeta();
					setMetaToDs(meta, dataset, null);
				}
			}
		}	
	}
	
	
	/**
	 * 将FilterMeta转成成FormElement
	 * @param filterMeta
	 * @return
	 */
	public static FormElement setMetaToFormEle(FilterMeta filterMeta, LfwWidget widget){
		FormElement formEle = new FormElement();
		String editorType;
			StringBuffer extInfo = new StringBuffer();
			switch(filterMeta.getDataType()){
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
					//String refId = filterMeta.getFieldCode().replaceAll("\\.", "_") + "_refNode";
					
					String refId = CompIdGenerator.generateRefCompId(SimpleQueryWidgetProvider.MAINDS, filterMeta.getFieldCode());

					
					formEle.setRefNode(refId);
					formEle.setVisible(false);
					extInfo.append(refId);
					String refCode = filterMeta.getValueEditorDescription();
					AbstractRefModel model = LfwNCRefUtil.getRefModel(refCode);
					if(model == null)
						return null;
					int type = LfwNCRefUtil.getRefType(model);
					ILfwRefModel refModel = null;
					if(type == IRefConst.GRIDTREE){
						NcAdapterTreeGridRefModel tgrm = new NcAdapterTreeGridRefModel();
						tgrm.setNcModel((AbstractRefGridTreeModel) model);
						refModel = tgrm;
					}
					
					else if(type == IRefConst.TREE){
						NcAdapterTreeRefModel tgrm = new NcAdapterTreeRefModel();
						tgrm.setNcModel((AbstractRefTreeModel) model);
						refModel = tgrm;
					}
					
					else if(type == IRefConst.GRID){
						NcAdapterGridRefModel tgrm = new NcAdapterGridRefModel();
						tgrm.setNcModel(model);
						refModel = tgrm;
					}
					
					AppLfwRefGenUtil gen = new AppLfwRefGenUtil(refModel, null);
					String[] refEles = gen.getRefElements();
					//读入字段属性，包含Pk和name字段
					String readFields = refEles[0] + "," + refEles[2];
				
					Dataset ds = widget.getViewModels().getDataset(SimpleQueryWidgetProvider.MAINDS);
					Field field_mc = ds.getFieldSet().getField(filterMeta.getFieldCode() + "_mc");
					if(field_mc == null){
						field_mc = new Field();
						field_mc.setId(filterMeta.getFieldCode() + "_mc");
						field_mc.setText(filterMeta.getFieldName());
						ds.getFieldSet().addField(field_mc);
					}
					//生成写入字段，包含字段本身和添加的字段_mc
					String writeFields = filterMeta.getFieldCode() + "," + filterMeta.getFieldCode() + "_mc";
					NCRefNode refNode = new RefNodeGenerator().createRefNode(ds, false, filterMeta.getFieldCode(), null, refCode, readFields, writeFields, false, null, null);
					widget.getViewModels().addRefNode(refNode);
		
					FormComp form = (FormComp) widget.getViewComponents().getComponent(SimpleQueryWidgetProvider.MAINFORM);
					FormElement formEleMc = form.getElementById(filterMeta.getFieldCode() + "_mc");
					if(formEleMc == null){
						formEleMc = new FormElement();
						formEleMc.setEditorType(editorType);
						formEleMc.setId(filterMeta.getFieldCode());
						formEleMc.setText(filterMeta.getFieldName());
						formEleMc.setField(filterMeta.getFieldCode());
						formEleMc.setRefNode(refId);
						form.addElement(formEleMc);
					}
					
					
					if(model != null){
						//LfwRefGenUtil refUtil = new String[]{model.getRefPkField(), model.getRefCodeField(), model.getRefNameField()};
						//new LfwRefGenUtil(model);
						//设置pk_group的值,为了把查询参照数据
						model.setPk_group(LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
						//取出参照三要素对应的字段名称(pk, code, name)
						String[] threeEle = new String[]{model.getPkFieldCode(), model.getRefCodeField(), model.getRefNameField()};
					//	model.setBlurFields(threeEle);
						//model.setBlurValue(defaultValue);
						//Vector dataV = model.matchBlurData(defaultValue);
//						if(dataV != null && dataV.size() > 0){
//							int index = model.getFieldIndex(threeEle[2]);
//							Object value = ((Vector)dataV.get(0)).get(index);
//							String showValue = null;
//							if (value instanceof RefValueVO)
//								showValue = (String) ((RefValueVO)value).getNewValue();
//							else
//								showValue = (String) ((Vector)dataV.get(0)).get(index);
//							if(showValue != null)
//								row.setString(3, showValue);
//						}
						//row.setString(13, defaultValue);
					}
					//存真实值
					//row.setString(13, defaultValue);
					break;
					
				case IQueryConstants.USERCOMBO:
					editorType = EditorTypeConst.COMBODATA;
					break;
				default:
					editorType = EditorTypeConst.STRINGTEXT;
			}
			
		formEle.setEditorType(editorType);
		formEle.setId(filterMeta.getFieldCode());
		formEle.setText(filterMeta.getFieldName());
		formEle.setField(filterMeta.getFieldCode());
		return formEle;
	}
	

	
	
	
	/**
	 * 把FilterMeta转换到数据集中
	 * 
	 * @param meta
	 * @param ds
	 */
	public static void setMetaToDs(FilterMeta meta, Dataset ds, Map<String, IFilter> fieldCodeFilterMap){
		
		IFilter filter = null;
		if (fieldCodeFilterMap != null)
			filter = fieldCodeFilterMap.get(meta.getFieldCode());
		if (!meta.isCondition() || meta.isDefault() || meta.isFixCondition()) {
			Row row = ds.getEmptyRow();
			String id = meta.getFieldCode().replaceAll("\\.", "_");
			row.setString(0, id);
			row.setString(1, meta.getFieldName());
			
			String defaultValue = null;
			String dynamicDefaultValue = null;
//			if (listener != null)
//				dynamicDefaultValue = listener.getDynamicDefaultValue(meta.getFieldCode());
			defaultValue = dynamicDefaultValue == null ? meta.getDefaultValue() : dynamicDefaultValue;
//			String defaultValue = meta.getDefaultValue();
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
							Object value = ((Vector)dataV.get(0)).get(index);
							String showValue = null;
							if (value instanceof RefValueVO)
								showValue = (String) ((RefValueVO)value).getNewValue();
							else
								showValue = (String) ((Vector)dataV.get(0)).get(index);
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
			String operatorValue = null;
			if(filter != null)
				operatorValue = filter.getOperator().getOperatorCode();
			else{
				IOperator[] operators = meta.getOperators();
				for(int i = 0; i < operators.length; i++) {
					if (editorType == EditorTypeConst.DATETEXT){
						if (defaultValue != null){
							UFDateTime endDate = new UFDateTime(defaultValue); 
							UFDateTime beginDate = getBeforeOneMonthDate(endDate);
							row.setString(3, beginDate.getMillis() + "," + endDate.getMillis());
						}
						if(operators[i].getOperatorCode().equals("between")){
							operatorValue = "between";
							break;
						}
					}
					else if(operators[i].getOperatorCode().equals("=")){
						operatorValue = "=";
						break;
					}
				}
			}
			
			row.setString(2, operatorValue);
			
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
			ds.addRow(row);
		}
	}
	
	private static UFDateTime getBeforeOneMonthDate(UFDateTime endDate) {
		int year = endDate.getYear();
		int month = endDate.getMonth();
		int day = endDate.getDay();
		
		month = month == 1 ? 12 : month -1;
		UFDateTime tempDate = new UFDateTime(year + "-" + month + "-01 00:00:00");
		
		int beginDaysMonth = tempDate.getDaysMonth();
		int offsetDays = day > beginDaysMonth ? day : beginDaysMonth;  
		tempDate = endDate.getDateTimeBefore(offsetDays);
		return tempDate; 
	}

	public static QuerySchemeVO getQuerySchemeVoByPk(String pk){
		String where = "pk_queryscheme = '"+  pk + "'";
		Collection<QuerySchemeVO> results = null;
		BaseDAO basedao = new BaseDAO();
		try {
			results = basedao.retrieveByClause(QuerySchemeVO.class, where);
		} catch (DAOException e) {
			Logger.error(e);
		}
		if(results == null) 
			return null;
		return results.toArray(new QuerySchemeVO[0])[0];
	}
	
	protected static DefaultFieldValue createDefaultFieldValue(FilterMeta meta, String strValue, RefResultVO refVO){
		DefaultFieldValue value = new DefaultFieldValue();

		String[] strValues = strValue.split(",");
		List<IFieldValueElement> elems = createFieldValueElements(meta, strValues, refVO);
		for (IFieldValueElement element : elems){
			value.add(element);
		}
		return value;
	}
	
	private static List<IFieldValueElement> createFieldValueElements(FilterMeta meta, String[] strValues, RefResultVO refVO){
		List<IFieldValueElement> elementList = new ArrayList<IFieldValueElement>();
		for (int i = 0; i < strValues.length; i++){
			final String strValue = strValues[i];
			if (isSysFuntin(strValue)){
				elementList.addAll(createFieldValueElementsForSysFunction(meta, strValue));
			}
			else{
				elementList.add(createFieldValueElement(meta, strValue, null));
			}
		}
		return elementList;
	}
	
	private static List<IFieldValueElement> createFieldValueElementsForSysFunction(FilterMeta meta, final String strValue){
		List<IFieldValueElement> tempElementList =  new ArrayList<IFieldValueElement>();
		return tempElementList;
	}

	private static IFieldValueElement createFieldValueElement(FilterMeta meta, String strValue, RefResultVO refVO) {
		IFieldValueElement elem = null;
		switch (meta.getDataType()) {
			case IQueryConstants.STRING: {
				elem = new DefaultFieldValueElement(strValue,strValue,strValue);
				break;
			}
	
			case IQueryConstants.INTEGER: {
				Integer i = Integer.valueOf(strValue);
				elem = new DefaultFieldValueElement(strValue,strValue,i);
				break;				
			}
			case IQueryConstants.DECIMAL: {
				UFDouble dbl = new UFDouble(strValue);
				elem = new DefaultFieldValueElement(strValue,strValue,dbl);
				break;					
			}
			case IQueryConstants.BOOLEAN: {
				UFBoolean b = UFBoolean.valueOf(strValue);
				DefaultConstEnum enu = new DefaultConstEnum(b,b.booleanValue()?"是":"否");
				elem = new DefaultFieldValueElement(enu);
				break;				
			}
			case IQueryConstants.USERCOMBO: {
				DefaultConstEnum enu = null;
				if(meta.getValueEditorDescription().startsWith(IQueryConstants.COMBO_INDEX))
					enu = new DefaultConstEnum(Integer.valueOf(strValue),strValue);
				else{
					enu = new DefaultConstEnum(strValue,strValue);
				}
				elem = new DefaultFieldValueElement(enu);
				break;
			}
			case IQueryConstants.UFREF: {
				if(refVO==null){ //认为strValue是PK
					RefValueObject r = new RefValueObject();
					r.setPk(strValue);
					elem = new DefaultFieldValueElement(strValue,strValue,r);
				}
				else
				{
					//返回编码 返回名称
					RefValueObject r = new RefValueObject();
					r.setPk(refVO.getRefPK());
					String showString = meta.getDispType()==IQueryConstants.DISPCODE?refVO.getRefCode():refVO.getRefName();
					String sqlString = (meta.getReturnType()==IQueryConstants.RETURNCODE)?refVO.getRefCode():
						((meta.getReturnType()==IQueryConstants.RETURNNAME?
							refVO.getRefName():refVO.getRefPK()));
					r.setCode(refVO.getRefCode());
					r.setName(refVO.getRefName());
					elem = new DefaultFieldValueElement(showString,sqlString,r);
				}
				break;
			}
			case IQueryConstants.DATE: // 日期
			{
				UFDate date = UFDate.getDate(strValue);
				elem = new DefaultFieldValueElement(date.toString(),date.toString(),date);
				break;
			}
			default:
				// TODO: 处理异常情况
				break;
		}
		return elem;
	}
	
	/**
	 * 取查询条件
	 * @param qsobject
	 * @param dataset
	 */
	private static void fetchCriteria(QuerySchemeObject qsobject, Dataset dataset) {
		qsobject.setComplete(true);
		QueryResult queryResult = new QueryResult();
		AndRule rule = new AndRule();
//		Row[] rows = dataset.getCurrentRowData().getRows();
//		for (Row row : rows){
//			row.getValue(dataset.nameToIndex(""));
//		}
		List<DefaultFilter> filters =  queryConditionDs2Filter(dataset);
		for (DefaultFilter filter : filters){
			QueryRule sqlRule = new QueryRule();
			sqlRule.setFormatObject(filter);
			((ContainerRule) rule).addRule(sqlRule);
		}
//		queryResult.setResultRule(tree.ripFixCondition().getQueryResultRule());//保存时将树中固定条件和必输条件去掉							
		queryResult.setEditorType(0);
		queryResult.setResultRule(rule);
		qsobject.setQueryResult(QueryTemplateUtils.writeToXML(queryResult));
	}
	
	private static void fetchQueryTypeInfo(QuerySchemeObject qsobject) {
		qsobject.setQueryType(0);
		
	}
	
	private static void fetchNormalCondition(QuerySchemeObject qsobject) {
		// TODO Auto-generated method stub
		
	}
	
	private static void fetchLogicalCondtion(QuerySchemeObject qsobject) {
		// TODO Auto-generated method stub
		
	}

	private static boolean isSysFuntin(String strValue){
		return strValue==null?false:strValue.startsWith("#")&&strValue.endsWith("#");
	}

}
