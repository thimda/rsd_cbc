package nc.uap.portal.ctrl.office.data;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.ScriptServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.file.FileManager;
import nc.uap.portal.ctrl.office.core.OfficeTempeteHelper;
import nc.vo.pub.lang.UFDateTime;
import org.apache.commons.lang.StringUtils;
/**
 * 上传完毕，回调侦听
 * 
 * @author lisw
 * 
 */
public class FileMgrUploadCompleteListener extends ScriptServerListener {
	public FileMgrUploadCompleteListener(LfwPageContext pageCtx, String widgetId) {
		super(pageCtx, widgetId);
	}
	@Override public void handlerEvent(ScriptEvent event) {
		LfwPageContext ct = getGlobalContext();
		LfwWidget widget = getGlobalContext().getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("files");
		String currKey = ds.getCurrentKey();
		if (currKey == null || currKey.equals("")) {
			ds.getRowSet(Dataset.MASTER_KEY, true);
			ds.setCurrentKey(Dataset.MASTER_KEY);
		}
		// 获取参数
		String pk_doc = ct.getParameter("pk");
		String fileName = ct.getParameter("fileName");
		String fileext = ct.getParameter("fileext");
		String doctype = OfficeTempeteHelper.GetDocTypeByext(fileext);
		String filetype = ct.getParameter("filetype");
		String billitem = ct.getParameter("billitem");
		// 创建对象
		OfficeFileVO file = new OfficeFileVO();
		file.setPk_file(billitem);
		file.setFilename(fileName);
		file.setFileurl(pk_doc);
		file.setDoctype(doctype);
		file.setDisplayname(fileName);
		filetype = StringUtils.defaultIfEmpty(filetype, "templete");
		file.setFiletype(filetype);
		file.setModifyby(nc.uap.lfw.core.LfwRuntimeEnvironment.getLfwSessionBean().getUser_code());
		// 插入数据库
		IOfficeFileQuery qry = NCLocator.getInstance().lookup(IOfficeFileQuery.class);
		String pk = qry.insertData(file);
		file = qry.queryByPK(pk);
		// 修改文件保存状态
		// FileManager.getSystemFileManager().billSaveComplete(pk);
		// 处理ds
		Row row = ds.getEmptyRow();
		row.setValue(ds.nameToIndex("pk_file"), file.getPk_file());
		row.setValue(ds.nameToIndex("filetype"), file.getFiletype());
		row.setValue(ds.nameToIndex("doctype"), file.getDoctype());
		row.setValue(ds.nameToIndex("filename"), file.getFilename());
		row.setValue(ds.nameToIndex("fileurl"), file.getFileurl());
		row.setValue(ds.nameToIndex("displayname"), file.getDisplayname());
		row.setValue(ds.nameToIndex("ts"), file.getTs());
		row.setValue(ds.nameToIndex("edit"), "edit");
		row.setValue(ds.nameToIndex("modifyby"), file.getModifyby());
		ds.addRow(row);
	}
}
