package nc.uap.wfm.refrence;
import nc.uap.lfw.reference.base.GridRefModel;
public class AssignActorsModel extends GridRefModel {
	@Override
	public String[] getHiddenFieldNames() {
		return null;
	}
	@Override
	public String[] getVisibleFieldNames() {
		String str[] = { "id", "name", "prodef_name" };
		return str;
	}
	@Override
	public int getFieldIndex(String field) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String[] getFilterFields() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFixedWherePart() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getGroupPart() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getHiddenFieldCodes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOrderPart() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getQuerySql() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getRefCodeField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getRefNameField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getRefPkField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStrPatch() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTablesString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getVisibleFieldCodes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getWherePart() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setWherePart(String wherePart) {
	// TODO Auto-generated method stub
	}
}
