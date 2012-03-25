package nc.uap.wfm.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
/**
 * 把导出的xml文件写到输出流,可以通过其他工具来进行下载（flex），而不是通过servlet下载到本地 2010-11-19 上午10:27:34
 * 
 * @author limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/downloadController") public class DownloadController extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings("deprecation") @Action(method = "POST") public void doPost() throws ServletException, IOException {
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		String filename = request.getRealPath("") + "/processxml/temp.xml";
		try {
			bis = new BufferedInputStream(new FileInputStream(filename));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null) {
				bos.flush();
				bos.close();
				bos = null;
			}
		}
		response.flushBuffer();
	}
}