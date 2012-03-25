package nc.uap.ctrl.tpl.qry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.md.IMDQueryFacade;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.md.model.impl.BusinessEntity;
import nc.md.model.type.IType;
import nc.md.model.type.impl.CollectionType;
import nc.md.model.type.impl.RefType;
import nc.uap.lfw.core.common.StringDataTypeConst;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.log.LfwLogger;
import nc.ui.querytemplate.querytree.FromWhereSQL;
import nc.ui.querytemplate.querytree.FromWhereSQLImpl;

/**
 * 
 */
public class QueryTool{
//	private final String defaultSql = " 1=1 ";
//	private LfwPageContext globalContext;
//	private WidgetContext context;
//	private LfwWidget widget;
	Map<String, String> filedCodeAndTabMap = new HashMap<String, String>();
//	private String beanID;
//	/** 数据权限分表中的主键列名 */
//	private static final String PK_DP = IResourceConst.DATAPERM_SPLITE_PKCOLNAME;
//	
//	/** AND */
//	private static final String AND = " AND ";
//	
//	
//	private IDataPowerAttrFilter dataPowerAttrFilter = null;
//	private boolean isPowerEnable = true;
	
	public QueryTool() {
		//beanID = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(NCQWidgetBuilder.MAIN_METACLASS_ID);
	}
//
//	public void onclick(MouseEvent<T> e) {
//		executeSqlQuery();
//	}

	/**
	 * 创建FromWhereSql，以便直接使用NC查询接口
	 * @param style
	 * @return
	 */
	public FromWhereSQL getFromWhere(Dataset ds, String mdClassId) {
		return getTableJoinWhereSql(ds, mdClassId);
	}
	
	/**
	 * 取tableJoin类型的sql
	 * @return
	 */
	public FromWhereSQL getTableJoinWhereSql(Dataset qryDs, String mdClassId){
		Map<String, String> attrpath_alias_map = new HashMap<String, String>();
//		String pWidgetId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("pwid");
//		String pDatasetId =  (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("pdsid");
//		PageMeta parentPm = null;//getGlobalContext().getParentGlobalContext().getPageMeta();
//		Dataset pds = parentPm.getWidget(pWidgetId).getViewModels().getDataset(pDatasetId);
//		String key = UUID.randomUUID().toString();
//		pds.setCurrentKey(key);
		//作为条件的行数据
		Row[] rows = qryDs.getCurrentRowData().getRows();//widget.getViewModels().getDataset(NCQWidgetBuilder.QUERY_CDS_ID).getCurrentRowSet().getCurrentRowData().getRows();
		//主元数据
		IBean mainMainBean = null;
		String mainTableName = null;
		StringBuilder fromPart = new StringBuilder();
		String mainMetaClassId = mdClassId;
		if(mainMetaClassId != null){
			IMDQueryFacade qry = MDBaseQueryFacade.getInstance();
			try {
				mainMainBean = qry.getBeanByID(mainMetaClassId);
				mainTableName = mainMainBean.getTable().getName();
				attrpath_alias_map.put(".", mainTableName);
				fromPart.append(mainTableName + " " +  mainTableName);
			} 
			catch (MetaDataException e) {
				LfwLogger.error(e.getMessage(), e);
			}
		}
		String sql = "";
		int aliascount = 0;
		if (rows != null) {
			sql = getWherePart(attrpath_alias_map, rows, mainMainBean,
					mainTableName, fromPart, sql, aliascount);
			if(sql == null || sql.equals(""))
				sql = "1 = 1";
		}
		String wherePart = fromPart.toString();
		FromWhereSQLImpl querySQL = new FromWhereSQLImpl(wherePart, sql);
		querySQL.setAttrpath_alias_map(attrpath_alias_map);
		return querySQL;
	}
	
	
	

