package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.lang.UFDateTime;
public class ProInsStateSetFlowInfoCtx extends WfmFlowInfoCtx {
	private static final long serialVersionUID = 7848795471621386598L;
	private String proInsPk;
	private String userPk;
	private String reason;
	private UFDateTime date;
	private String state;
	public String getProInsPk() {
		return proInsPk;
	}
	public void setProInsPk(String proInsPk) {
		this.proInsPk = proInsPk;
	}
	public String getUserPk() {
		return userPk;
	}
	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public UFDateTime getDate() {
		return date;
	}
	public void setDate(UFDateTime date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override public void check() {
		if (this.proInsPk == null || this.proInsPk.length() == 0) {
			throw new LfwRuntimeException("流程状态设置必须提供的流程实例Pk");
		}
		if (this.userPk == null || this.userPk.length() == 0) {
			throw new LfwRuntimeException("流程状态设置必须提供状态设置人的Pk");
		}
		if (this.state == null || this.state.length() == 0) {
			throw new LfwRuntimeException("流程状态设置必须提供流程设置后的状态");
		}
	}
}
