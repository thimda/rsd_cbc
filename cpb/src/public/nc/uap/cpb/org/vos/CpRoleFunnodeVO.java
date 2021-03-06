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
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 * 创建日期:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
public class CpRoleFunnodeVO extends SuperVO {
	private java.lang.String pk_role;
	private java.lang.String pk_rolefunnode;
	private java.lang.String pk_funnode;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_ROLE = "pk_role";
	public static final String PK_ROLEFUNNODE = "pk_rolefunnode";
	public static final String PK_FUNNODE = "pk_funnode";
			
	/**
	 * 属性pk_role的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_role () {
		return pk_role;
	}   
	/**
	 * 属性pk_role的Setter方法.
	 * 创建日期:
	 * @param newPk_role java.lang.String
	 */
	public void setPk_role (java.lang.String newPk_role ) {
	 	this.pk_role = newPk_role;
	} 	  
	/**
	 * 属性pk_rolefunnode的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_rolefunnode () {
		return pk_rolefunnode;
	}   
	/**
	 * 属性pk_rolefunnode的Setter方法.
	 * 创建日期:
	 * @param newPk_rolefunnode java.lang.String
	 */
	public void setPk_rolefunnode (java.lang.String newPk_rolefunnode ) {
	 	this.pk_rolefunnode = newPk_rolefunnode;
	} 	  
	/**
	 * 属性pk_funnode的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_funnode () {
		return pk_funnode;
	}   
	/**
	 * 属性pk_funnode的Setter方法.
	 * 创建日期:
	 * @param newPk_funnode java.lang.String
	 */
	public void setPk_funnode (java.lang.String newPk_funnode ) {
	 	this.pk_funnode = newPk_funnode;
	} 	  
	/**
	 * 属性dr的Getter方法.
	 * 创建日期:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.
	 * 创建日期:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性ts的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.
	 * 创建日期:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_role";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_rolefunnode";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_rolefunnode";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_rolefunnode";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public CpRoleFunnodeVO() {
		super();	
	}    
} 


