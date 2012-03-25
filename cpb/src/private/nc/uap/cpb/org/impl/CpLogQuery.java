package nc.uap.cpb.org.impl;

import java.lang.reflect.Array;
import java.util.List;

import nc.jdbc.framework.PersistenceManager;
import nc.uap.cpb.log.ICpLogQuery;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public class CpLogQuery implements ICpLogQuery {

	@Override
	public SuperVO[] getAllLogs(Class voClass) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.queryByCondition(voClass, "");
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public SuperVO getLogByPK(Class voClass,String pk) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return (SuperVO) pt.retrieveByPK(voClass,pk);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere)
			throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.queryByCondition(voClass, strWhere);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere, String[] fields)
			throws LfwBusinessException {
		// TODO Auto-generated method stub
		try {
			String dataSource = null;
			PersistenceManager manager = PersistenceManager
					.getInstance(dataSource);
			List list = (List) manager.retrieveByClause(voClass,
					strWhere, fields);
			return  (SuperVO[]) list.toArray((SuperVO[]) Array.newInstance(
					voClass, 0));
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public String insertLogVO(SuperVO vo) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVO(vo);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public String insertLogVOWithPK(SuperVO vo) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOWithPK(vo);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public String[] insertLogVOWithPKs(SuperVO[] vos)
			throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOWithPKs(vos);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public String[] insertLogVOs(SuperVO[] vos) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOs(vos);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public int updataVO(SuperVO vo) throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.updateVO(vo);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

}
