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
public class CpRoleVO extends SuperVO {
	public final static Integer ROLETYPE_ADMIN = ManageTypeVO.ADMIN_TYPE_VALUE;
	public final static Integer ROLETYPE_BUSINESS = ManageTypeVO.BUSINESS_TYPE_VALUE;
	//Ĭ�Ͻ�ɫ����
	public final static String DEFAULT_ROLECODE = "everyone";
	//Ĭ�Ͻ�ɫ����
	public final static String DEFAULT_ROLENAME = "Ĭ�Ͻ�ɫ";
	private java.lang.String pk_role;
	private java.lang.String rolename;
	private java.lang.String rolecode;
	private java.lang.String comments;
	private java.lang.String issealed;
	private nc.vo.pub.lang.UFDate datecreated;
	private java.lang.String usercreated;
	private java.lang.String pk_rolegroup;
	private java.lang.String pk_org;
	private java.lang.Integer type;
	private java.lang.Integer dr ;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_ROLE = "pk_role";
	public static final String ROLENAME = "rolename";
	public static final String ROLECODE = "rolecode";
	public static final String COMMENTS = "comments";
	public static final String ISSEALED = "issealed";
	public static final String TYPE = "type";
	public static final String DATECREATED = "datecreated";
	public static final String USERCREATED = "usercreated";
	public static final String PK_ROLEGROUP = "pk_rolegroup";
			
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
	 * ����rolename��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getRolename () {
		return rolename;
	}   
	/**
	 * ����rolename��Setter����.
	 * ��������:
	 * @param newRolename java.lang.String
	 */
	public void setRolename (java.lang.String newRolename ) {
	 	this.rolename = newRolename;
	} 	  
	
	public java.lang.String getPk_org() {
		return pk_org;
	}
	public void setPk_org(java.lang.String pk_org) {
		this.pk_org = pk_org;
	}
	/**
	 * ����rolecode��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getRolecode () {
		return rolecode;
	}   
	/**
	 * ����rolecode��Setter����.
	 * ��������:
	 * @param newRolecode java.lang.String
	 */
	public void setRolecode (java.lang.String newRolecode ) {
	 	this.rolecode = newRolecode;
	} 	  
	/**
	 * ����comments��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getComments () {
		return comments;
	}   
	/**
	 * ����comments��Setter����.
	 * ��������:
	 * @param newComments java.lang.String
	 */
	public void setComments (java.lang.String newComments ) {
	 	this.comments = newComments;
	} 	  
	/**
	 * ����issealed��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getIssealed () {
		return issealed;
	}   
	/**
	 * ����issealed��Setter����.
	 * ��������:
	 * @param newIssealed java.lang.String
	 */
	public void setIssealed (java.lang.String newIssealed ) {
	 	this.issealed = newIssealed;
	} 	  
	/**
	 * ����datecreated��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFDate
	 */
	public nc.vo.pub.lang.UFDate getDatecreated () {
		return datecreated;
	}   
	/**
	 * ����datecreated��Setter����.
	 * ��������:
	 * @param newDatecreated nc.vo.pub.lang.UFDate
	 */
	public void setDatecreated (nc.vo.pub.lang.UFDate newDatecreated ) {
	 	this.datecreated = newDatecreated;
	} 	  
	/**
	 * ����usercreated��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUsercreated () {
		return usercreated;
	}   
	/**
	 * ����usercreated��Setter����.
	 * ��������:
	 * @param newUsercreated java.lang.String
	 */
	public void setUsercreated (java.lang.String newUsercreated ) {
	 	this.usercreated = newUsercreated;
	} 	  
	/**
	 * ����pk_rolegroup��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_rolegroup () {
		return pk_rolegroup;
	}   
	/**
	 * ����type��Getter����.
	 * ��������:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getType () {
		return type;
	}   
	/**
	 * ����type��Setter����.
	 * ��������:
	 * @param newType java.lang.Integer
	 */
	public void setType (java.lang.Integer newType ) {
	 	this.type = newType;
	} 	
	/**
	 * ����pk_rolegroup��Setter����.
	 * ��������:
	 * @param newPk_rolegroup java.lang.String
	 */
	public void setPk_rolegroup (java.lang.String newPk_rolegroup ) {
	 	this.pk_rolegroup = newPk_rolegroup;
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
	  return "pk_role";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_role";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_role";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public CpRoleVO() {
		super();	
	}    
} 

