package nc.uap.wfm.expression;
/**
 * �����Ա��ʽ��������
 * @author tianchw
 *
 */
public class Expression {
	private String expression;
	public Expression(String expression) {
		super();
		this.expression = expression;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public boolean evaluate() {
		return true;
	}
}
