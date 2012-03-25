package nc.uap.cpb.org.plugin;

import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.lang.UFBoolean;

/**
 * 插件菜单状态控制listener
 *
 */
public class ExDsServerListener extends nc.uap.lfw.core.event.deft.DefaultDatasetServerListener{

	public ExDsServerListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}

	@Override
	public void onAfterRowSelect(DatasetEvent e) {
		Dataset ds = e.getSource();
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("请选中要操作的行!");
		UFBoolean isactive = row.getUFBoolean(ds.nameToIndex("isactive"));
		MenubarComp mc = getCurrentContext().getWidget().getViewMenus().getMenuBar("exmenu");
		boolean status = isactive != null && isactive.booleanValue();
		mc.getItem("cancel").setEnabled(status);
		mc.getItem("save").setEnabled(!status);
	}
	
}
