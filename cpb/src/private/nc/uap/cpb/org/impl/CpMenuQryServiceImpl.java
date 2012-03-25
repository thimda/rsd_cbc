package nc.uap.cpb.org.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpMenuQryService;
import nc.uap.cpb.org.vos.MenuAdapterVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpMenuQryServiceImpl implements ICpMenuQryService{
	public MenuAdapterVO[] getAllMenuCategorys() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select a.pk_menucategory,a.id,a.title from cp_menucategory a";
		List<MenuAdapterVO> list;
		try {
			list = (List<MenuAdapterVO>) dao.executeQuery(sql, new MenuCategoryAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return list.toArray(new MenuAdapterVO[0]);
	}
	public MenuAdapterVO[] getAllMenus() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql1 = "select a.pk_menucategory,a.id,a.title from cp_menucategory as a";
		String sql2 = "select b.code,b.name,b.pk_menuitem,b.pk_parent,b.pk_menucategory from cp_menuitem as b";
		List<MenuAdapterVO> list1;
		List<MenuAdapterVO> list2;
		try {
			list1 = (List<MenuAdapterVO>) dao.executeQuery(sql1, new MenuCategoryAdapterVOProcessor());
			list2 = (List<MenuAdapterVO>) dao.executeQuery(sql2, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		list1.addAll(list2);
		return list1.toArray(new MenuAdapterVO[0]);
	}
	
	public class MenuItemAdapterVOProcessor implements ResultSetProcessor {
		private static final long serialVersionUID = 6491811282825432325L;
		public Object handleResultSet(ResultSet rs) throws SQLException {
			List<MenuAdapterVO> list = new ArrayList<MenuAdapterVO>();
			while (rs.next()) {
				MenuAdapterVO menuadaptervo = new MenuAdapterVO();				
				menuadaptervo.setId(rs.getString("pk_menuitem"));
				String pk_parent = rs.getString("pk_parent");
				if(pk_parent==null||"".equals(pk_parent))
					menuadaptervo.setParentid(rs.getString("pk_parent"));
				else menuadaptervo.setParentid(rs.getString("pk_appscategory"));
				menuadaptervo.setTitle(rs.getString("name"));
				menuadaptervo.setType(MenuAdapterVO.MENU_TYPE);
				list.add(menuadaptervo);
			}
			return list;
		}
	}
	
	public class MenuCategoryAdapterVOProcessor implements ResultSetProcessor {
		private static final long serialVersionUID = 6491811282825432325L;
		public Object handleResultSet(ResultSet rs) throws SQLException {
			List<MenuAdapterVO> list = new ArrayList<MenuAdapterVO>();
			while (rs.next()) {
				MenuAdapterVO menuadaptervo = new MenuAdapterVO();				
				menuadaptervo.setId(rs.getString("pk_appscategory"));
				menuadaptervo.setParentid(null);
				menuadaptervo.setTitle(rs.getString("title"));
				menuadaptervo.setType(MenuAdapterVO.CATEGORY_TYPE);
				list.add(menuadaptervo);
			}
			return list;
		}
	}

}
