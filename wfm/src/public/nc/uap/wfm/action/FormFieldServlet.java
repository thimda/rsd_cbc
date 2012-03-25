package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmItmQry;
import nc.uap.dbl.itf.IFrmTmpQry;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-25 ÉÏÎç09:44:54 limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/formFieldServlet") public class FormFieldServlet extends WfBaseServlet {
	private static final long serialVersionUID = -1912478494778271618L;
	public Document buildXml(SuperVO[] vos) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Form");
		doc.appendChild(root);
		if (vos == null || vos.length < 1)
			return doc;
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			DblFormItemVO item = (DblFormItemVO) vos[i];
			Element node = doc.createElement("Field");
			node.setAttribute("text", item.getNamezh());
			node.setAttribute("field", item.getOccupydomain());
			root.appendChild(node);
		}
		doc.toString();
		return doc;
	}
	@Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String pk_formdefinition = request.getParameter("pk_formdefinition");
		DblFormItemVO[] items = new DblFormItemVO[] {};
		try {
			items = getFormItemVO(pk_formdefinition);
		} catch (DblServiceException e) {
			throw new LfwRuntimeException(e);
		}
		Document doc = buildXml(items);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
	public DblFormItemVO[] getFormItemVO(String pk_formdefinition) throws DblServiceException {
		DblFormTemplateVO tempvo = null;
		DblFormItemVO[] itemvos = null;
		try {
			IFrmItmQry frmItmQry = NCLocator.getInstance().lookup(IFrmItmQry.class);
			IFrmTmpQry frmTmpQry = NCLocator.getInstance().lookup(IFrmTmpQry.class);
			tempvo = frmTmpQry.getFrmTmpByFrmDefPk(pk_formdefinition);
			if (tempvo != null)
				itemvos = frmItmQry.getOldFrmItmsByFrmTmpPk(tempvo.getPk_formtemplate());
		} catch (DblServiceException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
		return itemvos;
	}
}
