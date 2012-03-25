package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlwFrmVO;

public interface IWfmFlwFrmBill {
	/**
	 * 保存表单流程的设置
	 * @param frmItmPrt
	 * @throws WfmServiceException
	 */
	void saveFlwFrm(WfmFlwFrmVO flwFrmVo) throws WfmServiceException;
	void saveFlwFrm(WfmFlwFrmVO[] flwFrmVos) throws WfmServiceException;
	/**
	 * 删除表单流程的设置
	 * @param proDefId
	 * @param prtId
	 * @throws WfmServiceException
	 */
	boolean deleteFrmItmPrt(String proDefPk, String proDefId, String prtId) throws WfmServiceException;
}
