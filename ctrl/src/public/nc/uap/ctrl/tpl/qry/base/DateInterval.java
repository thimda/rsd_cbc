package nc.uap.ctrl.tpl.qry.base;

import nc.vo.pub.lang.UFDate;

/**
 * ���ڷ�Χ
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2011-6-1
 */
public class DateInterval {

	private UFDate beginDate;
	private UFDate endDate;

	public DateInterval() {
		super();
	}

	/**
	 * @param beginDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 */
	public DateInterval(UFDate beginDate, UFDate endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	/**
	 * ��ʼ����
	 */
	public UFDate getBeginDate() {
		return beginDate;
	}

	/**
	 * ��ʼ����
	 */
	public void setBeginDate(UFDate beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * ��������
	 */
	public UFDate getEndDate() {
		return endDate;
	}

	/**
	 * ��������
	 */
	public void setEndDate(UFDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "[" + getDateString(beginDate) + "," + getDateString(endDate)
				+ "]";
	}

	/**
	 * ������ʼ���ںͽ�������֮�������
	 */
	public int getDays() {
		return UFDate.getDaysBetween(beginDate, endDate);
	}
	
	private String getDateString(UFDate date) {
		return date == null ? null : date.toLocalString();
	}
}