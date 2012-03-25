package nc.uap.wfm.engine;
import nc.uap.wfm.model.ProDef;
/**
 * 流程定义容器
 * 
 * @author tianchw
 * 
 */
public interface IProDefContainer {
	/**
	 * 消毁流程定义
	 * 
	 * @param proDefPk
	 * @param proDefId
	 */
	 void destory(String proDefPk, String proDefId);
	/**
	 * 构造流程定义
	 * 
	 * @param proDefPk
	 * @param proDefId
	 */
	void builder(String proDefPk, String proDefId);
	/**
	 * 流程定义定位根据流程定义ID和PK
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	ProDef getByProDefgPkAndId(String proDefPk, String proDefId);
	/**
	 * 流程定义定位根据发起单据
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	ProDef getByStartFrmDefPk(String startFrmDefPk);
}
