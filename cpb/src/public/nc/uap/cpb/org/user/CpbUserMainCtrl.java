package nc.uap.cpb.org.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserRoleBill;
import nc.uap.cpb.org.pubview.EnabledateController;
import nc.uap.cpb.org.pubview.RelateRoleController;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.rolegroup.RoleGroupConstants;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserRoleVO;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateService;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDatasetWhereCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ApplicationContext;
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

public class CpbUserMainCtrl extends AbstractWidgetController {
	private WindowContext getCurrentWinCtx() {
		return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
	}

	public void cpbUserOnLoad(DataLoadEvent se) {
		Dataset ds = se.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String pk_group = LfwRuntimeEnvironment.getLfwSessionBean()
						.getPk_unit();
				String where = " pk_group = '" + pk_group + "'";
				ds.setLastCondition(where);
				return where;
			}
		});
	}

	public void cpbUserOnAfterRow(DatasetEvent se) {
		Dataset ds = se.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}

	public void cpbUserAdd(MouseEvent<MenuItem> e) {
		addAppAttr("operator", "add");		
		new UifPlugoutCmd("main", "usermain_pluginout").execute();
		getCurrentWinCtx().popView("edit", DialogConstant.DOUBLE_COLUMN_WIDTH, DialogConstant.DOUBLE_COLUMN_HEIGHT, "新增用户");
	}

	public void cpbUserModify(MouseEvent<MenuItem> e) {
		addAppAttr("operator", "update");
		LfwWidget widget = getWidget("main");
		Dataset userData = widget.getViewModels().getDataset(
				UserMgrConstants.Ds_User);
		Row selectedRow = userData.getSelectedRow();
		if (selectedRow == null) {
			throw new LfwRuntimeException("请选中需要修改的行");
		}
		new UifPlugoutCmd("main", "usermain_pluginout").execute();
		getCurrentWinCtx().popView("edit", DialogConstant.DOUBLE_COLUMN_WIDTH, DialogConstant.DOUBLE_COLUMN_HEIGHT, "修改用户");
	}

	public void onStoped(MouseEvent<MenuItem> event) {
		LfwWidget widget = getWidget("main");
		Dataset ds = widget.getViewModels()
				.getDataset(UserMgrConstants.Ds_User);
		Row[] rows = ds.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选中需要停用的用户");
		}
		for (Row row : rows) {
			row.setValue(ds.nameToIndex("enablestate"), ICpUserConst.ENABLESTATE_STOP);
		}
		Dataset2SuperVOSerializer<SuperVO> serializer = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = serializer.serialize(ds, rows);
		try {
			CpbServiceFacility.getCpSuperVOBill().updateSuperVOs(vos);
			// 删除用户关联的角色
			String[] pk_users = new String[vos.length];
			for (int i = 0; i < vos.length; i++) {
				pk_users[i] = ((CpUserVO) vos[i]).getCuserid();
			}
			CpbServiceFacility.getCpUserRoleBill().deletePtRoleUserByUserpks(
					pk_users);			
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(
				UserMgrConstants.Ds_User));
		AppInteractionUtil.showMessage("停用成功!");
	}

	public void onUnStoped(MouseEvent<MenuItem> event) {
		LfwWidget widget = getWidget("main");
		Dataset ds = widget.getViewModels()
				.getDataset(UserMgrConstants.Ds_User);
		Row[] rows = ds.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选中需要启用的用户");
		}
		for (Row row : rows) {
			row.setValue(ds.nameToIndex("enablestate"), ICpUserConst.ENABLESTATE_ACTIVE);
		}
		Dataset2SuperVOSerializer<SuperVO> serializer = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] vos = serializer.serialize(ds, rows);
		try {
			CpbServiceFacility.getCpSuperVOBill().updateSuperVOs(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(
				UserMgrConstants.Ds_User));
		AppInteractionUtil.showMessage("启用成功!");
	}

	public void cpbUserDelete(MouseEvent<MenuItem> e) {	
		UifDelCmd cmd = new UifDelCmd(getMasterDsId(), getAggVoClazz()){
			protected void onDeleteVO(ArrayList<AggregatedValueObject> vos, boolean trueDel) {
				for(AggregatedValueObject aggvo:vos){
					CpUserVO uservo = (CpUserVO) aggvo.getParentVO();
					try {
						CpbServiceFacility.getCpUserBill().deleteCpUserVO(uservo);
					} catch (CpbBusinessException e) {
						LfwLogger.error(e.getMessage(),e);
					}
				}				
			}
		};
		cmd.execute();
	}

	public void cpbUserResetPwd(MouseEvent<MenuItem> e) {
		LfwWidget widget = getWidget(UserMgrConstants.Widget_Main);
		Dataset dsUser = widget.getViewModels().getDataset(
				UserMgrConstants.Ds_User);
		Row row = dsUser.getSelectedRow();
		if (row == null) {
			throw new LfwRuntimeException("请选择需要重置密码的用户!");
		}
		InteractionUtil.showConfirmDialog("确认对话框", "确认重置密码吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			row.setState(Row.STATE_UPDATE);
			row.setValue(dsUser.nameToIndex(CpUserVO.USER_PASSWORD), "portal");
			Dataset2SuperVOSerializer seria = new Dataset2SuperVOSerializer();
			SuperVO[] vos = seria.serialize(dsUser, row);
			if (vos == null || vos.length == 0) {
				return;
			}
			CpUserVO userVo = (CpUserVO) vos[0];
			try {
				CpbServiceFacility.getCpUserBill().updateUserPwd(userVo.getCuserid(), "portal", new UFDate());
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		}
	}

	public void pluginusermain_pluginin(Map<String, Object> keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("cp_user");
		String opeStatus = (String) getAppAttr("operator");
		Row row = null;
		if ("add".equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setValues(row, ds, keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if ("update".equals(opeStatus)) {
			row = ds.getSelectedRow();
			setValues(row, ds, keys);
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

	private void onVoSaves(SuperVO[] vos) {
		if (vos == null || vos.length == 0) {
			return;
		}
		CpUserVO userVo = (CpUserVO) vos[0];
		checkDupliVO(userVo);
		if (userVo.getCuserid() == null || userVo.getCuserid().length() == 0) {
			try {
				CpbServiceFacility.getCpUserBill().addCpUserVO(userVo);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		} else {
			try {
				CpbServiceFacility.getCpSuperVOBill().updateSuperVO(userVo);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		}
	}

	private void checkDupliVO(SuperVO vo) {
		CpUserVO headvo = (CpUserVO) vo;
		String opeStatus = (String) getAppAttr("operator");
		String code = headvo.getUser_code();
		try {
			CpUserVO userVO = CpbServiceFacility.getCpUserQry().getGlobalUserByCode(
					code);
			if (userVO == null)
				return;
			if ("add".equals(opeStatus)
					|| ("update".equals(opeStatus) && !userVO.getCuserid()
							.equals(headvo.getCuserid())))
				throw new LfwRuntimeException("用户编码已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}

	public void onSignManage(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = getWidget(UserMgrConstants.Widget_Main);
		Dataset ds = widgetMain.getViewModels().getDataset(UserMgrConstants.Ds_User);
		 Row userRow = ds.getSelectedRow();
		 if (userRow == null)
			 throw new LfwRuntimeException("请先选择用户!");
		 getCurrentWinCtx().addAppAttribute(UserMgrConstants.DATA, userRow);
		 getCurrentWinCtx().popView("signpic","350", "350" ,"管理用户签名图片");
		 //String pk_user = (String)userRow.getValue(ds.nameToIndex("pk_user"));
		 //String url = LfwRuntimeEnvironment.getCorePath() +
		// "/uimeta.um?pageId=myvisamgr&model=nc.portal.pwfm.listener.MyVisaPageModel&pk_user="+ pk_user + "";
		 //this.getGlobalContext().showModalDialog(url, "电子签章维护", "480", "480","myvismgr", true, true);
	}

	public void cpbUserAddRole(MouseEvent<MenuItem> e) {
		LfwWidget widgetMain = getWidget(UserMgrConstants.Widget_Main);
		Dataset pds = widgetMain.getViewModels().getDataset(
				UserMgrConstants.Ds_User);
		Row prow = pds.getSelectedRow();
		if (prow == null) {
			throw new LfwRuntimeException("未选择用户，不能关联角色！");
		}
		getCurrentWinCtx().popView(RelateRoleController.PUBLIC_VIEW_ROLE,DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT ,"新增关联角色");
	}

	public void pluginrole_plugin(Map keys) {
		LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_user");
		Row[] rows = pds.getSelectedRows();
		Dataset relateds = edit.getViewModels().getDataset("user_cp_userrole");
		List<Row> relInsertRows = new ArrayList<Row>();
		for (int c = 0; c < rows.length; c++) {
			Row row = rows[c];
			String pk_user = row.getString(pds.nameToIndex("cuserid"));
			Row[] relaterows = setRowsValues(row, relateds, keys);
			for (Row r : relaterows) {
				r.setValue(relateds.nameToIndex("pk_user"), pk_user);
			}
			for (int i = 0; i < relaterows.length; i++) {
				CpUserRoleVO vo = new CpUserRoleVO();
				vo.setPk_user(pk_user);
				vo.setPk_role((relaterows[i].getString(relateds
						.nameToIndex("pk_role"))));
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
						RelateRoleController.PUBLIC_VIEW_ROLE);
	}

	public void pluginquery_plugin(Map keys) {
		CmdInvoker.invoke(new UifDatasetWhereCmd("cp_user", (FromWhereSQL) keys
				.get("whereSql")));
	}

	public void pluginqueryplan_plugin(Map keys) {
		CmdInvoker.invoke(new UifDatasetWhereCmd("cp_user", (FromWhereSQL) keys
				.get("whereSql")));
	}

	private Row[] setRowsValues(Row row, Dataset ds, Map map) {
		TranslatedRows r = (TranslatedRows) map.get("selectedrow");
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

	public void cpbUserDeleteRole(MouseEvent<MenuItem> e) {
		LfwWidget refwidget = AppLifeCycleContext.current()
				.getApplicationContext().getCurrentWindowContext()
				.getViewContext(UserMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				UserMgrConstants.Ds_UserRole);
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
				throw new LfwRuntimeException("删除用户组关联角色出现异常!");
			}
			for (int i = 0; i < rows.length; i++) {
				relateDs.removeRow(rows[i]);
			}
		}
	}

	public void setEnaDisbleDate(MouseEvent mouseEvent) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				UserMgrConstants.Widget_Main).getView();
		Dataset userroleds = refwidget.getViewModels().getDataset(
				UserMgrConstants.Ds_UserRole);
		Row[] rows = userroleds.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择用户关联的角色!");
		}
		getCurrentWinCtx().popView(EnabledateController.PUBLIC_VIEW_ENABLEDATE,DialogConstant.DEFAULT_WIDTH, DialogConstant.DEFAULT_HEIGHT, "设置生效-失效日期");
	}

	public void pluginenabledate_plugin(Map map) {
		LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				UserMgrConstants.Widget_Main).getView();
		Dataset userroleds = refwidget.getViewModels().getDataset(
				UserMgrConstants.Ds_UserRole);
		Row[] rows = userroleds.getSelectedRows();
		if (rows == null || rows.length < 1)
			return;
		TranslatedRow r = (TranslatedRow) map.get("row");
		UFDate enabledate = (UFDate) r.getValue("enabledate");
		UFDate disabledate = (UFDate) r.getValue("disabledate");
		if(disabledate!=null&&disabledate.beforeDate(enabledate))
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

	public String getMasterDsId() {
		return "cp_user";
	}

	/**
	 * 简单查询
	 * 
	 * @param keys
	 */
	public void pluginsimpleQuery_plugin(Map<Object, Object> keys) {
		FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
		String wheresql = whereSql.getWhere();
		QueryCmd cmd = new QueryCmd("main", getMasterDsId(), wheresql) {
			protected String buildWhere(String whereSql) {
				String pk_group = LfwRuntimeEnvironment.getLfwSessionBean()
						.getPk_unit();
				return whereSql + " and pk_group = '" + pk_group + "'";
			}
		};
		cmd.excute();
	}

	/**
	 * 查询计划
	 * 
	 * @param keys
	 */
	public void pluginqueryPlan_plugin(Map keys) {

	}

	public void onTemplatePrint(MouseEvent mouseEvent) {
		LfwWidget print = AppLifeCycleContext.current().getWindowContext().getViewContext(UserMgrConstants.Widget_Main).getView();
		Dataset ds = print.getViewModels().getDataset(UserMgrConstants.Ds_User);
		ICpPrintTemplateService printTemplate = NCLocator.getInstance().lookup(ICpPrintTemplateService.class);
		try{
			printTemplate.print(ds);
		}catch(Exception e){
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(),e);
		}
	}
	
	public Object getAppAttr(String key){
		return getCntAppCtx().getAppAttribute(key);
	}
	
	private void addAppAttr(String key, Serializable value) {
		getCntAppCtx().addAppAttribute(key, value);
	}
	private ApplicationContext getCntAppCtx() {
		return AppLifeCycleContext.current().getApplicationContext();
	}
	
	private WindowContext getCntWindowCtx() {
		return getCntAppCtx().getCurrentWindowContext();
	}
	public LfwWidget getWidget(String name) {
		return  getCntWindowCtx().getWindow().getWidget(name);
	}
}
