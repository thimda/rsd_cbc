package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;

/**
 * �û��������ɫ�����ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserGroupRoleBill {

	/**
	 *�����û����ɫvo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public String addPtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

	/**
	 *���������û����ɫvo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public String[] addPtUserGroupRoleVOs(CpUserGroupRoleVO[] usergroupRolevos) throws CpbBusinessException;

	/**
	 * �����û����ɫvo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public void updatePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

	/**
	 * ɾ���û����ɫvo
	 * 
	 * @param usergroupRolevo
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupRoleVO(CpUserGroupRoleVO usergroupRolevo) throws CpbBusinessException;

}