package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpPluginQry;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;
import nc.uap.cpb.persist.dao.PtBaseDAO;

/**
 * 插件查询服务实现
 * 
 * @since 2010年9月10日12:37:25
 */
public class CpPluginQry implements ICpPluginQry {
	@Override
	public CpExtension[] getAllExtension(String module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (CpExtension[]) dao.queryByCondition(CpExtension.class, " module='" + module + "'");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpExtension[] getAllExtension() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (CpExtension[]) dao.queryByCondition(CpExtension.class, " 1=1  ");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpExtensionPoint[] getAllExtensionPoint() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (CpExtensionPoint[]) dao.queryByCondition(CpExtensionPoint.class, "");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@SuppressWarnings("unchecked") @Override
	public CpExtension[] getExtensionByPoint(String point) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(point);
			List<CpExtension> list = (List<CpExtension>)dao.executeQuery("SELECT * FROM dbo.cp_extension where point=?", parameter, new BeanListProcessor(CpExtension.class));
			return list != null ? list.toArray(new CpExtension[0]) : new CpExtension[0];
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpExtension getExtension(String pk_extension) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (CpExtension) dao.retrieveByPK(CpExtension.class, pk_extension);
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

}
