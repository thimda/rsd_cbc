package nc.uap.wfm.action;
import java.io.IOException;
import java.io.PrintWriter;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.wfm.message.TaskMessageGatherUtil;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 消息提醒方式 2010-11-25 上午09:44:54 limingf
 */
@Servlet(path = "/servlet/reminderServlet") public class ReminderServlet extends WfBaseServlet {
	private static final long serialVersionUID = -1912478494778271618L;
	public Document buildRoot() {
		Document doc = XMLUtil.getNewDocument();
		Element root = doc.createElement("Root");
		doc.appendChild(root);
		return doc;
	}
	public Document buildXml(Document doc, SuperVO[] vos, String type) {
		Element reminders = doc.createElement("Reminders");
		reminders.setAttribute("type", type);
		doc.getFirstChild().appendChild(reminders);
		if (vos == null || vos.length < 1)
			return doc;
		for (int i = 0; i < vos.length; i++) {
			PtExtension vo = (PtExtension) vos[i];
			Element node = doc.createElement("Reminder");
			node.setAttribute("id", vo.getId());
			node.setAttribute("name", vo.getTitle());
			reminders.appendChild(node);
		}
		return doc;
	}
	@SuppressWarnings("restriction") @Action(method = "POST") public void doPost() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		Document root = buildRoot();
		buildXml(root, TaskMessageGatherUtil.getTaskCreatedSenderType(), "taskCreated");
		buildXml(root, TaskMessageGatherUtil.getTaskCompletedSenderType(), "taskCompleted");
		buildXml(root, TaskMessageGatherUtil.getTaskOverTimeSenderType(), "overtime");
		root.toString();
		XMLUtil.printDOMTree(out, root, 0, "UTF-8");
		out.println();
	}
}
