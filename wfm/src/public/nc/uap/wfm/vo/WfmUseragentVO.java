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
public class WfmUseragentVO extends SuperVO {
	private java.lang.String pk_agenter;
	private java.lang.String pk_depts;
	private java.lang.String pk_user;
	private java.lang.String pk_useragent;
	private nc.vo.pub.lang.UFDateTime startdate;
	private nc.vo.pub.lang.UFDateTime stopdate;
	private nc.vo.pub.lang.UFBoolean useflag;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_AGENTER = "pk_agenter";
	public static final String PK_DEPTS = "pk_depts";
	public static final String PK_USER = "pk_user";
	public static final String PK_USERAGENT = "pk_useragent";
	public static final String STARTDATE = "startdate";
	public static final String STOPDATE = "stopdate";
	public static final String USEFLAG = "useflag";
			
	/**
	 * 属性pk_agenter的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_agenter () {
		return pk_agenter;
	}   
	/**
	 * 属性pk_agenter的Setter方法.
	 * 创建日期:
	 * @param newPk_agenter java.lang.String
	 */
	public void setPk_agenter (java.lang.String newPk_agenter ) {
	 	this.pk_agenter = newPk_agenter;
	} 	  
	/**
	 * 属性pk_depts的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_depts () {
		return pk_depts;
	}   
	/**
	 * 属性pk_depts的Setter方法.
	 * 创建日期:
	 * @param newPk_depts java.lang.String
	 */
	public void setPk_depts (java.lang.String newPk_depts ) {
	 	this.pk_depts = newPk_depts;
	} 	  
	/**
	 * 属性pk_user的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_user () {
		return pk_user;
	}   
	/**
	 * 属性pk_user的Setter方法.
	 * 创建日期:
	 * @param newPk_user java.lang.String
	 */
	public void setPk_user (java.lang.String newPk_user ) {
	 	this.pk_user = newPk_user;
	} 	  
	/**
	 * 属性pk_useragent的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_useragent () {
		return pk_useragent;
	}   
	/**
	 * 属性pk_useragent的Setter方法.
	 * 创建日期:
	 * @param newPk_useragent java.lang.String
	 */
	public void setPk_useragent (java.lang.String newPk_useragent ) {
	 	this.pk_useragent = newPk_useragent;
	} 	  
	/**
	 * 属性startdate的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getStartdate () {
		return startdate;
	}   
	/**
	 * 属性startdate的Setter方法.
	 * 创建日期:
	 * @param newStartdate nc.vo.pub.lang.UFDateTime
	 */
	public void setStartdate (nc.vo.pub.lang.UFDateTime newStartdate ) {
	 	this.startdate = newStartdate;
	} 	  
	/**
	 * 属性stopdate的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getStopdate () {
		return stopdate;
	}   
	/**
	 * 属性stopdate的Setter方法.
	 * 创建日期:
	 * @param newStopdate nc.vo.pub.lang.UFDateTime
	 */
	public void setStopdate (nc.vo.pub.lang.UFDateTime newStopdate ) {
	 	this.stopdate = newStopdate;
	} 	  
	/**
	 * 属性useflag的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getUseflag () {
		return useflag;
	}   
	/**
	 * 属性useflag的Setter方法.
	 * 创建日期:
	 * @param newUseflag nc.vo.pub.lang.UFBoolean
	 */
	public void setUseflag (nc.vo.pub.lang.UFBoolean newUseflag ) {
	 	this.useflag = newUseflag;
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
	  return "pk_useragent";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_useragent";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_useragent";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public WfmUseragentVO() {
		super();	
	}    
} 


