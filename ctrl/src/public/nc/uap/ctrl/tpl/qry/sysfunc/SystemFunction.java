package nc.uap.ctrl.tpl.qry.sysfunc;

import java.io.Serializable;

import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.value.SysFunctionManager;

/**
 * 系统函数
 * 
 * @author 刘晨伟
 *
 * 创建日期：2009-10-22
 */
public class SystemFunction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -351763330732452674L;
	
	private String expression;// 函数表达式
	
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
	 * 返回函数值
	 */
	public RefResultVO getFunctionValue() {
		return SysFunctionManager.getInstance().calculate(expression);
	}
	
	public String getName() {
		return SysFunctionManager.getInstance().getNameByCode(expression);
	}
	
	/**
	 * 判断参数是否是系统函数
	 */
	public static boolean isFunction(String expression) {
		if (expression == null) return false;
		String value = expression.trim();
		if (value.startsWith(ISysFunction.TOKEN) && value.endsWith(ISysFunction.TOKEN)) {
			return true;
		}// 肯定不是函数编码了，猜测是函数名称
		String functionCode = SysFunctionManager.getInstance().getCodeByName(expression);
		return functionCode != null;
	}
}