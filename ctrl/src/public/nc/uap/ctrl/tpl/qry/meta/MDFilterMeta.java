package nc.uap.ctrl.tpl.qry.meta;

import java.util.List;

import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.IBusinessEntity;
import nc.md.model.IForeignKey;
import nc.md.model.MetaDataException;
import nc.md.model.type.ICollectionType;
import nc.md.model.type.IEnumType;
import nc.md.model.type.IRefType;
import nc.md.model.type.IType;
import nc.md.util.MDUtil;
import nc.uap.ctrl.tpl.qry.base.MDTemplateUtil;
import nc.uap.ctrl.tpl.qry.base.MDType2QTTypeMapping;
import nc.uap.ctrl.tpl.qry.base.QTMDUtil;

public class MDFilterMeta extends FilterMeta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String beanID;
	
	private String attributePath;
	
	private transient String tableAlias;// 表别名，外部注入

	private transient IBean bean;
	
	private transient IAttribute attribute;
	
	public MDFilterMeta(String beanid, String attributePath) {
		super();
		this.beanID = beanid;
		this.attributePath = attributePath;
		init();
	}
	private void init() {
		// 不再将fieldcode设为列名，避免name和pk_org.name这两个查询条件的fieldcode相同导致后续无法区分
		setFieldCode(attributePath);
		setFieldName(getPathName());
		
		initDataTypeRelatedInfo();
		setCondition(true);
		setDefault(false);
		setRequired(false);
		setFixCondition(false);
		setOperators(MDTemplateUtil.getOperatorsByType(getDataType(),getReturnType()));
	}

	public IBean getBean() {
		if(bean == null)
		{
			bean = getBeanByID(beanID);
		}
		return bean;
	}

	private static IBean getBeanByID(String beanID) {
		IBean bean = null;
		try {
			bean = MDBaseQueryFacade.getInstance().getBeanByID(beanID);
		} catch (MetaDataException e) {
			throw new RuntimeException("根据ID获取Bean失败. ID=" + beanID);
		}
		return bean;
	}
	
	public IAttribute getAttribute() {
		if (attribute == null) {
			attribute = getBean().getAttributeByPath(attributePath);
		}
		return attribute;
	}
	
	/**
	 * 返回数据权限operation编码，没有则返回null
	 */
	public String getDataPowerOperation() {
		if (getAttribute() == null) return null;
		IAttribute attr = getAttribute();
		// 二级编码名称属性返回其父属性的使用权信息
		if(is2Or3LevelCodeNameAttr()){
			if(is2LevelAttr(getAttributePath())){
				String[] paths = getAttributePath().split("\\.");
				String parentAttrPath = paths[0];
				attr = getBean().getAttributeByPath(parentAttrPath);
			}else{
				String[] paths = getAttributePath().split("\\.");
				String subEntityAttrName = paths[0];
				IAttribute subBeanAttr = getBean().getAttributeByPath(subEntityAttrName);
				if(MDUtil.isCollectionType(subBeanAttr.getDataType())){
					String subBeanID = ((nc.md.model.impl.Attribute)subBeanAttr).getDataTypeID();
					IBean bean = getBeanByID(subBeanID);
					attr = bean.getAttributeByPath(paths[1]);
				}
			}
		}
		return attr.hasAccessPower() ? attr.getAccessPowerGroup() : null;
	}
	
	private boolean is2Or3LevelCodeNameAttr(){
		return is2Or3LevelAttr(getAttributePath()) && (isCodeAttr() || isNameAttr());
	}
	
	private boolean is2Or3LevelAttr(String attrpath){
		int length = attrpath.split("\\.").length;
		return length == 2 || length == 3;
	}

	private boolean is2LevelAttr(String attrpath) {
		return attrpath.split("\\.").length == 2;
	}
	
	/**
	 * 是否有表别名
	 */
	public boolean hasTableAlias() {
		return tableAlias != null;
	}
	
	/**
	 * 返回表别名
	 */
	public String getTableAlias() {
		return tableAlias;
	}
	
	/**
	 * 设置表别名
	 */
	public void setTableAlias(String alias) {
		this.tableAlias = alias;
	}
	
	/**
	 * 根据属性，获取值编辑器描述符。 
	 * @param attr
	 * @return
	 */
	private void initDataTypeRelatedInfo() {
		
		setDataType(MDType2QTTypeMapping.getQTType(getAttribute().getDataType().getTypeType()));
		String valueEditorDescription = null;
		int returntype = IQueryConstants.RETURNPK;
		int disptype = IQueryConstants.DISPNAME;
		if(MDUtil.isRefType(getAttribute().getDataType())|| MDUtil.isMDBean(getAttribute().getDataType()))
		{
			valueEditorDescription = getAttribute().getRefModelName();
		}
		else if(/*isNameAttr()||*/isCodeAttr())// 后来又决定不将名称属性展示为参照了
		{
			IBusinessEntity be = (IBusinessEntity) getAttribute().getContainer();
			if(getBean().equals(be))
			{
				valueEditorDescription = getBean().getDefaultRefModelName();
			}
			else
			{
				String parentattrpath = attributePath.substring(0,attributePath.lastIndexOf("."));
				IAttribute attr = getBean().getAttributeByPath(parentattrpath);
				valueEditorDescription = attr.getRefModelName();
			}
			returntype = IQueryConstants.RETURNCODE;
			disptype = IQueryConstants.DISPCODE;
			setIfAutoCheck(false);
			setDataType(IQueryConstants.UFREF);
		}
		else if (MDUtil.isEnumType(getAttribute().getDataType()))
		{
			//发明了一种新的描述方式，比如IM,xxx-xxx-xxx-xxx ， xxx-xxx-xxx-xxx表示EnumType的ID
			//在真正创建编辑器时解析出来这个ID，通过元数据获取该枚举类型的取值
			String combo_desc = null;
			IEnumType type = (IEnumType) getAttribute().getDataType();
			if (type.getReturnType().getTypeType()==IType.TYPE_Integer)
			{
				combo_desc = IQueryConstants.COMBO_INTEGER_META + ","+type.getID();//"IM";
			}
			else 
			{
				combo_desc = IQueryConstants.COMBO_STRING_META + ","+type.getID(); //"SM";
			}
			valueEditorDescription = combo_desc;
			
		}
		setValueEditorDescription(valueEditorDescription);
		setReturnType(returntype);
		setDispType(disptype);
	}
	private boolean isNameAttr() {
		return MDTemplateUtil.isNameAttribute(getAttribute());
	}
	private boolean isCodeAttr() {
		return MDTemplateUtil.isCodeAttribute(getAttribute());
	}

	/**
	 * 计算path的显示名称. 比如把 cust.custname的显示名称为 客户.客户名称
	 * @param bean
	 * @param path
	 * @return
	 */
	private String getPathName() {
		IBean bean = getBean();
		String path = attributePath;
		String[] ss = path.split("\\.");
		StringBuffer buf = new StringBuffer();
		String temppath = "";
		for(int i=0;i<ss.length;i++,temppath=temppath+".")
		{
			temppath = temppath+ss[i];
			IAttribute attr = bean.getAttributeByPath(temppath);
			buf.append(attr.getDisplayName()).append(".");
		}
		buf.deleteCharAt(buf.length()-1);
		return buf.toString();
		
	}
	
	@Override
	public String getInstrumentedSql(String basicSql) {
		
		if(basicSql==null||basicSql.trim().length()==0) return basicSql;
		
		// 1级扩展属性
		if(is1LevelAttr(attributePath)&&QTMDUtil.isExtAttr(getAttribute())) {
			return doInstrument4ExtAttr(getAttribute(),basicSql);
		}
		
		String[] ss = attributePath.split("\\.");
		String result = basicSql;
		
		for(int i=ss.length-2;i>=0;i--)
		{
			String childPath = concatenatString(ss,0,i+2,".");
			IAttribute childAttr = getBean().getAttributeByPath(childPath);
			if(QTMDUtil.isExtAttr(childAttr)) {
				result = doInstrument4ExtAttr(childAttr,result);
			}
			String tmpPath = concatenatString(ss,0,i+1,".");
			IAttribute attr = getBean().getAttributeByPath(tmpPath);
			result = doInstrument(attr,result);
			if(QTMDUtil.isExtAttr(attr)) {
				result = doInstrument4ExtAttr(attr,result);
			}
		}
		return result;
	}
	
	private String doInstrument4ExtAttr(IAttribute attr, String basicSql) {
		IBean bean = attr.getOwnerBean();
		String pk = bean.getPrimaryKey().getPKColumn().getName();
		return pk + " in (select " + pk + " from " + attr.getTable().getName()
				+ " where " + basicSql + ")";
	}

	private boolean is1LevelAttr(String attrpath) {
		return !attrpath.contains(".");
	}
	
	private String doInstrument(IAttribute attr, String basicSql) {
		if(MDUtil.isCollectionType(attr.getDataType())) //类似订单-订单明细的情况
		{
			
			StringBuffer buf = new StringBuffer();
			IBusinessEntity pbe = (IBusinessEntity) attr.getContainer();
			IBusinessEntity be = getBEOfAttribute(attr);
			String pkInParentBE = pbe.getPrimaryKey().getPKColumn().getName();
			String parentBEPkInChildBE = getForeignKey(be, pbe.getTable().getName(),pkInParentBE);	
			buf.append(pkInParentBE)
			.append(" in ").append("(select ")
			.append(parentBEPkInChildBE)
			.append(" from ")
			.append(be.getTable().getName())
			.append(" where ")
			.append(basicSql)
			.append(")");
			String result = buf.toString();
			return result;			
			
		}
		else
		{
			StringBuffer buf = new StringBuffer();
			IBusinessEntity be = getBEOfAttribute(attr);
			buf.append(attr.getColumn().getName())
			.append(" in ").append("(select ")
			.append(be.getPrimaryKey().getPKColumn().getName())
			.append(" from ")
			.append(be.getTable().getName())
			.append(" where ")
			.append(basicSql)
			.append(")");
			String result = buf.toString();
			return result;
		}
	}
	private static String getForeignKey(IBusinessEntity abe, String tablename,
			String referencedField) {
		List<IForeignKey> fks = abe.getTable().getForeignKeies();
		for (IForeignKey key : fks) {
			if (key.getEndTable().getName().equals(tablename)
					&& key.getEndColumn().getName().equals(referencedField))
				return key.getStartColumn().getName();
		}
		return null;
	}
	public static IBusinessEntity getBEOfAttribute(IAttribute attribute) {
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
	/**
	 * 把字符串数组ss中从start到end之间的元素用joinString连接起来.
	 * [start,end)前开后闭。
	 * 
	 * @param ss
	 * @param start
	 * @param end
	 * @param joinString
	 * @return
	 */
	private String concatenatString(String[] ss, int start, int end, String joinString)
	{
		StringBuffer buf = new StringBuffer();
		
		for (int i = start; i < end; i++) {
			if(i!=start)
			{
				buf.append(joinString);
			}
			buf.append(ss[i]);
		}
		return buf.toString();
	}
	public String getAttributePath()
	{
		return attributePath;
	}
	
	public String getSQLFieldCode() {
		if(hasTableAlias()){
			return getTableAlias() + "." + getColumnName();
		}
	    return getColumnName();
	}
	
	private String getColumnName() {
		if(getAttribute().isCalculation()) {
			return getAttribute().getName();
		}
		String name = getAttribute().getColumn().getName();
		// 特殊处理多语属性
		return isMultiLangName() ? name + getLangSeq() : name;
	}
	
	/**
	 * 适配QueryConditionVO的构造方法
	 */
	public MDFilterMeta(QueryConditionVO vo, String beanID,
			String attrpath, boolean isMultiTable) {
		super();
		this.beanID = beanID;
		this.attributePath = attrpath;
		init(vo, isMultiTable);
	}
	
	/**
	 * 自定义属性对应的MDFilterMeta即使是数值型也认为它不是<br>
	 * 因为自定义属性在数据库中存储的始终是字符串
	 *
	 * @see nc.ui.querytemplate.meta.FilterMeta#isNumberType()
	 */
	public boolean isNumberType() {
		return super.isNumberType(); 
//		&& (!isUserDef());
	}
}