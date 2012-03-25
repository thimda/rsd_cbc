package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpPwdSgyQry;
import nc.uap.cpb.org.vos.CpPwdSygVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
/**
 * 密码安全策略查询服务实现类 2011-6-9 下午02:35:58
 * 
 * @author limingf
 */
public class CpPwdSgyQry implements ICpPwdSgyQry {
	@SuppressWarnings("unchecked") @Override public CpPwdSygVO[] getAllPasswordSecuritys() throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_pwdsyg";
		List<CpPwdSygVO> list;
		try {
			list = (List<CpPwdSygVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpPwdSygVO.class));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if (list != null && list.size() > 0)
			return list.toArray(new CpPwdSygVO[list.size()]);
		return new CpPwdSygVO[] {};
	}
	@SuppressWarnings("unchecked") @Override public CpPwdSygVO[] getAllPtSecurityExcp(String pk_passwordsecurity) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_pwdsyg where pk_passwordsecurity <> ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_passwordsecurity);
		List<CpPwdSygVO> list;
		try {
			list = (List<CpPwdSygVO>) baseDAO.executeQuery(sql, parameter, new BeanProcessor(CpPwdSygVO.class));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if (list != null && list.size() > 0)
			return list.toArray(new CpPwdSygVO[list.size()]);
		return new CpPwdSygVO[] {};
	}
	@Override public CpPwdSygVO getPtPasswordSecurityByName(String name) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_pwdsyg where name = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(name);
		try {
			return (CpPwdSygVO) baseDAO.executeQuery(sql, parameter, new BeanProcessor(CpPwdSygVO.class));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@Override public CpPwdSygVO getPtPasswordSecurityByPk(String pk_passwordsecurity) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_pwdsyg where pk_passwordsecurity = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_passwordsecurity);
		try {
			return (CpPwdSygVO) baseDAO.executeQuery(sql, parameter, new BeanProcessor(CpPwdSygVO.class));
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
