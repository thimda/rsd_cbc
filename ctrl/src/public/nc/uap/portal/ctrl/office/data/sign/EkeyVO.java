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
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:
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
	 * ����pk_ekey��Getter����.������������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_ekey () {
		return pk_ekey;
	}   
	/**
	 * ����pk_ekey��Setter����.������������
	 * ��������:
	 * @param newPk_ekey java.lang.String
	 */
	public void setPk_ekey (java.lang.String newPk_ekey ) {
	 	this.pk_ekey = newPk_ekey;
	} 	  
	/**
	 * ����sn��Getter����.�����������к�
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getSn () {
		return sn;
	}   
	/**
	 * ����sn��Setter����.�����������к�
	 * ��������:
	 * @param newSn java.lang.String
	 */
	public void setSn (java.lang.String newSn ) {
	 	this.sn = newSn;
	} 	  
	/**
	 * ����createtime��Getter����.��������ע��ʱ��
	 * ��������:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getCreatetime () {
		return createtime;
	}   
	/**
	 * ����createtime��Setter����.��������ע��ʱ��
	 * ��������:
	 * @param newCreatetime nc.vo.pub.lang.UFDateTime
	 */
	public void setCreatetime (nc.vo.pub.lang.UFDateTime newCreatetime ) {
	 	this.createtime = newCreatetime;
	} 	  
	/**
	 * ����createby��Getter����.��������������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getCreateby () {
		return createby;
	}   
	/**
	 * ����createby��Setter����.��������������
	 * ��������:
	 * @param newCreateby java.lang.String
	 */
	public void setCreateby (java.lang.String newCreateby ) {
	 	this.createby = newCreateby;
	} 	  
	/**
	 * ����ekeyusers��Getter����.��������Ekey�û�
	 * ��������:
	 * @return nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[]
	 */
	public nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[] getEkeyusers () {
		return ekeyusers;
	}   
	/**
	 * ����ekeyusers��Setter����.��������Ekey�û�
	 * ��������:
	 * @param newEkeyusers nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[]
	 */
	public void setEkeyusers (nc.uap.portal.ctrl.office.data.sign.EkeyUserVo[] newEkeyusers ) {
	 	this.ekeyusers = newEkeyusers;
	} 	  
	/**
	 * ����owneruser_pk��Getter����.�������������û�PK
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getOwneruser_pk () {
		return owneruser_pk;
	}   
	/**
	 * ����owneruser_pk��Setter����.�������������û�PK
	 * ��������:
	 * @param newOwneruser_pk java.lang.String
	 */
	public void setOwneruser_pk (java.lang.String newOwneruser_pk ) {
	 	this.owneruser_pk = newOwneruser_pk;
	} 	  
	/**
	 * ����owneruser_code��Getter����.�������������û�
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getOwneruser_code () {
		return owneruser_code;
	}   
	/**
	 * ����owneruser_code��Setter����.�������������û�
	 * ��������:
	 * @param newOwneruser_code java.lang.String
	 */
	public void setOwneruser_code (java.lang.String newOwneruser_code ) {
	 	this.owneruser_code = newOwneruser_code;
	} 	  
	/**
	 * ����dr��Getter����.��������dr
	 * ��������:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.��������dr
	 * ��������:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����ts��Getter����.��������ts
	 * ��������:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.��������ts
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
	  return "pk_ekey";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "uapcp_ekey";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "uapcp_ekey";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public EkeyVO() {
		super();	
	}    
} 

