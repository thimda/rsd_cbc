package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmMyVisaVO extends SuperVO {
	private static final long serialVersionUID = -9036799113198831642L;
	private String pk_myvisa;
	private String pk_user;
	private String name;
	private String pk_lfwfile;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_myvisa() {
		return pk_myvisa;
	}
	public void setPk_myvisa(String pk_myvisa) {
		this.pk_myvisa = pk_myvisa;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPk_lfwfile() {
		return pk_lfwfile;
	}
	public void setPk_lfwfile(String pk_lfwfile) {
		this.pk_lfwfile = pk_lfwfile;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public java.lang.Integer getDr() {
		return dr;
	}
	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public String getPKFieldName() {
		return "pk_myvisa";
	}
	public String getTableName() {
		return "wfm_myvisa";
	}
}
