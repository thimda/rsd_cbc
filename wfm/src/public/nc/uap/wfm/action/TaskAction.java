package nc.uap.wfm.action;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.dbl.itf.IFrmDefQry;
import nc.uap.dbl.itf.IFrmTmpGen;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormDefinitionVO;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.wfm.bridge.IBillFormService;
import nc.uap.wfm.bridge.IWfmBillTypeQry;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.BackTaskInfoCtx;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import org.apache.commons.lang.StringUtils;
/**
 * @author tianchw
 * 
 */
@Servlet(path = "/task") public class TaskAction extends BaseAction {
	/**
	 * 启动一个流程实例
	 * 
	 * @throws WfmServiceException
	 */
	@SuppressWarnings("restriction") @Action(method = "get") public void start() throws WfmServiceException {
		try {
			String frmDefPk = request.getParameter("frmdefpk");
			frmDefPk = "0001AA1000000000CO1C";
			if (frmDefPk == null || frmDefPk.length() == 0) {
				return;
			}
			ProDef proDef = ProDefsContainer.getProDefByFlowTypePk(frmDefPk);
			// proDef.getFlwtype()
			String publishtype = request.getParameter("publishtype");
			String model = null;
			proDef = ProDefsContainer.getProDefByFlowTypePk(frmDefPk);
			StartEvent start = proDef.getStart();
			if (!start.isNotCoverMakePort()) {
				String flwDataDispModel = ((ITaskExtHandler) WfmClassUtil.loadClass(((HumAct) (WfmTaskUtil.getStratPort(proDef))).getDelegatorClass())).getFlowDataDispModel(null);
				if (flwDataDispModel == null || flwDataDispModel.length() == 0) {
					model = WfmConstants.FlwDataDispModel;
				}
			}
			if (model == null || model.length() == 0) {
				model = WfmConstants.FlwDataDispModel;
			}
			String url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um";
			url = url + "?pageId=flwdatadisps";
			url = url + "&model=" + model;
			url = url + "&frmdefpk=" + frmDefPk;
			url = url + "&publishtype=" + publishtype;
			getResponse().sendRedirect(url);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	/**
	 * 执行一个活动节点
	 * 
	 * @throws PdblBusinessException
	 */
	@SuppressWarnings("restriction") @Action(method = "get") public void execute() throws WfmServiceException {
		try {
			String taskPk = request.getParameter("taskpk");
			if (taskPk == null || taskPk.length() == 0) {
				return;
			}
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			IBillFormService billFormService = NCLocator.getInstance().lookup(IWfmBillTypeQry.class).getBillFormService(task.getProDef().getFlwtype().getPk_flwtype());
			String model = billFormService.getPageModelClazz(task);
			if (model == null || model.length() == 0) {
				model = WfmConstants.FlwDataDispModel;
			}
			String tokenId = request.getParameter(WfmConstants.TokenId);
			String eventSrc = request.getParameter(WfmConstants.EventSrc);
			String url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um?pageId=flwdatadisps";
			url = url + "&" + WfmConstants.Model + "=" + model;
			url = url + "&" + WfmConstants.TaskPk + "=" + taskPk;
			if (StringUtils.isNotBlank(tokenId)) {
				url = url + "&" + WfmConstants.TokenId + "=" + tokenId;
			}
			if (StringUtils.isNotBlank(eventSrc)) {
				url = url + "&" + WfmConstants.EventSrc + "=" + eventSrc;
			}
			getResponse().sendRedirect(url);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("restriction") @Action(method = "get") public void back() throws WfmServiceException {
		PrintWriter pw = null;
		WfmServiceException be = null;
		try {
			pw = response.getWriter();
			String taskPk = request.getParameter("taskpk");
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			BackTaskInfoCtx flwInfoCtx = new BackTaskInfoCtx();
			flwInfoCtx.setTaskPk(task.getPk_task());
			flwInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			pw.write(URLEncoder.encode("", "UTF-8"));
		} catch (Exception e) {
			if (e instanceof WfmServiceException) {
				be = (WfmServiceException) e;
			} else {
				be = new WfmServiceException(e);
			}
		} finally {
			if (be != null) {
				response.resetBuffer();
				response.setHeader("exception", "exception");
				try {
					pw.write(URLEncoder.encode(be.getMessage().toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {}
			}
			pw.close();
		}
	}
	@SuppressWarnings("restriction") @Action(method = "get") public void fill() throws WfmServiceException {
		try {
			String frmDefPk = request.getParameter("frmdefpk");
			if (StringUtils.isNotBlank(frmDefPk)) {
				IFrmDefQry frmDefQry = NCLocator.getInstance().lookup(IFrmDefQry.class);
				DblFormDefinitionVO frmDefVO = frmDefQry.getFrmDefByPk(frmDefPk);
				String pageMeta = frmDefVO.getSelfdefclass();
				if (pageMeta == null || pageMeta.length() == 0) {
					pageMeta = DblConstants.DefalutPageMeta;
				}
				IFrmTmpQry frmTmpQry = NCLocator.getInstance().lookup(IFrmTmpQry.class);
				DblFormTemplateVO frmTmpVO = frmTmpQry.getFrmTmpByFrmDefPk(frmDefPk);
				IFrmTmpGen frmTmpGen = NCLocator.getInstance().lookup(IFrmTmpGen.class);
				frmTmpGen.genDispTmp(frmTmpVO, null, null, null, "PC");
				String url = "/pbase/core/" + frmTmpVO.getPk_formtemplate() + ".jsp?";
				url = url + "pageId=" + DblConstants.Path_FrmTmpDisp + "&";
				url = url + "model=" + pageMeta + "&";
				url = url + "frmdefpk=" + frmDefPk;
				getResponse().sendRedirect(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
