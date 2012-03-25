package nc.uap.ctrl.tpl.qry.operator;

import nc.uap.ctrl.tpl.qry.base.DateInterval;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFieldValue;
import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IFilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.uap.ctrl.tpl.qry.meta.MDFilterMeta;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.sysfunc.SystemFunction;
import nc.uap.ctrl.tpl.qry.value.Attribute;
import nc.vo.pub.lang.ICalendar;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;

public abstract class AbstractOperator implements IOperator {

	public int getParameterNumber() {
		return 0;
	}

	public String getSQLString(FilterMeta meta, IFieldValue value) {
		return null;
	}

	public abstract String getOperatorCode();
	
	/**
	 * �ж�meta�Ƿ�����ֵ����
	 */
	protected boolean isNumberType(IFilterMeta meta)
	{
		return meta.isNumberType();
	}
	
	/**
	 * �ж�meta�Ƿ������ڻ�ʱ������
	 */
	protected boolean isDateType(FilterMeta meta) {
		return IQueryConstants.DATE == meta.getDataType() || IQueryConstants.LITERALDATE == meta.getDataType() || IQueryConstants.TIME == meta.getDataType();
	}
	
	/**
	 * �ж�meta�Ƿ���������������
	 */
	protected boolean isLiteralDate(FilterMeta meta){
		return IQueryConstants.LITERALDATE == meta.getDataType();
	}
	
	protected boolean isBooleanType(FilterMeta meta){
		return IQueryConstants.BOOLEAN == meta.getDataType();
	}
	
	/**
	 * IFieldValueElement�����Ķ����Ƿ���ϵͳ����SystemFunction
	 */
	protected boolean isSystemFunction(IFieldValueElement value) {
		return value == null ? false : value.getValueObject() instanceof SystemFunction;
	}
	
	/**
	 * IFieldValueElement�����Ķ����Ƿ��ǿ������Attribute
	 */
	protected boolean isAttribute(IFieldValueElement value) {
		return value == null ? false : value.getValueObject() instanceof Attribute;
	}
	
	/**
	 * ����FilterMeta��Ӧ�ı��������ҪΪ����������ɱ�����ʽSQLʹ��
	 * ʹ�ÿ��������Ϊ��ѯֵʱ��Ȼ�ܹ���ȡ������Ա�ƴ�����Ա�֤SQL��ȷ��
	 */
	protected String getTableAlias(FilterMeta meta) {
		return ((MDFilterMeta) meta).getTableAlias();
	}
	
	/**
	 * ���ش�������Ŀ������
	 * <p>
	 * ���������Ϊ����ֵʱҪע�������ʽSQL������
     * <p>
	 * �㷨��������:
	 * 1���ȸ���FilterMeta(ʵ������fieldCode)�õ������
     * fieldCodeֻ������������б�����ĺ��ޱ������
	 * 2�����fieldCodeû�б��������������Ҳ���ñ����
	 * 3�����fieldCode�б��������������Ҳ�ӵ����������
	 * ��ΪfieldCode��Ӧ�����ԺͿ�����Կ϶���������ͬһ��ʵ��
	 * 
	 * @param meta
	 *            FilterMeta
	 * @param attr
	 *            �������
	 */
	protected String getAttrWithTableAlias(FilterMeta meta, String attr) {
		String alias = getTableAlias(meta);
		return alias == null ? attr : alias + "." + attr;
	}
	
	public boolean isFuzzy()
	{
		return false;
	}
	
	protected ICalendar getCalendarBegin(IFieldValueElement fve) {
		Object valueObject = fve.getValueObject();
		ICalendar calendar = null;
		if (valueObject instanceof SystemFunction) {
			RefResultVO functionValue = ((SystemFunction) valueObject).getFunctionValue();
			DateInterval dateInterval = (DateInterval) functionValue.getRefObj();
			calendar = dateInterval.getBeginDate();
		} else if (valueObject instanceof ICalendar){
			calendar = (ICalendar) valueObject;
		}
		return calendar;
	}
	
