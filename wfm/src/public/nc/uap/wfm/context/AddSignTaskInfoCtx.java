package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class AddSignTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -9122974872229254529L;
	private AddSignUserInfoCtx[] addSingUsers;
	public AddSignUserInfoCtx[] getAddSingUsers() {
		return addSingUsers;
	}
	public void setAddSingUsers(AddSignUserInfoCtx[] addSingUsers) {
		this.addSingUsers = addSingUsers;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("任务加签必须提供任务Pk");
		}
	}
}
