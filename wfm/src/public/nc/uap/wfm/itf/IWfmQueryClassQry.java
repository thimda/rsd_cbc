package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmQueryclassVO;

public interface IWfmQueryClassQry {
	
	public WfmQueryclassVO getQueryClassByPk(String queryclassPk)throws WfmServiceException;
	//�û����ڲ�ѯ��ѯģ��ķ���
	public WfmQueryclassVO[] getExistsTemplateByPk(String userPk)throws WfmServiceException;

}
