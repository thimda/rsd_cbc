package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.org.GroupVO;
import nc.vo.org.OrgVO;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-2 上午10:19:30 limingf
 */
@Servlet(path = "/servlet/grouporgTreeServlet") public class PtGroupOrgsTreeServlet extends ExcuterServlet {
	private static final long serialVersionUID = -7558156809709671481L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Depts");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		Map<String, Element> groupmap = new HashMap<String, Element>();
		String[] pk_groups = new String[size];
		for (int i = 0; i < size; i++) {
			GroupVO group = (GroupVO) vos[i];
			Element node = doc.createElement("Dept");
			node.setAttribute("code", group.getCode());
			node.setAttribute("name", group.getName());
			node.setAttribute("pk_group", group.getPk_group());
			groupmap.put(group.getPk_group(), node);
			pk_groups[i] = group.getPk_group();
		}
		for (int i = 0; i < size; i++) {
			GroupVO group = (GroupVO) vos[i];
			Element node = groupmap.get(group.getPk_group());
			if (group.getPk_fathergroup() == null || "".equals(group.getPk_fathergroup())) {
				root.appendChild(node);
			} else {
				Element parent = groupmap.get(group.getPk_fathergroup());
				if (parent != null)
					parent.appendChild(node);
				else
					root.appendChild(node); // 由于数据库数据有问题，暂时这么办
			}
		}
		OrgVO[] orgvos = new OrgVO[] {};
		try {
			orgvos = CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDWithGlobeOrg(pk_groups[0], true);
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Map<String, Element> orgmap = new HashMap<String, Element>();
		String[] pk_orgs = new String[orgvos == null ? 0 : orgvos.length];
		for (int i = 0; i < orgvos.length; i++) {
			OrgVO orgvo = orgvos[i];
			Element node = doc.createElement("Dept");
			node.setAttribute("code", orgvo.getCode());
			node.setAttribute("name", orgvo.getName());
			node.setAttribute("pk_org", orgvo.getPk_org());
			orgmap.put(orgvo.getPk_org(), node);
			pk_orgs[i] = orgvo.getPk_org();
		}
		for (int i = 0; i < orgvos.length; i++) {
			OrgVO orgvo = orgvos[i];
			Element node = orgmap.get(orgvo.getPk_org());
			if (orgvo.getPk_fatherorg() == null || "".equals(orgvo.getPk_fatherorg())) {
				if (groupmap.get(orgvo.getPk_group()) != null) {
					groupmap.get(orgvo.getPk_group()).appendChild(node);
				}
			} else {
				Element parent = orgmap.get(orgvo.getPk_fatherorg());
				if (parent != null)
					parent.appendChild(node);
				else
					root.appendChild(node);
			}
		}
		//this.docToString(doc);
		return doc;
	}
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		LfwSessionBean session = LfwRuntimeEnvironment.getLfwSessionBean();
		if (session != null) {
			session.getPk_user();
		}
		String pk_group = request.getParameter("pk_group");
		GroupVO[] groups = new GroupVO[] {};
		try {
			if (pk_group != null && !"".equals(pk_group)) {
				groups = CpbServiceFacility.getCpGroupQry().queryGroupVOsByPKS(new String[] { pk_group });
			} else {
				groups = CpbServiceFacility.getCpGroupQry().queryAllGroupVOs();
			}
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(groups);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
