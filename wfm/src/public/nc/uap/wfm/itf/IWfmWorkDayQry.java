package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmVacationVO;
import nc.uap.wfm.vo.WfmWeekendVO;

/**
 * 工作日查询服务
 * @author licza
 *
 */
public interface IWfmWorkDayQry { 
	
	/**
	 * 获得假期
	 * @return
	 */
	public WfmVacationVO[] getHolidays();
	
	/**
	 * 获得特殊工作日
	 * @return
	 */
	public WfmVacationVO[] getSpecialWorkDay();
	
	/**
	 * 获得周末
	 * @return
	 */
	public Integer[] getWeekend();
	
	/**
	 * 获得周末的配置信息
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmWeekendVO getWeekendProp() throws WfmServiceException;
	
	/**
	 * 初始化缓存
	 */
	public void initCache();
}
