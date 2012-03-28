package nc.uap.ctrl.tpl.qry;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import nc.bs.logging.Logger;
import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.md.model.type.ICollectionType;
import nc.md.model.type.IRefType;
import nc.md.util.MDUtil;
import nc.uap.lfw.querytemplate.model.LfwConditionField;
import nc.vo.pub.BusinessException;
import nc.vo.pub.core.BizObject;
import nc.vo.pub.core.ObjectNode;
import nc.vo.pub.core.ObjectStorage;
import nc.vo.uap.pf.OrganizeUnit;

public class LfwQueryTemplateServiceImpl implements IQryTemplateRpcService{

	private static final long serialVersionUID = -4163549287271080016L;
//	private transient LfwLogger logger = LoggerManager.getLogger(LfwQueryTemplateServiceViaJsonImpl.class);

//	public Map<String, Object> getEntityAttrs(String entityName, String level) {
//		try {
//			IBean bean = MDQueryService.lookupMDQueryService().getBeanByFullName(entityName);
//			List<IAttribute> attrList = bean.getAttributes();
//			Iterator<IAttribute> it = attrList.iterator();
//			List<String[]> resultList = new ArrayList<String[]>();
//			while(it.hasNext()){
//				IAttribute attr = it.next();
//				if(attr.isNotExistInModel())
//					continue;
//				String[] result = new String[8];
//				String code;
//				IBusinessEntity be = null;
//				//跳过聚合类型 TODO,其它处理方式？
//				if(MDUtil.isCollectionType(attr.getDataType()))
//					continue;
//				
//				if(MDUtil.isMDBean(attr.getDataType()) || MDUtil.isRefType(attr.getDataType()))
//					be = getBEOfAttribute(attr);
////					if(MDUtil.isCollectionType(attr.getDataType())){
////						IBusinessEntity pbe = (IBusinessEntity) attr.getContainer();
////						String referencedField = pbe.getTable().getPrimaryKey().getPKColumn().getName();
////						code = getForeignKey(be, pbe.getTable().getName(), referencedField);
////					}
////					else
//				
//				code = attr.getColumn().getName();
//				String dataType = LfwTypeToITypeTranslator.translateITypeToString(attr.getDataType());
//				result[0] = level + code;
//				result[1] = attr.getDisplayName();
//				result[2] = dataType;
//				result[3] = code;
//				result[4] = EditorTypeConst.dt2etMap.get(dataType);
//				if(be != null)
//					result[5] = be.getFullName();
//				result[7] = bean.getTable().getName();
//				resultList.add(result);
//			}
//			String[][] dsResult = (String[][])resultList.toArray(new String[][]{});
//			ComboData[] combs = QueryTemplateComboUtil.getComboDataMD(attrList);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("dsResult", dsResult);
//			map.put("combs", combs);
//			return map;
//		} 
//		catch (Exception e) {
//			throw new LfwRuntimeException(e.getMessage());
//		}
//	}
	
	/**
	 * 查询模板--收藏夹--保存(文件夹或查询条件)
	 * @param conditions
	 * @param nodeInfo
	 * @throws UnsupportedEncodingException
	 */
	public void saveQueryCondition(String[] conditions, String nodeInfo) {
		String[] nodeInfoArray = getNodeInfoArray(nodeInfo);
		String kind = nodeInfoArray[4];
		
		String dbName = null;
		//String storageClassName = MyFavoritesStorage.class.getName();
		BizObject obj = null;
//		MyFavoritesNode node = null;
//		
//		try {
//			if(MyFavoritesNode.Kind.equals(kind)){//Favorites
//				LfwConditionField[] conditionFields = convert2CondFields(conditions);
//				node =  getFavoritesNode(nodeInfoArray);
//				node.setKind(MyFavoritesNode.Kind);
//				obj  =  getFavoritesDefObject(conditionFields,node);
//				
//			}else{//Folder
//				node =  getFavoritesNode(nodeInfoArray);
//				node.setKind(FolderNode.Kind);
//			}
//			saveQueryObject(dbName, storageClassName, node, obj);
//		} catch (BusinessException e) {
//			Logger.error(e);
//		}catch(UnsupportedEncodingException e){
//			Logger.error("查询模板--收藏夹--保存>>>" + e);
//		}
	}
	
