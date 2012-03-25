package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class TransmitTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -1677526339136967397L;
	private String transimgUserPk;
	public String getTransimgUserPk() {
		return transimgUserPk;
	}
	public void setTransimgUserPk(String transimgUserPk) {
		this.transimgUserPk = transimgUserPk;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("任务转发必须提供任务Pk");
		}
		if (this.getTransimgUserPk() == null || this.getTransimgUserPk().length() == 0) {
			throw new LfwRuntimeException("任务转发必须提供转发人的Pk");
		}
	}
}
