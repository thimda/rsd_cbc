package nc.uap.cpb.defdoc.defdoclist;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.CmdInvoker;
import java.util.Map;
import nc.vo.pub.lang.UFDateTime;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.tags.AppDynamicCompUtil;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.bs.dao.DAOException;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.itf.bd.defdoc.IDefdoclistQryService;
import nc.itf.bd.defdoc.IDefdoclistService;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.ctx.ApplicationContext;
import nc.uap.lfw.core.data.Dataset;
import nc.vo.bd.defdoc.DefdoclistVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.bd.defdoc.DefdocVO;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.ctrl.IController;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.BusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.lang.UFBoolean;
import java.lang.reflect.InvocationTargetException;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 * @author chouhl
 */
public class MainCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void onDataLoad_defdoclist(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		IDefdoclistQryService ser = NCLocator.getInstance().lookup(IDefdoclistQryService.class);
		DefdoclistVO[] vos;
		try {
			vos = ser.queryDefdoclistVOs();
		} catch (BusinessException e) {
			throw new LfwRuntimeException(e);
		}
		if(vos!=null&&vos.length!=0){
			ds.getCurrentRowSet().getPaginationInfo().setRecordsCount(vos.length);
			new SuperVO2DatasetSerializer().serialize(vos, ds,Row.STATE_NORMAL);
			ButtonStateManager.updateButtons();
		}
  }
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
  }
  public void editEvent(  MouseEvent mouseEvent){
    getCurrentWinCtx().addAppAttribute("edit", "edit");
		Dataset ds = AppLifeCycleContext.current().getViewContext().getView()
				.getViewModels().getDataset("defdoclist");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要修改的自定义档案！");
		else {
			checkEditRow(row);
			AppLifeCycleContext.current().getWindowContext().popView("edit",
					DialogConstant.FIVE_ELE_WIDTH, DialogConstant.SIX_ELE_HEIGHT, "编辑");
			getCurrentWinCtx().addAppAttribute("data", row);
		}
  }
  public void addEvent(  MouseEvent mouseEvent){
	  AppLifeCycleContext.current().getWindowContext().popView("edit",
				DialogConstant.FIVE_ELE_WIDTH, DialogConstant.SIX_ELE_HEIGHT, "增加");
		getCurrentWinCtx().addAppAttribute("edit", "add");
  }
  public void maintenEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID("defdoclist");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请选择需要维护的自定义档案！");
		else{
			String pk_defdoclist = row.getString(ds.nameToIndex("pk_defdoclist"));
			UFBoolean isgrade = row.getUFBoolean(ds.nameToIndex("isgrade"));
			Integer mngctlmode = row.getInt(ds.nameToIndex("mngctlmode"));
			getCurrentWinCtx().addAppAttribute("pk_defdoclist", pk_defdoclist);
			getCurrentWinCtx().addAppAttribute("mngctlmode", mngctlmode);
			if(isgrade.equals(UFBoolean.TRUE)){
				String coderule = row.getString(ds.nameToIndex("coderule"));
				if(coderule!=null&&coderule.length()>0){
					getCurrentWinCtx().addAppAttribute("coderule", coderule);
				}else
					getCurrentWinCtx().addAppAttribute("coderule", "");
				AppLifeCycleContext.current().getWindowContext().popView("maintenTree",
						DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT, "维护");
			}
			else {
				AppLifeCycleContext.current().getWindowContext().popView("maintenGrid",
						DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT, "维护");
			}	
		}
  }
  public void pluginedittomain_plugin(  Map keys){
    LfwWidget mainView = AppLifeCycleContext.current().getWindowContext()
				.getViewContext("main").getView();
		Dataset ds = getDatasetByID("defdoclist");
		String edit = (String )getCurrentWinCtx().getAppAttribute("edit");
		if(edit.equals("add")){
			Row emptyRow = ds.getEmptyRow();
			setValues(emptyRow,ds,keys);
			emptyRow.setValue(ds.nameToIndex("creationtime"), new UFDateTime());
			emptyRow.setValue(ds.nameToIndex("modifiedtime"), new UFDateTime());
			emptyRow.setValue(ds.nameToIndex("creator"), LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			ds.setSelectedIndex(ds.getRowIndex(emptyRow));
			DefdoclistVO[] vos=  new Dataset2SuperVOSerializer<DefdoclistVO>().serialize(ds, emptyRow);
			if (vos == null || vos.length == 0)
				return;
			BatchOperateVO batchVO = new BatchOperateVO();
			batchVO.setAddObjs(vos);
			IDefdoclistService listSer= NCLocator.getInstance().lookup(IDefdoclistService.class);
			try {
				listSer.batchSaveDefdoclist(batchVO);
			} catch (BusinessException e) {
				throw new LfwRuntimeException(e);
			}
		}
		else if(edit.equals("edit")){
			Row row = ds.getSelectedRow();
			setValues(row, ds, keys);
//			checkAddRow(row,ds);
			row.setValue(ds.nameToIndex("modifiedtime"), new UFDateTime());
			row.setValue(ds.nameToIndex("modifier"), LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			DefdoclistVO[] vos=  new Dataset2SuperVOSerializer<DefdoclistVO>().serialize(ds, ds.getSelectedRow());
			if (vos == null || vos.length == 0)
				return;
			BatchOperateVO batchVO = new BatchOperateVO();
			batchVO.setUpdObjs(vos);
			IDefdoclistService listSer= NCLocator.getInstance().lookup(IDefdoclistService.class);
			try {
				listSer.batchSaveDefdoclist(batchVO);
			} catch (BusinessException e) {
				throw new LfwRuntimeException(e);
			}
		}
	    AppLifeCycleContext.current().getApplicationContext()
		.getCurrentWindowContext().closeView("edit");
	    AppDynamicCompUtil util = new AppDynamicCompUtil(AppLifeCycleContext.current().getApplicationContext(),AppLifeCycleContext.current().getViewContext());
		util.refreshDataset(getDatasetByID("defdoclist"));
  }
  private void setValues(  Row row,  Dataset ds,  Map map){
    TranslatedRow r = (TranslatedRow) map.get("editRow");
		String[] keys = r.getKeys();

		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
  }
  private Dataset getDatasetByID(  String dsID){
    return AppLifeCycleContext.current().getViewContext().getView()
		.getViewModels().getDataset(dsID);
  }
  public void delEvent(  MouseEvent mouseEvent){
		if (getDatasetByID("defdoclist").getSelectedRow() == null)
			throw new LfwRuntimeException("请选择需要删除的自定义档案！");
		
	  DefdoclistVO[] vos=  new Dataset2SuperVOSerializer<DefdoclistVO>().serialize(getDatasetByID("defdoclist"), getDatasetByID("defdoclist").getSelectedRows());
		if (vos == null || vos.length == 0)
			return;
		BatchOperateVO batchVO = new BatchOperateVO();
		batchVO.setDelObjs(vos);
		IDefdoclistService listSer= NCLocator.getInstance().lookup(IDefdoclistService.class);
		try {
			listSer.batchSaveDefdoclist(batchVO);
		} catch (BusinessException e) {
			throw new LfwRuntimeException(e);
		}catch(Exception e){
			throw new LfwRuntimeException(e);
		}
		AppDynamicCompUtil util = new AppDynamicCompUtil(AppLifeCycleContext.current().getApplicationContext(),AppLifeCycleContext.current().getViewContext());
		util.refreshDataset(getDatasetByID("defdoclist"));
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
		ButtonStateManager.updateButtons();
  }
  public void onAfterRowUnSelect(  DatasetEvent datasetEvent){
    ButtonStateManager.updateButtons();
  }
  private void checkEditRow(  Row row){
    Dataset ds = getDatasetByID("defdoclist");
	  Integer doclevel = (Integer)row.getValue(ds.nameToIndex("doclevel"));
	  if(doclevel!=null && doclevel!=0){
		  throw new LfwRuntimeException("系统档案不允许修改");
	  }
  }
  public void pluginsimpleQuery_plugin(  Map keys){
//	  FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
//	     String wheresql = whereSql.getWhere();
//	     QueryCmd cmd = new QueryCmd("main", "defdoclist", wheresql);
//	     cmd.excute();
  }
}
