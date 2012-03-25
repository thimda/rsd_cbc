package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupRoleVO;

public interface ICpUserGroupRoleQry {

	/**
	 * �����û�pk��ѯ���н�ɫ��
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupRoleVO[] getUgRoleByUserPk(String pk_user) throws CpbBusinessException;

	/**
	 * ���ݽ�ɫpk��ѯ��ɫ���������û���
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupRoleVO[] getUgRoleByRolePk(String pk_role) throws CpbBusinessException;

}
