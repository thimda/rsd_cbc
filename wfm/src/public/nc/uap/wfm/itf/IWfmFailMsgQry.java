package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFailMsgVO;
public interface IWfmFailMsgQry {
	/**
	 * 获取发往信息中心失败的信息
	 * @param tableName
	 * @return
	 */
	WfmFailMsgVO[] getFailMsgByTabName(String tableName);
}
