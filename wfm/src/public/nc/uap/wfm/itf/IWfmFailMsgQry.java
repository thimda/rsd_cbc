package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFailMsgVO;
public interface IWfmFailMsgQry {
	/**
	 * ��ȡ������Ϣ����ʧ�ܵ���Ϣ
	 * @param tableName
	 * @return
	 */
	WfmFailMsgVO[] getFailMsgByTabName(String tableName);
}
