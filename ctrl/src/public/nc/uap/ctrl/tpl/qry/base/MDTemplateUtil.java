package nc.uap.ctrl.tpl.qry.base;

import java.util.Map;

import nc.md.model.IAttribute;
import nc.md.model.IBusinessEntity;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.ctrl.tpl.qry.operator.OperatorFactory;
import nc.vo.bd.meta.IBDObject;


/**
 * 元数据查询模板工具类
 * @author ewei
 * 日期：2008年9月5日
 */
public class MDTemplateUtil {
	
	private static final String CODE = "code";
	private static final String NAME = "name";
	
	/*
	 * 根据返回类型和数据类型得到元数据可使用的操作符
	 */
	public static IOperator[] getOperatorsByType(int dataType,int returntype) {
		return OperatorFactory.getInstance().getOperators(getQTOperatorsByType(dataType,returntype));			
	}
	
	public static String getQTOperatorsByType(int dataType,int returntype){
		switch (dataType) {
		case IQueryConstants.INTEGER:
		case IQueryConstants.DECIMAL:
		case IQueryConstants.DATE:
		case IQueryConstants.TIME:
		case IQueryConstants.LITERALDATE:
			return "between@=@>@>=@<@<=@";
		case IQueryConstants.STRING:
			return "like@=@left like@right like@";
		case IQueryConstants.MULTILANG:
			return "like@=@left like@right like@";
		case IQueryConstants.UFREF:
		{
			if(returntype==IQueryConstants.RETURNPK)
			{
				return "="; 
			}
			else if(returntype==IQueryConstants.RETURNNAME||returntype==IQueryConstants.RETURNCODE)
			{
				return "=@between@like@>@>=@<@<=@";
			}
		}
		case IQueryConstants.USERCOMBO:
			return "=@<>@";
		case IQueryConstants.BOOLEAN:
			return "=@";
		default:
			return "=@";
		}
	}
	
	/*
	 * 根据与数据数据类型得到查询vo数据类型
	 */
	public static int getQTDataType(int mdtype){
		return MDType2QTTypeMapping.getQTType(mdtype);
	}
	
	/**
	 * 判断属性是否是编码属性
	 * 
	 * @param attr
	 *            属性
	 */
	public static boolean isCodeAttribute(IAttribute attr) {
		return isCodeNameAttribute(attr, CODE);
	}

	/**
	 * 判断属性是否是名称属性
	 * 
	 * @param attr
	 *            属性
	 */
	public static boolean isNameAttribute(IAttribute attr) {
		return isCodeNameAttribute(attr, NAME);
	}

	private static boolean isCodeNameAttribute(IAttribute attr,
			String code_or_name) {
		if (attr.getOwnerBean() instanceof IBusinessEntity) {
			IBusinessEntity be = (IBusinessEntity) attr.getOwnerBean();
			String attrname = attr.getName();
			String itfName = IBDObject.class.getName();
			Map<String, String> map = be.getBizInterfaceMapInfo(itfName);
			for (String key : map.keySet()) {
				if (key.equals(code_or_name)) {
					return attrname.equals(map.get(key));
				}
			}
		}
		return false;
	}
}