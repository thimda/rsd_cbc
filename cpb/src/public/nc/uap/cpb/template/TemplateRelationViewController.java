package nc.uap.cpb.template;
import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.templaterela.itf.ITemplateRelationQryService;
import nc.uap.cpb.templaterela.itf.ITemplateRelationService;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.TabEvent;
import nc.uap.lfw.core.model.plug.TranslatedRows;
import nc.uap.lfw.core.page.LfwWidget;
/** 
 * @author chouhl
 */
public class TemplateRelationViewController extends AbstractWidgetController implements IController {
  private static final long serialVersionUID=1L;
  public void onTemplateUserDataLoad(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
    String pk_template = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(TemplateConstant.PK_TEMPLATE);
    if(pk_template != null){
    	ds.setLastCondition("pk_template = '" + pk_template + "'");
    	CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
    }
  }
  public void onTemplateRoleDataLoad(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
    String pk_template = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(TemplateConstant.PK_TEMPLATE);
    if(pk_template != null){
    	ds.setLastCondition("pk_template = '" + pk_template + "'");
    	CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
    }
  }
  public void onTemplateOrgDataLoad(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
    String pk_template = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(TemplateConstant.PK_TEMPLATE);
    if(pk_template != null){
    	ds.setLastCondition("pk_template = '" + pk_template + "'");
    	CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
    }
  }
  public void pluginassign_plugin(  Map<Object, Object> keys){
    String pk_template = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(TemplateConstant.PK_TEMPLATE);
    Integer templateType =  (Integer) AppLifeCycleContext.current().getApplicationContext().getAppAttribute(TemplateConstant.TEMPLATE_TYPE);
	  TranslatedRows trs = (TranslatedRows) keys.get("assignRow");
	  String[] rowKeys = trs.getKeys();
	  int count = trs.getValue("type").size();
	  
	  if(count == 0)
		  return;
	  
	  for(int k = 0; k < count; k ++){
		  Map<String, String> valueMap = new HashMap<String, String>();
		  for(int i = 0; i < rowKeys.length; i ++){
			  String key = rowKeys[i];
			  String value = (String) trs.getValue(key).get(k);
			  valueMap.put(key, value);
		  }
		  
		  if(valueMap == null)
			  continue;
		  
		  String type = valueMap.get("type");
		  
		  ITemplateRelationQryService qryService = NCLocator.getInstance().lookup(ITemplateRelationQryService.class);
		  ITemplateRelationService service = NCLocator.getInstance().lookup(ITemplateRelationService.class);
		  
		  if(TemplateConstant.ASSIGN_TYPE_ORG.equals(type)){
			  String sqlWhere = "pk_template ='" + pk_template +"' and pk_org = '" + valueMap.get("pk_primary") + "'";
			  CpTemplateOrgVO[] vos = qryService.getTemplateOrgVOsByCondition(sqlWhere);
			  if(vos == null || vos.length == 0){
				  CpTemplateOrgVO vo = new CpTemplateOrgVO();
				  vo.setPk_org(valueMap.get("pk_primary"));
				  vo.setPk_template(pk_template);
				  if(templateType != null)
					  vo.setTemplatetype(templateType);
				  service.createTemplateOrgVO(vo);
			  }
		  }
		  else if(TemplateConstant.ASSIGN_TYPE_ROLE.equals(type)){
			  
			  String sqlWhere = "pk_template ='" + pk_template + "' and pk_role = '" + valueMap.get("pk_primary") + "'";
			  CpTemplateRoleVO[] vos = qryService.getTemplateRoleVOsByCondition(sqlWhere);
			  if(vos == null || vos.length == 0){
				  CpTemplateRoleVO vo = new CpTemplateRoleVO();
				  vo.setPk_template(pk_template);
				  vo.setPk_role(valueMap.get("pk_primary"));
				  if(templateType != null)
					  vo.setTemplatetype(templateType);
				  service.createTemplateRoleVO(vo);
			  }
		  }
		  else if(TemplateConstant.ASSIGN_TYPE_USER.equals(type)){
			  
			  String sqlWhere = "pk_template ='" + pk_template + "' and pk_user = '" + valueMap.get("pk_primary") + "'";
			  CpTemplateUserVO[] vos = qryService.getTemplateUserVOsByCondition(sqlWhere);
			  if(vos == null || vos.length == 0){
				  CpTemplateUserVO vo = new CpTemplateUserVO();
				  vo.setPk_template(pk_template);
				  vo.setPk_user(valueMap.get("pk_primary"));
				  if(templateType != null)
					  vo.setTemplatetype(templateType);
				  service.createTemplateUserVO(vo);
			  }
		  }
		  
	  }
  }

  public void afterActivedTabItemChange(  TabEvent tabEvent){
	  LfwWidget widget = getCurrentWidget();
	  Dataset templateUserDs = widget.getViewModels().getDataset("ds_templateuser");
	  Dataset templateRoleDs = widget.getViewModels().getDataset("ds_templaterole"); 
	  Dataset templateOrgDs = widget.getViewModels().getDataset("ds_templateorg");
	  
	  String pk_template = (String) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("pk_template");
//	  System.out.println("--------------:" +pk_template);
	  
	  if(pk_template == null)
		  return;
	  
	  String currentIndex = tabEvent.getSource().getCurrentItem();
	  if(currentIndex.equals("0")){
		  templateOrgDs.setLastCondition("pk_template='" + pk_template + "'");
		  CmdInvoker.invoke(new UifDatasetLoadCmd(templateOrgDs.getId()));
	  }
	  else if(currentIndex.equals("1")){
		  templateRoleDs.setLastCondition("pk_template='" + pk_template + "'");
		  CmdInvoker.invoke(new UifDatasetLoadCmd(templateRoleDs.getId()));
	  }
	  else if(currentIndex.equals("2")){
		  templateUserDs.setLastCondition("pk_template='" + pk_template + "'");
		  CmdInvoker.invoke(new UifDatasetLoadCmd(templateUserDs.getId()));
	  }
	  
  }
  
  	private LfwWidget getCurrentWidget() {
		LfwWidget widget = AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getViewContext("template_relation").getView();
		return widget;
	}
	  @Override 
	  public String getMasterDsId(){
	    return null;
	  }
}
