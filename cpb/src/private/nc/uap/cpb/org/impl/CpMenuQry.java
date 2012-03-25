package nc.uap.cpb.org.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.itf.ICpMenuQry;
import nc.uap.cpb.org.menucategory.IMenuItemFilter;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.uap.cpb.org.vos.MenuItemAdapterVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.lang.UFBoolean;
/**
 * 菜单查询服务实现
 * 2011-10-13 上午11:14:22 limingf
 */
public class CpMenuQry implements ICpMenuQry {
	@SuppressWarnings("unchecked") @Override public CpMenuItemVO[] getMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException {
		if (pk_menucategory == null) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menuitem where  pk_menucategory=?";
		parameter.addParam(pk_menucategory);
		try {
			List<CpMenuItemVO> list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpMenuItemVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") @Override public CpMenuItemVO[] getMenuItemsByFuncnodes(String[] pk_funnodes) throws CpbBusinessException {
		if (pk_funnodes == null || pk_funnodes.length < 1) {
			return new CpMenuItemVO[] {};
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_funnodes.length; i++) {
			sb.append("'").append(pk_funnodes[i]).append("'");
			if (i != pk_funnodes.length - 1)
				sb.append(",");
		}
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menuitem where  pk_funnode in(" + sb.toString() + ")";
		try {
			List<CpMenuItemVO> list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpMenuItemVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return new CpMenuItemVO[] {};
	}
	@SuppressWarnings("unchecked") @Override public CpMenuCategoryVO[] getAllMenuCategory() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menucategory";
		try {
			List<CpMenuCategoryVO> list = (List<CpMenuCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpMenuCategoryVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	public CpMenuCategoryVO[] getMenuCategory(String where) throws CpbBusinessException{
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_menucategory  where "+where;
		List<CpMenuCategoryVO> list = null;
		try {
			list = (List<CpMenuCategoryVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpMenuCategoryVO.class));
			if (list == null || list.size() < 1)
				return new CpMenuCategoryVO[] {};
			return list.toArray(new CpMenuCategoryVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	
	public CpMenuItemVO[] getMenuItem(String where) throws CpbBusinessException{
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_menuitem  where "+where;
		List<CpMenuItemVO> list = null;
		try {
			list = (List<CpMenuItemVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpMenuItemVO.class));
			if (list == null || list.size() < 1)
				return new CpMenuItemVO[] {};
			return list.toArray(new CpMenuItemVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuCategoryVO getMenuCategorysByPk(String pk_menucategory) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menucategory where pk_menucategory = ?";
		parameter.addParam(pk_menucategory);
		try {
			List<CpMenuCategoryVO> list = (List<CpMenuCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpMenuCategoryVO[0])[0];
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuCategoryVO[] getMenuCategorysByPks(String[] pk_menucategory) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<pk_menucategory.length;i++){
			sb.append("'").append(pk_menucategory[i]).append("'");
			if(i==pk_menucategory.length-1)sb.append(",");
		}
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menucategory where pk_menucategory in("+sb.toString()+")";
		parameter.addParam(pk_menucategory);
		try {
			List<CpMenuCategoryVO> list = (List<CpMenuCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpMenuCategoryVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return new CpMenuCategoryVO[]{};
	}
	
	@SuppressWarnings("unchecked") @Override public CpMenuItemVO getMenuItemsByFuncnode(String pk_funnode) throws CpbBusinessException {
		if (pk_funnode == null) {
			return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menuitem where  pk_funnode=?";
		parameter.addParam(pk_funnode);
		try {
			List<CpMenuItemVO> list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") @Override public MenuItemAdapterVO[] getMenuItems(String pk_menucategory) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* ,b.* from cp_menuitem as a left join  cp_appsnode as b on(a.pk_funnode = b.pk_appsnode) where  a.pk_menucategory=?";
		parameter.addParam(pk_menucategory);
		List<MenuItemAdapterVO> list;
		try {
			list = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return list.toArray(new MenuItemAdapterVO[0]);
	}
	@SuppressWarnings("unchecked") @Override 
	public MenuItemAdapterVO[] getMenuItemsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* ,b.* from cp_menuitem as a left join  cp_appsnode as b on(a.pk_funnode = b.pk_appsnode) where  a.pk_menucategory=?";
		parameter.addParam(pk_menucategory);
		List<MenuItemAdapterVO> list;
		try {
			list = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new MenuItemAdapterVO[]{};
		List<MenuItemAdapterVO> plist = new ArrayList<MenuItemAdapterVO>();
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes==null)
				permissinnodes = new CpAppsNodeVO[]{};
			Map<String,CpAppsNodeVO> map = new HashMap<String,CpAppsNodeVO>();
			for(CpAppsNodeVO nodevo:permissinnodes){
				map.put(nodevo.getPk_appsnode(), nodevo);
			}
			for(int i=0;i<list.size();i++){		
//				String pk_funnode = list.get(i).getPk_funnode();
//				if(pk_funnode!=null&&!"".equals(pk_funnode.trim())&&map.get(pk_funnode)==null)
//					list.remove(i);
				String pk_funnode = list.get(i).getPk_funnode();
				if(pk_funnode==null||"".equals(pk_funnode)||"~".equals(pk_funnode)||map.get(pk_funnode)!=null){
					plist.add(list.get(i));
				}
			}	
			list = plist;
		}
		//根据菜单分组上注册的插件过滤
		if(filter){
			ICpMenuQry menuqry = NCLocator.getInstance().lookup(ICpMenuQry.class);
			CpMenuCategoryVO categoryvo = menuqry.getMenuCategorysByPk(pk_menucategory);
			String filterclass = categoryvo.getFilterclass();		
			if(filterclass!=null){
				if(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter){
					IMenuItemFilter excuter = (IMenuItemFilter) LfwClassUtil.newInstance(filterclass);
					return excuter.filter(list.toArray(new MenuItemAdapterVO[0]));	
				}
			}
		}		
		return list.toArray(new MenuItemAdapterVO[0]);
	}
	
	public MenuRoot[] getMenuRootWithPermission(String pk_menucategory,String pk_user,boolean permission,boolean filter) throws CpbBusinessException{		
		MenuItemAdapterVO[] menuitemadapters = getMenuItemsWithPermission(pk_menucategory,pk_user,permission,filter);
		List<MenuItemAdapterVO> adaptervolist = Arrays.asList(menuitemadapters);		
		ComputerAdapterMenuChild(adaptervolist);
		List<MenuRoot> list  = 	LoadMenuRootFromMenuAdapter(adaptervolist);
		if(list==null)
			return new MenuRoot[]{};
		return list.toArray(new MenuRoot[0]);
	}

	
	@SuppressWarnings("unchecked") @Override 
	public MenuItemAdapterVO[] getMenuItemsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* ,b.* from cp_menuitem as a left join  cp_appsnode as b on(a.pk_funnode = b.pk_appsnode) where  a.pk_menucategory=?";
		//排序
		if(orderby)
			sql += " order by a.ordernum asc";
		parameter.addParam(pk_menucategory);
		List<MenuItemAdapterVO> list;
		try {
			list = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new MenuItemAdapterVO[]{};
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes!=null&&list!=null){
				Map<String,CpAppsNodeVO> map = new HashMap<String,CpAppsNodeVO>();
				for(CpAppsNodeVO nodevo:permissinnodes){
					map.put(nodevo.getPk_appsnode(), nodevo);
				}
				for(int i=0;i<list.size();i++){				
					if(map.get(list.get(i).getPk_funnode())==null)
						list.remove(i);
				}
			}
		}
		//根据菜单分组上注册的插件过滤
		if(filter){
			ICpMenuQry menuqry = NCLocator.getInstance().lookup(ICpMenuQry.class);
			CpMenuCategoryVO categoryvo = menuqry.getMenuCategorysByPk(pk_menucategory);
			String filterclass = categoryvo.getFilterclass();		
			if(filterclass!=null){
				if(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter){
					IMenuItemFilter excuter = (IMenuItemFilter) LfwClassUtil.newInstance(filterclass);
					return excuter.filter(list.toArray(new MenuItemAdapterVO[0]));	
				}
			}
		}
		return list.toArray(new MenuItemAdapterVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override 
	public MenuItemAdapterVO[] getMenuItemsByParent(String pk_menuitem, String pk_user,boolean permission,boolean orderby) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();		
		
		String sql1 = "select pk_menuitem,pk_parent from cp_menuitem";
		List<Map<String,String>> pkmaplist = null;
		try {
			pkmaplist = (List<Map<String, String>>) dao.executeQuery(sql1, new MapListProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		List<String> pklist = new ArrayList<String>();
		pklist = getPkTrees(pklist,pkmaplist,pk_menuitem);
		//移除自己
		pklist.remove(pk_menuitem);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<pklist.size();i++){
			sb.append("'").append(pklist.get(i)).append("'");
			if(i!=pklist.size()-1)sb.append(",");
		}
		
		String sql = "select a.* ,b.* from cp_menuitem as a left join  cp_appsnode as b on(a.pk_funnode = b.pk_appsnode) where  a.pk_menuitem in ("+sb.toString()+")";
		//排序
		if(orderby)
			sql += " order by a.ordernum asc";
		List<MenuItemAdapterVO> list;
		try {
			list = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new MenuItemAdapterVO[]{};
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes!=null&&list!=null){
				Map<String,CpAppsNodeVO> map = new HashMap<String,CpAppsNodeVO>();
				for(CpAppsNodeVO nodevo:permissinnodes){
					map.put(nodevo.getPk_appsnode(), nodevo);
				}
				for(int i=0;i<list.size();i++){				
					if(map.get(list.get(i).getPk_funnode())==null)
						list.remove(i);
				}
			}
		}
		return list.toArray(new MenuItemAdapterVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_user,boolean permission,boolean filter) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* from cp_menuitem as a";
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes==null)
				permissinnodes = new CpAppsNodeVO[] {};
			String[] pk_funnodes = new String[permissinnodes.length];
			for(int i=0;i<permissinnodes.length;i++){
				pk_funnodes[i] = permissinnodes[i].getPk_appsnode();
			}
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<pk_funnodes.length;i++){
				sb.append("'").append(pk_funnodes[i]).append("'");
				if(i==pk_funnodes.length-1)sb.append(",");
			}
			sql +=" where a.pk_funnode in("+sb.toString()+")";				
		}		
		List<CpMenuItemVO> list;
		try {
			list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}		
		if(list==null)
			return new CpMenuItemVO[]{};
		//根据菜单分组上注册的插件过滤
		if(filter){
			ICpMenuQry menuqry = NCLocator.getInstance().lookup(ICpMenuQry.class);
			List<String> categorylist = new ArrayList<String>();
			for(CpMenuItemVO itemvo:list){
				if(!categorylist.contains(itemvo.getPk_menucategory()))
					categorylist.add(itemvo.getPk_menucategory());
			}
			CpMenuCategoryVO[] categoryvos = menuqry.getMenuCategorysByPks(categorylist.toArray(new String[0]));
			CpMenuItemVO[] filterItemvos = list.toArray(new CpMenuItemVO[0]);
			for(int i=0;i<categoryvos.length;i++){
				String filterclass = categoryvos[i].getFilterclass();		
				if(filterclass!=null){
					if(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter){
						IMenuItemFilter excuter = (IMenuItemFilter) LfwClassUtil.newInstance(filterclass);
						filterItemvos = excuter.filter(filterItemvos);	
					}
				}
			}
			return filterItemvos;
		}
		return list.toArray(new CpMenuItemVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* from cp_menuitem as a where  a.pk_menucategory=?";
		parameter.addParam(pk_menucategory);
		List<CpMenuItemVO> list;
		try {
			list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new CpMenuItemVO[]{};
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes!=null&&list!=null){
				Map<String,CpAppsNodeVO> map = new HashMap<String,CpAppsNodeVO>();
				for(CpAppsNodeVO nodevo:permissinnodes){
					map.put(nodevo.getPk_appsnode(), nodevo);
				}
				for(int i=0;i<list.size();i++){				
					if(map.get(list.get(i).getPk_funnode())==null)
						list.remove(i);
				}
			}
		}
		//根据菜单分组上注册的插件过滤
		if(filter){
			ICpMenuQry menuqry = NCLocator.getInstance().lookup(ICpMenuQry.class);
			CpMenuCategoryVO categoryvo = menuqry.getMenuCategorysByPk(pk_menucategory);
			String filterclass = categoryvo.getFilterclass();		
			if(filterclass!=null){
				if(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter){
					IMenuItemFilter excuter = (IMenuItemFilter) LfwClassUtil.newInstance(filterclass);
					return excuter.filter(list.toArray(new CpMenuItemVO[0]));	
				}
			}
		}
		return list.toArray(new CpMenuItemVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select a.* from cp_menuitem as a where  a.pk_menucategory=?";
		//排序
		if(orderby)
			sql += " order by a.ordernum asc";
		parameter.addParam(pk_menucategory);
		List<CpMenuItemVO> list;
		try {
			list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new CpMenuItemVO[]{};
		//过滤没有权限的节点
		if(permission){
			ICpAppsNodeQry nodeqry = NCLocator.getInstance().lookup(ICpAppsNodeQry.class);
			CpAppsNodeVO[] permissinnodes = nodeqry.getNodeWithPermission(pk_user);
			if(permissinnodes!=null&&list!=null){
				Map<String,CpAppsNodeVO> map = new HashMap<String,CpAppsNodeVO>();
				for(CpAppsNodeVO nodevo:permissinnodes){
					map.put(nodevo.getPk_appsnode(), nodevo);
				}
				for(int i=0;i<list.size();i++){				
					if(map.get(list.get(i).getPk_funnode())==null)
						list.remove(i);
				}
			}
		}
		//根据菜单分组上注册的插件过滤
		if(filter){
			ICpMenuQry menuqry = NCLocator.getInstance().lookup(ICpMenuQry.class);
			CpMenuCategoryVO categoryvo = menuqry.getMenuCategorysByPk(pk_menucategory);
			String filterclass = categoryvo.getFilterclass();		
			if(filterclass!=null){
				if(LfwClassUtil.newInstance(filterclass) instanceof IMenuItemFilter){
					IMenuItemFilter excuter = (IMenuItemFilter) LfwClassUtil.newInstance(filterclass);
					return excuter.filter(list.toArray(new CpMenuItemVO[0]));	
				}
			}
		}
		return list.toArray(new CpMenuItemVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override public MenuItemAdapterVO[] getMenuItemsByParent(String pk_parent) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select a.*,b.* from cp_menuitem as a left join  cp_appsnode as b on(a.pk_funnode = b.pk_appsnode) where a.pk_parent=?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_parent);
		List<MenuItemAdapterVO> list;
		try {
			list = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		if(list==null)
			return new MenuItemAdapterVO[]{};
		return list.toArray(new MenuItemAdapterVO[0]);
	}
	
	@SuppressWarnings("unchecked") @Override 
	public CpMenuItemVO[] getRootMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException {
		if (pk_menucategory == null) {
		return null;
		}
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menuitem where  pk_menucategory=? and isnull(pk_parent,'~')='~'";
		parameter.addParam(pk_menucategory);
		try {
		List<CpMenuItemVO> list = (List<CpMenuItemVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpMenuItemVO.class));
		if (list != null && !list.isEmpty()) {
		return list.toArray(new CpMenuItemVO[0]);
		}
		} catch (DAOException e) {
		LfwLogger.error(e.getMessage(), e.getCause());
		throw new CpbBusinessException(e);
		}
		return null;
		}

/**
 * 根据菜单分组查询全部菜单
 * modify by lisw 2012.2.13,废除之前逻辑，重新获取菜单 
 */
	@SuppressWarnings("unchecked") @Override 
	public MenuRoot[] getMenuRoot(String pk_menucategory) throws CpbBusinessException {
		List<MenuRoot> list = null;
		//MenuItemAdapterVOProcessor
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_menuitem  as a left join cp_appsnode as b on a.pk_funnode = b.pk_appsnode where pk_menucategory = ?  order by a.ordernum";
		parameter.addParam(pk_menucategory);
		List<MenuItemAdapterVO> adaptervolist = null;
		try {
			adaptervolist = (List<MenuItemAdapterVO>) dao.executeQuery(sql, parameter, new MenuItemAdapterVOProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		ComputerAdapterMenuChild(adaptervolist);
		list = 	LoadMenuRootFromMenuAdapter(adaptervolist);
		if(list==null)
			return new MenuRoot[]{};
		return list.toArray(new MenuRoot[0]);
		/*
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();		
		String sql = "select c.*,d.* from (select a.*,b.code as pcode,b.name as pname from cp_menuitem a " + "left join  cp_menuitem b on(a.pk_parent = b.pk_menuitem) where  a.pk_menucategory=? "
				+ "and (isnull(b.pk_parent, '~') = '~')) c " + "left join cp_appsnode d on (c.pk_funnode = d.pk_appsnode)";
		parameter.addParam(pk_menucategory);
		List<MenuRoot> list = null;
		try {
			list = (List<MenuRoot>) dao.executeQuery(sql, parameter, new MenuRootProcessor());
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return list.toArray(new MenuRoot[0]);
		*/
	}
	/**
	 * 计算所有的跟节点
	 * @param adaptervolist
	 * @return
	 */
	private List<MenuRoot> LoadMenuRootFromMenuAdapter(List<MenuItemAdapterVO> adaptervolist){
		List<MenuRoot> rootlist = new ArrayList<MenuRoot>();
		if(adaptervolist != null){
			for(MenuItemAdapterVO vo : adaptervolist){
				String PK_parent = vo.getPk_parent();
				if(PK_parent == null || PK_parent.isEmpty() || PK_parent.equals("~")){
					MenuRoot root = new MenuRoot(vo.getTitle(),vo.getMenuitem().getCode());
					root.setIcon(vo.getMenuitem().getIconpath());
					root.setPk_parent(vo.getPk_menuitem());
					rootlist.add(root);
					root.setNodes(vo.getChildrenMenu());
				}	
			}
		}
		return rootlist;
	}
	/**
	 * 计算出所有的子节点
	 * @param adaptervolist
	 */
	private void ComputerAdapterMenuChild(List<MenuItemAdapterVO> adaptervolist){
		if(null != adaptervolist){
			for(int i=0;i<adaptervolist.size();i++){
				MenuItemAdapterVO curmenu = adaptervolist.get(i);
				for(int j=0;j<adaptervolist.size();j++){
					if(i== j)
						continue;
					MenuItemAdapterVO childmenu = adaptervolist.get(j);
					if(childmenu.getPk_parent() != null)
						if(childmenu.getPk_parent().equals(curmenu.getPk_menuitem())){
							curmenu.addChildMenu(childmenu);
						}
				}
			}
		}
	}
	/**
	 * 构造MenuRoot
	 * 
	 * @param menuitemlist
	 * @return
	 * @throws CpbBusinessException
	 */
	@SuppressWarnings("unused") private MenuRoot[] buildMenuRoot(List<CpMenuItemVO> menuitemlist) throws CpbBusinessException {
		if (menuitemlist == null || menuitemlist.size() < 1)
			return new MenuRoot[] {};
		MenuRoot[] roots = new MenuRoot[menuitemlist.size()];
		for (int i = 0; i < menuitemlist.size(); i++) {
			CpMenuItemVO menuitem = menuitemlist.get(i);
			MenuRoot root = new MenuRoot(menuitem.getName(), menuitem.getCode());
			MenuItemAdapterVO[] vos = getMenuItemsByParent(menuitem.getPk_menuitem());
			root.setNodes(Arrays.asList(vos));
			roots[i] = root;
		}
		return roots;
	}
	public class MenuItemAdapterVOProcessor implements ResultSetProcessor {
		private static final long serialVersionUID = 6491811282825432325L;
		public Object handleResultSet(ResultSet rs) throws SQLException {
			List<MenuItemAdapterVO> list = new ArrayList<MenuItemAdapterVO>();
			while (rs.next()) {
				MenuItemAdapterVO menuitemadaptervo = new MenuItemAdapterVO();
				CpMenuItemVO menuitem = new CpMenuItemVO();
				CpAppsNodeVO appsnode = new CpAppsNodeVO();
				menuitem.setCode(rs.getString("code"));
				menuitem.setName(rs.getString("name"));
				menuitem.setIconpath(rs.getString("iconpath"));
				menuitem.setIsnotleaf(new UFBoolean(rs.getString("isnotleaf")));
				menuitem.setMenuitemdes(rs.getString("menuitemdes"));
				menuitem.setPk_funnode(rs.getString("pk_funnode"));
				menuitem.setPk_menucategory(rs.getString("pk_menucategory"));
				menuitem.setPk_parent(rs.getString("pk_parent"));
				menuitem.setPk_menuitem(rs.getString("pk_menuitem"));
				appsnode.setActiveflag(new UFBoolean(rs.getString("activeflag")));
				appsnode.setI18nname(rs.getString("i18nname"));
				appsnode.setId(rs.getString("id"));
				appsnode.setPk_appscategory(rs.getString("pk_appscategory"));
				appsnode.setPk_appsnode(rs.getString("pk_appsnode"));
				appsnode.setTitle(rs.getString("title"));
				appsnode.setUrl(rs.getString("url"));
				menuitemadaptervo.setPk_menuitem(menuitem.getPk_menuitem());
				menuitemadaptervo.setMenuitem(menuitem);
				menuitemadaptervo.setPk_parent(menuitem.getPk_parent());
				menuitemadaptervo.setTitle(menuitem.getName());
				menuitemadaptervo.setPk_funnode(menuitem.getPk_funnode());
				menuitemadaptervo.setFunnode(appsnode);
				list.add(menuitemadaptervo);
			}
			return list;
		}
	}
	public class MenuRootProcessor implements ResultSetProcessor {
		private static final long serialVersionUID = -8126917569173826972L;
		public Object handleResultSet(ResultSet rs) throws SQLException {
			Map<String, MenuRoot> map = new HashMap<String, MenuRoot>();
			while (rs.next()) {
				String pk_parent = rs.getString("pk_parent");
				MenuRoot root = map.get(pk_parent);
				if (root == null) {
					if (pk_parent == null || "".equals(pk_parent)) {
						String name = rs.getString("name");
						String pk_menuitem = rs.getString("pk_menuitem");
						root = map.get(pk_menuitem);
						if(root == null){
							root = new MenuRoot(rs.getString("name"), rs.getString("code"));
							map.put(pk_menuitem, root);
						}
						root.setPk_parent(pk_menuitem);
						root.setIcon(rs.getString("iconpath"));
					} else {
						root = new MenuRoot(rs.getString("pname"), rs.getString("pcode"));
						root.setPk_parent(rs.getString("pk_parent"));
						root.setIcon(rs.getString("iconpath"));
						map.put(rs.getString("pk_parent"), root);
					}
				}
				if (!(pk_parent == null || "".equals(pk_parent))) {
					MenuItemAdapterVO menuitemadaptervo = new MenuItemAdapterVO();
					CpMenuItemVO menuitem = new CpMenuItemVO();
					CpAppsNodeVO appsnode = new CpAppsNodeVO();
					menuitem.setCode(rs.getString("code"));
					menuitem.setName(rs.getString("name"));
					menuitem.setIconpath(rs.getString("iconpath"));
					menuitem.setIsnotleaf(new UFBoolean(rs.getString("isnotleaf")));
					menuitem.setMenuitemdes(rs.getString("menuitemdes"));
					menuitem.setPk_funnode(rs.getString("pk_funnode"));
					menuitem.setPk_menucategory(rs.getString("pk_menucategory"));
					menuitem.setPk_parent(rs.getString("pk_parent"));
					menuitem.setPk_menuitem(rs.getString("pk_menuitem"));
					appsnode.setActiveflag(new UFBoolean(rs.getString("activeflag")));
					appsnode.setI18nname(rs.getString("i18nname"));
					appsnode.setId(rs.getString("id"));
					appsnode.setPk_appscategory(rs.getString("pk_appscategory"));
					appsnode.setPk_appsnode(rs.getString("pk_appsnode"));
					appsnode.setTitle(rs.getString("title"));
					appsnode.setUrl(rs.getString("url"));
					menuitemadaptervo.setPk_menuitem(menuitem.getPk_menuitem());
					menuitemadaptervo.setMenuitem(menuitem);
					menuitemadaptervo.setPk_parent(menuitem.getPk_parent());
					menuitemadaptervo.setTitle(menuitem.getName());
					menuitemadaptervo.setPk_funnode(menuitem.getPk_funnode());
					menuitemadaptervo.setFunnode(appsnode);
					root.addNode(menuitemadaptervo);
				}
			}
			List<MenuRoot> rootlist = new ArrayList<MenuRoot>();
			Iterator<MenuRoot> ite = map.values().iterator();
			while (ite.hasNext()) {
				rootlist.add(ite.next());
			}
			return rootlist;
		}
	}
	
	private List<String> getPkTrees(List<String> pklist,List<Map<String,String>> pkmaplist,String pk_menuitem){
		if(pkmaplist==null)return null;
		pklist.add(pk_menuitem);
		for(int i=0;i<pkmaplist.size();i++){
			Map<String,String> map = pkmaplist.get(i);
			if(pk_menuitem.equals(map.get("pk_parent"))){
				getPkTrees(pklist,pkmaplist,map.get("pk_menuitem"));
			}
		}
		return pklist;
	}
}
