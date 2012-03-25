package nc.uap.wfm.url;
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
	public static String getProcessImageUrlByFrmDefPk(String frmDefPk) {
		ProDef proDef = ProDefsContainer.getProDefByFlowTypePk(frmDefPk);
		return ProcessUtil.getProcessUrl(proDef.getPk_prodef(), true);
	}
}
