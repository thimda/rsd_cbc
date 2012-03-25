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
 * BLOB对象工具
 * <p>
 * </br>提供VO中的对象和字节数组之间相互转换的功能
 * </br>因为现有数据库服务不支持BLOB字段直接存取，所以需要先传换成字节数组
 * </br>同样从数据库中取出该字段值(字节数组)之后需要转换成对象才可供调用程序使用
 * <p><strong>实际上是将Java对象转换成XML串后再压缩才形成了字节数组</strong>
 * 
 * @author 刘晨伟
 * 
 * 创建日期：2010-10-20
 */
public class BlobUtil {

	/**
	 * 将字节数组恢复成Java对象
	 * 
	 * @param bytes
	 *            字节数组
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
	 * 将Java对象转换成字节数组
	 * 
	 * @param object
	 *            Java对象
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