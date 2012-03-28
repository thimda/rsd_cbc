package nc.uap.wfm.handler;
import java.util.Iterator;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.vo.pub.lang.UFBoolean;
public class EndEventHandler extends AbstractHandler implements IHandler<Void> {
	private ProIns proIns = null;
	private Task task = null;
	private HumActIns pHumActIns = null;
	private IPort port = null;
	private ProIns rProIns = null;
	public EndEventHandler(Task task, IPort port, HumActIns pHumActIns, ProIns proIns, ProIns rProIns) {
		super();
		this.proIns = proIns;
		this.pHumActIns = pHumActIns;
		this.task = task;
		this.port = port;
		this.rProIns = rProIns;
	}
	public Void handler() {
		try {
			task.setIsNotEnded(UFBoolean.TRUE);
			task.asyn();
			proIns = ModelBuilder.builder(proIns);
			Set<HumActIns> set = proIns.getHumActInses();
			Iterator<HumActIns> humActInsIter = set.iterator();
			HumActIns humActIns = null;
			Set<Task> tasks = null;
			Iterator<Task> taskIter = null;
			while (humActInsIter.hasNext()) {
				humActIns = humActInsIter.next();
				humActIns = ModelBuilder.builder(humActIns);
				tasks = humActIns.getTasks();
				if (tasks == null) {
					continue;
				}
				taskIter = tasks.iterator();
				/**
				 * 处理掉没有完成的任务
				 */
				Task tmpTask = null;
				while (taskIter.hasNext()) {
					tmpTask = taskIter.next();
					if (Task.CreateType_Deliver.equalsIgnoreCase(tmpTask.getCreateType())) {
						continue;
					}
					if (!tmpTask.getIsnotexe().booleanValue()) {
						taskExe.realDeleteTask(tmpTask);
					}
				}
				/**
				 * 处理掉没有执行的活动实例
				 */
				HumAct humAct = null;
				if (humActIns.getIsNotExe() == null || !humActIns.getIsNotExe().booleanValue()) {
					humAct = humActIns.getHumAct();
					if (!humAct.getActionType().equalsIgnoreCase(WfmConstants.ActionType_Cirulated)) {
						humActInsExe.realDeleteHumActIns(humActIns);
					}
				}
			}
			/**
			 * 处理流程实例
			 */
			this.proInsExe.endProIns(proIns);
			proIns.asyn();
			WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
			if (formVo != null) {
				formVo.setAttributeValue(formVo.getFrmStateField(), ProIns.End);
			}
			this.addEndEventActIns(proIns, rProIns, pHumActIns, (EndEvent) port);
			return null;
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e);
		}
	}
	/**
	 * 增加结束节点的活动实例信息
	 * 
	 * @param task
	 */
	public HumActIns addEndEventActIns(ProIns proIns, ProIns rootProIns, HumActIns pHumActIns, EndEvent endEvent) {
		try {
			HumActIns humActIns = humActInsExe.createHumActIns(endEvent);
			humActIns.setIsNotExe(new UFBoolean(true));
			humActIns.setIsNotPas(new UFBoolean(true));
			humActIns.setProIns(proIns);
			humActIns.setState(HumActIns.End);
			humActIns.setRootProIns(rootProIns);
			humActIns.setParent(pHumActIns);
			humActIns.asyn();
			return humActIns;
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
