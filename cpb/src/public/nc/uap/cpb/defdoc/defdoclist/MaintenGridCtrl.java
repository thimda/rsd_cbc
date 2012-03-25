package nc.uap.cpb.defdoc.defdoclist;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.itf.bd.defdoc.IDefdocService;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.tags.AppDynamicCompUtil;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.bd.defdoc.DefdocVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
/** 
 * @author chouhl
 */
public class MaintenGridCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent){
    String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute(
				"pk_defdoclist");
		String wheresql = "pk_defdoclist = '" + pk_defdoclist + "'";
		QueryCmd cmd = new QueryCmd("maintenGrid", "defdoc", wheresql);
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
				} catch (Exception e) {
					throw new LfwRuntimeException(e);
				}
			}
			reloadDataset();
		}
	}
  public void cancelEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID("defdoc");
    if(ds.isEnabled()){
    	ds.setEnabled(false);
    	reloadDataset();
    }
  }
  public void addEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID("defdoc");
	  Row emptyRow = ds.getEmptyRow();
	  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  emptyRow.setValue(ds.nameToIndex("pk_defdoclist"), pk_defdoclist);
	  ds.addRow(emptyRow);
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow));
	  ds.setEnabled(true);
  }
  public void editEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID("defdoc");
	  ds.setEnabled(true);
  }
  public void delEvent(MouseEvent mouseEvent) {
		Dataset ds = getDatasetByID("defdoc");
		if (ds.getSelectedRows() != null && ds.getSelectedRows().length != 0) {
			IDefdocService defser = NCLocator.getInstance().lookup(
					IDefdocService.class);
			DefdocVO[] vos = new Dataset2SuperVOSerializer<DefdocVO>()
					.serialize(ds, ds.getSelectedRows());
			try {
				defser.deleteDefdocs(vos[0].getPk_org(), vos);
			} catch (BusinessException e) {
				throw new LfwRuntimeException(e);
			} catch (Exception e) {
				throw new LfwRuntimeException(e);
			}
		}
		reloadDataset();
	}
  private void reloadDataset(){
	  Dataset ds = getDatasetByID("defdoc");
	  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  String wheresql = "pk_defdoclist = '"+pk_defdoclist+"'";
	  QueryCmd cmd = new QueryCmd("maintenGrid", "defdoc", wheresql);
	  cmd.excute();
  }
}
