package nc.uap.wfm.engine;
/**
 * ���̵�����չ�ӿ�
 * @author tianchw
 *
 */
public interface IProcessSheduler {
	/**
	 * ����ID
	 * @return
	 */
	String getId();
	/**
	 * ���ȼ��
	 * @return
	 */
	long getSeperator();
	/**
	 * ����ʵ��
	 */
	void shceduler();
}
