package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
/**
 *��ɫ��Ȩ��Դ 2010-10-28 ����01:43:19 limingf
 */
public interface ICpRoleResourceBill {
	/**
	 * ������Դpkɾ����ɫ��Ȩ��Դ
	 * 
	 * @param pk_resource
	 * @throws PortalServiceException
	 */
	public void deleteRoleResourceByPk_resource(String pk_resource) throws CpbBusinessException;
	/**
	 * ���ݽ�ɫpkɾ����ɫ��Ȩ��Դ
	 * 
	 * @param pk_resource
	 * @throws PortalServiceException
	 */
	public void deleteRoleResourceByRolePk(String pk_role) throws CpbBusinessException;
	public void deleteRoleResourceByRoleResoPk(String roleResoPk) throws CpbBusinessException;
}
