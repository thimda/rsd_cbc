package nc.uap.portal.ctrl.office.controller;
import nc.vo.pub.AggregatedValueObject;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.portal.ctrl.office.data.sign.EkeyVO;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.cpb.org.exception.CpbBusinessException;

import java.util.HashMap;
import java.util.Map;

import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.data.Dataset;
public class EKeyControl {
  public static String EKeyOPerator="operator";
  public static String EKeyOPerator_new="new";
  public static String EKeyOPerator_edit="edit";
  public void onDataLoad_ekeylist(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void newonclick(  MouseEvent mouseEvent){
    UifEditCmd cmd = new UifEditCmd("edit", "600", "400", "注册加密狗");
		
		AppLifeCycleContext.current().getWindowContext().addAppAttribute(EKeyOPerator,EKeyOPerator_new);
		cmd.execute();
  }
	public void newsignonclick(MouseEvent mouseEvent) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("edittype", "ekeynew");
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("signeditor", map);
		AppLifeCycleContext.current().getApplicationContext().navgateTo("sign_editor", "印章编辑", "670", "350", map);
	}
	public void editsignonclick(MouseEvent mouseEvent) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("edittype", "ekeyedit");
		LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("signeditor", map);
		AppLifeCycleContext.current().getApplicationContext().navgateTo("sign_editor", "印章编辑", "670", "350", map);
	}
  public void pluginsignedit_pluginin(  Map<String,Object> keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		Dataset ds = edit.getViewModels().getDataset("ekeyds");
		String opeStatus = (String) AppLifeCycleContext.current().getWindowContext().getAppAttribute(EKeyOPerator);
		Row row = null;
		if (EKeyOPerator_new.equals(opeStatus)) {
			// 新增行并选中
			row = ds.getEmptyRow();
			row =  setRowValues(row,ds,keys);
			ds.addRow(row);
			
		} else if (EKeyOPerator_edit.equals(opeStatus)) {
			row = ds.getSelectedRow();
			setRowValues(row,ds,keys);
		}
		
		UifSaveCmd cmd = new UifSaveCmd(getMasterDsId(), null, getAggVoClazz(), false) {
			protected void onVoSave(AggregatedValueObject agggo) 
			{
				onVoSaves(new SuperVO[] { (SuperVO) agggo.getParentVO()});
			}
		};
		cmd.execute();
		
		AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeView("edit");
  }
  private void onVoSaves(  SuperVO[] vos){
    if (vos == null || vos.length == 0) {
			return;
		}
		try {
			EkeyVO vo = (EkeyVO) vos[0];
			ICpSuperVOBill supqry = NCLocator.getInstance().lookup(ICpSuperVOBill.class);
			supqry.mergeSuperVO(vo);
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1);
		}
  }
  protected String getAggVoClazz(){
    return LfwExAggVO.class.getName();
  }
  public String getMasterDsId(){
    return "ekeyds";
  }
  private Row setRowValues(  Row row,  Dataset ds,  Map map){
    TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
		return row;
  }
	public void EkeyEditEvent(ScriptEvent event) {
		String pk_ekey = AppLifeCycleContext.current().getParameter("pk");// 参数
		String type = AppLifeCycleContext.current().getParameter("type");// 参数
		if(type.equals("edit")){
			UifEditCmd cmd = new UifEditCmd("edit", "650", "400", "注册加密狗");
			AppLifeCycleContext.current().getWindowContext().addAppAttribute(EKeyOPerator,EKeyOPerator_edit);
			AppLifeCycleContext.current().getWindowContext().addAppAttribute("pk",pk_ekey);
			cmd.execute();
		}
		else if(type.equals("delete")){
			try {
				Dataset ds = AppLifeCycleContext.current().getWindowContext()
						.getViewContext("main").getView().getViewModels()
						.getDataset("ekeyds");				
				Row row = ds.getSelectedRow();
				if (row != null) {
					ICpSuperVOBill supqry = NCLocator.getInstance().lookup(
							ICpSuperVOBill.class);
					supqry.deleteByPK(EkeyVO.class, pk_ekey);
					
					ds.removeRow(row);
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
	     QueryCmd cmd = new QueryCmd("main", "ekeyds", wheresql);
	     cmd.excute();
  }
}
