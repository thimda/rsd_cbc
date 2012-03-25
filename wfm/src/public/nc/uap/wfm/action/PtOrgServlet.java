package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
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
@Servlet(path = "/servlet/orgServlet") public class PtOrgServlet extends ExcuterServlet {
	private static final long serialVersionUID = 4957897475197418742L;
	@Override public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Orgs");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			OrgVO org = (OrgVO) vos[i];
			Element node = doc.createElement("Org");
			node.setAttribute("code", org.getCode());
			node.setAttribute("name", org.getName());
			node.setAttribute("pk_org", org.getPk_org());
			node.setAttribute("pk_group", org.getPk_group());
			root.appendChild(node);
		}
		return doc;
	}
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		SuperVO[] orgs = new OrgVO[] {};
		String pk_group = request.getParameter("pk_group");
		String pks = request.getParameter("pk_org");
		String[] pk_orgs = null;
		if (pks != null && !"".equals(pks))
			pk_orgs = pks.split(",");
		try {
			if (pk_orgs != null && !"".equals(pk_orgs[0]))
				orgs = CpbServiceFacility.getCpOrgQry().getOrgs(pk_orgs);
			else if (pk_group != null && !"".equals(pk_group))
				orgs = CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDWithGlobeOrg(pk_group, true);
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(orgs);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
