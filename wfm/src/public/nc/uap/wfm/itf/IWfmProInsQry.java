package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.vo.WfmProInsVO;
public interface IWfmProInsQry {
	/**
	 * 获取流程实例数据
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	ProIns getProInsByPk(String proInsPk) throws WfmServiceException;
	/**
	 * 获取我启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProIns(String userPk) throws WfmServiceException;
	/**
	 * 获取我启动并且是完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProIns(String userPk) throws WfmServiceException;
	/**
	 * 获取我今天启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByDay(String userPk) throws WfmServiceException;
	/**
	 * 获取我今天启动并且是完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByDay(String userPk) throws WfmServiceException;
	/**
	 * 获取我最近一周启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByWeek(String userPk) throws WfmServiceException;
	/**
	 * 获取我最近一周启动并且是完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByWeek(String userPk) throws WfmServiceException;
	/**
	 * 获取我最近一个月启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByMonth(String userPk) throws WfmServiceException;
	/**
	 * 获取我最近一个月启动并且是完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByMonth(String userPk) throws WfmServiceException;
	/**
	 * 获取我指定时间段启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUndneProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException;
	/**
	 * 获取我指定时间段启动并且没有完成的流程
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException;
	WfmProInsVO[] getSubProInsByProInsPk(String proInsPk) throws WfmServiceException;
}
