/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.portal.ctrl.office.data;
	
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
public class OfficeFileUserVO extends SuperVO {
	private java.lang.String pk_fileuser;
	private java.lang.String usercode;
	private java.lang.String pk_file;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_FILEUSER = "pk_fileuser";
	public static final String USERCODE = "usercode";
	public static final String PK_FILE = "pk_file";
			
	/**
	 * ����pk_fileuser��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_fileuser () {
		return pk_fileuser;
	}   
	/**
	 * ����pk_fileuser��Setter����.
	 * ��������:
	 * @param newPk_fileuser java.lang.String
	 */
	public void setPk_fileuser (java.lang.String newPk_fileuser ) {
	 	this.pk_fileuser = newPk_fileuser;
	} 	  
	/**
	 * ����usercode��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUsercode () {
		return usercode;
	}   
	/**
	 * ����usercode��Setter����.
	 * ��������:
	 * @param newUsercode java.lang.String
	 */
	public void setUsercode (java.lang.String newUsercode ) {
	 	this.usercode = newUsercode;
	} 	  
	/**
	 * ����pk_file��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_file () {
		return pk_file;
	}   
	/**
	 * ����pk_file��Setter����.
	 * ��������:
	 * @param newPk_file java.lang.String
	 */
	public void setPk_file (java.lang.String newPk_file ) {
	 	this.pk_file = newPk_file;
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
	  return "pk_fileuser";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "uapcp_officefileuser";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "uapcp_officefileuser";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public OfficeFileUserVO() {
		super();	
	}    
} 

