package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFailMsgVO;
public interface IWfmFailMsgBill {
	/**
	 * 保存失败的消息
	 * @param failMsgVO
	 */
	void saveFailMsg(WfmFailMsgVO failMsgVO);
	/**
	 * 删除失败的消息
	 * @param failMsgPk
	 */
	void deleteFailMsgByPk(String failMsgPk);
}
