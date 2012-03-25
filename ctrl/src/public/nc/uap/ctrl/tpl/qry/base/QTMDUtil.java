package nc.uap.ctrl.tpl.qry.base;

import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.IForeignKey;
import nc.md.model.ITable;
import nc.md.util.MDUtil;

/**
 * ��ѯģ��Ԫ���ݹ�����
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-8-5
 */
public class QTMDUtil {

	/**
	 * ����ʵ���Ӧ�ı���
	 */
	public static String getTableName(IBean bean) {
		return bean.getTable().getName();
	}

	/**
	 * ������������ʵ���Ӧ�ı���
	 */
	public static String getTableName(IAttribute attr) {
		return getTableName(getOwnerBean(attr));
	}

	/**
	 * ����ʵ���Ӧ�����������
	 */
	public static String getPKColumnName(IBean bean) {
		return bean.getPrimaryKey().getPKColumn().getName();
	}

	/**
	 * ������������ʵ���Ӧ�����������
	 */
	public static String getPKColumnName(IAttribute attr) {
		return getPKColumnName(getOwnerBean(attr));
	}

	/**
	 * ��������������ʵ��
	 */
	public static IBean getAssociationBean(IAttribute attr) {
		return attr.getAssociation().getEndBean();
	}

	/**
	 * ��������������ʵ���Ӧ�ı���
	 */
	public static String getAssociationTableName(IAttribute attr) {
		return getAssociationBean(attr).getTable().getName();
	}

	/**
	 * ��������������ʵ���Ӧ�����������
	 */
	public static String getAssociationPKColumnName(IAttribute attr) {
		return getAssociationBean(attr).getTable().getPrimaryKeyName();
	}

	/**
	 * ���ؾۺ�����������ʵ����������
	 */
	public static String getAssociationFKColumnName(IAttribute attr) {
		return getFKColumnName(getAssociationBean(attr), getOwnerBean(attr));
	}

	/**
	 * ���ؾۺ�����ʵ��֮�����ʵ���������
	 */
	public static String getFKColumnName(IBean child, IBean parent) {
		ITable parentTable = parent.getTable();
		ITable childTable = child.getTable();
		// ���
		IForeignKey fk = parentTable.getForeignKeieFromSubTable(childTable.getName());
		if (fk == null) return null;
		return fk.getStartColumn().getName();
	}

	/**
	 * ������������ʵ��
	 */
	public static IBean getOwnerBean(IAttribute attr) {
		return attr.getOwnerBean();
	}

	/**
	 * ��������������ʵ���Ӧ������
	 */
	public static String getColumnName(IAttribute attr) {
		return attr.getColumn().getName();
	}

	/**
	 * ����������ע�������Ȩ�޲����ַ���
	 */
	public static String getDataPowerOperation(IAttribute attr) {
		return attr.getAccessPowerGroup();
	}

	/**
	 * ���Զ�Ӧ�����Ƿ�ǿգ������ۺ����Ժͷǿ�Ref����
	 */
	public static boolean isNotNull(IAttribute attr) {
		return isCollection(attr) || (!attr.getColumn().isNullable());
	}

	/**
	 * �����Ƿ��ǲ���ʵ���һ�����ԣ����Ƿ��ǲ���ʵ���ֱ������
	 */
	public static boolean is1LevelAttr(IAttribute attr, IBean bean) {
		return getOwnerBean(attr).getID().equals(bean.getID());
	}

	/**
	 * �����Ƿ���չ����
	 */
	public static boolean isExtAttr(IAttribute attr) {
		ITable table = attr.getTable();// �������Կ��ܷ���null
		if(table == null) return false;
		return !getTableName(attr).equals(table.getName());
	}

	/**
	 * �ж������Ƿ��Ǿۺ�����
	 */
	public static boolean isCollection(IAttribute attr) {
		return MDUtil.isCollectionType(attr.getDataType());
	}

	/**
	 * �ж������Ƿ�����������
	 */
	public static boolean isRef(IAttribute attr) {
		return MDUtil.isRefType(attr.getDataType());
	}
}