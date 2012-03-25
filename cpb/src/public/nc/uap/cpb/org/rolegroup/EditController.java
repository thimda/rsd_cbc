package nc.uap.cpb.org.rolegroup;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
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
  public void onCancelClick(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog("edit");
  }
  public void onBeforeShow(  DialogEvent dialogEvent){
    String opeStatus = (String) getCurrentWinCtx().getAppAttribute(RoleGroupConstants.OPERATE_STATUS);
	  LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("edit").getView();
	  Dataset ds = edit.getViewModels().getDataset("ds_rolegroup");
	 String type = (String) getCurrentWinCtx().getAppAttribute("type");
	 //为了参照使用
	 LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("rolegroup_type", type);
	  if(RoleGroupConstants.ADD_OPERATE.equals(opeStatus)){		  	
		    String pk_parent = (String) getCurrentWinCtx().getAppAttribute("pk_parent");
		    FormComp form = (FormComp) edit.getViewComponents().getComponent("form_rolegroup001");
	    	FormElement element = form.getElementById("pk_org_name");
		    if(pk_parent==null||"".equals(pk_parent)){		    	
		    	element.setEditable(true);
		    }
		    else element.setEditable(false);
		    String pk_org = (String) getCurrentWinCtx().getAppAttribute("pk_org");
		  	// 新增行并选中
			Row row = ds.getEmptyRow();
			row.setValue(ds.nameToIndex("datecreated"), new UFDateTime());
			row.setValue(ds.nameToIndex("usercreated"), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			row.setValue(ds.nameToIndex("pk_group"), LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
			row.setValue(ds.nameToIndex("pk_parent"), pk_parent);
			row.setValue(ds.nameToIndex("pk_org"), pk_org);
			row.setValue(ds.nameToIndex("type"), type);
			ds.addRow(row);
			ds.setSelectedIndex(ds.getRowIndex(row));
			ds.setEnabled(true);
	  }
	  else if(RoleGroupConstants.EDIT_OPERATE.equals(opeStatus)){
		    Row oldrow = (Row)getCurrentWinCtx().getAppAttribute(RoleGroupConstants.DATA);
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
			//为了参照使用
			 LfwRuntimeEnvironment.getWebContext().getAppSession().setAttribute("pk_current_rolegroup", 
					 row.getString(ds.nameToIndex("pk_rolegroup")));
	  }
  }
}
