package nc.uap.wfm.action;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import org.apache.commons.io.FileUtils;
@SuppressWarnings("restriction") @Servlet(path = "/servlet/saveXmlToFileServlet") public class SaveXmlToFileServlet extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings("deprecation") @Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		ServletInputStream input = request.getInputStream();
		String filename = request.getParameter("filename");
		if (filename == null || "".equals(filename))
			filename = UUID.randomUUID().toString();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);
		String savePath = request.getRealPath("") + "/processxml/" + filename + ".xml";
		File file = new File(savePath);
		String line = br.readLine();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		while (line != null) {
			String str = URLDecoder.decode(line, "utf-8");
			sb.append(str).append("\r\n");
			line = br.readLine();
		}
		FileUtils.writeStringToFile(file, sb.toString(), "UTF-8");
		br.close();
	}
}