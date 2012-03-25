package nc.uap.ctrl.tpl.qry.meta;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompositeMetaVO implements Serializable {
	private String activeFieldCode;	

	private String passiveFieldCode;
	
	private FilterMeta passiveFiltermeta;//不必设值,内部使用之
	private FilterMeta activeFiltermeta;//不必设值,内部使用之
	

	public String getActiveFieldCode() {
		return activeFieldCode;
	}

	public void setActiveFieldCode(String activeFieldCode) {
		this.activeFieldCode = activeFieldCode;
	}

	public String getPassiveFieldCode() {
		return passiveFieldCode;
	}

	public void setPassiveFieldCode(String passiveFieldCode) {
		this.passiveFieldCode = passiveFieldCode;
	}

	public FilterMeta getActiveFiltermeta() {
		return activeFiltermeta;
	}

	public void setActiveFiltermeta(FilterMeta activeFiltermeta) {
		this.activeFiltermeta = activeFiltermeta;
	}

	public FilterMeta getPassiveFiltermeta() {
		return passiveFiltermeta;
	}

	public void setPassiveFiltermeta(FilterMeta passiveFiltermeta) {
		this.passiveFiltermeta = passiveFiltermeta;
	}
	

}
