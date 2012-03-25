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
			throw new LfwRuntimeException("����ת�������ṩ����Pk");
		}
		if (this.getTransimgUserPk() == null || this.getTransimgUserPk().length() == 0) {
			throw new LfwRuntimeException("����ת�������ṩת���˵�Pk");
		}
	}
}
