package nc.uap.wfm.itf;

import nc.uap.wfm.vo.WfmAttachPurviewVO;

public interface IWfmAttachPurviewBill {
	void saveAttachPurview(WfmAttachPurviewVO[] vos);
	void deleteAttachPurview(String proDefPk, String proDefId, String portId);
}
