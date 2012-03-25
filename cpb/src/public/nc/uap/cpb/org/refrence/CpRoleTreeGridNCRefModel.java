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
		setRefNodeName("��ɫ");
		reset();
	}
	@Override public void reset() {
		super.reset();
		setFilterRefNodeName(new String[] { "ҵ��Ԫ+����" });
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
		setRootName("��ɫ��");
		m_strRefNodeName = newRefNodeName;
		setClassTableName("cp_rolegroup");
		setClassFatherField("pk_parent");
		setClassChildField("pk_rolegroup");
		setClassFieldCode(new String[] { "groupcode", "groupname", "pk_rolegroup","pk_parent" });
		setClassJoinField("pk_rolegroup");
		// *��������������Ӧ����
		setFieldCode(new String[] { "rolecode", "rolename" });
		setFieldName(new String[] { "��ɫ����", "��ɫ����" });
		setHiddenFieldCode(new String[] { "pk_role" });
		setPkFieldCode("pk_role");
		setRefCodeField("rolecode");
		setRefNameField("rolename");
		setTableName("cp_role");
		setDocJoinField("pk_rolegroup");
		// �ǹ���Ա���ͻ�����ͨҵ���ɫ����
		resetFieldName();
	}

	@Override public String getClassWherePart() {
		return classWherePart;
	}
	@Override public void setClassWherePart(String classWherePart) {
		this.classWherePart = classWherePart;
	}
}
