package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.wfm.dftimpl.DefaultFormOper;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.parse.ProcessParser;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.jcom.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
@Servlet(path = "/PaWfm2") public class PaWfmAction2 extends BaseAction {
	@SuppressWarnings("restriction") @Action(method = "POST") public void open() {
		String proDefXml = request.getParameter("prodefxml");
		try {
			proDefXml = URLDecoder.decode(proDefXml, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		ProcessParser parse = ProcessParser.getInstance();
		try {
			ProDef proDef = parse.parse(proDefXml);
			String portId = request.getParameter("port_id");
			HumAct humAct = (HumAct) proDef.getPorts().get(portId);
			String proDefPk = request.getParameter("pk_prodef");
			WfmProdefVO proDefVo = WfmServiceFacility.getProDefQry().getProDefVOByProDefPk(proDefPk);
			String flowTypePk = proDefVo.getFlwtype();
			WfmFlwTypeVO flowTypeVo = WfmServiceFacility.getFlwTypeQry().getFlwTypeVoByPk(flowTypePk);
			String serverClazz = flowTypeVo.getServerclass();
			if (serverClazz == null || serverClazz.length() == 0) {
				serverClazz = DefaultFormOper.class.getName();
			}
			IWfmFormOper formOper = (IWfmFormOper) WfmClassUtil.loadClass(serverClazz);
			String url = formOper.getExtAttrSettingUrl(humAct);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
			Document document = this.buildXml(url);
			XMLUtil.printDOMTree(out, document, 0, "UTF-8");
			out.println();
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public Document buildXml(String url) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Root");
		doc.appendChild(root);
		Element node = doc.createElement("Url");
		node.setTextContent(url);
		root.appendChild(node);
		return doc;
	}
}
