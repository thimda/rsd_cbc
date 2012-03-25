package nc.uap.wfm.engine;
/**
 * �Զ�����ˮ����չ�ӿ�
 * 
 * @author tianchw
 * 
 */
public interface IFrmNumBillGen {
	/**
	 * ��ˮ�����ɽӿ�
	 * 
	 * @param frmDefPk
	 * @param taskPk
	 * @return
	 */
	String getValue(String flowTypePk, String taskPk);
	/**
	 * �Ƿ���ǰ�ñ���
	 * 
	 * @param frmDefPk
	 * @param taskPk
	 * @return
	 */
	boolean isBefore(String flowTypePk, String taskPk);
	
	String genFrmNumBillByFrmDefPk_RequiresNew(String flowTypePk);
	String genFrmNumBillByProDefPk_RequiresNew(String proDefPk);
}
