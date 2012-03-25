package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmVirtualRoleQry;
import nc.uap.wfm.vo.WfmVirtualRoleVO;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-2 ÉÏÎç10:19:30 limingf
 */
@Servlet(path = "/servlet/virsualRoleServlet") public class PtVirtualRoleServlet extends ExcuterServlet {
	private static final long serialVersionUID = -7558156809709671481L;
	@Override public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Roles");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			WfmVirtualRoleVO role = (WfmVirtualRoleVO) vos[i];
			Element node = doc.createElement("Role");
			node.setAttribute("code", role.getCode());
			node.setAttribute("name", role.getName());
			node.setAttribute("pk_role", role.getPk_virtualrole());
			node.setAttribute("group", role.getPk_group());
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
		WfmVirtualRoleVO[] roles = new WfmVirtualRoleVO[] {};
		String pk_group = request.getParameter("pk_group");
		String pks = request.getParameter("pk_role");
		String[] pk_roles = null;
		if (pks != null && !"".equals(pks))
			pk_roles = pks.split(",");
		try {
			IWfmVirtualRoleQry qryService = (IWfmVirtualRoleQry) NCLocator.getInstance().lookup(IWfmVirtualRoleQry.class.getName());
			if (pk_roles != null && !"".equals(pk_roles[0]))
				roles = qryService.getRoleByPk(pk_roles);
			else if (pk_group != null && !"".equals(pk_group)) {
				roles = qryService.getRoleByGroup(pk_group);
			} else
				roles = qryService.getAllRoles();
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(roles);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
