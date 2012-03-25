package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.vo.pub.SuperVO;

/**
 * 查询模板系统函数VO
 * 
 * @author 刘晨伟
 * @version 6.0
 */
public class SysFuncVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String pk_sysfunc;
	private java.lang.String classname;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_SYSFUNC = "pk_sysfunc";
	public static final String CLASSNAME = "classname";

	/**
	 * 属性pk_sysfunc的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_sysfunc() {
		return pk_sysfunc;
	}

	/**
	 * 属性pk_sysfunc的Setter方法.
	 * 创建日期:
	 * @param newPk_sysfunc java.lang.String
	 */
	public void setPk_sysfunc(java.lang.String newPk_sysfunc) {
		this.pk_sysfunc = newPk_sysfunc;
	}

	/**
	 * 属性classname的Getter方法.
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getClassname() {
		return classname;
	}

	/**
	 * 属性classname的Setter方法.
	 * 创建日期:
	 * @param newClassname java.lang.String
	 */
	public void setClassname(java.lang.String newClassname) {
		this.classname = newClassname;
	}

	/**
	 * 属性dr的Getter方法.
	 * 创建日期:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr() {
		return dr;
	}

	/**
	 * 属性dr的Setter方法.
	 * 创建日期:
	 * @param newDr java.lang.Integer
	 */
	public void setDr(java.lang.Integer newDr) {
		this.dr = newDr;
	}

	/**
	 * 属性ts的Getter方法.
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs() {
		return ts;
	}

	/**
	 * 属性ts的Setter方法.
	 * 创建日期:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(nc.vo.pub.lang.UFDateTime newTs) {
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
		return "pk_sysfunc";
	}

	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "pub_query_sysfunc";
	}

	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "pub_query_sysfunc";
	}

	/**
	 * 按照默认方式创建构造子.
	 *
	 * 创建日期:
	 */
	public SysFuncVO() {
		super();
	}
}