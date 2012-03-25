package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmWeekendVO;

/**
 * 工作日设置服务接口
 * @author licza
 *
 */
public interface IWfmWorkDayBill {
	/**
	 * 插入一个周末设置VO
	 * @param vo
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmWeekendVO add(WfmWeekendVO vo) throws WfmServiceException;
	/**
	 * 更新一个周末设置VO
	 * @param vo
	 * @throws PortalServiceException
	 */
	public void update(WfmWeekendVO vo) throws WfmServiceException;
}
