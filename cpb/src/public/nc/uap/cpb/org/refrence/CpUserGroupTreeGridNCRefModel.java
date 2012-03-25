package nc.uap.cpb.org.refrence;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.ui.bd.ref.AbstractRefGridTreeModel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.vo.uap.rbac.constant.IRoleConst;
public class CpUserGroupTreeGridNCRefModel extends AbstractRefGridTreeModel {
	private String classWherePart = "";
	/**
	 * @see IRoleConst
	 */
	private Integer roleType = null;
	String userID = InvocationInfoProxy.getInstance().getUserId();
	String pk_group = InvocationInfoProxy.getInstance().getGroupId();
	public CpUserGroupTreeGridNCRefModel() {
		super();
		setRefNodeName("����");
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
		setRootName(newRefNodeName);
		m_strRefNodeName = newRefNodeName;
		setClassTableName("org_orgs");
		setClassFatherField("pk_fatherorg");
		setClassChildField("pk_org");
		setClassFieldCode(new String[] { "code", "name", "pk_org" });
		setClassJoinField("pk_org");
		// *��������������Ӧ����
		setTableName("cp_usergroup");
		setFieldCode(new String[] { "group_code", "group_name" });
		setFieldName(new String[] { "�û������", "�û�������" });
		setHiddenFieldCode(new String[] { "pk_usergroup" });
		setPkFieldCode("pk_usergroup");
		//setRefCodeField("group_code");
		//setRefNameField("group_name");
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
