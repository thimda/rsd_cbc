package nc.uap.cpb.org.funcregister;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.page.LfwWidget;
/** 
 */
public class EditNodeController implements IController {
  private static final long serialVersionUID=1L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onCancelClick(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("edit_node");
  }
  public void onBeforeShow(  DialogEvent dialogEvent){
    String opeStatus = (String) getCurrentWinCtx().getAppAttribute(FuncregisterConstants.OPERATE_STATUS);
	  LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("edit_node").getView();
	  Dataset ds = edit.getViewModels().getDataset("ds_appsnode");
	  if(FuncregisterConstants.ADD_OPERATE.equals(opeStatus)){		  	
		  	// 新增行并选中
			Row row = ds.getEmptyRow();		
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
	  else if(FuncregisterConstants.EDIT_OPERATE.equals(opeStatus)){
		    Row oldrow = (Row)getCurrentWinCtx().getAppAttribute(FuncregisterConstants.DATA);
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			Field[] fields = ds.getFieldSet().getFields();
			  for(Field field:fields){
				  int index = ds.nameToIndex(field.getField());
				  if(index<0)continue;
				  row.setValue(index, oldrow.getValue(index));
			  }
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
  }
}
