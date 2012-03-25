package nc.uap.wfm.engine;
import nc.uap.wfm.model.Task;
/**
 * ������չ�ӿ�
 * 
 * @author tianchw
 * 
 */
public interface ITaskExtHandler {
	/**
	 * ���񴴽�����չ
	 * 
	 * @param task
	 */
	void afterCreateTask(Task task);
	/**
	 * �������ǰ��չ����
	 */
	void beforeCmpltTask(Task task);
	/**
	 * ������ɺ����չ����
	 */
	void afterCmpltTask(Task task);
	/**
	 * �Ƿ�ʱ��չ����
	 * 
	 * @param task
	 * @return
	 */
	boolean isOverTime(Task task);
	/**
	 * ������պ����չ����
	 * 
	 * @param task
	 */
	void afterBack(Task task);
	/**
	 * ����̨��ģ����Ⱦ��
	 * 
	 * @param task
	 * @return
	 */
	String getFlowDataDispModel(Task task);
	/**
	 * ������ϸģ����Ⱦ��
	 * 
	 * @param task
	 * @return
	 */
	String getFlwDataDetailModel(Task task);
	/**
	 * ������Ϣ����������
	 * 
	 * @param task
	 * @return
	 */
	String getPhoneMsg(Task task);
}
