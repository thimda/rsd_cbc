package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.org.GroupVO;
import nc.vo.org.OrgVO;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
@Servlet(path = "/servlet/userServlet") public class PtUserServlet extends ExcuterServlet {
	private static final long serialVersionUID = 2807698011444492120L;
	@Override public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Users");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		// 查出用户所在的集团
		String[] pk_groups = new String[vos.length];
		List<String> orglist = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			CpUserVO user = (CpUserVO) vos[i];
			pk_groups[i] = user.getPk_group();
			if (user.getPk_org() != null && !"".equals(user.getPk_org())) {
				orglist.add(user.getPk_org());
			}
		}
		GroupVO[] groups = new GroupVO[] {};
		OrgVO[] orgs = new OrgVO[] {};
		try {
			groups = CpbServiceFacility.getCpGroupQry().queryGroupVOsByPKS(pk_groups);
			orgs = CpbServiceFacility.getCpOrgQry().getOrgs(new String[orglist.size()]);
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Map<String, GroupVO> gmap = new HashMap<String, GroupVO>();
		int groupsize = groups == null ? 0 : groups.length;
		for (int i = 0; i < groupsize; i++) {
			GroupVO group = groups[i];
			gmap.put(group.getPk_group(), group);
		}
		Map<String, OrgVO> omap = new HashMap<String, OrgVO>();
		int orgsize = orgs == null ? 0 : orgs.length;
		for (int i = 0; i < orgsize; i++) {
			OrgVO org = orgs[i];
			omap.put(org.getPk_org(), org);
		}
		for (int i = 0; i < size; i++) {
			CpUserVO user = (CpUserVO) vos[i];
			Element node = doc.createElement("User");
			node.setAttribute("id", user.getCuserid());
			node.setAttribute("name", user.getUser_name());
			node.setAttribute("pk_user", user.getCuserid());
			node.setAttribute("pk_group", user.getPk_group());
			OrgVO org = omap.get(user.getPk_org());
			if (user.getPk_org() != null && !"".equals(user.getPk_org()) && org != null)
				node.setAttribute("orgname", org.getName());
			GroupVO group = gmap.get(user.getPk_group());
			if (group != null)
				node.setAttribute("groupname", group.getName());
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
		String pk_group = request.getParameter("pk_group");
		String pk_org = request.getParameter("pk_org");
		String pk_dept = request.getParameter("pk_dept");
		CpUserVO[] users = new CpUserVO[] {};
		String pks = request.getParameter("pk_user");
		String[] pk_users = null;
		if (pks != null && !"".equals(pks))
			pk_users = pks.split(",");
		try {
			if (pk_group != null && !"".equals(pk_group))
				users = CpbServiceFacility.getCpUserQry().getUserByPkGroup(pk_group);
			else if (pk_org != null && !"".equals(pk_org)) {
				users = CpbServiceFacility.getCpUserQry().getUserByPkorg(pk_org);
			} else if (pk_dept != null && !"".equals(pk_dept)) {
				users = CpbServiceFacility.getCpUserQry().getAllUserByPkDept(pk_dept);
			} else if (pk_users != null && !"".equals(pk_users[0]))
				users = CpbServiceFacility.getCpUserQry().getUserByPkS(pk_users);
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(users);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}