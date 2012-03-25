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
public class CpUserTreeGridNCRefModel extends AbstractRefGridTreeModel {
	/**
	 * @see IRoleConst
	 */
	private Integer roleType = null;
	String userID = InvocationInfoProxy.getInstance().getUserId();
	String pk_group = InvocationInfoProxy.getInstance().getGroupId();
	public CpUserTreeGridNCRefModel() {
		super();
		setRefNodeName("��֯");
		reset();
	}
	public void reset() {
		super.reset();
		setFilterRefNodeName(new String[] { "ҵ��Ԫ+����" });
	}
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
		setRootName("�û���");
		m_strRefNodeName = newRefNodeName;
		setClassTableName("cp_usergroup");
		setClassFatherField("pk_parent");
		setClassChildField("pk_usergroup");
		setClassFieldCode(new String[] { "group_code", "group_name", "pk_usergroup","pk_parent" });
		setClassJoinField("pk_usergroup");
		// *��������������Ӧ����
		setTableName("cp_user");
		setFieldCode(new String[] { "user_code", "user_name" });
		setFieldName(new String[] { "�û�����", "�û�����" });
		setHiddenFieldCode(new String[] { "cuserid" });
		setPkFieldCode("cuserid");
		setRefCodeField("user_code");
		setRefNameField("user_name");
		setDocJoinField("pk_usergroupforcreate");
		// �ǹ���Ա���ͻ�����ͨҵ���ɫ����
		resetFieldName();
	}
	public String getClassWherePart() {
		ISQLNode classWherePartNode = createClassWherePartSQLNode();
		return classWherePartNode.getSQL();
	}
	private ISQLNode createClassWherePartSQLNode() {
		// LfwRuntimeEnvironment.getWebContext().getPageMeta().getWidget("main");
		ISQLNode superClassWherePartNode = SQLNodeFactory.createSQLWhereElementNode(super.getClassWherePart());
		ISQLNode groupAndOrgWherePart = new GroupAndOrgPartProvider(pk_group, getPk_org(), false).tackleUserSqlWhere();
		ISQLNode roleTypeWherePartNode = new RoleGroupTypeWherePartProvider(roleType).tackleUserSqlWhere();
		ISQLNode roleGroupAuthPermPartNode = new RoleGroupAuthPermWherePartProvider(userID, pk_group).tackleUserSqlWhere();
		return SQLNodeFactory.createANDNode(superClassWherePartNode, roleTypeWherePartNode, groupAndOrgWherePart, roleGroupAuthPermPartNode);
	}
}
