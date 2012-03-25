package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQueryconditionVO;

public interface IWfmConditonQry {
	
	public WfmQueryconditionVO[] getQueryConditionVOByTemplatePk(String templatePk) throws WfmServiceException;

}
