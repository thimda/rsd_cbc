package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQuerytemplateVO;

public interface IWfmMyQueryQry {
	
	public WfmQuerytemplateVO[] getQuerytemplateVOsByUserPk(String userPk) throws WfmServiceException;
	public WfmQuerytemplateVO getQuerytemplateVOByPk(String templatePk) throws WfmServiceException;
	

}
