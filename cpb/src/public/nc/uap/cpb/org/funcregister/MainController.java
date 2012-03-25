package nc.uap.cpb.org.funcregister;

import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;

public class MainController<T extends WebElement> extends
		AbstractWidgetController implements IController {
	private static final long serialVersionUID = 8990769129712284556L;

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}

	public void onAdd(MouseEvent<T> mouseEvent) {
		String pk_appscategory = (String) getCurrentWinCtx().getAppAttribute("pk_appscategory");
		if (pk_appscategory == null||"".equals(pk_appscategory))
			throw new LfwRuntimeException("请先选择功能节点分组!");	
		getCurrentWinCtx().addAppAttribute(FuncregisterConstants.OPERATE_STATUS,
				FuncregisterConstants.ADD_OPERATE);
		getCurrentWinCtx().popView("edit_node",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"新增功能节点");
	}

	public void onEdit(MouseEvent<T> mouseEvent) {
		getCurrentWinCtx().addAppAttribute(FuncregisterConstants.OPERATE_STATUS,
				FuncregisterConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("ds_appsnode");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的功能节点！");
		getCurrentWinCtx().addAppAttribute(FuncregisterConstants.DATA, row);
		getCurrentWinCtx().popView("edit_node",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"修改功能节点");
	}

	public void pluginedit_node_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_appsnode");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				FuncregisterConstants.OPERATE_STATUS);
		Row row = null;
		String pk_appscategory = (String) getCurrentWinCtx().getAppAttribute("pk_appscategory");
		if (FuncregisterConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			row.setValue(ds.nameToIndex("pk_appscategory"), pk_appscategory);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (FuncregisterConstants.EDIT_OPERATE.equals(opeStatus)) {
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
				.getCurrentWindowContext().closeViewDialog("edit_node");
	}

	private void setValues(Row row, Dataset ds, Map map) {
		TranslatedRow r = (TranslatedRow) map.get("row");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}

	private void onVoSaves(SuperVO[] vos) {
		if (vos == null || vos.length == 0) {
			return;
		}
		try {
			CpAppsNodeVO vo = (CpAppsNodeVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_appsnode() == null
					|| vo.getPk_appsnode().length() == 0) {
				CpbServiceFacility.getCpAppsNodeBill().add(vo);
			} else {
				CpbServiceFacility.getCpAppsNodeBill().update(vo);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
	}

	/**
	 * 保存，更新前检查菜单组编码，名称是否重复
	 * 
	 * @param vo
	 */
	private void checkDupliVO(SuperVO vo) {
		CpAppsNodeVO headvo = (CpAppsNodeVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				FuncregisterConstants.OPERATE_STATUS);
		String code = headvo.getId();
		String name = headvo.getTitle();
		String where = " (id='" + code + "' or title='" + name + "')";
		try {
			CpAppsNodeVO[] appsnodes = CpbServiceFacility.getPortalManagerAppService().getAppsNodeVos(where);		
			if (appsnodes == null || appsnodes.length < 1)
				return;
			if (FuncregisterConstants.ADD_OPERATE.equals(opeStatus)
					|| (FuncregisterConstants.EDIT_OPERATE.equals(opeStatus) && !appsnodes[0].getPk_appsnode().equals(
									headvo.getPk_appsnode())))
				throw new LfwRuntimeException("节点编码/名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

	@Override
	public String getMasterDsId() {
		return "ds_appsnode";
	}

	public void onDel(MouseEvent<T> mouseEvent) {
		UifDelCmd cmd = new UifDelCmd(getMasterDsId(), getAggVoClazz());
		cmd.execute();
	}
	public void pluginappscategory_plugin(Map keys){
		  LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		  Dataset ds = main.getViewModels().getDataset("ds_appsnode");
		  TranslatedRow r = (TranslatedRow) keys.get("appscategory_click");
		  final String pk_appscategory = (String)r.getValue("pk_appscategory");
		  getCurrentWinCtx().addAppAttribute("pk_appscategory",pk_appscategory);
		  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
			  protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
					String where =" pk_appscategory='"+pk_appscategory+"'";
					ds.setLastCondition(where);
					return where ;			  
				}
			});
	  }
	
	  public void onAfterRowSelect_ds_appsnode(  DatasetEvent datasetEvent){
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
