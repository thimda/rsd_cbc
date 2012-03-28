package nc.uap.cpb.defdoc.defdoclist;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.RecursiveTreeLevel;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.tags.AppDynamicCompUtil;
import nc.uap.lfw.core.ctx.WindowContext;
import java.awt.MenuComponent;
import nc.uap.lfw.core.comp.TreeLevel;
import nc.itf.bd.defdoc.IDefdocQryService;
import nc.itf.bd.defdoc.IDefdocService;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.data.Dataset;
import nc.vo.bd.meta.BatchOperateVO;
import java.util.ArrayList;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.vo.bd.defdoc.DefdocVO;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.vo.pub.BusinessRuntimeException;
import nc.uap.lfw.core.ctrl.IController;
import nc.vo.pub.BusinessException;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.comp.CodeTreeLevel;
import nc.bs.uif2.LockFailedException;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.bs.uif2.VersionConflictException;
/** 
 * @author chouhl
 */
public class MaintenTreeCtrl implements IController {
  private static final long serialVersionUID=1L;
  public void beforeShow(  DialogEvent dialogEvent){
	  
	  Dataset ds = getDatasetByID(getDatasetId());
		ds.clear();
		ds.setEnabled(false);
		//树初始化
		String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute(
				"pk_defdoclist");
		String coderule = (String) getCurrentWinCtx().getAppAttribute(
				"coderule");
		Integer mngctlmode = (Integer) getCurrentWinCtx().getAppAttribute(
				"mngctlmode");
		TreeViewComp tree = (TreeViewComp) AppLifeCycleContext.current()
				.getViewContext().getView().getViewComponents().getComponent(
						"defdocTree");
		// 有编码规则判断是编码树还是递归树
		if (coderule != null && coderule.trim().length() != 0) {
			CodeTreeLevel treelevel = new CodeTreeLevel();
			treelevel.setDataset("defdoc");
			treelevel.setId("level1");
			treelevel.setMasterKeyField("pk_defdoc");
			treelevel.setCodeRule(coderule);
			treelevel.setRecursiveKeyField("pk_defdoc");
			treelevel.setRecursivePKeyField("pid");
			treelevel.setLabelFields("name");
			tree.setTopLevel(treelevel);
			
			FormComp form = (FormComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("defdocForm");
			form.getElementById("pid_name").setVisible(false);
		} else {
			//递归树 
			RecursiveTreeLevel treelevel = new RecursiveTreeLevel();
			treelevel.setDataset("defdoc");
			treelevel.setId("level1");
			treelevel.setMasterKeyField("pk_defdoc");
			treelevel.setRecursiveKeyField("pk_defdoc");
			treelevel.setRecursivePKeyField("pid");
			treelevel.setLabelFields("name");
			tree.setTopLevel(treelevel);
			
			FormComp form = (FormComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("defdocForm");
			form.getElementById("pid_name").setVisible(true);
		}
		//业务单元参照初始化
		ReferenceComp text = (ReferenceComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("orgInput");
		text.setValue(null);
		text.setShowValue(" ");

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
		if (ds.getFieldSet().getField("pk_org").getDefaultValue() != null) {
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
			}catch(Exception e){
				throw new LfwRuntimeException(e);
			}
			if (vos != null && vos.length != 0) {
				DefdocHelper.putVOstoDS(vos, getDatasetByID(getDatasetId()),
						DefdocVO.class);
				tree.setEnabled(true);
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
  public void addEvent(  MouseEvent mouseEvent){
    //	  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
//	  menu.setVisible(false);
    Dataset ds = getDatasetByID(getDatasetId());
	  Row emptyRow = ds.getEmptyRow();
	  //传pk_defdoclist
	  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
	  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_defdoclist", pk_defdoclist);
	  //传pk_group
	  String pk_group = getDatasetByID(getDatasetId()).getFieldSet().getField("pk_group").getDefaultValue().toString();
	  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_group", pk_group);
	  emptyRow.setValue(ds.nameToIndex("pk_defdoclist"), pk_defdoclist);
	  ds.addRow(emptyRow);
	  ds.setSelectedIndex(ds.getRowIndex(emptyRow));
	  ds.setEnabled(true);
	  //树不可选
	  TreeViewComp tree = (TreeViewComp) AppLifeCycleContext.current()
		.getViewContext().getView().getViewComponents().getComponent(
				"defdocTree");
	  tree.setEnabled(false);
	  //增加、编辑、删除按钮失效
	  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
	  //menu.getMenuList().get(0).setEnabled(false);
	  menu.getMenuList().get(1).setEnabled(false);
	  menu.getMenuList().get(2).setEnabled(false);
	  checkbuttons();
	  
  }
  private String getDatasetId(){
    return "defdoc";
  }
  public void editEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID(getDatasetId());
    //如果存在选中行
	  if(ds.getSelectedRows()!=null && ds.getSelectedRows().length == 1){
		  ds.setEnabled(true);
		  //传pk_defdoclist
		  String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
		  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_defdoclist", pk_defdoclist);
		  //传pk_group
		  String pk_group = getDatasetByID(getDatasetId()).getFieldSet().getField("pk_group").getDefaultValue().toString();
		  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_group", pk_group);
		  
		//树不可选
		  TreeViewComp tree = (TreeViewComp) AppLifeCycleContext.current()
			.getViewContext().getView().getViewComponents().getComponent(
					"defdocTree");
		  tree.setEnabled(false);
		  //增加、编辑、删除按钮失效
		  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
		  //menu.getMenuList().get(0).setEnabled(false);
		  menu.getMenuList().get(1).setEnabled(false);
		  menu.getMenuList().get(2).setEnabled(false);
		  checkbuttons();
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
    if(ds.isEnabled())ds.setEnabled(false);
    reloadDataset();
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
				} catch(NullPointerException e){
					throw new LfwRuntimeException(e);
				}catch (Exception e) {
					throw new LfwRuntimeException(e);
				}
			}

			// 刷新ds
			ds.setEnabled(false);
			reloadDataset();
			
			//树可选
			  TreeViewComp tree = (TreeViewComp) AppLifeCycleContext.current()
				.getViewContext().getView().getViewComponents().getComponent(
						"defdocTree");
			  tree.setEnabled(true);
			  //增加、编辑、删除按钮恢复
			  ButtonStateManager.updateButtons();
			  checkbuttons();
		}
  }
  public void cancelEvent(  MouseEvent mouseEvent){
    Dataset ds = getDatasetByID("defdoc");
		// 刷新ds
		if (ds.isEnabled()) {
			reloadDataset();
			ds.setEnabled(false);
			//树可选
			  TreeViewComp tree = (TreeViewComp) AppLifeCycleContext.current()
				.getViewContext().getView().getViewComponents().getComponent(
						"defdocTree");
			  tree.setEnabled(true);
			  //增加、编辑、删除按钮恢复
			  MenubarComp menu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
			  //ButtonStateManager.updateButtons();
			  //checkbuttons();
		}
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
		ButtonStateManager.updateButtons();
		checkbuttons();
  }
  public void onAfterRowUnSelect(  DatasetEvent datasetEvent){
	  ButtonStateManager.updateButtons();
	  checkbuttons();
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
  public void valueChanged(  TextEvent textEvent){
	  ReferenceComp text=(ReferenceComp)AppLifeCycleContext.current().getViewContext().getView().getViewComponents().getComponent("orgInput");
	  String pk_org=text.getValue();
	  if (pk_org != null && pk_org.trim().length() > 0) {
			getDatasetByID(getDatasetId()).getFieldSet().getField("pk_org")
					.setDefaultValue(pk_org);
			//传pk_org  pk_group
			LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_org", pk_org);
			String pk_group = getDatasetByID(getDatasetId()).getFieldSet().getField("pk_group").getDefaultValue().toString();
			LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_group", pk_group);
			reloadDataset();
		}
  }
  private void checkbuttons(){
	  Dataset ds = getDatasetByID(getDatasetId());
	  MenubarComp defdocmenu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocMenu");
	  MenubarComp Savemenu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("defdocSaveMenu");
	  MenubarComp enablemenu = (MenubarComp)AppLifeCycleContext.current().getViewContext().getView().getViewMenus().getMenuBar("enableMenu");
	  if(!ds.isEnabled()){
		  defdocmenu.getMenuList().get(0).setEnabled(true);
	  }
	  else defdocmenu.getMenuList().get(0).setEnabled(false);
	  if(ds.getSelectedRows() != null && ds.getSelectedRows().length != 0){
		  if(!ds.isEnabled()){
			  Savemenu.getMenuList().get(0).setEnabled(false);
			  Savemenu.getMenuList().get(1).setEnabled(false);
			  enablemenu.getMenuList().get(0).setEnabled(true);
			  enablemenu.getMenuList().get(1).setEnabled(true);
		  }
		  else {
			  Savemenu.getMenuList().get(0).setEnabled(true);
			  Savemenu.getMenuList().get(1).setEnabled(true);
			  enablemenu.getMenuList().get(0).setEnabled(false);
			  enablemenu.getMenuList().get(1).setEnabled(false);
		  }
	  }
  }
}
