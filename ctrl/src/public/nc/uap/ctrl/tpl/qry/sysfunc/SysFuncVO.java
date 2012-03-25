package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.vo.pub.SuperVO;

/**
 * ��ѯģ��ϵͳ����VO
 * 
 * @author ����ΰ
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
	 * ����pk_sysfunc��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_sysfunc() {
		return pk_sysfunc;
	}

	/**
	 * ����pk_sysfunc��Setter����.
	 * ��������:
	 * @param newPk_sysfunc java.lang.String
	 */
	public void setPk_sysfunc(java.lang.String newPk_sysfunc) {
		this.pk_sysfunc = newPk_sysfunc;
	}

	/**
	 * ����classname��Getter����.
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getClassname() {
		return classname;
	}

	/**
	 * ����classname��Setter����.
	 * ��������:
	 * @param newClassname java.lang.String
	 */
	public void setClassname(java.lang.String newClassname) {
		this.classname = newClassname;
	}

	/**
	 * ����dr��Getter����.
	 * ��������:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr() {
		return dr;
	}

	/**
	 * ����dr��Setter����.
	 * ��������:
	 * @param newDr java.lang.Integer
	 */
	public void setDr(java.lang.Integer newDr) {
		this.dr = newDr;
	}

	/**
	 * ����ts��Getter����.
	 * ��������:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs() {
		return ts;
	}

	/**
	 * ����ts��Setter����.
	 * ��������:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(nc.vo.pub.lang.UFDateTime newTs) {
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
		return "pk_sysfunc";
	}

	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "pub_query_sysfunc";
	}

	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "pub_query_sysfunc";
	}

	/**
	 * ����Ĭ�Ϸ�ʽ����������.
	 *
	 * ��������:
	 */
	public SysFuncVO() {
		super();
	}
}