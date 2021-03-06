/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.cpb.log.datalog;
	
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
public class DataLogVO extends SuperVO {
	private java.lang.String pk_businesslog;
	private java.lang.String detail;
	private java.lang.String clientip;
	private java.lang.String busobjtype;
	private nc.vo.pub.lang.UFDateTime opertime;
	private java.lang.String operate;
	private java.lang.String businessobject;
	private nc.vo.pub.lang.UFBoolean operresult;
	private java.lang.String busobjcode;
	private java.lang.String busobjorganization;
	private java.lang.String logingrop;
	private java.lang.String username;
	private java.lang.String truename;
	private java.lang.String pk_busobj;
	private java.lang.String pk_logingrop;
	private java.lang.String logingropcode;
	private java.lang.String pk_user;
	private java.lang.String sessionid;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_BUSINESSLOG = "pk_businesslog";
	public static final String DETAIL = "detail";
	public static final String CLIENTIP = "clientip";
	public static final String BUSOBJTYPE = "busobjtype";
	public static final String OPERTIME = "opertime";
	public static final String OPERATE = "operate";
	public static final String BUSINESSOBJECT = "businessobject";
	public static final String OPERRESULT = "operresult";
	public static final String BUSOBJCODE = "busobjcode";
	public static final String BUSOBJORGANIZATION = "busobjorganization";
	public static final String LOGINGROP = "logingrop";
	public static final String USERNAME = "username";
	public static final String TRUENAME = "truename";
	public static final String PK_BUSOBJ = "pk_busobj";
	public static final String PK_LOGINGROP = "pk_logingrop";
	public static final String LOGINGROPCODE = "logingropcode";
	public static final String PK_USER = "pk_user";
	public static final String SESSIONID = "sessionid";
			
