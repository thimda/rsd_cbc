package nc.uap.wfm.listener;
import nc.bs.framework.common.NCLocator;
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
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.vo.WfmTaskVO;
public class MyStartTasksActionsListener extends MouseServerListener<MenuItem> {
	public MyStartTasksActionsListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	@Override
	public void onclick(MouseEvent<MenuItem> e) {
		String source = e.getSource().getId().toString();
		LfwWidget mainWidget = this.getGlobalContext().getWidgetContext(WfmConstants.WidgetMain).getWidget();
		ViewModels viwMdls = mainWidget.getViewModels();
		Dataset dsUndneProIns = viwMdls.getDataset(WfmConstants.DsUndneProIns);
		Dataset dsCmpltProIns = viwMdls.getDataset(WfmConstants.DsCmpltProIns);
		/**
		 *流程删除
		 */
		if (WfmConstants.MnuItmDel.equalsIgnoreCase(source)) {
			Row selectedRow = dsCmpltProIns.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要删除的流程实例");
			}
		}
		/**
		 * 流程催办
		 */
		else if (WfmConstants.MnuItmRemind.equalsIgnoreCase(source)) {
			Row selectedRow = dsUndneProIns.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要催办的流程实例");
			}
		}
		/**
		 * 流程明细
		 */
		else if (WfmConstants.MnuItmDetail.equalsIgnoreCase(source)) {
			String currentItmId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(WfmConstants.CurrentItemId);
			Dataset currentDs = null;
			if (WfmConstants.TablUndneProIns.equalsIgnoreCase(currentItmId)) {
				currentDs = dsUndneProIns;
			} else {
				currentDs = dsCmpltProIns;
			}
			Row selectedRow = currentDs.getSelectedRow();
			if (selectedRow == null) {
				throw new LfwRuntimeException("请选中要查看的流程实例");
			}
			String proInsPk = (String) selectedRow.getValue(currentDs.nameToIndex("pk_proins"));
			IWfmTaskQry taskQry = NCLocator.getInstance().lookup(IWfmTaskQry.class);
			try {
				WfmTaskVO taskVo = taskQry.getLasterUndneTaskByProInsPk(proInsPk);
				if (taskVo == null) {
					return;
				}
				String url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um?pageId=flwdatadetail&";
				
				//url = url + "model=" + WfmConstants.FlwDataDetailModel + "&";
				url = url + "taskpk=" + taskVo.getPk_task();
				this.getGlobalContext().addExecScript("document.location.href='" + url + "'");
			} catch (WfmServiceException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		}
	}
}
