package nc.uap.cpb.defdoc.defdoclist;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.bd.defdoc.IDefdoclistService;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ApplicationContext;
import nc.vo.bd.defdoc.DefdocVO;
import nc.vo.bd.defdoc.DefdoclistVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.tags.AppDynamicCompUtil;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.lang.UFBoolean;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 * @author chouhl
 */
public class MainCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void onDataLoad_defdoclist(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
		QueryCmd cmd = new QueryCmd("main", "defdoclist", "1=1");
		  cmd.excute();
		  ButtonStateManager.updateButtons();
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
					"600", "500", "编辑");
			getCurrentWinCtx().addAppAttribute("data", row);
		}
  }
  public void addEvent(  MouseEvent mouseEvent){
    AppLifeCycleContext.current().getWindowContext().popView("edit", "600",
				"500", "增加");
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
			getCurrentWinCtx().addAppAttribute("pk_defdoclist", pk_defdoclist);
			if(isgrade.equals(UFBoolean.TRUE)){
				AppLifeCycleContext.current().getWindowContext().popView("maintenTree",
						"800", "500", "维护");
			}
			else {
				AppLifeCycleContext.current().getWindowContext().popView("maintenGrid",
						"800", "500", "维护");
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
//			checkAddRow(emptyRow,ds);
//			ds.addRow(emptyRow);
			emptyRow.setValue(ds.nameToIndex("creationtime"), new UFDateTime());
			emptyRow.setValue(ds.nameToIndex("modifiedtime"), new UFDateTime());
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
//			catch(InvocationTargetException e){
//				throw new LfwRuntimeException(e);
//			}
		}
		
		
//		DefdoclistVO vo = vos[0];
//		PtBaseDAO pt = new PtBaseDAO();
//		if(edit.equals("edit")){
//			try {
//				pt.updateVO(vo);
//			} catch (DAOException e) {
//				throw new LfwRuntimeException(e);
//			}
//		} else if(edit.equals("add"))
//			try {
//				pt.insertVO(vo);
//			} catch (DAOException e) {
//				throw new LfwRuntimeException(e);
//			}
//		UifSaveCmd cmd = new UifSaveCmd("defdoclist",null,LfwExAggVO.class.getName());
//		  cmd.execute();
		
		
		
	    AppLifeCycleContext.current().getApplicationContext()
		.getCurrentWindowContext().closeView("edit");
	    AppDynamicCompUtil util = new AppDynamicCompUtil(AppLifeCycleContext.current().getApplicationContext(),AppLifeCycleContext.current().getViewContext());
		util.refreshDataset(getDatasetByID("defdoclist"));
  }
  private void checkAddRow(Row row, Dataset ds) {
		String code = row.getString(ds.nameToIndex("code"));
		String name = row.getString(ds.nameToIndex("name"));
		String pk_defdoclist;
		try{
			pk_defdoclist = row.getString(ds.nameToIndex("pk_defdoclist"));
		}catch(Exception e){
			pk_defdoclist = "";
		}
		if (code == null || code.length() == 0)
			throw new LfwRuntimeException("自定义档案编码不能为空！");
		if (name == null || name.length() == 0)
			throw new LfwRuntimeException("自定义档案名称不能为空！");
		if (row.getValue(ds.nameToIndex("mngctlmode")) == null
				|| row.getValue(ds.nameToIndex("mngctlmode")).toString()
						.length() == 0)
			throw new LfwRuntimeException("档案管控模式不能为空！");
		PtBaseDAO pt = new PtBaseDAO();
		String where = "(code = '" + code + "' or name = '" + name
				+ "')and pk_defdoclist != '" + pk_defdoclist + "'";
		try {
			DefdoclistVO[] vos = (DefdoclistVO[]) pt.queryByCondition(
					DefdoclistVO.class, where);
			if (vos != null && vos.length > 0)
				throw new LfwRuntimeException("自定义档案编码/名称必须全局唯一");
		} catch (DAOException e) {
			throw new LfwRuntimeException(e);
		}
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
  public void delEvent(MouseEvent mouseEvent) {
//		if (getDatasetByID("defdoclist").getSelectedRows() != null
//				&& getDatasetByID("defdoclist").getSelectedRows().length != 0) {
//			if (checkDelRows(getDatasetByID("defdoclist").getSelectedRows())) {
//				UifDelCmd cmd = new UifDelCmd("defdoclist", LfwExAggVO.class
//						.getName());
//				CmdInvoker.invoke(cmd);
//			}
//			else throw new LfwRuntimeException("选中行档案存在档案内容，不能删除！");
//		}
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
  private boolean checkDelRows(Row[] rows){
	  Dataset ds = getDatasetByID("defdoclist");
		for (Row row : rows) {
			String pk_defdoclist = row.getString(ds
					.nameToIndex("pk_defdoclist"));
			PtBaseDAO pt = new PtBaseDAO();
			try {
				SuperVO[] vos = pt.queryByCondition(DefdocVO.class,
						"pk_defdoclist = '" + pk_defdoclist + "'");
				if (vos != null && vos.length > 0)
					return false;
			} catch (DAOException e) {
				throw new LfwRuntimeException(e);
			}
		}
	return true;  
  }
  private void checkEditRow(Row row){
	  Dataset ds = getDatasetByID("defdoclist");
	  Integer doclevel = (Integer)row.getValue(ds.nameToIndex("doclevel"));
	  if(doclevel!=null && doclevel!=0){
		  throw new LfwRuntimeException("系统档案不允许修改");
	  }
  }
}
