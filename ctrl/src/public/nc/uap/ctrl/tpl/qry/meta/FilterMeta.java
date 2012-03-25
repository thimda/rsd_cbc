package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;
import java.util.Comparator;

import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.ctrl.tpl.qry.operator.OperatorFactory;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * ��ѯ�Ի�����ʾ�õ�����VO.
 * 
 * @author liujian
 */
public class FilterMeta implements IFilterMeta,Serializable ,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2713896428412550665L;

	private String tableCode; // �����

	private String tableName; // ������

	private String fieldCode; // �ֶα���

	private String fieldName; // �ֶ�����

	private String defaultValue = null;

	private int dispType;

	private int returnType; // ��������,���嶨��� IQueryConstants

	private boolean ifAutoCheck;

	private String valueEditorDescription; // ֵ�༭����������.����ձ���,��-99��ʾ�ǲ���

	private boolean isEditable; // �Ƿ�ɱ༭

	private int dataType; // ��������

	private IOperator[] operators;

	private boolean isFixCondition = false;

	private boolean isRequired = false;

	private boolean isDefault = false;
	
	private boolean isUserDef = false; //�Ƿ��û��Զ�����
	
	private boolean isDataPower = false;//�Ƿ�����Ȩ�޿���
	
	private boolean isCondition = true; //�Ƿ�Ϊ��ѯ����,Ĭ��Ϊtrue,��֮Ϊ�߼�����.@since v5.3
	
	private String instrumentedsql ;
	
	private boolean isOrder = false;
	
