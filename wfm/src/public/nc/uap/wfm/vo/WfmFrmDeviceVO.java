/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.wfm.vo;
	
import nc.vo.pub.SuperVO;

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
	 * ����isnotdisp��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsnotdisp () {
		return isnotdisp;
	}   
	/**
	 * ����isnotdisp��Setter����.
	 * ��������:
	 * @param newIsnotdisp nc.vo.pub.lang.UFBoolean
	 */
	public void setIsnotdisp (nc.vo.pub.lang.UFBoolean newIsnotdisp ) {
	 	this.isnotdisp = newIsnotdisp;
	} 	  
	/**
	 * ����pk_device��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_device () {
		return pk_device;
	}   
	/**
	 * ����pk_device��Setter����.
	 * ��������:
	 * @param newPk_device java.lang.String
	 */
	public void setPk_device (java.lang.String newPk_device ) {
	 	this.pk_device = newPk_device;
	} 	  
	/**
	 * ����pk_frmdevice��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_frmdevice () {
		return pk_frmdevice;
	}   
	/**
	 * ����pk_frmdevice��Setter����.
	 * ��������:
	 * @param newPk_frmdevice java.lang.String
	 */
	public void setPk_frmdevice (java.lang.String newPk_frmdevice ) {
	 	this.pk_frmdevice = newPk_frmdevice;
	} 	  
	/**
	 * ����pk_frmitm��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_frmitm () {
		return pk_frmitm;
	}   
	/**
	 * ����pk_frmitm��Setter����.
	 * ��������:
	 * @param newPk_frmitm java.lang.String
	 */
	public void setPk_frmitm (java.lang.String newPk_frmitm ) {
	 	this.pk_frmitm = newPk_frmitm;
	} 	  
	/**
	 * ����pk_prodef��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_prodef () {
		return pk_prodef;
	}   
	/**
	 * ����pk_prodef��Setter����.
	 * ��������:
	 * @param newPk_prodef java.lang.String
	 */
	public void setPk_prodef (java.lang.String newPk_prodef ) {
	 	this.pk_prodef = newPk_prodef;
	} 	  
	/**
	 * ����port_id��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPort_id () {
		return port_id;
	}   
	/**
	 * ����port_id��Setter����.
	 * ��������:
	 * @param newPort_id java.lang.String
	 */
	public void setPort_id (java.lang.String newPort_id ) {
	 	this.port_id = newPort_id;
	} 	  
	/**
	 * ����prodef_id��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getProdef_id () {
		return prodef_id;
	}   
	/**
	 * ����prodef_id��Setter����.
	 * ��������:
	 * @param newProdef_id java.lang.String
	 */
	public void setProdef_id (java.lang.String newProdef_id ) {
	 	this.prodef_id = newProdef_id;
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
	  return "pk_frmdevice";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_frmdevice";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_frmdevice";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public WfmFrmDeviceVO() {
		super();	
	}    
} 

