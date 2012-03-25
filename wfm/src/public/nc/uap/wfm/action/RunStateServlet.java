package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.render.FlowImgRender;
import nc.vo.jcom.xml.XMLUtil;
/**
 * 获取流程执行状态信息 2011-1-18 上午09:12:30 limingf
 */
@Servlet(path = "/servlet/runStateServlet") public class RunStateServlet extends WfBaseServlet {
	private static final long serialVersionUID = -8356983652899198379L;
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		String proInsPk = request.getParameter("proInsPk");
		if (proInsPk == null || proInsPk.length() == 0) {
			out.println();
		} else {
			XMLUtil.printDOMTree(out, FlowImgRender.getRenderProcessXml(proInsPk), 0, "UTF-8");
			// XMLUtil.printDOMTree(out, "", 0, "UTF-8");
		}
	}
}
