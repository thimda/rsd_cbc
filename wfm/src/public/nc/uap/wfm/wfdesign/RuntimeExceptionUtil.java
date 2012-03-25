package nc.uap.wfm.wfdesign;

import java.util.Arrays;

import nc.uap.lfw.core.exception.LfwRuntimeException;

/**
 * 2010-12-29 обнГ02:25:09  limingf
 */

public class RuntimeExceptionUtil {
	public static String getMessage(LfwRuntimeException e){
		String errmsg=e.getMessage();
		StringBuffer sb=new StringBuffer();
		String[] str=errmsg.replaceAll("<br>", "").split("\r\n");
		sb.append("var str=encodeURIComponent('"+Arrays.asList(str).toString()+"');\r\n");
		sb.append("alert(decodeURIComponent(str));");
		return sb.toString();
	}

}
