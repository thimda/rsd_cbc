package nc.uap.cpb.org.refrence;
import nc.uap.lfw.reference.base.TreeRefModel;
public class PtOrgsRefPageModel extends TreeRefModel {
	@Override public String getChildField() {
		return "pk_org";
	}
	@Override public String getFatherField() {
		return "pk_fatherorg";
	}
	@Override public String getRootName() {
		return "×éÖ¯";
	}
	@Override public String[] getFilterFields() {
		return null;
	}
	@Override public String getFixedWherePart() {
		return null;
	}
	@Override public String getGroupPart() {
		return null;
	}
	@Override public String[] getHiddenFieldCodes() {
		String[] hiddenFieldsCodes = {"pk_org","pk_fatherorg"};
		return hiddenFieldsCodes;
	}
	@Override public String getOrderPart() {
		return null;
	}
	@Override public String getQuerySql() {
		return null;
	}
	@Override public String getRefCodeField() {
		return "code";
	}
	@Override public String getRefNameField() {
		return "name";
	}
	@Override public String getRefPkField() {
		return "pk_org";
	}
	@Override public String getTablesString() {
		return "org_group";
	}
	@Override public String[] getVisibleFieldCodes() {
		String[] visibleFields = {"code", "name"};  
		return visibleFields;
	}
}
