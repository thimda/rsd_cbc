package nc.uap.wfm.itf;

import nc.uap.wfm.vo.WfmAttachPurviewVO;

public interface IWfmAttachPurviewQry {
	WfmAttachPurviewVO[] getAttachPurviews(String proDefPk, String proDefId, String portId);
	WfmAttachPurviewVO getAttachPurview(String proDefPk, String proDefId, String portId, String targetId);
}