	/**
	 * 查询模板--收藏夹--载入查询条件
	 * @param nodeInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[] loadQueryCondition(String nodeInfo){

		String[] nodeInfoArray = getNodeInfoArray(nodeInfo);
//		MyFavoritesDefObject nodeInfoObj = (MyFavoritesDefObject) getObjectStorageInstance().loadObject(getFavoritesNode(nodeInfoArray));
//		if(nodeInfoObj != null){
//			LfwConditionField[] conditionFields = (LfwConditionField[]) nodeInfoObj.getUserDefCondition();
//			return convert2StringArr(conditionFields);
//		}
		return null;
	}
	
	/**
	 * 查询模板--收藏夹--删除（文件夹或查询条件）
	 * @param nodeInfo
	 */
	@SuppressWarnings("unchecked")
	public void deleteQueryCondition(String nodeInfo){
		String[] nodeInfoArray = getNodeInfoArray(nodeInfo);
		//getObjectStorageInstance().deleteObject(getFavoritesNode(nodeInfoArray));
	}
	
	/**
	 * 查询模板--收藏夹--修改名称
	 * @param conditions
	 * @param nodeInfo
	 * @throws UnsupportedEncodingException 
	 */
	public void modifyQueryConditionName(String nodeInfo){
		saveQueryCondition(loadQueryCondition(nodeInfo),nodeInfo);
	}
	
	@SuppressWarnings("unchecked")
	private ObjectStorage getObjectStorageInstance(){
		String dbName = null;
		String StorageClassName = "nc.bs.pub.quertytemplate.MyFavoritesStorage";
		ObjectStorage storage = null;
		try{
			if (dbName == null || dbName.length() == 0) {
				storage = (ObjectStorage) Class.forName(StorageClassName).newInstance();
			} else {
				Constructor constructor = Class.forName(StorageClassName).getConstructor(
						new Class[] { String.class });
				storage = (ObjectStorage) constructor.newInstance(new Object[] { dbName });
			}
		}catch (Exception e) {
			Logger.error("查询模板获取ObjectStorage实例>>>" + e);
		}
		return storage;
	}
	
//	private MyFavoritesNode getFavoritesNode(String[] nodeInfoArray){
//		NcSessionBean ses = (NcSessionBean) LfwRuntimeEnvironment.getLfwSessionBean();
//		TemplateInfo tempInfo = new TemplateInfo();
//		tempInfo.setPk_Org(ses.getPk_unit());
//		//tempInfo.setCurrentCorpPk(LfwRuntimeEnvironment.getPkCorp());
//		tempInfo.setFunNode(nodeInfoArray[7]);
//		tempInfo.setUserid(ses.getPk_user());
//		tempInfo.setBusiType(null);
//		tempInfo.setNodekey(null);
//		tempInfo.setTemplateId(nodeInfoArray[8]);
//		
//		MyFavoritesNode node = new MyFavoritesNode();
//		node.populateTempInfo(tempInfo);
//		node.setID(nodeInfoArray[0]);
//		node.setGUID(nodeInfoArray[1]);
//		node.setParentGUID(nodeInfoArray[2]);
//		node.setDisplayName(nodeInfoArray[3]);
//		node.setDefault(isTrue(nodeInfoArray[6]));
//		return node;
//	}
	
    private boolean isTrue(String val) {
        if (val != null && val.length() > 0
                && (val.equalsIgnoreCase("true") || val.charAt(0) == 'Y' || val.charAt(0) == 'y'))
            return true;
        else
            return false;
    }
    
//	private MyFavoritesDefObject getFavoritesDefObject(LfwConditionField[] conditionFields,MyFavoritesNode node) {
//		
//		MyFavoritesDefObject def = new MyFavoritesDefObject();
//		def.setUserDefCondition(conditionFields);
//		node.setObject(def);
//		def.setNode(node);
//		return def;
//	}
	

	private LfwConditionField[] convert2CondFields(String[] conditions) throws UnsupportedEncodingException {
		List<LfwConditionField> list = new ArrayList<LfwConditionField>();
		for (int i = 0; i < conditions.length; i++) {
			String[] strArr = conditions[i].split(",", -1);
			LfwConditionField field = new LfwConditionField();
			field.setFieldId(strArr[0]);
			field.setLabel(convertStr(strArr[1]));
			field.setOperatorValue(convertStr(strArr[2]));
			field.setDefaultValue(convertStr(strArr[3]));
			field.setPfieldId(strArr[4]);
			field.setEditorType(strArr[5]);
			field.setDataType(strArr[6]);
			field.setEditorInfo(convertStr(strArr[7]));
			field.setField(strArr[8]);
			
			String condTypeStr = convertStr(strArr[9]);
			if(condTypeStr != null)
				field.setCondType(Integer.parseInt(condTypeStr));
			field.setEditorInfo2(convertStr(strArr[10]));
			field.setTableName(convertStr(strArr[12]));
			field.setExtendField(convertStr(strArr[13]));
			String logicType = convertStr(strArr[14]);
			if(logicType != null)
				field.setLogicType(Integer.valueOf(convertStr(strArr[14])));
			list.add(field);
		}
		return list.toArray(new LfwConditionField[conditions.length]);
	}
	
