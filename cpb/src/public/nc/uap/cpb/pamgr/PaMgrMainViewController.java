package nc.uap.cpb.pamgr;
import nc.uap.cpb.org.constant.DialogConstant;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.stylemgr.vo.UwIncrementVO;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import java.io.Serializable;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import java.util.List;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.cmd.UifDsLoadRowEnabledCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.model.util.DefaultUIMetaBuilder;
import nc.uap.lfw.pa.info.InfoCategory;
import nc.uap.lfw.pa.PaBusinessException;
import nc.uap.lfw.stylemgr.vo.UwTemplateVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.cmd.base.AbstractWidgetController;
import nc.uap.lfw.jsp.uimeta.UIElementFinder;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.pa.info.IPropertyInfo;
import nc.uap.lfw.core.model.util.DefaultPageMetaBuilder;
import java.util.Map;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.FormComp;
import java.lang.reflect.Method;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import java.util.HashMap;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.stylemgr.itf.IUwTemplateService;
import java.util.Collection;
import nc.uap.lfw.pa.info.BaseInfo;
import nc.uap.lfw.core.comp.GridColumn;
import nc.uap.lfw.core.page.RequestLifeCycleContext;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.page.LifeCyclePhase;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.stylemgr.vo.UwViewVO;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.cmd.UifEditCmd;
import nc.uap.lfw.core.event.MouseEvent;
/** 
 * @author wupeng1
 */
public class PaMgrMainViewController extends AbstractWidgetController implements IController {
  private static final String EDIT_OPERATE="EDIT_OPERATE";
  private static final String ADD_OPERATE="ADD_OPERATE";
  private static final String OPERATE_STATUS="OPERATE_STATUS";
  private static final long serialVersionUID=1L;
  public void addEvent(  MouseEvent<?> mouseEvent){
    Row navRow = (Row) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("navRow");
		if(navRow == null)
			throw new LfwRuntimeException("请选择正确的导航！");
		UifEditCmd cmd = new UifEditCmd("editView", DialogConstant.TEN_ELE_WIDTH, DialogConstant.TEN_ELE_WIDTH, "新建模板");
  		getCurrentWinCtx().addAppAttribute(OPERATE_STATUS,
				ADD_OPERATE);
		cmd.execute();
  }
  public void editEvent(  MouseEvent<?> mouseEvent){
    UifEditCmd cmd = new UifEditCmd("editView", "720", "600", "编辑模板");
  		getCurrentWinCtx().addAppAttribute(OPERATE_STATUS,
				EDIT_OPERATE);
  		
  		Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
		.getViewModels().getDataset(this.getMasterDsId());
  		Row row = ds.getSelectedRow();
  		if(row == null)
  			throw new LfwRuntimeException("请选择要编辑的数据！");
  		getCurrentWinCtx().addAppAttribute("editRow", row);
  		
		cmd.execute();
  }
  public void deleteEvent(  MouseEvent<?> mouseEvent){
    LfwWidget widget = getCurrentWinCtx().getWindow().getWidget("main");
	  	Dataset ds = widget.getViewModels().getDataset(getMasterDsId());
	  	Row row = ds.getSelectedRow();
	  	if(row == null)
	  		throw new LfwRuntimeException("请选择要删除的数据");
	  	
	  	String pk_template = (String) row.getValue(ds.nameToIndex("pk_template"));
	  	
	  	
		IUwTemplateService service = NCLocator.getInstance().lookup(IUwTemplateService.class);
		try {
			String condition = "pk_template = '" + pk_template + "'";
			UwTemplateVO template = service.getTemplateVOByPK(pk_template);
			if(template != null)
				CRUDHelper.getCRUDService().deleteVo(template);
			List<UwViewVO> views = service.getViewVOsByCondition(condition);
			if(views != null && views.size() > 0)
				CRUDHelper.getCRUDService().deleteVos(views.toArray(new UwViewVO[0]));
			List<UwIncrementVO> incres = service.getUwIncrementVOsByCondition(condition);
			if(incres != null && incres.size() > 0)
				CRUDHelper.getCRUDService().deleteVos(incres.toArray(new UwIncrementVO[0]));
			
		} catch (Exception  e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("删除数据出错" + e.getMessage());
		}
		
		ds.removeRow(row);
  }
  public void paSetEvent(  MouseEvent<?> mouseEvent){
    LfwWidget mwidget = getCurrentWinCtx().getWindow().getWidget("main");
		
		Dataset ds = mwidget.getViewModels().getDataset(getMasterDsId());
		
		Row row = ds.getSelectedRow();
		
		if(row == null){
			throw new LfwRuntimeException("请选择模板");
		}
		String appId = row.getString(ds.nameToIndex("appid"));
		String winId = row.getString(ds.nameToIndex("windowid"));
		String pk_template = row.getString(ds.nameToIndex("pk_template"));
		
		String url = null;
		if(winId == null)
			return;
		else{
			if(pk_template==null)
				throw new LfwRuntimeException("模板主键为空！");
			url = "app/mockapp/pa&model=nc.uap.lfw.pa.PaEditorPageModel&from=1&appId=" + appId + "&winId=" + winId + "&pk_template=" + pk_template;
		}
		AppLifeCycleContext.current().getApplicationContext().popOuterWindow(url, "个性化设置", "1280", "1024");
  }
  public void upgradeEvent(  MouseEvent<?> mouseEvent){
    //	  	System.out.println(mouseEvent.getSource());
		
		LfwWidget mwidget = getCurrentWinCtx().getWindow().getWidget("main");
		
		Dataset ds = mwidget.getViewModels().getDataset(getMasterDsId());
		
		Row row = ds.getSelectedRow();
		
		if(row == null){
			throw new LfwRuntimeException("请选择模板");
		}
//		String appId = row.getString(ds.nameToIndex("appid"));
		String winId = row.getString(ds.nameToIndex("windowid"));
		String pk_template = row.getString(ds.nameToIndex("pk_template"));
		
//		System.out.println("模板PK： " + pk_template);
//		
//		System.out.println("APP的ID：" + appId);
//		System.out.println("WIN的ID：" + winId);
		IUwTemplateService service = NCLocator.getInstance().lookup(IUwTemplateService.class);
		
		
		List<UwIncrementVO> increVOs = null;
		try {
			
			String condition = "pk_template = '" + pk_template + "'";
			increVOs = service.getUwIncrementVOsByCondition(condition);
		} catch (PaBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage(), e1);
		}
//		System.out.println(increVOs.size());
		
