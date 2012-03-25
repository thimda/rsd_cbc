package nc.uap.wfm.expression;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmSelfFunVO;
public class LogicParser {
	// "(${0000Z010000000005FZO.wjfl}=='44545')&&(${0000Z01000000005FZO.sfg}>='789')||(#{test}=#{test1})";
	public static String getExpressionValue(Task task, SequenceFlow sf, WfmFormInfoCtx formVo) throws Exception {
		String expression = sf.getCondition();
		expression = LogicParser.parseVar(task, sf, formVo, expression);
		expression = LogicParser.parseFun(task, sf, formVo, expression);
		return expression;
	}
	public static String parseVar(Task task, SequenceFlow sf, WfmFormInfoCtx formVo, String expression) {
		Map<String, WfmFormInfoCtx> valueMap = LogicParser.getVarValueMap(task, sf, formVo);
		char[] chs = expression.toCharArray();
		String formVoName = "";
		String frmField = "";
		String value = "";
		int startIndex = 0;
		int middleIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '$') {
				startIndex = i;
				while (chs[i] != '}') {
					i++;
				}
				endIndex = i;
				frmField = expression.substring(startIndex + 2, endIndex);
				middleIndex = frmField.lastIndexOf(".");
				formVoName = frmField.substring(0, middleIndex);
				frmField = frmField.substring(middleIndex, endIndex);
				value = LogicParser.getVarValue(valueMap, formVoName, frmField);
				expression = LogicParser.replace(expression, startIndex, endIndex + 1, value);
				formVoName = "";
				frmField = "";
			}
		}
		expression = expression.replaceAll("\'", "");
		expression = expression.replaceAll(" ", "");
		return expression;
	}
	public static String parseFun(Task task, SequenceFlow sf, WfmFormInfoCtx formVo, String expression) {
		char[] chs = expression.toCharArray();
		String funName = "";
		String value = "";
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '#') {
				startIndex = i;
				i = i + 2;
				while (chs[i] != '}') {
					i++;
				}
				endIndex = i;
				funName = expression.substring(startIndex + 2, endIndex);
				value = LogicParser.getFunValue(task, sf, formVo, funName);
				expression = LogicParser.replace(expression, startIndex, endIndex + 1, value);
				if (expression.length() > chs.length) {
					expression = LogicParser.parseFun(task, sf, formVo, expression);
					break;
				}
				funName = "";
			}
		}
		expression = expression.replaceAll("\'", "");
		expression = expression.replaceAll(" ", "");
		return expression;
	}
	public static String getFunValue(Task task, SequenceFlow sf, WfmFormInfoCtx formVo, String funName) {
		List<WfmSelfFunVO> list = CpbUtil.queryList("select * from wfm_selffunc where funname='" + funName + "'", null, WfmSelfFunVO.class);
		if (list == null) {
			throw new LfwRuntimeException("函数" + funName + "未注册！");
		}
		WfmSelfFunVO vo = CpbUtil.returnValue(list);
		String clazzName = vo.getClazz();
		if (clazzName == null || clazzName.trim().length() == 0) {
			throw new LfwRuntimeException("函数" + funName + "未注册实现类！");
		}
		ISelfFunction function = (ISelfFunction) WfmClassUtil.loadClass(clazzName);
		String value = function.exec(task, sf, formVo);
		return value;
	}
	/**
	 * 获取变量值
	 * 
	 * @param map
	 * @param flwTypePk
	 * @param frmField
	 * @return
	 */
	public static String getVarValue(Map<String, WfmFormInfoCtx> map, String formVoName, String frmField) {
		if (map == null) {
			return "";
		}
		WfmFormInfoCtx frmIns = map.get(formVoName);
		if (frmIns == null) {
			return "";
		}
		String value = String.valueOf(frmIns.getAttributeValue(frmField));
		return value;
	}
	/**
	 * 获取变量的Map
	 * 
	 * @param task
	 * @param sf
	 * @param formVo
	 * @return
	 */
	public static Map<String, WfmFormInfoCtx> getVarValueMap(Task task, SequenceFlow sf, WfmFormInfoCtx formVo) {
		Map<String, WfmFormInfoCtx> valueMap = new HashMap<String, WfmFormInfoCtx>();
		valueMap.put(formVo.getClass().getName(), formVo);
		if (task == null) {
			return valueMap;
		}
		return valueMap;
	}
	/**
	 * 特殊的值替换，保持长度一致
	 * 
	 * @param str
	 * @param startIndex
	 * @param endIndex
	 * @param target
	 * @return
	 */
	public static String replace(String str, int startIndex, int endIndex, String target) {
		StringBuffer sb = new StringBuffer();
		sb.append(str.substring(0, startIndex));
		sb.append(target);
		if (endIndex - startIndex - target.length() >= 0) {
			for (int i = 0; i < endIndex - startIndex - target.length(); i++) {
				sb.append(" ");
			}
		}
		sb.append(str.substring(endIndex, str.length()));
		return sb.toString();
	}
	public static void main(String args[]) {}
}
