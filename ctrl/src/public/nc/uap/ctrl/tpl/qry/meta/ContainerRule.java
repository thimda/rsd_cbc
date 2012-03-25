package nc.uap.ctrl.tpl.qry.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public abstract class ContainerRule implements IRule {
	private static final long serialVersionUID = -132585563803603535L;

	/**
	 * ��������.����And.Or.Deductive
	 */
	public abstract String getContainerCode();

	List<IRule> rules = new ArrayList<IRule>();// And�ļ�����

	/**
	 * ��ȡ������Ĺ���
	 * 
	 * @return
	 */
	public IRule[] getChildrenRules() {
		return (IRule[]) rules.toArray(new IRule[0]);
	}

	public List<IRule> getRules() {
		return rules;
	}

	public boolean addRule(IRule rule) {
		return rules.add(rule);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();		
		sb.append("<ul><i><b>"+getContainerCode()+"</i></b>");
		List<IRule> rules = getRules();	
		for (IRule rule : rules) {			
			sb.append(rule.toString());
		}		
		sb.append("</ul>"); 
		return sb.toString();
	}

}
