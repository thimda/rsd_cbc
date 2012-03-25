/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.wfm.vo;
	
import nc.vo.pub.SuperVO;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
public class WfmQueryconditionVO extends SuperVO {
	private java.lang.String pk_querytemplate;
	private java.lang.String code;
	private java.lang.String defautvalue;
	private nc.vo.pub.lang.UFBoolean isconst;
	private nc.vo.pub.lang.UFBoolean iseditable;
	private nc.vo.pub.lang.UFBoolean isvisible;
	private java.lang.String name;
	private java.lang.String operator;
	private java.lang.String options;
	private java.lang.String pk_querycondition;
	private java.lang.String type;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_QUERYTEMPLATE = "pk_querytemplate";
	public static final String CODE = "code";
	public static final String DEFAUTVALUE = "defautvalue";
	public static final String ISCONST = "isconst";
	public static final String ISEDITABLE = "iseditable";
	public static final String ISVISIBLE = "isvisible";
	public static final String NAME = "name";
	public static final String OPERATOR = "operator";
	public static final String OPTIONS = "options";
	public static final String PK_QUERYCONDITION = "pk_querycondition";
	public static final String TYPE = "type";
			
	/**
	 * ����pk_querytemplate��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_querytemplate () {
		return pk_querytemplate;
	}   
	/**
	 * ����pk_querytemplate��Setter����.
	 * ��������:
	 * @param newPk_querytemplate java.lang.String
	 */
	public void setPk_querytemplate (java.lang.String newPk_querytemplate ) {
	 	this.pk_querytemplate = newPk_querytemplate;
	} 	  
	/**
	 * ����code��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getCode () {
		return code;
	}   
	/**
	 * ����code��Setter����.
	 * ��������:
	 * @param newCode java.lang.String
	 */
	public void setCode (java.lang.String newCode ) {
	 	this.code = newCode;
	} 	  
	/**
	 * ����defautvalue��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getDefautvalue () {
		return defautvalue;
	}   
	/**
	 * ����defautvalue��Setter����.
	 * ��������:
	 * @param newDefautvalue java.lang.String
	 */
	public void setDefautvalue (java.lang.String newDefautvalue ) {
	 	this.defautvalue = newDefautvalue;
	} 	  
	/**
	 * ����isconst��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsconst () {
		return isconst;
	}   
	/**
	 * ����isconst��Setter����.
	 * ��������:
	 * @param newIsconst nc.vo.pub.lang.UFBoolean
	 */
	public void setIsconst (nc.vo.pub.lang.UFBoolean newIsconst ) {
	 	this.isconst = newIsconst;
	} 	  
	/**
	 * ����iseditable��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIseditable () {
		return iseditable;
	}   
	/**
	 * ����iseditable��Setter����.
	 * ��������:
	 * @param newIseditable nc.vo.pub.lang.UFBoolean
	 */
	public void setIseditable (nc.vo.pub.lang.UFBoolean newIseditable ) {
	 	this.iseditable = newIseditable;
	} 	  
	/**
	 * ����isvisible��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsvisible () {
		return isvisible;
	}   
	/**
	 * ����isvisible��Setter����.
	 * ��������:
	 * @param newIsvisible nc.vo.pub.lang.UFBoolean
	 */
	public void setIsvisible (nc.vo.pub.lang.UFBoolean newIsvisible ) {
	 	this.isvisible = newIsvisible;
	} 	  
	/**
	 * ����name��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}   
	/**
	 * ����name��Setter����.
	 * ��������:
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	  
	/**
	 * ����operator��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getOperator () {
		return operator;
	}   
	/**
	 * ����operator��Setter����.
	 * ��������:
	 * @param newOperator java.lang.String
	 */
	public void setOperator (java.lang.String newOperator ) {
	 	this.operator = newOperator;
	} 	  
	/**
	 * ����options��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getOptions () {
		return options;
	}   
	/**
	 * ����options��Setter����.
	 * ��������:
	 * @param newOptions java.lang.String
	 */
	public void setOptions (java.lang.String newOptions ) {
	 	this.options = newOptions;
	} 	  
	/**
	 * ����pk_querycondition��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_querycondition () {
		return pk_querycondition;
	}   
	/**
	 * ����pk_querycondition��Setter����.
	 * ��������:
	 * @param newPk_querycondition java.lang.String
	 */
	public void setPk_querycondition (java.lang.String newPk_querycondition ) {
	 	this.pk_querycondition = newPk_querycondition;
	} 	  
	/**
	 * ����type��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getType () {
		return type;
	}   
	/**
	 * ����type��Setter����.
	 * ��������:
	 * @param newType java.lang.String
	 */
	public void setType (java.lang.String newType ) {
	 	this.type = newType;
	} 	  
	/**
	 * ����dr��Getter����.
	 * ��������:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.
	 * ��������:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����ts��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.
	 * ��������:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_querytemplate";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_querycondition";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_querycondition";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_querycondition";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public WfmQueryconditionVO() {
		super();	
	}    
} 

