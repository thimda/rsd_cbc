/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.dbl.vo;
	
import nc.vo.pub.SuperVO;

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
public class DblRefTypeVO extends SuperVO {
	private java.lang.String name;
	private java.lang.String pk_reftype;
	private java.lang.String serverclass;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String NAME = "name";
	public static final String PK_REFTYPE = "pk_reftype";
	public static final String SERVERCLASS = "serverclass";
			
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
	 * 属性pk_reftype的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_reftype () {
		return pk_reftype;
	}   
	/**
	 * 属性pk_reftype的Setter方法.
	 * 创建日期:
	 * @param newPk_reftype java.lang.String
	 */
	public void setPk_reftype (java.lang.String newPk_reftype ) {
	 	this.pk_reftype = newPk_reftype;
	} 	  
	/**
	 * 属性serverclass的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getServerclass () {
		return serverclass;
	}   
	/**
	 * 属性serverclass的Setter方法.
	 * 创建日期:
	 * @param newServerclass java.lang.String
	 */
	public void setServerclass (java.lang.String newServerclass ) {
	 	this.serverclass = newServerclass;
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
	  return "pk_reftype";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "dbl_reftype";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "dbl_reftype";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public DblRefTypeVO() {
		super();	
	}    
} 


