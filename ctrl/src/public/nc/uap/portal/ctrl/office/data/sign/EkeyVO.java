/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.portal.ctrl.office.data.sign;
	
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
public class EkeyVO extends SuperVO {
	private java.lang.String pk_ekey;
	private java.lang.String sn;
	private nc.vo.pub.lang.UFDateTime createtime;
	private java.lang.String createby;
	private java.lang.String owneruser_pk;
	private java.lang.String owneruser_code;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;
	private nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[] ekeyusers;

	public static final String PK_EKEY = "pk_ekey";
	public static final String SN = "sn";
	public static final String CREATETIME = "createtime";
	public static final String CREATEBY = "createby";
	public static final String OWNERUSER_PK = "owneruser_pk";
	public static final String OWNERUSER_CODE = "owneruser_code";
			
	/**
	 * 属性pk_ekey的Getter方法.属性名：主键
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_ekey () {
		return pk_ekey;
	}   
	/**
	 * 属性pk_ekey的Setter方法.属性名：主键
	 * 创建日期:
	 * @param newPk_ekey java.lang.String
	 */
	public void setPk_ekey (java.lang.String newPk_ekey ) {
	 	this.pk_ekey = newPk_ekey;
	} 	  
	/**
	 * 属性sn的Getter方法.属性名：序列号
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getSn () {
		return sn;
	}   
	/**
	 * 属性sn的Setter方法.属性名：序列号
	 * 创建日期:
	 * @param newSn java.lang.String
	 */
	public void setSn (java.lang.String newSn ) {
	 	this.sn = newSn;
	} 	  
	/**
	 * 属性createtime的Getter方法.属性名：注册时间
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getCreatetime () {
		return createtime;
	}   
	/**
	 * 属性createtime的Setter方法.属性名：注册时间
	 * 创建日期:
	 * @param newCreatetime nc.vo.pub.lang.UFDateTime
	 */
	public void setCreatetime (nc.vo.pub.lang.UFDateTime newCreatetime ) {
	 	this.createtime = newCreatetime;
	} 	  
	/**
	 * 属性createby的Getter方法.属性名：创建人
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getCreateby () {
		return createby;
	}   
	/**
	 * 属性createby的Setter方法.属性名：创建人
	 * 创建日期:
	 * @param newCreateby java.lang.String
	 */
	public void setCreateby (java.lang.String newCreateby ) {
	 	this.createby = newCreateby;
	} 	  
	/**
	 * 属性ekeyusers的Getter方法.属性名：Ekey用户
	 * 创建日期:
	 * @return nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[]
	 */
	public nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[] getEkeyusers () {
		return ekeyusers;
	}   
	/**
	 * 属性ekeyusers的Setter方法.属性名：Ekey用户
	 * 创建日期:
	 * @param newEkeyusers nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[]
	 */
	public void setEkeyusers (nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[] newEkeyusers ) {
	 	this.ekeyusers = newEkeyusers;
	} 	  
	/**
	 * 属性owneruser_pk的Getter方法.属性名：所属用户PK
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getOwneruser_pk () {
		return owneruser_pk;
	}   
	/**
	 * 属性owneruser_pk的Setter方法.属性名：所属用户PK
	 * 创建日期:
	 * @param newOwneruser_pk java.lang.String
	 */
	public void setOwneruser_pk (java.lang.String newOwneruser_pk ) {
	 	this.owneruser_pk = newOwneruser_pk;
	} 	  
	/**
	 * 属性owneruser_code的Getter方法.属性名：所属用户
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getOwneruser_code () {
		return owneruser_code;
	}   
	/**
	 * 属性owneruser_code的Setter方法.属性名：所属用户
	 * 创建日期:
	 * @param newOwneruser_code java.lang.String
	 */
	public void setOwneruser_code (java.lang.String newOwneruser_code ) {
	 	this.owneruser_code = newOwneruser_code;
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
	  return "pk_ekey";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "uapcp_ekey";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "uapcp_ekey";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public EkeyVO() {
		super();	
	}    
} 


