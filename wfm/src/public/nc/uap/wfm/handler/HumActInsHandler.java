package nc.uap.wfm.handler;
import java.util.HashSet;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.actorsgy.ActorSgyManager;
import nc.uap.wfm.cmd.CreateTaskCmd;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class HumActInsHandler extends AbstractHandler implements IHandler<HumActIns> {
	private HumAct humAct = null;
	private IPort[] nextPorts = null;
	private ProIns proIns = null;
	private ProIns rProIns = null;
	private HumActIns pHumActIns = null;
	private HumActIns nHumActIns = null;
	private Task pTask = null;
	public HumActInsHandler(HumAct humAct, IPort[] nextPorts, ProIns proIns, ProIns rProIns, HumActIns pHumActIns, Task pTask) {
		super();
		this.humAct = humAct;
		this.nextPorts = nextPorts;
		this.proIns = proIns;
		this.rProIns = rProIns;
		this.pHumActIns = pHumActIns;
		this.pTask = pTask;
	}
	public HumActIns handler() {
		if (nextPorts == null || nextPorts.length == 0) {
			throw new LfwRuntimeException("无流程下一步信息，请提供流程下一步信息");
		}
		if (PortAndEdgeHandler.isContainPort(nextPorts, humAct)) {
			PwfmContext.getCurrentBpmnSession().setCurrentHumAct(humAct);
			try {
				IHumActHandler before = (IHumActHandler) Class.forName(humAct.getDelegatorClass()).newInstance();
				before.beforeHumAct();
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			nHumActIns = this.createHumActIns();
			ActorSgyManager actorManager = ActorSgyManager.getInstance();
			WfmFormInfoCtx formVo = (WfmFormInfoCtx) PwfmContext.getCurrentBpmnSession().getFormVo();
			String actionType = humAct.getActionType();
			Set<Task> tasks = new HashSet<Task>();
			Task newTask = null;
			PwfmContext.getCurrentBpmnSession().setPriority(0);
			if (HumAct.ActionType_Hndertake.equalsIgnoreCase(actionType)) {
				String majorActors[] = actorManager.getMajorActors(formVo, proIns, humAct, pTask);
				if (majorActors == null || majorActors.length == 0) {
					throw new LfwRuntimeException(humAct.getName() + "无参与者设置，请先指定参与者");
				}
				for (int j = 0; j < majorActors.length; j++) {
					newTask = null;
					newTask = this.createMajorTask(majorActors[j]);
					tasks.add(newTask);
				}
				String[] assistActors = actorManager.getAssistActors(formVo, rProIns, humAct, pTask);
				if (assistActors != null && assistActors.length != 0) {
					for (int j = 0; j < assistActors.length; j++) {
						newTask = null;
						newTask = this.createAssistTask(assistActors[j]);
						tasks.add(newTask);
					}
				}
			}
			if (HumAct.ActionType_Normal.equalsIgnoreCase(actionType) || HumAct.ActionType_Deliver.equalsIgnoreCase(actionType)) {
				String noramlActors[] = actorManager.getRuntimeActors(formVo, proIns, humAct, pTask);
				if (noramlActors == null || noramlActors.length == 0) {
					throw new LfwRuntimeException(humAct.getName() + "无参与者设置，请先指定参与者");
				}
				for (int j = 0; j < noramlActors.length; j++) {
					newTask = null;
					newTask = this.createNormalTask(noramlActors[j]);
					tasks.add(newTask);
				}
			}
			PwfmContext.getCurrentBpmnSession().setPriority(0);
			return nHumActIns;
		}
		return null;
	}
	public HumActIns createHumActIns() {
		HumActIns newHumActIns = null;;
		try {
			newHumActIns = humActInsExe.createHumActIns(humAct);
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e.getMessage(), e);
		}
		newHumActIns.setProIns(proIns);
		newHumActIns.setParent(pHumActIns);
		newHumActIns.setRootProIns(rProIns);
		newHumActIns.asyn();
		return newHumActIns;
	}
	public Task createMajorTask(String ownerPk) {
		try {
			return new CreateTaskCmd(null, pTask, nHumActIns, proIns, rProIns, humAct, ownerPk, HumAct.ActionType_Major).execute();
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public Task createAssistTask(String ownerPk) {
		try {
			return new CreateTaskCmd(null, pTask, nHumActIns, proIns, rProIns, humAct, ownerPk, HumAct.ActionType_Assist).execute();
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public Task createNormalTask(String ownerPk) {
		try {
			return new CreateTaskCmd(null, pTask, nHumActIns, proIns, rProIns, humAct, ownerPk, HumAct.ActionType_Normal).execute();
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
