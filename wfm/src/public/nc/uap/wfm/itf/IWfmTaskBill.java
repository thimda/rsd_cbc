package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
public interface IWfmTaskBill {
	/**
	 * 同步任务实例数据
	 * @param task
	 * @return
	 * @throws WfmServiceException
	 */
	Task asynTask(Task task) throws WfmServiceException;
	/**
	 * 假删任务实例数据
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean bogusDeleteTask(String taskPk) throws WfmServiceException;
	/**
	 * 真删任务实例数据
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean realDeleteTask(String taskPk) throws WfmServiceException;
	/**
	 * 保存任务实例
	 * @param vo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO saveOrUpate(WfmTaskVO vo) throws WfmServiceException;
	/**
	 * 根据实例PK删除任务信息
	 * @param proInsPk
	 * @throws WfmServiceException
	 */
	void deleteTaskByProInsPk(String proInsPk) throws WfmServiceException;
}
