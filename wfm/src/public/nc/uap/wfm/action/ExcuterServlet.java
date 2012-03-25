package nc.uap.wfm.action;
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
import nc.vo.pub.SuperVO;
import org.w3c.dom.Document;
/**
 * 2010-11-2 ÉÏÎç10:06:49 limingf
 */
public abstract class ExcuterServlet extends WfBaseServlet {
	private static final long serialVersionUID = -784588174841545334L;
	/**
	 * Document to String
	 * 
	 * @param doc
	 * @return
	 */
	public String docToString(Document doc) {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult xmlResult = new StreamResult(writer);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer = null;
		try {
			transFormer = transFactory.newTransformer();
			transFormer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transFormer.transform(domSource, xmlResult);
		} catch (TransformerConfigurationException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		} catch (TransformerException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return writer.toString();
	}
	public abstract Document buildXml(SuperVO[] vos);
}
