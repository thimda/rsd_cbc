package nc.uap.ctrl.tpl.qry.sysfunc;

import java.io.Serializable;

import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.value.SysFunctionManager;

/**
 * ϵͳ����
 * 
 * @author ����ΰ
 *
 * �������ڣ�2009-10-22
 */
public class SystemFunction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -351763330732452674L;
	
	private String expression;// �������ʽ
	
	public SystemFunction(String express){
		this.expression = express;
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * ���غ���ֵ
	 */
	public RefResultVO getFunctionValue() {
		return SysFunctionManager.getInstance().calculate(expression);
	}
	
	public String getName() {
		return SysFunctionManager.getInstance().getNameByCode(expression);
	}
	
	/**
	 * �жϲ����Ƿ���ϵͳ����
	 */
	public static boolean isFunction(String expression) {
		if (expression == null) return false;
		String value = expression.trim();
		if (value.startsWith(ISysFunction.TOKEN) && value.endsWith(ISysFunction.TOKEN)) {
			return true;
		}// �϶����Ǻ��������ˣ��²��Ǻ�������
		String functionCode = SysFunctionManager.getInstance().getCodeByName(expression);
		return functionCode != null;
	}
}