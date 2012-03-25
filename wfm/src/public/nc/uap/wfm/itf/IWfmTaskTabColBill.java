package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmTaskTabColVO;

public interface IWfmTaskTabColBill {
	void saveTaskTabCol(WfmTaskTabColVO taskTabColVos[])throws WfmServiceException;
	void saveTaskTabCol(WfmTaskTabColVO taskTabColVo)throws WfmServiceException;
	void deleteTaskTabCol(String proDefPk, String proDefId,String tabCtrlValue)throws WfmServiceException;
}
