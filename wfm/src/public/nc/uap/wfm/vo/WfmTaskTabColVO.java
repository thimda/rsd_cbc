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
public class WfmTaskTabColVO extends SuperVO {
	private java.lang.String dispname;
	private java.lang.String orderstr;
	private java.lang.String pk_prodef;
	private java.lang.String pk_tasktabcol;
	private java.lang.String prodef_id;
	private java.lang.String tabctrlvalue;
	private java.lang.String tabfield_id;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String DISPNAME = "dispname";
	public static final String ORDERSTR = "orderstr";
	public static final String PK_PRODEF = "pk_prodef";
	public static final String PK_TASKTABCOL = "pk_tasktabcol";
	public static final String PRODEF_ID = "prodef_id";
	public static final String TABCTRLVALUE = "tabctrlvalue";
	public static final String TABFIELD_ID = "tabfield_id";
			
	/**
	 * ����dispname��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getDispname () {
		return dispname;
	}   
	/**
	 * ����dispname��Setter����.
	 * ��������:
	 * @param newDispname java.lang.String
	 */
	public void setDispname (java.lang.String newDispname ) {
	 	this.dispname = newDispname;
	} 	  
	/**
	 * ����orderstr��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getOrderstr () {
		return orderstr;
	}   
	/**
	 * ����orderstr��Setter����.
	 * ��������:
	 * @param newOrderstr java.lang.String
	 */
	public void setOrderstr (java.lang.String newOrderstr ) {
	 	this.orderstr = newOrderstr;
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
	 * ����pk_tasktabcol��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_tasktabcol () {
		return pk_tasktabcol;
	}   
	/**
	 * ����pk_tasktabcol��Setter����.
	 * ��������:
	 * @param newPk_tasktabcol java.lang.String
	 */
	public void setPk_tasktabcol (java.lang.String newPk_tasktabcol ) {
	 	this.pk_tasktabcol = newPk_tasktabcol;
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
	 * ����tabctrlvalue��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTabctrlvalue () {
		return tabctrlvalue;
	}   
	/**
	 * ����tabctrlvalue��Setter����.
	 * ��������:
	 * @param newTabctrlvalue java.lang.String
	 */
	public void setTabctrlvalue (java.lang.String newTabctrlvalue ) {
	 	this.tabctrlvalue = newTabctrlvalue;
	} 	  
	/**
	 * ����tabfield_id��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTabfield_id () {
		return tabfield_id;
	}   
	/**
	 * ����tabfield_id��Setter����.
	 * ��������:
	 * @param newTabfield_id java.lang.String
	 */
	public void setTabfield_id (java.lang.String newTabfield_id ) {
	 	this.tabfield_id = newTabfield_id;
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
	  return "pk_tasktabcol";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "wfm_tasktabcol";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "wfm_tasktabcol";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public WfmTaskTabColVO() {
		super();	
	}    
} 

