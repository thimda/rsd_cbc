package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 2010-11-2 ÉÏÎç10:19:30 limingf
 */
@Servlet(path = "/servlet/getFormItem") public class FormItemServlet extends ExcuterServlet {
	private static final long serialVersionUID = -7558156809709671481L;
	public Document buildXml(IBean bean) {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("FormItems");
		doc.appendChild(root);
		List<IAttribute> attributes = bean.getAttributes();
		if (attributes == null || attributes.size() == 0)
			return doc;
		int size = attributes.size();
		IAttribute attri = null;
		for (int i = 0; i < size; i++) {
			attri = attributes.get(i);
			Element node = doc.createElement("FormItem");
			node.setAttribute("code", attri.getColumn().getName());
			node.setAttribute("name", attri.getDisplayName());
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
		String clazz = request.getParameter("clazz");
		IBean bean = null;
		try {
			bean = nc.md.MDBaseQueryFacade.getInstance().getBeanByFullClassName(clazz);
		} catch (MetaDataException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		Document doc = buildXml(bean);
		XMLUtil.printDOMTree(out, doc, 0, "UTF-8");
		out.println();
	}
	public Document buildXml(SuperVO[] vos) {
		return null;
	}
}
