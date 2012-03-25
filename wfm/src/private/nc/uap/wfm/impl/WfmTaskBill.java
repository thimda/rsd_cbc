package nc.uap.wfm.impl;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.builder.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmTaskBill;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmTaskVO;
public class WfmTaskBill implements IWfmTaskBill {
	public Task asynTask(Task task) throws WfmServiceException {
		if (task == null) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		WfmTaskVO taskVO = BeanConvert.toTaskVO(task);
		try {
			if (taskVO.getDr() == null) {
				taskVO.setDr(new Integer(0));
			}
			if (taskVO.getPk_task() == null || taskVO.getPk_task().length() == 0) {
				dao.insertVO(taskVO);
			} else {
				dao.updateVO(taskVO);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		task.setPk_task(taskVO.getPk_task());
		return task;
	}
	public boolean bogusDeleteTask(String taskPk) throws WfmServiceException {
		if (taskPk == null || taskPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("update wfm_task set dr=1 where pk_task='" + taskPk + "'");
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override public boolean realDeleteTask(String taskPk) throws WfmServiceException {
		if (taskPk == null || taskPk.length() == 0) {
			return false;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete wfm_task where pk_task='" + taskPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return true;
	}
	@Override public WfmTaskVO saveOrUpate(WfmTaskVO vo) throws WfmServiceException {
		if (vo == null) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			if (vo.getPk_task() == null || vo.getPk_task().length() == 0) {
				dao.insertVO(vo);
			} else {
				dao.updateVO(vo);
			}
			return vo;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@Override public void deleteTaskByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return;
		}
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String sql = "delete from wfm_task where pk_proins='" + proInsPk + "'";
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
