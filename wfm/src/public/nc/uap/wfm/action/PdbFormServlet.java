package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-3 ÉÏÎç09:18:19 limingf
 */
@Servlet(path = "/servlet/formServlet") public class PdbFormServlet extends WfBaseServlet {
	private static final long serialVersionUID = 856521354399862503L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Forms");
		doc.appendChild(root);
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			CpAppsNodeVO form = (CpAppsNodeVO) vos[i];
			Element node = doc.createElement("Form");
			node.setAttribute("name", form.getUrl());
			node.setAttribute("pk_formdefinition", form.getPk_appsnode());
			node.setAttribute("tabname", form.getTableName());
			root.appendChild(node);
		}
		return doc;
	}
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		try {
			CpAppsNodeVO[] appNodes = NCLocator.getInstance().lookup(ICpAppsNodeQry.class).getAllNodes();
			Document doc = buildXml(appNodes);
			XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		out.println();
	}
}
