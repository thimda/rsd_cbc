package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.org.GroupVO;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
@Servlet(path = "/servlet/groupServlet") public class PtGroupServlet extends ExcuterServlet {
	private static final long serialVersionUID = -8417057858342060664L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Groups");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		Map<String, Element> map = new HashMap<String, Element>();
		for (int i = 0; i < size; i++) {
			GroupVO group = (GroupVO) vos[i];
			Element node = doc.createElement("Group");
			node.setAttribute("code", group.getCode());
			node.setAttribute("name", group.getName());
			node.setAttribute("pk_group", group.getPk_group());
			node.setAttribute("pk_parent", group.getPk_fathergroup());
			map.put(group.getPk_group(), node);
		}
		for (int i = 0; i < size; i++) {
			GroupVO group = (GroupVO) vos[i];
			Element node = map.get(group.getPk_group());
			if (group.getPk_fathergroup() == null || "".equals(group.getPk_fathergroup())) {
				root.appendChild(node);
			} else {
				Element parent = map.get(group.getPk_fathergroup());
				if (parent != null) {
					parent.appendChild(node);
				} else
					root.appendChild(node);
			}
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
		String pk_group = request.getParameter("pk_group");
		GroupVO[] groups = new GroupVO[] {};
		try {
			if (pk_group != null && !"".equals(pk_group))
				groups = CpbServiceFacility.getCpGroupQry().queryGroupVOsByPKS(new String[] { pk_group });
			else
				groups = CpbServiceFacility.getCpGroupQry().queryAllGroupVOs();
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(groups);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}