	private String[] convert2StringArr(LfwConditionField[] field){
		String[] strArr = new String[field.length];
		for (int i = 0; i < field.length; i++) {
			strArr[i] = convertFiled(field[i].getFieldId()) + "#";
			strArr[i] += convertFiled(field[i].getLabel()) + "#";
			strArr[i] += convertFiled(field[i].getOperatorValue()) + "#";
			strArr[i] += convertFiled(field[i].getDefaultValue()) + "#";
			strArr[i] += convertFiled(field[i].getPfieldId()) + "#";
			strArr[i] += convertFiled(field[i].getEditorType()) + "#";
			strArr[i] += convertFiled(field[i].getDataType()) + "#";
			strArr[i] += convertFiled(field[i].getEditorInfo()) + "#";
			strArr[i] += convertFiled(field[i].getField()) + "#";
			strArr[i] += convertFiled(String.valueOf(field[i].getCondType())) + "#";
			strArr[i] += convertFiled(field[i].getEditorInfo2()) + "#";
			strArr[i] += convertFiled(field[i].getParentField()) + "#";
			strArr[i] += convertFiled(field[i].getTableName()) + "#";
			strArr[i] += convertFiled(field[i].getExtendField()) + "#";
			strArr[i] += convertFiled(field[i].getLogicType()+"");
			
		}
		return strArr;
	}
	
	private String convertStr(String sourceStr) throws UnsupportedEncodingException{
		if(sourceStr == null)
			return null;
		if(sourceStr.equals("$NULL$"))
			return null;
		return URLDecoder.decode(sourceStr, "UTF-8");
	}
	
	/**
	 * 将null和“$NULL$”转换为空值，便于前台处理
	 * @param str
	 * @return
	 */
	private String convertFiled(String str){
		try{
			return (str == null || str.equals("$NULL$")) ? "" : URLDecoder.decode(str, "UTF-8");   
		}catch(UnsupportedEncodingException e){
			Logger.error("查询模板转换>>>" + e);
		}
		return "";
	}
	
	/**
	 * 解析前台传来的树节点信息 或 文件夹信息 为String[]
	 * @param nodeInfo
	 * @return String[]
	 */
	private String[] getNodeInfoArray(String nodeInfo){
		String[] strArr = nodeInfo.split(",", -1);
		for(int i=0; i < strArr.length; i++){
			if(strArr[i].equals("$NULL$"))
				strArr[i] = null;
			else
				try {
					strArr[i] = URLDecoder.decode(strArr[i], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					Logger.error(e.getMessage(),e);
				}
		}
		return strArr;
	}
	
	//TODO
//	private String getTemplateID(TemplateInfo tempInfo) {
//		TemplateParaVO paraVo = new TemplateParaVO();
//		paraVo.setTemplateType(ITemplateStyle.queryTemplate);
//		paraVo.setPk_orgUnit(tempInfo.getPk_Org());
//		paraVo.setOrgType(tempInfo.getOrgType());
//		paraVo.setFunNode(tempInfo.getFunNode());
//		paraVo.setOperator(tempInfo.getUserid());
//		paraVo.setBusiType(tempInfo.getBusiType());
//		paraVo.setNodeKey(tempInfo.getNodekey());
//		IPFTemplate pftemplate = (IPFTemplate) NCLocator.getInstance().lookup(
//				IPFTemplate.class.getName());
//		String templateid = null;
//		try {
//			templateid = pftemplate.getTemplateId(paraVo);
//		} 
//		catch (BusinessException e) {
//			//出现异常仍然可以继续
//			Logger.error("exception when find the template id ", e);
//		}
//		return templateid;
//	}
	
	/**
	 * @param dbName
	 * @param StorageClassName
	 * @param node
	 * @param obj
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void saveQueryObject(String dbName, String StorageClassName, ObjectNode node, BizObject obj)
		throws BusinessException {
		Logger.debug("开始实际的查询模板数据保存:");
		try {
			// 先执行存储操作
			ObjectStorage storage = null;
			if (dbName == null || dbName.length() == 0) {
				storage = (ObjectStorage) Class.forName(StorageClassName).newInstance();
			} else {
				Constructor constructor = Class.forName(StorageClassName).getConstructor(
						new Class[] { String.class });
				storage = (ObjectStorage) constructor.newInstance(new Object[] { dbName });
			}
			/*
			 * 因为在数据库存储对象的时候把对象的属性存储在外面的字段里（ID, displayname, guid,kind)所以在对象BizObject
			 * 里对象Node就是transeant 因此在对象序列化到后端的时候，就会丢失了其节点的指针引用，因此在此进行强制性的恢复。
			 */
			// 执行实际的物理数据库存储操作(插入或者更新)
			storage.saveObject(node, obj);
//		
//			// V502+ 发消息通知集群节点
//			boolean bSingle = ServerConfiguration.getServerConfiguration().isSingle();
//			// Logger.debug("是否集群: " + !bSingle);
//			if (!bSingle) {
//				// 判空, modified by guowl
//				if (StringUtil.isEmptyWithTrim(node.getID()) || StringUtil.isEmptyWithTrim(dbName)
//						|| StringUtil.isEmptyWithTrim(StorageClassName)) {
//					Logger.error("[发送集群消息]：" + node.getID() + "," + dbName + "," + StorageClassName);
//				} else {
//					// 发消息的服务
//					ClusterSender service = (ClusterSender) NCLocator.getInstance().lookup(
//							ClusterSender.class.getName());
//					// 消息主题————过滤使用
//					ClusterMessage msg = new TextClusterMessage("DDC_ObjectNode_NotifyMessage");
//					// 消息头————保存主要的信息
//					msg.addHeader(new ClusterMessageHeader("ID", node.getID()));
//					msg.addHeader(new ClusterMessageHeader("dsName", dbName));
//					msg.addHeader(new ClusterMessageHeader("storageKey", StorageClassName));
//					service.sendMessage(msg);
//				}
//			}
		} catch (Exception e) {
			Logger.error("查询模板-->具体保存动作>>>" + e);
		}
	}
	
	private static IBusinessEntity getBEOfAttribute(IAttribute attribute) {
		IBusinessEntity abe = null;
		if (MDUtil.isRefType(attribute.getDataType())) {
			IRefType ref = (IRefType) attribute.getDataType();
			if (MDUtil.isMDBean(ref.getRefType())) {
				abe = (IBusinessEntity) ref.getRefType();
			}
		} else if (MDUtil.isCollectionType(attribute.getDataType())) {
			ICollectionType col = (ICollectionType) attribute.getDataType();
			if (MDUtil.isMDBean(col.getElementType())) {
				abe = (IBusinessEntity) col.getElementType();
			}
		} else if (MDUtil.isMDBean(attribute.getDataType())) {
			abe = (IBusinessEntity) attribute.getDataType();
		}
		return abe;
	}
