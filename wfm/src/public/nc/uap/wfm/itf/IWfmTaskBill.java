package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
public interface IWfmTaskBill {
	/**
	 * ͬ������ʵ������
	 * @param task
	 * @return
	 * @throws WfmServiceException
	 */
	Task asynTask(Task task) throws WfmServiceException;
	/**
	 * ��ɾ����ʵ������
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean bogusDeleteTask(String taskPk) throws WfmServiceException;
	/**
	 * ��ɾ����ʵ������
	 * @param taskPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean realDeleteTask(String taskPk) throws WfmServiceException;
	/**
	 * ��������ʵ��
	 * @param vo
	 * @return
	 * @throws WfmServiceException
	 */
	WfmTaskVO saveOrUpate(WfmTaskVO vo) throws WfmServiceException;
	/**
	 * ����ʵ��PKɾ��������Ϣ
	 * @param proInsPk
	 * @throws WfmServiceException
	 */
	void deleteTaskByProInsPk(String proInsPk) throws WfmServiceException;
}
