package nc.uap.cpb.org.responsibility;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.pub.lang.UFDateTime;
/** 
 */
public class EditController implements IController {
  private static final long serialVersionUID=1L;
  
  private WindowContext getCurrentWinCtx(){
	  return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onBeforeShow(  DialogEvent dialogEvent){
	  String opeStatus = (String) getCurrentWinCtx().getAppAttribute(RespConstant.OPERATE_STATUS);
	  LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("edit").getView();
	  Dataset ds = edit.getViewModels().getDataset("ds_responsibility");
	  if(RespConstant.ADD_OPERATE.equals(opeStatus)){		  	
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			row.setValue(ds.nameToIndex("creationtime"), new UFDateTime());
			row.setValue(ds.nameToIndex("creator"), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			row.setValue(ds.nameToIndex("pk_group"), LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
			row.setValue(ds.nameToIndex("pk_org"), LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
			String type = (String) AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getAppAttribute("type");
			if(type != null && type.equals("1"))
				type = "0";
			else
				type = "1";
			row.setValue(ds.nameToIndex("type"), type);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
	  else if(RespConstant.EDIT_OPERATE.equals(opeStatus)){
		    Row oldrow = (Row)getCurrentWinCtx().getAppAttribute(RespConstant.DATA);
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			Field[] fields = ds.getFieldSet().getFields();
			  for(Field field:fields){
				  int index = ds.nameToIndex(field.getField());
				  row.setValue(index, oldrow.getValue(index));
			  }
			row.setValue(ds.nameToIndex("modifiedtime"), new UFDateTime());
			row.setValue(ds.nameToIndex("modifier"), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
  }
  public void onCancelClick(  MouseEvent<?> mouseEvent){
	  AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("edit");
  }
}
