package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 获取流程定义 2010-11-25 上午09:44:54 limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/processServlet") public class ProcessServlet extends WfBaseServlet {
	private static final long serialVersionUID = -1912478494778271618L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Prodefs");
		doc.appendChild(root);
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			WfmProdefVO  item = (WfmProdefVO) vos[i];
			Element node = doc.createElement("Prodef");
			node.setAttribute("id", item.getId());
			node.setAttribute("pk_prodef", item.getPk_prodef());
			node.setAttribute("name", item.getName());
			root.appendChild(node);
		}
		doc.toString();
		return doc;
	}
	@Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		WfmProdefVO[] pros = new WfmProdefVO[] {};
		IWfmProDefQry prodefService = NCLocator.getInstance().lookup(IWfmProDefQry.class);
		String pks = request.getParameter("pk_prodef");
		String[] pk_prodefs = null;
		if (pks != null && !"".equals(pks))
			pk_prodefs = pks.split(",");
		try {
			if (pk_prodefs != null && !"".equals(pk_prodefs[0]))
				pros = prodefService.getProDefVOByProDefPks(pk_prodefs);
			else
				pros = prodefService.getAllProDef();
		} catch (WfmServiceException e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(pros);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
}
