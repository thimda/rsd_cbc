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
	 * ��������������ȡVO
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getTaskVOByPk(String taskPk) throws WfmServiceException;
	/**
	 * ��������PK��ȡ��������
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	Task getTaskByPk(String taskPk) throws WfmServiceException;
	/**
	 * ���ݻʵ��PK��ȡ����ʵ������
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<Task> getTasksByHumActInsPk(String humActInsPk) throws WfmServiceException;
	/**
	 * ���ݻʵ��PK��ȡ����ʵ������
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	Set<Task> getTasksByHumActInsPk(String humActInsPk, String createType) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ���û����ɵ�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUndneTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ�����ɵ�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyCmpltTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ���δ�ĵ�����
	 * 
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDeliverTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ������ĵ�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyDeliveredTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ��ΰ�������
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyEndedTasksByDate(String userPk, String proDefPk, String startDate, String endDate, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ�ҵ����������������״̬ 0��δ��ɺʹ��ġ�1���Ѿ���ɺ����Ķ�
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
	 * ��������ʵ����ȡ������������
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getTasksNotAddSignStopByRootProInsPk(String rootProInsPk) throws WfmServiceException;
	WfmTaskVO[] getTasksConAddSignStopByRootProInsPk(String rootProInsPk) throws WfmServiceException;
	/**
	 * ���µ�û����ɵ�����
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * ���µ�û����ɵ�����
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterUndneTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException;
	/**
	 * ���µĹ��������
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterSuspendedTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * ���µ���ɵ�����
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk) throws WfmServiceException;
	/**
	 * ���µ���ɵ�����
	 * 
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO getLasterCmpltTaskByProInsPk(String proInsPk, String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�������µļ�ǩ�������
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignCmpltTaskByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * ��ȡ�������µļ�ǩ���񿿴���
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByTaskPkAndTimes(String taskPk, int times) throws WfmServiceException;
	/**
	 * ��ȡ�������µļ�ǩ����
	 * 
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * ��ȡһ���ʵ���µļ�ǩ����
	 * 
	 * @param humActInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getAddSignTaskByHumActInsPk(String humActInsPk) throws WfmServiceException;
	/**
	 * ��ȡ�ұ�ǩ
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	List<Map<String, String>> getMyScratchpadByTaskPk(String taskPk) throws WfmServiceException;
	/**
	 * ��ȡ��û����ɵ�����
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDneTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ����ɵ�����
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyCmpltTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ�Ҵ��ĵ�����
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyUnDeliverTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ�����ĵ�����
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyDeliveredTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ȡ�Ұ�������
	 * 
	 * @param userPk
	 * @param proDefPk
	 * @param pinfo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO[] getMyEndedTasks(String userPk, String proDefPk, PaginationInfo pinfo) throws WfmServiceException;
	/**
	 * ��ѯ�����д���ʹ��ĵ�����
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
	 * ��ȡһ������ʵ���µ����д�������
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