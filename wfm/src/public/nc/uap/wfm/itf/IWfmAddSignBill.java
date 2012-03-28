package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmBeforeAddSignVO;
public interface IWfmAddSignBill {
	void deleteAddSignVoByTaskPk(String taskPk) throws WfmServiceException;
	void saveAddSignVo(WfmBeforeAddSignVO addSignVo) throws WfmServiceException;
}
