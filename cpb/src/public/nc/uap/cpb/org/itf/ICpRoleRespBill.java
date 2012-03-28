package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;


/**
 * 角色职责服务接口
 * 2012-3-26 下午03:01:56
 * @author limingf
 *
 */
public interface ICpRoleRespBill {
	/**
	 * 删除角色职责关联
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleRespByRolepk(String[] pk_roles) throws CpbBusinessException;
	
	/**
	 * 删除角色职责关联
	 * @param pk_user
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleRespByResppks(String[] pk_responsibilitys) throws CpbBusinessException;

}