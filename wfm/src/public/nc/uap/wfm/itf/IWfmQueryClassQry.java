package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQueryclassVO;

public interface IWfmQueryClassQry {
	
	public WfmQueryclassVO getQueryClassByPk(String queryclassPk)throws WfmServiceException;
	//用户存在查询查询模板的分组
	public WfmQueryclassVO[] getExistsTemplateByPk(String userPk)throws WfmServiceException;

}