	private String getWherePart(Map<String, String> attrpath_alias_map,
			Row[] rows, IBean mainMainBean, String mainTableName,
			StringBuilder fromPart, String sql, int aliascount) {
		IBean mainBean = null;
		int validIndex = -1;
		for (int i = 0; i < rows.length; i++) {
			String value = null;
			// 如果是参照类型的一行数据，则取pk从extend_field中得对应是在LfwQtConditionWrapper的126设置
			if (rows[i].getString(5) != null && rows[i].getString(5).equals("Reference")) {
				value = rows[i].getString(13);
			} else
				value = rows[i].getString(3);
			if (value == null || "".equals(value))
				continue;
			String fieldCode = rows[i].getString(8);
			mainBean = mainMainBean;
			String tableName = null;
			String fileld = null;
			String leftFileld = null;
			if(fieldCode != null && fieldCode.indexOf(".") != -1){
				fileld = fieldCode.substring(0, fieldCode.indexOf("."));
				leftFileld = fieldCode.substring(fieldCode.indexOf(".") + 1);
				while(tableName == null){
					List<IAttribute> attributes = mainBean.getAttributes();
					//第一个属性
					IAttribute attr = null;
					 for (int j = 0; j < attributes.size(); j++) {
						 IAttribute attri = attributes.get(j);
						 if(attri.getName() != null && attri.getName().equals(fileld)){
							 attr = attri;
							 break;
						 }
					 }
					 BusinessEntity childEntity = null;
					 if(attr != null){
						 if(attr.getDataType() instanceof CollectionType){
							 CollectionType collection = (CollectionType) attr.getDataType();
							 childEntity = (BusinessEntity) collection.getElementType();
						 }
						 else if(attr.getDataType().getTypeType() == IType.REF){
							 childEntity =  (BusinessEntity) ((RefType) attr.getDataType()).getRefType();
						 }
						 if(leftFileld.indexOf(".") == -1){
							 List<IAttribute> childattributes = childEntity.getAttributes();
							 for (int k = 0; k < childattributes.size(); k++) {
								 IAttribute childattr = childattributes.get(k);
								 if(childattr.getName() != null && childattr.getName().equals(leftFileld)){
									 if(childattr.getDataType() instanceof CollectionType){
										 CollectionType collection = (CollectionType) attr.getDataType();
										 childEntity = (BusinessEntity) collection.getElementType();
										 tableName = childEntity.getDefaultTableName();
										 break;
									 }
								 }else {
									 tableName = childEntity.getTable().getName();
									 fileld = attr.getColumn().getName();
									 break;
								 }
							 }
							 
						 }
						 else{
							 mainBean = childEntity;
							 fileld = leftFileld.substring(0, fieldCode.indexOf("."));
							 leftFileld = leftFileld.substring(fieldCode.indexOf(".") + 1);
						 }
					 }
				}
			}
			if(tableName != null && attrpath_alias_map.get(tableName) == null){
				 //表别名
				 String alias = "T" + (++aliascount);
				 fromPart.append(" inner join " + tableName + " " + alias);
				 fromPart.append(" on " + mainTableName + "." + fileld + "=" +  alias + "." + fileld); 
				 attrpath_alias_map.put(tableName, alias);
				 filedCodeAndTabMap.put(fieldCode.substring(0, fieldCode.lastIndexOf(".")), alias);
			}
			String rowSql = processMultiRowToSql(rows[i], mainTableName);
			if (rowSql == null)
				continue;
			validIndex++;
			if (validIndex == 0)
				sql += (" " + rowSql);
			else {
				sql += (" AND " + rowSql + " ");
			}
		}
		return sql;
	}
	
//	/**
//	 * 该方法实现对ds的查询
//	 * 
//	 */
//	@SuppressWarnings("unchecked")
//	public void executeSqlQuery() {
//		String sql = getQuerySql();
//		String pWidgetId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("pwid");
//		String pDatasetId =  (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("pdsid");
//		PageMeta parentPm = getGlobalContext().getParentGlobalContext().getPageMeta();
//		Dataset pds = parentPm.getWidget(pWidgetId).getViewModels().getDataset(pDatasetId);
//		String key = UUID.randomUUID().toString();
//		pds.setCurrentKey(key);
//		Class<SuperVO> c = (Class<SuperVO>) LfwClassUtil.forName(pds.getVoMeta());
//		try {
//			SuperVO[] vos = queryVOs(sql, c, pds.getCurrentRowSet().getPaginationInfo());
//			new SuperVO2DatasetSerializer().serialize(vos, pds, Row.STATE_NORMAL);
//			setPageState(pWidgetId, parentPm, pds);
//			//记录本次查询条件
//			pds.setLastCondition(true, sql);
//			getGlobalContext().getParentGlobalContext().hideCurrentDialog();
//		} 
//		catch (LfwBusinessException e) {
//			Logger.error(e.getMessage(), e);
//			throw new LfwRuntimeException("查询数据出现异常");
//		}
//	}
//
//	/**
//	 * 设置页面状态
//	 * @param pWidgetId
//	 * @param parentPm
//	 * @param pds
//	 */
//	private void setPageState(String pWidgetId, PageMeta parentPm, Dataset pds) {
//		if(pds.isControloperatorStatus())
//			parentPm.setOperatorState(IOperatorState.INIT);
//		if(pds.isControlwidgetopeStatus())
//			parentPm.getWidget(pWidgetId).setOperatorState(IOperatorState.INIT);
//	}
//	
//	protected SuperVO[] queryVOs(String sql, Class<SuperVO> c, PaginationInfo paginationInfo) throws LfwBusinessException{
//		SuperVO vo = LfwClassUtil.newInstance(c);
//		return getCrudService().queryVOs(vo, paginationInfo, sql, null, null);
//	}
//	
//	protected ILfwCRUDService<SuperVO, AggregatedValueObject> getCrudService() {
//		return CRUDHelper.getCRUDService();
//	}
//
//	/**
//	 * 通过调用此方法获取构建之后的sql查询条件语句。
//	 * 
//	 * 构建Sql语句的基本算法：(1) 对于非特殊操作符的根记录（即parentId为默认Id并且不是特殊运算符），按and进行处理。 (2)
//	 * 遇到特殊运算符记录，入栈；并设置为当前操作运算符（and/or），Sql语句中增加一个"("。 (3)
//	 * 在当前操作符之后的所有子节点都按当前操作符处理，直到遇到第一个不是当前操作符子节点的记录。 (4)
//	 * 如果遇到一个不是当前操作符的记录，则在操作符栈中出栈查找其父
//	 * ，并记录出栈个数count，sql语句追加count-1个")"，并以父结点操作符作为其运算符，并设置为当前操作节点。 (5)
//	 * 如果遇不到记录了，即已经结束，那么在sql语句中追加操作栈当前个数的"）"。 (6)在处理中遇到的子节点可能是特殊操作符，此时按照(2)进行处理。
//	 * 
//	 * @param baseOnTreeOrDataset
//	 *            是通过dataset还是通过树的数据来构建sql语句，当前QueryTemplateController中的拖拽事件是通过
//	 *            树来构建的
//	 *            。QueryTemplateProcessor.tree/QueryTemplateProcessor.dataset
//	 */
//
//	private String getNormalQuerySql(Dataset qryDs) {
//		Row[] rows = qryDs.get;
//		String sql = "";
//		if (rows != null) {
//			int validIndex = -1;
//			for (int i = 0; i < rows.length; i++) {
//				if (null == rows[i].getString(14) || rows[i].getString(14).equals("1"))
//					continue;
//				String rowSql = processRowToSql(rows[i]);
//				if (rowSql == null)
//					continue;
//				validIndex++;
//				if (validIndex == 0)
//					
//					sql += (" " + rowSql);
//				else {
//					sql += (" AND " + rowSql + " ");
//				}
//			}
//		}
//		//权限sql
////		String powerSql = genPowerSQL4WhereSQLOnly();
////		if(powerSql != null && !"".equals(powerSql))
////			sql += " and " + powerSql;
//		return sql;
//		
//	}
//
//	/**
//	 * 
//	 * @param isTree
//	 * @return
//	private String getLogicCondition(boolean isTree) {
//		Dataset ds;
//		if (isTree)
//			ds = widget.getViewModels().getDataset("queryConditionTreeDataset");
//		else
//			ds = widget.getViewModels().getDataset("queryConditionDataset");
//		Row[] rows = ds.getCurrentRowSet().getCurrentRowData().getRows();
//		String cond = "";
//		if (rows != null) {
//			for (int i = 0; i < rows.length; i++) {
//				Row row = rows[i];
//				if (null == row.getString(14) || row.getString(14).equals("1"))
//					continue;
//				String field = row.getString(8);
//				String value = null;
//
//				// 如果是参照类型的一行数据，则取pk从extend_field中得对应是在LfwQtConditionWrapper的126设置
//				if (row.getString(5).equals("Reference"))
//					value = row.getString(13);
//				else
//					value = row.getString(3);
//				if (value == null || value == "")
//					continue;
//				if (cond != "")
//					cond += "&";
//				cond += (field + "=" + value);
//			}
//		}
//		return cond;
//	}
//	 */
//
//	private String getAdvancedQuerySql() {
//		TreeViewComp advanceTree = (TreeViewComp) widget.getViewComponents()
//				.getComponent("advanceTree");
//		WebTreeNode rootNode = advanceTree.getTreeModel().getRootNode();
//		List<WebTreeNode> nodes = rootNode.getChildNodeList();
//		if (nodes.size() == 0)
//			return null;
//		Dataset qctds = null;//widget.getViewModels().getDataset();
//		return generateSql(qctds, nodes.get(0));
//	}
//
//	
	/**
	 * 处理加表别名的的sql
	 * @param row
	 * @param mainTableName
	 * @return
	 */
	private String processMultiRowToSql(Row row, String mainTableName) {
		String field = row.getString(8);
		if(field != null && field.indexOf(".") != -1){
			String rightFiled = field.substring(0,field.lastIndexOf("."));
			//String leftFileld = field.substring(field.lastIndexOf(".") + 1);
			field = field.replace(rightFiled, filedCodeAndTabMap.get(rightFiled));
		}else{
			field = mainTableName + "." + field;
		}
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
			sqlSpatch += (field + " " + condition + " ");
				if (isStringType)
					sqlSpatch += ("'" + value + "' ");
				else
					sqlSpatch += ("'" + value + "' ");
			} else if (condition.equals("between")) {
			String[] values = value.split(",");
			String smallValue = values[0];
			String bigValue = values[1];
			if ((bigValue == null || "".equals(bigValue))
					&& (smallValue == null || "".equals(smallValue)))
				return null;
			if (bigValue == null || "".equals(bigValue)) {
				sqlSpatch += (field + " >= ");
				if (isStringType)
					sqlSpatch += ("'" + smallValue + "' ");
				else
					sqlSpatch += ("'" + smallValue + "' ");
			} else if (smallValue == null || "".equals(smallValue)) {
				sqlSpatch += (field + " <= ");
				if (isStringType)
					sqlSpatch += ("'" + bigValue + "' ");
				else
					sqlSpatch += ("'" + bigValue + "' ");
			} else {
					if (isStringType)
						sqlSpatch += (field + " >= '" + smallValue + "' and "
								+ field + " <= '" + bigValue + "' ");
					else
						sqlSpatch += (field + " >= '" + smallValue + "' and " + field
								+ " <= '" + bigValue + "' ");
			}
		} else if (condition.equals("=")) {
			String[] values = value.split(",");
			if (values.length == 1) {
				//
				sqlSpatch += (field + " = ");
				if (isStringType)
					sqlSpatch += ("'" + values[0] + "' ");
				else
					sqlSpatch += ("'" + values[0] + "' ");
			} else {
				sqlSpatch += (field + " in (");
				for (int i = 0; i < values.length; i++) {
					if (isStringType)
						sqlSpatch += ("'" + values[i] + "'");
					else
						sqlSpatch += ("'" + values[i] + "'");
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
//	
//	/**
//	 * 该方法对一行数据进行分析，生成sql片段。
//	 * 
//	 * @private
//	 */
//	private String processRowToSql(Row row) {
//		String field = row.getString(8);
//		String dataType = row.getString(6);
//		String condition = row.getString(2);
//		String value = null;
//
//		// 如果是参照类型的一行数据，则取pk从extend_field中得对应是在LfwQtConditionWrapper的126设置
//		if (row.getString(5) != null && row.getString(5).equals("Reference")) {
//			value = row.getString(13);
//		} else
//			value = row.getString(3);
//
//		if (value == null || "".equals(value))
//			return null;
//		boolean isStringType = isStringType(dataType);
//		// TODO 性能问题
//		String sqlSpatch = "";
//		if (condition.equals(">") || condition.equals("<")
//				|| condition.equals("<=") || condition.equals(">=")
//				|| condition.equals("<>")) {
//			
//			if("DateText".equals(row.getString(5))){
//				TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
//				if(condition.equals("<") || condition.equals("<="))
//					value =  new UFDate(value, timeZone).asBegin(timeZone).toString();
//				else if(condition.equals(">") || condition.equals(">="))
//					value = new UFDate(value, timeZone).asEnd(timeZone).toString();
//			}
//			
//			sqlSpatch += (field + " " + condition + " ");
//			if (isStringType)
//				sqlSpatch += ("'" + value + "' ");
//			else
//				sqlSpatch += (value + " ");
//		} else if (condition.equals("between")) {
//			String smallValue = null;
//			String bigValue = null;
//			String[] values = value.split(",");
//			if (values.length > 0){
//				smallValue = values[0];
////				bigValue = values[0];
//				if(values.length == 2)
//					bigValue = values[1];
//			}
//			if ((bigValue == null || "".equals(bigValue))
//					&& (smallValue == null || "".equals(smallValue)))
//				return null;
//			
//			if("DateText".equals(row.getString(5))){
//				TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
//				if(bigValue != null && !"".equals(bigValue))
//					bigValue = new UFDate(bigValue, timeZone).asEnd(timeZone).toString();
//				if(smallValue != null && !"".equals(smallValue))
//					smallValue = new UFDate(smallValue, timeZone).asBegin(timeZone).toString();
//			}
//			
//			if (bigValue == null || "".equals(bigValue)) {
//				sqlSpatch += (field + " >= ");
//				if (isStringType)
//					sqlSpatch += ("'" + smallValue + "' ");
//				else
//					sqlSpatch += (smallValue + " ");
//			} else if (smallValue == null || "".equals(smallValue)) {
//				sqlSpatch += (field + " <= ");
//				if (isStringType)
//					sqlSpatch += ("'" + bigValue + "' ");
//				else
//					sqlSpatch += (bigValue + " ");
//			} else {
//					if (isStringType)
//						sqlSpatch += (field + " >= '" + smallValue + "' and "
//								+ field + " <= '" + bigValue + "' ");
//					else
//						sqlSpatch += (field + " >= '" + smallValue + "' and "
//								+ field + " <= '" + bigValue + "' ");
//			}
//		} else if (condition.equals("=")) {
//			String[] values = value.split(",");
//			if (values.length == 1) {
//				//
//				if("DateText".equals(row.getString(5))){
//					TimeZone timeZone = (TimeZone) LfwRuntimeEnvironment.getLfwSessionBean().getTimeZone();
//					String smallValue =  new UFDate(values[0], timeZone).asBegin(timeZone).toString();
//					String bigValue =  new UFDate(values[0], timeZone).asEnd(timeZone).toString();
//					sqlSpatch += (field + " >= '" + smallValue + "' and "
//							+ field + " <= '" + bigValue + "' ");
//				}
//				else{
//					sqlSpatch += (field + " = ");
//					if (isStringType)
//						sqlSpatch += ("'" + values[0] + "' ");
//					else
//						sqlSpatch += ("'" + values[0] + "'");
//				}
//			} else {
//				sqlSpatch += (field + " in (");
//				for (int i = 0; i < values.length; i++) {
//					if (isStringType)
//						sqlSpatch += ("'" + values[i] + "'");
//					else
//						sqlSpatch += values[i];
//					if (i != values.length - 1)
//						sqlSpatch += ",";
//					else
//						sqlSpatch += ")";
//				}
//			}
//		} else if (condition.equals("like")) {
//			sqlSpatch += (field + " like '%");
//			sqlSpatch += (value + "%'");
//		}
//
//		//左包含
//		else if (condition.equals("left like")) {
//			sqlSpatch += (field + " like '" + value + "%'");
//		}
//		//右包含
//		else if (condition.equals("right like")) {
//			sqlSpatch += (field + " like '%" + value + "'");
//		}
//		if (row.getString(11) != null && !"".equals(row.getString(11))) {
//			String parentField = row.getString(11);
//			String tableName = row.getString(12);
//			sqlSpatch = parentField + " in (select " + parentField + " from "
//					+ tableName + " where " + sqlSpatch + ")";
//		}
//		if (sqlSpatch.equals(""))
//			LfwLogger.debug("空条件行：" + row);
//		return sqlSpatch;
//	}
//	
//	
//	
////	/**
////	 * 处理数据权限
////	 * @return
////	 */
////	private String genPowerSQL4WhereSQLOnly() {
////		Map<IAttribute,String> dpAttr_dpTable_map = getAttr_DPTable_Map();
////		// 没有权限控制，直接返回空串
////		if (isWithoutDataPower(dpAttr_dpTable_map)) return "";
////		StringBuilder powersqlSB = new StringBuilder();
////		powersqlSB.append("(");
////		powersqlSB.append(genAttrsPowerSQL4WhereSQLOnly(dpAttr_dpTable_map));
////		String powersql = powersqlSB.toString();
////		// 没有生成真正的权限sql
////		if (powersql.trim().equals("(")) return "";
////		return powersql;
////	}
////	
////	/**
////	 * 根据map中的所有属性生成数据权限SQL
////	 */
////	private String genAttrsPowerSQL4WhereSQLOnly(Map<IAttribute, String> dpAttr_dpTable_map) {
////		StringBuilder powersql = new StringBuilder();
////		for (IAttribute dpAttr : dpAttr_dpTable_map.keySet()) {
////			String dpTable = dpAttr_dpTable_map.get(dpAttr);
////			if (dpTable != null) {
////				powersql.append(genAttrPowerSQL4WhereSQLOnly(dpAttr, dpTable));
////			}
////		}
////		String sql = deleteTheLastAND(powersql.toString());
////		return sql;
////	}
////	
////	
////	/**
////	 * 删除掉最后一个AND，如果有的话
////	 */
////	private String deleteTheLastAND(String powersql) {
////		String sql = powersql;
////		// 去掉最后一个 AND
////		if (sql.contains(AND.trim())) {
////			int index = sql.lastIndexOf(AND.trim());
////			sql = sql.substring(0, index) + ")";
////		}
////		return sql;
////	}
////	
////	
////	/**
////	 * 根据权限表名为指定属性生成数据权限SQL
////	 */
////	private String genAttrPowerSQL4WhereSQLOnly(IAttribute dpAttr,String dpTable) {
////		if (is1LevelAttr(dpAttr)) {// 主表属性
////			return gen1LevelAttrPowerSQL4WhereSQLOnly(dpAttr, dpTable);
////		} else {// 子实体属性
////			return genNot1LevelAttrPowerSQL4WhereSQLOnly(dpAttr, dpTable);
////		}
////	}
//	
//	
////	/**
////	 * 为非一级属性(即子实体中的属性)生成数据权限SQL(WhereSQLOnly形式)
////	 * <p>
////	 * pk_parent in (select distinct fk_parent from table_child where column in (select pk_doc from DPtable)) AND
////	 */
////	private String genNot1LevelAttrPowerSQL4WhereSQLOnly(IAttribute dpAttr, String dpTable) {
////		IBean parent = getBeanByID(beanID);
////		IBean child = dpAttr.getOwnerBean();
////		StringBuilder sql = new StringBuilder();
////		sql.append(getPKColumnName(parent)).append(" in (select distinct ")
////				.append(getFKColumnName(child, parent)).append(" from ")
////				.append(getTableName(child)).append(" where ").append(
////						getColumnName(dpAttr)).append(" in (select ").append(
////						PK_DP).append(" from ").append(dpTable).append("))")
////				.append(AND);
////		return sql.toString();
////	}
////	
////	
////	/**
////	 * 为一级属性生成数据权限SQL(WhereSQLOnly形式)
////	 * <p>
////	 * column in (select pk_doc from DPtable) AND
////	 */
////	private String gen1LevelAttrPowerSQL4WhereSQLOnly(IAttribute dpAttr,String dpTable){
////		StringBuilder sql = new StringBuilder();
////		sql.append(getColumnName(dpAttr)).append(" in (select ").append(PK_DP)
////				.append(" from ").append(dpTable).append(")").append(AND);
////		return sql.toString();
////	}
////	
////	
////	private boolean is1LevelAttr(IAttribute dpAttr) {
////		return QTMDUtil.is1LevelAttr(dpAttr, getBeanByID(beanID));
////	}
//	
//	
////	/**
////	 * 根据map判定是否启用数据权限
////	 */
////	private boolean isWithoutDataPower(Map<IAttribute,String> dpAttr_DPTable_Map){
////		return isEmpty(dpAttr_DPTable_Map);
////	}
////	
////	private static <T> boolean isEmpty(T t) {
////		return t == null || t.toString().trim().length() == 0;
////	}
//	
////	/**
////	 * 返回 元数据属性_数据权限分表表名 Map
////	 */
////	private Map<IAttribute,String>  getAttr_DPTable_Map() {
////		//元数据ID
////		String mainMetaClassId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(NCQWidgetBuilder.MAIN_METACLASS_ID);
////		IBean bean = getBeanByID(mainMetaClassId);
////		if (bean == null) return null;
////		List<IAttribute> dpAttrs = filter(getDPAttrs(bean));
////		return createAttr_DPTable_Map(dpAttrs);
////	}
//	
////	private Map<IAttribute,String> createAttr_DPTable_Map(List<IAttribute> dpAttrs) {
////		if (dpAttrs != null && dpAttrs.size() != 0) {
////			Map<IAttribute,String> dpAttr_dpTable_map = new HashMap<IAttribute, String>();
////			String[] dpTables = getDPTables(dpAttrs);
////			if (dpTables != null && dpTables.length != 0) {
////				for (int i = 0; i < dpTables.length; i++) {
////					dpAttr_dpTable_map.put(dpAttrs.get(i), dpTables[i]);
////				}
////			}
////			return dpAttr_dpTable_map;
////		}
////		return null;
////	}
//	
//	// 数据权限分表表名数组，在本对象的生命周期内它只会加载一次，以便于生成三种不同样式的SQL时能够减少数据权限服务的远程调用次数
////	private String[] dpTables;
//	
////	private String[] getDPTables(List<IAttribute> dpAttrs) {
////		if(dpTables == null) {
////			String[] beanIDs = extractBeanIDs(dpAttrs);
////			String[] operations = extractOperations(dpAttrs);
////			// 根据beanIDs和operations查询数据权限分表信息，这份信息是会随时间而变化的，所以实时查询
////			IDataPowerTableQueryService dataPowerService = new DataPowerTableQueryService();
//////		    IDataPowerTableQueryService dataPowerService = new MockDataPowerTableQueryService();
////			dpTables = dataPowerService.getDataPowerTables(beanIDs, operations);
////		}
////		return dpTables;
////	}
//	
////	/**
////	 * 抽取数据权限属性的operation
////	 */
////	private String[] extractOperations(List<IAttribute> dpAttrs) {
////		String[] operations = new String[dpAttrs.size()];
////		for (int i = 0; i < dpAttrs.size(); i++) {
////			operations[i] = getDataPowerOperation(dpAttrs.get(i));
////		}
////		return operations;
////	}
////
////	/**
////	 * 抽取数据权限属性关联实体的id
////	 */
////	private String[] extractBeanIDs(List<IAttribute> dpAttrs) {
////		String[] beanIDs = new String[dpAttrs.size()];
////		for (int i = 0; i < dpAttrs.size(); i++) {
////			beanIDs[i] = getAssociationBean(dpAttrs.get(i)).getID();
////		}
////		return beanIDs;
////	}
////
////	
////	private QtVO2MetaConvertor voConvertor = new QtVO2MetaConvertor();
//	
////	/**
////	 * 返回参数列表过滤后的数据权限属性
////	 */
////	private List<IAttribute> filter(List<IAttribute> dpAttrs) {
////		QueryTempletTotalVO totalVO = (QueryTempletTotalVO) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(NCQWidgetBuilder.QUERY_DATA_KEY);
////		List<FilterMeta> filterMetas = new ArrayList<FilterMeta>();
////		//做多语处理
////		//translate(totalVO);
////		QueryConditionVO[] vos = totalVO.getConditionVOs();
////		if (vos != null) {
////			for (QueryConditionVO vo : vos) {
////				if (vo.getIfUsed() == null
////						|| !vo.getIfUsed().booleanValue())
////					continue;
////				FilterMeta tmp = voConvertor.convert(vo);
////				filterMetas.add(tmp);
////			}
////		}
////		
////		QTDataPowerAttrFilter dataPowerFilter = null;
////		return dpAttrs;
//////		//new QTDataPowerAttrFilter(filterMetas);
//////		if (dpAttrs != null && dpAttrs.size() != 0) {
//////			List<IAttribute> filteredAttrs = new ArrayList<IAttribute>();
//////			for (IAttribute attr : dpAttrs) {
//////				if (dataPowerFilter.accept(attr)) {
//////					filteredAttrs.add(attr);
//////				}
//////			}
//////			return filteredAttrs;
//////		}
//////		return null;
////	}
//	
////	private IDataPowerAttrFilter getDataPowerAttrFilter() {
////		return dataPowerAttrFilter;
////	}
////
////	public void setDataPowerAttrFilter(IDataPowerAttrFilter dpAttrFilter) {
////		if (isPowerEnable) {
////			((DataPowerIncludedQuerySQLGenerator) getGenerator()).setDataPowerAttrFilter(dpAttrFilter);
////		} 
////	}
////	IQuerySQLGenerator generator = null;
////	private IQuerySQLGenerator getGenerator() {
////		if (generator == null) {
////			generator = isPowerEnable ? new DataPowerIncludedQuerySQLGenerator(beanId)
////					: new QuerySQLGenerator();
////		}
////		return generator;
////	}
////	
//	
//	
////	
////	/**
////	 * 返回实体中数据权限感兴趣的属性
////	 */
////	private List<IAttribute> getDPAttrs(IBean bean){
////		return DataPowerMDUtil.extractDPAttrs(bean);
////	}
//	
////	
////	private static IBean getBeanByID(String beanID) {
////		IBean bean = null;
////		try {
////			bean = MDBaseQueryFacade.getInstance().getBeanByID(beanID);
////		} catch (MetaDataException e) {
////			throw new RuntimeException("根据ID获取Bean失败. ID=" + beanID);
////		}
////		return bean;
////	}
//	
//
//	private String generateSql(Dataset ds, WebTreeNode parentNode) {
//		String cond = ds.getRowById(parentNode.getRowId()).getString(3);
//		List<WebTreeNode> nodes = parentNode.getChildNodeList();
//		String sql = null;
//		// 有子节点，表示必定为组合条件 AND/OR
//		if (nodes != null && nodes.size() > 0) {
//			String[] tmpSqls = new String[nodes.size()];
//			List<String> filteredSqls = new ArrayList<String>();
//			for (int i = 0; i < nodes.size(); i++) {
//				Row row = ds.getRowById(nodes.get(i).getRowId());
//				tmpSqls[i] = this.processRowToSql(row);
//			}
//			// 确定有几个sql块，多余一个，需要加AND/OR 和括号
//			for (int i = 0; i < nodes.size(); i++) {
//				if (tmpSqls[i] != null && tmpSqls[i] != "") {
//					filteredSqls.add(tmpSqls[i]);
//				}
//			}
//			if (filteredSqls.size() != 0) {
//				if (filteredSqls.size() == 1)
//					sql = filteredSqls.get(0);
//				else {
//					sql = "(";
//					for (int i = 0; i < filteredSqls.size(); i++) {
//						sql += filteredSqls.get(i);
//						if (i != filteredSqls.size() - 1)
//							sql += " " + cond + " ";
//					}
//					sql += ")";
//				}
//			}
//		} else {
//			Row row = ds.getRowById(parentNode.getRowId());
//			sql = this.processRowToSql(row);
//		}
//		return sql;
//	}
//
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

//	/**
//	 * 获取查询的参数的数组
//	 * 
//	 * @private
//	 */
//	private String getQueryParamArray() {
//		if (globalContext.getTab("sqlTab").getCurrentIndex() == 0)
//			return this.getNormalQueryParams();
//		else
//			return this.getAdvancedQueryParams();
//	};

//	private String getNormalQueryParams() {
//		Row[] rows = widget.getViewModels().getDataset(
//				NCQWidgetBuilder.QUERY_CDS_ID).getCurrentRowSet()
//				.getCurrentRowData().getRows();
//		String params = "";
//		if (rows != null) {
//			for (int i = 0; i < rows.length; i++) {
//				String condition = rows[i].getString(2) + ",";
//				String dataType = rows[i].getString(6) + ",";
//				String field = rows[i].getString(8) + ",";
//				String value = null;
//				// 如果是参照类型的一行数据，则取pk从extend_field中得对应是在LfwQtConditionWrapper的126设置
//				if (rows[i].getString(5).equals("getString"))
//					value = rows[i].getString(13);
//				else
//					value = rows[i].getString(3);
//				if (value == null || value.equals(""))
//					continue;
//				params += "{" + condition + dataType + field + value + "},";
//			}
//		}
//		return params;
//	};

//	private String getAdvancedQueryParams() {
//		TreeViewComp advanceTree = (TreeViewComp) widget.getViewComponents()
//				.getComponent(NCQWidgetBuilder.ADVANCE_TREE_ID);
//		WebTreeNode rootNode = advanceTree.getTreeModel().getRootNode();
//		List<WebTreeNode> pnodes = rootNode.getChildNodeList();
//		if (pnodes.size() == 0)
//			return null;
//		WebTreeNode parentNode = pnodes.get(0);
//		Dataset ds = widget.getViewModels().getDataset(
//				NCQWidgetBuilder.QUERY_CT_DS_ID);
//		String cond = ds.getRowById(parentNode.getRowId()).getString(3);
//		List<WebTreeNode> nodes = parentNode.getChildNodeList();
//		
//		//TODO
//		
//		return null;
//
//	};

}
