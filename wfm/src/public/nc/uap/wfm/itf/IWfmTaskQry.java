package nc.uap.wfm.itf;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
public interface IWfmTaskQry {
	/**
	 * 根据流程主键获取VO
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getTaskVOByPk(String taskPk) throws WfmServiceException;
	/**
	 * 根据任务PK获取任务数据
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	Task getTaskByPk(String taskPk) throws WfmServiceException;
	/**
	 * 根据活动实例PK获取任务实例数据
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<Task> getTasksByHumActInsPk(String humActInsPk) throws WfmServiceException;
	/**
	 * 根据活动实例PK获取任务实例数据
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<Task> getTasksByHumActInsPk(String humActInsPk, String createType) throws WfmServiceException;
	/**
	 * 获取我指定时间段没有完成的任务
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUndneTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我指定时间段完成的任务
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyCmpltTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我指定时间段未阅的任务
	 * 
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDeliverTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我指定时间段已阅的任务
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyDeliveredTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我指定时间段办结的任务
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyEndedTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我的任务工作项，根据任务状态 0，未完成和待阅。1，已经完成和已阅读
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @param state
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyTaskByState(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo, String[] state) throws WfmServiceException;
	/**
	 * 根据流程实例获取它的所有任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getTasksNotAddSignStopByRootProInsPk(String rootProInsPk) throws WfmServiceException;
	WfmTaskVO[] getTasksConAddSignStopByRootProInsPk(String rootProInsPk) throws WfmServiceException;
	/**
	 * 最新的没有完成的任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * 最新的没有完成的任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException;
	/**
	 * 最新的挂起的任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterSuspendedTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * 最新的完成的任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * 最新的完成的任务
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException;
	/**
	 * 获取该任务下的加签完成任务
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignCmpltTaskByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * 获取该任务下的加签任务靠次数
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByTaskPkAndTimes(String taskPk, int times) throws WfmServiceException;
	/**
	 * 获取该任务下的加签任务
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * 获取一个活动实例下的加签任务
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByHumActInsPk(String humActInsPk) throws WfmServiceException;
	/**
	 * 获取我便签
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	List<Map<String, String>> getMyScratchpadByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * 获取我没有完成的任务
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDneTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我完成的任务
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyCmpltTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我待阅的任务
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDeliverTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我已阅的任务
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyDeliveredTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取我办结的任务
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyEndedTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 查询出所有待办和待阅的任务。
	 * @param userPk
	 * @param proDefPk
	 * @param startDate
	 * @param endDate
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUndneTasksAndUnDeliverByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * 获取一个流程实例下的所有传阅任务
	 * 
	 * @param rootProInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getDeliverTaskVosByRootProInsPk(String rootProInsPk) throws WfmServiceException;
	boolean getTaskPortId(String pk_frmins, String[] portid) throws WfmServiceException;
	WfmTaskVO[] getTaskVOsByWhere(String where, String priority) throws WfmServiceException;
	WfmTaskVO[] getTaskVOsByWhere(String where) throws WfmServiceException;
	WfmTaskVO[] getTaskVosBySql(String sql) throws WfmServiceException;
	String getProInsPkByfrmInPk(String frmInsPk) throws WfmServiceException;
	String[] getFrmInsPksByFrmDefPkAndUserPk(String frmDefPk, String userPk) throws WfmServiceException;
}