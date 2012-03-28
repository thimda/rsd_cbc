package nc.uap.wfm.execution;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsBill;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.scratchpad.TaskScratchpadUtil;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import org.apache.commons.lang.StringUtils;
public class HumActInsExecution {
	private static HumActInsExecution instance = null;
	private HumActInsExecution() {}
	synchronized public static HumActInsExecution getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new HumActInsExecution();
			return instance;
		}
	}
	/**
	 * 创建活动实例
	 * 
	 * @param actIns
	 * @throws WfmServiceException
	 */
	public HumActIns createHumActIns(IPort port) throws WfmServiceException {
		HumActIns humActIns = new HumActIns();
		humActIns.setPort(port);
		humActIns.setIsNotExe(UFBoolean.FALSE);
		humActIns.setIsNotPas(UFBoolean.FALSE);
		humActIns.setIsNotReject(UFBoolean.FALSE);
		humActIns.setState(HumActIns.Run);
		return humActIns;
	}
	/**
	 * 完成人工活动
	 * 
	 * @param actIns
	 * @throws WfmServiceException
	 */
	public HumActIns completeHumActIns(HumActIns humActIns) throws WfmServiceException {
		humActIns.setIsNotPas(UFBoolean.TRUE);
		if (HumActIns.Exe.equalsIgnoreCase(humActIns.getState())) {
			humActIns.setState(HumActIns.End);
		}
		return humActIns;
	}
	/**
	 * 假删除活动实例
	 */
	public boolean bodusDeleteHumActIns(HumActIns humActIns) throws WfmServiceException {
		humActIns.setState(HumActIns.End);
		humActIns.setIsNotPas(UFBoolean.FALSE);
		humActIns.asyn();
		IWfmHumActInsBill humActInsBill = NCLocator.getInstance().lookup(IWfmHumActInsBill.class);
		humActInsBill.bogusDeleteHumActInsByPk(humActIns.getPk_humactins());
		return true;
	}
	/**
	 * 删掉活动实例
	 * 
	 * @param humActIns
	 * @throws WfmServiceException
	 */
	public void realDeleteHumActIns(HumActIns humActIns) throws WfmServiceException {
		IWfmHumActInsBill humActInsBill = NCLocator.getInstance().lookup(IWfmHumActInsBill.class);
		humActInsBill.realDeleteHumActInsByPk(humActIns.getPk_humactins());
	}
	/**
	 * 活动节点驳回
	 * 
	 * @param humActIns
	 * @throws WfmServiceException
	 */
	public void rejectHumActIns(Task task, HumActIns humActIns, HumAct humAct, String[] rejectUser, String scratchPad) throws WfmServiceException {
		if (rejectUser == null || rejectUser.length == 0) {
			throw new LfwRuntimeException("请选择退回人");
		}
		/**
		 * 设置该活动实例的相关信息
		 */
		humActIns.setState(HumActIns.End);
		humActIns.setIsNotExe(UFBoolean.TRUE);
		humActIns.setIsNotPas(UFBoolean.FALSE);
		humActIns.setTasks(null);
		humActIns.asyn();
		/**
		 * 拷贝驳回节点的相关信息
		 */
		String proInsPk = humActIns.getProIns().getPk_proins();
		String rejectPortId = humAct.getId();
		HumActIns rHumActIns = NCLocator.getInstance().lookup(IWfmHumActInsQry.class).getHumActInsByProInsPkAndPrtId(proInsPk, rejectPortId);
		if (rHumActIns == null) {
			throw new WfmServiceException("流程没有经过此节点，不能够退回到此节点");
		}
		HumActIns cloneHumActIns = ModelBuilder.builder(rHumActIns.clone());
		cloneHumActIns.setPk_humactins(null);
		cloneHumActIns.setIsNotExe(UFBoolean.FALSE);
		cloneHumActIns.setIsNotPas(UFBoolean.FALSE);
		cloneHumActIns.setState(HumActIns.Run);
		cloneHumActIns.setIsNotReject(UFBoolean.TRUE);
		cloneHumActIns.setParent(humActIns);
		cloneHumActIns.setPPort(humActIns.getPort());
		cloneHumActIns.asyn();
		Set<Task> tasks = cloneHumActIns.getTasks();
		if (tasks == null || tasks.size() == 0) {
			throw new LfwRuntimeException("退回失败");
		}
		Iterator<Task> iterTask = tasks.iterator();
		List<String> list = Arrays.asList(rejectUser);
		String createUserPk = PwfmContext.getCurrentBpmnSession().getCurrentUserPk();
		Task tmpTask = null;
		String ownerPk = null;
		while (iterTask.hasNext()) {
			tmpTask = iterTask.next();
			ownerPk = tmpTask.getPk_owner();
			if (!list.contains(ownerPk)) {
				continue;
			}
			tmpTask.setEndDate(null);
			tmpTask.setOpinion(null);
			tmpTask.setSignDate(null);
			tmpTask.setPk_executer(null);
			tmpTask.setPk_creater(createUserPk);
			tmpTask.setPk_task(null);
			tmpTask.setHumActIns(cloneHumActIns);
			tmpTask.setStartDate(new UFDate());
			tmpTask.setIsnotexe(UFBoolean.FALSE);
			tmpTask.setIsnotpas(UFBoolean.FALSE);
			tmpTask.setState(Task.State_Run);
			tmpTask.setParent(task);
			tmpTask.setCreateType(Task.CreateType_Reject);
			tmpTask.setHandlepiece(Task.HandlerPiece_Rejected);
			if (StringUtils.isNotBlank(scratchPad)) {
				scratchPad = TaskScratchpadUtil.getScratchpad(WfmTransUtil.getUserNameByUserPk(tmpTask.getPk_creater()), tmpTask.getStartDate().toString(), scratchPad);
			}
			tmpTask.setScratchpad(scratchPad);
			tmpTask.asyn();
		}
	}
}
