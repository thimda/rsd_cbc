package nc.uap.wfm.action;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
/**
 * 导出图片到本地 2010-11-19 上午10:34:01
 * 
 * @author limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/outputImageServlet") public class OutputImageServlet extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings("deprecation") @Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setContentType("application/octet-stream");
		String filename = request.getParameter("filename");
		String filetype = request.getParameter("imagetype");
		if (filename == null || "".equals(filename))
			filename = UUID.randomUUID().toString();
		filename += "." + filetype;
		String savePath = request.getRealPath("") + "/processxml/images/" + filename;
		InputStream is = request.getInputStream();
		DataOutputStream dos = null;
		try {
			int size = 0;
			byte[] tmp = new byte[1024 * 10];
			// 创建一个文件夹用来保存发过来的图片；
			File f = new File(savePath);
			dos = new DataOutputStream(new FileOutputStream(f));
			int len = -1;
			while ((len = is.read(tmp)) != -1) {
				dos.write(tmp, 0, len);
				size += len;
			}
			dos.flush();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		} finally {
			if (dos != null) {
				dos.close();
			}
		}
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
		String fullPath = request.getRealPath("") + "/processxml/images/" + fileName;
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