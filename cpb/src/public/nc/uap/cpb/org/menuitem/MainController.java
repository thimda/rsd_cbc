package nc.uap.cpb.org.menuitem;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDelMultiCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import java.util.Map;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.lang.UFBoolean;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.event.MouseEvent;
public class MainController<T extends WebElement> extends AbstractWidgetController implements IController {
  private static final long serialVersionUID=8990769129712284556L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
  }
  public void onAdd(  MouseEvent<T> mouseEvent){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_menuitem");
		Row row = ds.getSelectedRow();
		String pk_menucategory = (String) getCurrentWinCtx().getAppAttribute("pk_menucategory");
		if (pk_menucategory == null||"".equals(pk_menucategory))
			throw new LfwRuntimeException("请先选择菜单分组!");
		if(row!=null){
			UFBoolean isnotleaf = row.getUFBoolean(ds.nameToIndex("isnotleaf"));
			if(!(isnotleaf!=null&&Boolean.TRUE.equals(isnotleaf.booleanValue())))
				throw new LfwRuntimeException("不能在该菜单上创建!");
		}
		getCurrentWinCtx().addAppAttribute(MenuitemConstants.OPERATE_STATUS,
				MenuitemConstants.ADD_OPERATE);
		getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"新增菜单项");
  }
  public void onEdit(  MouseEvent<T> mouseEvent){
		getCurrentWinCtx().addAppAttribute(MenuitemConstants.OPERATE_STATUS,
				MenuitemConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("ds_menuitem");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的菜单项！");
		getCurrentWinCtx().addAppAttribute(MenuitemConstants.DATA, row);
		getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"修改菜单项");
  }
  public void pluginedit_plugin(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_menuitem");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				MenuitemConstants.OPERATE_STATUS);
		Row prow = ds.getSelectedRow();	
		Row row = null;
		String pk_menucategory = (String) getCurrentWinCtx().getAppAttribute("pk_menucategory");
		if (MenuitemConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			if(prow != null) {
				pk_menucategory = prow.getString(ds.nameToIndex("pk_menucategory"));
				String pk_parent = (String) prow.getValue(ds.nameToIndex("pk_menuitem"));	
				row.setValue(ds.nameToIndex("pk_parent"),pk_parent);
			}
			row.setValue(ds.nameToIndex("pk_menucategory"), pk_menucategory);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (MenuitemConstants.EDIT_OPERATE.equals(opeStatus)) {
			row = ds.getSelectedRow();
			setValues(row, ds, keys);
		}
		UifSaveCmd cmd = new UifSaveCmd(getMasterDsId(),getDetailDsIds(),getAggVoClazz(),false){			
			protected void onVoSave(AggregatedValueObject agggo) {
				onVoSaves(new SuperVO[]{(SuperVO)agggo.getParentVO()});
			}
		};
		cmd.execute();
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("edit");
  }
  private void setValues(  Row row,  Dataset ds,  Map map){
    TranslatedRow r = (TranslatedRow) map.get("row");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
  }
  private void onVoSaves(  SuperVO[] vos){
    if (vos == null || vos.length == 0) {
			return;
		}
		try {
			CpMenuItemVO vo = (CpMenuItemVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_menuitem() == null
					|| vo.getPk_menuitem().length() == 0) {
				CpbServiceFacility.getMenuService().addMenuitem(vo);
			} else {
				CpbServiceFacility.getMenuService().updateMenuitem(vo);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
  }
  /** 
 * 保存，更新前检查菜单组编码，名称是否重复
 * @param vo
 */
  private void checkDupliVO(  SuperVO vo){
    CpMenuItemVO headvo = (CpMenuItemVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				MenuitemConstants.OPERATE_STATUS);
		String pk_menucategory = headvo.getPk_menucategory();
		String code = headvo.getCode();
		String name = headvo.getName();
		String where = "pk_menucategory = '"+pk_menucategory+"' and (code='" + code + "' or name='" + name + "')";
		try {
			CpMenuItemVO[] respvos = CpbServiceFacility.getMenuQry().getMenuItem(where)		;			
			if (respvos == null || respvos.length < 1)
				return;
			if (MenuitemConstants.ADD_OPERATE.equals(opeStatus)
					|| (MenuitemConstants.EDIT_OPERATE.equals(opeStatus) && !respvos[0].getPk_menuitem().equals(
									headvo.getPk_menuitem())))
				throw new LfwRuntimeException("在相同菜单分组下，菜单编码/菜单名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
  }
  @Override public String getMasterDsId(){
    return "ds_menuitem";
  }
  public void onDel(  MouseEvent<T> mouseEvent){
    UifDelMultiCmd cmd = new UifDelMultiCmd(getMasterDsId());
	cmd.execute();
  }
  public void onDataLoad_ds_menucategory(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void onAfterRowSelect_ds_menucategory(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
  public void onAfterRowSelect_ds_menuitem(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
  public void pluginmenucategory_plugin(Map keys){
	  LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
	  Dataset ds = main.getViewModels().getDataset("ds_menuitem");
	  TranslatedRow r = (TranslatedRow) keys.get("menucategory_click");
	  final String pk_menucategory = (String)r.getValue("pk_menucategory");
	  getCurrentWinCtx().addAppAttribute("pk_menucategory",pk_menucategory);
	  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
		  protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String where =" pk_menucategory='"+pk_menucategory+"' ";
				ds.setLastCondition(where);
				return where ;			  
			}
		});
  }
	/**
	 * 简单查询
	 * @param keys
	 */
	 public void pluginsimpleQuery_plugin(Map<Object,Object> keys) {
		 FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
	     String wheresql = whereSql.getWhere();
	     QueryCmd cmd = new QueryCmd("main", getMasterDsId(), wheresql);
	     cmd.excute();
		 }
	 /**
	  * 查询计划
	  * @param keys
	  */
   public void pluginqueryPlan_plugin(Map keys) {
		 
	 }
}
