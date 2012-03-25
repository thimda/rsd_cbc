package nc.uap.wfm.vo;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
public class WfmFailMsgVO extends SuperVO {
	private static final long serialVersionUID = -4915513468374842722L;
	private String pk_failmsg;
	private String pk_msg;
	private String tabname;
	private UFDateTime ts;
	private java.lang.Integer dr;
	public String getPk_failmsg() {
		return pk_failmsg;
	}
	public void setPk_failmsg(String pk_failmsg) {
		this.pk_failmsg = pk_failmsg;
	}
	public String getPk_msg() {
		return pk_msg;
	}
	public void setPk_msg(String pk_msg) {
		this.pk_msg = pk_msg;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public java.lang.Integer getDr() {
		return dr;
	}
	public void setDr(java.lang.Integer dr) {
		this.dr = dr;
	}
	public String getPKFieldName() {
		return "pk_failmsg";
	}
	public String getTableName() {
		return "wfm_failmsg";
	}
}
