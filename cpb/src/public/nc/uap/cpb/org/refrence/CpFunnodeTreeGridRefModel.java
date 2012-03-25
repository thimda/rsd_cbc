package nc.uap.cpb.org.refrence;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.ui.bd.ref.AbstractRefGridTreeModel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.vo.uap.rbac.constant.IRoleConst;
public class CpFunnodeTreeGridRefModel extends AbstractRefGridTreeModel {
	/**
	 * @see IRoleConst
	 */
	private Integer roleType = null;
	String userID = InvocationInfoProxy.getInstance().getUserId();
	String pk_group = InvocationInfoProxy.getInstance().getGroupId();
	public CpFunnodeTreeGridRefModel() {
		super();
		setRefNodeName("���ܽڵ�");
		reset();
	}
	public void reset() {
		super.reset();
		setFilterRefNodeName(new String[] { "���ܽڵ�" });
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
		setClassTableName("cp_appscategory");
		setClassFatherField("pk_parent");
		setClassChildField("pk_appscategory");
		setClassFieldCode(new String[] { "id", "title", "pk_appscategory" ,"pk_parent"});
		setClassJoinField("pk_appscategory");
		// *��������������Ӧ����
		setTableName("cp_appsnode");
		setFieldCode(new String[] { "id", "title" });
		setFieldName(new String[] { "�ڵ����", "�ڵ�����" });
		setHiddenFieldCode(new String[] { "pk_appsnode" });
		setPkFieldCode("pk_appsnode");
		setRefCodeField("id");
		setRefNameField("title");
		setDocJoinField("pk_appscategory");
		// �ǹ���Ա���ͻ�����ͨҵ���ɫ����
		resetFieldName();
	}
}