	protected ICalendar getCalendarEnd(IFieldValueElement fve) {
		Object valueObject = fve.getValueObject();
		ICalendar calendar = null;
		if (valueObject instanceof SystemFunction) {
			RefResultVO functionValue = ((SystemFunction) valueObject).getFunctionValue();
			DateInterval dateInterval = (DateInterval) functionValue.getRefObj();
			calendar = dateInterval.getEndDate();
		} else if (valueObject instanceof ICalendar){
			calendar = (ICalendar) valueObject;
		}
		return calendar;
	}
	
	/**
	 * ����UFDate��Ӧ�Ļ�׼ʱ����ʼʱ�䣬�� "yyyy-MM-dd 00:00:00"
	 */
	protected String getDateBegin(UFDate date) {
		return date.asLocalBegin().toString();
	} 
	
	/**
	 * ����UFDate��Ӧ�Ļ�׼ʱ������ʱ�䣬�� "yyyy-MM-dd 00:00:00"
	 */
    protected String getDateEnd(UFDate date){
		return date.asLocalEnd().toString();
	} 
    
    /**
	 * �������ڵ���ָ�����ڵ�SQL����"date >= 'yyyy-MM-dd 00:00:00'"
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param calendar
	 *            ���ڻ�ʱ��
	 */
	protected String buildGETDateSQL(String fieldCode, ICalendar calendar) {
		String value = null;
		if (calendar instanceof UFDate) {
			value = getDateBegin((UFDate) calendar);
		} else if (calendar instanceof UFDateTime) {
			value = getDateBegin(((UFDateTime)calendar).getDate());
		}else if (calendar instanceof UFLiteralDate){
			value = ((UFLiteralDate)calendar).toStdString();
		}
		return fieldCode + " " + IOperatorConstants.GE + " '" + value + "'";
	}

	/**
	 * ��������ָ�����ڵ�SQL����"date > 'yyyy-MM-dd 23:59:59'"
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param calendar
	 *            ���ڻ�ʱ��           
	 */
	protected String buildGTDateSQL(String fieldCode, ICalendar calendar) {
		String value = null;
		if (calendar instanceof UFDate) {
			value = getDateEnd((UFDate) calendar);
		} else if (calendar instanceof UFDateTime) {
			value = ((UFDateTime) calendar).toStdString();
		} else if (calendar instanceof UFLiteralDate){
			value = ((UFLiteralDate)calendar).toStdString();
		}
		return fieldCode + " " + IOperatorConstants.GREATE + " '" + value + "'";
	}

	/**
	 * ����С�ڵ���ָ�����ڵ�SQL����"date <= 'yyyy-MM-dd 23:59:59'"
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param calendar
	 *            ���ڻ�ʱ��   
	 */
	protected String buildLETDateSQL(String fieldCode, ICalendar calendar) {
		String value = null;
		if (calendar instanceof UFDate) {
			value = getDateEnd((UFDate) calendar);
		} else if (calendar instanceof UFDateTime) {
			value = getDateEnd(((UFDateTime)calendar).getDate());
		} else if (calendar instanceof UFLiteralDate){
			value = ((UFLiteralDate)calendar).toStdString();
		}
		return fieldCode + " " + IOperatorConstants.LE + " '" + value + "'";
	}

	/**
	 * ����С��ָ�����ڵ�SQL����"date < 'yyyy-MM-dd 00:00:00'"
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param calendar
	 *            ���ڻ�ʱ��
	 */
	protected String buildLTDateSQL(String fieldCode, ICalendar calendar) {
		String value = null;
		if (calendar instanceof UFDate) {
			value = getDateBegin((UFDate) calendar);
		} else if (calendar instanceof UFDateTime) {
			value = ((UFDateTime) calendar).toStdString();
		} else if (calendar instanceof UFLiteralDate){
			value = ((UFLiteralDate)calendar).toStdString();
		}
		return fieldCode + " " + IOperatorConstants.LESS + " '" + value + "'";
	}
}