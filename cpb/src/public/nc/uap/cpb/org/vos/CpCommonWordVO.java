/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.uap.cpb.org.vos;
import nc.vo.pub.SuperVO;
/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 * �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:
 * 
 * @author
 * @version NCPrj ??
 */
@SuppressWarnings("serial") public class CpCommonWordVO extends SuperVO {
	/** �û���Χ�� */
	public static final int SCOPE_USER = 0;
	/** ��ɫ��Χ�� */
	public static final int SCOPE_ROLE = 1;
	/** ��֯��Χ�� */
	public static final int SCOPE_ORG = 2;
	/** ���ŷ�Χ�� */
	public static final int SCOPE_GROUP = 3;
	/** ϵͳ��Χ */
	public static final int SCOPE_SYS = 4;
	/** �����ﷶΧ����Ϊϵͳʱ����������ʹ�õ�ͳһֵ */
	public static final String OBJ_SYS = "********************";
	private java.lang.String pk_commonword;
	private java.lang.String contents;
	private java.lang.String pk_obj;
	private java.lang.Integer type;
	private java.lang.Integer showorder;
	private java.lang.Integer dr ;
	private nc.vo.pub.lang.UFDateTime ts;
	public static final String PK_COMMONWORD = "pk_commonword";
	public static final String CONTENTS = "contents";
	public static final String PK_OBJ = "pk_obj";
	public static final String TYPE = "type";
	public static final String SHOWORDER = "showorder";
	/**
	 * ����pk_commonword��Getter����. ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPk_commonword() {
		return pk_commonword;
	}
	/**
	 * ����pk_commonword��Setter����. ��������:
	 * 
	 * @param newPk_commonword
	 *            java.lang.String
	 */
	public void setPk_commonword(java.lang.String newPk_commonword) {
		this.pk_commonword = newPk_commonword;
	}
	/**
	 * ����contents��Getter����. ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getContents() {
		return contents;
	}
	/**
	 * ����contents��Setter����. ��������:
	 * 
	 * @param newContents
	 *            java.lang.String
	 */
	public void setContents(java.lang.String newContents) {
		this.contents = newContents;
	}
	/**
	 * ����pk_obj��Getter����. ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPk_obj() {
		return pk_obj;
	}
	/**
	 * ����pk_obj��Setter����. ��������:
	 * 
	 * @param newPk_obj
	 *            java.lang.String
	 */
	public void setPk_obj(java.lang.String newPk_obj) {
		this.pk_obj = newPk_obj;
	}
	/**
	 * ����type��Getter����. ��������:
	 * 
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getType() {
		return type;
	}
	/**
	 * ����type��Setter����. ��������:
	 * 
	 * @param newType
	 *            java.lang.Integer
	 */
	public void setType(java.lang.Integer newType) {
		this.type = newType;
	}
	/**
	 * ����showorder��Getter����. ��������:
	 * 
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getShoworder() {
		return showorder;
	}
	/**
	 * ����showorder��Setter����. ��������:
	 * 
	 * @param newShoworder
	 *            java.lang.Integer
	 */
	public void setShoworder(java.lang.Integer newShoworder) {
		this.showorder = newShoworder;
	}
	/**
	 * ����dr��Getter����. ��������:
	 * 
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr() {
		return dr;
	}
	/**
	 * ����dr��Setter����. ��������:
	 * 
	 * @param newDr
	 *            java.lang.Integer
	 */
	public void setDr(java.lang.Integer newDr) {
		this.dr = newDr;
	}
	/**
	 * ����ts��Getter����. ��������:
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs() {
		return ts;
	}
	/**
	 * ����ts��Setter����. ��������:
	 * 
	 * @param newTs
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(nc.vo.pub.lang.UFDateTime newTs) {
		this.ts = newTs;
	}
	/**
	 * <p>
	 * ȡ�ø�VO�����ֶ�.
	 * <p>
	 * ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {
		return null;
	}
	/**
	 * <p>
	 * ȡ�ñ�����.
	 * <p>
	 * ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_commonword";
	}
	/**
	 * <p>
	 * ���ر�����.
	 * <p>
	 * ��������:
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cp_commonword";
	}
	/**
	 * <p>
	 * ���ر�����.
	 * <p>
	 * ��������:
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cp_commonword";
	}
	/**
	 * ����Ĭ�Ϸ�ʽ����������.
	 * 
	 * ��������:
	 */
	public CpCommonWordVO() {
		super();
	}
}