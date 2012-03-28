package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.convert.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
public class BackProInsCmd extends AbstractCommand implements ICommand<Void> {
	protected ProIns proIns = null;
	public BackProInsCmd(ProIns proIns) {
		super();
		this.proIns = proIns;
	}
	public Void execute() throws WfmServiceException {
		ProDef proDef = proIns.getProDef();
		if (!proDef.isNotBack()) {
			throw new LfwRuntimeException("流程不允许反审核");
		}
		if (!ProIns.End.equalsIgnoreCase(proIns.getState())) {
			throw new LfwRuntimeException("流程没有结束，不能反审核");
		}
		WfmTaskVO taskVo = NCLocator.getInstance().lookup(IWfmTaskQry.class).getLasterCmpltTaskByProInsPk(proIns.getPk_proins());
		Task task = BeanConvert.toTask(taskVo);
		Task newTask = task.clone();
		taskExe.backTask(newTask);
		newTask.asyn();
		return null;
	}
}
