package nc.uap.cpb.org.pubview;
import nc.itf.org.IOrgConst;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.org.OrgVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
/** 
 */
public class RelateRoleController implements IController {
  private static final long serialVersionUID=1L;
  public static final String PUBLIC_VIEW_ROLE="role";
  public static final String ORG_TEXT="org_text";
  
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  public void onCancelClick(  MouseEvent<?> mouseEvent){
    AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeViewDialog(PUBLIC_VIEW_ROLE);
  }
  public void onBeforeShow(  DialogEvent dialogEvent){
		  LfwWidget role = AppLifeCycleContext.current().getWindowContext().getViewContext("role").getView();		  
		  ReferenceComp org_text = (ReferenceComp) role.getViewComponents().getComponent("org_text");
		  String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		  OrgVO[] orgvos = null;
		try {
			orgvos = CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDAndOrgTypes(pk_group, new String[]{IOrgConst.BUSINESSUNITORGTYPE,
					IOrgConst.GROUPORGTYPE}, true);
		} catch (BusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		  if(orgvos==null||orgvos.length<1)
			  return;
		  org_text.setValue(orgvos[0].getPk_org());
		  org_text.setShowValue(orgvos[0].getName());		  
  }
  public void onDataLoad_org(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
			CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){
				protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
					SuperVO[] vos = CpbServiceFacility.getCpOrgRefefenceQry().getReferenceOrgs();
					return vos;
				}
			});
  }
  public void onAfterRowSelect_rolegroup(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
  public void onOrgvalueChanged(  TextEvent textEvent){
	  LfwWidget role = AppLifeCycleContext.current().getWindowContext().getViewContext(PUBLIC_VIEW_ROLE).getView();	
	  Dataset ds = role.getViewModels().getDataset("ds_rolegroup");
	  ReferenceComp org_text = (ReferenceComp) role.getViewComponents().getComponent("org_text");
	  String pk_org = org_text.getValue();
	  CpRoleGroupVO[] vos = null;
	try {
		vos = CpbServiceFacility.getCpRoleGroupQry().getRoleGroupByPkorgs(new String[]{pk_org});
	} catch (CpbBusinessException e) {
		LfwLogger.error(e.getMessage(), e);
	}
	  new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
	  if(ds.getCurrentRowCount() > 0){
			ds.setSelectedIndex(0);
		}
  }
}
