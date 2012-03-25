package nc.uap.cpb.org.responsibility;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.funcres.extention.FuncResExtentionUtil;
import nc.uap.cpb.org.itf.ICpResponsibilityBill;
import nc.uap.cpb.org.vos.CpFuncResVO;
import nc.uap.cpb.org.vos.CpRespFuncVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebTreeModel;
import nc.uap.lfw.core.comp.WebTreeNode;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.pub.lang.UFDate;
/** 
 * 2012-1-13 上午09:44:48
 * @author limingf
 */
public class RelateController implements IController {
  private static final long serialVersionUID=1L;
  
  public void onBeforeShow(  DialogEvent dialogEvent){
	  LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset leftds = edit.getViewModels().getDataset("ds_selecting_funcres");
	  Dataset rightds = edit.getViewModels().getDataset("ds_selected_funcres");
	  leftds.clear();
	  rightds.clear();
	  if(leftds.getCurrentKey()==null||"".equals(leftds.getCurrentKey())){
		  leftds.setCurrentKey(Dataset.MASTER_KEY);
	  }
	  if(rightds.getCurrentKey()==null||"".equals(rightds.getCurrentKey())){
		  rightds.setCurrentKey(Dataset.MASTER_KEY);
	  }
	  CpFuncResVO[] funcres = FuncResExtentionUtil.getFuncRes();
	  if(funcres==null)
		  return;
	  for(CpFuncResVO vo:funcres){
		  Row row = leftds.getEmptyRow();
		  row.setValue(leftds.nameToIndex("code"), vo.getCode());
		  row.setValue(leftds.nameToIndex("name"), vo.getName());
		  row.setValue(leftds.nameToIndex("pk_busifunc"), vo.getPk_busifunc());
		  row.setValue(leftds.nameToIndex("pk_funcres"), vo.getPk_funcres());
		  row.setValue(leftds.nameToIndex("pk_parent"), vo.getPk_parent());
		  row.setValue(leftds.nameToIndex("type"), vo.getType());
		  leftds.addRow(row);
	  }
  }
  public void onDataLoad_ds_selecting_funcres(  DataLoadEvent dataLoadEvent){
  }
  public void onCancelBtnClick(  MouseEvent mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("relate");
  }
  /** 
 * 增加选中
 * @param mouseEvent
 */
  public void onAddBtnClick(  MouseEvent mouseEvent){
      LfwWidget relate = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset leftds = relate.getViewModels().getDataset("ds_selecting_funcres");
	  Row[] rows = leftds.getSelectedRows();
	  if(rows==null||rows.length<1)
		  return;
	  Dataset rightds = relate.getViewModels().getDataset("ds_selected_funcres");
	  for(int i=0;i<rows.length;i++){
		  rightds.addRow(rows[i]);
	  }
//	  TreeViewComp lefttree = (TreeViewComp) relate.getViewComponents().getComponent("selecting_funcres_tree");
//	  WebTreeModel model = lefttree.getTreeModel();
//	  WebTreeNode selectedNode = model.getCurrNode();
//	  String currentnodeid = selectedNode.getId();
//	  WebTreeNode currentnode = selectedNode;
//	  //移动父节点
//	  while(currentnode.getParentNode()!=null){
//		  String currrowid = currentnode.getRowId();
//		  Row row = leftds.getRowById(currrowid);
//		  Row rrow = rightds.getRowById(row.getRowId());
//		  if(rrow==null)
//			  rightds.addRow(row);
//		  if(currentnodeid.equals(currentnode.getId()))
//			  leftds.removeRow(row);
//		  currentnode = model.getTreeNodeById(currentnode.getParentNode());
//	}
//	  //移动子节点
//	  List<WebTreeNode> childlist = selectedNode.getChildNodeList();
//	  moveRow(childlist,leftds,rightds);
  }
  private void moveRow(  List<WebTreeNode> list,  Dataset leftds,  Dataset rightds){
    if(list==null)return;
	  for(WebTreeNode currentnode:list){
		  String currrowid = currentnode.getRowId();
		  Row row = leftds.getRowById(currrowid);
		  if(row==null)
			  break;
		  rightds.addRow(row);
		  leftds.removeRow(row);
		  moveRow(currentnode.getChildNodeList(),leftds,rightds);
	  }
  }
  /** 
 * 增加所有
 * @param mouseEvent
 */
  public void onAllAddBtnClick(MouseEvent mouseEvent){
	  LfwWidget relate = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset leftds = relate.getViewModels().getDataset("ds_selecting_funcres");	  
	  Dataset rightds = relate.getViewModels().getDataset("ds_selected_funcres");
	  TreeViewComp lefttree = (TreeViewComp) relate.getViewComponents().getComponent("selecting_funcres_tree");
	  WebTreeModel model = lefttree.getTreeModel();
	  WebTreeNode rootnode = model.getRootNode();
	  //移动所有子节点
	  List<WebTreeNode> childlist = rootnode.getChildNodeList();
	  moveRow(childlist,leftds,rightds);
  }
  /** 
 * 删除选择
 * @param mouseEvent
 */
  public void onDelBtnClick(MouseEvent mouseEvent){
	  LfwWidget relate = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset leftds = relate.getViewModels().getDataset("ds_selected_funcres");
	  Row[] rows = leftds.getSelectedRows();
	  if(rows==null||rows.length<1)
		  return;
	  Dataset rightds = relate.getViewModels().getDataset("ds_selecting_funcres");
	  TreeViewComp lefttree = (TreeViewComp) relate.getViewComponents().getComponent("selected_funcres_tree");
	  WebTreeModel model = lefttree.getTreeModel();
//	  for(int i=0;i<rows.length;i++){
//		  leftds.removeRow(rows[i]);
//	  }	  
	  for(int i=0;i<rows.length;i++){
		  Row trow = rows[i];
		  WebTreeNode selectedNode = model.getTreeNodeByRowId(trow.getRowId());
		  String currentnodeid = selectedNode.getId();
		  WebTreeNode currentnode = selectedNode;
		  //移动父节点
		  while(currentnode.getParentNode()!=null){
			  String currrowid = currentnode.getRowId();
			  Row row = leftds.getRowById(currrowid);
			  if(row==null)
				  break;
			  Row rrow = rightds.getRowById(row.getRowId());
			  if(rrow==null)
				  rightds.addRow(row);
			  if(currentnodeid.equals(currentnode.getId()))
				  leftds.removeRow(row);
			  currentnode = model.getTreeNodeById(currentnode.getParentNode());
		}
		  //移动子节点
		  List<WebTreeNode> childlist = selectedNode.getChildNodeList();
		  moveRow(childlist,leftds,rightds);
	  }
  }
  /** 
 * 删除所有
 * @param mouseEvent
 */
  public void onAllDelBtnClick(  MouseEvent mouseEvent){
	  LfwWidget relate = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset leftds = relate.getViewModels().getDataset("ds_selected_funcres");	  
	  Dataset rightds = relate.getViewModels().getDataset("ds_selecting_funcres");
	  TreeViewComp lefttree = (TreeViewComp) relate.getViewComponents().getComponent("selected_funcres_tree");
	  WebTreeModel model = lefttree.getTreeModel();
	  WebTreeNode rootnode = model.getRootNode();
	  //移动所有子节点
	  List<WebTreeNode> childlist = rootnode.getChildNodeList();
	  moveRow(childlist,leftds,rightds);
  }
  /** 
 * 确定
 * @param mouseEvent
 */
  public void onOkBtnClick(  MouseEvent mouseEvent){
	  LfwWidget relate = AppLifeCycleContext.current().getWindowContext().getViewContext("relate").getView();
	  Dataset selected_funcres_ds = relate.getViewModels().getDataset("ds_selected_funcres");	
	  LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
	  Dataset resp_func_ds = main.getViewModels().getDataset("ds_resp_func");	
	  Dataset responsibility_ds = main.getViewModels().getDataset("ds_responsibility");	
	  Row row = responsibility_ds.getSelectedRow();
	  String pk_responsibility = row.getString(responsibility_ds.nameToIndex("pk_responsibility"));
	  TreeViewComp tree = (TreeViewComp) relate.getViewComponents().getComponent("selected_funcres_tree");
	  WebTreeModel model = tree.getTreeModel();
	  WebTreeNode rootnode = model.getRootNode();
	  //移动所有子节点
	  List<WebTreeNode> childlist = rootnode.getChildNodeList();
	  List<CpRespFuncVO> volist = new ArrayList<CpRespFuncVO>();
	  parseSelectNode(childlist,volist,selected_funcres_ds,resp_func_ds,pk_responsibility);
	 //保存
	  try {
		NCLocator.getInstance().lookup(ICpResponsibilityBill.class).delAllRespFuncVos(pk_responsibility);
		NCLocator.getInstance().lookup(ICpResponsibilityBill.class).addRespFuncVos(volist.toArray(new CpRespFuncVO[0]));
	} catch (CpbBusinessException e) {
		LfwLogger.error(e.getMessage(), e);
	}
	resp_func_ds.clear();
	new SuperVO2DatasetSerializer().serialize(volist.toArray(new CpRespFuncVO[0]), resp_func_ds, Row.STATE_NORMAL);
	AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("relate");
  }
  private void parseSelectNode(List<WebTreeNode> nodelist,List<CpRespFuncVO> volist,Dataset ds1,Dataset ds2,String pk_responsibility){
	  if(nodelist==null)return;
	  for(WebTreeNode currentnode:nodelist){
		  String currrowid = currentnode.getRowId();
		  Row row = ds1.getRowById(currrowid);
		  String busi_pk = row.getString(ds1.nameToIndex("pk_busifunc"));
		  String name = row.getString(ds1.nameToIndex("name"));
		  if(busi_pk!=null&&!"".equals(busi_pk)){
			  CpRespFuncVO vo = new CpRespFuncVO();
			  vo.setBusi_pk(busi_pk);
			  vo.setCreationtime(new UFDate());
			  vo.setCreator(LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			  vo.setPk_group(LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
			  vo.setPk_responsibility(pk_responsibility);
			  vo.setType(Integer.parseInt(row.getString(ds1.nameToIndex("type"))));
			  vo.setBusi_name(name);
			  volist.add(vo);
		  }
		  parseSelectNode(currentnode.getChildNodeList(),volist,ds1,ds2,pk_responsibility);
	  }
  }
}
