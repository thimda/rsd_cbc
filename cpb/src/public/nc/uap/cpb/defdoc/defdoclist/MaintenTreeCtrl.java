package nc.uap.cpb.defdoc.defdoclist;
import nc.bs.framework.common.NCLocator;
import nc.bs.uif2.LockFailedException;
import nc.bs.uif2.VersionConflictException;
import nc.itf.bd.defdoc.IDefdocService;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.tags.AppDynamicCompUtil;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.WindowContext;
import java.awt.MenuComponent;
import java.util.ArrayList;

import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.bd.defdoc.DefdocVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
/** 
 * @author chouhl
 */
public class MaintenTreeCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent){
    String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  String wheresql = "pk_defdoclist = '"+pk_defdoclist+"'";
	  QueryCmd cmd = new QueryCmd("maintenTree", "defdoc", wheresql);
	  cmd.excute();
  }
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
					.getCurrentWindowContext();
  }
  private Dataset getDatasetByID(  String dsID){
    return AppLifeCycleContext.current().getViewContext().getView()
			.getViewModels().getDataset(dsID);
  }
  public void addEvent(  MouseEvent mouseEvent){
    //	  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
//	  menu.setVisible(false);
    Dataset ds = getDatasetByID(getDatasetId());
	  Row emptyRow = ds.getEmptyRow();
	  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  getCurrentWinCtx().addAppAttribute("pk", pk_defdoclist);
	  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk", pk_defdoclist);
	  emptyRow.setValue(ds.nameToIndex("pk_defdoclist"), pk_defdoclist);
	  ds.addRow(emptyRow);
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow));
	  ds.setEnabled(true);
	  
//	  //增加 编辑 删除按钮失效
//	  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
//	  menu.getMenuList().get(0).setEnabled(false);
//	  menu.getMenuList().get(1).setEnabled(false);
//	  menu.getMenuList().get(2).setEnabled(false);
//	//保存和取消按钮工作
//		MenubarComp saveMenu = (MenubarComp) AppLifeCycleContext.current()
//		.getViewContext().getView().getViewMenus().getMenuBar(
//				"defdocSaveMenu");
//		saveMenu.getMenuList().get(0).setEnabled(true);
//		saveMenu.getMenuList().get(1).setEnabled(true);
	  
  }
  private String getDatasetId(){
    return "defdoc";
  }
  public void editEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID(getDatasetId());
	  if(ds.getSelectedRows()!=null && ds.getSelectedRows().length == 1){
		  ds.setEnabled(true);
	  }
  }
  public void delEvent(  MouseEvent mouseEvent){
	  Dataset ds = getDatasetByID("defdoc");
    if (ds.getSelectedRows()!=null && ds.getSelectedRows().length!=0) {
    		IDefdocService defser = NCLocator.getInstance().lookup(IDefdocService.class);
    		DefdocVO[] vos = new Dataset2SuperVOSerializer<DefdocVO>().serialize(ds, ds.getSelectedRows());
    		try {
				defser.deleteDefdocs(vos[0].getPk_org(), vos);
			} catch (BusinessException e) {
				throw new LfwRuntimeException(e);
			}catch(Exception e){
				throw new LfwRuntimeException(e);
			}
		}
    //刷新ds
    reloadDataset();
  }
  public void saveEvent(MouseEvent mouseEvent) {
		Dataset ds = getDatasetByID("defdoc");
		if (ds.isEnabled()) {
			if (ds.getSelectedRows() != null
					&& ds.getSelectedRows().length != 0) {
				IDefdocService defser = NCLocator.getInstance().lookup(
						IDefdocService.class);
				DefdocVO[] vos = new Dataset2SuperVOSerializer<DefdocVO>()
						.serialize(ds, ds.getSelectedRows());
				BatchOperateVO batchVO = new BatchOperateVO();
				ArrayList<DefdocVO> addList = new ArrayList<DefdocVO>();
				ArrayList<DefdocVO> editList = new ArrayList<DefdocVO>();
				for (DefdocVO vo : vos) {
					if (vo.getPk_defdoc() != null
							&& vo.getPk_defdoc().length() > 0) {
						editList.add(vo);
					} else
						addList.add(vo);
				}
				DefdocVO[] addvos = addList.toArray(new DefdocVO[0]);
				DefdocVO[] aditvos = editList.toArray(new DefdocVO[0]);
				batchVO.setAddObjs(addvos);
				batchVO.setUpdObjs(aditvos);
				try {
					defser.batchSaveDefdoc(vos[0].getPk_org(), batchVO);
				} catch (BusinessException e) {
					throw new LfwRuntimeException(e);
				} catch (BusinessRuntimeException e) {
					throw new LfwRuntimeException(e);
				} catch(NullPointerException e){
					throw new LfwRuntimeException(e);
				}catch (Exception e) {
					throw new LfwRuntimeException(e);
				}
			}

			// 刷新ds
			reloadDataset();
			// //增加按钮可以工作
			// MenubarComp menu =
			// (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
			// menu.getMenuList().get(0).setEnabled(true);
		}
	}
  public void cancelEvent(MouseEvent mouseEvent) {
		Dataset ds = getDatasetByID("defdoc");
		// 刷新ds
		if (ds.isEnabled()) {
			reloadDataset();
			ds.setEnabled(false);
			// // 增加按钮可以工作
			// MenubarComp menu = (MenubarComp) AppLifeCycleContext.current()
			// .getViewContext().getView().getViewMenus().getMenuBar(
			// "defdocMenu");
			// menu.getMenuList().get(0).setEnabled(true);
			//		
			// //保存和取消按钮失效
			// MenubarComp saveMenu = (MenubarComp)
			// AppLifeCycleContext.current()
			// .getViewContext().getView().getViewMenus().getMenuBar(
			// "defdocSaveMenu");
			// saveMenu.getMenuList().get(0).setEnabled(false);
			// saveMenu.getMenuList().get(1).setEnabled(false);
		}

	}
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
		ButtonStateManager.updateButtons();
//		MenubarComp menu = (MenubarComp) AppLifeCycleContext.current()
//		.getViewContext().getView().getViewMenus().getMenuBar(
//				"defdocMenu");
//		if (ds.getSelectedRow().getString(ds.nameToIndex("pk_defdoc")) != null
//				&& ds.getSelectedRow().getString(ds.nameToIndex("pk_defdoc"))
//						.length() > 0) {
//			// 增加 编辑 删除按钮可以工作
//			menu.getMenuList().get(0).setEnabled(true);
//			menu.getMenuList().get(1).setEnabled(true);
//			menu.getMenuList().get(2).setEnabled(true);
//		}
//		else{
//			menu.getMenuList().get(0).setEnabled(false);
//			menu.getMenuList().get(1).setEnabled(false);
//			menu.getMenuList().get(2).setEnabled(false);
//		}
  }
  public void onAfterRowUnSelect(  DatasetEvent datasetEvent){
	  ButtonStateManager.updateButtons();
  }
  private void reloadDataset(){
	  Dataset ds = getDatasetByID("defdoc");
	  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  String wheresql = "pk_defdoclist = '"+pk_defdoclist+"'";
	  QueryCmd cmd = new QueryCmd("maintenTree", "defdoc", wheresql);
	  cmd.excute();
  }
}
