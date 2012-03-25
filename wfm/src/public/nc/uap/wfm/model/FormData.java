package nc.uap.wfm.model;

import java.util.List;
import java.util.Map;

public class FormData {

	private Map<String, String> frmIns = null;
	private Map<String, List<Map<String, String>>> dynaRowData = null;

	public Map<String, String> getFrmIns() {
		return frmIns;
	}

	public void setFrmIns(Map<String, String> frmIns) {
		this.frmIns = frmIns;
	}

	public Map<String, List<Map<String, String>>> getDynaRowData() {
		return dynaRowData;
	}

	public void setDynaRowData(Map<String, List<Map<String, String>>> dynaRowData) {
		this.dynaRowData = dynaRowData;
	}

}
