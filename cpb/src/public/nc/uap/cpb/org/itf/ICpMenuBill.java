package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.DynamicMenuVO;

/**
 * 协同菜单操作服务接口
 * 2011-10-13 上午11:11:34  limingf
 */

public interface ICpMenuBill {
	/**
	 * 根据编辑模式操作菜单项
	 * @param menuitemvo
	 * @return
	 * @throws CpbBusinessException
	 */
	public void updateMenuitem(DynamicMenuVO menuitemvo) throws CpbBusinessException;
	
	/**
	 * 增加菜单项
	 * @param menuitem
	 * @return
	 * @throws CpbBusinessException
	 */
	public String addMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * 删除菜单项
	 * @param menuitem
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * 根据pk删除菜单项
	 * @param pk_menuitem
	 * @throws CpbBusinessException
	 */
	public void delMenuitem(String pk_menuitem) throws CpbBusinessException;
	/**
	 * 更新菜单项
	 * @param menuitem
	 * @throws CpbBusinessException
	 */
	public void updateMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * 增加菜单分组
	 * @param menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public String addMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
	/**
	 * 更新菜单分组
	 * @param menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public String updateMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
	/**
	 * 删除菜单分组
	 * @param menucategory
	 * @throws CpbBusinessException
	 */
	public void delMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
 
}
