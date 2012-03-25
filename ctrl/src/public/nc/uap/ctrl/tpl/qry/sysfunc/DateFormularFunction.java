package nc.uap.ctrl.tpl.qry.sysfunc;

import nc.bs.logging.Logger;
import nc.uap.ctrl.tpl.qry.base.DateInterval;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.vo.pub.lang.UFDate;

/**
 * ͨ����ʽʵ�ֵ����ں���
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-10-12
 */
public class DateFormularFunction extends FormularFunction {

	/**
	 * @param expression
	 *            ��ʽ���ʽ
	 * @param resid
	 *            �������ƶ�����Դ��
	 */
	public DateFormularFunction(String expression, String resid) {
		super(expression,resid);
	}

	@Override
	public SFType getType() {
		return SFType.DATE;
	}

	@Override
	public RefResultVO value() {
//		UFDate date = (UFDate) calculate();
//		String strDate = date.toLocalString();
//		RefResultVO result = new RefResultVO();
//		result.setRefPK(strDate);
//		result.setRefCode(strDate);
//		result.setRefName(strDate);
//		result.setRefObj(date);
//		return result;
		DateInterval dateInterval = null;
		Object obj = calculate();
		if(obj instanceof UFDate){
			UFDate date = (UFDate) obj;
			dateInterval = new DateInterval(date,date);
		}else if(obj instanceof DateInterval){
			dateInterval = (DateInterval)obj;
		}
		if(dateInterval != null){
			String strDate = dateInterval.getBeginDate().toString();
			RefResultVO result = new RefResultVO();
			result.setRefPK(strDate);
			result.setRefCode(strDate);
			result.setRefName(strDate);
			result.setRefObj(dateInterval);
			return result;
		}
		Logger.error("ʹ��ϵͳ����" + getCode() + "ʱ����");
		return null;
	}
}