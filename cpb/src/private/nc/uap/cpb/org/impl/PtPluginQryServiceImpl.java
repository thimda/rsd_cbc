package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.IPtPluginQryService;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

/**
 * 插件查询服务实现
 * 
 * @author licza
 * @since 2010年9月10日12:37:25
 */
public class PtPluginQryServiceImpl implements IPtPluginQryService {
	@Override
	public PtExtension[] getAllExtension(String module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (PtExtension[]) dao.queryByCondition(PtExtension.class, " module='" + module + "'");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public PtExtension[] getAllExtension() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (PtExtension[]) dao.queryByCondition(PtExtension.class, " 1=1  ");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public PtExtensionPoint[] getAllExtensionPoint() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (PtExtensionPoint[]) dao.queryByCondition(PtExtensionPoint.class, "");
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public PtExtension[] getExtensionByPoint(String point) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(point);
			List<PtExtension> list = (List<PtExtension>)dao.executeQuery("SELECT * FROM dbo.pt_extension where point=?", parameter, new BeanListProcessor(PtExtension.class));
			return list != null ? list.toArray(new PtExtension[0]) : new PtExtension[0];
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public PtExtension getExtension(String pk_extension) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (PtExtension) dao.retrieveByPK(PtExtension.class, pk_extension);
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}

}
