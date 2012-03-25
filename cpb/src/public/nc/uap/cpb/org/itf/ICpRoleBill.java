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
	public String addPtRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * �������ӽ�ɫ
	 * @param rolevos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] addPtRoleVOs(CpRoleVO[] rolevos) throws CpbBusinessException;
	/**
	 * ���½�ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void updatePtRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * ����pkɾ����ɫ
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deletePtRoleVO(String pk_role) throws CpbBusinessException;
	/**
	 * ɾ����ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * ɾ����ɫ
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleVO(CpRoleVO[] rolevos) throws CpbBusinessException;
}