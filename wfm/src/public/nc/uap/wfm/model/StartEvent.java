package nc.uap.wfm.model;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.dbl.constant.DblConstants;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFTime;
public class StartEvent extends HumAct {
	private static final long serialVersionUID = -9088873256001818387L;
	private UFDate statrtDate;
	private UFTime statrtTime;
	private CpUserVO startUser;
	private String allowIncludeBill;
	public String getAllowIncludeBill() {
		return allowIncludeBill;
	}
	public void setAllowIncludeBill(String allowIncludeBill) {
		this.allowIncludeBill = allowIncludeBill;
	}
	public UFDate getStatrtDate() {
		return statrtDate;
	}
	public void setStatrtDate(UFDate statrtDate) {
		this.statrtDate = statrtDate;
	}
	public UFTime getStatrtTime() {
		return statrtTime;
	}
	public void setStatrtTime(UFTime statrtTime) {
		this.statrtTime = statrtTime;
	}
	public CpUserVO getStartUser() {
		return startUser;
	}
	public void setStartUser(CpUserVO startUser) {
		this.startUser = startUser;
	}
	public boolean isNotCoverMakePort() {
		String flag = this.getAllowIncludeBill();
		if(flag==null||flag.length()==0){
			flag="true";
		}
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
}
