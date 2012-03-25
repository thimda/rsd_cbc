package nc.uap.wfm.utils;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.handler.PortAndEdgeHandler;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class WfmTaskUtil {
	public static Task getTaskFromSessionCache(String taskPk) {
		if (taskPk == null || taskPk.length() == 0) {
			return null;
		}
		Task task = (Task) LfwCacheManager.getSessionCache().get(taskPk);
		if (task == null) {
			task = WfmTaskUtil.getTaskByTaskPk(taskPk);
		}
		if (task != null) {
			LfwCacheManager.getSessionCache().put(taskPk, task);
		}
		return task;
	}
	public static Task getTaskByTaskPk(String taskPk) {
		if (taskPk == null) {
			return null;
		}
		Task task = null;
		try {
			task = NCLocator.getInstance().lookup(IWfmTaskQry.class).getTaskByPk(taskPk);
			return task;
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public static IPort getStratPort(ProDef proDef) {
		StartEvent start = proDef.getStart();
		if (start.isNotCoverMakePort()) {
			return start;
		} else {
			IPort[] ports = PortAndEdgeHandler.getNextHumActs(start);
			if (ports == null || ports.length == 0) {
				return null;
			}
			return ports[0];
		}
	}
	public static CpAppsNodeVO getCpAppNodeVo(Task task) {
		if (task == null) {
			return null;
		}
		String appNodePk = task.getHumActIns().getHumAct().getPk_formdefinition();
		if (appNodePk == null || appNodePk.length() == 0) {
			WfmFlwTypeVO flwTypeVo = task.getProDef().getFlwtype();
			appNodePk = flwTypeVo.getPageid();
		}
		CpAppsNodeVO appNodeVo = null;
		try {
			appNodeVo = NCLocator.getInstance().lookup(ICpAppsNodeQry.class).getNodeByPk(appNodePk);
			return appNodeVo;
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	/**
	 * 获取执行任务的视图
	 * 
	 * @return
	 */
	public static LfwWidget getExeTaskView() {
		return AppUtil.getWidget(WfmConstants.PUBVIEW_EXETASK);
	}
	/**
	 * 获取任务指派的视图
	 * 
	 * @return
	 */
	public static LfwWidget getAssignView() {
		return AppUtil.getWidget(WfmConstants.PUBVIEW_ASSIGN);
	}
	/**
	 * 获取单据信息
	 * 
	 * @return
	 */
	public static WfmFormInfoCtx getWfmFormInfoCtx() {
		WfmFormInfoCtx formCtx = (WfmFormInfoCtx) AppUtil.getCntAppCtx().getAppAttribute(WfmConstants.FormVO);
		return formCtx;
	}
	public static String getOperator() {
		return (String) AppUtil.getAppAttr(WfmConstants.Operator);
	}
	public static String getTaskPk() {
		return (String) AppUtil.getAppAttr(WfmConstants.TaskPk);
	}
	public static String getFlowTypePk() {
		return (String) AppUtil.getAppAttr(WfmConstants.FlwTypePk);
	}
	public static String getOpinion() {
		return (String) AppUtil.getAppAttr(WfmConstants.Opinion);
	}
}
