package nc.uap.wfm.engine;
/**
 * 流程调度扩展接口
 * @author tianchw
 *
 */
public interface IProcessSheduler {
	/**
	 * 调度ID
	 * @return
	 */
	String getId();
	/**
	 * 调度间隔
	 * @return
	 */
	long getSeperator();
	/**
	 * 调度实现
	 */
	void shceduler();
}
