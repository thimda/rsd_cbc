package nc.uap.ctrl.tpl.qry.operator;

import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.value.IFieldValue;
import nc.vo.pub.query.IQueryConstants;

public class IsnullOperator extends NoneParaOperator {

	private static final long serialVersionUID = 3253547401202428704L;


	private static final IsnullOperator INSTANCE = new IsnullOperator();

	public static IsnullOperator getInstance() {
		return INSTANCE;
	}

	public IsnullOperator() {}
	
	public String getSQLString(FilterMeta meta, IFieldValue value) {
		String columnName = meta.getSQLFieldCode();
		switch (meta.getDataType()) {
		case IQueryConstants.UFREF:// 参照
			return columnName + " = '~'";
		case IQueryConstants.INTEGER:// 数值
		case IQueryConstants.DECIMAL:
			return "isnull(cast(" + columnName + " as char), '~') = '~'";
		default:
			return "isnull(" + columnName + ", '~') = '~'";
		}
	}

	@Override
	public String toString() {//为空
		return IOperatori18n.getISNull_Desc();
	}

	@Override
	public String getOperatorCode() {
		return IOperatorConstants.ISNULL;
	}

}
