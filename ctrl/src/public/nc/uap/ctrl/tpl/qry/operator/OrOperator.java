package nc.uap.ctrl.tpl.qry.operator;

import nc.rule.tools.PredicateConstants;
import nc.vo.ml.NCLangRes4VoTransl;

public class OrOperator implements ILogicalOperator {
	
	public static String  OR_i18n(){
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_template", "UPP_NewQryTemplate-0031");//"Лђеп"
	}
	
	private static final long serialVersionUID = 5247576263364233299L;
	private static OrOperator instance = new OrOperator();
	private OrOperator()
	{
		
	}
	public static OrOperator getInstance()
	{
		return instance;
	}
	@Override
	public String toString() {
		return OR_i18n();
	}
	private Object readResolve()
	{
		return instance;
	}
	public String getLogicOperatorCode() {
		return PredicateConstants.STRING_OR;
	}
}
