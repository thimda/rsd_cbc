package nc.uap.ctrl.tpl.qry.meta;

/**
 * ���շ��ؽ��VO.
 * ��������:(2001-8-3 14:01:26)
 * @author:����
 */
public class RefResultVO extends nc.vo.pub.ValueObject {
	private String refPK;
	private String refCode;
	private String refName;
	private Object refObj;

	private boolean isIncludeSub = false;
/**
 * RefResultVO ������ע��.
 */
public RefResultVO() {
	super();
}
/**
 * ������ֵ�������ʾ����.
 * 
 * ��������:(2001-2-15 14:18:08)
 * @return java.lang.String ������ֵ�������ʾ����.
 */
public String getEntityName() {
	return null;
}
/**
 * ���ض����ʶ,����Ψһ��λ����.
 *
 * ��������:(2001-6-14)
 * @return String
 */
public String getPrimaryKey() {

	return refPK;
}
/**
 * ����refCode��Getter����.
 *
 * ��������:(2001-6-14)
 * @return String
 */
public String getRefCode() {
	return refCode;
}
/**
 * ����refName��Getter����.
 *
 * ��������:(2001-6-14)
 * @return String
 */
public String getRefName() {
	return refName;
}
/**
 * ����refObj��Getter����.
 *
 * ��������:(2001-6-14)
 * @return Object
 */
public Object getRefObj() {
	return refObj;
}
/**
 * ����refPK��Getter����.
 *
 * ��������:(2001-6-14)
 * @return String
 */
public String getRefPK() {
	return refPK;
}
/**
 * �˴����뷽��˵��.
 * ��������:(2004-4-15 9:51:14)
 * @return boolean
 */
public boolean isIncludeSub() {
	return isIncludeSub;
}
/**
 * �˴����뷽��˵��.
 * ��������:(2004-4-15 9:51:14)
 * @param newIsIncludeSub boolean
 */
public void setIsIncludeSub(boolean newIsIncludeSub) {
	isIncludeSub = newIsIncludeSub;
}
/**
 * ���ö����ʶ,����Ψһ��λ����.
 *
 * ��������:(2001-6-14)
 * @param newPK String 
 */
public void setPrimaryKey(String newPK) {

	refPK = newPK;
}
/**
 * ����refCode��setter����.
 *
 * ��������:(2001-6-14)
 * @param newRefCode String
 */
public void setRefCode(String newRefCode) {

	refCode = newRefCode;
}
/**
 * ����refName��setter����.
 *
 * ��������:(2001-6-14)
 * @param newRefName String
 */
public void setRefName(String newRefName) {

	refName = newRefName;
}
/**
 * ����refObj��setter����.
 *
 * ��������:(2001-6-14)
 * @param newRefObj Object
 */
public void setRefObj(Object newRefObj) {

	refObj = newRefObj;
}
/**
 * ����refPK��setter����.
 *
 * ��������:(2001-6-14)
 * @param newRefPK String
 */
public void setRefPK(String newRefPK) {

	refPK = newRefPK;
}
/**
 * ��֤���������֮��������߼���ȷ��.
 * 
 * ��������:(2001-2-15 11:47:35)
 * @exception nc.vo.pub.ValidationException �����֤ʧ��,�׳�
 *     ValidationException,�Դ�����н���.
 */
public void validate() throws nc.vo.pub.ValidationException {}
}
