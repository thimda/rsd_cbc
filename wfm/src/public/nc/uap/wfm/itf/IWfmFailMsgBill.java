package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFailMsgVO;
public interface IWfmFailMsgBill {
	/**
	 * ����ʧ�ܵ���Ϣ
	 * @param failMsgVO
	 */
	void saveFailMsg(WfmFailMsgVO failMsgVO);
	/**
	 * ɾ��ʧ�ܵ���Ϣ
	 * @param failMsgPk
	 */
	void deleteFailMsgByPk(String failMsgPk);
}