//	private static String getForeignKey(IBusinessEntity abe, String tablename,
//	String referencedField) {
	//// FIXME maybe assign the ENDs
	//List<IForeignKey> fks = abe.getTable().getForeignKeies();
	//for (IForeignKey key : fks) {
	//	if (key.getEndTable().getName().equals(tablename)
	//			&& key.getEndColumn().getName().equals(referencedField))
	//		return key.getStartColumn().getName();
	//}
	//return null;
	//}
	
	/**
	 * 通过角色和所属公司来得到用户
	 * @param role
	 * @param pkCorp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[] getUsersByRole(String role,String pkCorp){
//		UserVO[] uservos = null;
//		try {
//			IRoleManageQuery myRoleManageQuery = NCLocator.getInstance().lookup(IRoleManageQuery.class);
//			uservos = myRoleManageQuery.getUsers(role, pkCorp);
//		} catch (Exception e) {
//			Logger.error(e.getMessage(), e);
//		}
//		OrganizeUnit[] orgunits = OrganizeUnit.fromUserVOs(uservos);
//		if (orgunits != null){
//			Arrays.sort(orgunits, new OrganizeUnitComparator());
//			return convertObject2StringArray(orgunits);
//		}
//		else 
//			return null;
		return null;
	}
	
	private String[] convertObject2StringArray(OrganizeUnit[] orgunits){
//		String[] result = new String[orgunits.length];
//		for(int i=0; i < orgunits.length; i++){
//				result[i] = orgunits[i].getPk() + ",";
//				result[i] += ",";
//				result[i] += orgunits[i].getPkCorp() + ",";
//				result[i] += orgunits[i].getCode() + ",";
//				result[i] += orgunits[i].getName() + ",";
//				result[i] += orgunits[i].getOrgUnitType() + "";
//		}
//		return result;
		return null;
	}
	
//	/**
//	 * @param pkCorp
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public String[] getRoleUsersByCorpPk(String pkCorp){
//		
//		OrganizeUnit[] organizeUnit = ReceiverRefImpl.getInstance().getLocalUserVOsByPkcorp(pkCorp);
//		if (organizeUnit != null){
//			Arrays.sort(organizeUnit, new OrganizeUnitComparator());
//			return convertObject2StringArray(organizeUnit);
//		}
//		else 
//			return null;
//	}
}
