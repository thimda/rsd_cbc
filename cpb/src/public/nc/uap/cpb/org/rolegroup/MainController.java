package nc.uap.cpb.org.rolegroup;

import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelMultiCmd;
import nc.uap.lfw.core.cmd.UifEditCmd;
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
		String type = (String) getCurrentWinCtx().getAppAttribute("type");
		if (type == null || "".equals(type))
			throw new LfwRuntimeException("请先选择角色组类型!");
		getCurrentWinCtx().addAppAttribute(RoleGroupConstants.OPERATE_STATUS,
				RoleGroupConstants.ADD_OPERATE);
		LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset("cp_rolegroup");
		Row prow = ds.getSelectedRow();
		if (prow != null){
			getCurrentWinCtx().addAppAttribute("pk_parent",prow.getString(ds.nameToIndex("pk_rolegroup")));
			getCurrentWinCtx().addAppAttribute("pk_org",prow.getString(ds.nameToIndex("pk_org")));
		}else{
			getCurrentWinCtx().addAppAttribute("pk_parent",null);
			getCurrentWinCtx().addAppAttribute("pk_org",null);
		}
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "新增角色组");
	}

	public void onEdit(MouseEvent<T> mouseEvent) {
		getCurrentWinCtx().addAppAttribute(RoleGroupConstants.OPERATE_STATUS,
				RoleGroupConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("cp_rolegroup");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的数据！");
		getCurrentWinCtx().addAppAttribute(RoleGroupConstants.DATA, row);
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "修改角色组");
	}

	public void pluginedit_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("cp_rolegroup");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				RoleGroupConstants.OPERATE_STATUS);
		Row row = null;
		if (RoleGroupConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (RoleGroupConstants.EDIT_OPERATE.equals(opeStatus)) {
			row = ds.getSelectedRow();
			setValues(row, ds, keys);
		}
//		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
//		SuperVO[] vos = serializer.serialize(ds, ds.getSelectedRow());
//		onVoSave(vos);
//		row.setValue(ds.nameToIndex(vos[0].getPKFieldName()), vos[0].getPrimaryKey());
		UifSaveCmd cmd = new UifSaveCmd(getMasterDsId(),getDetailDsIds(),getAggVoClazz(),false){			
			protected void onVoSave(AggregatedValueObject agggo) {
				onVoSaves(new SuperVO[]{(SuperVO)agggo.getParentVO()});
			}
		};
		cmd.execute();
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("edit");
	}

	public void pluginmanage_type_plugin(Map keys) {
		LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset("cp_rolegroup");
		TranslatedRow r = (TranslatedRow) keys.get("manage_type_row");
		final String type = (String) r.getValue("value");
		getCurrentWinCtx().addAppAttribute("type", type);
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
				String where = " pk_group= '"+pk_group+"' and  type='" + type + "'";
				ds.setLastCondition(where);
				return where;
			}
		});
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
			CpRoleGroupVO vo = (CpRoleGroupVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_rolegroup() == null
					|| vo.getPk_rolegroup().length() == 0) {
				CpbServiceFacility.getCpRoleGroupBill().addPtRoleGroupVOs(
						new CpRoleGroupVO[] { vo });
			} else {
				CpbServiceFacility.getCpRoleGroupBill().updatePtRoleGroupVO(vo);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
	}

	/**
	 * 保存，更新前检查角色组编码，名称是否重复
	 * 
	 * @param vo
	 */
	private void checkDupliVO(SuperVO vo) {
		CpRoleGroupVO headvo = (CpRoleGroupVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				RoleGroupConstants.OPERATE_STATUS);
		String code = headvo.getGroupcode();
		String name = headvo.getGroupname();
		String pk_group = headvo.getPk_group();
		String where = " pk_group ='" + pk_group + "' and (groupcode='" + code+ "' or groupname='" + name + "')";

		try {
			CpRoleGroupVO[] respvos = CpbServiceFacility.getCpRoleGroupQry()
					.getRoleGroupVos(where);
			if (respvos == null || respvos.length < 1)
				return;
			if (RoleGroupConstants.ADD_OPERATE.equals(opeStatus)
					|| (RoleGroupConstants.EDIT_OPERATE.equals(opeStatus) && !respvos[0]
							.getPk_rolegroup().equals(headvo.getPk_rolegroup())))
				throw new LfwRuntimeException("角色组编码/角色组名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

	@Override
	public String getMasterDsId() {
		return "cp_rolegroup";
	}

	public void onDel(MouseEvent<T> mouseEvent) {
		LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset("cp_rolegroup");
		Row row = ds.getSelectedRow();
		if(row==null)
			return;
		String pk_rolegroup = row.getString(ds.nameToIndex("pk_rolegroup"));		
		try {
			CpRoleGroupVO[] rolegroupvos = CpbServiceFacility.getCpRoleGroupQry().getRoleGroupByParent(pk_rolegroup);
			if(rolegroupvos!=null&&rolegroupvos.length>0)
				throw new LfwRuntimeException("你选择的角色组存在子角色组，不允许删除!");
			CpRoleVO[] rolevos  = CpbServiceFacility.getCpRoleQry().getRoleByRoleGroup(pk_rolegroup);
			if(rolevos!=null&&rolevos.length>0)
				throw new LfwRuntimeException("你选择的角色组存在关联角色，不允许删除!");
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new LfwRuntimeException("删除时出错了!");			
		}		
		UifDelMultiCmd cmd = new UifDelMultiCmd(getMasterDsId());
		cmd.execute();
	}

	public void onDataLoad_cp_rolegroup(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}

	public void onAfterRowSelect(DatasetEvent datasetEvent) {
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
	     QueryCmd cmd = new QueryCmd("main", getMasterDsId(), wheresql){
	    	 protected String buildWhere(String whereSql){
	    		 String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
	    		 return whereSql +" and pk_group = '" + pk_group + "'";
	    	 }
	     };
	     cmd.excute();
	     }
	 /**
	  * 查询计划
	  * @param keys
	  */
     public void pluginqueryPlan_plugin(Map keys) {
		 
	 }
}
