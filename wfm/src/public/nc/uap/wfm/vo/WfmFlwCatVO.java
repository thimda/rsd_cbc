/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.wfm.vo;
	
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
public class WfmFlwCatVO extends SuperVO {
	private java.lang.String pk_flwcat;
	private java.lang.String catname;
	private java.lang.String catcode;
	private java.lang.String serverclass;
	private java.lang.Integer dr ;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_FLWCAT = "pk_flwcat";
	public static final String CATNAME = "catname";
	public static final String CATCODE = "catcode";
	public static final String SERVERCLASS = "serverclass";
			
	/**
	 * 属性pk_flwcat的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_flwcat () {
		return pk_flwcat;
	}   
	/**
	 * 属性pk_flwcat的Setter方法.
	 * 创建日期:
	 * @param newPk_flwcat java.lang.String
	 */
	public void setPk_flwcat (java.lang.String newPk_flwcat ) {
	 	this.pk_flwcat = newPk_flwcat;
	} 	  
	/**
	 * 属性catname的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getCatname () {
		return catname;
	}   
	/**
	 * 属性catname的Setter方法.
	 * 创建日期:
	 * @param newCatname java.lang.String
	 */
	public void setCatname (java.lang.String newCatname ) {
	 	this.catname = newCatname;
	} 	  
	/**
	 * 属性catcode的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getCatcode () {
		return catcode;
	}   
	/**
	 * 属性catcode的Setter方法.
	 * 创建日期:
	 * @param newCatcode java.lang.String
	 */
	public void setCatcode (java.lang.String newCatcode ) {
	 	this.catcode = newCatcode;
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
	  return "pk_flwcat";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_flwcat";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_flwcat";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public WfmFlwCatVO() {
		super();	
	}    
} 


