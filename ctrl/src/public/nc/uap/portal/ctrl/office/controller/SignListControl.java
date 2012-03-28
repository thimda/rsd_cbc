package nc.uap.portal.ctrl.office.controller;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import java.util.Map;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.cpb.org.pubview.RelateUserController;
import nc.uap.cpb.org.querycmd.QueryCmd;

import java.util.HashMap;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.portal.ctrl.office.data.SignHelper;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.sign.UsersignsVO;
import java.util.ArrayList;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.bs.framework.common.NCLocator;
import java.util.List;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.model.plug.TranslatedRows;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.event.MouseEvent;
public class SignListControl {
  public void onDataLoad_signlist(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void newuseronclick(  MouseEvent mouseEvent){
	  
    UifEditCmd cmd = new UifEditCmd(RelateUserController.PUBLIC_VIEW_USER,
				"600", "400", "新增关联用户");
		cmd.execute();
  }
  public void newuonclick(  MouseEvent mouseEvent){
    // UifEditCmd cmd = new
		// UifEditCmd(RelateUserController.PUBLIC_VIEW_USER, "600", "400",
		// "新增关联用户");
		// cmd.execute();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("edittype", "new");
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("signeditor", map);
		AppLifeCycleContext.current().getApplicationContext().navgateTo("sign_editor", "印章编辑", DialogConstant.DOUBLE_COLUMN_WIDTH, "405", map);
  }
  public void pluginsignedit_plugin(  Map keys){
    try {
			String signpk = (String) AppLifeCycleContext.current().getWindowContext().getAppAttribute("signpk");
			if(signpk  != null && signpk.length() != 0){
				ServersignVO vo;
					vo = SignHelper.GetServerSignByPK(signpk);
				if(vo != null){
					LfwWidget signwidget = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
					Dataset ds = signwidget.getViewModels().getDataset("signlist");
					if(ds != null){
						Row[] rows = ds.getCurrentRowData().getRows();
						Row row = null;
						if (rows != null) {
							for (int i = 0; i < rows.length; i++) {
								String curpk = (String) rows[i].getValue(ds.nameToIndex("PK_sign"));
								if (signpk.equals(curpk)) {
									row = rows[i];
									break;
								}
							}
						}
						if(row == null){
							row = ds.getEmptyRow();
							ds.addRow(row);
						}
						row.setValue(ds.nameToIndex("PK_sign"), vo.getPk_sign());
						row.setValue(ds.nameToIndex("createby"), vo.getCreateby());
						row.setValue(ds.nameToIndex("createtime"), vo.getCreatetime());
						row.setValue(ds.nameToIndex("name"), vo.getName());
						row.setValue(ds.nameToIndex("no"), vo.getNo());
						ds.setCurrentKey(Dataset.MASTER_KEY);
					}
				}
			}
		} catch (LfwBusinessException e) {
			LfwLogger.error(e);
		}
		AppLifeCycleContext.current().getApplicationContext().closeWinDialog();
  }
  public void pluginuser_plugin(  Map keys){
    try {
			TranslatedRows r = (TranslatedRows) keys.get("row");
			if (r != null) {
				List<Object> userpks = r.getValue("cuserid");
				if (userpks != null) {
					ICpSuperVOBill supqry = NCLocator.getInstance().lookup(
							ICpSuperVOBill.class);

					LfwWidget edit = AppLifeCycleContext.current()
							.getWindowContext().getViewContext("main")
							.getView();
					Dataset pds = edit.getViewModels().getDataset("signlist");
					Row row = pds.getSelectedRow();
					if (row == null)
						AppInteractionUtil.showErrorDialog("请选择印章");

					String pk_sign = row.getString(pds.nameToIndex("PK_sign"));
					Dataset relateds = edit.getViewModels().getDataset(
							"signuserlist");

					UsersignsVO[] usersigns = SignHelper
							.GetAllSignusers(pk_sign);
					List<String> curuserpks = new ArrayList<String>();
					if (null != usersigns) {
						for (UsersignsVO vo : usersigns) {
							curuserpks.add(vo.getPk_user());
						}
					}

					for (Object obj : userpks) {
						String pk_user = obj.toString();
						if (pk_user == null || pk_user.equals(""))
							continue;
						if (curuserpks.contains(pk_user))
							continue;

						UsersignsVO newvo = new UsersignsVO();
						newvo.setPk_sign(pk_sign);
						newvo.setPk_user(pk_user);

						String pk_usersign = supqry.insertSuperVO(newvo);

						Row newrow = relateds.getEmptyRow();
						newrow.setValue(relateds.nameToIndex("pk_sign"),
								pk_sign);
						newrow.setValue(relateds.nameToIndex("pk_usersign"),
								pk_usersign);
						newrow.setValue(relateds.nameToIndex("pk_user"),
								pk_user);
						newrow.setValue(relateds
								.nameToIndex("pk_user_user_name"), pk_sign);
						newrow.setValue(relateds
								.nameToIndex("pk_user_user_code"), pk_sign);

						relateds.addRow(newrow);
					}
				}
			}
		} catch (LfwBusinessException e) {
			LfwLogger.error(e);
		}
		AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext().closeViewDialog(
						RelateUserController.PUBLIC_VIEW_USER);
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
  public void DeleteUserSign(  ScriptEvent event){
    String pk_signuser = AppLifeCycleContext.current().getParameter("pk");// 参数
		try {
			Dataset ds = AppLifeCycleContext.current().getWindowContext()
					.getViewContext("main").getView().getViewModels()
					.getDataset("signuserlist");			
			Row row = ds.getSelectedRow();
			

			if (row != null) {
				ICpSuperVOBill supqry = NCLocator.getInstance().lookup(
						ICpSuperVOBill.class);
				supqry.deleteByPK(UsersignsVO.class, pk_signuser);
				ds.removeRow(row);
			}
		} catch (CpbBusinessException e) {
			LfwLogger.error(e);
		}
  }
  public void EditSignEvent(  ScriptEvent event){
    String pk_sign = AppLifeCycleContext.current().getParameter("pk");// 参数
		String type = AppLifeCycleContext.current().getParameter("type");// 参数
		if(type.equals("edit")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("edittype", type);
			map.put("pk", pk_sign);
			LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("signeditor", map);
			AppLifeCycleContext.current().getApplicationContext().navgateTo("sign_editor", "印章编辑", DialogConstant.DOUBLE_COLUMN_WIDTH, "405", map);
		}
		else if(type.equals("delete")){
			try {
				Dataset ds = AppLifeCycleContext.current().getWindowContext()
						.getViewContext("main").getView().getViewModels()
						.getDataset("signlist");				
				Row row = ds.getSelectedRow();
				if (row != null) {
					SignHelper.DeleteServerSign(pk_sign);
					ds.removeRow(row);
					Dataset userds = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView().getViewModels().getDataset("signuserlist");
					userds.clear();
				}
			} catch (CpbBusinessException e) {
				LfwLogger.error(e);
			} catch (LfwBusinessException e) {
				LfwLogger.error(e);
			}
		}
  }
  public void pluginsimpleQuery_plugin(  Map keys){
	  FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
	     String wheresql = whereSql.getWhere();
	     QueryCmd cmd = new QueryCmd("main", "signlist", wheresql);
	     cmd.excute();
  }
}
