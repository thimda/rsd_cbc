package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.org.OrgVO;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-2 ÉÏÎç10:18:27 limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/deptServlet") public class PtDeptServlet extends WfBaseServlet {
	private static final long serialVersionUID = 4957897475197418742L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Orgs");
		doc.appendChild(root);
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			OrgVO org = (OrgVO) vos[i];
			Element node = doc.createElement("Org");
			node.setAttribute("code", org.getCode());
			node.setAttribute("name", org.getName());
			node.setAttribute("pk_org", org.getPk_org());
			node.setAttribute("group", org.getPk_group());
			root.appendChild(node);
		}
		return doc;
	}
	@Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		SuperVO[] orgs = new OrgVO[] {};
		// try {
		// orgs = null;//CpbServiceFacility.getCpOrgQry().getAllOrgs();
		// } catch (BusinessException e) {
		// throw new LfwRuntimeException(e);
		// }
		Document doc = buildXml(orgs);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
