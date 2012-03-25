package nc.uap.cpb.org.impl;

import java.lang.reflect.Method;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.ctrl.office.data.OfficeFileVO;
import nc.vo.pub.SuperVO;

public class CpSuperVOBill implements ICpSuperVOBill {
	@Override
	public void deleteSuperVO(SuperVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@Override
	public void deleteByPK(Class className, String pk) throws CpbBusinessException{
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(className, pk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	
	@Override
	public void deleteSuperVOs(SuperVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public String insertSuperVO(SuperVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			CpSuperVOBill.invokeMethod(vo, "setDr", new Object[] { 0 });
			return dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object invokeMethod(Object owner, String methodName, Object[] args) {
		Class<? extends Object> ownerClass = owner.getClass();
		Class[] argsClass = null;
		if (args != null) {
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				if (args[i] == null) {
					return null;
				}
				argsClass[i] = args[i].getClass();
			}
		}
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName, argsClass);
			return method.invoke(owner, args);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public String[] insertSuperVOs(SuperVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		if (vos == null || vos.length == 0) {
			return null;
		}
		SuperVO superVo = null;
		for (int i = 0; i < vos.length; i++) {
			superVo = vos[i];
			superVo = (SuperVO) CpSuperVOBill.invokeMethod(superVo, "setDr", new Object[] { 0 });
		}
		try {
			return dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void updateSuperVO(SuperVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void updateSuperVOs(SuperVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public String mergeSuperVO(SuperVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String pk = "";
		try {
			pk = vo.getPrimaryKey();
			if(pk == null || pk.equals(""))				
				pk = dao.insertVO(vo);
			else
			{
				SuperVO temp = (SuperVO)dao.retrieveByPK(vo.getClass(), pk);
				if(null != temp){					
					dao.updateVO(vo);
				}
				else
					pk = dao.insertVOWithPK(vo);
			}
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new CpbBusinessException(e);
		}
		return pk;
	}
}
