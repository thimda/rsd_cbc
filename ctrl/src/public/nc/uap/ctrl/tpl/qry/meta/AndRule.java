package nc.uap.ctrl.tpl.qry.meta;

import nc.rule.tools.PredicateConstants;

/**
 * And πÊ‘Ú
 * @author huangzg
 * 
 */
public class AndRule extends ContainerRule {

	private static final long serialVersionUID = 79122814953707688L;

	public AndRule() {
		super();
	}

	@Override
	public String getContainerCode() {
		return PredicateConstants.STRING_AND;
	}
}
