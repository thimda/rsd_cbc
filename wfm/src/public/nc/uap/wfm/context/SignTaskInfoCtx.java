package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class SignTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = -6430232359750546036L;
	private String signOpinion;
	public String getSignOpinion() {
		return signOpinion;
	}
	public void setSignOpinion(String signOpinion) {
		this.signOpinion = signOpinion;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("签收任务必须提供任务Pk");
		}
	}
}
