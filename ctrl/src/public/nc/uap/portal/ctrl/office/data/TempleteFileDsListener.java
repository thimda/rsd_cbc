package nc.uap.portal.ctrl.office.data;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.portal.ctrl.office.core.OfficeTempeteHelper;
import nc.uap.portal.ctrl.office.core.OfficeTempleteFileVo;
import nc.uap.portal.ctrl.office.core.SignHelper;
import nc.uap.portal.ctrl.office.core.OfficeSignVO;

public class TempleteFileDsListener  extends DefaultDatasetServerListener{

	public TempleteFileDsListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}

	@Override
	public void onDataLoad(DataLoadEvent se) {
		Dataset ds = se.getSource();
		String filetype =  (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("f");
		/**
		 * 文档类型 0: word,1:excel;6:wps,7:et
		 */
		String doctype =  (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("d");
		if(filetype == null || filetype == "")
			return;
		InsertData(ds,filetype,doctype);
	}
	
	/**
	 * 插入相关数据
	 * @param ds 当前数据集
	 * @param filetype 当前模板类型
	 */
	private void InsertData(Dataset ds,String filetype,String doctype){
		
		
		if(filetype.equalsIgnoreCase("sign"))
			InsertDataforSign(ds,filetype,doctype);
		else
			InsertDataforTemnplete(ds,filetype,doctype);
			
	}
	/**
	 * 插入模板类数据
	 * @param ds
	 * @param filetype
	 * @param doctype 文档类型
	 */
	private void InsertDataforTemnplete(Dataset ds,String filetype,String doctype){
		IOfficeFileQuery qry = NCLocator.getInstance().lookup(IOfficeFileQuery.class);
		List<OfficeFileVO> files = qry.queryByCondition(filetype, doctype);
		if(files == null)
			return;
		for(OfficeFileVO file : files){
			Row row = ds.getEmptyRow();			
			row.setValue(ds.nameToIndex("url"), buildDownUrl(file.getFileurl()));
			row.setValue(ds.nameToIndex("name"), file.getDisplayname());
			row.setValue(ds.nameToIndex("pk_file"), file.getPk_file());
			ds.addRow(row);
		}
		
	}
	/**
	 * 插入签名类数据
	 * @param ds
	 * @param filetype
	 */
	private void InsertDataforSign(Dataset ds,String filetype,String doctype){
		IOfficeFileQuery qry = NCLocator.getInstance().lookup(IOfficeFileQuery.class);
		List<OfficeFileVO> files = qry.queryByCondition(filetype, doctype);
		if(files == null)
			return;
		for(OfficeFileVO file : files){
			String usercode ="";
			List<OfficeFileUserVO> userlist = qry.queryOfficeUserByfilepk(file.getPk_file());
			if(userlist != null && userlist.size() > 0){
				usercode = userlist.get(0).getUsercode();
			}
			
			Row row = ds.getEmptyRow();		
			row.setValue(ds.nameToIndex("url"), buildDownUrl(file.getFileurl()));
			row.setValue(ds.nameToIndex("name"), file.getDisplayname());
			row.setValue(ds.nameToIndex("pk_file"), file.getPk_file());
			row.setValue(ds.nameToIndex("usercode"), usercode);
			
			ds.addRow(row);
		}
	}
	private String buildDownUrl(String url){
		String realurl = LfwRuntimeEnvironment.getRootPath() + "/pt/doc/file/down?id=" + url;
		return realurl;
	}
}
