package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlwFrmVO;

public interface IWfmFlwFrmQry {
	/**
	 * ��ȡ���������õ���Ϣ
	 * @param prodef_id
	 * @param port_id
	 * @return
	 * @throws WfmServiceException
	 */
	WfmFlwFrmVO[] getFrmItmPrts(String proDefPk, String proDefId, String portId, String devicePk) throws WfmServiceException;
}
