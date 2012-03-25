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
//	/** ����Ȩ�޷ֱ��е��������� */
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
	 * ����FromWhereSql���Ա�ֱ��ʹ��NC��ѯ�ӿ�
	 * @param style
	 * @return
	 */
	public FromWhereSQL getFromWhere(Dataset ds, String mdClassId) {
		return getTableJoinWhereSql(ds, mdClassId);
	}
	
	/**
	 * ȡtableJoin���͵�sql
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
		//��Ϊ������������
		Row[] rows = qryDs.getCurrentRowData().getRows();//widget.getViewModels().getDataset(NCQWidgetBuilder.QUERY_CDS_ID).getCurrentRowSet().getCurrentRowData().getRows();
		//��Ԫ����
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
			// ����ǲ������͵�һ�����ݣ���ȡpk��extend_field�еö�Ӧ����LfwQtConditionWrapper��126����
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
					//��һ������
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
				 //�����
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
//	 * �÷���ʵ�ֶ�ds�Ĳ�ѯ
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
//			//��¼���β�ѯ����
//			pds.setLastCondition(true, sql);
//			getGlobalContext().getParentGlobalContext().hideCurrentDialog();
//		} 
//		catch (LfwBusinessException e) {
//			Logger.error(e.getMessage(), e);
//			throw new LfwRuntimeException("��ѯ���ݳ����쳣");
//		}
//	}
//
//	/**
//	 * ����ҳ��״̬
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
//	 * ͨ�����ô˷�����ȡ����֮���sql��ѯ������䡣
//	 * 
//	 * ����Sql���Ļ����㷨��(1) ���ڷ�����������ĸ���¼����parentIdΪĬ��Id���Ҳ������������������and���д��� (2)
//	 * ���������������¼����ջ��������Ϊ��ǰ�����������and/or����Sql���������һ��"("�� (3)
//	 * �ڵ�ǰ������֮��������ӽڵ㶼����ǰ����������ֱ��������һ�����ǵ�ǰ�������ӽڵ�ļ�¼�� (4)
//	 * �������һ�����ǵ�ǰ�������ļ�¼�����ڲ�����ջ�г�ջ�����丸
//	 * ������¼��ջ����count��sql���׷��count-1��")"�����Ը�����������Ϊ���������������Ϊ��ǰ�����ڵ㡣 (5)
//	 * �����������¼�ˣ����Ѿ���������ô��sql�����׷�Ӳ���ջ��ǰ������"��"�� (6)�ڴ������������ӽڵ�������������������ʱ����(2)���д���
//	 * 
//	 * @param baseOnTreeOrDataset
//	 *            ��ͨ��dataset����ͨ����������������sql��䣬��ǰQueryTemplateController�е���ק�¼���ͨ��
//	 *            ����������
//	 *            ��QueryTemplateProcessor.tree/QueryTemplateProcessor.dataset
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
//		//Ȩ��sql
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
//				// ����ǲ������͵�һ�����ݣ���ȡpk��extend_field�еö�Ӧ����LfwQtConditionWrapper��126����
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
	 * ����ӱ�����ĵ�sql
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

		// ����ǲ������͵�һ�����ݣ���ȡpk��extend_field�еö�Ӧ����LfwQtConditionWrapper��126����
		if (row.getString(5) != null && row.getString(5).equals("Reference")) {
			value = row.getString(13);
		} else
			value = row.getString(3);

		if (value == null || "".equals(value))
			return null;
		boolean isStringType = isStringType(dataType);
		// TODO ��������
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
		//�����
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
			LfwLogger.debug("�������У�" + row);
		return sqlSpatch;
	}
//	
//	/**
//	 * �÷�����һ�����ݽ��з���������sqlƬ�Ρ�
//	 * 
//	 * @private
//	 */
//	private String processRowToSql(Row row) {
//		String field = row.getString(8);
//		String dataType = row.getString(6);
//		String condition = row.getString(2);
//		String value = null;
//
//		// ����ǲ������͵�һ�����ݣ���ȡpk��extend_field�еö�Ӧ����LfwQtConditionWrapper��126����
//		if (row.getString(5) != null && row.getString(5).equals("Reference")) {
//			value = row.getString(13);
//		} else
//			value = row.getString(3);
//
//		if (value == null || "".equals(value))
//			return null;
//		boolean isStringType = isStringType(dataType);
//		// TODO ��������
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
//		//�����
//		else if (condition.equals("left like")) {
//			sqlSpatch += (field + " like '" + value + "%'");
//		}
//		//�Ұ���
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
//			LfwLogger.debug("�������У�" + row);
//		return sqlSpatch;
//	}
//	
//	
//	
////	/**
////	 * ��������Ȩ��
////	 * @return
////	 */
////	private String genPowerSQL4WhereSQLOnly() {
////		Map<IAttribute,String> dpAttr_dpTable_map = getAttr_DPTable_Map();
////		// û��Ȩ�޿��ƣ�ֱ�ӷ��ؿմ�
////		if (isWithoutDataPower(dpAttr_dpTable_map)) return "";
////		StringBuilder powersqlSB = new StringBuilder();
////		powersqlSB.append("(");
////		powersqlSB.append(genAttrsPowerSQL4WhereSQLOnly(dpAttr_dpTable_map));
////		String powersql = powersqlSB.toString();
////		// û������������Ȩ��sql
////		if (powersql.trim().equals("(")) return "";
////		return powersql;
////	}
////	
////	/**
////	 * ����map�е�����������������Ȩ��SQL
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
////	 * ɾ�������һ��AND������еĻ�
////	 */
////	private String deleteTheLastAND(String powersql) {
////		String sql = powersql;
////		// ȥ�����һ�� AND
////		if (sql.contains(AND.trim())) {
////			int index = sql.lastIndexOf(AND.trim());
////			sql = sql.substring(0, index) + ")";
////		}
////		return sql;
////	}
////	
////	
////	/**
////	 * ����Ȩ�ޱ���Ϊָ��������������Ȩ��SQL
////	 */
////	private String genAttrPowerSQL4WhereSQLOnly(IAttribute dpAttr,String dpTable) {
////		if (is1LevelAttr(dpAttr)) {// ��������
////			return gen1LevelAttrPowerSQL4WhereSQLOnly(dpAttr, dpTable);
////		} else {// ��ʵ������
////			return genNot1LevelAttrPowerSQL4WhereSQLOnly(dpAttr, dpTable);
////		}
////	}
//	
//	
////	/**
////	 * Ϊ��һ������(����ʵ���е�����)��������Ȩ��SQL(WhereSQLOnly��ʽ)
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
////	 * Ϊһ��������������Ȩ��SQL(WhereSQLOnly��ʽ)
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
////	 * ����map�ж��Ƿ���������Ȩ��
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
////	 * ���� Ԫ��������_����Ȩ�޷ֱ���� Map
////	 */
////	private Map<IAttribute,String>  getAttr_DPTable_Map() {
////		//Ԫ����ID
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
//	// ����Ȩ�޷ֱ�������飬�ڱ������������������ֻ�����һ�Σ��Ա����������ֲ�ͬ��ʽ��SQLʱ�ܹ���������Ȩ�޷����Զ�̵��ô���
////	private String[] dpTables;
//	
////	private String[] getDPTables(List<IAttribute> dpAttrs) {
////		if(dpTables == null) {
////			String[] beanIDs = extractBeanIDs(dpAttrs);
////			String[] operations = extractOperations(dpAttrs);
////			// ����beanIDs��operations��ѯ����Ȩ�޷ֱ���Ϣ�������Ϣ�ǻ���ʱ����仯�ģ�����ʵʱ��ѯ
////			IDataPowerTableQueryService dataPowerService = new DataPowerTableQueryService();
//////		    IDataPowerTableQueryService dataPowerService = new MockDataPowerTableQueryService();
////			dpTables = dataPowerService.getDataPowerTables(beanIDs, operations);
////		}
////		return dpTables;
////	}
//	
////	/**
////	 * ��ȡ����Ȩ�����Ե�operation
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
////	 * ��ȡ����Ȩ�����Թ���ʵ���id
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
////	 * ���ز����б���˺������Ȩ������
////	 */
////	private List<IAttribute> filter(List<IAttribute> dpAttrs) {
////		QueryTempletTotalVO totalVO = (QueryTempletTotalVO) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(NCQWidgetBuilder.QUERY_DATA_KEY);
////		List<FilterMeta> filterMetas = new ArrayList<FilterMeta>();
////		//�����ﴦ��
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
////	 * ����ʵ��������Ȩ�޸���Ȥ������
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
////			throw new RuntimeException("����ID��ȡBeanʧ��. ID=" + beanID);
////		}
////		return bean;
////	}
//	
//
//	private String generateSql(Dataset ds, WebTreeNode parentNode) {
//		String cond = ds.getRowById(parentNode.getRowId()).getString(3);
//		List<WebTreeNode> nodes = parentNode.getChildNodeList();
//		String sql = null;
//		// ���ӽڵ㣬��ʾ�ض�Ϊ������� AND/OR
//		if (nodes != null && nodes.size() > 0) {
//			String[] tmpSqls = new String[nodes.size()];
//			List<String> filteredSqls = new ArrayList<String>();
//			for (int i = 0; i < nodes.size(); i++) {
//				Row row = ds.getRowById(nodes.get(i).getRowId());
//				tmpSqls[i] = this.processRowToSql(row);
//			}
//			// ȷ���м���sql�飬����һ������Ҫ��AND/OR ������
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
	 * @private �ж��Ƿ������ �� ���ַ�������
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
//	 * ��ȡ��ѯ�Ĳ���������
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
//				// ����ǲ������͵�һ�����ݣ���ȡpk��extend_field�еö�Ӧ����LfwQtConditionWrapper��126����
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
