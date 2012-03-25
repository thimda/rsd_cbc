package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmAddSignVO;
public interface IWfmAddSignBill {
	void deleteAddSignVoByTaskPk(String taskPk) throws WfmServiceException;
	void saveAddSignVo(WfmAddSignVO addSignVo) throws WfmServiceException;
}
