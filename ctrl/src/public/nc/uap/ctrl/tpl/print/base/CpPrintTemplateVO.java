/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.ctrl.tpl.print.base;
	
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
public class CpPrintTemplateVO extends SuperVO {
	private java.lang.String pk_print_template;
	private java.lang.String pkcorp;
	private java.lang.String modelcode;
	private java.lang.String modelname;
	private java.lang.String nodecode;
	private java.lang.String description;
	private java.lang.String resid;
	private java.lang.String metaclass;
	private java.lang.String pk_file;
	private java.lang.String parentid;
	private java.lang.String nodekey;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_PRINT_TEMPLATE = "pk_print_template";
	public static final String PKCORP = "pkcorp";
	public static final String MODELCODE = "modelcode";
	public static final String MODELNAME = "modelname";
	public static final String NODECODE = "nodecode";
	public static final String DESCRIPTION = "description";
	public static final String RESID = "resid";
	public static final String METACLASS = "metaclass";
	public static final String PK_FILE = "pk_file";
	public static final String PARENTID = "parentid";
	public static final String NODEKEY = "nodekey";
			
	/**
	 * 属性pk_print_template的Getter方法.属性名：系统ID
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_print_template () {
		return pk_print_template;
	}
	
	public java.lang.String getNodekey() {
		return nodekey;
	}
	public void setNodekey(java.lang.String nodekey) {
		this.nodekey = nodekey;
	}
	/**
	 * 属性pk_print_template的Setter方法.属性名：系统ID
	 * 创建日期:
	 * @param newPk_print_template java.lang.String
	 */
	public void setPk_print_template (java.lang.String newPk_print_template ) {
	 	this.pk_print_template = newPk_print_template;
	} 	  
	/**
	 * 属性pkcorp的Getter方法.属性名：单位编码
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPkcorp () {
		return pkcorp;
	}   
	/**
	 * 属性pkcorp的Setter方法.属性名：单位编码
	 * 创建日期:
	 * @param newPkcorp java.lang.String
	 */
	public void setPkcorp (java.lang.String newPkcorp ) {
	 	this.pkcorp = newPkcorp;
	} 	  
	/**
	 * 属性modelcode的Getter方法.属性名：模板编号
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getModelcode () {
		return modelcode;
	}   
	/**
	 * 属性modelcode的Setter方法.属性名：模板编号
	 * 创建日期:
	 * @param newModelcode java.lang.String
	 */
	public void setModelcode (java.lang.String newModelcode ) {
	 	this.modelcode = newModelcode;
	} 	  
	/**
	 * 属性modelname的Getter方法.属性名：模板名称
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getModelname () {
		return modelname;
	}   
	/**
	 * 属性modelname的Setter方法.属性名：模板名称
	 * 创建日期:
	 * @param newModelname java.lang.String
	 */
	public void setModelname (java.lang.String newModelname ) {
	 	this.modelname = newModelname;
	} 	  
	/**
	 * 属性nodecode的Getter方法.属性名：节点编码
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getNodecode () {
		return nodecode;
	}   
	/**
	 * 属性nodecode的Setter方法.属性名：节点编码
	 * 创建日期:
	 * @param newNodecode java.lang.String
	 */
	public void setNodecode (java.lang.String newNodecode ) {
	 	this.nodecode = newNodecode;
	} 	  
	/**
	 * 属性description的Getter方法.属性名：模板描述
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getDescription () {
		return description;
	}   
	/**
	 * 属性description的Setter方法.属性名：模板描述
	 * 创建日期:
	 * @param newDescription java.lang.String
	 */
	public void setDescription (java.lang.String newDescription ) {
	 	this.description = newDescription;
	} 	  
	/**
	 * 属性resid的Getter方法.属性名：资源ID
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getResid () {
		return resid;
	}   
	/**
	 * 属性resid的Setter方法.属性名：资源ID
	 * 创建日期:
	 * @param newResid java.lang.String
	 */
	public void setResid (java.lang.String newResid ) {
	 	this.resid = newResid;
	} 	  
	/**
	 * 属性metaclass的Getter方法.属性名：元数据主实体
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getMetaclass () {
		return metaclass;
	}   
	/**
	 * 属性metaclass的Setter方法.属性名：元数据主实体
	 * 创建日期:
	 * @param newMetaclass java.lang.String
	 */
	public void setMetaclass (java.lang.String newMetaclass ) {
	 	this.metaclass = newMetaclass;
	} 	  
	/**
	 * 属性pk_file的Getter方法.属性名：上传文件编号
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_file () {
		return pk_file;
	}   
	/**
	 * 属性pk_file的Setter方法.属性名：上传文件编号
	 * 创建日期:
	 * @param newPk_file java.lang.String
	 */
	public void setPk_file (java.lang.String newPk_file ) {
	 	this.pk_file = newPk_file;
	} 	  
	/**
	 * 属性parentid的Getter方法.属性名：父模板主键
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getParentid () {
		return parentid;
	}   
	/**
	 * 属性parentid的Setter方法.属性名：父模板主键
	 * 创建日期:
	 * @param newParentid java.lang.String
	 */
	public void setParentid (java.lang.String newParentid ) {
	 	this.parentid = newParentid;
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
	  return "pk_print_template";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_print_template";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_print_template";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public CpPrintTemplateVO() {
		super();	
	}    
} 


