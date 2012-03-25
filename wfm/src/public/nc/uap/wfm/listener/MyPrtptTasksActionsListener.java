package nc.uap.wfm.listener;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.wfm.bridge.IBillFormService;
import nc.uap.wfm.bridge.IWfmBillTypeQry;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.BackTaskInfoCtx;
import nc.uap.wfm.context.ExeDeliverInfoCtx;
import nc.uap.wfm.context.RetractTaskInfoCtx;
import nc.uap.wfm.dftimpl.DefaultFormOper;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsBill;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public class MyPrtptTasksActionsListener extends MouseServerListener<MenuItem> {
	private final String MnuItmExecute = "execute";
	private final String MnuItmBack = "back";
	private final String MunItmRetract = "retract";
	private final String MunItmDetail = "detail";
	private final String MunItmQry = "qry";
	private final String MnuItmUerge = "uerge";
	private final String MnuItmDelete = "delete";
	public MyPrtptTasksActionsListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	@Override public void onclick(MouseEvent<MenuItem> e) {
		String source = e.getSource().getId().toString();
		LfwWidget mainWidget = this.getGlobalContext().getWidgetContext(WfmConstants.WidgetMain).getWidget();
		ViewModels viwMdls = mainWidget.getViewModels();
		Dataset dsUndneTask = viwMdls.getDataset(WfmConstants.DsUndneTask);
		Dataset dsCmpltTask = viwMdls.getDataset(WfmConstants.DsCmpltTask);
		Dataset dsPlmntTask = viwMdls.getDataset(WfmConstants.DsPlmntTask);
		Dataset dsSendTask = viwMdls.getDataset(WfmConstants.DsSendTask);
		Dataset dsUnreadTask = viwMdls.getDataset(WfmConstants.DsUndeliverTask);
		Dataset dsReadedTask = viwMdls.getDataset(WfmConstants.DsDeliveredTask);
		Dataset dsEndedTask = viwMdls.getDataset(WfmConstants.DsEndedTask);
		String url = "";
		/**
		 * 任务执行
		 */
		if (MnuItmExecute.equalsIgnoreCase(source)) {
			String cntItmId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(WfmConstants.CurrentItemId);
			Row selectedRow = null;
			String taskPk = null;
			if (WfmConstants.TablUndneTask.equalsIgnoreCase(cntItmId)) {
				selectedRow = dsUndneTask.getSelectedRow();
			}
			if (WfmConstants.TabUnReadTask.equalsIgnoreCase(cntItmId)) {
				selectedRow = dsUnreadTask.getSelectedRow();
			}
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要执行的任务");
			}
			if (WfmConstants.TablUndneTask.equalsIgnoreCase(cntItmId)) {
				taskPk = (String) selectedRow.getValue(dsUndneTask.nameToIndex(WfmConstants.PkTask));
			}
			if (WfmConstants.TabUnReadTask.equalsIgnoreCase(cntItmId)) {
				taskPk = (String) selectedRow.getValue(dsUnreadTask.nameToIndex(WfmConstants.PkTask));
			}
			if (taskPk == null || taskPk.length() == 0) {
				return;
			}
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			if (task != null) {
				String createType = task.getCreateType();
				if (Task.CreateType_Deliver.equalsIgnoreCase(createType)) {
					ExeDeliverInfoCtx flwInfoCtx = new ExeDeliverInfoCtx();
					flwInfoCtx.setTaskPk(task.getPk_task());
					flwInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
				}
			}
			WfmFlwTypeVO flwTypeVo = task.getProDef().getFlwtype();
			String serverClass = flwTypeVo.getServerclass();
			if (serverClass == null || serverClass.length() == 0) {
				serverClass = DefaultFormOper.class.getName();
			}
			IWfmFormOper frmOper = (IWfmFormOper) WfmClassUtil.loadClass(serverClass);
			url = frmOper.getHanlderUrlByTask(task);
			url = LfwRuntimeEnvironment.getRootPath() + "/" + url;
			this.getGlobalContext().addExecScript("document.location.href='" + url + "'");
		}
		/**
		 * 任务取回
		 */
		else if (MnuItmBack.equalsIgnoreCase(source)) {
			Row selectedRow = dsCmpltTask.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要取回的任务");
			}
			String taskPk = (String) selectedRow.getValue(dsCmpltTask.nameToIndex(WfmConstants.PkTask));
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			BackTaskInfoCtx flwInfoCtx = new BackTaskInfoCtx();
			flwInfoCtx.setTaskPk(task.getPk_task());
			flwInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			this.getGlobalContext().addExecScript("alert(取回处理成功');");
			this.getGlobalContext().addExecScript(this.getScript(task));
		}
		/**
		 * 任务收回
		 */
		else if (MunItmRetract.equalsIgnoreCase(source)) {
			Row selectedRow = dsSendTask.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要收回的任务");
			}
			String taskPk = (String) selectedRow.getValue(dsCmpltTask.nameToIndex(WfmConstants.PkTask));
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			RetractTaskInfoCtx flwInfoCtx = new RetractTaskInfoCtx();
			flwInfoCtx.setTaskPk(task.getPk_task());
			flwInfoCtx.setCntUserPk(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			this.getGlobalContext().addExecScript(this.getScript(task));
		} else if (MunItmDetail.equalsIgnoreCase(source)) {
			String currentItmId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(WfmConstants.CurrentItemId);
			Row row = null;
			String taskPk = "";
			if (WfmConstants.TablUndneTask.equalsIgnoreCase(currentItmId)) {
				row = dsUndneTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsUndneTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabCmpltTask.equalsIgnoreCase(currentItmId)) {
				row = dsCmpltTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsCmpltTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabPlmntTask.equalsIgnoreCase(currentItmId)) {
				row = dsPlmntTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsPlmntTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabSendTask.equalsIgnoreCase(currentItmId)) {
				row = dsSendTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsSendTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabUnReadTask.equalsIgnoreCase(currentItmId)) {
				row = dsUnreadTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsUnreadTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabReadedTask.equalsIgnoreCase(currentItmId)) {
				row = dsReadedTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsReadedTask.nameToIndex(WfmConstants.PkTask));
			} else if (WfmConstants.TabEndedTask.equalsIgnoreCase(currentItmId)) {
				row = dsEndedTask.getSelectedRow();
				if (row == null) {
					return;
				}
				taskPk = (String) row.getValue(dsEndedTask.nameToIndex(WfmConstants.PkTask));
			}
			if (taskPk == null || taskPk.length() == 0) {
				return;
			}
			url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um?pageId=flwdatadetail&";
			IWfmBillTypeQry btQry = NCLocator.getInstance().lookup(IWfmBillTypeQry.class);
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			IBillFormService frmQry = btQry.getBillFormService(task.getProDef().getFlwtype().getPk_flwtype());
			url = url + "model=" + frmQry.getDetailPageModelClazz(task) + "&";
			url = url + "taskpk=" + taskPk;
			this.getGlobalContext().addExecScript("document.location.href='" + url + "'");
		} else if (MunItmQry.equalsIgnoreCase(source)) {
			LfwWidget widget = this.getGlobalContext().getWidgetContext(WfmConstants.WidgetQuery).getWidget();
			widget.setVisible(true);
		} else if (MnuItmUerge.equalsIgnoreCase(source)) {
			LfwWidget widget = this.getGlobalContext().getWidgetContext(WfmConstants.WidgetUerge).getWidget();
			widget.setVisible(true);
		} else if (MnuItmDelete.equalsIgnoreCase(source)) {
			Row selectedRow = dsUndneTask.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要删除的流程数据");
			}
			String taskPk = (String) selectedRow.getValue(dsUndneTask.nameToIndex(WfmConstants.PkTask));
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			HumAct humAct = task.getHumActIns().getHumAct();
			ProDef proDef = task.getProDef();
			boolean flag = false;
			ProIns proIns = task.getProIns();
			if (WfmTaskUtil.getStratPort(proDef) == humAct) {
				flag = true;
			} else {
				if (!ProIns.NottStart.equalsIgnoreCase(proIns.getState())) {
					throw new LfwRuntimeException("流程处于运行中，不允许删除！");
				}
			}
			if (flag) {
				InteractionUtil.showConfirmDialog("确认框", "确认要删除吗?");
				boolean f = InteractionUtil.getConfirmDialogResult();
				if (f) {
					try {
						NCLocator.getInstance().lookup(IWfmProInsBill.class).deleteProInsByProInsPk(proIns.getPk_proins());
					} catch (WfmServiceException e1) {
						LfwLogger.error(e1.getMessage(), e1);
						throw new LfwRuntimeException(e1.getMessage());
					}
					this.getGlobalContext().addExecScript("alert('删除成功');");
					this.getGlobalContext().addExecScript(this.getScript(task));
				}
			}
		}
	}
	public String getScript(Task task) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}
