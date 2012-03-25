package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.vo.pub.lang.UFBoolean;
/**
 * 管理节点查找接口
 * 
 */
public interface ICpAppsNodeQry {
	/**
	 * 根据节点id查找Node
	 * 
	 * @param nodeid
	 * @return
	 * @throws PortalServiceException
	 */
	public CpAppsNodeVO getNodeById(String nodeId) throws CpbBusinessException;
	/**
	 * 根据用户pk，节点分组带权限查询用户节点
	 * @param pk_user
	 * @param pk_appscategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user,String pk_appscategory) throws CpbBusinessException;
	
	/**
	 * 根据where条件查询功能节点
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAppsNodeVos(String where) throws CpbBusinessException;
	
	/**
	 * 根据where条件查询功能节点分组
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAppsCategoryVos(String where) throws CpbBusinessException;
	/**
	 * 根据用户pk带权限查询用户节点
	 * @param pk_user
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user) throws CpbBusinessException;
	/**
	 * 查询是否可以个性化的节点
	 * @param flag
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeBySpecial(UFBoolean flag) throws CpbBusinessException;
	/**
	 * 查询所有节点
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAllNodes() throws CpbBusinessException;
	
	/**
	 * 按照节点类型查询节点
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getAllNodesByType(String type) throws CpbBusinessException;
	/**
	 * 根据节点类别查询节点
	 * 
	 * @param pk_appscategory
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeByCategory(String pk_appscategory) throws CpbBusinessException;
	/**
	 * 根据节点pks查询节点
	 * @param pk_funnodes
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO[] getNodeByPks(String[] pk_funnodes) throws CpbBusinessException;
	/**
	 * 根据节点pk查询节点
	 * @param pk_funnode
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsNodeVO getNodeByPk(String pk_funnode) throws CpbBusinessException;
	
	/**
	 * 查找节点类别
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	public CpAppsCategoryVO getCategory(String categoryId, String parentId, String module) throws CpbBusinessException;
	/**
	 * 查找节点类别
	 * 
	 * @param pk_category
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO getCategoryByPk(String pk_category) throws CpbBusinessException;
	/**
	 * 查找类别
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	public CpAppsCategoryVO getRootCategory(String categoryId, String appsId, String module) throws CpbBusinessException;
	/**
	 * 查找模块下所有的功能节点目录
	 * 
	 * @param pk_module
	 * @param groupPk
	 * @throws PortalServiceException
	 */
	public CpAppsCategoryVO[] getAllCategory(String pk_module) throws CpbBusinessException;
	
	
	/**
	 * 获得所有功能节点目录
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAllCategory() throws CpbBusinessException;
	
	/**
	 * 根据父类别节点类别
	 * 
	 * @param appsCategory
	 * @param groupPk
	 * @throws PortalServiceException
	 */
	public CpAppsCategoryVO[] getAppsCategoryByParent(String pk_parent) throws CpbBusinessException;
	/**
	 * 根据模块查询节点分组
	 * @param pk_module
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpAppsCategoryVO[] getAppsCategoryByModule(String pk_module) throws CpbBusinessException;
	/**
	 * 根据模块，节点分组id查询节点分组
	 * @param categoryid
	 * @param module
	 * @return
	 * @throws CpbBusinessException
	 */
	public String[] getAppsCategoryGroup(String categoryid, String module) throws CpbBusinessException;

	public String[] getDeriveCategory(String categoryPK) throws CpbBusinessException;
	/**
	 * 获得当前的父目录
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
