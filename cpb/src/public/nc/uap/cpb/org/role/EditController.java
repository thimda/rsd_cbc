package nc.uap.cpb.org.role;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.ctx.WindowContext;
/** 
 */
public class EditController implements IController {
  private static final long serialVersionUID=1L;
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onCancelClick(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("edit");
  }
  public void onBeforeShow(  DialogEvent dialogEvent){
    String opeStatus = (String) getCurrentWinCtx().getAppAttribute(RoleMgrConstants.OPERATE_STATUS);
	  LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("edit").getView();
	  Dataset ds = edit.getViewModels().getDataset("cp_role");
	  String type = (String) getCurrentWinCtx().getAppAttribute("type");
	  LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("rolegroup_type", type);
	  if(RoleMgrConstants.ADD_OPERATE.equals(opeStatus)){		  	
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			row.setValue(ds.nameToIndex("datecreated"), new UFDateTime());
			row.setValue(ds.nameToIndex("usercreated"), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			row.setValue(ds.nameToIndex("type"), type);
			row.setValue(ds.nameToIndex("issealed"), "0");
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
	  else if(RoleMgrConstants.EDIT_OPERATE.equals(opeStatus)){
		    Row oldrow = (Row)getCurrentWinCtx().getAppAttribute(RoleMgrConstants.DATA);
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			Field[] fields = ds.getFieldSet().getFields();
			  for(Field field:fields){
				  int index = ds.nameToIndex(field.getField());
				  if(index<0)
					  continue;
				  row.setValue(index, oldrow.getValue(index));
			  }
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
  }
}
