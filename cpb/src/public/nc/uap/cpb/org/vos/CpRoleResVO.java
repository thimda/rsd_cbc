/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.cpb.org.vos;
	
import nc.vo.pub.*;
import nc.vo.pub.lang.*;
import java.util.HashMap;
import java.util.Map;

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
public class CpRoleResVO extends SuperVO {
	private java.lang.String pk_role;
	private java.lang.String pk_roleres;
	private java.lang.String pk_resource;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_ROLE = "pk_role";
	public static final String PK_ROLERES = "pk_roleres";
	public static final String PK_RESOURCE = "pk_resource";
			
	/**
	 * ����pk_role��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_role () {
		return pk_role;
	}   
	/**
	 * ����pk_role��Setter����.
	 * ��������:
	 * @param newPk_role java.lang.String
	 */
	public void setPk_role (java.lang.String newPk_role ) {
	 	this.pk_role = newPk_role;
	} 	  
	/**
	 * ����pk_roleres��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_roleres () {
		return pk_roleres;
	}   
	/**
	 * ����pk_roleres��Setter����.
	 * ��������:
	 * @param newPk_roleres java.lang.String
	 */
	public void setPk_roleres (java.lang.String newPk_roleres ) {
	 	this.pk_roleres = newPk_roleres;
	} 	  
	/**
	 * ����pk_resource��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_resource () {
		return pk_resource;
	}   
	/**
	 * ����pk_resource��Setter����.
	 * ��������:
	 * @param newPk_resource java.lang.String
	 */
	public void setPk_resource (java.lang.String newPk_resource ) {
	 	this.pk_resource = newPk_resource;
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
		return "pk_role";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_roleres";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_roleres";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_roleres";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public CpRoleResVO() {
		super();	
	}    
} 

