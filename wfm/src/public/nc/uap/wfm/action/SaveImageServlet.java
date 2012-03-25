package nc.uap.wfm.action;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.servlet.ServletException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
/**
 * 保存图片 2011-2-24 上午09:13:13
 * 
 * @author limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/saveImageServlet") public class SaveImageServlet extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings("deprecation") @Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setContentType("application/octet-stream");
		String filename = request.getParameter("filename");
		if (filename == null || "".equals(filename))
			filename = UUID.randomUUID().toString();
		filename += ".png";
		String savePath = request.getRealPath("") + "/processxml/images/" + filename;
		InputStream is = request.getInputStream();
		try {
			int size = 0;
			byte[] tmp = new byte[1024 * 10];
			// 创建一个文件夹用来保存发过来的图片；
			File f = new File(savePath);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
			int len = -1;
			while ((len = is.read(tmp)) != -1) {
				dos.write(tmp, 0, len);
				size += len;
			}
			dos.flush();
			dos.close();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}