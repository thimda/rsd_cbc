package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.utils.WfmXmlUtil;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.org.OrgVO;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 获取角色组 2010-11-2 上午10:19:30 limingf
 */
@Servlet(path = "/servlet/rolegroupTreeServlet") public class PtRoleGroutTreeServlet extends ExcuterServlet {
	private static final long serialVersionUID = -7558156809709671481L;
	@Override public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Rolegroups");
		doc.appendChild(root);
		if (vos == null || vos.length == 0) {
			return doc;
		}
		int size = vos.length;
		Map<String, Element> map = new HashMap<String, Element>();
		Map<String, Element> map1 = new HashMap<String, Element>();
		for (int i = 0; i < size; i++) {
			OrgVO org = (OrgVO) vos[i];
			Element node = doc.createElement("Rolegroup");
			node.setAttribute("code", org.getCode());
			node.setAttribute("name", org.getName());
			node.setAttribute("pk_org", org.getPk_org());
			map.put(org.getPk_org(), node);
		}
		for (int i = 0; i < size; i++) {
			OrgVO org = (OrgVO) vos[i];
			Element node = map.get(org.getPk_org());
			if (org.getPk_fatherorg() == null || org.getPk_fatherorg().length() == 0) {
				root.appendChild(node);
			} else {
				Element parent = map.get(org.getPk_fatherorg());
				if (parent == null) {
					root.appendChild(node);
				} else {
					parent.appendChild(node);
				}
			}
			CpRoleGroupVO[] roleGroupVos = null;
			try {
				roleGroupVos = CpbServiceFacility.getCpRoleGroupQry().getRoleGroupByPkorgs(new String[] { org.getPk_org() });
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			if (roleGroupVos != null && roleGroupVos.length != 0) {
				for (int j = 0; j < roleGroupVos.length; j++) {
					Element element = doc.createElement("Rolegroup");
					element.setAttribute("code", roleGroupVos[j].getGroupcode());
					element.setAttribute("name", roleGroupVos[j].getGroupname());
					element.setAttribute("pk_rolegroup", roleGroupVos[j].getPk_rolegroup());
					map1.put(roleGroupVos[j].getPk_rolegroup(), element);
					node.appendChild(element);
				}
				for (int j = 0; j < roleGroupVos.length; j++) {
					Element element = map1.get(roleGroupVos[j].getPk_rolegroup());
					if (roleGroupVos[j].getPk_parent() == null || roleGroupVos[j].getPk_parent().length() == 0) {
						node.appendChild(element);
					} else {
						Element parent = map1.get(roleGroupVos[j].getPk_parent());
						if (parent == null) {
							node.appendChild(element);
						} else {
							parent.appendChild(element);
						}
					}
				}
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
		OrgVO[] orgs = new OrgVO[] {};
		String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		try {
			if (pk_group != null && !"".equals(pk_group)) {
				orgs = CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDWithGlobeOrg(pk_group, true);
			}
		} catch (Exception e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(orgs);
		System.out.println(WfmXmlUtil.docToString(doc));
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
