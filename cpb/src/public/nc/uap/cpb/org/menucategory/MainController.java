package nc.uap.cpb.org.menucategory;

import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelMultiCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
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
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;

public class MainController<T extends WebElement> extends AbstractWidgetController implements IController {
  private static final long serialVersionUID=8990769129712284556L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
  }
  public void onAdd(  MouseEvent<T> mouseEvent){
		getCurrentWinCtx().addAppAttribute(MenucategoryConstants.OPERATE_STATUS,
				MenucategoryConstants.ADD_OPERATE);
		getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"新增菜单组");
  }
  public void onEdit(  MouseEvent<T> mouseEvent){
		getCurrentWinCtx().addAppAttribute(MenucategoryConstants.OPERATE_STATUS,
				MenucategoryConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("ds_menucategory");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的数据！");
		getCurrentWinCtx().addAppAttribute(MenucategoryConstants.DATA, row);
		getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"修改菜单组");
  }
  public void pluginedit_plugin(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_menucategory");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				MenucategoryConstants.OPERATE_STATUS);
		Row row = null;
		if (MenucategoryConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (MenucategoryConstants.EDIT_OPERATE.equals(opeStatus)) {
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
			CpMenuCategoryVO vo = (CpMenuCategoryVO) vos[0];
			checkDupliVO(vo);
			checkFilterClass(vo);
			if (vo.getPk_menucategory() == null|| vo.getPk_menucategory().length() == 0) {
				CpbServiceFacility.getMenuService().addMenuCategory(vo);
			} else {
				CpbServiceFacility.getMenuService().updateMenuCategory(vo);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
  }
  private void checkFilterClass(SuperVO vo){
	    CpMenuCategoryVO headvo = (CpMenuCategoryVO) vo;
		String filterclass = headvo.getFilterclass();
		if(filterclass==null||"".equals(filterclass))
			return;
		try{
			if(!(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter)){
				throw new LfwRuntimeException("过滤器必须实现IMenuItemFilter类！");
			}
		}catch(Exception e){
			Logger.error(e, e);
			throw new LfwRuntimeException("过滤器必须实现IMenuItemFilter类！");
		}
	}
  /** 
 * 保存，更新前检查菜单组编码，名称是否重复
 * @param vo
 */
  private void checkDupliVO(  SuperVO vo){
	  CpMenuCategoryVO headvo = (CpMenuCategoryVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				MenucategoryConstants.OPERATE_STATUS);
		String code = headvo.getId();
		String name = headvo.getTitle();
		String where = " (id='" + code
				+ "' or title='" + name + "')";
		try {
			CpMenuCategoryVO[] respvos = CpbServiceFacility.getMenuQry().getMenuCategory(where);
			if (respvos == null || respvos.length < 1)
				return;
			if (MenucategoryConstants.ADD_OPERATE.equals(opeStatus)
					|| (MenucategoryConstants.EDIT_OPERATE.equals(opeStatus) && !respvos[0].getPk_menucategory().equals(
									headvo.getPk_menucategory())))
				throw new LfwRuntimeException("菜单组编码/菜单组名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
  }
  @Override public String getMasterDsId(){
    return "ds_menucategory";
  }
  public void onDel(  MouseEvent<T> mouseEvent){
	  UifDelMultiCmd cmd = new UifDelMultiCmd(getMasterDsId());
	  cmd.execute();
  }
  public void onDataLoad(  DataLoadEvent dataLoadEvent){
	    Dataset ds = dataLoadEvent.getSource();
			CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	  }
	  public void onAfterRowSelect(  DatasetEvent datasetEvent){
	    Dataset ds = datasetEvent.getSource();
			CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
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
