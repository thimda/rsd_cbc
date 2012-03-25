package nc.uap.cpb.org.resource;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.uif.listener.UifMouseListener;

/**
 * 资源同步类
 * 
 * 
 * @param <T>
 */
public class ResourceUifSyncMouseListener<T extends WebElement> extends UifMouseListener<T> {
	public ResourceUifSyncMouseListener(LfwPageContext arg0, String arg1) {
		super(arg0, arg1);
	}

	public void onclick(MouseEvent<T> e) {
		LfwWidget widget = this.getGlobalContext().getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("groupds");
		Row row = ds.getSelectedRow();
		if (row == null)
			return;
		// todo
		// 已注册资源
		String pk_group = row.getString(ds.nameToIndex("pk_group"));
		if (pk_group == null || pk_group.equals(""))
			throw new LfwRuntimeException("同步资源时未获取所属集团!");
		try {
			CpbServiceFacility.getCpResourceBill().groupResourcSyn(pk_group);
		} catch (CpbBusinessException e1) {
			throw new LfwRuntimeException("同步资源出错");
		}
	}

	public String getPageState() {
		return "1";
	}

	public Integer getItemIndex() {
		return null;
	}

	public String getWidgetId() {
		return "main";
	}

	public String getDatasetId() {
		return "resourceds";
	}

	public String getCardLayoutId() {
		return null;
	}
}
