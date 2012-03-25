package nc.uap.ctrl.tpl.qry.operator;

import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFieldValue;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;

public class IsnotnullOperator extends NoneParaOperator {

	private static final long serialVersionUID = 3253547401202428704L;

	private static final IsnotnullOperator INSTANCE = new IsnotnullOperator();

	public static IsnotnullOperator getInstance() {
		return INSTANCE;
	}

	@Override
	public String toString() {
		//��Ϊ��
		return IOperatori18n.getISNotNull_Desc();
	}
	@Override
	public String getOperatorCode() {
		return IOperatorConstants.ISNOTNULL;
	}

	public String getSQLString(FilterMeta meta, IFieldValue value) {
		String columnName = meta.getSQLFieldCode();
		switch (meta.getDataType()) {
		case IQueryConstants.UFREF:// ����
			return columnName + " <> '~'";
		case IQueryConstants.INTEGER:// ��ֵ
		case IQueryConstants.DECIMAL:
			return "isnull(cast(" + columnName + " as char), '~') <> '~'";
		default:
			return "isnull(" + columnName + ", '~') <> '~'";
		}
	}
}
