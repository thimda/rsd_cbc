package nc.uap.cpb.org.plugin;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.IPtPluginQryService;
import nc.uap.cpb.org.itf.IPtPluginService;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;

public class MainController<T extends WebElement> extends
		AbstractWidgetController implements IController {
	private static final long serialVersionUID = 8990769129712284556L;

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}	

	@Override
	public String getMasterDsId() {
		return "pointds";
	}
	
	public void onDataLoad_pt_extpoint(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}
	
	public void onDataLoad_pt_extension(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}
	
	public void onAfterRowSelect_pt_extension(  DatasetEvent e){
		Dataset ds = e.getSource();
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("请选中要操作的行!");
		UFBoolean isactive = row.getUFBoolean(ds.nameToIndex("isactive"));
		MenubarComp mc = (MenubarComp) AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView().getViewMenus().getMenuBar("exmenu");
		boolean status = isactive != null && isactive.booleanValue();
		mc.getItem("cancel").setEnabled(status);
		mc.getItem("save").setEnabled(!status);
	}
	public void onAfterRowSelect_pt_extpoint(  DatasetEvent datasetEvent){
		  Dataset ds = datasetEvent.getSource();
		  CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
	
	public void onActive(  MouseEvent<?> mouseEvent){
		doHandle("save");
	  }
	
	public void onForbid(  MouseEvent<?> mouseEvent){
		doHandle("cancel");
	}
	
	private void doHandle(String id){
		LfwWidget wd = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		Dataset ds = wd.getViewModels().getDataset("exds");
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("请选中要操作的行!");
		UFBoolean isactive = UFBoolean.valueOf(StringUtils.equals(id, "save"));
		String pk = row.getString(ds.nameToIndex("pk_extension"));
		IPtPluginQryService qryservice = NCLocator.getInstance().lookup(IPtPluginQryService.class);
		try {
			PtExtension ex = qryservice.getExtension(pk);
			ex.setIsactive(isactive);
			NCLocator.getInstance().lookup(IPtPluginService.class).update(ex);
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(),e1);
		}
		PluginManager.newIns().refresh();
		MenubarComp mc = wd.getViewMenus().getMenuBar("exmenu");
		mc.getItem("cancel").setEnabled(isactive.booleanValue());
		mc.getItem("save").setEnabled(!isactive.booleanValue());
		
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd("pointds"));
		
//		Dataset parentDs = wd.getViewModels().getDataset("pointds");
//		Row pr = parentDs.getSelectedRow();
//		if(pr != null){
//			String point = pr.getString(parentDs.nameToIndex("point"));
//			try {
//				PtExtension[] exs = qryservice.getExtensionByPoint(point);
//				new SuperVO2DatasetSerializer().serialize(exs, ds, Row.STATE_NORMAL);
//			} catch (CpbBusinessException e1) {
//				LfwLogger.error(e1.getMessage(),e1);
//			}
//		}
		//InteractionUtil.showMessageDialog("操作成功!");
	}

}
