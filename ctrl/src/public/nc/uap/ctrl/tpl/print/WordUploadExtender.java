package nc.uap.ctrl.tpl.print;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.file.FileManager;
import nc.uap.lfw.file.IFileUploadExtender;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public class WordUploadExtender implements IFileUploadExtender {

	private String[] rets = null;
	
	@Override
	public void extend(MultipartHttpServletRequest req, String filepk)
			throws LfwRuntimeException {
		// TODO Auto-generated method stub
			
		try {
			String pk_template = req.getParameter("billitem");			
			ICpPrintTemplateInnerService print = NCLocator.getInstance().lookup(ICpPrintTemplateInnerService.class);
			ICpPrintTemplateInnerQryService printQry = NCLocator.getInstance().lookup(ICpPrintTemplateInnerQryService.class);
			try{
			//更新模板
				CpPrintTemplateVO templateVo = printQry.getPrintTemplateVO(pk_template);
				templateVo.setPk_file(filepk);
				print.updateTemplate(templateVo);
			}catch(TplBusinessException e){
				Logger.error(e.getMessage(), e);
			}
			
			//修改文件保存状态
			FileManager.getSystemFileManager().billSaveComplete(pk_template);
			
			rets = new String[]{filepk,pk_template};
		} catch (LfwRuntimeException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage(),e);
		}

	}

	@Override
	public String[] getRetValues() {
		// TODO Auto-generated method stub
		return rets;
	}

}
