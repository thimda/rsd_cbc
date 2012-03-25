package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserRoleVO;

/**
 * �û���ɫ���������ӿ�
 * @author zhangxya
 *
 */
public interface ICpUserRoleBill {
	/**
	 * �����û���ɫ����vo
	 * 
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public String addPtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;

	/**
	 * ���������û���ɫ����vo
	 * @param roleUservos
	 * @return
	 * @throws PortalServiceException
	 */
	public void addPtRoleUserVOS(CpUserRoleVO[] roleUservos) throws CpbBusinessException;
	
	/**
	 * �����û���ɫ����vo
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public void updatePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;
	
	/**
	 * ɾ���û���ɫ����vo
	 * @param roleUservo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserVO(CpUserRoleVO roleUservo) throws CpbBusinessException;

	/**
	 * ɾ���û���ɫ����vo
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserByRolepk(String pk_role) throws CpbBusinessException;
	
	/**
	 * ɾ���û���ɫ����vo
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deletePtRoleUserByUserpks(String[] pk_users) throws CpbBusinessException;

}