		LfwRuntimeEnvironment.getWebContext().getRequest().setAttribute(WebConstant.PERSONAL_PAGE_ID_KEY, winId);
		PageMeta pageMeta = this.getPageMetaById(winId);
		UIMeta uimeta = this.getUIMetaByWinId(winId);
		
		for(int i = 0; i< increVOs.size(); i++){
			
			UwIncrementVO increVO = increVOs.get(i);
			
			String id = increVO.getString_ext1();
			String widgetId = increVO.getString_ext2();
			String compType = increVO.getComptype();
			
//			UIElement uiEle = uimeta.findElementById(uimeta, id);
			UIElement uiEle = UIElementFinder.findElementById(uimeta, id);
			WebComponent ele = pageMeta.getWidget(widgetId).getViewComponents().getComponent(id);
			BaseInfo pbi = InfoCategory.getInfo(compType);
			IPropertyInfo[] ipi = pbi.getPropertyInfos();
			
			if(ele != null){
				
				for(int j = 0; j < ipi.length; j++){
					IPropertyInfo pinfo = ipi[j];
					if(pinfo.getId() != ""){
						
						try{
							setProperty(ele, pinfo.getId(), increVO.getAttributeValue(pinfo.getDsField()));
						}
						catch(Exception ex){
							if(uiEle != null){
								try{
									uiEle.setAttribute(pinfo.getId(), (Serializable) increVO.getAttributeValue(pinfo.getDsField()));
									
								}
								catch(Exception e1){
									LfwLogger.error("从UIElement:" + uiEle.getClass().getName() + "中获取:" + pinfo.getId() + "出错");
								}
								
							}
							else{
								LfwLogger.error("从WebElement:" + ele.getClass().getName() + "中获取:" + pinfo.getId() + "出错");
							}
						}
						
					}
				}
			}
			else{
				if(uiEle != null){
					for(int j = 0; j < ipi.length; j++){
						IPropertyInfo pinfo = ipi[j];
						try{
							uiEle.setAttribute(pinfo.getId(), (Serializable) increVO.getAttributeValue(pinfo.getDsField()));
						}
						catch(Exception ex){
							LfwLogger.error("从UIElement:" + uiEle.getClass().getName() + "中获取:" + pinfo.getId() + "出错");
						}
					}
				}
				else{
					
					String parentid = increVOs.get(i).getParentid();
//					UIElement uiPaEle = uimeta.findElementById(uimeta, parentid);
					if(LfwPageContext.SOURCE_TYPE_GRID_HEADER.equals(compType)){
						GridComp grid = (GridComp) pageMeta.getWidget(widgetId).getViewComponents().getComponent(parentid);
						GridColumn column = (GridColumn) grid.getColumnById(id);
						
						for(int j = 0; j < ipi.length; j++){
							IPropertyInfo pinfo = ipi[j];
//							Object value = null;
							if(pinfo.getId() != ""){
								try {
									setProperty(column, pinfo.getId(), increVO.getAttributeValue(pinfo.getDsField()));
								}catch(Exception ex){
									
									LfwLogger.error("设置GridColumn出错");
//									throw new LfwRuntimeException(ex.getMessage(), ex);
								}
							}
						}
//						System.out.println(column.getText());
					}
					if(LfwPageContext.SOURCE_TYPE_FORMELE.equals(compType)){
						FormComp form = (FormComp) pageMeta.getWidget(widgetId).getViewComponents().getComponent(parentid);
						FormElement formEle = form.getElementById(id);
						
						for(int j = 0; j < ipi.length; j++){
							IPropertyInfo pinfo = ipi[j];
//							Object value = null;
							if(pinfo.getId() != ""){
								try {
									setProperty(formEle, pinfo.getId(), increVO.getAttributeValue(pinfo.getDsField()));
								}catch(Exception ex){
									LfwLogger.error("设置formElement出错");
								}
							}
						}
					}
				}
			}
		}
//		System.out.println("设置结束");
		
		
//		super.onclick(e);
  }
  public static final void setProperty(  Object bean,  String propertyName,  Object value) throws Exception {
    Method m = null;
		String upCaseName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		try {
			m = bean.getClass().getMethod("set" + upCaseName, String.class);
			if(m != null){
				if(value == null){
					m.invoke(bean, null);
				}
				else
					m.invoke(bean, value);
			}
		} catch (SecurityException e) {
			LfwLogger.error(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			try {
				m = bean.getClass().getMethod("set" + upCaseName, boolean.class);
				if(m != null){
					if(value == null){
						m.invoke(bean, null);
					}
					else
						m.invoke(bean, value);
				}
			} catch (SecurityException e1) {
				LfwLogger.error(e1.getMessage(),e1);
			} 
		}
		
//		if( m != null){
//			m.invoke(m, value);
//		}
  }
  private WindowContext getCurrentWinCtx(){
    return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
  }
  private UIMeta getUIMetaByWinId(  String winId){
    PageMeta pageMeta = this.getPageMetaById(winId);
			DefaultUIMetaBuilder uiMetaBuilder = new DefaultUIMetaBuilder();
			UIMeta meta = uiMetaBuilder.buildUIMeta(pageMeta);
			return meta;
  }
  private PageMeta getPageMetaById(  String winId){
    PageMeta pm = null;
			DefaultPageMetaBuilder dpb = new DefaultPageMetaBuilder();
			Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(WebConstant.PAGE_ID_KEY, winId);	
		RequestLifeCycleContext.get().setPhase(LifeCyclePhase.nullstatus);
		pm = dpb.buildPageMeta(paramMap);
		RequestLifeCycleContext.get().setPhase(LifeCyclePhase.ajax);
		return pm;
  }
  @Override public String getMasterDsId(){
    return "ds_template";
  }
  public void onDataLoad(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		UifDsLoadRowEnabledCmd dsCmd = new UifDsLoadRowEnabledCmd(this.getMasterDsId(), null);
  		dsCmd.execute();
  		
		 CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
		 ButtonStateManager.updateButtons();
  }
  public void onAfterRowSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
    Row row = ds.getSelectedRow();
    String pk_template = (String) row.getValue(ds.nameToIndex("pk_template"));
    AppLifeCycleContext.current().getApplicationContext().addAppAttribute("pk_template", pk_template);
    
		  CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
  }
  public void pluginnav_plugin(  Map keys){
    LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
	    Dataset ds = main.getViewModels().getDataset(this.getMasterDsId());	
//	    System.out.println(keys.size());
	    TranslatedRow r = (TranslatedRow) keys.get("navRow");
	    String pk_funcnode = (String) r.getValue("pk_funcnode");
	    String winId = (String) r.getValue("windowId");
	    String appId = (String) r.getValue("appId");
	    
	    if(pk_funcnode != null)
			pk_funcnode = pk_funcnode.substring(0,20);
		
		IUwTemplateService service = NCLocator.getInstance().lookup(IUwTemplateService.class);
	
		if(pk_funcnode == null){
			return;
		}

		String con0 = null;
		if(appId == null){
			con0 = " and appid IS NULL ";
		}
		else{
			con0 = " and appid = '" + appId + "' ";
		}
		
		String con1 = null;
		if(winId == null){
			con1 = " and windowid IS NULL ";
		}
		else{
			con1 = " and windowid = '" + winId + "' "; 
		}

		String conditon = "pk_funcnode = '" + pk_funcnode + "' " + con0 + con1;
		Collection<UwTemplateVO> tvos;
		try {
			tvos = service.getTemplateVOByCondition(conditon);
			new SuperVO2DatasetSerializer().serialize(tvos.toArray(new SuperVO[0]), ds, Row.STATE_NORMAL);
		} catch (PaBusinessException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1);
		}
  }
  public void pluginedit_plugin(  Map keys){
    LfwWidget widget = getCurrentWinCtx().getWindow().getWidget("main");
	  Dataset ds = widget.getViewModels().getDataset(this.getMasterDsId());
	  String operStatus = (String) getCurrentWinCtx().getAppAttribute(OPERATE_STATUS);
	  if(ADD_OPERATE.equals(operStatus)){
		  Row row = ds.getEmptyRow();
		  setRowValue(row, ds, keys);
		  ds.addRow(row);
		  ds.setSelectedIndex(ds.getRowIndex(row));
	  }
	  else if(EDIT_OPERATE.equals(operStatus)){
		  Row row = ds.getSelectedRow();
		  setRowValue(row, ds, keys);
	  }
  }
  private Row setRowValue(  Row row,  Dataset ds,  Map map){
    TranslatedRow r =  (TranslatedRow)map.get("selectRow");
    if(r == null)
    	return null;
	  String[] keys = r.getKeys();
	  for (String key : keys) {
		row.setValue(ds.nameToIndex(key), r.getValue(key));
	  }
	  return row;
  }
  public void beforeShowEvent(  DialogEvent dialogEvent){
    
  }
  public void onTemplateAssignEvent(  MouseEvent<?> mouseEvent){
	  Dataset ds = getCurrentWinCtx().getViewContext("main").getView()
		.getViewModels().getDataset(this.getMasterDsId());
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("请选择要编辑的数据！");
    AppLifeCycleContext.current().getApplicationContext().navgateTo("cp_templateassign", "模板分配", "800", "600");
  }
  public void onAfterRowUnSelect(  DatasetEvent datasetEvent){
	  ButtonStateManager.updateButtons();
  }
}
