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
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 * 创建日期:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
public class OfficeFileVO extends SuperVO {
	private java.lang.String pk_file;
	private java.lang.String filetype;
	private java.lang.String doctype;
	private java.lang.String filename;
	private java.lang.String fileurl;
	private java.lang.String displayname;
	private java.lang.String modifyby;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_FILE = "pk_file";
	public static final String FILETYPE = "filetype";
	public static final String DOCTYPE = "doctype";
	public static final String FILENAME = "filename";
	public static final String FILEURL = "fileurl";
	public static final String DISPLAYNAME = "displayname";
	public static final String MODIFYBY = "modifyby";
			
	/**
	 * 属性pk_file的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_file () {
		return pk_file;
	}   
	/**
	 * 属性pk_file的Setter方法.
	 * 创建日期:
	 * @param newPk_file java.lang.String
	 */
	public void setPk_file (java.lang.String newPk_file ) {
	 	this.pk_file = newPk_file;
	} 	  
	/**
	 * 属性filetype的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getFiletype () {
		return filetype;
	}   
	/**
	 * 属性filetype的Setter方法.
	 * 创建日期:
	 * @param newFiletype java.lang.String
	 */
	public void setFiletype (java.lang.String newFiletype ) {
	 	this.filetype = newFiletype;
	} 	  
	/**
	 * 属性doctype的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getDoctype () {
		return doctype;
	}   
	/**
	 * 属性doctype的Setter方法.
	 * 创建日期:
	 * @param newDoctype java.lang.String
	 */
	public void setDoctype (java.lang.String newDoctype ) {
	 	this.doctype = newDoctype;
	} 	  
	/**
	 * 属性filename的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getFilename () {
		return filename;
	}   
	/**
	 * 属性filename的Setter方法.
	 * 创建日期:
	 * @param newFilename java.lang.String
	 */
	public void setFilename (java.lang.String newFilename ) {
	 	this.filename = newFilename;
	} 	  
	/**
	 * 属性fileurl的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getFileurl () {
		return fileurl;
	}   
	/**
	 * 属性fileurl的Setter方法.
	 * 创建日期:
	 * @param newFileurl java.lang.String
	 */
	public void setFileurl (java.lang.String newFileurl ) {
	 	this.fileurl = newFileurl;
	} 	  
	/**
	 * 属性displayname的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getDisplayname () {
		return displayname;
	}   
	/**
	 * 属性displayname的Setter方法.
	 * 创建日期:
	 * @param newDisplayname java.lang.String
	 */
	public void setDisplayname (java.lang.String newDisplayname ) {
	 	this.displayname = newDisplayname;
	} 	  
	/**
	 * 属性modifyby的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getModifyby () {
		return modifyby;
	}   
	/**
	 * 属性modifyby的Setter方法.
	 * 创建日期:
	 * @param newModifyby java.lang.String
	 */
	public void setModifyby (java.lang.String newModifyby ) {
	 	this.modifyby = newModifyby;
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
	  return "pk_file";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "uapcp_officefile";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "uapcp_officefile";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public OfficeFileVO() {
		super();	
	}    
} 


