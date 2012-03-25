package nc.uap.wfm.action;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.wfm.gui.DrawBoard;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 2011-5-25 ����02:33:39 limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/workflowImageServlet") public class WorkflowImageServlet extends WfBaseServlet {
	@Action public void doPost() {
		// ����ҳ�治����
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String wfpk = request.getParameter("wfpk");
		String proInsPk = request.getParameter("proInsPk");
		DrawBoard drawboard = new DrawBoard();
		BufferedImage image = drawboard.getWorkflowImage(wfpk, proInsPk);
		// ���ͼ���ļ�
		// ImageIO.write(image, "JPEG", new File("D://workflow.jpg"));
		// ImageIO.write(image, "JPEG", response.getOutputStream());
		try {
			ServletOutputStream sos = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
			encoder.encode(image);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("��ȡ����ͼƬ����");
		}
	}
}
