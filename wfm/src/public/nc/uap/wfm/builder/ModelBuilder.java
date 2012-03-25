package nc.uap.wfm.builder;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
public class ModelBuilder {
	public static ProIns builder(ProIns proIns) throws WfmServiceException {
		IWfmHumActInsQry humActInsQry = NCLocator.getInstance().lookup(IWfmHumActInsQry.class);
		Set<HumActIns> humActInses = humActInsQry.getHumActInsesByProInsPk(proIns.getPk_proins());
		proIns.setHumActInses(humActInses);
		return proIns;
	}
	public static HumActIns builder(HumActIns humActIns) throws WfmServiceException {
		IWfmHumActInsQry humActInsQry = NCLocator.getInstance().lookup(IWfmHumActInsQry.class);
		humActIns = humActInsQry.getHumActInsByPk(humActIns.getPk_humactins());
		IWfmTaskQry taskQry = NCLocator.getInstance().lookup(IWfmTaskQry.class);
		Set<Task> tasks = taskQry.getTasksByHumActInsPk(humActIns.getPk_humactins());
		humActIns.setTasks(tasks);
		return humActIns;
	}
	public static Task builder(Task task) throws WfmServiceException {
		String taskPk = task.getPk_task();
		IWfmTaskQry taskQry = NCLocator.getInstance().lookup(IWfmTaskQry.class);
		task = taskQry.getTaskByPk(taskPk);
		return task;
	}
	public static HumActIns flwBuilder(HumActIns humActIns) throws WfmServiceException {
		IWfmHumActInsQry humActInsQry = NCLocator.getInstance().lookup(IWfmHumActInsQry.class);
		humActIns = humActInsQry.getHumActInsByPk(humActIns.getPk_humactins());
		IWfmTaskQry taskQry = NCLocator.getInstance().lookup(IWfmTaskQry.class);
		Set<Task> tasks = taskQry.getTasksByHumActInsPk(humActIns.getPk_humactins(),Task.CreateType_Deliver);
		humActIns.setTasks(tasks);
		return humActIns;
	}
}
