package nc.uap.wfm.action;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import org.apache.commons.io.FileUtils;
/**
 * 导出xml文件，先将xml文件保存到服务器上的临时文件里，再下载到本地 2010-11-19 上午10:31:03
 * 
 * @author limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/outputXmlServlet") public class OutputXmlServlet extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings("deprecation") @Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		ServletInputStream input = request.getInputStream();
		String filename = request.getParameter("filename");
		if (filename == null || "".equals(filename))
			filename = UUID.randomUUID().toString();
		filename += ".xml";
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);
		String savePath = request.getRealPath("") + "/processxml/" + filename;
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
		download(request, response, filename);
	}
	/**
	 * 下载到用户本地
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation") public void download(HttpServletRequest request, HttpServletResponse response, String fileName) throws ServletException, IOException {
		String fullPath = request.getRealPath("") + "/processxml/" + fileName;
		File f = new File(fullPath);
		if (!f.exists()) {
			response.sendError(404, "File not save success!");
			return;
		}
		response.reset(); // 非常重要
		// 下载
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		byte[] buf = new byte[1024];
		int len = 0;
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(new FileInputStream(f));
			out = response.getOutputStream();
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		} finally {
			if (br != null) {
				br.close();
				br = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
}