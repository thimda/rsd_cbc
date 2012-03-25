package nc.uap.cpb.org.refrence;


import nc.uap.lfw.reference.base.TreeRefModel;



public class PtGroupRefModel extends TreeRefModel{
	@Override
	public String getChildField() {
		return "pk_group";
	}

	@Override
	public String getFatherField() {
		return "pk_fathergroup";
	}

	@Override
	public String getRootName() {
		return "¼¯ÍÅ";
	}
	@Override
	public String[] getFilterFields() {
	    
		return null;
	}

	@Override
	public String getFixedWherePart() {
		return null;
	}

	@Override
	public String getGroupPart() {
		return null;
	}

	@Override
	public String[] getHiddenFieldCodes() {
		String[] hiddenFieldsCodes = {"pk_group","pk_fathergroup"};
		return hiddenFieldsCodes;
	}

	@Override
	public String getOrderPart() {
		return null;
	}

	@Override
	public String getQuerySql() {
		return null;
	}

	@Override
	public String getRefCodeField() {
		return "code";
	}

	@Override
	public String getRefNameField() {
		return "name";
	}

	@Override
	public String getRefPkField() {
		return "pk_group";
	}
	
    
	@Override
	public String getStrPatch() {
		return " ";
	}

	@Override
	public String getTablesString() {
		return "org_group";
	}

	@Override
	public String[] getVisibleFieldCodes() {
		String[] visibleFields = {"code", "name"};  
		return visibleFields;
	}

	@Override
	public int getFieldIndex(String field) {
		return 0;
	}

}
