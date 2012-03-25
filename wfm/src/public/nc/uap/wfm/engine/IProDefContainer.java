package nc.uap.wfm.engine;
import nc.uap.wfm.model.ProDef;
/**
 * ���̶�������
 * 
 * @author tianchw
 * 
 */
public interface IProDefContainer {
	/**
	 * �������̶���
	 * 
	 * @param proDefPk
	 * @param proDefId
	 */
	 void destory(String proDefPk, String proDefId);
	/**
	 * �������̶���
	 * 
	 * @param proDefPk
	 * @param proDefId
	 */
	void builder(String proDefPk, String proDefId);
	/**
	 * ���̶��嶨λ�������̶���ID��PK
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	ProDef getByProDefgPkAndId(String proDefPk, String proDefId);
	/**
	 * ���̶��嶨λ���ݷ��𵥾�
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	ProDef getByStartFrmDefPk(String startFrmDefPk);
}
