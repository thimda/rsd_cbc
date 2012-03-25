package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-2 ÉÏÎç10:19:30 limingf
 */
@Servlet(path = "/servlet/getFormEntry") public class FormEntryServlet extends ExcuterServlet {
	private static final long serialVersionUID = -7558156809709671481L;
	public Document buildXml(Class<SuperVO>[] clazz) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Clazz");
		doc.appendChild(root);
		if (clazz == null || clazz.length < 1)
			return doc;
		int size = clazz.length;
		for (int i = 0; i < size; i++) {
			Class<SuperVO> cla = (Class<SuperVO>) clazz[i];
			Element node = doc.createElement("FormEntry");
			node.setAttribute("code", cla.getName());
			node.setAttribute("name", cla.getSimpleName());
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
		String proDefPk = request.getParameter("proDefPk");
		WfmFlwTypeVO flowTypeVo = ProDefsContainer.getByProDefPkAndId(proDefPk, null).getFlwtype();
		IWfmFormOper formOpera = (IWfmFormOper) WfmClassUtil.loadClass(flowTypeVo.getServerclass());
		Class<SuperVO>[] clazz = formOpera.getBizMetaDataDesc(flowTypeVo);
		Document doc = buildXml(clazz);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
	public Document buildXml(SuperVO[] vos) {
		return null;
	}
}
