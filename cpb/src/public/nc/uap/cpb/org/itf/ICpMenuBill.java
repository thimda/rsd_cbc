package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.DynamicMenuVO;

/**
 * Эͬ�˵���������ӿ�
 * 2011-10-13 ����11:11:34  limingf
 */

public interface ICpMenuBill {
	/**
	 * ���ݱ༭ģʽ�����˵���
	 * @param menuitemvo
	 * @return
	 * @throws CpbBusinessException
	 */
	public void updateMenuitem(DynamicMenuVO menuitemvo) throws CpbBusinessException;
	
	/**
	 * ���Ӳ˵���
	 * @param menuitem
	 * @return
	 * @throws CpbBusinessException
	 */
	public String addMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * ɾ���˵���
	 * @param menuitem
	 * @return
	 * @throws CpbBusinessException
	 */
	public String delMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * ����pkɾ���˵���
	 * @param pk_menuitem
	 * @throws CpbBusinessException
	 */
	public void delMenuitem(String pk_menuitem) throws CpbBusinessException;
	/**
	 * ���²˵���
	 * @param menuitem
	 * @throws CpbBusinessException
	 */
	public void updateMenuitem(CpMenuItemVO menuitem) throws CpbBusinessException;
	/**
	 * ���Ӳ˵�����
	 * @param menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public String addMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
	/**
	 * ���²˵�����
	 * @param menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public String updateMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
	/**
	 * ɾ���˵�����
	 * @param menucategory
	 * @throws CpbBusinessException
	 */
	public void delMenuCategory(CpMenuCategoryVO menucategory) throws CpbBusinessException;
 
}
