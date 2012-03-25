package nc.uap.ctrl.tpl.qry;

import java.util.Map;

import nc.uap.lfw.core.cmd.base.FromWhereSQL;

/**
 * From-Where SQL����
 * 
 * 
 * �������ڣ�2010-8-19
 */
public class FromWhereSQLImpl implements FromWhereSQL {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5325555531149190224L;
	
	private String from;
	private String where;

	// Ԫ��������·��_�����_Map
	private Map<String, String> attrpath_alias_map;

	public FromWhereSQLImpl() {
		super();
	}

	public FromWhereSQLImpl(String from, String where) {
		this.from = from;
		this.where = where;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * ����Ԫ��������·���������Զ�Ӧ��ı���
	 * 
	 * @param attrpath
	 *            Ԫ��������·��(�磺dept.code)
	 */
	public final String getTableAliasByAttrpath(String attrpath) {
		if (attrpath_alias_map == null) return null;
		return attrpath_alias_map.get(attrpath);
	}

	/**
	 * ҵ���������ò�Ҫֱ��ʹ�ô�map�������
	 * @see getTableAliasByAttrpath(String attrpath)
	 */
	public Map<String, String> getAttrpath_alias_map() {
		return attrpath_alias_map;
	}

	public void setAttrpath_alias_map(Map<String, String> attrpath_alias_map) {
		this.attrpath_alias_map = attrpath_alias_map;
	}

	@Override
	public String toString() {
		return " FROM " + getFrom() + " WHERE " + getWhere();
	}
}