package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IPort;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.jcom.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-3 ÉÏÎç09:18:19 limingf
 */
@Servlet(path = "/servlet/saveProDefServlet") public class SaveProDefServlet extends WfBaseServlet {
	private static final long serialVersionUID = 856521354399862503L;
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		String proDefXml = request.getParameter("prodefxml");
		try {
			proDefXml = URLDecoder.decode(proDefXml, "UTF-8");
			System.out.println(proDefXml);
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		String proDefPk = request.getParameter("prodefpk");
		//proDefPk="0000Z7100000000089UZ";
		try {
			WfmProdefVO proDefVo = WfmServiceFacility.getProDefQry().getProDefVOByProDefPk(proDefPk);
			proDefVo.setProcessstr(proDefXml);
			ProDef proDef = ProDefsContainer.getProDef(proDefVo);
			IPort stratPort = WfmTaskUtil.getStratPort(proDef);
			if (stratPort instanceof HumAct) {
				HumAct humAct = (HumAct) stratPort;
				String frmDefPk = humAct.getPk_formdefinition();
				proDefVo.setPk_startfrm(frmDefPk);
			}
			WfmServiceFacility.getProDefBill().updateProdef(proDefVo);
			ProDefsContainer.parseProDef(proDefVo);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		Document document = this.buildXml("true");
		XMLUtil.printDOMTree(out, document, 0, "UTF-8");
		out.println();
		System.out.println(document.toString());
	}
	public Document buildXml(String result) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Root");
		doc.appendChild(root);
		Element node = doc.createElement("Result");
		node.setTextContent(result);
		root.appendChild(node);
		return doc;
	}
}
