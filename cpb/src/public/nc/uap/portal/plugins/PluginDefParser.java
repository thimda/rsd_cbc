package nc.uap.portal.plugins;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.model.PtPlugin;

import org.apache.commons.io.FileUtils;

/**
 * 扩展机制定义解析类
 * 
 * @author licza
 * @2010年9月9日14:59:03
 */
public class PluginDefParser {
	/**
	 * 解析输入
	 * 
	 * @param reader
	 * @return
	 */
	public static PtPlugin parser(Reader reader) {
		try {
			JAXBContext jc = JAXBContext.newInstance(PtPlugin.class);
			Unmarshaller um = jc.createUnmarshaller();
			return (PtPlugin) um.unmarshal(reader);
		} catch (Exception e) {
			LfwLogger.error("插件解析失败", e);
		}
		return null;
	}

	/**
	 * 解析xml文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static PtPlugin reader(String filePath) {
		Reader reader = null;
		try {
			File f = new File(filePath);
			if(!f.exists())
				return null;
			String xmlText = FileUtils.readFileToString(new File(filePath), "UTF-8");
			reader = new StringReader(xmlText);
			return parser(reader);
		} catch (IOException e) {
			LfwLogger.error("扩展不存在!", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				LfwLogger.error("Reader close failed", e);
			}
		}
		return null;
	}

}
