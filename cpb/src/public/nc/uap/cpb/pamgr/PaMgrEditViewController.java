package nc.uap.cpb.pamgr;
import java.util.Map;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.persistence.PageMetaToXml;
import nc.uap.lfw.jsp.uimeta.UIElementFinder;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import java.util.HashMap;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.stylemgr.itf.IUwTemplateService;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.page.RequestLifeCycleContext;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.page.LifeCyclePhase;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.model.util.DefaultUIMetaBuilder;
import nc.uap.lfw.core.serializer.IPageElementSerializer;
import nc.uap.lfw.pa.PaBusinessException;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.stylemgr.vo.UwTemplateVO;
import nc.uap.lfw.stylemgr.vo.UwViewVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.model.util.DefaultPageMetaBuilder;
import nc.uap.lfw.core.event.MouseEvent;
import nc.vo.pub.lang.UFBoolean;
/** 
 * @author chouhl
 */
public class PaMgrEditViewController implements IController {
	
	private static final long serialVersionUID=1L;
  	public void onOkEvent(  MouseEvent<?> mouseEvent){
  		LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("editView").getView();
		Dataset ds = edit.getViewModels().getDataset("ds_template");
  		Row row = ds.getSelectedRow();
  		
  		String pk_template = (String) row.getValue(ds.nameToIndex("pk_template"));
  		String windowId = (String) row.getValue(ds.nameToIndex("windowid"));
  		String pk_funcnode = (String) row.getValue(ds.nameToIndex("pk_funcnode"));
  		String appId = (String) row.getValue(ds.nameToIndex("appid"));
  		String templateName = (String) row.getValue(ds.nameToIndex("templatename"));
  		String pk_group = (String) row.getValue(ds.nameToIndex("pk_group"));
  		Integer priority = (Integer) row.getValue(ds.nameToIndex("priority"));
  		UFBoolean isactive = (UFBoolean) row.getValue(ds.nameToIndex("isactive"));
  		
  		LfwRuntimeEnvironment.getWebContext().getRequest().setAttribute(WebConstant.PERSONAL_PAGE_ID_KEY, windowId);
  		IUwTemplateService service = NCLocator.getInstance().lookup(IUwTemplateService.class);
  		IPageElementSerializer pmSerializer = NCLocator.getInstance().lookup(IPageElementSerializer.class);
  		
  		PageMeta pageMeta = this.getPageMetaById(windowId);
  		UIMeta uimeta = this.getUIMetaByWinId(windowId);
  		String pageMetaStr = PageMetaToXml.toString(pageMeta);
  		String uiMetaStr = pmSerializer.serializeUIMeta(uimeta);
  		
		try {
			UwTemplateVO vo = service.getTemplateOrCreate(pk_template);
			vo.setAppid(appId);
			vo.setWindowid(windowId);
			vo.setPk_funcnode(pk_funcnode);
			vo.doSetPageMetaStr(pageMetaStr);
  			vo.doSetUIMetaStr(uiMetaStr);
  			vo.setTemplatename(templateName);
  			vo.setPk_group(pk_group);
  			vo.setPriority(priority);
  			vo.setIsactive(isactive);
  			
  			service.updateTemplateVO(vo);
  			
  			String[] ids = pageMeta.getWidgetIds();
  			for(int i = 0; i < ids.length; i++){
  				String widgetId = ids[i];
  				LfwWidget view = pageMeta.getWidget(widgetId);
  				String widgetStr = pmSerializer.serializeWidget(view);
  				UIMeta childUIMeta = UIElementFinder.findUIMeta(uimeta, widgetId, widgetId+"_um");
//  			UIMeta childUIMeta = (UIMeta) uimeta.findElementById(uimeta, widgetId + "_um");
  				if(childUIMeta == null){
					DefaultUIMetaBuilder uiMetaBuilder = new DefaultUIMetaBuilder();
					LifeCyclePhase oriPhase = RequestLifeCycleContext.get().getPhase();
					RequestLifeCycleContext.get().setPhase(LifeCyclePhase.nullstatus);	
					UIMeta meta = uiMetaBuilder.buildUIMeta(pageMeta,windowId, widgetId);
					RequestLifeCycleContext.get().setPhase(oriPhase);
					childUIMeta = meta;
  				}
  				if(childUIMeta == null)
  					continue;
  				String childUIMetaStr = pmSerializer.serializeUIMeta(childUIMeta);
				pk_template = vo.getPk_template();
				UwViewVO viewVO = service.getViewOrCreate(pk_template, widgetId);
				
				viewVO.doSetUIMetaStr(childUIMetaStr);
				viewVO.doSetWidgetStr(widgetStr);
				viewVO.setPk_template(pk_template);
				
				service.updateViewVO(viewVO);
  			}
			
		} catch (PaBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
		AppLifeCycleContext.current().getWindowContext().closeViewDialog("editView");
  }
	  private UIMeta getUIMetaByWinId(  String winId){
		  	PageMeta pageMeta = this.getPageMetaById(winId);
			DefaultUIMetaBuilder uiMetaBuilder = new DefaultUIMetaBuilder();
			LifeCyclePhase oriPhase = RequestLifeCycleContext.get().getPhase();
			RequestLifeCycleContext.get().setPhase(LifeCyclePhase.nullstatus);		
			UIMeta meta = uiMetaBuilder.buildUIMeta(pageMeta);
			RequestLifeCycleContext.get().setPhase(oriPhase);
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
	  public void onCancelEvent(  MouseEvent<?> mouseEvent){
		  AppLifeCycleContext.current().getWindowContext().closeViewDialog("editView");
	  }
	  private WindowContext getCurrentWinCtx(){
		  return AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext();
	  }
	  public void beforeShowEvent(  DialogEvent dialogEvent){
		  	String opeStatus = (String) getCurrentWinCtx().getAppAttribute("OPERATE_STATUS");
			LfwWidget edit = AppLifeCycleContext.current().getWindowContext().getViewContext("editView").getView();
			Dataset ds = edit.getViewModels().getDataset("ds_template");
			
			Row navRow = (Row) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("navRow");
			Dataset navDs = (Dataset) AppLifeCycleContext.current().getApplicationContext().getAppAttribute("navDs");
			
			if(navRow == null)
				throw new LfwRuntimeException("请选择正确的导航");
			
			if("ADD_OPERATE".equals(opeStatus)){
				Row row = ds.getEmptyRow();
				
				String appId = (String) navRow.getValue(navDs.nameToIndex("appId"));
				String winId = (String) navRow.getValue(navDs.nameToIndex("windowId"));
				String pk_funcnode = (String) navRow.getValue(navDs.nameToIndex("pk_funcnode"));
				
				if(pk_funcnode != null)
					pk_funcnode = pk_funcnode.substring(0, 20);
				
				if(winId == null){
					throw new LfwRuntimeException("请选择正确的导航数据集");
				}
				row.setValue(ds.nameToIndex("windowid"), winId);
				row.setValue(ds.nameToIndex("appid"), appId);
				row.setValue(ds.nameToIndex("pk_funcnode"), pk_funcnode);
				
				ds.addRow(row);
				ds.setSelectedIndex(ds.getRowCount() - 1);
				ds.setEnabled(true);
			}
			else if("EDIT_OPERATE".equals(opeStatus)){
				Row oldRow = (Row) getCurrentWinCtx().getAppAttribute("editRow");
				Row row = ds.getEmptyRow();
				Field[] fields = ds.getFieldSet().getFields();
				  for(Field field:fields){
					  int index = ds.nameToIndex(field.getField());
					  if(index<0)
						  continue;
					  row.setValue(index, oldRow.getValue(index));
				  }
				ds.addRow(row);
				ds.setSelectedIndex(ds.getRowIndex(row));
				ds.setEnabled(true);
			}
	  }
}
