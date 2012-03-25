package nc.uap.cpb.org.responsibility;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpResponsibilityVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifCancelCmd;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
public class MainController<T extends WebElement> extends AbstractWidgetController implements IController {
  private static final long serialVersionUID=8990769129712284556L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onAdd(  MouseEvent<T> mouseEvent){
	  	String type = (String) getCurrentWinCtx().getAppAttribute("type");
		if (type == null||"".equals(type))
			throw new LfwRuntimeException("请先职责类型!");
		getCurrentWinCtx().addAppAttribute(RespConstant.OPERATE_STATUS, RespConstant.ADD_OPERATE);
		getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"新增职责");
  }
  public void onEdit(  MouseEvent<T> mouseEvent){
      getCurrentWinCtx().addAppAttribute(RespConstant.OPERATE_STATUS, RespConstant.EDIT_OPERATE);
      Dataset ds = getCurrentWinCtx().getViewContext("main").getView().getViewModels().getDataset("ds_responsibility");
      Row row = ds.getSelectedRow();
      if(row==null)
    	  throw new LfwRuntimeException("请选择需要修改的数据！");
      getCurrentWinCtx().addAppAttribute(RespConstant.DATA, row);
	  getCurrentWinCtx().popView("edit",DialogConstant.FIVE_ELE_WIDTH, DialogConstant.FIVE_ELE_HEIGHT ,"修改职责");
  }
  public void onDel(  MouseEvent<T> mouseEvent){
    UifDelCmd cmd=new UifDelCmd(getMasterDsId(),getAggVoClazz());
	  cmd.execute();
  }
  public void onSave(  MouseEvent<T> mouseEvent){
    UifSaveCmd cmd=new UifSaveCmd(getMasterDsId(),getDetailDsIds(),getAggVoClazz(),bodyNotNull());
		cmd.execute();
  }
  public void onCancel(  MouseEvent<T> mouseEvent){
    UifCancelCmd cmd=new UifCancelCmd(getMasterDsId());
	    cmd.execute();
  }
  public void onRelateFunc(  MouseEvent<T> mouseEvent){
      Dataset ds = getCurrentWinCtx().getViewContext("main").getView().getViewModels().getDataset("ds_responsibility");
      Row row = ds.getSelectedRow();
      if(row==null)
    	  throw new LfwRuntimeException("请选择需要关联功能节点的职责！");
	  getCurrentWinCtx().popView("relate",DialogConstant.MAX_WIDTH, DialogConstant.MAX_HEIGHT ,"功能分配");
  }
  public void onDataLoad_ds_responsibility(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
	CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void pluginafteradd(  Map keys){
    LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
	   Dataset ds = edit.getViewModels().getDataset("ds_responsibility");
	   String opeStatus = (String) getCurrentWinCtx().getAppAttribute(RespConstant.OPERATE_STATUS);
	   Row row = null;
	   if(RespConstant.ADD_OPERATE.equals(opeStatus)){	
		   // 新增行并选中
			row = ds.getEmptyRow();
			setValues(row,ds,keys);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
	   }
	   else if(RespConstant.EDIT_OPERATE.equals(opeStatus)){
		    row = ds.getSelectedRow();
			setValues(row,ds,keys);
	   }
		UifSaveCmd cmd = new UifSaveCmd(getMasterDsId(),getDetailDsIds(),getAggVoClazz(),false){			
			protected void onVoSave(AggregatedValueObject agggo) {
				onVoSaves(new SuperVO[]{(SuperVO)agggo.getParentVO()});
			}
		};
		cmd.execute();
	   AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("edit");
  }
  private void setValues(  Row row,  Dataset ds,  Map map){
    TranslatedRow r = (TranslatedRow)map.get("row");
	  String[] keys = r.getKeys();
	  
	  for(String key:keys){
		  row.setValue(ds.nameToIndex(key), r.getValue(key));
	  }
  }
  private void onVoSaves(  SuperVO[] vos){
    if (vos == null || vos.length == 0) {
			return;
		}
		try {
			CpResponsibilityVO vo = (CpResponsibilityVO) vos[0];
			checkDupliVO(vo);
			if (vo.getPk_responsibility() == null || vo.getPk_responsibility().length() == 0) {
				CpbServiceFacility.getCpResponsibilityBill().add(new CpResponsibilityVO[]{vo});
			} else {
				CpbServiceFacility.getCpResponsibilityBill().update(new CpResponsibilityVO[]{vo});
			}
		} catch (CpbBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
  }
  /** 
 * 保存，更新前检查职责编码，名称是否重复
 * @param vo
 */
  private void checkDupliVO(  SuperVO vo){
    CpResponsibilityVO headvo = (CpResponsibilityVO)vo;
		String opeStatus = (String) getCurrentWinCtx().getAppAttribute(RespConstant.OPERATE_STATUS);
		String code = headvo.getCode();
		String name = headvo.getName();
		String pk_group = headvo.getPk_group();
		String where = " pk_group ='"+pk_group+"' and (code='"+code+"' or name='"+name+"')";
		try {
			CpResponsibilityVO[] respvos = CpbServiceFacility.getCpResponsibilityQry().getResponsibilityVos(where);
			if (respvos == null || respvos.length<1)
				return;
			if (RespConstant.ADD_OPERATE.equals(opeStatus) || (RespConstant.EDIT_OPERATE.equals(opeStatus) && !respvos[0].getPk_responsibility().equals(headvo.getPk_responsibility())))
				throw new LfwRuntimeException("职责编码/职责名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
  }
  @Override public String getMasterDsId(){
    return "ds_responsibility";
  }
  public void onAfterRowSelect_ds_responsibility(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
	public void pluginmanage_type_plugin(Map keys){
		  LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
		  Dataset ds = main.getViewModels().getDataset("ds_responsibility");
		  TranslatedRow r = (TranslatedRow) keys.get("manage_type_row");
		  final String type = (String)r.getValue("value");
		  getCurrentWinCtx().addAppAttribute("type",type);
		  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
			  protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				    String where ="";
				  	if(type != null && type.equals("1")){
				  		where =" type='0'";
				  	}
				  	else if(type != null && type.equals("0")){
				  		where =" type='1'";
				  	}
					
					ds.setLastCondition(where);
					return where ;			  
				}
			});
	  }
	/**
	 * 简单查询
	 * @param keys
	 */
	 public void pluginsimpleQuery_plugin(Map<Object,Object> keys) {
		 FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
	     String wheresql = whereSql.getWhere();
	     QueryCmd cmd = new QueryCmd("main", getMasterDsId(), wheresql){
	    	 protected String buildWhere(String whereSql){
	    		 String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
	    		 return whereSql +" and pk_group = '" + pk_group + "'";
	    	 }
	     };
	     cmd.excute();
		 }
	 /**
	  * 查询计划
	  * @param keys
	  */
     public void pluginqueryPlan_plugin(Map keys) {
		 
	 }
}
