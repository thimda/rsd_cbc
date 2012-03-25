package nc.uap.cpb.org.refrence;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.uap.rbac.core.rule.sql.ISQLNode;
import nc.uap.rbac.core.rule.sql.SQLNodeFactory;
import nc.uap.rbac.core.sqlprovider.common.GroupAndOrgPartProvider;
import nc.uap.rbac.core.sqlprovider.role.RoleGroupAuthPermWherePartProvider;
import nc.uap.rbac.core.sqlprovider.role.RoleGroupTypeWherePartProvider;
import nc.ui.bd.ref.AbstractRefGridTreeModel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.vo.uap.rbac.constant.IRoleConst;
public class CpRoleGroupTreeGridNCRefModel extends AbstractRefGridTreeModel {
	private String classWherePart = "";
	/**
	 * @see IRoleConst
	 */
	private Integer roleType = null;
	public CpRoleGroupTreeGridNCRefModel() {
		super();
		setRefNodeName("��ɫ");
		reset();
	}
	public void reset() {
		super.reset();
		setFilterRefNodeName(new String[] { "ҵ��Ԫ+����" });
	}
	String userID = InvocationInfoProxy.getInstance().getUserId();
	String pk_group = InvocationInfoProxy.getInstance().getGroupId();
	public void filterValueChanged(ValueChangedEvent changedValue) {
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
	public void setRefNodeName(String newRefNodeName) {
		setRootName("��֯");
		m_strRefNodeName = newRefNodeName;
		setClassTableName("org_orgs");
		setClassFatherField("pk_fatherorg");
		setClassChildField("pk_org");
		setClassFieldCode(new String[] { "code", "name","pk_org","pk_fatherorg" });
		setClassJoinField("pk_org");
		// *��������������Ӧ����
		setFieldCode(new String[] { "groupcode", "groupname" });
		setFieldName(new String[] { "��ɫ�����", "��ɫ������" });
		setHiddenFieldCode(new String[] { "pk_rolegroup" });
		setPkFieldCode("pk_rolegroup");
		setRefCodeField("groupcode");
		setRefNameField("groupname");
		setTableName("cp_rolegroup");
		setDocJoinField("pk_org");
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
