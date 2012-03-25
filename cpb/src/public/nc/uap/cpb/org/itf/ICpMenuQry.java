package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.MenuItemAdapterVO;

/**
 * 协同菜单查询服务接口
 * 2011-10-13 上午11:11:18  limingf
 */

public interface ICpMenuQry {
	/**
	 * 获取所有菜单分组
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getAllMenuCategory() throws CpbBusinessException;
	
	
	/**
	 * 根据where条件查询菜单分组
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getMenuCategory(String where) throws CpbBusinessException;
	
	/**
	 * 根据where条件查询菜单分组
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItem(String where) throws CpbBusinessException;
	
	/**
	 * 根据主键获取菜单分组
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO getMenuCategorysByPk(String pk_menucategory) throws CpbBusinessException;
	/**
	 * 根据主键查询菜单分组
	 * @param pk_menucategorys
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getMenuCategorysByPks(String[] pk_menucategorys) throws CpbBusinessException;
	/**
	 * 根据菜单分组查找菜单项
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException;
	
	/**
	 * 根据功能节点查找菜单项
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO getMenuItemsByFuncnode(String pk_funnode) throws CpbBusinessException;
	
	/**
	 * 根据功能节点查找菜单项
	 * @param pk_funnodes
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItemsByFuncnodes(String[] pk_funnodes) throws CpbBusinessException;
	/**
	 * 查询菜单分组下的菜单项
	 * @param pk_menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getRootMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException ;
	/**
	 * 查询用户所有的菜单项
	 * @param pk_user  用户主键
	 * @param permission  是否过滤权限
	 * @param filter   是否自定义过滤器
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_user,boolean permission,boolean filter) throws CpbBusinessException ;
	/**
	 * 查询在某菜单组下用户所有的菜单项
	 * @param pk_menucategory
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter) throws CpbBusinessException ;

	/**
	 * 查询在某菜单组下用户所有的菜单项
	 * @param pk_menucategory
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @param orderby   是否排序  按照ordernum 排序
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException ;

	/**
	 * 获得菜单的根
	 * @param pk_menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuRoot[] getMenuRoot(String pk_menucategory) throws CpbBusinessException;
	/**
	 * 根据菜单分类获得菜单 
	 * @param pk_menucategory
	 * @param pk_user
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItems(String pk_menucategory) throws CpbBusinessException;
	
	/**
	 * 根据菜单分类获得菜单, 
	 * @param pk_menucategory
	 * @param pk_user
	 * @permission 是否过滤用户权限
	 * @filter 是否根据filter过滤
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItemsWithPermission(String pk_menucategory,String pk_user,boolean permission,boolean filter) throws CpbBusinessException;
	
	MenuRoot[] getMenuRootWithPermission(String pk_menucategory,String pk_user,boolean permission,boolean filter) throws CpbBusinessException;

	/**
	 * 
	 * @param pk_menucategory
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @param orderby   是否排序  按ordernum排序
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItemsWithPermission(String pk_menucategory,String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException;
	/**
	 * 查询某个菜单下的所有子菜单
	 * @param pk_menuitem
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @param orderby
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItemsByParent(String pk_menuitem,String pk_user,boolean permission,boolean orderby) throws CpbBusinessException;
	
	
	/**
	 * 根据菜单的父获得所有子菜单
	 * @param pk_parent
	 * @return
	 */
	MenuItemAdapterVO[] getMenuItemsByParent(String pk_parent) throws CpbBusinessException;
	
	
}
