package nc.uap.ctrl.tpl.print;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifCloseViewCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;

/**
 * @author chouhl
 */
public class TemplatePubViewController implements IController {
	private static final long serialVersionUID = 1L;
	public static final String PUBVIEW_TEMPALTE = "pubview_template";
	public static final String TEMPLATELIST = "templateList";
	public static final String TEMPLATE_WIDTH = "500";
	public static final String TEMPLATE_HEIGHT = "500";
	public static final String TEMPALTE_TITLE = "模板选择";

	public void onOK(MouseEvent mouseEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		ViewContext viewCtx = ctx.getApplicationContext()
				.getCurrentWindowContext().getCurrentViewContext();
		Dataset ds = viewCtx.getView().getViewModels().getDataset(TEMPLATELIST);
		LfwWidget widget = (LfwWidget)ctx.getApplicationContext().getAppAttribute("Widget");
		Dataset masterDs = (Dataset)ctx.getApplicationContext().getAppAttribute("MasterDs");
		Row row = ds.getSelectedRow();
		if (row == null)
			throw new LfwRuntimeException("请先选中模板");
		String pk_template = (String)row.getValue(ds.nameToIndex("pk_template"));
		ICpPopWordTemplateService popService = NCLocator.getInstance()
		.lookup(ICpPopWordTemplateService.class);
		try{
			popService.open(pk_template, masterDs,widget);
			CmdInvoker.invoke(new UifCloseViewCmd(PUBVIEW_TEMPALTE));
		}catch (TplBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e.getMessage(),e);
		}
		
	}

	public void onCANCEL(MouseEvent mouseEvent) {
		CmdInvoker.invoke(new UifCloseViewCmd(PUBVIEW_TEMPALTE));
	}

	public void data_beforeShow(DialogEvent dialogEvent) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		Dataset templateList = ctx.getWindowContext().getViewContext(
				PUBVIEW_TEMPALTE).getView().getViewModels().getDataset(
				TEMPLATELIST);
		templateList.clear();
		CpPrintTemplateVO[] ptVos = (CpPrintTemplateVO[]) ctx
				.getApplicationContext().getAppAttribute("ptVos");
		for (CpPrintTemplateVO vo : ptVos) {
			Row emptyRow = templateList.getEmptyRow();
			int codeIndex = templateList.nameToIndex("modelcode");
			int nameIndex = templateList.nameToIndex("modelname");
			int pkIndex = templateList.nameToIndex("pk_template");
			emptyRow.setValue(codeIndex, vo.getModelcode());
			emptyRow.setValue(nameIndex, vo.getModelname());
			emptyRow.setValue(pkIndex, vo.getPk_print_template());
			templateList.addRow(emptyRow);
		}
	}
}
