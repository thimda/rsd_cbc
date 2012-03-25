package nc.uap.ctrl.tpl.qry.base;

import java.util.HashMap;
import java.util.Map;

import nc.md.model.type.IType;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;

/**
 * 元数据的数据类型到查询模板的数据类型之间的映射表
 * @author liujian
 *
 */
public class MDType2QTTypeMapping {

	private static Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();
	static
	{
		mapping.put(IType.TYPE_String, IQueryConstants.STRING);
		mapping.put(IType.TYPE_Double, IQueryConstants.DECIMAL);
		mapping.put(IType.TYPE_Boolean, IQueryConstants.BOOLEAN);
		mapping.put(IType.TYPE_Integer, IQueryConstants.INTEGER);
		mapping.put(IType.TYPE_Date, IQueryConstants.DATE);
		mapping.put(IType.TYPE_INT, IQueryConstants.INTEGER);
		mapping.put(IType.TYPE_BOOL, IQueryConstants.BOOLEAN);
		mapping.put(IType.TYPE_CHAR, IQueryConstants.STRING);
		mapping.put(IType.TYPE_BYTE, IQueryConstants.STRING);
		mapping.put(IType.TYPE_DOUBLE, IQueryConstants.DECIMAL);
		mapping.put(IType.TYPE_FLOAT, IQueryConstants.DECIMAL);
		mapping.put(IType.TYPE_SHORT, IQueryConstants.INTEGER);
		mapping.put(IType.TYPE_LONG, IQueryConstants.INTEGER);
		mapping.put(IType.TYPE_CALENDAR, IQueryConstants.DATE);
		mapping.put(IType.TYPE_MEMO, IQueryConstants.STRING);
		mapping.put(IType.TYPE_UFDouble, IQueryConstants.DECIMAL);
		mapping.put(IType.TYPE_UFBoolean, IQueryConstants.BOOLEAN);
		mapping.put(IType.TYPE_UFDate, IQueryConstants.DATE);
		mapping.put(IType.TYPE_UFDATE_LITERAL, IQueryConstants.LITERALDATE);
		mapping.put(IType.TYPE_UFDate_BEGIN, IQueryConstants.DATE);
		mapping.put(IType.TYPE_UFDate_END, IQueryConstants.DATE);
		mapping.put(IType.TYPE_UFDateTime, IQueryConstants.TIME);
		mapping.put(IType.TYPE_UFNumberFormat, IQueryConstants.DECIMAL);
		mapping.put(IType.TYPE_UFID, IQueryConstants.STRING);
		mapping.put(IType.TYPE_UFMoney, IQueryConstants.DECIMAL);
		mapping.put(IType.ENUM, IQueryConstants.USERCOMBO);
		mapping.put(IType.VALUEOBJECT, IQueryConstants.USERCOMBO);
		mapping.put(IType.REF, IQueryConstants.UFREF);
		mapping.put(IType.ENTITY, IQueryConstants.UFREF);
		mapping.put(IType.MULTILANGUAGE, IQueryConstants.MULTILANG);
	}
	
	public static int getQTType(int mdtype)
	{
		Integer i = mapping.get(mdtype);
		return (i==null)?IQueryConstants.STRING:i;
	}
	
}
