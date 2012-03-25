package nc.uap.ctrl.tpl.qry.meta;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import nc.vo.querytemplate.QueryTemplateUtils;

/**
 * BLOB���󹤾�
 * <p>
 * </br>�ṩVO�еĶ�����ֽ�����֮���໥ת���Ĺ���
 * </br>��Ϊ�������ݿ����֧��BLOB�ֶ�ֱ�Ӵ�ȡ��������Ҫ�ȴ������ֽ�����
 * </br>ͬ�������ݿ���ȡ�����ֶ�ֵ(�ֽ�����)֮����Ҫת���ɶ���ſɹ����ó���ʹ��
 * <p><strong>ʵ�����ǽ�Java����ת����XML������ѹ�����γ����ֽ�����</strong>
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-10-20
 */
public class BlobUtil {

	/**
	 * ���ֽ�����ָ���Java����
	 * 
	 * @param bytes
	 *            �ֽ�����
	 */
	public static Object convert(byte[] bytes) {
		if (bytes != null) {
			StringBuilder sb = new StringBuilder();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new GZIPInputStream(bis)));
				String s;
				while ((s = reader.readLine()) != null) {
					sb.append(s);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String xml = sb.toString();
			return QueryTemplateUtils.readFromXML(xml);
		}
		return null;
	}
	
    /**
	 * ��Java����ת�����ֽ�����
	 * 
	 * @param object
	 *            Java����
	 */
	public static byte[] convert(Object object) {
		if(object == null) return null;
		String xml = QueryTemplateUtils.writeToXML(object);
		if (isEmpty(xml)) return null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gzos = new GZIPOutputStream(bos);
			gzos.write(xml.getBytes());
			gzos.flush();
			gzos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
    
    private static boolean isEmpty(Object object){
    	return object == null || object.toString().trim().length() == 0;
    }
}