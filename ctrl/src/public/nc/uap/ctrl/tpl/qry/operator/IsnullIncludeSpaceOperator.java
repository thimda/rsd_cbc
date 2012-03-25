package nc.uap.ctrl.tpl.qry.operator;

import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.value.IFieldValue;

/**
 * �����մ���null������
 * @author huangzg 2007-4-25
 *
 */
public class IsnullIncludeSpaceOperator extends IsnullOperator {

	private static final long serialVersionUID = 3253547412292428704L;
	
	private static final IsnullIncludeSpaceOperator INSTANCE = new IsnullIncludeSpaceOperator();

	public static IsnullIncludeSpaceOperator getInstance() {
		return INSTANCE;
	}

	@Override
	public String getOperatorCode() {
		return IOperatorConstants.ISNULL_INCLUDE_SPACE;
	}

	@Override
	public String getSQLString(FilterMeta meta, IFieldValue value) {
		String sql = super.getSQLString(meta, value);
		return "(" + sql + " or " + "len(" + meta.getFieldCode() + ")=0)";
	}

}
