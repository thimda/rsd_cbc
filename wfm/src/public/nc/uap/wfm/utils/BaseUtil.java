package nc.uap.wfm.utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import org.apache.commons.lang.StringUtils;
/**
 * ������
 * 
 */
public class BaseUtil {
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNull(String str) {
		return str == null || str.trim().length() <= 0 || str.toLowerCase().equals("null");
	}
	/**
	 * �ж������Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNull(Object[] os) {
		return os == null || os.length == 0;
	}
	/**
	 * �жϼ����Ƿ�Ϊ��
	 * 
	 * @param cs
	 * @return
	 */
	public static final boolean isNull(Collection<?> cs) {
		return cs == null || cs.isEmpty();
	}
	/**
	 * �ж��ַ����Ƿ�����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumbic(String str) {
		return (StringUtils.isNotBlank(str) && StringUtils.isNumeric(str));
	}
	/**
	 * �ַ�����󳤶�����
	 * 
	 * @param str
	 * @param length
	 * @param ext
	 * @return
	 */
	public static final String maxStr(String str, int length, String ext) {
		if (StringUtils.isBlank(str) || str.length() <= length)
			return str;
		else {
			return str.substring(0, length) + ext;
		}
	}
	/**
	 * �ַ�����󳤶�����
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static final String maxStr(String str, int length) {
		boolean isIpad = LfwRuntimeEnvironment.getBrowserInfo().isIpad();
		return maxStr(str, isIpad ? length - 4 : length, "...");
	}
	/**
	 * �Ƿ�IE
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction") public static final boolean isIE() {
		String agent = LfwRuntimeEnvironment.getWebContext().getRequest().getHeader("user-agent");
		return StringUtils.isNotBlank(agent) && agent.indexOf("MSIE") > 0;
	}
	/**
	 * ��superVO����ת����aggVO
	 * 
	 * @param vos
	 * @return
	 */
	public static final AggregatedValueObject[] setSuperVO2AggVOParent(List<? extends SuperVO> vos, int status) {
		List<AggregatedValueObject> aggvolist = new ArrayList<AggregatedValueObject>();
		if (vos != null && vos.size() > 0) {
			for (SuperVO vo : vos) {
				AggregatedValueObject agg = new LfwExAggVO();
				agg.setParentVO(vo);
				vo.setStatus(status);
				aggvolist.add(agg);
			}
		}
		return aggvolist.toArray(new AggregatedValueObject[0]);
	}
}