//	private boolean isMultiLangName = false;// �Ƿ��Ƕ�������
	
	private transient int langSeq = 0;// �������Ƶ����ֺ�
	
	// �������͵�where������ֻ�����ڴ���ʹ�ã�����־û������ݿ�
	private String where;
	
	private String prerestrict;// Ԥ����������
	private String guideline;// ָ������,Ϊ˫�������
	    
    private boolean isSubIncluded;// �����Ƿ�����¼�
    
    // fields below add for V6 composite ref
    private boolean isSysFuncRefUsed;// �Ƿ�ʹ��ϵͳ��������
    private boolean isAttrRefUsed;// �Ƿ�ʹ�ÿ�����Բ���
    
    private int maxlength;// ��󳤶�
    private int dispSequence;// ��ʾ���
    
    private int limit = 9999;// ��Ŀ����
    
	public FilterMeta() {
		super();
	}

	public int getDataType() {
		return dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public int getLangSeq() {
		return langSeq;
	}
	
	public void setLangSeq(int langSeq) {
		this.langSeq = langSeq;
	}
	
	public String getFieldCode() {
		// ���⴦�������������
		return isMultiLangName() ? fieldCode + getLangSeq() : fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public boolean getIsEditable() {
		return isEditable;
	}

	public String getTableCode() {
		return tableCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setDataType(int newDataType) {
		dataType = newDataType;
	}

	public void setDefaultValue(String newValue) {
		defaultValue = newValue;
	}

	public void setFieldCode(String newCode) {
		fieldCode = newCode;
	}

	public void setFieldName(String newName) {
		fieldName = newName;
	}

	public void setIsEditable(boolean flag) {
		isEditable = flag;
	}

	public void setTableCode(String newCode) {
		tableCode = newCode;
	}

	public void setTableName(String newName) {
		tableName = newName;
	}

	public IOperator[] getOperators() {
		return operators;
	}

	public void setOperators(IOperator[] operators) {
		this.operators = operators;
	}

	public String toString() {
		if(getTableName()!=null&&getTableName().length()>0){
			return getTableName()+"."+getFieldName();
		}
		return getFieldName();
	}

	public String getValueEditorDescription() {
		return valueEditorDescription;
	}

	public void setValueEditorDescription(String valueEditorDescription) {
		this.valueEditorDescription = valueEditorDescription;
	}

	public int getDispType() {
		return dispType;
	}

	public void setDispType(int dispType) {
		this.dispType = dispType;
	}

	public boolean getIfAutoCheck() {
		return ifAutoCheck;
	}

	public void setIfAutoCheck(boolean ifAutoCheck) {
		this.ifAutoCheck = ifAutoCheck;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	
	public boolean isFixCondition() {
		return isFixCondition;
	}

	public void setFixCondition(boolean isFixCondition) {
		this.isFixCondition = isFixCondition;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean isDataPower() {
		return isDataPower;
	}

	public void setDataPower(boolean isDataPower) {
		this.isDataPower = isDataPower;
	}

	public boolean isUserDef() {
		return isUserDef;
	}

	public void setUserDef(boolean isUserDef) {
		this.isUserDef = isUserDef;
	}
	
	
	
	public boolean isCondition() {
		return isCondition;
	}

	public void setCondition(boolean isCondition) {
		this.isCondition = isCondition;
	}

	public String getSQLFieldCode() {
		if (getTableCode() == null || getFieldCode().indexOf(".") != -1||getTableCode().length()==0) {
			return getFieldCode();
		} else {
			return getTableCode() + "." + getFieldCode();
		}
	}

	public boolean isNumberType() {
		boolean result = false;
		if (IQueryConstants.INTEGER == getDataType()
				|| IQueryConstants.DECIMAL == getDataType()) {
			result = true;
		} else if (IQueryConstants.USERCOMBO == getDataType()) {
			if (getValueEditorDescription() != null
					&& getValueEditorDescription().startsWith(IQueryConstants.COMBO_INDEX))
				result = true;
		}

		return result;
	}

	
	@Override
	public Object clone() {
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

//	public IFilterMeta onDoubleClick(TreePath selPath,Object nodeObject,List<FilterMeta> dbthiss) {
//		FilterMeta fm = (FilterMeta)nodeObject;
//		return fm; 
//	}
//	
	

	public String getInstrumentedsql() {
		return instrumentedsql;
	}

	public void setInstrumentedsql(String instrumentedsql) {
		this.instrumentedsql = instrumentedsql;
	}
	
	public String getInstrumentedSql(String basicSql)
	{
		if(basicSql==null||basicSql.trim().length()==0) return basicSql;
		if(getInstrumentedsql()==null||getInstrumentedsql().trim().length()==0)
		{
			return basicSql;
		}
		else
		{
			return StringUtil.replaceAllString(getInstrumentedsql(), "???", basicSql);
		}
	}

	public boolean isOrder() {
		return isOrder;
	}

	public void setOrder(boolean isOrder) {
		this.isOrder = isOrder;
	}

	public boolean isMultiLangName() {
		return getLangSeq() != 0;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
	
	public String getPrerestrict() {
		return prerestrict;
	}
	
	public void setPrerestrict(String prerestrict) {
		this.prerestrict = prerestrict;
	}
	
	public String getGuideline() {
		return guideline;
	}
	
	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}
	
	public boolean isSubIncluded() {
		return isSubIncluded;
	}

	public void setSubIncluded(boolean isSubIncluded) {
		this.isSubIncluded = isSubIncluded;
	}
	
	public boolean isSysFuncRefUsed() {
		return isSysFuncRefUsed;
	}
	
	public void setSysFuncRefUsed(boolean isSysFuncRefUsed) {
		this.isSysFuncRefUsed = isSysFuncRefUsed;
	}
	
	public boolean isAttrRefUsed() {
		return isAttrRefUsed;
	}
	
	public void setAttrRefUsed(boolean isAttrRefUsed) {
		this.isAttrRefUsed = isAttrRefUsed;
	}
	
	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
	
	public int getDispSequence() {
		return dispSequence;
	}

	public void setDispSequence(int dispSequence) {
		this.dispSequence = dispSequence;
	}
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * ����ʾ˳��������
	 */
	public static Comparator<FilterMeta> byDispSequence() {
		return new Comparator<FilterMeta>() {

			@Override
			public int compare(FilterMeta o1, FilterMeta o2) {
				return o1.getDispSequence() - o2.getDispSequence();
			}
		};
	}
	
	/**
	 * ��������Ȩ��operation���룬û���򷵻�null
	 */
	public String getDataPowerOperation() {
		return null;
	}

	/**
	 * ����QueryConditionVO�Ĺ��췽��
	 */
	public FilterMeta(QueryConditionVO vo,boolean isMultiTable) {
		super();
		init(vo,isMultiTable);
	}
	
	protected void init(QueryConditionVO vo,boolean isMultiTable) {
		this.setFieldCode(vo.getFieldCode());
		this.setFieldName(vo.getFieldName());
		this.setOperators(OperatorFactory.getInstance().getOperators(vo.getOperaCode()));
		this.setTableCode(vo.getTableCode());
		// �����������ٴ�������ƣ�û��ʲô����
		if(isMultiTable) {
			// ���⴦������Ķ��﷭��
			if (isEmpty(vo.getTableCode())) {
				this.setTableName(vo.getTableName());
			} else {
				String tableName = getTableNameByCode(vo.getTableCode(), vo.getTableName());
				this.setTableName(tableName);
			}
		}
		this.setIfAutoCheck(vo.getIfAutoCheck()==null?false:vo.getIfAutoCheck().booleanValue());
		this.setIsEditable(vo.getIfImmobility()==null?true:!vo.getIfImmobility().booleanValue());
		this.setDispType(vo.getDispType());
		this.setReturnType(vo.getReturnType());
		this.setValueEditorDescription(vo.getConsultCode());
		this.setDataType(vo.getDataType());
		this.setDefaultValue(vo.getValue());
		this.setFixCondition(vo.getIfImmobility()==null?false:vo.getIfImmobility().booleanValue());
		this.setRequired(vo.getIfMust()==null?false:vo.getIfMust().booleanValue());
		this.setDefault(vo.getIfDefault()==null?false:vo.getIfDefault().booleanValue());
		this.setDataPower(vo.getIfDataPower().booleanValue());
		this.setUserDef(vo.getUserDef());
		this.setCondition(vo.getIsCondition()==null?true:vo.getIsCondition().booleanValue());
		this.setInstrumentedsql(vo.getInstrumentedsql());
		this.setOrder(vo.getIfOrder().booleanValue());
		this.setWhere(vo.getWhere());
		this.setPrerestrict(vo.getPrerestrict());
		this.setGuideline(vo.getGuideline());
		this.setSubIncluded(vo.getIsSubIncluded()==null?true:vo.getIsSubIncluded().booleanValue());
		this.setSysFuncRefUsed(vo.getIsSysFuncRefUsed()==null?false:vo.getIsSysFuncRefUsed().booleanValue());
		this.setAttrRefUsed(vo.getIsAttrRefUsed()==null?false:vo.getIsAttrRefUsed().booleanValue());
		this.setMaxlength(vo.getMaxLength()==null?1000:vo.getMaxLength());
		this.setDispSequence(vo.getDispSequence()==null?1000:vo.getDispSequence());
		this.setLimit(vo.getLimit() == null ? 9999 : vo.getLimit());
	}
	
	/**
	 * ���ݱ����õ���Ӧ�ı�����(����)�����û�н��ж���ע���򷵻ز���tableName
	 */
	private static String getTableNameByCode(String tableCode,String tableName) {
		// ���������ֵ�Ķ����ļ����з���
		return NCLangRes4VoTransl.getNCLangRes().getString("datadict", tableName,"D" + tableCode);
	}
	
	private static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
}