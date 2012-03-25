package nc.uap.wfm.utils;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import org.w3c.dom.Document;
public class WfmXmlUtil {
	public static String docToString(Document doc) {
		StringWriter writer = new StringWriter();
		StreamResult xmlResult = new StreamResult(writer);
		Transformer transFormer = null;
		try {
			transFormer = TransformerFactory.newInstance().newTransformer();
			transFormer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transFormer.transform(new DOMSource(doc), xmlResult);
		} catch (TransformerConfigurationException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		} catch (TransformerException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return writer.toString();
	}
}
