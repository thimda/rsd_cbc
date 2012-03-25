package nc.uap.cpb.template;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.org.DeptVO;
import nc.vo.pub.BusinessException;
/** 
 * @author chouhl
 */
public class MainViewController implements IController {
  private static final long serialVersionUID=1L;
  public void onDataLoad(  DataLoadEvent dataLoadEvent){
	  LfwWidget view = getWidgetById("main");
	  Dataset ds = dataLoadEvent.getSource();
	  String orgPk = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("orgPk");
	  ReferenceComp refcomp = (ReferenceComp) view.getViewComponents().getComponent("text_reforg");
	  refcomp.setValue(orgPk);
	
	  if(orgPk != null){
		 setNavTreeValue(orgPk, ds);
		 refcomp.setEnabled(false);
	  }
//	  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
  }
  public void valueChanged(  TextEvent textEvent) {
    LfwWidget view = getWidgetById("main");
	  ReferenceComp refcomp = (ReferenceComp) view.getViewComponents().getComponent("text_reforg");
	  
	  Dataset ds = view.getViewModels().getDataset("ds_proxytree");
	  setNavTreeValue(refcomp.getValue(), ds);
  }
  private void setNavTreeValue(String orgPk, Dataset ds) {
	  ds.clear();
	  int idIndex = ds.nameToIndex("id");
	  int pIdIndex = ds.nameToIndex("pid");
	  int codeIndex = ds.nameToIndex("code");
	  int nameIndex = ds.nameToIndex("name");
	  
	  Row row = ds.getEmptyRow();
	  row.setValue(idIndex, "00000000000000000001");
	  row.setValue(pIdIndex, null);
	  row.setValue(codeIndex, "role");
	  row.setValue(nameIndex, "角色");
	  ds.addRow(row);
	 
	  row = ds.getEmptyRow();
	  row.setValue(idIndex, "00000000000000000002");
	  row.setValue(pIdIndex, null);
	  row.setValue(codeIndex, "user");
	  row.setValue(nameIndex, "用户");
	  ds.addRow(row);
	  
	  row = ds.getEmptyRow();
	  row.setValue(idIndex, "00000000000000000003");
	  row.setValue(pIdIndex, null);
	  row.setValue(codeIndex, "dept");
	  row.setValue(nameIndex, "部门");
	  ds.addRow(row);
	  
	  if(orgPk == null)
		  return;
	  
	  CpUserVO[] users = null;
	  CpRoleVO[] roles = null;
	  DeptVO[] depts = null;
	  try {
			users = CpbServiceFacility.getCpUserQry().getUserByPkorg(orgPk);
			String where = "pk_org = '" + orgPk + "'";
			roles = CpbServiceFacility.getCpRoleQry().getRoleVos(where);
			depts = CpbServiceFacility.getCpDeptQry().queryAllDeptVOsByOrgID(orgPk);
			
			
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		} catch (BusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
	  
	  if(users == null || users.length == 0){}
	  else{
		  for(int i = 0; i < users.length; i++){
			 CpUserVO user = users[i];
			 row = ds.getEmptyRow();
			 row.setValue(idIndex, user.getPrimaryKey());
			 row.setValue(pIdIndex, "00000000000000000002");
			 row.setValue(codeIndex, user.getUser_code());
			 row.setValue(nameIndex, user.getUser_name());
			 ds.addRow(row);
		 }
	 }
	 
	
	 if(roles == null || roles.length == 0) {}
	 else{
		 for(int i = 0; i < roles.length; i++){
			 CpRoleVO role = roles[i];
			 row = ds.getEmptyRow();
			 row.setValue(idIndex, role.getPrimaryKey());
			 row.setValue(pIdIndex, "00000000000000000001");
			 row.setValue(codeIndex, role.getRolecode());
			 row.setValue(nameIndex, role.getRolename());
			 ds.addRow(row);
		 }
	 }
	 
	 if(depts == null || depts.length == 0){}
	 else{
		 for(int i = 0; i < depts.length; i++){
			 DeptVO dept = depts[i];
			 row = ds.getEmptyRow();
			 row.setValue(idIndex, dept.getPrimaryKey());
			 row.setValue(pIdIndex, "00000000000000000003");
			 row.setValue(codeIndex, dept.getCode());
			 row.setValue(nameIndex, dept.getName());
			 ds.addRow(row);
		 }
	 }
	 
  }
  private LfwWidget getWidgetById(  String viewId){
    LfwWidget view = AppLifeCycleContext.current().getWindowContext().getViewContext(viewId).getView();
		return view;
  }
  public void navToGridEvent(  MouseEvent<?> mouseEvent){
    LfwWidget widget = getWidgetById("main");
	    Dataset navDs = widget.getViewModels().getDataset("ds_proxytree");
	    Dataset gridDs = widget.getViewModels().getDataset("ds_proxygrid");
	    
	    Row[] navRows = navDs.getAllSelectedRows();
	    if(navRows == null || navRows.length == 0)
	    	return;
	    
	   
	    
	    for(int i = 0; i < navRows.length; i++){
	    	Row gridRow = gridDs.getEmptyRow();
	    	Row navRow = navRows[i];
	    	if(navRow == null || navRow.size() == 0)
	    		continue;
	    	setNavRowToGridRow(navRow, gridRow);
	    	
	    	boolean isExit = isExitRow(gridDs, gridRow);
	    	boolean isEmptyRow = isEmptyRow(gridDs, gridRow);
	    	if(isExit || isEmptyRow)
	    		continue;
	    	gridDs.addRow(gridRow);
	    }
  }
  private boolean isEmptyRow(  Dataset ds,  Row gridRow){
    String primaray = (String) gridRow.getValue(ds.nameToIndex("pk_primary"));
	if(primaray == null || primaray.length() == 0)
		return true;
	return false;
  }
  private boolean isExitRow(  Dataset ds,  Row gridRow){
    String primaray = (String) gridRow.getValue(ds.nameToIndex("pk_primary"));  
	if(primaray == null)
		return true;
	if(ds.getRowCount() == 0)
		return false;
	Row[] rows = ds.getCurrentRowData().getRows();
	if(rows == null || rows.length == 0)
		return false;
	for(int i = 0; i < rows.length; i ++){
		Row row = rows[i];
		String pk_primary = (String) row.getValue(ds.nameToIndex("pk_primary"));
		if(pk_primary == null)
			return false;
		if(pk_primary.equals(primaray))
			return true;
	}
	return false;
  }
  public void gridToNavEvent(  MouseEvent<?> mouseEvent){
    LfwWidget widget = getWidgetById("main");
	  Dataset gridDs = widget.getViewModels().getDataset("ds_proxygrid");
	  Row[] gridRows = gridDs.getAllSelectedRows();
	  if(gridRows == null || gridRows.length == 0)
		  return;
	  
	  for(int i = 0; i < gridRows.length; i++)
		  gridDs.removeRow(gridRows[i]);
  }
  public void setNavRowToGridRow(  Row navRow,  Row gridRow){
    LfwWidget widget = getWidgetById("main");
	  Dataset navDs = widget.getViewModels().getDataset("ds_proxytree");
	  int idIndex = navDs.nameToIndex("id");
	  int pIdIndex = navDs.nameToIndex("pid");
	  int codeIndex = navDs.nameToIndex("code");
	  int nameIndex = navDs.nameToIndex("name");
		
	  Dataset gridDs = widget.getViewModels().getDataset("ds_proxygrid");
	  int zPrimaryIndex = gridDs.nameToIndex("pk_primary");
	  int zCodeIndex = gridDs.nameToIndex("code");
	  int zNameIndex = gridDs.nameToIndex("name");
	  int zTypeIndex = gridDs.nameToIndex("type");
	  
	  String pId = (String) navRow.getValue(pIdIndex);
	  
	  if(pId == null)
		  return;
	  gridRow.setValue(zPrimaryIndex, navRow.getValue(idIndex));
	  gridRow.setValue(zCodeIndex, navRow.getValue(codeIndex));
	  gridRow.setValue(zNameIndex, navRow.getValue(nameIndex));
	  if(pId.equals("00000000000000000001")){
		  gridRow.setValue(zTypeIndex, TemplateConstant.ASSIGN_TYPE_ROLE);
	  }
	  else if(pId.equals("00000000000000000002")){
		  gridRow.setValue(zTypeIndex, TemplateConstant.ASSIGN_TYPE_USER);
	  }
	  else if(pId.equals("00000000000000000003")){
		  gridRow.setValue(zTypeIndex, "4");
	  }
  }
  public void onOkEvent(  MouseEvent mouseEvent){
	  AppLifeCycleContext.current().getApplicationContext().closeWinDialog();
  }
  public void onCancelEvent(  MouseEvent mouseEvent){
	  AppLifeCycleContext.current().getApplicationContext().closeWinDialog();
  }
}
