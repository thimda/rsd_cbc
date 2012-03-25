package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.vo.pub.lang.UFBoolean;
/**
 * ����ڵ���ҽӿ�
 * 
 */
public interface ICpAppsNodeQry {
	/**
	 * ���ݽڵ�id����Node
	 * 
	 * @param nodeid
	 * @return
	 * @throws PortalServiceException
	 */
	public CpAppsNodeVO getNodeById(String nodeId) throws CpbBusinessException;
	/**
	 * �����û�pk���ڵ�����Ȩ�޲�ѯ�û��ڵ�
	 * @param pk_user
	 * @param pk_appscategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user,String pk_appscategory) throws CpbBusinessException;
	
	/**
	 * ����where������ѯ���ܽڵ�
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAppsNodeVos(String where) throws CpbBusinessException;
	
	/**
	 * ����where������ѯ���ܽڵ����
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAppsCategoryVos(String where) throws CpbBusinessException;
	/**
	 * �����û�pk��Ȩ�޲�ѯ�û��ڵ�
	 * @param pk_user
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user) throws CpbBusinessException;
	/**
	 * ��ѯ�Ƿ���Ը��Ի��Ľڵ�
	 * @param flag
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeBySpecial(UFBoolean flag) throws CpbBusinessException;
	/**
	 * ��ѯ���нڵ�
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAllNodes() throws CpbBusinessException;
	
	/**
	 * ���սڵ����Ͳ�ѯ�ڵ�
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAllNodesByType(String type) throws CpbBusinessException;
	/**
	 * ���ݽڵ�����ѯ�ڵ�
	 * 
	 * @param pk_appscategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeByCategory(String pk_appscategory) throws CpbBusinessException;
	/**
	 * ���ݽڵ�pks��ѯ�ڵ�
	 * @param pk_funnodes
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeByPks(String[] pk_funnodes) throws CpbBusinessException;
	/**
	 * ���ݽڵ�pk��ѯ�ڵ�
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO getNodeByPk(String pk_funnode) throws CpbBusinessException;
	
	/**
	 * ���ҽڵ����
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	public CpAppsCategoryVO getCategory(String categoryId, String parentId, String module) throws CpbBusinessException;
	/**
	 * ���ҽڵ����
	 * 
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO getCategoryByPk(String pk_category) throws CpbBusinessException;
	/**
	 * �������
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	public CpAppsCategoryVO getRootCategory(String categoryId, String appsId, String module) throws CpbBusinessException;
	/**
	 * ����ģ�������еĹ��ܽڵ�Ŀ¼
	 * 
	 * @param pk_module
	 * @param groupPk
	 * @throws PortalServiceException
	 */
	public CpAppsCategoryVO[] getAllCategory(String pk_module) throws CpbBusinessException;
	
	
	/**
	 * ������й��ܽڵ�Ŀ¼
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAllCategory() throws CpbBusinessException;
	
	/**
	 * ���ݸ����ڵ����
	 * 
	 * @param appsCategory
	 * @param groupPk
	 * @throws PortalServiceException
	 */
	public CpAppsCategoryVO[] getAppsCategoryByParent(String pk_parent) throws CpbBusinessException;
	/**
	 * ����ģ���ѯ�ڵ����
	 * @param pk_module
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAppsCategoryByModule(String pk_module) throws CpbBusinessException;
	/**
	 * ����ģ�飬�ڵ����id��ѯ�ڵ����
	 * @param categoryid
	 * @param module
	 * @return
	 * @throws CpbBusinessException
	 */
	public String[] getAppsCategoryGroup(String categoryid, String module) throws CpbBusinessException;

	public String[] getDeriveCategory(String categoryPK) throws CpbBusinessException;
	/**
	 * ��õ�ǰ�ĸ�Ŀ¼
	 * 
	 * @param categoryPK
	 * @param pk_group
	 * @param module
	 * @param categoryid
	 * @return
	 * @throws PortalServiceException
	 */
	public String getParentCategory(String categoryPK,String module, String categoryid) throws CpbBusinessException;
	
	public CpAppsNodeVO[] getNodeByUser(String appsCategory, String pk_user, int resourceType) throws CpbBusinessException;
}
