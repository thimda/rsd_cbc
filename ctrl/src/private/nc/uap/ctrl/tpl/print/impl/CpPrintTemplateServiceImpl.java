package nc.uap.ctrl.tpl.print.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPopWordTemplateService;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateService;
import nc.uap.ctrl.tpl.print.TemplatePubViewController;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.app.plugin.AppControlPlugin;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetFieldRelCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.file.action.FileSystemAction;
import nc.uap.oba.word.generator.MergeEngine;
import nc.uap.oba.word.generator.util.DocumentHelper;

import org.w3c.dom.Document;

public class CpPrintTemplateServiceImpl implements ICpPrintTemplateService {

	@Override
	public void print(Dataset ds) throws TplBusinessException {
		// TODO Auto-generated method stub
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		if (ctx == null) {
			return;
		}
		LfwWidget widget = ctx.getViewContext().getView();
		String nodeCode = (String) ctx.getApplicationContext().getAppAttribute(
				AppControlPlugin.NODECODE);
		new UifDatasetFieldRelCmd(ds.getId()).execute();
		Row[] rows = ds.getSelectedRows();
		if (rows == null || rows.length < 1) {
			throw new LfwRuntimeException("请选择打印信息!");
		} else if (rows.length > 1) {
			throw new LfwRuntimeException("请选择单行打印信息!");
		}
		ICpPrintTemplateInnerQryService service = NCLocator.getInstance()
				.lookup(ICpPrintTemplateInnerQryService.class);
		try {
			CpPrintTemplateVO[] ptVos = service.getPrintTemplates(nodeCode);
			if(ptVos.length>1){
				ctx.getApplicationContext().addAppAttribute("ptVos", ptVos);
				ctx.getApplicationContext().addAppAttribute("Widget", widget);
				ctx.getApplicationContext().addAppAttribute("MasterDs", ds);
				ctx.getApplicationContext().getCurrentWindowContext().popView(TemplatePubViewController.PUBVIEW_TEMPALTE, TemplatePubViewController.TEMPLATE_WIDTH, TemplatePubViewController.TEMPLATE_HEIGHT, TemplatePubViewController.TEMPALTE_TITLE);
			}else if(ptVos.length==1){
				ICpPopWordTemplateService popService = NCLocator.getInstance()
				.lookup(ICpPopWordTemplateService.class);
				popService.open(ptVos[0].getPk_print_template(),ds,widget);
			}else{
				throw new TplBusinessException("没有匹配的打印模板");
			}
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(),e);
		}
	}

	@Override
	public void merger(String id, HttpServletResponse response)
			throws TplBusinessException {
		// TODO Auto-generated method stub
		OutputStream out = null;
		String path = null;
		try {
			out = response.getOutputStream();
			String[] str = id.split(",");
			String pk_file = str[0];
			String fileName = str[1];
			String root = LfwRuntimeEnvironment.getRealPath();
			String tmp_path = root + "/" + UUID.randomUUID().toString()
					+ ".docx";
			if (null == fileName || "".equals(fileName.trim())
					|| null == pk_file || "".equals(pk_file.trim())) {
				return;
			}
			path = root + "/" + fileName;
			File dataFile = new File(path);
			OutputStream out_tmp = null;
			try {
				out_tmp = new FileOutputStream(tmp_path);
				new FileSystemAction().getFileManager().download(pk_file,
						out_tmp);
				File template = new java.io.File(tmp_path);
				Document document = DocumentHelper.file2Document(dataFile);
				// 合并
				String data = DocumentHelper.document2String(document);

				MergeEngine generator = new MergeEngine(template, data);
				// 设置发布的文档是否为最终状态
				generator.getOptions().setPublishAsFinal(true);

				// 合并结果
				generator.generate(out);
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new TplBusinessException("文件下载失败", e);
			} finally {
				if (null != out_tmp) {
					out_tmp.flush();
					out_tmp.close();
				}
				deleteFile(tmp_path);
			}
		} catch (Exception e1) {
			throw new TplBusinessException("文件下载失败", e1);
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (Exception e2) {
				throw new TplBusinessException(e2.getMessage(), e2);
			} finally {
				deleteFile(path);
			}
		}
	}

	private void deleteFile(String path) {
		String rootPath = path.substring(0, path.lastIndexOf("/"));
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		File file = new File(rootPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (fileName.equals(f.getName())) {
					f.delete();
				}
			}
		}
	}
}
