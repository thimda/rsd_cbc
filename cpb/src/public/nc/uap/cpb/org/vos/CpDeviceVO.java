/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.cpb.org.vos;
	
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
public class CpDeviceVO extends SuperVO {
	private java.lang.String code;
	private java.lang.String memo;
	private java.lang.String name;
	private java.lang.String pk_device;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String CODE = "code";
	public static final String MEMO = "memo";
	public static final String NAME = "name";
	public static final String PK_DEVICE = "pk_device";
			
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
	 * ����memo��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getMemo () {
		return memo;
	}   
	/**
	 * ����memo��Setter����.
	 * ��������:
	 * @param newMemo java.lang.String
	 */
	public void setMemo (java.lang.String newMemo ) {
	 	this.memo = newMemo;
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
	 * ����pk_device��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_device () {
		return pk_device;
	}   
	/**
	 * ����pk_device��Setter����.
	 * ��������:
	 * @param newPk_device java.lang.String
	 */
	public void setPk_device (java.lang.String newPk_device ) {
	 	this.pk_device = newPk_device;
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
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_device";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_device";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_device";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public CpDeviceVO() {
		super();	
	}    
} 

