package nc.uap.cpb.org.funcregister;

import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import java.util.Map;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.event.MouseEvent;

public class AppscategoryController<T extends WebElement> extends
		AbstractWidgetController implements IController {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMasterDsId() {
		return "ds_appscategory";
	}

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}

	public void onAdd(MouseEvent<T> mouseEvent) {
		LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("appscategory").getView();
		Dataset ds = main.getViewModels().getDataset("ds_appscategory");
		Row row = ds.getSelectedRow();
		Dataset module_ds = getCurrentWinCtx().getViewContext("appscategory").getView()
				.getViewModels().getDataset("ds_module");
		Row module_row = module_ds.getSelectedRow();
		if (row == null && module_row == null)
			throw new LfwRuntimeException("请选择需要功能节点分组或者模块！");
		getCurrentWinCtx().addAppAttribute(
				FuncregisterConstants.OPERATE_STATUS,
				FuncregisterConstants.ADD_OPERATE);
		getCurrentWinCtx().popView("edit_category",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"新增功能节点分组");
	}

	public void onEdit(MouseEvent<T> mouseEvent) {
		getCurrentWinCtx().addAppAttribute(
				FuncregisterConstants.OPERATE_STATUS,
				FuncregisterConstants.EDIT_OPERATE);
		Dataset category_ds = getCurrentWinCtx().getViewContext("appscategory")
				.getView().getViewModels().getDataset("ds_appscategory");
		Row category_row = category_ds.getSelectedRow();
		if (category_row == null )
			throw new LfwRuntimeException("请选择需要修改的功能节点分组！");
		getCurrentWinCtx().addAppAttribute(FuncregisterConstants.DATA,
				category_row);
		getCurrentWinCtx().popView("edit_category",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"修改功能节点分组");
	}

	public void onDel(MouseEvent<T> mouseEvent) {
		UifDelCmd cmd = new UifDelCmd(getMasterDsId(), getAggVoClazz());
		cmd.execute();
	}

	public void pluginedit_category_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("appscategory").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_appscategory");
		Dataset module_ds = edit.getViewModels().getDataset("ds_module");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				FuncregisterConstants.OPERATE_STATUS);
		Row module_row = module_ds.getSelectedRow();
		Row prow = ds.getSelectedRow();
		Row row = null;
		if (FuncregisterConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			if (prow != null) {
				String pk_parent = (String) prow.getValue(ds
						.nameToIndex("pk_appscategory"));
				row.setValue(ds.nameToIndex("pk_parent"), pk_parent);
				row.setValue(ds.nameToIndex("pk_module"), prow.getString(ds
						.nameToIndex("pk_module")));
			} else {
				row.setValue(ds.nameToIndex("pk_module"), module_row
						.getString(module_ds.nameToIndex("pk_module")));
			}
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
				.getCurrentWindowContext().closeViewDialog("edit_category");
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
			CpAppsCategoryVO vo = (CpAppsCategoryVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_appscategory() == null
					|| vo.getPk_appscategory().length() == 0) {
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
		CpAppsCategoryVO headvo = (CpAppsCategoryVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				FuncregisterConstants.OPERATE_STATUS);
		String code = headvo.getId();
		String name = headvo.getTitle();
		String where = " (id='" + code + "' or title='" + name + "')";
		try {
			CpAppsNodeVO[] appsnodes = CpbServiceFacility
					.getPortalManagerAppService().getAppsNodeVos(where);
			if (appsnodes == null || appsnodes.length < 1)
				return;
			if (FuncregisterConstants.ADD_OPERATE.equals(opeStatus)
					|| (FuncregisterConstants.EDIT_OPERATE.equals(opeStatus) && !appsnodes[0]
							.getPk_appscategory().equals(
									headvo.getPk_appscategory())))
				throw new LfwRuntimeException("节点编码/名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

	public void onDataLoad_ds_module(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}

	public void onDataLoad_ds_appscategory(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String values = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_VALUES);
				 String pk_module = values;
				 String where = " pk_module = '"+pk_module+"'";
				 return where;
			}
		});
	}
}
