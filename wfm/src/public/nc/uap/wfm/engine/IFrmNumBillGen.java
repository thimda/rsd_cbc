package nc.uap.wfm.engine;
/**
 * 自定义流水号扩展接口
 * 
 * @author tianchw
 * 
 */
public interface IFrmNumBillGen {
	/**
	 * 流水号生成接口
	 * 
	 * @param frmDefPk
	 * @param taskPk
	 * @return
	 */
	String getValue(String flowTypePk, String taskPk);
	/**
	 * 是否是前置编码
	 * 
	 * @param frmDefPk
	 * @param taskPk
	 * @return
	 */
	boolean isBefore(String flowTypePk, String taskPk);
	
	String genFrmNumBillByFrmDefPk_RequiresNew(String flowTypePk);
	String genFrmNumBillByProDefPk_RequiresNew(String proDefPk);
}
