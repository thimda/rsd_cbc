package nc.uap.cpb.org.pubview.mode;

import java.io.Serializable;

public class ModeOrgFilter implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1540537177600968857L;
	
	private boolean isgrpadmin =false;
	private boolean isneedGlobal = false;
	private boolean isneedGroup = false;
	
	private boolean isfilterSecurity = true;

	public void setIsgrpadmin(boolean isgrpadmin) {
		this.isgrpadmin = isgrpadmin;
	}

	public boolean isIsgrpadmin() {
		return isgrpadmin;
	}

	public void setIsneedGlobal(boolean isneedGlobal) {
		this.isneedGlobal = isneedGlobal;
	}

	public boolean isIsneedGlobal() {
		return isneedGlobal;
	}

	public void setIsneedGroup(boolean isneedGroup) {
		this.isneedGroup = isneedGroup;
	}

	public boolean isIsneedGroup() {
		return isneedGroup;
	}

	public void setIsfilterSecurity(boolean isfilterSecurity) {
		this.isfilterSecurity = isfilterSecurity;
	}

	public boolean isIsfilterSecurity() {
		return isfilterSecurity;
	}
	
}
