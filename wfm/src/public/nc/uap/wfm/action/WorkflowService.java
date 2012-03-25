package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 获取流程定义servlet 2010-9-19 下午01:52:33 limingf
 */
@Servlet(path = "/servlet/workflowService") public class WorkflowService extends WfBaseServlet {
	private static final long serialVersionUID = -2227036470921130845L;
	private WfmProdefVO getWfXmlByPk(String pk) {
		IWfmProDefQry qry = NCLocator.getInstance().lookup(IWfmProDefQry.class);
		WfmProdefVO vo = null;
		try {
			vo = qry.getProDefVOByProDefPk(pk);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return vo;
	}
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		String pk = request.getParameter("proDefPk");
		WfmProdefVO wf = getWfXmlByPk(pk);
		if (wf == null) {
			return;
		}
		if (wf.getProcessstr() == null) {
			return;
		}
		String xml = wf.getProcessstr();
		out.println(xml);
	}
}
