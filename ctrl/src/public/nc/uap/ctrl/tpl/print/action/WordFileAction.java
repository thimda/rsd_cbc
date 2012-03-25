package nc.uap.ctrl.tpl.print.action;

import java.io.IOException;

import nc.bs.framework.common.NCLocator;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateService;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.file.action.FileSystemAction;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;

@Servlet(path = "/word/file")
public class WordFileAction extends FileSystemAction {

	/**
	 * @param id
	 *            pk_file 和 fileName 组成的字符串
	 * @throws IOException
	 */
	@Action
	public void down(@Param(name = "id") String id) throws IOException {
		ICpPrintTemplateService printTemplate = NCLocator.getInstance().lookup(
				ICpPrintTemplateService.class);
		try {
			printTemplate.merger(id, response);
		} catch (TplBusinessException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
