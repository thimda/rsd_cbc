package nc.uap.wfm.agent;
/**
 * �����ո�����
 * 
 * @author licza
 * 
 */
public class WorkDayHelper {
	// /**
	// * ���ָ������֮���ָ����������
	// *
	// * @param start
	// * ��ʼ����
	// * @param howLong
	// * ����������
	// * @return
	// */
	// public Date getNextDate(long startTimeSpan, int howLong) {
	// ProvingDate pd = new ProvingDate();
	// int j = 1;
	// while (pd.getIdentifier() <= howLong) {
	// pd.setTimestamp(DateUtils.addDays(startTimeSpan, j));
	// rock(pd);
	// j++;
	// }
	// return new Date(pd.getTtimestamp());
	// }
	//
	// /**
	// * ��ù���ʱ���
	// *
	// * @param start
	// * @param end
	// * @return
	// */
	// public long getWorkTimeDifference(long start, long end) {
	// long wt = end - start;
	// if (wt < 0)
	// throw new IllegalArgumentException("��ʼʱ�����С�ڽ���ʱ��!");
	// /**
	// * ��ȥ��ĩ
	// */
	// Integer[] weekend = PortalServiceUtil.getWorkDayQryService()
	// .getWeekend();
	// long t = 0L;
	// int k = 1;
	// boolean isEndDay = false;
	// while (!isEndDay) {
	// t = DateUtils.addDays(start, k);
	// Integer dow = DateUtils.getDayOfWeek(t);
	// if (ArrayUtils.contains(weekend, dow))
	// wt = wt - DAY_MMS;
	// isEndDay = isSameDay(t, end);
	// }
	// /**
	// * ������
	// */
	//
	// try {
	// PtVacationVO[] vos =
	// PortalServiceUtil.getWorkDayQryService().getHolidays();
	// if (!PtUtil.isNull(vos)) {
	// for (PtVacationVO vo : vos) {
	// long s2 = vo.getStartday().getMillis();
	// long e2 = vo.getEndday().getMillis();
	// wt = wt - getMixTime(start, end, s2, e2);
	// }
	// }
	// } catch (Exception e) {
	// LfwLogger.error(e.getMessage(), e);
	// }
	// /**
	// * �������⹤����
	// */
	// try {
	// PtVacationVO[] vos =
	// PortalServiceUtil.getWorkDayQryService().getSpecialWorkDay();
	// if (!PtUtil.isNull(vos)) {
	// for (PtVacationVO vo : vos) {
	// long s2 = vo.getStartday().getMillis();
	// long e2 = vo.getEndday().getMillis();
	// wt = wt + getMixTime(start, end, s2, e2);
	// }
	// }
	// } catch (Exception e) {
	// LfwLogger.error(e.getMessage(), e);
	// }
	// return 0L;
	// }
	//	
	// /**
	// * һ��ĺ�����
	// */
	// private static final long DAY_MMS = 60*60*24L;
	//
	// /**
	// * ������ʱ��ν���
	// *
	// * @param s1
	// * ʱ���1��ʼʱ��
	// * @param e1
	// * ʱ���1����ʱ��
	// * @param s2
	// * ʱ���2��ʼʱ��
	// * @param e2
	// * ʱ���2����ʱ��
	// * @return
	// */
	// public static long getMixTime(long s1, long e1, long s2, long e2) {
	// /**
	// * �������У��
	// */
	// if (s1 > e1 || s2 > e2)
	// throw new IllegalArgumentException("��ʼʱ�����С�ڽ���ʱ��!");
	//
	// /**
	// * �ж��Ƿ��н���
	// */
	// if (s2 > e1 || e2 < s1)
	// return 0L;
	// /**
	// * 2��1
	// */
	// if (s1 > s2 && e1 > e2)
	// return e2 - s2;
	// /**
	// * 1��2
	// */
	// if (s2 > s1 && e2 > e1)
	// return e1 - s1;
	//
	// if (s2 < e1)
	// return e1 - s2;
	//
	// if (s1 < e2)
	// return e2 - s1;
	// return 0L;
	// }
	//
	// private static boolean isSameDay(long d1, long d2) {
	// Date date1 = new Date(d1);
	// Date date2 = new Date(d2);
	// return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
	// }
	//
	// /**
	// * ����
	// */
	// private static WorkDayHelper wdh = null;
	//
	// /**
	// * ��ʼ��һ�������ո�����
	// *
	// * @return
	// */
	// public static WorkDayHelper newIns() {
	// if (wdh == null)
	// wdh = new WorkDayHelper();
	// return wdh;
	// }
	//
	// /**
	// * �������캯��
	// */
	// private WorkDayHelper() {
	// PortalServiceUtil.getWorkDayQryService().initCache();
	// }
	//
	// /**
	// * �����չ�������
	// */
	// private static final IWorkDayFilterChain[] chains = new
	// IWorkDayFilterChain[] {
	// new SpecialWorkDayFilterChain(), new HolidayFilterChain(),
	// new WeekendFilterChain() };
	//
	// /**
	// * ���������
	// *
	// * @param pd
	// */
	// private void rock(ProvingDate pd) {
	// WorkDayFilterChain chainBase = new WorkDayFilterChain();
	// for (int k = 0; k < chains.length; k++) {
	// /**
	// * �����Ͽ�������
	// */
	// if (!chainBase.isChainBreak())
	// chains[k].doFilter(pd, chainBase);
	// }
	// }
	//
	// /**
	// * ����ʱ��
	// *
	// * @param startDay
	// * @param stopDay
	// * @param t
	// * @return
	// */
	// public static boolean among(long startDay, long stopDay, long t) {
	// // return t >= DateUtils.startOfDayInMillis(startDay) && t <=
	// // DateUtils.endOfDayInMillis(stopDay);
	// // ʹ�þ���ʱ��Ƚ�
	// return t >= startDay && t <= stopDay;
	// }
	//
	// /**
	// * ���ݶ���������������ַ���
	// *
	// * @param str
	// * @param parsePatterns
	// * @return
	// * @throws ParseException
	// */
	// public static Date parseDate(String str, String[] parsePatterns)
	// throws ParseException {
	// if (str == null || parsePatterns == null) {
	// throw new IllegalArgumentException("���ڽ�������Ϊ��!");
	// }
	// SimpleDateFormat parser = null;
	// ParsePosition pos = new ParsePosition(0);
	// for (int i = 0; i < parsePatterns.length; i++) {
	// if (i == 0) {
	// parser = new SimpleDateFormat(parsePatterns[0]);
	// } else {
	// parser.applyPattern(parsePatterns[i]);
	// }
	// pos.setIndex(0);
	// Date date = parser.parse(str, pos);
	// if (date != null && pos.getIndex() == str.length()) {
	// return date;
	// }
	// }
	// throw new ParseException("Unable to parse the date: " + str, -1);
	// }
	// }
	//
	// /**
	// * Ҫ�����ʱ��
	// *
	// * @author licza
	// *
	// */
	// class ProvingDate {
	// /**
	// * ��ǰ������
	// */
	// private int identifier = 1;
	// /**
	// * ����ʱ���
	// */
	// private Long timestamp;
	//
	// public int getIdentifier() {
	// return identifier;
	// }
	//
	// public void setIdentifier(int i) {
	// this.identifier = i;
	// }
	//
	// public Long getTtimestamp() {
	// return timestamp;
	// }
	//
	// public void setTimestamp(Long t) {
	// this.timestamp = t;
	// }
	// }
	//
	// /**
	// * �����չ������ӿ�
	// *
	// * @author licza
	// *
	// */
	// interface IWorkDayFilterChain {
	// /**
	// * �����Ƿ���Ϲ���
	// *
	// * @param t
	// * ���������
	// * @param i
	// * ����
	// * @param filterChain
	// * ��һ����
	// */
	// public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain);
	// }
	//
	// /**
	// * �����չ�����
	// *
	// * @author licza
	// *
	// */
	// class WorkDayFilterChain {
	// /** ���Ƿ�Ͽ� **/
	// private boolean breakChain = false;
	//
	// public void doFilter(ProvingDate pd) {
	// pd.setIdentifier(pd.getIdentifier() + 1);
	// fire();
	// }
	//
	// /**
	// * �������Ƿ�Ͽ�
	// *
	// * @return
	// */
	// public boolean isChainBreak() {
	// return breakChain;
	// }
	//
	// /**
	// * �Ͽ���
	// **/
	// public void fire() {
	// breakChain = true;
	// }
	// }
	//
	// /**
	// * ��ĩ������
	// *
	// * @author licza
	// *
	// */
	// class WeekendFilterChain implements IWorkDayFilterChain {
	//
	// @Override
	// public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
	// Integer[] weekend = PortalServiceUtil.getWorkDayQryService()
	// .getWeekend();
	// int wk = DateUtils.getDayOfWeek(pd.getTtimestamp());
	// if (!ArrayUtils.contains(weekend, new Integer(wk)))
	// filterChain.doFilter(pd);
	// }
	// }
	//
	// /**
	// * ���⹤���չ�����
	// *
	// * @author licza
	// *
	// */
	// class SpecialWorkDayFilterChain implements IWorkDayFilterChain {
	// @Override
	// public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
	// try {
	// PtVacationVO[] vos = PortalServiceUtil.getWorkDayQryService()
	// .getSpecialWorkDay();
	// if (!PtUtil.isNull(vos)) {
	// for (PtVacationVO vo : vos) {
	// long start = vo.getStartday().getMillis();
	// long stop = vo.getEndday().getMillis();
	// boolean b = WorkDayHelper.among(start, stop, pd
	// .getTtimestamp());
	// if (b)
	// filterChain.doFilter(pd);
	// return;
	// }
	// }
	// } catch (Exception e) {
	// LfwLogger.error(e.getMessage(), e);
	// }
	// }
	// }
	//
	// /**
	// * ���ڹ�����
	// *
	// * @author licza
	// *
	// */
	// class HolidayFilterChain implements IWorkDayFilterChain {
	// @Override
	// public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
	// try {
	// PtVacationVO[] vos = PortalServiceUtil.getWorkDayQryService()
	// .getHolidays();
	// if (!PtUtil.isNull(vos)) {
	// for (PtVacationVO vo : vos) {
	// long start = vo.getStartday().getMillis();
	// long stop = vo.getEndday().getMillis();
	// boolean b = WorkDayHelper.among(start, stop, pd
	// .getTtimestamp());
	// /**
	// * ����Ǽ��� ��ֹ��
	// */
	// if (b)
	// filterChain.fire();
	// return;
	// }
	// }
	// } catch (Exception e) {
	// LfwLogger.error(e.getMessage(), e);
	// }
	// }
}
