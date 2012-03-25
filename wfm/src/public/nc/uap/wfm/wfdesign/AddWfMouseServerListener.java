package nc.uap.wfm.wfdesign;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
public class AddWfMouseServerListener extends MouseServerListener<MenuItem> {
	public AddWfMouseServerListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	public void onclick(MouseEvent<MenuItem> e) {
		LfwWidget mainWidget = this.getGlobalContext().getPageMeta().getWidget("main");
		Dataset dsFlwType = mainWidget.getViewModels().getDataset("ds_flwtype");
		Row selectedRow = dsFlwType.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中所属的流程类型");
		}
		String flwTypePk = (String) selectedRow.getValue(dsFlwType.nameToIndex("pk_flwtype"));
		LfwWidget editWidget = this.getGlobalContext().getPageMeta().getWidget("edit");
		editWidget.setVisible(true);
		Dataset dsProDef = editWidget.getViewModels().getDataset("ds_prodef");
		dsProDef.clear();
		dsProDef.setCurrentKey(Dataset.MASTER_KEY);
		Row emptyRow = dsProDef.getEmptyRow();
		emptyRow.setValue(dsProDef.nameToIndex("flwtype"), flwTypePk);
		String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		emptyRow.setValue(dsProDef.nameToIndex("pk_group"), groupPk);
		dsProDef.addRow(emptyRow);
		dsProDef.setRowSelectIndex(0);
		dsProDef.setEnabled(true);
	}
}
