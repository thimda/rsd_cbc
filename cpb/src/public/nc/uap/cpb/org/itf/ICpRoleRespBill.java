package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;


/**
 * ��ɫְ�����ӿ�
 * 2012-3-26 ����03:01:56
 * @author limingf
 *
 */
public interface ICpRoleRespBill {
	/**
	 * ɾ����ɫְ�����
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleRespByRolepk(String[] pk_roles) throws CpbBusinessException;
	
	/**
	 * ɾ����ɫְ�����
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleRespByResppks(String[] pk_responsibilitys) throws CpbBusinessException;

}