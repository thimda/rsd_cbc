package nc.uap.wfm.action;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * 上传xml文件到WorkFlow.xml 2010-11-19 上午10:25:45
 * 
 * @author limingf
 */
@SuppressWarnings("restriction") @Servlet(path = "/servlet/uploadController") public class UploadController extends WfBaseServlet {
	private static final long serialVersionUID = -5603687395645617927L;
	@SuppressWarnings( { "deprecation", "unchecked" }) @Action(method = "POST") public void doPost() throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String savePath = request.getRealPath("") + "/processxml/";
		// savePath = savePath + "/";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		Iterator<FileItem> it = fileList.iterator();
		String filename = "WorkFlow.xml";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				/*
				 * name = item.getName(); if (name == null ||
				 * name.trim().equals("")) { continue; } // 扩展名格式： if
				 * (name.lastIndexOf(".") >= 0) { extName =
				 * name.substring(name.lastIndexOf(".")); }
				 */
				savePath = savePath + "/";
				File f1 = new File(savePath);
				if (!f1.exists()) {
					f1.mkdirs();
				}
				File saveFile = new File(savePath + filename);
				try {
					item.write(saveFile);
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
					throw new LfwRuntimeException(e.getMessage());
				}
			}
		}
		response.getWriter().print(filename + " 上传成功");
	}
}