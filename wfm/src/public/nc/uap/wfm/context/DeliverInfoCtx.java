package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class DeliverInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -9122974872229254529L;
	private DeliverUserInfoCtx[] deliverUserInfo;
	public DeliverUserInfoCtx[] getDeliverUserInfo() {
		return deliverUserInfo;
	}
	public void setDeliverUserInfo(DeliverUserInfoCtx[] deliverUserInfo) {
		this.deliverUserInfo = deliverUserInfo;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("任务传阅必须提供任务Pk");
		}
	}
}
