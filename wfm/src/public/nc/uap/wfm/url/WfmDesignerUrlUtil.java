package nc.uap.wfm.url;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.ProcessUtil;
public class WfmDesignerUrlUtil {
	public static String getProcessDesignUrl(Task task) {
		if (task == null) {
			return null;
		}
		ProIns rootProIns = task.getRootProIns();
		String url = ProcessUtil.getProcessUrl(rootProIns.getProDef().getPk_prodef(), rootProIns.getPk_proins(), true);
		return url;
	}
}
