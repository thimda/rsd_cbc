package nc.uap.wfm.vo.base;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
public class SuitPrintBaseVO extends SuperVO {
	private static final long serialVersionUID = 1918220852160855328L;
	private String pk_suitprint;
	private String code;
	private String name;
	private String pk_formdefinition;
	private String orderstr;
	private byte[] contents;
	private String serverclass;
	private UFDateTime ts;
	private UFBoolean dr;
	public String getPk_suitprint() {
		return pk_suitprint;
	}
	public void setPk_suitprint(String pk_suitprint) {
		this.pk_suitprint = pk_suitprint;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPk_formdefinition() {
		return pk_formdefinition;
	}
	public void setPk_formdefinition(String pk_formdefinition) {
		this.pk_formdefinition = pk_formdefinition;
	}
	public String getOrderstr() {
		return orderstr;
	}
	public void setOrderstr(String orderstr) {
		this.orderstr = orderstr;
	}
	public byte[] getContents() {
		return contents;
	}
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public UFBoolean getDr() {
		return dr;
	}
	public void setDr(UFBoolean dr) {
		this.dr = dr;
	}
	public String getPKFieldName() {
		return "pk_suitprint";
	}
	public String getTableName() {
		return "pwfm_suitprint";
	}
	public String getServerclass() {
		//		if (this.serverclass == null || this.serverclass.length() == 0) {
		//			return PwfmConstants.DefaultFormDataBuilder;
		//		}
		return serverclass;
	}
	public void setServerclass(String serverclass) {
		this.serverclass = serverclass;
	}
}
