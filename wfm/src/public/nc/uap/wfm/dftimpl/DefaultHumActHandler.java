package nc.uap.wfm.dftimpl;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.exception.WfmValidateException;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.orgs.WfmFlowDeptDesc;
import nc.uap.wfm.orgs.WfmFlowOrgDesc;
import nc.uap.wfm.orgs.WfmFlowUserDesc;
import nc.uap.wfm.overtimesgy.OverTimeCalculator;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class DefaultHumActHandler implements IHumActHandler {
	public boolean isNotOverTime(Task task) {
		return OverTimeCalculator.isOverTime(task);
	}
	public boolean isAssign(Task task, HumAct humAct) {
		if (humAct instanceof StartEvent) {
			return false;
		} else if (humAct instanceof HumAct) {
			if (humAct.isNotPreAssign()) {
				return true;
			}
		}
		return false;
	}
	public String getPhoneMsg(Task task) {
		return null;
	}
	public String getTaskExtClazz() {
		return DefaultTaskExtHandler.class.getName();
	}
	public WfmFlowDeptDesc[] getBeforeAddSignDeptDesc(String taskPk, String orgPk) {
		return null;
	}
	public WfmFlowOrgDesc[] getBeforeAddSignOrgDesc(String taskPk) {
		return null;
	}
	public WfmFlowUserDesc[] getBeforeAddSignUserDesc(String taskPk, String deptPk) {
		return null;
	}
	public WfmFlowDeptDesc[] getDeliverDeptDesc(String taskPk, String orgPk) {
		return null;
	}
	public WfmFlowOrgDesc[] getDeliverOrgDesc(String taskPk) {
		return null;
	}
	public WfmFlowUserDesc[] getDeliverUserDesc(String taskPk, String deptPk) {
		return null;
	}
	public void check(WfmFormInfoCtx formVo, WfmFlwTypeVO flowTypeVo) throws WfmValidateException {}
	public void beforeHumAct() {}
	public void afterHumAct() {}
}
