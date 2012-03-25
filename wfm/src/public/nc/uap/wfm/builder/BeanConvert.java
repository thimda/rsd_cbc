package nc.uap.wfm.builder;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmHumActInsVO;
import nc.uap.wfm.vo.WfmProInsVO;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.uap.wfm.vo.WfmTaskVO;
/**
 * 该类主要用来对SuperVO的转化
 * 
 * @author tianchw
 * 
 */
public class BeanConvert {
	/**
	 * 转化为流程定义VO
	 * 
	 * @param proDef
	 * @return
	 * @throws WfmServiceException
	 */
	public static WfmProdefVO toProDefVO(ProDef proDef) throws WfmServiceException {
		if (proDef == null) {
			throw new LfwRuntimeException("流程定义不能为空");
		}
		WfmProdefVO proDefVo = new WfmProdefVO();
		proDefVo.setId(proDef.getId());
		proDefVo.setPk_prodef(proDef.getPk_prodef());
		proDefVo.setVersion(proDef.getVersion());
		proDefVo.setName(proDef.getName());
		proDefVo.setMemo(proDef.getMemo());
		proDefVo.setFlwtype(proDef.getFlwtype().getPk_flwtype());
		proDefVo.setPk_startfrm(proDef.getPk_startfrm());
		proDefVo.setServerclass(proDef.getServerClass());
		return proDefVo;
	}
	/**
	 * 装化为流程定义
	 * 
	 * @param proDefVO
	 * @return
	 * @throws WfmServiceException
	 */
	public static ProDef toProDef(WfmProdefVO proDefVo) throws WfmServiceException {
		if (proDefVo == null) {
			throw new LfwRuntimeException("流程定义VO不能为空");
		}
		ProDef proDef = new ProDef();
		proDef.setId(proDefVo.getId());
		proDef.setPk_prodef(proDefVo.getPk_prodef());
		proDef.setVersion(proDefVo.getVersion());
		proDef.setName(proDefVo.getName());
		proDef.setMemo(proDefVo.getMemo());
		// proDef.setFlwtype(proDefVo.getFlwtype());
		proDef.setPk_startfrm(proDefVo.getPk_startfrm());
		proDef.setServerClass(proDefVo.getServerclass());
		return proDef;
	}
	/**
	 * 转化为流程实例VO
	 * 
	 * @param proIns
	 * @return
	 */
	public static WfmProInsVO toProInsVO(ProIns proIns) throws WfmServiceException {
		if (proIns == null) {
			throw new LfwRuntimeException("流程实例不能为空");
		}
		WfmProInsVO proInsVo = new WfmProInsVO();
		proInsVo.setPk_proins(proIns.getPk_proins());
		proInsVo.setStartdate(proIns.getStartDate());
		proInsVo.setEnddate(proIns.getEndDate());
		proInsVo.setPk_starter(proIns.getPk_starter());
		proInsVo.setState(proIns.getState());
		proInsVo.setPk_startfrmins(proIns.getPk_startfrmins());
		proInsVo.setPk_starttask(proIns.getPk_starttask());
		if (proIns.getParent() != null) {
			proInsVo.setPk_parent(proIns.getParent().getPk_proins());
		}
		if (proIns.getProDef() != null) {
			proInsVo.setPk_prodef(proIns.getProDef().getPk_prodef());
			proInsVo.setProdef_id(proIns.getProDef().getId());
		}
		if (proIns.getPproIns() != null) {
			proInsVo.setPk_pproins(proIns.getPproIns().getPk_proins());
		}
		return proInsVo;
	}
	/**
	 * 转化为流程实例
	 * 
	 * @param proInsVO
	 * @return
	 */
	public static ProIns toProIns(WfmProInsVO proInsVo) throws WfmServiceException {
		if (proInsVo == null) {
			throw new LfwRuntimeException("流程实例VO不能为空");
		}
		ProIns proIns = new ProIns();
		proIns.setPk_proins(proInsVo.getPk_proins());
		proIns.setPk_starter(proInsVo.getPk_starter());
		proIns.setPk_startfrmins(proInsVo.getPk_startfrmins());
		proIns.setStartDate(proInsVo.getStartdate());
		proIns.setEndDate(proInsVo.getEnddate());
		proIns.setState(proInsVo.getState());
		proIns.setPk_starttask(proInsVo.getPk_starttask());
		ProDef proDef = ProDefsContainer.getByProDefPkAndId(proInsVo.getPk_prodef(), proInsVo.getProdef_id());
		proIns.setProDef(proDef);
		return proIns;
	}
	/**
	 * 转化为活动实例VO
	 * 
	 * @param actIns
	 * @return
	 */
	public static WfmHumActInsVO toHumActInsVO(HumActIns humActIns) throws WfmServiceException {
		if (humActIns == null) {
			throw new LfwRuntimeException("流程实例不能为空");
		}
		WfmHumActInsVO humActInsVo = new WfmHumActInsVO();
		humActInsVo.setPk_humactins(humActIns.getPk_humactins());
		humActInsVo.setIsnotexe(humActIns.getIsNotExe());
		humActInsVo.setIsnotpas(humActIns.getIsNotPas());
		humActInsVo.setIsnotreject(humActIns.getIsNotReject());
		humActInsVo.setState(humActIns.getState());
		if (humActIns.getPort() != null) {
			humActInsVo.setHumact_id(humActIns.getPort().getId());
		}
		if (humActIns.getProIns() != null) {
			humActInsVo.setPk_proins(humActIns.getProIns().getPk_proins());
		}
		if (humActIns.getRootProIns() != null) {
			humActInsVo.setPk_rootproins(humActIns.getRootProIns().getPk_proins());
		}
		if (humActIns.getParent() != null) {
			humActInsVo.setPk_parent(humActIns.getParent().getPk_humactins());
			humActInsVo.setPhumact_id(humActIns.getParent().getHumAct().getId());
		}
		if (humActIns.getPPort() != null) {
			humActInsVo.setPhumact_id(humActIns.getPPort().getId());
		}
		return humActInsVo;
	}
	/**
	 * 转化为活动实例
	 * 
	 * @param actInsVO
	 * @return
	 * @throws WfmServiceException
	 */
	public static HumActIns toHumActIns(WfmHumActInsVO humActInsVo) throws WfmServiceException {
		if (humActInsVo == null) {
			throw new LfwRuntimeException("活动实例VO不能为空");
		}
		HumActIns humActIns = new HumActIns();
		humActIns.setPk_humactins(humActInsVo.getPk_humactins());
		humActIns.setState(humActInsVo.getState());
		IWfmProInsQry proInsQry = NCLocator.getInstance().lookup(IWfmProInsQry.class);
		ProIns proIns = proInsQry.getProInsByPk(humActInsVo.getPk_proins());
		ProDef proDef = proIns.getProDef();
		IPort port = proDef.getPorts().get(humActInsVo.getHumact_id());
		IPort pPort = proDef.getPorts().get(humActInsVo.getPhumact_id());
		humActIns.setPPort(pPort);
		humActIns.setPort(port);
		humActIns.setProIns(proIns);
		humActIns.setIsNotExe(humActInsVo.getIsnotexe());
		humActIns.setIsNotPas(humActInsVo.getIsnotpas());
		humActIns.setIsNotReject(humActInsVo.getIsnotreject());
		humActIns.setState(humActIns.getState());
		if (humActInsVo.getPk_proins().equalsIgnoreCase(humActInsVo.getPk_rootproins())) {
			humActIns.setRootProIns(proIns);
		} else {
			ProIns rootProIns = proInsQry.getProInsByPk(humActInsVo.getPk_proins());
			humActIns.setRootProIns(rootProIns);
		}
		return humActIns;
	}
	public static WfmTaskVO toTaskVO(Task task) throws WfmServiceException {
		if (task == null) {
			throw new LfwRuntimeException("任务不能为空");
		}
		WfmTaskVO taskVo = new WfmTaskVO();
		taskVo.setPk_task(task.getPk_task());
		taskVo.setPk_creater(task.getPk_creater());
		taskVo.setPk_owner(task.getPk_owner());
		taskVo.setPk_executer(task.getPk_executer());
		taskVo.setPk_agenter(task.getPk_agenter());
		taskVo.setPk_frmins(task.getPk_frmins());
		taskVo.setPriority(task.getPriority());
		taskVo.setPk_myvisa(task.getPk_myvisa());
		taskVo.setPk_assigner(task.getPk_assigner());
		taskVo.setStartdate(task.getStartDate());
		taskVo.setEnddate(task.getEndDate());
		taskVo.setIsnotexe(task.getIsnotexe());
		taskVo.setIsnotpas(task.getIsnotpas());
		taskVo.setState(task.getState());
		taskVo.setOpinion(task.getOpinion());
		taskVo.setPk_frmdef(task.getPk_frmdef());
		taskVo.setFinishtype(task.getFinishType());
		taskVo.setCreatetype(task.getCreateType());
		taskVo.setStandingtime(task.getStandingTime());
		taskVo.setActiontype(task.getActionType());
		if (task.getFlowType() != null) {
			taskVo.setPk_flowtype(task.getFlowType().getPk_flwtype());
		}
		taskVo.setFlowtypename(task.getFlowType().getTypename());
		taskVo.setScratchpad(task.getScratchpad());
		taskVo.setAddsigntimes(task.getAddSignTimes());
		taskVo.setPk_ownerdept(task.getPk_ownerdept());
		taskVo.setSigndate(task.getSignDate());
		taskVo.setHandlepiece(task.getHandlepiece());
		taskVo.setProinsstartdate(task.getProInsStartDate());
		taskVo.setIsnotended(task.getIsNotEnded());
		if (task.getHumActIns() != null) {
			taskVo.setPk_humactins(task.getHumActIns().getPk_humactins());
			taskVo.setHumactname(task.getHumActIns().getHumAct().getName());
			taskVo.setPort_id(task.getHumActIns().getHumAct().getId());
		}
		if (task.getParent() != null) {
			taskVo.setPk_parent(task.getParent().getPk_task());
		}
		if (task.getProIns() != null) {
			taskVo.setPk_proins(task.getProIns().getPk_proins());
		}
		if (task.getRootProIns() != null) {
			taskVo.setPk_rootproins(task.getRootProIns().getPk_proins());
		}
		if (task.getProDef() != null) {
			taskVo.setProdef_id(task.getProDef().getId());
			taskVo.setPk_prodef(task.getProDef().getPk_prodef());
			taskVo.setProdefname(task.getProDef().getName());
		}
		{
			for (int i = 0; i < 10; i++) {
				Object o;
				try {
					o = WfmClassUtil.invokeMethod(task, "getExt" + i, null);
					if (o == null)
						continue;
					WfmClassUtil.invokeMethod(taskVo, "setExt" + i, new Object[] { o });
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
					throw new LfwRuntimeException(e.getMessage());
				}
			}
		}
		return taskVo;
	}
	public static Task toTask(WfmTaskVO taskVo) throws WfmServiceException {
		if (taskVo == null) {
			throw new LfwRuntimeException("任务VO不能为空");
		}
		Task task = new Task();
		task.setPk_task(taskVo.getPk_task());
		task.setPk_creater(taskVo.getPk_creater());
		task.setPk_owner(taskVo.getPk_owner());
		task.setPk_executer(taskVo.getPk_executer());
		task.setPk_agenter(taskVo.getPk_agenter());
		task.setPk_myvisa(taskVo.getPk_myvisa());
		task.setPk_frmdef(taskVo.getPk_frmdef());
		task.setPk_frmins(taskVo.getPk_frmins());
		task.setPk_assigner(taskVo.getPk_assigner());
		task.setState(taskVo.getState());
		task.setOpinion(taskVo.getOpinion());
		task.setPriority(taskVo.getPriority());
		task.setStartDate(taskVo.getStartdate());
		task.setEndDate(taskVo.getEnddate());
		task.setIsnotexe(taskVo.getIsnotexe());
		task.setIsnotpas(taskVo.getIsnotpas());
		task.setFinishType(taskVo.getFinishtype());
		task.setCreateType(taskVo.getCreatetype());
		task.setStandingTime(taskVo.getStandingtime());
		task.setActionType(taskVo.getActiontype());
		task.setScratchpad(taskVo.getScratchpad());
		task.setPk_ownerdept(taskVo.getPk_ownerdept());
		task.setAddSignTimes(taskVo.getAddsigntimes());
		task.setSignDate(taskVo.getSigndate());
		task.setHandlepiece(taskVo.getHandlepiece());
		task.setProInsStartDate(taskVo.getProinsstartdate());
		task.setIsNotEnded(taskVo.getIsnotended());
		{
			IWfmHumActInsQry humActInsQry = NCLocator.getInstance().lookup(IWfmHumActInsQry.class);
			HumActIns humActIns = humActInsQry.getHumActInsByPk(taskVo.getPk_humactins());
			task.setHumActIns(humActIns);
		}
		{
			IWfmProInsQry proInsQry = NCLocator.getInstance().lookup(IWfmProInsQry.class);
			ProIns proIns = proInsQry.getProInsByPk(taskVo.getPk_proins());
			task.setProIns(proIns);
			if (taskVo.getPk_proins().equalsIgnoreCase(taskVo.getPk_rootproins())) {
				task.setRootProIns(proIns);
			} else {
				ProIns rootProIns = proInsQry.getProInsByPk(taskVo.getPk_rootproins());
				task.setRootProIns(rootProIns);
			}
			ProDef proDef = proIns.getProDef();
			task.setProDef(proDef);
		}
		{
			WfmFlwTypeVO flowTypeVo = WfmServiceFacility.getFlwTypeQry().getFlwTypeVoByPk(taskVo.getPk_flowtype());
			task.setFlowType(flowTypeVo);
		}
		{
			for (int i = 0; i < 10; i++) {
				Object o;
				try {
					o = WfmClassUtil.invokeMethod(taskVo, "getExt" + i, null);
					if (o == null)
						continue;
					WfmClassUtil.invokeMethod(task, "setExt" + i, new Object[] { o });
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
					throw new LfwRuntimeException(e.getMessage());
				}
			}
		}
		return task;
	}
}
