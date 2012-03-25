package nc.uap.wfm.model;
import java.util.Map;
public class FrmFieldDesc {
	private String name;
	private String nameZH;
	private String dataType;
	private String compType;
	private Boolean visible = true;
	private Map<String, String> comboxData;
	private String refType;
	private String serverClass;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameZH() {
		return nameZH;
	}
	public void setNameZH(String nameZH) {
		this.nameZH = nameZH;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getServerClass() {
		return serverClass;
	}
	public void setServerClass(String serverClass) {
		this.serverClass = serverClass;
	}
	public String getCompType() {
		return compType;
	}
	public void setCompType(String compType) {
		this.compType = compType;
	}
	public Map<String, String> getComboxData() {
		return comboxData;
	}
	public void setComboxData(Map<String, String> comboxData) {
		this.comboxData = comboxData;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
