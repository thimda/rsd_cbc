package nc.uap.cpb.org.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.cpb.org.itf.ICpUserRoleBill;
import nc.uap.cpb.org.pubview.EnabledateController;
import nc.uap.cpb.org.pubview.RelateOrgController;
import nc.uap.cpb.org.pubview.RelateUserController;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleOrgVO;
import nc.uap.cpb.org.vos.CpRoleRespVO;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.model.plug.TranslatedRows;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

public class MainController<T extends WebElement> extends
		AbstractWidgetController implements IController {
	private static final long serialVersionUID = 8990769129712284556L;

	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}
	
	private Row setRowValues(Row row, Dataset ds, Map map) {
		TranslatedRow r = (TranslatedRow) map.get("row");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
		return row;
	}

	private Row[] setRowsValues(Row row, Dataset ds, Map map) {
		TranslatedRows r = (TranslatedRows) map.get("row");
		String[] keys = r.getKeys();
		int size = r.getValue(keys[0]).size();
		Row[] rows = new Row[size];
		for (int i = 0; i < size; i++) {
			rows[i] = ds.getEmptyRow();
		}
		for (String key : keys) {
			if (ds.nameToIndex(key) < 0)
				continue;
			List<Object> values = r.getValue(key);
			if (values == null)
				continue;
			for (int i = 0; i < values.size(); i++) {
				rows[i].setValue(ds.nameToIndex(key), values.get(i));
			}
		}

		return rows;
	}
	
	/**
	 * 用户字段不一致，该方法关联用户专用
	 * 
	 * @param row
	 * @param ds
	 * @param map
	 * @return
	 */
	private Row[] setUserRowsValues(Row row, Dataset ds, Map map) {
		TranslatedRows r = (TranslatedRows) map.get("row");
		String[] keys = r.getKeys();
		int size = r.getValue(keys[0]).size();
		Row[] rows = new Row[size];
		for (int i = 0; i < size; i++) {
			rows[i] = ds.getEmptyRow();
		}
		for (String key : keys) {
			if (ds.nameToIndex(key) < 0 && !"cuserid".equals(key))
				continue;
			List<Object> values = r.getValue(key);
			if (values == null)
				continue;
			for (int i = 0; i < values.size(); i++) {
				if ("cuserid".equals(key))
					rows[i].setValue(ds.nameToIndex("pk_user"), values.get(i));
				else
					rows[i].setValue(ds.nameToIndex(key), values.get(i));
			}
		}
		return rows;
	}
	
	private void onVoSaves(SuperVO[] vos) {
		if (vos == null || vos.length == 0) {
			return;
		}
		try {
			CpRoleVO vo = (CpRoleVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_role() == null || vo.getPk_role().length() == 0) {
				CpbServiceFacility.getCpRoleBill().addPtRoleVO(vo);
			} else {
				CpbServiceFacility.getCpRoleBill().updatePtRoleVO(vo);
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
		CpRoleVO headvo = (CpRoleVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				RoleMgrConstants.OPERATE_STATUS);
		String code = headvo.getRolecode();
		String name = headvo.getRolename();
		String pk_org = headvo.getPk_rolegroup();
		String where = " pk_rolegroup ='" + pk_org + "' and (rolecode='" + code
				+ "' or rolename='" + name + "')";
		try {
			CpRoleVO[] respvos = CpbServiceFacility.getCpRoleQry().getRoleVos(
					where);
			if (respvos == null || respvos.length < 1)
				return;
			if (RoleMgrConstants.ADD_OPERATE.equals(opeStatus)
					|| (RoleMgrConstants.EDIT_OPERATE.equals(opeStatus) && !respvos[0]
							.getPk_role().equals(headvo.getPk_role())))
				throw new LfwRuntimeException("角色编码/角色名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

	/**
	 * 保存关联数据前重复校验
	 */
	public boolean onBeforeVOSave(SuperVO vo) {
		SuperVO[] vos = null;
		try {
			vos = CRUDHelper.getCRUDService().queryVOs(vo, null, null);
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			return false;
		}
		if (vos == null || vos.length == 0) {
			return true;
		}
		return false;
	}

	

	@Override
	public String getMasterDsId() {
		return "cp_role";
	}


	public void onAdd(MouseEvent<T> mouseEvent) {
		getCurrentWinCtx().addAppAttribute(RoleMgrConstants.OPERATE_STATUS,
				RoleMgrConstants.ADD_OPERATE);
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "新增角色");
	}

	public void onEdit(MouseEvent<T> mouseEvent) {
		getCurrentWinCtx().addAppAttribute(RoleMgrConstants.OPERATE_STATUS,
				RoleMgrConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("cp_role");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的数据！");
		getCurrentWinCtx().addAppAttribute(RoleMgrConstants.DATA, row);
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT, "修改角色");

	}

	public void onDel(MouseEvent<T> mouseEvent) {
		UifDelCmd cmd = new UifDelCmd(getMasterDsId(), getAggVoClazz());
		cmd.execute();
	}

	public void onRelateUser(MouseEvent mouseEvent) {
		getCurrentWinCtx().popView(RelateUserController.PUBLIC_VIEW_USER,DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT, "新增关联用户");
	}

	public void onDelRelateUser(MouseEvent mouseEvent) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				RoleMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				RoleMgrConstants.Ds_UserRole);
		Row[] rows = relateDs.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择要删除的角色关联的用户!");
		}
		InteractionUtil.showConfirmDialog("确认对话框", "确认要删除的角色关联的用户吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
			SuperVO[] pvos = serializer.serialize(relateDs, rows);
			try {
				CpbServiceFacility.getCpSuperVOBill().deleteSuperVOs(pvos);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException("删除角色关联用户出现异常!");
			}
			for (int i = 0; i < rows.length; i++) {
				relateDs.removeRow(rows[i]);
			}
		}
	}

	public void onRelateOrg(MouseEvent mouseEvent) {
		getCurrentWinCtx().popView(RelateOrgController.PUBLIC_VIEW_ORG,DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT, "新增关联组织");
	}
	

	public void setEnaDisbleDate(MouseEvent mouseEvent) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				RoleMgrConstants.Widget_Main).getView();
		Dataset userroleds = refwidget.getViewModels().getDataset(
				RoleMgrConstants.Ds_UserRole);
		Row[] rows = userroleds.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择角色关联的用户!");
		}
		getCurrentWinCtx().popView(EnabledateController.PUBLIC_VIEW_ENABLEDATE,
				"500", "160", "设置生效-失效日期");
	}
	
	public void onDelRelateOrg(MouseEvent mouseEvent) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				RoleMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				RoleMgrConstants.Ds_ref_org);
		Row[] rows = relateDs.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择要删除的角色关联的组织!");
		}
		InteractionUtil.showConfirmDialog("确认对话框", "确认要删除的角色关联的组织吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
			SuperVO[] pvos = serializer.serialize(relateDs, rows);
			try {
				CpbServiceFacility.getCpSuperVOBill().deleteSuperVOs(pvos);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException("删除角色关联组织出现异常!");
			}
			for (int i = 0; i < rows.length; i++) {
				relateDs.removeRow(rows[i]);
			}
		}
	}
	public void onRelateResp(MouseEvent mouseEvent) {
		getCurrentWinCtx().popView("relateresp",DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT, "新增关联职责");
	}

	public void onDelRelateResp(MouseEvent mouseEvent) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				RoleMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				RoleMgrConstants.Ds_relate_resp);
		Row[] rows = relateDs.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择要删除的角色关联的组织!");
		}
		InteractionUtil.showConfirmDialog("确认对话框", "确认要删除的角色关联的组织吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
			SuperVO[] pvos = serializer.serialize(relateDs, rows);
			try {
				CpbServiceFacility.getCpSuperVOBill().deleteSuperVOs(pvos);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException("删除角色关联组织出现异常!");
			}
			for (int i = 0; i < rows.length; i++) {
				relateDs.removeRow(rows[i]);
			}
		}
	}
	
	public void pluginedit_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("cp_role");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				RoleMgrConstants.OPERATE_STATUS);
		Row row = null;
		if (RoleMgrConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setRowValues(row, ds, keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (RoleMgrConstants.EDIT_OPERATE.equals(opeStatus)) {
			row = ds.getSelectedRow();
			setRowValues(row, ds, keys);
		}
		UifSaveCmd cmd = new UifSaveCmd(getMasterDsId(), getDetailDsIds(),
				getAggVoClazz(), false) {
			protected void onVoSave(AggregatedValueObject agggo) {
				onVoSaves(new SuperVO[] { (SuperVO) agggo.getParentVO() });
			}
		};
		cmd.execute();
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("edit");
	}

	public void pluginenabledate_plugin(Map map) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				RoleMgrConstants.Widget_Main).getView();
		Dataset userroleds = refwidget.getViewModels().getDataset(
				RoleMgrConstants.Ds_UserRole);
		Row[] rows = userroleds.getSelectedRows();
		if (rows == null || rows.length < 1)
			return;
		TranslatedRow r = (TranslatedRow) map.get("row");
		UFDate enabledate = (UFDate) r.getValue("enabledate");
		UFDate disabledate = (UFDate) r.getValue("disabledate");
		if(disabledate.beforeDate(enabledate))
			throw new LfwRuntimeException("失效日期必须大于生效日期!");
		
		for (int i = 0; i < rows.length; i++) {
			rows[i].setValue(userroleds.nameToIndex("enabledate"), enabledate);
			rows[i]
					.setValue(userroleds.nameToIndex("disabledate"),
							disabledate);
		}
		Dataset2SuperVOSerializer<SuperVO> serializer = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = serializer.serialize(userroleds, rows);
		try {
			CpbServiceFacility.getCpSuperVOBill().updateSuperVOs(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("设置生效-失效日期出现异常!");
		}
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("enabledate");
	}



	public void pluginorg_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_role");
		Row[] rows = pds.getSelectedRows();
		Dataset relateds = edit.getViewModels().getDataset("cp_role_roleorg");
		List<Row> relInsertRows = new ArrayList<Row>();
		for(int c=0;c<rows.length;c++){		
			Row row = rows[c];
			String pk_role = row.getString(pds.nameToIndex("pk_role"));			
			Row[] relaterows = setRowsValues(row, relateds, keys);
			for (Row r : relaterows) {
				r.setValue(relateds.nameToIndex("pk_role"), pk_role);
			}			
			for (int i = 0; i < relaterows.length; i++) {
				CpRoleOrgVO vo = new CpRoleOrgVO();
				vo.setPk_role(pk_role);
				vo.setPk_org(relaterows[i]
						.getString(relateds.nameToIndex("pk_org")));
				if (onBeforeVOSave(vo)) {
					relInsertRows.add(relaterows[i]);
				}
			}
		}
		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
		SuperVO[] vos = serializer.serialize(relateds, relInsertRows
				.toArray(new Row[0]));
		try {
			NCLocator.getInstance().lookup(ICpSuperVOBill.class)
					.insertSuperVOs(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(pds.getId()));
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("relateorg");
	}

	public void pluginuser_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_role");
		Row[] rows = pds.getSelectedRows();
		Dataset relateds = edit.getViewModels().getDataset("cp_role_userrole");
		List<Row> relInsertRows = new ArrayList<Row>();
		for(int c=0;c<rows.length;c++){		
			Row row = rows[c];
			String pk_role = row.getString(pds.nameToIndex("pk_role"));
			Row[] relaterows = setUserRowsValues(row, relateds, keys);
			for (Row r : relaterows) {
				r.setValue(relateds.nameToIndex("pk_role"), pk_role);
			}
			for (int i = 0; i < relaterows.length; i++) {
				CpUserRoleVO vo = new CpUserRoleVO();
				vo.setPk_role(pk_role);
				vo.setPk_user(relaterows[i].getString(relateds
						.nameToIndex("pk_user")));
				if (onBeforeVOSave(vo)) {
					relInsertRows.add(relaterows[i]);
				}
			}
		}
		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
		CpUserRoleVO[] vos = (CpUserRoleVO[]) serializer.serialize(relateds, relInsertRows
				.toArray(new Row[0]));
		try {
			NCLocator.getInstance().lookup(ICpUserRoleBill.class).addPtRoleUserVOS(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(pds.getId()));
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog(
						RelateUserController.PUBLIC_VIEW_USER);
	}

	public void pluginresp_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_role");
		Row[] rows = pds.getSelectedRows();
		Dataset relateds = edit.getViewModels().getDataset("ds_role_roleresp");
		List<Row> relInsertRows = new ArrayList<Row>();
		for(int c=0;c<rows.length;c++){		
			Row row = rows[c];
			String pk_role = row.getString(pds.nameToIndex("pk_role"));
			Row[] relaterows = setRowsValues(row, relateds, keys);
			for (Row r : relaterows) {
				r.setValue(relateds.nameToIndex("pk_role"), pk_role);
			}
			for (int i = 0; i < relaterows.length; i++) {
				CpRoleRespVO vo = new CpRoleRespVO();
				vo.setPk_role(pk_role);
				vo.setPk_responsibility(relaterows[i].getString(relateds
						.nameToIndex("pk_responsibility")));
				if (onBeforeVOSave(vo)) {
					relInsertRows.add(relaterows[i]);
				}
			}
		}
		Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
		SuperVO[] vos = serializer.serialize(relateds, relInsertRows
				.toArray(new Row[0]));
		try {
			NCLocator.getInstance().lookup(ICpSuperVOBill.class)
					.insertSuperVOs(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(pds.getId()));
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog("relateresp");
	}

	public void onDataLoad_cp_role(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		final String type = (String) LfwRuntimeEnvironment.getWebContext()
				.getWebSession().getOriginalParameter("type");
		getCurrentWinCtx().addAppAttribute("type", type);
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String where = " type='" + type + "'";
				ds.setLastCondition(where);
				return where;
			}
		});
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
	    		 String type = (String) getCurrentWinCtx().getAppAttribute("type");
	    		 return whereSql +" and type = '"+type+"' and pk_rolegroup in (select pk_rolegroup from cp_rolegroup where pk_group = '"+pk_group+"')";
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
