package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleResVO;
/**
 * ��ɫ��Ȩ��Դ��ѯ 2010-10-28 ����01:43:19 limingf
 */
public interface ICpRoleResourceQry {
	/**
	 * ��ѯ��ɫ��Ȩ����Դ
	 * 
	 * @param pk_roles
	 * @param resourcetype
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleResVO[] getPtRoleResourceByPkRoles(String[] pk_roles, int resourcetype) throws CpbBusinessException;
	/**
	 * ��ѯ��ɫ��Դ
	 * 
	 * @param pk_resource
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleResVO[] getRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException;
}
