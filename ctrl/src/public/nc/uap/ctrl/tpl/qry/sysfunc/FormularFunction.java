package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.querytemplate.formular.QTFormularParser;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * 通过公式实现的系统函数
 * 
 * @author 刘晨伟
 * 
 * 创建日期：2010-10-12
 */
public abstract class FormularFunction implements ISysFunction {

	// 公式解析器
	private FormulaParse formulaParser = QTFormularParser.getInstance().getParser();
	
	private String code;
	private String resid;
	
	/**
	 * @param expression
	 *            公式表达式
	 * @param resid
	 *            函数名称多语资源号
	 */
	public FormularFunction(String expression, String resid) {
		this.code = TOKEN + expression + TOKEN;
		this.resid = resid;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template", resid);
	}

	/**
	 * 公式计算出结果对象
	 */
	protected Object calculate() {
		String express = code.replace(TOKEN, "");
		formulaParser.setExpress(express);
		return formulaParser.getValueAsObject();
	}
}