package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleVO;
/**
 * ��ɫ�����ӿ�
 * @author zhangxya
 *
 */
public interface ICpRoleBill {
	/**
	 * �����ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public String addCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * �������ӽ�ɫ
	 * @param rolevos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] addCpRoleVOs(CpRoleVO[] rolevos) throws CpbBusinessException;
	/**
	 * ���½�ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void updateCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * ����pkɾ����ɫ
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(String pk_role) throws CpbBusinessException;
	/**
	 * ɾ����ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * ɾ����ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(CpRoleVO[] rolevos) throws CpbBusinessException;
}