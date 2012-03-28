package nc.uap.cpb.defdoc.defdoclist;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.itf.bd.defdoc.IDefdocService;
import nc.itf.bd.defdoc.IDefdocQryService;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.vo.bd.meta.BatchOperateVO;
import java.util.ArrayList;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.vo.bd.defdoc.DefdocVO;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.BusinessException;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 * @author chouhl
 */
public class MaintenGridCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent){
    String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute(
				"pk_defdoclist");
    Integer mngctlmode = (Integer) getCurrentWinCtx().getAppAttribute(
	"mngctlmode");
    Dataset ds = getDatasetByID(getDatasetId());
	ds.clear();
	ReferenceComp text = (ReferenceComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("orgInput");
	text.setValue(" ");
	text.setShowValue(" ");
	//AppDynamicCompUtil util = new AppDynamicCompUtil(AppLifeCycleContext.current().getApplicationContext(),AppLifeCycleContext.current().getViewContext());

	// 由管控模式判断所属组织默认值
	// 如果管控模式为全局 所属组织默认值设置为全局
	if (mngctlmode == 0) {
		ds.getFieldSet().getField("pk_org").setDefaultValue(
				"GLOBLE00000000000000");
		text.setEnabled(false);
	}
	// 如果管控模式为集团 所属组织默认值设置为grp1
	else if (mngctlmode == 1) {
		ds.getFieldSet().getField("pk_org").setDefaultValue(
				"0001PT10000000000362");
		text.setEnabled(false);
	}
	// 如果管控模式为集团+业务单元、业务单元 组织由参照输入
	else
	{
		ds.getFieldSet().getField("pk_org").setDefaultValue(null);
		text.setEnabled(true);
	}
	if (ds.getFieldSet().getField("pk_org").getDefaultValue() != null
				&& ds.getFieldSet().getField("pk_org").getDefaultValue()
						.toString().trim().length() > 0) {
		IDefdocQryService ser = NCLocator.getInstance().lookup(
				IDefdocQryService.class);
		String[] str = { pk_defdoclist };
		DefdocVO[] vos;
		try {
			vos = ser.queryDefdocVOsByDoclistPk(pk_defdoclist, ds
					.getFieldSet().getField("pk_org").getDefaultValue()
					.toString(), ds.getFieldSet().getField("pk_group")
					.getDefaultValue().toString());
		} catch (BusinessException e) {
			throw new LfwRuntimeException(e);
		}
		if (vos != null && vos.length != 0) {
			DefdocHelper.putVOstoDS(vos, getDatasetByID(getDatasetId()),
					DefdocVO.class);
			ButtonStateManager.updateButtons();
			checkbuttons();
		}
	}
  }
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext()
				.getCurrentWindowContext();
  }
  private Dataset getDatasetByID(  String dsID){
    return AppLifeCycleContext.current().getViewContext().getView()
				.getViewModels().getDataset(dsID);
  }
  public void saveEvent(  MouseEvent mouseEvent){
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
			ds.setEnabled(false);
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
  public void delEvent(  MouseEvent mouseEvent){
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
		ds.clear();
		if (ds.getFieldSet().getField("pk_org").getDefaultValue() == null)return;
		String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute(
				"pk_defdoclist");
		IDefdocQryService ser = NCLocator.getInstance().lookup(
				IDefdocQryService.class);
		String[] str = { pk_defdoclist };
		DefdocVO[] vos;
		try {
			vos = ser.queryDefdocVOsByDoclistPk(pk_defdoclist, ds.getFieldSet()
					.getField("pk_org").getDefaultValue().toString(), ds
					.getFieldSet().getField("pk_group").getDefaultValue()
					.toString());
		} catch (BusinessException e) {
			throw new LfwRuntimeException(e);
		}
		if (vos != null && vos.length != 0) {
			DefdocHelper.putVOstoDS(vos, getDatasetByID(getDatasetId()),
					DefdocVO.class);
			ButtonStateManager.updateButtons();
			checkbuttons();
		}
  }
  private String getDatasetId(){
    return "defdoc";
  }
  public void valueChanged(  TextEvent textEvent){
	  ReferenceComp text=(ReferenceComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("orgInput");
	  String pk_org=text.getValue();
	  if (pk_org != null && pk_org.trim().length() > 0) {
			getDatasetByID(getDatasetId()).getFieldSet().getField("pk_org")
					.setDefaultValue(pk_org);
			reloadDataset();
		}
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
		ButtonStateManager.updateButtons();
  }
  public void onAfterRowUnSelect(  DatasetEvent datasetEvent){
	  ButtonStateManager.updateButtons();
  }
  public void enableEvent(  MouseEvent mouseEvent){
	    Dataset ds = getDatasetByID(getDatasetId());
		  if (ds.getSelectedRows() != null
					&& ds.getSelectedRows().length != 0) {
				IDefdocService defser = NCLocator.getInstance().lookup(
						IDefdocService.class);
				DefdocVO[] vos = new Dataset2SuperVOSerializer<DefdocVO>()
						.serialize(ds, ds.getSelectedRows());
				try {
					defser.enableDefdocs(vos[0].getPk_org(), vos);
				} catch (BusinessException e) {
					throw new LfwRuntimeException(e);
				}catch(Exception e){
					throw new LfwRuntimeException(e);
				}
				reloadDataset();
		  }
	  }
	  public void DisableEvent(  MouseEvent mouseEvent){
	    Dataset ds = getDatasetByID(getDatasetId());
		  if (ds.getSelectedRows() != null
					&& ds.getSelectedRows().length != 0) {
				IDefdocService defser = NCLocator.getInstance().lookup(
						IDefdocService.class);
				DefdocVO[] vos = new Dataset2SuperVOSerializer<DefdocVO>()
						.serialize(ds, ds.getSelectedRows());
				try {
					defser.disableDefdocs(vos[0].getPk_org(), vos);
				} catch (BusinessException e) {
					throw new LfwRuntimeException(e);
				}catch(Exception e){
					throw new LfwRuntimeException(e);
				}
				reloadDataset();
		  }
	  }
	  private void checkbuttons(){
		  Dataset ds = getDatasetByID(getDatasetId());
		  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocSaveMenu");
		  if(ds.getSelectedRows() != null && ds.getSelectedRows().length != 0){
			  if(!ds.isEnabled()){
				  menu.getMenuList().get(0).setEnabled(false);
				  menu.getMenuList().get(1).setEnabled(false);
			  }
		  }
	  }
}
