package nc.uap.wfm.actorsgy;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public interface IVirtualRoleActor {
	String[] getActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask);
}
