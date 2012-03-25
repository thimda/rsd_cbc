package nc.uap.ctrl.tpl.qry.operator;

import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFieldValue;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IFilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;

public abstract class OneParaOperator extends AbstractOperator {

	public int getParameterNumber() {
		return 1;
	}


	public String getSQLString(FilterMeta meta,IFieldValue value) {
		if(value==null||value.getFieldValues()==null||value.getFieldValues().size()<1||value.getFieldValues().get(0)==null)
			return null;
		IFieldValueElement fieldValueElement = value.getFieldValues().get(0);
		String code = getOperatorCode();
		String valueString = fieldValueElement.getSqlString();
		String fieldCode = meta.getSQLFieldCode();
		// ���������Ϊ����ֵʱҪע�������ʽSQL������
		if (isAttribute(fieldValueElement)) {
			valueString = getAttrWithTableAlias(meta, valueString);
		}else if (!isNumberType(meta)) {
			// ���Ӷ��������͵�i18n����
			if (isDateType(meta) || isLiteralDate(meta)) {
				int dataType = meta.getDataType();
//				ICalendar date = getCalendar(fieldValueElement);
				valueString = "'" + getCalendarSQLString(fieldValueElement,dataType) + "'";
				return fieldCode + " " + code + " " + valueString + " and " + fieldCode + " != '~'";
			} else if (isBooleanType(meta) && meta.isUserDef()){
				if("N".equals(valueString) || "n".equals(valueString)){
					return fieldCode + " " + code + " " + valueString + " and " + fieldCode + " = '~'";
				}
			}
			else {
				valueString = "'" + valueString + "'";
			}
		}else if(isNumberType(meta)){
			if(meta.isUserDef()){
				int dataType = meta.getDataType();
				if(dataType == IQueryConstants.DECIMAL){
					return fieldCode + " != '~' and cast(" + fieldCode + " as float)" + code + " " + valueString;  
				}else{
					return fieldCode + " != '~' and cast(" + fieldCode + " as int)" + code + " " + valueString;
				}
			}
		}
		return fieldCode + " " + code + " " + valueString;
	}

	/**
	 * ����������Ӧ��SQLֵ
	 */
	protected abstract String getCalendarSQLString(IFieldValueElement fieldValueElement,int dataType);
	
	public String getDescription(IFilterMeta meta, IFieldValue value) {
		if(value==null||value.getFieldValues()==null||value.getFieldValues().size()<1||value.getFieldValues().get(0)==null)
			return meta.toString();
		String opeartorCode = toString();
		String valueString = value.getFieldValues().get(0).getShowString();
		return meta.toString()+" "+opeartorCode+" "+valueString;
	}
}
