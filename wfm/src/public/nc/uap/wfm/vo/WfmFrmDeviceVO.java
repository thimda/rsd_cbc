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
public class WfmFrmDeviceVO extends SuperVO {
	private nc.vo.pub.lang.UFBoolean isnotdisp;
	private java.lang.String pk_device;
	private java.lang.String pk_frmdevice;
	private java.lang.String pk_frmitm;
	private java.lang.String pk_prodef;
	private java.lang.String port_id;
	private java.lang.String prodef_id;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String ISNOTDISP = "isnotdisp";
	public static final String PK_DEVICE = "pk_device";
	public static final String PK_FRMDEVICE = "pk_frmdevice";
	public static final String PK_FRMITM = "pk_frmitm";
	public static final String PK_PRODEF = "pk_prodef";
	public static final String PORT_ID = "port_id";
	public static final String PRODEF_ID = "prodef_id";
			
	/**
	 * 属性isnotdisp的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsnotdisp () {
		return isnotdisp;
	}   
	/**
	 * 属性isnotdisp的Setter方法.
	 * 创建日期:
	 * @param newIsnotdisp nc.vo.pub.lang.UFBoolean
	 */
	public void setIsnotdisp (nc.vo.pub.lang.UFBoolean newIsnotdisp ) {
	 	this.isnotdisp = newIsnotdisp;
	} 	  
	/**
	 * 属性pk_device的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_device () {
		return pk_device;
	}   
	/**
	 * 属性pk_device的Setter方法.
	 * 创建日期:
	 * @param newPk_device java.lang.String
	 */
	public void setPk_device (java.lang.String newPk_device ) {
	 	this.pk_device = newPk_device;
	} 	  
	/**
	 * 属性pk_frmdevice的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_frmdevice () {
		return pk_frmdevice;
	}   
	/**
	 * 属性pk_frmdevice的Setter方法.
	 * 创建日期:
	 * @param newPk_frmdevice java.lang.String
	 */
	public void setPk_frmdevice (java.lang.String newPk_frmdevice ) {
	 	this.pk_frmdevice = newPk_frmdevice;
	} 	  
	/**
	 * 属性pk_frmitm的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_frmitm () {
		return pk_frmitm;
	}   
	/**
	 * 属性pk_frmitm的Setter方法.
	 * 创建日期:
	 * @param newPk_frmitm java.lang.String
	 */
	public void setPk_frmitm (java.lang.String newPk_frmitm ) {
	 	this.pk_frmitm = newPk_frmitm;
	} 	  
	/**
	 * 属性pk_prodef的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_prodef () {
		return pk_prodef;
	}   
	/**
	 * 属性pk_prodef的Setter方法.
	 * 创建日期:
	 * @param newPk_prodef java.lang.String
	 */
	public void setPk_prodef (java.lang.String newPk_prodef ) {
	 	this.pk_prodef = newPk_prodef;
	} 	  
	/**
	 * 属性port_id的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPort_id () {
		return port_id;
	}   
	/**
	 * 属性port_id的Setter方法.
	 * 创建日期:
	 * @param newPort_id java.lang.String
	 */
	public void setPort_id (java.lang.String newPort_id ) {
	 	this.port_id = newPort_id;
	} 	  
	/**
	 * 属性prodef_id的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getProdef_id () {
		return prodef_id;
	}   
	/**
	 * 属性prodef_id的Setter方法.
	 * 创建日期:
	 * @param newProdef_id java.lang.String
	 */
	public void setProdef_id (java.lang.String newProdef_id ) {
	 	this.prodef_id = newProdef_id;
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
	  return "pk_frmdevice";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_frmdevice";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_frmdevice";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public WfmFrmDeviceVO() {
		super();	
	}    
} 


