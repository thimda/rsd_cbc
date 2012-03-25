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
public class WfmAttachPurviewVO extends SuperVO {
	private nc.vo.pub.lang.UFBoolean isstartup;
	private java.lang.String pk_attachpurview;
	private java.lang.String pk_prodef;
	private java.lang.String port_id; 
	private java.lang.String prodef_id;
	private java.lang.String taget_id;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String ISSTARTUP = "isstartup";
	public static final String PK_ATTACHPURVIEW = "pk_attachpurview";
	public static final String PK_PRODEF = "pk_prodef";
	public static final String PORT_ID = "port_id";
	public static final String PRODEF_ID = "prodef_id";
	public static final String TAGET_ID = "taget_id";
			
	/**
	 * ����isstartup��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIsstartup () {
		return isstartup;
	}   
	/**
	 * ����isstartup��Setter����.
	 * ��������:
	 * @param newIsstartup nc.vo.pub.lang.UFBoolean
	 */
	public void setIsstartup (nc.vo.pub.lang.UFBoolean newIsstartup ) {
	 	this.isstartup = newIsstartup;
	} 	  
	/**
	 * ����pk_attachpurview��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_attachpurview () {
		return pk_attachpurview;
	}   
	/**
	 * ����pk_attachpurview��Setter����.
	 * ��������:
	 * @param newPk_attachpurview java.lang.String
	 */
	public void setPk_attachpurview (java.lang.String newPk_attachpurview ) {
	 	this.pk_attachpurview = newPk_attachpurview;
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
	 * ����taget_id��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTaget_id () {
		return taget_id;
	}   
	/**
	 * ����taget_id��Setter����.
	 * ��������:
	 * @param newTaget_id java.lang.String
	 */
	public void setTaget_id (java.lang.String newTaget_id ) {
	 	this.taget_id = newTaget_id;
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
	  return "pk_attachpurview";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_attachpurview";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_attachpurview";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public WfmAttachPurviewVO() {
		super();	
	}    
} 

