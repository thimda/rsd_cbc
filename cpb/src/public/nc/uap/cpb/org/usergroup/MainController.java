package nc.uap.cpb.org.usergroup;
import nc.vo.pub.AggregatedValueObject;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.cpb.org.exception.CpbBusinessException;
import java.util.Map;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.cpb.org.pubview.RelateUserController;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.cpb.org.vos.CpUserGroupUserVO;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;
import nc.uap.cpb.org.pubview.RelateRoleController;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.cpb.org.user.UserMgrConstants;
import nc.uap.cpb.org.util.CpbServiceFacility;
import java.util.ArrayList;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.bs.framework.common.NCLocator;
import java.util.List;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.SuperVO;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.bs.logging.Logger;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateService;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.model.plug.TranslatedRows;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.event.MouseEvent;
public class MainController<T extends WebElement> extends AbstractWidgetController implements IController {
  private static final long serialVersionUID=8990769129712284556L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
  }
  public void onAdd(  MouseEvent<T> mouseEvent){
    getCurrentWinCtx().addAppAttribute(
				UserGroupMgrConstants.OPERATE_STATUS,
				UserGroupMgrConstants.ADD_OPERATE);
		LfwWidget main = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset("cp_usergroup");
		Row prow = ds.getSelectedRow();
		if (prow != null) {
			getCurrentWinCtx().addAppAttribute("pk_parent",
					prow.getString(ds.nameToIndex("pk_usergroup")));
			getCurrentWinCtx().addAppAttribute("pk_org",
					prow.getString(ds.nameToIndex("pk_org")));
		} else {
			getCurrentWinCtx().addAppAttribute("pk_parent", null);
			getCurrentWinCtx().addAppAttribute("pk_org", null);
		}
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.SIX_ELE_HEIGHT, "新增用户组");
  }
  public void onEdit(  MouseEvent<T> mouseEvent){
    getCurrentWinCtx().addAppAttribute(
				UserGroupMgrConstants.OPERATE_STATUS,
				UserGroupMgrConstants.EDIT_OPERATE);
		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
				.getViewModels().getDataset("cp_usergroup");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的数据！");
		getCurrentWinCtx().addAppAttribute(UserGroupMgrConstants.DATA, row);
		getCurrentWinCtx().popView("edit", DialogConstant.FIVE_ELE_WIDTH, DialogConstant.SIX_ELE_HEIGHT, "修改用户组");
  }
  private Row setRowValues(  Row row,  Dataset ds,  Map map){
    TranslatedRow r = (TranslatedRow) map.get("row");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
		return row;
  }
  private Row[] setRowsValues(  Row row,  Dataset ds,  Map map){
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
 * @param row
 * @param ds
 * @param map
 * @return
 */
  private Row[] setUserRowsValues(  Row row,  Dataset ds,  Map map){
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
  private void onVoSaves(  SuperVO[] vos){
    if (vos == null || vos.length == 0) {
			return;
		}
		try {
			CpUserGroupVO vo = (CpUserGroupVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_usergroup() == null
					|| vo.getPk_usergroup().length() == 0) {
				CpbServiceFacility.getCpUserGroupBill().addPtUserGroupVO(vo);
			} else {
				CpbServiceFacility.getCpUserGroupBill().updatePtUserGroupVO(vo);
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
  }
  /** 
 * 保存，更新前检查用户组编码，名称是否重复
 * @param vo
 */
  private void checkDupliVO(  SuperVO vo){
    CpUserGroupVO headvo = (CpUserGroupVO) vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				UserGroupMgrConstants.OPERATE_STATUS);
		String code = headvo.getGroup_code();
		String name = headvo.getGroup_name();
		String pk_group = headvo.getPk_group();
		String where = " pk_group ='" + pk_group + "' and (group_code='" + code
				+ "' or group_name='" + name + "')";
		try {
			CpUserGroupVO[] usergroupvos = CpbServiceFacility
					.getCpUserGroupQry().getUserGroupVos(where);
			if (usergroupvos == null || usergroupvos.length < 1)
				return;
			if (UserGroupMgrConstants.ADD_OPERATE.equals(opeStatus)
					|| (UserGroupMgrConstants.EDIT_OPERATE.equals(opeStatus) && !usergroupvos[0]
							.getPk_usergroup().equals(headvo.getPk_usergroup())))
				throw new LfwRuntimeException("用户组编码/用户组名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
  }
  /** 
 * 保存关联数据前重复校验
 */
  public boolean onBeforeVOSave(  SuperVO vo){
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
  @Override public String getMasterDsId(){
    return "cp_usergroup";
  }
  public void onDel(  MouseEvent<T> mouseEvent){
    LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		Dataset ds = main.getViewModels().getDataset("cp_usergroup");
		Row row = ds.getSelectedRow();
		if(row==null)
			return;
		String pk_usergroup = row.getString(ds.nameToIndex("pk_usergroup"));		
		try {
			CpUserGroupVO[] usergroupvos = CpbServiceFacility.getCpUserGroupQry().getUserGroupByParent(pk_usergroup);
			if(usergroupvos!=null&&usergroupvos.length>0)
				throw new LfwRuntimeException("你选择的用户组存在子用户组，不允许删除!");
			CpUserVO[] users  = CpbServiceFacility.getCpUserQry().getUserByUserGroup(pk_usergroup);
			if(users!=null&&users.length>0)
				throw new LfwRuntimeException("你选择的用户组存在关联用户，不允许删除!");
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
			throw new LfwRuntimeException("删除时出错了!");			
		}		
		UifDelCmd cmd = new UifDelCmd(getMasterDsId(), getAggVoClazz());
		cmd.execute();
  }
  public void onDataLoad_cp_rolegroup(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void onRelateUser(  MouseEvent mouseEvent){
    UifEditCmd cmd = new UifEditCmd(RelateUserController.PUBLIC_VIEW_USER,
				"600", "400", "新增关联用户");
		cmd.execute();
  }
  public void onDelRelateUser(  MouseEvent mouseEvent){
    LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				UserGroupMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				UserGroupMgrConstants.Ds_UserGroupUser);
		Row[] rows = relateDs.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择要删除的用户组关联的用户!");
		}
		InteractionUtil.showConfirmDialog("确认对话框", "确认要删除的用户组关联的用户吗?");
		if (InteractionUtil.getConfirmDialogResult().booleanValue()) {
			Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
			SuperVO[] pvos = serializer.serialize(relateDs, rows);
			try {
				CpbServiceFacility.getCpSuperVOBill().deleteSuperVOs(pvos);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException("删除用户组关联用户出现异常!");
			}
			for (int i = 0; i < rows.length; i++) {
				relateDs.removeRow(rows[i]);
			}
		}
  }
  public void onRelateRole(  MouseEvent mouseEvent){
    UifEditCmd cmd = new UifEditCmd(RelateRoleController.PUBLIC_VIEW_ROLE,
				"600", "400", "新增关联角色");
		cmd.execute();
  }
  public void onDelRelateRole(  MouseEvent mouseEvent){
    LfwWidget refwidget = getCurrentWinCtx().getViewContext(
				UserGroupMgrConstants.Widget_Main).getView();
		Dataset relateDs = refwidget.getViewModels().getDataset(
				UserGroupMgrConstants.Ds_UserGroupRole);
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
  public void pluginedit_plugin(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("cp_usergroup");
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(
				UserGroupMgrConstants.OPERATE_STATUS);
		Row row = null;
		if (UserGroupMgrConstants.ADD_OPERATE.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			setRowValues(row, ds, keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
		} else if (UserGroupMgrConstants.EDIT_OPERATE.equals(opeStatus)) {
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
  public void pluginuser_plugin(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_usergroup");
		Row row = pds.getSelectedRow();
		String pk_usergroup = row.getString(pds.nameToIndex("pk_usergroup"));
		Dataset relateds = edit.getViewModels().getDataset(
				"cp_usergroup_usergroupuser");
		Row[] relaterows = setUserRowsValues(row, relateds, keys);
		for (Row r : relaterows) {
			r.setValue(relateds.nameToIndex("pk_usergroup"), pk_usergroup);
		}
		List<Row> relInsertRows = new ArrayList<Row>();
		for (int i = 0; i < relaterows.length; i++) {
			CpUserGroupUserVO vo = new CpUserGroupUserVO();
			vo.setPk_usergroup(pk_usergroup);
			vo.setPk_user(relaterows[i].getString(relateds
					.nameToIndex("pk_user")));
			if (onBeforeVOSave(vo)) {
				relInsertRows.add(relaterows[i]);
			}
		}
		if (relInsertRows.size() > 0) {
			Dataset2SuperVOSerializer serializer = new Dataset2SuperVOSerializer();
			SuperVO[] vos = serializer.serialize(relateds, relInsertRows
					.toArray(new Row[0]));
			try {
				NCLocator.getInstance().lookup(ICpSuperVOBill.class)
						.insertSuperVOs(vos);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
			}
			for (Row tmprow : relInsertRows) {
				relateds.addRow(tmprow);
			}
		}
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog(
						RelateUserController.PUBLIC_VIEW_USER);
  }
  public void pluginrole_plugin(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset pds = edit.getViewModels().getDataset("cp_usergroup");
		Row row = pds.getSelectedRow();
		String pk_usergroup = row.getString(pds.nameToIndex("pk_usergroup"));
		Dataset relateds = edit.getViewModels().getDataset(
				"cp_usergroup_usergrouprole");
		Row[] relaterows = setRowsValues(row, relateds, keys);
		for (Row r : relaterows) {
			r.setValue(relateds.nameToIndex("pk_usergroup"), pk_usergroup);
		}
		List<Row> relInsertRows = new ArrayList<Row>();
		for (int i = 0; i < relaterows.length; i++) {
			CpUserGroupRoleVO vo = new CpUserGroupRoleVO();
			vo.setPk_usergroup(pk_usergroup);
			vo.setPk_role((relaterows[i].getString(relateds
					.nameToIndex("pk_role"))));
			if (onBeforeVOSave(vo)) {
				relInsertRows.add(relaterows[i]);
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
		for (Row tmprow : relInsertRows) {
			relateds.addRow(tmprow);
		}
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog(
						RelateRoleController.PUBLIC_VIEW_ROLE);
  }
  public void onDataLoad_cp_usergroup(  DataLoadEvent dataLoadEvent){
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
  public void pluginsimpleQuery_plugin(  Map<Object,Object> keys){
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
 * @param keys
 */
  public void pluginqueryPlan_plugin(  Map keys){
    
  }
  public void onTempaltePrint(  MouseEvent mouseEvent){
	  LfwWidget print = AppLifeCycleContext.current().getWindowContext().getViewContext(UserMgrConstants.Widget_Main).getView();
		Dataset ds = print.getViewModels().getDataset("cp_usergroup");
		ICpPrintTemplateService printTemplate = NCLocator.getInstance().lookup(ICpPrintTemplateService.class);
		try{
			printTemplate.print(ds);
		}catch(Exception e){
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(),e);
		}
  }
}
