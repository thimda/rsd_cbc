package nc.uap.ctrl.tpl.qry.operator;

import nc.uap.ctrl.tpl.qry.meta.IFieldValueElement;
import nc.uap.ctrl.tpl.qry.meta.IQueryConstants;
import nc.vo.pub.lang.ICalendar;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;


public class LetOperator extends OneParaOperator {

	private static final long serialVersionUID = -5191289628896157070L;
	
	private static final LetOperator INSTANCE = new LetOperator();

	public static LetOperator getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String toString() {
		return IOperatori18n.getLE_Desc();//С�ڵ���
	}
	@Override
	public String getOperatorCode() {
		return IOperatorConstants.LE;
	}
	
	/**
	 * �����������ͣ�����С�ڵ���ĳ������Ӧ�÷�������"yyyy-MM-dd 23:59:59"��"yyyy-MM-dd"��SQL
	 */
	protected String getCalendarSQLString(IFieldValueElement fieldValueElement,int dataType) {
		ICalendar date = getCalendarEnd(fieldValueElement);
		UFDate ufDate = null;
		if(date instanceof UFDate){
			ufDate = (UFDate)date;
		}else if(date instanceof UFDateTime){
			ufDate = ((UFDateTime)date).getDate();
		}
		if(ufDate!=null){
			String dateStr = getDateEnd(ufDate);
			if(dataType == IQueryConstants.LITERALDATE){
				return UFLiteralDate.getDate(dateStr).toStdString();
			}else{
				return dateStr;
			}
		}
		return null;
	}
}
