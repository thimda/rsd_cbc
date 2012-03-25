package nc.uap.wfm.context;
import nc.uap.lfw.core.base.ExtendAttributeSupport;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
public abstract class WfmFlowInfoCtx extends ExtendAttributeSupport {
	private static final long serialVersionUID = 3542078163836371009L;
	protected String scratchpad;
	protected String opinion;
	protected String myVisaPk;
	protected String cntUserPk;
	protected String[] msgType;
	public String getScratchpad() {
		return scratchpad;
	}
	public void setScratchpad(String scratchpad) {
		this.scratchpad = scratchpad;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getMyVisaPk() {
		return myVisaPk;
	}
	public void setMyVisaPk(String myVisaPk) {
		this.myVisaPk = myVisaPk;
	}
	public String getCntUserPk() {
		return cntUserPk;
	}
	public void setCntUserPk(String cntUserPk) {
		this.cntUserPk = cntUserPk;
	}
	public String[] getMsgType() {
		return msgType;
	}
	public void setMsgType(String[] msgType) {
		this.msgType = msgType;
	}
	abstract public void check();
	public ProDef getProDef() {
		if (this == null) {
			throw new LfwRuntimeException("没有流程上下文信息");
		}
		ProDef proDef = null;
		if (this instanceof UnStartProInsInfoCtx) {
			String flowTypePk = ((UnStartProInsInfoCtx) this).getFlowTypePk();
			proDef = ProDefsContainer.getProDefByFlowTypePk(flowTypePk);
		}
		if (this instanceof StartedProInsInfoCtx) {
			String taskPk = ((StartedProInsInfoCtx) this).getTaskPk();
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			proDef = task.getProDef();
		}
		return proDef;
	}
}
