package nc.uap.cpb.org.plugin;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.uif.listener.UifMouseListener;
import nc.uap.portal.plugins.PluginManager;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;
/**
 * 插件激活/禁用
 *
 */
public class ExStatusMouseListener extends UifMouseListener<MenuItem> {
	public ExStatusMouseListener(LfwPageContext arg0, String arg1) {
		super(arg0, arg1);
	}

	@Override
	public void onclick(MouseEvent<MenuItem> e) {
		String id = e.getSource().getId();
		LfwWidget wd = getGlobalContext().getPageMeta().getWidget("main");
		Dataset ds = wd.getViewModels().getDataset("exds");
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("请选中要操作的行!");
		UFBoolean isactive = UFBoolean.valueOf(StringUtils.equals(id, "save"));
		String pk = row.getString(ds.nameToIndex("pk_extension"));
		try {
			CpExtension ex = CpbServiceFacility.getPluginQryService().getExtension(pk);
			ex.setIsactive(isactive);
			CpbServiceFacility.getPluginService().update(ex);
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(),e1);
		}
		PluginManager.newIns().refresh();
		MenubarComp mc = wd.getViewMenus().getMenuBar("exmenu");
		mc.getItem("cancel").setEnabled(Boolean.FALSE);
		mc.getItem("save").setEnabled(Boolean.FALSE);
		Dataset parentDs = wd.getViewModels().getDataset("pointds");
		Row pr = parentDs.getSelectedRow();
		if(pr != null){
			String point = pr.getString(parentDs.nameToIndex("point"));
			try {
				CpExtension[] exs = CpbServiceFacility.getPluginQryService().getExtensionByPoint(point);
				new SuperVO2DatasetSerializer().serialize(exs, ds, Row.STATE_NORMAL);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(),e1);
			}
		}
		InteractionUtil.showMessageDialog("操作成功!");
	}
}
