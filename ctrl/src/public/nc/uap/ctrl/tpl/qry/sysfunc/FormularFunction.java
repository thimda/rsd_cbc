package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.querytemplate.formular.QTFormularParser;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * ͨ����ʽʵ�ֵ�ϵͳ����
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-10-12
 */
public abstract class FormularFunction implements ISysFunction {

	// ��ʽ������
	private FormulaParse formulaParser = QTFormularParser.getInstance().getParser();
	
	private String code;
	private String resid;
	
	/**
	 * @param expression
	 *            ��ʽ���ʽ
	 * @param resid
	 *            �������ƶ�����Դ��
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
	 * ��ʽ������������
	 */
	protected Object calculate() {
		String express = code.replace(TOKEN, "");
		formulaParser.setExpress(express);
		return formulaParser.getValueAsObject();
	}
}