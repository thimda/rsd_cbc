package nc.uap.wfm.url;
import nc.bs.framework.aop.Decorated;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.ProcessUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
public class WfmFlowImgUrlUtil {
	/**
	 * 处理流程的url
	 * 
	 * @param task
	 * @param frmDefPk
	 */
	public static String getProcessImageUrlByTask(Task task) {
		ProIns rootProIns = task.getRootProIns();
		return ProcessUtil.getProcessUrl(rootProIns.getProDef().getPk_prodef(), rootProIns.getPk_proins(), true);
	}
	public static String getProcessImageUrlByTaskPk(String taskPk) {
		Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
		return WfmFlowImgUrlUtil.getProcessImageUrlByTask(task);
	}
	@Decorated public static String getProcessImageUrlByFrmDefPk(String frmDefPk) {
		return WfmFlowImgUrlUtil.getProcessImageUrlByFlowTypePk(frmDefPk);
	}
	public static String getProcessImageUrlByFlowTypePk(String flowTypePk) {
		ProDef proDef = ProDefsContainer.getProDefByFlowTypePk(flowTypePk);
		return ProcessUtil.getProcessUrl(proDef.getPk_prodef(), true);
	}
}
