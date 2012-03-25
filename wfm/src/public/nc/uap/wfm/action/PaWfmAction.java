package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifPersonalizationCmd;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.vo.jcom.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
@Servlet(path = "/PaWfm") public class PaWfmAction extends BaseAction {
	@SuppressWarnings("restriction") @Action(method = "POST") public void open() {
		String proDefPk = request.getParameter("pk_prodef");
		ProDef proDef = ProDefsContainer.getByProDefPkAndId(proDefPk, null);
		WfmFlwTypeVO flwTypeVo = proDef.getFlwtype();
		String funNodePk = flwTypeVo.getPageid();
		ICpAppsNodeQry cpAppNodeService = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
		String where = CpAppsNodeVO.PK_APPSNODE + " = '" + funNodePk + "'";
		CpAppsNodeVO funNodeVo = null;
		try {
			CpAppsNodeVO[] vos = cpAppNodeService.getAppsNodeVos(where);
			if (vos == null || vos.length == 0)
				return;
			funNodeVo = vos[0];
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("查询功能节点出错！", e);
		}
		String appId = funNodeVo.getAppid();
		String url = funNodeVo.getUrl();
		int start = url.lastIndexOf("app/");
		start = start + "app/".length();
		int end = url.indexOf("?");
		if (end == -1) {
			end = url.length();
		}
		String winId = null;
		if (url.indexOf("mockapp") > -1)
			winId = url.substring(start, end);
		else
			appId = url.substring(start, end);
		String portId = request.getParameter("port_id");
		Map<String, String> wfmparamMap = new HashMap<String, String>();
		wfmparamMap.put("pk_prodef", proDefPk);
		wfmparamMap.put("port_id", portId);
		wfmparamMap.put("ext1", null);
		wfmparamMap.put("ext2", null);
		wfmparamMap.put("ext3", null);
		wfmparamMap.put("ext4", null);
		wfmparamMap.put("ext5", null);
		UifPersonalizationCmd cmd = new UifPersonalizationCmd(appId, winId, null, wfmparamMap, "流程定义个性化");
		String pk_template = cmd.responseTemplatePk();
		cmd.execute();
		String resUrl = LfwRuntimeEnvironment.getRootPath() + "/app/mockapp/pa?model=nc.uap.lfw.pa.PaEditorPageModel&from=1&appId=" + appId + "&winId=" + winId + "&pk_template=" + pk_template;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		Document document = this.buildXml(resUrl);
		XMLUtil.printDOMTree(out, document, 0, "UTF-8");
		out.println();
	}
	public Document buildXml(String url) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Root");
		doc.appendChild(root);
		Element node = doc.createElement("Url");
		node.setTextContent(url);
		root.appendChild(node);
		return doc;
	}
}
