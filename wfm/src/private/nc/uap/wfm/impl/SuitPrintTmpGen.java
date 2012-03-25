package nc.uap.wfm.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.engine.ISuitPrintTmpGen;
import nc.uap.wfm.model.FormData;
import nc.uap.wfm.vo.WfmSuitPrintVO;
import org.apache.commons.io.FileUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
public class SuitPrintTmpGen implements ISuitPrintTmpGen {

	public String genTmpGenStringUrl(FormData formData, WfmSuitPrintVO suitPrintVo) {
		String septor = "/";
		String url = "";
		String folderpath = WfmConstants.WebTmpFilePath + DblConstants.Path_FrmTmpPrint;
		File folder = new File(folderpath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String ftlfilepath = folderpath + septor + suitPrintVo.getPk_suitprint() + ".ftl";
		String html = suitPrintVo.getSuitprinttmp();
		File ftlFile = new File(ftlfilepath);
		try {
			FileUtils.writeStringToFile(ftlFile, html, "UTF-8");
		} catch (IOException e1) {
			LfwLogger.error(e1.getMessage(), e1);
			throw new LfwRuntimeException(e1.getMessage());
		}
		Map<String, Object> root = this.builderRootMap(formData);
		html = this.renderTmp(ftlfilepath, root);
		File file = new File(folderpath + septor + suitPrintVo.getPk_suitprint() + ".html");
		try {
			FileUtils.writeStringToFile(file, html, "UTF-8");
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		url = LfwRuntimeEnvironment.getRootPath() + "/webtemp/html/nodes/" + DblConstants.Path_FrmTmpPrint + septor + suitPrintVo.getPk_suitprint() + ".html?ts="+System.currentTimeMillis();
		return url;
	}
	private Map<String, Object> builderRootMap(FormData formData) {
		Map<String, Object> root = new HashMap<String, Object>();
		Map<String, String> frmIns = formData.getFrmIns();
		if (frmIns == null) {
			return root;
		}
		Set<String> keys = frmIns.keySet();
		Iterator<String> iter = keys.iterator();
		String key = null;
		while (iter.hasNext()) {
			key = iter.next();
			root.put(key, frmIns.get(key));
		}
		Map<String, List<Map<String, String>>> dynaRowData = formData.getDynaRowData();
		if (dynaRowData == null) {
			return root;
		}
		keys = dynaRowData.keySet();
		iter = keys.iterator();
		while (iter.hasNext()) {
			key = iter.next();
			root.put(key, dynaRowData.get(key).toArray(new Map[0]));
		}
		return root;
	}
	public String renderTmp(String filepath, Map<String, Object> root) {
		String folderpath = filepath.substring(0, filepath.lastIndexOf("/"));
		Configuration systemConfig = new Configuration();
		Reader reader = null;
		try {
			systemConfig.setDefaultEncoding("UTF-8");
			systemConfig.setDirectoryForTemplateLoading(new File(folderpath));
			reader = new InputStreamReader(new FileInputStream(new File(filepath)), "UTF-8");
			Template template = new Template(filepath, reader, systemConfig);
			Writer out = new StringWriter();
			template.process(root, out);
			return out.toString();
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
