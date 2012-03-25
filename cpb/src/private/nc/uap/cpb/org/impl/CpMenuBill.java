package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpMenuBill;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.DynamicMenuVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * 2011-10-13 ÉÏÎç11:14:57  limingf
 */

public class CpMenuBill implements ICpMenuBill {
	public void updateMenuitem(DynamicMenuVO menuitemvo) throws CpbBusinessException{
		String action = menuitemvo.getAction();
		PtBaseDAO dao = new PtBaseDAO();
		try {
		if(DynamicMenuVO.ADD_ACTION.equals(action))
			dao.insertVO(menuitemvo.getMenuitemvo());
		else if(DynamicMenuVO.UPDATE_ACTION.equals(action)){
			dao.updateVO(menuitemvo.getMenuitemvo());
		}
		else if(DynamicMenuVO.DEL_ACTION.equals(action)){
				dao.deleteVO(menuitemvo.getMenuitemvo());	
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
	@Override
	public String addMenuitem(CpMenuItemVO vo)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
	@Override
	public String delMenuitem(CpMenuItemVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return vo.getPk_menuitem();
	}

	@Override
	public void delMenuitem(String pk_menuitem) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpMenuItemVO.class, pk_menuitem);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void updateMenuitem(CpMenuItemVO vo)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public String addMenuCategory(CpMenuCategoryVO menucategory)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(menucategory);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return menucategory.getPk_menucategory();
	}



	@Override
	public void delMenuCategory(CpMenuCategoryVO menucategory)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(menucategory);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}



	@Override
	public String updateMenuCategory(CpMenuCategoryVO menucategory)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(menucategory);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return menucategory.getPk_menucategory();
	}
}
