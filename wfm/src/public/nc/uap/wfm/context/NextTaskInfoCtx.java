package nc.uap.wfm.context;
import nc.uap.lfw.core.exception.LfwRuntimeException;
public class NextTaskInfoCtx extends StartedProInsInfoCtx {
	private static final long serialVersionUID = 153203730207105856L;
	private HumActInfoEngCtx[] nextInfo;
	private String finishType;
	private String tokenId;
	public HumActInfoEngCtx[] getNextInfo() {
		return nextInfo;
	}
	public void setNextInfo(HumActInfoEngCtx[] nextInfo) {
		this.nextInfo = nextInfo;
	}
	public String getFinishType() {
		return finishType;
	}
	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public void check() {
		if (this.getTaskPk() == null || this.getTaskPk().length() == 0) {
			throw new LfwRuntimeException("流程任务执行必须提供任务Pk");
		}
	}
}
