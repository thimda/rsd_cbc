package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupVO;

/**
 * 用户组常用操作接口
 * @author zhangxya
 *
 */
public interface ICpUserGroupBill {
	/**
	 * 插入用户组
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public String addPtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;
	
    /**
     * 插入用户组
     * @param usergroupvos
     * @throws CpbBusinessException
     */
	public void addPtUserGroupVOWithPk(CpUserGroupVO[] usergroupvos) throws CpbBusinessException;

	/**
	 * 更新用户组
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public void updatePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;

	/**
	 * 根据pk删除用户组
	 * @param pk_usergroup
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVO(String pk_usergroup) throws CpbBusinessException;

	/**
	 * 删除用户组
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;
	
	/**
	 * 删除用户组
	 * @param usergroupvos
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVOs(CpUserGroupVO[] usergroupvos) throws CpbBusinessException;
	
	public boolean isReference(CpUserGroupVO usergroupvo)throws CpbBusinessException;

	}