package nc.uap.cpb.org.refrence;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.ui.bd.ref.AbstractRefGridTreeModel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.vo.uap.rbac.constant.IRoleConst;
public class CpRoleTreeGridNCRefModel extends AbstractRefGridTreeModel {
	private String classWherePart = "";
	/**
	 * @see IRoleConst
	 */
	private Integer roleType = null;
	public CpRoleTreeGridNCRefModel() {
		super();
		setRefNodeName("角色");
		reset();
	}
	@Override public void reset() {
		super.reset();
		setFilterRefNodeName(new String[] { "业务单元+集团" });
	}
	String userID = InvocationInfoProxy.getInstance().getUserId();
	String pk_group = InvocationInfoProxy.getInstance().getGroupId();
	@Override public void filterValueChanged(ValueChangedEvent changedValue) {
		String[] pks = (String[]) changedValue.getNewValue();
		if (pks != null && pks.length > 0) {
			String pk_org = pks[0];
			setPk_org(pk_org);
			reloadClassData();
			reloadData();
		}
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public Integer getRoleType() {
		return roleType;
	}
	@Override public void setRefNodeName(String newRefNodeName) {
		setRootName("角色组");
		m_strRefNodeName = newRefNodeName;
		setClassTableName("cp_rolegroup");
		setClassFatherField("pk_parent");
		setClassChildField("pk_rolegroup");
		setClassFieldCode(new String[] { "groupcode", "groupname", "pk_rolegroup","pk_parent" });
		setClassJoinField("pk_rolegroup");
		// *根据需求设置相应参数
		setFieldCode(new String[] { "rolecode", "rolename" });
		setFieldName(new String[] { "角色编码", "角色名称" });
		setHiddenFieldCode(new String[] { "pk_role" });
		setPkFieldCode("pk_role");
		setRefCodeField("rolecode");
		setRefNameField("rolename");
		setTableName("cp_role");
		setDocJoinField("pk_rolegroup");
		// 是管理员类型或者普通业务角色类型
		resetFieldName();
	}

	@Override public String getClassWherePart() {
		return classWherePart;
	}
	@Override public void setClassWherePart(String classWherePart) {
		this.classWherePart = classWherePart;
	}
}
