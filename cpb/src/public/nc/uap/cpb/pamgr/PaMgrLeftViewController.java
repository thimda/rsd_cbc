package nc.uap.cpb.pamgr;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.uap.lfw.core.ContextResourceUtil;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.model.parser.ApplicationParser;
import nc.uap.lfw.core.model.util.DefaultPageMetaBuilder;
import nc.uap.lfw.core.page.LifeCyclePhase;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.RequestLifeCycleContext;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.pa.itf.IPaFuncnodeLoadService;
import nc.uap.lfw.pa.pamgr.PaFuncNodeProxyVO;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.lang.UFBoolean;
/** 
 * @author chouhl
 */
public class PaMgrLeftViewController implements IController {
	private static final long serialVersionUID=1L;
	public void onDataLoad(  DataLoadEvent dataLoadEvent){
	  	Dataset ds = dataLoadEvent.getSource();
//		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
		
		IPaFuncnodeLoadService service = getPaFucnodeServie();
		List<PaFuncNodeProxyVO> allFuncnodes = service.getFucnodeVOBySpecial(UFBoolean.TRUE);
		
		ds.setCurrentKey(Dataset.MASTER_KEY);
		
		int idIndex = ds.nameToIndex("id");
		int titleIndex = ds.nameToIndex("title");
		int funcnodIndex = ds.nameToIndex("pk_funcnode");
		int parentIndex = ds.nameToIndex("pk_parent");
		int groupIndex = ds.nameToIndex("pk_group");
		int urlIndex = ds.nameToIndex("url");
		int i18nIndex = ds.nameToIndex("i18nname");
		int windIdIndex = ds.nameToIndex("windowId");
		int appIdIndex = ds.nameToIndex("appId");
		
		Row row = ds.getEmptyRow();
		if(allFuncnodes != null && allFuncnodes.size() > 0){
			for(int i = 0; i < allFuncnodes.size(); i++){
				PaFuncNodeProxyVO node = allFuncnodes.get(i);
				
				if(node != null){
					String url = node.getUrl();
					if(url.isEmpty()){
						LfwLogger.error(node.getId() + "的树节点出错，节点的url为空了");
						continue;
//						throw new LfwRuntimeException("加载树节点出错，节点的url不能为空");
					}
					if(!isAppNode(url)){
						String pageId = getPageIdByUrl(url);
						
						row = ds.getEmptyRow();
						row.setValue(funcnodIndex, node.getPk_appsnode());
						row.setValue(idIndex, node.getId());
						row.setValue(windIdIndex, pageId);
						row.setValue(titleIndex, node.getTitle());
						row.setValue(urlIndex, node.getUrl());
						row.setValue(i18nIndex, node.getI18nname());
						
						ds.addRow(row);
					}
					else{
						//通过url获取app的id，并将其设置为父节点
						String appId = getAppId(url);
						
						row = ds.getEmptyRow();
						row.setValue(funcnodIndex, node.getPk_appsnode());
						row.setValue(idIndex, appId);
						row.setValue(titleIndex, node.getTitle());
						row.setValue(groupIndex, node.getPk_group());
						row.setValue(appIdIndex, appId);
						row.setValue(i18nIndex, node.getI18nname());
						ds.addRow(row);
						
						
						File appDefFile = ContextResourceUtil.getFile("/html/applications/" + appId + "/application.app");
						Application app = ApplicationParser.parse(appDefFile);
						
						if(app == null){
							String winId = getWinId(url);
							if(winId == null)
								continue;
							PageMeta pm = getPageMetaById(winId);
							
							String pmTitle = pm.getCaption();
							
							row = ds.getEmptyRow();
							row.setValue(parentIndex, node.getPk_appsnode());
							row.setValue(appIdIndex, appId);
							row.setValue(funcnodIndex, node.getPk_appsnode()+ winId);
							row.setValue(groupIndex, node.getPk_group());
							row.setValue(windIdIndex, winId);
							row.setValue(idIndex, winId);
							row.setValue(titleIndex, pmTitle);
							ds.addRow(row);
							
						}
						else{
							List<PageMeta> winList = app.getWindowList();
							
							if(winList != null){
								for(int j = 0; j < winList.size(); j++){
									PageMeta win = winList.get(j);
									String winId = win.getId();
									
									PageMeta pm = getPageMetaById(winId);
									if(pm == null)
										return;
									
									String pmTitle = win.getCaption();
									
									row = ds.getEmptyRow();
									row.setValue(parentIndex, node.getPk_appsnode());
									row.setValue(appIdIndex, appId);
									row.setValue(funcnodIndex, node.getPk_appsnode()+ winId);
									row.setValue(groupIndex, node.getPk_group());
									row.setValue(windIdIndex, winId);
									row.setValue(idIndex, winId);
									row.setValue(titleIndex, pmTitle);
									ds.addRow(row);
									
								}
							}
						}
					}
				}
			}
		}
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
  	private String getPageIdByUrl(  String url){
  		String sub = "pageId=";
		String subString = null ;
		int begin = url.indexOf(sub);
		if(begin != -1){
			int end = url.indexOf("&", begin);
			if(end != -1)
				subString = url.substring(begin+sub.length(), url.indexOf("&", begin));
			else
				subString = url.substring(begin + sub.length());
			}
		return subString;
  	}
  	private String getAppId(  String url){
	  	String[] splitUrl = url.split("/");
		int appIndex = getSplitIndex(splitUrl, "app");
		return splitUrl[appIndex + 1];
  	}
  	
	private String getWinId(String url) {
		String[] splitUrl = url.split("/");
		int winIndex = getSplitIndex(splitUrl, "mockapp");
		return splitUrl[winIndex+1];
	}
  	
  	private int getSplitIndex(  String[] splitUrl, String split){
  		for(int i = 0; i < splitUrl.length; i++){
				String sub = splitUrl[i];
				if(sub.equals(split)){
					return i;
				}
			}
		return -1;
  	}
  	private boolean isAppNode(  String url){
  		if(url.contains("app/"))
			return true;
		return false;
  	}
  	public IPaFuncnodeLoadService getPaFucnodeServie(){
  		return (IPaFuncnodeLoadService) LfwClassUtil.newInstance("nc.uap.cpb.org.impl.PaFuncnodeLoadServiceImpl");
  	}
  	public void onAfterRowSelect(  DatasetEvent datasetEvent){
			Dataset navDs = datasetEvent.getSource();
			Row navRow = navDs.getSelectedRow();
			
			if(navRow == null)
				throw new LfwRuntimeException("请选择导航");
			
			AppLifeCycleContext.current().getApplicationContext().addAppAttribute("navRow", navRow);
			AppLifeCycleContext.current().getApplicationContext().addAppAttribute("navDs", navDs);
			
  	}
}
