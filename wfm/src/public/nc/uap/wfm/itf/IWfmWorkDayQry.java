package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmVacationVO;
import nc.uap.wfm.vo.WfmWeekendVO;

/**
 * �����ղ�ѯ����
 * @author licza
 *
 */
public interface IWfmWorkDayQry { 
	
	/**
	 * ��ü���
	 * @return
	 */
	public WfmVacationVO[] getHolidays();
	
	/**
	 * ������⹤����
	 * @return
	 */
	public WfmVacationVO[] getSpecialWorkDay();
	
	/**
	 * �����ĩ
	 * @return
	 */
	public Integer[] getWeekend();
	
	/**
	 * �����ĩ��������Ϣ
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmWeekendVO getWeekendProp() throws WfmServiceException;
	
	/**
	 * ��ʼ������
	 */
	public void initCache();
}
