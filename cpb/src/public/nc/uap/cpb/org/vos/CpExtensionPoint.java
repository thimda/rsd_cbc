/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.cpb.org.vos;
	
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
public class CpExtensionPoint extends SuperVO {
	private java.lang.String classname;
	private java.lang.String pk_extpoint;
	private java.lang.String point;
	private java.lang.String title;
	private java.lang.Integer dr;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String CLASSNAME = "classname";
	public static final String PK_EXTPOINT = "pk_extpoint";
	public static final String POINT = "point";
	public static final String TITLE = "title";
			
	/**
	 * ����classname��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getClassname () {
		return classname;
	}   
	/**
	 * ����classname��Setter����.
	 * ��������:
	 * @param newClassname java.lang.String
	 */
	public void setClassname (java.lang.String newClassname ) {
	 	this.classname = newClassname;
	} 	  
	/**
	 * ����pk_extpoint��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_extpoint () {
		return pk_extpoint;
	}   
	/**
	 * ����pk_extpoint��Setter����.
	 * ��������:
	 * @param newPk_extpoint java.lang.String
	 */
	public void setPk_extpoint (java.lang.String newPk_extpoint ) {
	 	this.pk_extpoint = newPk_extpoint;
	} 	  
	/**
	 * ����point��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPoint () {
		return point;
	}   
	/**
	 * ����point��Setter����.
	 * ��������:
	 * @param newPoint java.lang.String
	 */
	public void setPoint (java.lang.String newPoint ) {
	 	this.point = newPoint;
	} 	  
	/**
	 * ����title��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTitle () {
		return title;
	}   
	/**
	 * ����title��Setter����.
	 * ��������:
	 * @param newTitle java.lang.String
	 */
	public void setTitle (java.lang.String newTitle ) {
	 	this.title = newTitle;
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
	  return "pk_extpoint";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_extpoint";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_extpoint";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public CpExtensionPoint() {
		super();	
	}    
} 

