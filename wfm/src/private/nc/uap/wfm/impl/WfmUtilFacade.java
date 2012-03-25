package nc.uap.wfm.impl;
import java.util.List;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.facade.IWfmUtilFacade;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.orgs.WfmFlowDeptDesc;
import nc.uap.wfm.orgs.WfmFlowOrgDesc;
import nc.uap.wfm.orgs.WfmFlowUserDesc;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.runtime.RejectHumActInfoUtil;
import nc.uap.wfm.url.WfmFlowImgUrlUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class WfmUtilFacade implements IWfmUtilFacade {
	public List<HumActInfoPageCtx> getNextHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo) {
		return new NextHumActInfoUtil().getNextHumActInfo(taskPk, formVo);
	}
	public String getProcessImageUrlByFrmDefPk(String frmDefPk) {
		return WfmFlowImgUrlUtil.getProcessImageUrlByFrmDefPk(frmDefPk);
	}
	public String getProcessImageUrlByTaskPk(String taskPk) {
		return WfmFlowImgUrlUtil.getProcessImageUrlByTaskPk(taskPk);
	}
	public List<HumActInfoPageCtx> getRejectHumActInfoCtx(String taskPk, WfmFormInfoCtx formVo) {
		return new RejectHumActInfoUtil().getRejectHumActInfo(taskPk);
	}
	public List<HumActInfoPageCtx> getStartHumActInfoCtx(String frmDefPk, WfmFormInfoCtx formVo) {
		return new NextHumActInfoUtil().getStartNextHumActInfo(frmDefPk, formVo);
	}
	public WfmFlwTypeVO getFlwTypeVoByPageId(String pageId) {
		return WfmServiceFacility.getFlwTypeQry().getFlwTypeVoByPageId(pageId);
	}
	public String getUrlByTask(Task task) {
		ProDef proDef = task.getProDef();
		String pageId = proDef.getFlwtype().getPageid();
		String url = "";
		try {
			CpAppsNodeVO nodeVo = NCLocator.getInstance().lookup(ICpAppsNodeQry.class).getNodeByPk(pageId);
			url = nodeVo.getUrl();
			url = url + "taskpk=" + task.getPk_task();
		} catch (CpbBusinessException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
		return url;
	}
	public String getUrlByTaskPk(String taskPk) {
		return this.getUrlByTask(WfmTaskUtil.getTaskByTaskPk(taskPk));
	}
	public WfmFlowDeptDesc[] getBeforeAddSignDeptDesc(String taskPk, String orgPk) {
		return this.getHumActHandler(taskPk).getDeliverDeptDesc(taskPk, orgPk);
	}
	public WfmFlowOrgDesc[] getBeforeAddSignOrgDesc(String taskPk) {
		return this.getHumActHandler(taskPk).getBeforeAddSignOrgDesc(taskPk);
	}
	public WfmFlowUserDesc[] getBeforeAddSignUserDesc(String taskPk, String deptPk) {
		return this.getHumActHandler(taskPk).getBeforeAddSignUserDesc(taskPk, deptPk);
	}
	public WfmFlowDeptDesc[] getDeliverDeptDesc(String taskPk, String orgPk) {
		return this.getHumActHandler(taskPk).getDeliverDeptDesc(taskPk, orgPk);
	}
	public WfmFlowOrgDesc[] getDeliverOrgDesc(String taskPk) {
		return this.getHumActHandler(taskPk).getDeliverOrgDesc(taskPk);
	}
	public WfmFlowUserDesc[] getDeliverUserDesc(String taskPk, String deptPk) {
		return this.getHumActHandler(taskPk).getDeliverUserDesc(taskPk, deptPk);
	}
	protected IHumActHandler getHumActHandler(String taskPk) {
		if (taskPk == null) {
			return null;
		}
		Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		String serverClass = humAct.getDelegatorClass();
		IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(serverClass);
		return humActHandler;
	}
	public String getWordUrlByFrmDefPk(String frmDefPk) {
		return null;
	}
	public String getWordUrlbyTaskPk(String taskPk) {
		return null;
	}
}
