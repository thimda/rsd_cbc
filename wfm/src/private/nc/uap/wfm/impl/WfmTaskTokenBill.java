package nc.uap.wfm.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskTokenBill;
import nc.uap.wfm.vo.WfmTaskTokenVO;

public class WfmTaskTokenBill implements IWfmTaskTokenBill {
	

	@Override
	public void deleteTaskToken(String taskTokenVoPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(WfmTaskTokenVO.class, taskTokenVoPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public void saveTaskToken(WfmTaskTokenVO taskTokenVo) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(taskTokenVo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteTaskTokenByTaskPk(String taskPk) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_tasktoken where pk_task='" + taskPk + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteTaskTokenByTokenId(String tokenId) throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "delete from wfm_tasktoken where token_id='" + tokenId + "'";
		try {
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		
	}
}
