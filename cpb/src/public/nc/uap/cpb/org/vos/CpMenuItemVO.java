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
public class CpMenuItemVO extends SuperVO {
	private java.lang.String pk_funnode;
	private java.lang.String iconpath;
	private nc.vo.pub.lang.UFBoolean isnotleaf;
	private java.lang.String menuitemdes;
	private java.lang.String code;
	private java.lang.String name;
	private java.lang.String pk_menucategory;
	private java.lang.String pk_menuitem;
	private java.lang.String pk_parent;
	private java.lang.String ordernum;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_FUNNODE = "pk_funnode";
	public static final String ICONPATH = "iconpath";
	public static final String ISNOTLEAF = "isnotleaf";
	public static final String MENUITEMDES = "menuitemdes";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String PK_MENUCATEGORY = "pk_menucategory";
	public static final String PK_MENUITEM = "pk_menuitem";
	public static final String PK_PARENT = "pk_parent";
	public static final String ORDERNUM = "ordernum";
			
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
	 * 属性iconpath的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getIconpath () {
		return iconpath;
	}   
	/**
	 * 属性iconpath的Setter方法.
	 * 创建日期:
	 * @param newIconpath java.lang.String
	 */
	public void setIconpath (java.lang.String newIconpath ) {
	 	this.iconpath = newIconpath;
	} 	  
	/**
	 * 属性isnotleaf的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsnotleaf () {
		return isnotleaf;
	}   
	/**
	 * 属性isnotleaf的Setter方法.
	 * 创建日期:
	 * @param newIsnotleaf nc.vo.pub.lang.UFBoolean
	 */
	public void setIsnotleaf (nc.vo.pub.lang.UFBoolean newIsnotleaf ) {
	 	this.isnotleaf = newIsnotleaf;
	} 	  
	/**
	 * 属性menuitemdes的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getMenuitemdes () {
		return menuitemdes;
	}   
	/**
	 * 属性menuitemdes的Setter方法.
	 * 创建日期:
	 * @param newMenuitemdes java.lang.String
	 */
	public void setMenuitemdes (java.lang.String newMenuitemdes ) {
	 	this.menuitemdes = newMenuitemdes;
	} 	  
	/**
	 * 属性code的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getCode () {
		return code;
	}   
	/**
	 * 属性code的Setter方法.
	 * 创建日期:
	 * @param newCode java.lang.String
	 */
	public void setCode (java.lang.String newCode ) {
	 	this.code = newCode;
	} 	  
	/**
	 * 属性name的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}   
	/**
	 * 属性name的Setter方法.
	 * 创建日期:
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	  
	/**
	 * 属性pk_menucategory的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_menucategory () {
		return pk_menucategory;
	}   
	/**
	 * 属性pk_menucategory的Setter方法.
	 * 创建日期:
	 * @param newPk_menucategory java.lang.String
	 */
	public void setPk_menucategory (java.lang.String newPk_menucategory ) {
	 	this.pk_menucategory = newPk_menucategory;
	} 	  
	/**
	 * 属性pk_menuitem的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_menuitem () {
		return pk_menuitem;
	}   
	/**
	 * 属性pk_menuitem的Setter方法.
	 * 创建日期:
	 * @param newPk_menuitem java.lang.String
	 */
	public void setPk_menuitem (java.lang.String newPk_menuitem ) {
	 	this.pk_menuitem = newPk_menuitem;
	} 	  
	/**
	 * 属性pk_parent的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_parent () {
		return pk_parent;
	}   
	/**
	 * 属性pk_parent的Setter方法.
	 * 创建日期:
	 * @param newPk_parent java.lang.String
	 */
	public void setPk_parent (java.lang.String newPk_parent ) {
	 	this.pk_parent = newPk_parent;
	} 	  
	/**
	 * 属性ordernum的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getOrdernum () {
		return ordernum;
	}   
	/**
	 * 属性ordernum的Setter方法.
	 * 创建日期:
	 * @param newOrdernum java.lang.String
	 */
	public void setOrdernum (java.lang.String newOrdernum ) {
	 	this.ordernum = newOrdernum;
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
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_menuitem";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_menuitem";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_menuitem";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public CpMenuItemVO() {
		super();	
	}    
} 


