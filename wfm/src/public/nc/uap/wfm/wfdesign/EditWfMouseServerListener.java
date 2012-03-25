package nc.uap.wfm.wfdesign;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
public class EditWfMouseServerListener extends MouseServerListener<MenuItem> {
	public EditWfMouseServerListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	public void onclick(MouseEvent<MenuItem> e) {
		LfwWidget mainWidget = this.getGlobalContext().getPageMeta().getWidget("main");
		Dataset dsMainProDef = mainWidget.getViewModels().getDataset("ds_prodef");
		Row selectedRow = dsMainProDef.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中要修改的流程定义");
		}
		LfwWidget editWidget = this.getGlobalContext().getPageMeta().getWidget("edit");
		Dataset dsEditProDef = editWidget.getViewModels().getDataset("ds_prodef");
		dsEditProDef.clear();
		dsEditProDef.setCurrentKey(Dataset.MASTER_KEY);
		dsEditProDef.addRow(selectedRow);
		dsEditProDef.setRowSelectIndex(0);
		dsEditProDef.setEnabled(true);
		editWidget.setVisible(true);
		
		
	
	}
}
