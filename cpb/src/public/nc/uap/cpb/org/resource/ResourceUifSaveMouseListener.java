package nc.uap.cpb.org.resource;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.uif.delegator.IUifDeletator;
import nc.uap.lfw.core.uif.listener.UifSaveMouseListener;

public class ResourceUifSaveMouseListener<T extends WebElement> extends UifSaveMouseListener<T> {
	public ResourceUifSaveMouseListener(LfwPageContext arg0, String arg1) {
		super(arg0, arg1);
	}

	public void onclick(MouseEvent<T> e) {
		LfwWidget widget = this.getGlobalContext().getPageMeta().getWidget(getWidgetId());
		Dataset ds = widget.getViewModels().getDataset("groupds");
		if (ds.getSelectedRow() == null)
			return;

		// 设置为修改状态
		Dataset resourceds = widget.getViewModels().getDataset(getMasterDsId());
		Row[] rows = resourceds.getCurrentRowData().getRows();
		if (rows == null || rows.length < 1)
			return;
		for (Row row : rows) {
			row.setState(Row.STATE_UPDATE);
		}

		// 更新
		IUifDeletator delegator = new ResourceSaveDelegator(getWidgetId(), getMasterDsId(), getDetailDsIds(), getAggVoClazz(), bodyNotNull());
		delegator.setGlobalContext(getGlobalContext());
		delegator.execute();

		// 更新后使页面仍旧处于编辑状态
		setPageEdibale();
	}

	protected String getWidgetId() {
		return "main";
	}

	protected String getMasterDsId() {
		return "resourceds";
	}

	/**
	 * 使页面一值处于可编辑状态
	 */
	private void setPageEdibale() {
		LfwWidget widget = this.getGlobalContext().getPageMeta().getWidget(getWidgetId());
		Dataset resourceds = widget.getViewModels().getDataset(getMasterDsId());
		resourceds.setEnabled(true);
	}
}