	/**
	 * 属性pk_businesslog的Getter方法.属性名：主键
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_businesslog () {
		return pk_businesslog;
	}   
	/**
	 * 属性pk_businesslog的Setter方法.属性名：主键
	 * 创建日期:
	 * @param newPk_businesslog java.lang.String
	 */
	public void setPk_businesslog (java.lang.String newPk_businesslog ) {
	 	this.pk_businesslog = newPk_businesslog;
	} 	  
	/**
	 * 属性detail的Getter方法.属性名：详细信息
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getDetail () {
		return detail;
	}   
	/**
	 * 属性detail的Setter方法.属性名：详细信息
	 * 创建日期:
	 * @param newDetail java.lang.String
	 */
	public void setDetail (java.lang.String newDetail ) {
	 	this.detail = newDetail;
	} 	  
	/**
	 * 属性clientip的Getter方法.属性名：客户端IP
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getClientip () {
		return clientip;
	}   
	/**
	 * 属性clientip的Setter方法.属性名：客户端IP
	 * 创建日期:
	 * @param newClientip java.lang.String
	 */
	public void setClientip (java.lang.String newClientip ) {
	 	this.clientip = newClientip;
	} 	  
	/**
	 * 属性busobjtype的Getter方法.属性名：业务对象类型
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getBusobjtype () {
		return busobjtype;
	}   
	/**
	 * 属性busobjtype的Setter方法.属性名：业务对象类型
	 * 创建日期:
	 * @param newBusobjtype java.lang.String
	 */
	public void setBusobjtype (java.lang.String newBusobjtype ) {
	 	this.busobjtype = newBusobjtype;
	} 	  
	/**
	 * 属性opertime的Getter方法.属性名：操作时间
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getOpertime () {
		return opertime;
	}   
	/**
	 * 属性opertime的Setter方法.属性名：操作时间
	 * 创建日期:
	 * @param newOpertime nc.vo.pub.lang.UFDateTime
	 */
	public void setOpertime (nc.vo.pub.lang.UFDateTime newOpertime ) {
	 	this.opertime = newOpertime;
	} 	  
	/**
	 * 属性operate的Getter方法.属性名：操作
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getOperate () {
		return operate;
	}   
	/**
	 * 属性operate的Setter方法.属性名：操作
	 * 创建日期:
	 * @param newOperate java.lang.String
	 */
	public void setOperate (java.lang.String newOperate ) {
	 	this.operate = newOperate;
	} 	  
	/**
	 * 属性businessobject的Getter方法.属性名：业务对象
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getBusinessobject () {
		return businessobject;
	}   
	/**
	 * 属性businessobject的Setter方法.属性名：业务对象
	 * 创建日期:
	 * @param newBusinessobject java.lang.String
	 */
	public void setBusinessobject (java.lang.String newBusinessobject ) {
	 	this.businessobject = newBusinessobject;
	} 	  
	/**
	 * 属性operresult的Getter方法.属性名：操作结果
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getOperresult () {
		return operresult;
	}   
	/**
	 * 属性operresult的Setter方法.属性名：操作结果
	 * 创建日期:
	 * @param newOperresult nc.vo.pub.lang.UFBoolean
	 */
	public void setOperresult (nc.vo.pub.lang.UFBoolean newOperresult ) {
	 	this.operresult = newOperresult;
	} 	  
	/**
	 * 属性busobjcode的Getter方法.属性名：业务对象编码
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getBusobjcode () {
		return busobjcode;
	}   
	/**
	 * 属性busobjcode的Setter方法.属性名：业务对象编码
	 * 创建日期:
	 * @param newBusobjcode java.lang.String
	 */
	public void setBusobjcode (java.lang.String newBusobjcode ) {
	 	this.busobjcode = newBusobjcode;
	} 	  
	/**
	 * 属性busobjorganization的Getter方法.属性名：业务对象组织
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getBusobjorganization () {
		return busobjorganization;
	}   
	/**
	 * 属性busobjorganization的Setter方法.属性名：业务对象组织
	 * 创建日期:
	 * @param newBusobjorganization java.lang.String
	 */
	public void setBusobjorganization (java.lang.String newBusobjorganization ) {
	 	this.busobjorganization = newBusobjorganization;
	} 	  
	/**
	 * 属性logingrop的Getter方法.属性名：登录集团
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getLogingrop () {
		return logingrop;
	}   
	/**
	 * 属性logingrop的Setter方法.属性名：登录集团
	 * 创建日期:
	 * @param newLogingrop java.lang.String
	 */
	public void setLogingrop (java.lang.String newLogingrop ) {
	 	this.logingrop = newLogingrop;
	} 	  
	/**
	 * 属性username的Getter方法.属性名：用户名称
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getUsername () {
		return username;
	}   
	/**
	 * 属性username的Setter方法.属性名：用户名称
	 * 创建日期:
	 * @param newUsername java.lang.String
	 */
	public void setUsername (java.lang.String newUsername ) {
	 	this.username = newUsername;
	} 	  
	/**
	 * 属性truename的Getter方法.属性名：真实姓名
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTruename () {
		return truename;
	}   
	/**
	 * 属性truename的Setter方法.属性名：真实姓名
	 * 创建日期:
	 * @param newTruename java.lang.String
	 */
	public void setTruename (java.lang.String newTruename ) {
	 	this.truename = newTruename;
	} 	  
	/**
	 * 属性pk_busobj的Getter方法.属性名：业务对象主键
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_busobj () {
		return pk_busobj;
	}   
	/**
	 * 属性pk_busobj的Setter方法.属性名：业务对象主键
	 * 创建日期:
	 * @param newPk_busobj java.lang.String
	 */
	public void setPk_busobj (java.lang.String newPk_busobj ) {
	 	this.pk_busobj = newPk_busobj;
	} 	  
	/**
	 * 属性pk_logingrop的Getter方法.属性名：登录集团主键
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_logingrop () {
		return pk_logingrop;
	}   
	/**
	 * 属性pk_logingrop的Setter方法.属性名：登录集团主键
	 * 创建日期:
	 * @param newPk_logingrop java.lang.String
	 */
	public void setPk_logingrop (java.lang.String newPk_logingrop ) {
	 	this.pk_logingrop = newPk_logingrop;
	} 	  
	/**
	 * 属性logingropcode的Getter方法.属性名：登录集团编码
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getLogingropcode () {
		return logingropcode;
	}   
	/**
	 * 属性logingropcode的Setter方法.属性名：登录集团编码
	 * 创建日期:
	 * @param newLogingropcode java.lang.String
	 */
	public void setLogingropcode (java.lang.String newLogingropcode ) {
	 	this.logingropcode = newLogingropcode;
	} 	  
	/**
	 * 属性pk_user的Getter方法.属性名：用户pk
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_user () {
		return pk_user;
	}   
	/**
	 * 属性pk_user的Setter方法.属性名：用户pk
	 * 创建日期:
	 * @param newPk_user java.lang.String
	 */
	public void setPk_user (java.lang.String newPk_user ) {
	 	this.pk_user = newPk_user;
	} 	  
	/**
	 * 属性sessionid的Getter方法.属性名：sessionID
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getSessionid () {
		return sessionid;
	}   
	/**
	 * 属性sessionid的Setter方法.属性名：sessionID
	 * 创建日期:
	 * @param newSessionid java.lang.String
	 */
	public void setSessionid (java.lang.String newSessionid ) {
	 	this.sessionid = newSessionid;
	} 	  
	/**
	 * 属性dr的Getter方法.属性名：dr
	 * 创建日期:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性ts的Getter方法.属性名：ts
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.属性名：ts
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
	  return "pk_businesslog";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cpb_business_log";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cpb_business_log";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public DataLogVO() {
		super();	
	}    
} 


