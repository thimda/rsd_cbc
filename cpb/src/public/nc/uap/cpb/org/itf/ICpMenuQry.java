package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.menuitem.MenuRoot;
import nc.uap.cpb.org.vos.CpMenuCategoryVO;
import nc.uap.cpb.org.vos.CpMenuItemVO;
import nc.uap.cpb.org.vos.MenuItemAdapterVO;

/**
 * Эͬ�˵���ѯ����ӿ�
 * 2011-10-13 ����11:11:18  limingf
 */

public interface ICpMenuQry {
	/**
	 * ��ȡ���в˵�����
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getAllMenuCategory() throws CpbBusinessException;
	
	
	/**
	 * ����where������ѯ�˵�����
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getMenuCategory(String where) throws CpbBusinessException;
	
	/**
	 * ����where������ѯ�˵�����
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItem(String where) throws CpbBusinessException;
	
	/**
	 * ����������ȡ�˵�����
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO getMenuCategorysByPk(String pk_menucategory) throws CpbBusinessException;
	/**
	 * ����������ѯ�˵�����
	 * @param pk_menucategorys
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuCategoryVO[] getMenuCategorysByPks(String[] pk_menucategorys) throws CpbBusinessException;
	/**
	 * ���ݲ˵�������Ҳ˵���
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException;
	
	/**
	 * ���ݹ��ܽڵ���Ҳ˵���
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO getMenuItemsByFuncnode(String pk_funnode) throws CpbBusinessException;
	
	/**
	 * ���ݹ��ܽڵ���Ҳ˵���
	 * @param pk_funnodes
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getMenuItemsByFuncnodes(String[] pk_funnodes) throws CpbBusinessException;
	/**
	 * ��ѯ�˵������µĲ˵���
	 * @param pk_menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getRootMenuItemsByCategory(String pk_menucategory) throws CpbBusinessException ;
	/**
	 * ��ѯ�û����еĲ˵���
	 * @param pk_user  �û�����
	 * @param permission  �Ƿ����Ȩ��
	 * @param filter   �Ƿ��Զ��������
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_user,boolean permission,boolean filter) throws CpbBusinessException ;
	/**
	 * ��ѯ��ĳ�˵������û����еĲ˵���
	 * @param pk_menucategory
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter) throws CpbBusinessException ;

	/**
	 * ��ѯ��ĳ�˵������û����еĲ˵���
	 * @param pk_menucategory
	 * @param pk_user
	 * @param permission
	 * @param filter
	 * @param orderby   �Ƿ�����  ����ordernum ����
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpMenuItemVO[] getCpMenuItemVOsWithPermission(String pk_menucategory, String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException ;

	/**
	 * ��ò˵��ĸ�
	 * @param pk_menucategory
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuRoot[] getMenuRoot(String pk_menucategory) throws CpbBusinessException;
	/**
	 * ���ݲ˵������ò˵� 
	 * @param pk_menucategory
	 * @param pk_user
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItems(String pk_menucategory) throws CpbBusinessException;
	
	/**
	 * ���ݲ˵������ò˵�, 
	 * @param pk_menucategory
	 * @param pk_user
	 * @permission �Ƿ�����û�Ȩ��
	 * @filter �Ƿ����filter����
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
	 * @param orderby   �Ƿ�����  ��ordernum����
	 * @return
	 * @throws CpbBusinessException
	 */
	MenuItemAdapterVO[] getMenuItemsWithPermission(String pk_menucategory,String pk_user,boolean permission,boolean filter,boolean orderby) throws CpbBusinessException;
	/**
	 * ��ѯĳ���˵��µ������Ӳ˵�
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
	 * ���ݲ˵��ĸ���������Ӳ˵�
	 * @param pk_parent
	 * @return
	 */
	MenuItemAdapterVO[] getMenuItemsByParent(String pk_parent) throws CpbBusinessException;
	
	
}
