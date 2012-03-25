package nc.uap.wfm.wfdesign;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
public class DeleteWfMouseServerListener extends MouseServerListener<MenuItem> {
	public DeleteWfMouseServerListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	public void onclick(MouseEvent<MenuItem> e) {
		LfwWidget mainWidget = this.getGlobalContext().getPageMeta().getWidget("main");
		Dataset dsMainProDef = mainWidget.getViewModels().getDataset("ds_prodef");
		Row selectedRow = dsMainProDef.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要删除的流程定义");
		}
		String proDefPk = (String) selectedRow.getValue(dsMainProDef.nameToIndex("pk_prodef"));
		try {
			WfmServiceFacility.getProDefBill().deleteProDefByPk(proDefPk);
		} catch (WfmServiceException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
	}
}
