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
 * ��չ���ƶ��������
 * 
 * @author licza
 * @2010��9��9��14:59:03
 */
public class PluginDefParser {
	/**
	 * ��������
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
			LfwLogger.error("�������ʧ��", e);
		}
		return null;
	}

	/**
	 * ����xml�ļ�
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
			LfwLogger.error("��չ������!", e);
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
