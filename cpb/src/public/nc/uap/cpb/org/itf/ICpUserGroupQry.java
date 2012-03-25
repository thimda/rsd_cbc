package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupVO;

/**
 * 用户组查询接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserGroupQry {
	/**
	 * 查询所有用户组
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getAllUserGroups() throws CpbBusinessException;
	
	/**
	 * 根据where条件查询用户组
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserGroupVO[] getUserGroupVos(String where) throws CpbBusinessException;

	/**
	 * 根据pk查询用户组
	 * 
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByPk(String pk_usergroup) throws CpbBusinessException;
	
	/**
	 * 根据集团pk查询用户组
	 * 
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getUserGroupByGroupPk(String pk_group) throws CpbBusinessException;
	
	/**
	 * 查询某个用户组下的第一级子用户组
	 * @param pk_parent
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserGroupVO[] getUserGroupByParent(String pk_parent) throws CpbBusinessException;

	/**
	 * 根据用户组Id查询用户组
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupCode(String groupcode) throws CpbBusinessException;
	/**
	 * 根据pk_org,用户组Id查询用户组
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupCode(String pk_org,String groupcode) throws CpbBusinessException;
	/**
	 * 根据用户组名称查询用户组
	 * 
	 * @param groupname
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupName(String groupname) throws CpbBusinessException;

	/**
	 * 根据多个用户组pk查询批量用户组
	 * @param pk_usergroups
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getUserGroupByPkS(String[] pk_usergroups) throws CpbBusinessException;
	
	/**
	 * 查询除掉除去pk_usergroup外的所有用户组
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getAllUserGroupExcp(String pk_usergroup) throws CpbBusinessException;
}