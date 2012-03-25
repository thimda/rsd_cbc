package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleGroupVO;

/**
 * ��ɫ����������ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleGroupBill {
	/**
	 * ��ʼ����֯��ɫ��
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] initRoleGroupVos(String pk_org) throws CpbBusinessException;
	/**
	 * �����ɫ��
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public String addPtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 * ���������ɫ��
	 * 
	 * @param rolegroupvos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] addPtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException;

	/**
	 * ���½�ɫ��
	 * 
	 * @param rolegroupvo
	 * @throws PortalServiceException
	 */
	public void updatePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 *����pk ɾ����ɫ��
	 * 
	 * @param pk_rolegroup
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVO(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * ɾ����ɫ��
	 * 
	 * @param rolegroupvo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 * ɾ����ɫ��
	 * 
	 * @param rolegroupvos
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException;
}
