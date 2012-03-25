package nc.uap.wfm.wfdesign;
import org.apache.commons.lang.StringUtils;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
public class DocwfDesignMouseListener extends MouseServerListener<MenuItem> {
	public DocwfDesignMouseListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	@Override public void onclick(MouseEvent<MenuItem> e) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		LfwWidget widget = getGlobalContext().getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("ds_prodef");
		Row row = ds.getSelectedRow();
		String pk_group = row.getString(ds.nameToIndex("pk_group"));
		if (row == null) {
			this.getGlobalContext().addExecScript(RuntimeExceptionUtil.getMessage(new LfwRuntimeException("请选择需要设计的流程！")));
			return;
		}
		String pk_formdefinition = row.getString(ds.nameToIndex("pk_startfrm"));
		String proDefPk=row.getString(ds.nameToIndex("pk_prodef"));
		String url = projectPath + "/html/bin-release/wfdesigner.jsp?pk_group=" + pk_group + "&pk_formdefinition=" + pk_formdefinition;
		if(StringUtils.isNotBlank(proDefPk)){
			url=url+"&pk_prodef="+proDefPk;
			url=url+"&wfpk="+proDefPk;
		}
		
		this.getGlobalContext().addExecScript(
				"window.open ('" + url + "', '流程设计', 'height=768, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')");
		// else if (IOperatorState.EDIT.equals(opeStatus)) {
		// String pk = row.getString(ds.nameToIndex("pk_prodef"));
		// String url = projectPath +
		// "/html/bin-release/wfdesigner.jsp?browseMode=false&wfpk=" + pk +
		// "&pk_group=" + pk_group + "&pk_formdefinition=" + pk_formdefinition;
		// this.getGlobalContext().openMaxWindow(url, "流程设计器", true);
		// }
	}
}