package nc.uap.portal.ctrl.office.impl;

import java.lang.reflect.Array;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.PersistenceManager;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.sign.IsignLogQuery;
import nc.uap.portal.ctrl.office.data.sign.SignlogVO;
import nc.vo.pub.SuperVO;

public class SignLogQuery implements IsignLogQuery {

	public SignlogVO[] getAllSignLogs() throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return (SignlogVO[]) pt.queryByCondition(SignlogVO.class, "");
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}
	
	public SignlogVO getSignLogByPK(String pk) throws LfwBusinessException{
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return (SignlogVO) pt.retrieveByPK(SignlogVO.class,pk);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	public SignlogVO[] getSignLogsByCondition(String strWhere)
			throws LfwBusinessException {
		// TODO Auto-generated method stub
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return (SignlogVO[]) pt.queryByCondition(SignlogVO.class, strWhere);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}
	
	public SignlogVO[] getSignLogsByCondition(String strWhere, String[] fields)
			throws LfwBusinessException {
		try {
			String dataSource = null;
			PersistenceManager manager = PersistenceManager
					.getInstance(dataSource);
			List list = (List) manager.retrieveByClause(SignlogVO.class,
					strWhere, fields);
			return (SignlogVO[]) list.toArray((SignlogVO[]) Array.newInstance(
					SignlogVO.class, 0));
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}
	
	public String insertSignLogVOWithPK(SignlogVO vo) throws LfwBusinessException{
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOWithPK(vo);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}
	
	public String[] insertSignLogVOWithPKs(SignlogVO[] vos)
			throws LfwBusinessException{
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOWithPKs(vos);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	public String insertSignLogVO(SignlogVO vo) throws LfwBusinessException{
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVO(vo);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

	public String[] insertSignLogVOs(SignlogVO[] vos) throws LfwBusinessException{
		PtBaseDAO pt = new PtBaseDAO();
		try {
			return pt.insertVOs(vos);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}

}
