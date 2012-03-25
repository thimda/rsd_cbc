package nc.uap.wfm.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmVirtualRoleQry;
import nc.uap.wfm.vo.WfmVirtualRoleVO;
/**
 * 虚拟角色查询实现 2011-4-26 上午09:20:53
 * 
 * @author limingf
 */
public class WfmVirtualRoleQry implements IWfmVirtualRoleQry {
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO[] getAllRoles() throws WfmServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from wfm_virtualrole";
		try {
			List<WfmVirtualRoleVO> list = (List<WfmVirtualRoleVO>) dao.executeQuery(sql, new BeanListProcessor(WfmVirtualRoleVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmVirtualRoleVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO getRoleByCode(String code) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select p.* from wfm_virtualrole p where p.code = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(code);
		try {
			List<WfmVirtualRoleVO> list = (List<WfmVirtualRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmVirtualRoleVO.class));
			if (list != null && list.size() > 0)
				return list.toArray(new WfmVirtualRoleVO[list.size()])[0];
			return null;
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO getRoleByName(String name) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select p.* from wfm_virtualrole p where p.name = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(name);
		try {
			List<WfmVirtualRoleVO> list = (List<WfmVirtualRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmVirtualRoleVO.class));
			if (list != null && list.size() > 0)
				return list.toArray(new WfmVirtualRoleVO[list.size()])[0];
			return null;
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO getRoleByName(String pk_group, String name) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select p.* from wfm_virtualrole p where p.pk_group=? and p.name = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_group);
		parameter.addParam(name);
		try {
			List<WfmVirtualRoleVO> list = (List<WfmVirtualRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmVirtualRoleVO.class));
			if (list != null && list.size() > 0)
				return list.toArray(new WfmVirtualRoleVO[list.size()])[0];
			return null;
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO[] getRoleByPk(String[] pk_roles) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		if (pk_roles == null || pk_roles.length < 1)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_roles.length; i++) {
			sb.append("'").append(pk_roles[i]).append("'");
			if (i != pk_roles.length - 1)
				sb.append(",");
		}
		String sql = "select * from wfm_virtualrole p  where p.pk_virtualrole in(" + sb.toString() + ")";
		List<WfmVirtualRoleVO> list = null;
		try {
			list = (List<WfmVirtualRoleVO>) baseDAO.executeQuery(sql, new BeanListProcessor(WfmVirtualRoleVO.class));
			if (list == null || list.size() < 1)
				return new WfmVirtualRoleVO[] {};
			return list.toArray(new WfmVirtualRoleVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmVirtualRoleVO[] getRoleByGroup(String pk_group) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select p.* from wfm_virtualrole p where p.pk_group = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_group);
		try {
			List<WfmVirtualRoleVO> list = (List<WfmVirtualRoleVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmVirtualRoleVO.class));
			return (WfmVirtualRoleVO[]) list.toArray(new WfmVirtualRoleVO[list.size()]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new WfmServiceException(e);
		}
	}
}
