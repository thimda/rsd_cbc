package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmTaskTabColVO;

public interface IWfmTaskTabColQry {
	WfmTaskTabColVO[] getTaskTabColVos(String proDefPk, String proDefId, String tabCtrlValue)throws WfmServiceException;
}
