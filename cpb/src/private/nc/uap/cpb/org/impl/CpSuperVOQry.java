package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpSuperVOQry;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.SuperVO;

public class CpSuperVOQry implements ICpSuperVOQry {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public SuperVO[] getSuperVOs(SuperVO vo,String where) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from "+vo.getTableName()+" where "+where;
		try {
			List<SuperVO> list = (List<SuperVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(SuperVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new SuperVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}

}
