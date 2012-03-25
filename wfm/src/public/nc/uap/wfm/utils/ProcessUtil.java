package nc.uap.wfm.utils;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.page.LfwWidget;
/**
 * 打开流程设计器 2010-12-28 下午01:19:51 limingf
 */
public class ProcessUtil {
	public static String DESIGNER_URL = "/html/bin-release/wfdesigner.jsp";
	public static final String BROWSER_URL = "/html/bin-release/wfbrowser.jsp";
	/**
	 * 打开流程设计时的界面
	 * 
	 * @param context
	 * @param browse
	 *            :是否只是浏览
	 * @param newflag
	 *            :是否是新建
	 */
	public static void showDesigner(LfwPageContext context, boolean browse, boolean newflag) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		if (newflag) {
			String url = projectPath + DESIGNER_URL;
			context.addExecScript("setTabItemURL(\"wfdesgin_tab\",\"0\",\"" + url + "\")");
			return;
		}
		LfwWidget widget = context.getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("prodefds");
		Row row = ds.getSelectedRow();
		if (row == null) {
			context.addExecScript("setTabItemURL(\"wfdesgin_tab\",\"0\",\"" + "" + "\")");
			return;
		}
		String pk = (String) row.getValue(ds.nameToIndex("pk_prodef"));
		String url = projectPath + BROWSER_URL + "?wfpk=" + pk;
		context.addExecScript("setTabItemURL(\"wfdesgin_tab\",\"0\",\"" + url + "\")");
	}
	/**
	 * 获得流程浏览态的url地址,并指定流程定义，流程实例pk
	 * 
	 * @param wfpk
	 * @param proInsPk
	 * @param browse
	 * @return
	 */
	public static String getProcessUrl(String wfpk, String proInsPk, boolean browse) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		String url = "";
		// 如果是ios
		if (LfwRuntimeEnvironment.getBrowserInfo().isIos()) {
			url = LfwRuntimeEnvironment.getCorePath() + "/flwimage.jsp?proDefPk=" + wfpk + "&proInsPk=" + proInsPk;
		} else
			url = projectPath + BROWSER_URL + "?proDefPk=" + wfpk + "&proInsPk=" + proInsPk;
		return url;
	}
	/**
	 * 获得流程浏览态的url地址,并指定流程定义，流程实例pk
	 * 
	 * @param wfpk
	 * @param browse
	 * @return
	 */
	public static String getProcessUrl(String wfpk, boolean browse) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		String url = "";
		// 如果是ios
		if (LfwRuntimeEnvironment.getBrowserInfo().isIos()) {
			url = LfwRuntimeEnvironment.getCorePath() + "/flwimage.jsp?wfpk=" + wfpk;
		} else
			url = projectPath + BROWSER_URL + "?proDefPk=" + wfpk;
		return url;
	}
	/**
	 * 获得加签态流程设计器地址
	 * 
	 * @param wfpk
	 * @param proInsPk
	 * @return
	 */
	public static String getAddSignatureUrl(String wfpk, String proInsPk) {
		String projectPath = LfwRuntimeEnvironment.getRootPath();
		String url = "";
		// 如果是ios
		if (LfwRuntimeEnvironment.getBrowserInfo().isIos()) {
			url = "/portal" + "/pt/servlet/workflowImageServlet/doPost?wfpk=" + wfpk + "&proInsPk=" + proInsPk;
		} else
			url = projectPath + BROWSER_URL + "?proDefPk=" + wfpk + "&proInsPk=" + proInsPk;
		return url;
	}
}
