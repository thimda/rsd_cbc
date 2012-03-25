package nc.uap.ctrl.tpl.qry.base;

import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.IForeignKey;
import nc.md.model.ITable;
import nc.md.util.MDUtil;

/**
 * 查询模板元数据工具类
 * 
 * @author 刘晨伟
 * 
 * 创建日期：2010-8-5
 */
public class QTMDUtil {

	/**
	 * 返回实体对应的表名
	 */
	public static String getTableName(IBean bean) {
		return bean.getTable().getName();
	}

	/**
	 * 返回属性所属实体对应的表名
	 */
	public static String getTableName(IAttribute attr) {
		return getTableName(getOwnerBean(attr));
	}

	/**
	 * 返回实体对应表的主键列名
	 */
	public static String getPKColumnName(IBean bean) {
		return bean.getPrimaryKey().getPKColumn().getName();
	}

	/**
	 * 返回属性所属实体对应表的主键列名
	 */
	public static String getPKColumnName(IAttribute attr) {
		return getPKColumnName(getOwnerBean(attr));
	}

	/**
	 * 返回属性所关联实体
	 */
	public static IBean getAssociationBean(IAttribute attr) {
		return attr.getAssociation().getEndBean();
	}

	/**
	 * 返回属性所关联实体对应的表名
	 */
	public static String getAssociationTableName(IAttribute attr) {
		return getAssociationBean(attr).getTable().getName();
	}

	/**
	 * 返回属性所关联实体对应表的主键列名
	 */
	public static String getAssociationPKColumnName(IAttribute attr) {
		return getAssociationBean(attr).getTable().getPrimaryKeyName();
	}

	/**
	 * 返回聚合属性所关联实体的外键列名
	 */
	public static String getAssociationFKColumnName(IAttribute attr) {
		return getFKColumnName(getAssociationBean(attr), getOwnerBean(attr));
	}

	/**
	 * 返回聚合主子实体之间的子实体外键列名
	 */
	public static String getFKColumnName(IBean child, IBean parent) {
		ITable parentTable = parent.getTable();
		ITable childTable = child.getTable();
		// 外键
		IForeignKey fk = parentTable.getForeignKeieFromSubTable(childTable.getName());
		if (fk == null) return null;
		return fk.getStartColumn().getName();
	}

	/**
	 * 返回属性所属实体
	 */
	public static IBean getOwnerBean(IAttribute attr) {
		return attr.getOwnerBean();
	}

	/**
	 * 返回属性在所属实体对应的列名
	 */
	public static String getColumnName(IAttribute attr) {
		return attr.getColumn().getName();
	}

	/**
	 * 返回属性上注册的数据权限操作字符串
	 */
	public static String getDataPowerOperation(IAttribute attr) {
		return attr.getAccessPowerGroup();
	}

	/**
	 * 属性对应的列是否非空：包括聚合属性和非空Ref属性
	 */
	public static boolean isNotNull(IAttribute attr) {
		return isCollection(attr) || (!attr.getColumn().isNullable());
	}

	/**
	 * 属性是否是参数实体的一级属性，即是否是参数实体的直接属性
	 */
	public static boolean is1LevelAttr(IAttribute attr, IBean bean) {
		return getOwnerBean(attr).getID().equals(bean.getID());
	}

	/**
	 * 属性是否扩展属性
	 */
	public static boolean isExtAttr(IAttribute attr) {
		ITable table = attr.getTable();// 计算属性可能返回null
		if(table == null) return false;
		return !getTableName(attr).equals(table.getName());
	}

	/**
	 * 判断属性是否是聚合类型
	 */
	public static boolean isCollection(IAttribute attr) {
		return MDUtil.isCollectionType(attr.getDataType());
	}

	/**
	 * 判断属性是否是引用类型
	 */
	public static boolean isRef(IAttribute attr) {
		return MDUtil.isRefType(attr.getDataType());
	}
}