package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmWeekendVO;

/**
 * ���������÷���ӿ�
 * @author licza
 *
 */
public interface IWfmWorkDayBill {
	/**
	 * ����һ����ĩ����VO
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmWeekendVO add(WfmWeekendVO vo) throws WfmServiceException;
	/**
	 * ����һ����ĩ����VO
	 * @param vo
	 * @throws PortalServiceException
	 */
	public void update(WfmWeekendVO vo) throws WfmServiceException;
}
