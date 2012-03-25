package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupVO;

/**
 * �û��鳣�ò����ӿ�
 * @author zhangxya
 *
 */
public interface ICpUserGroupBill {
	/**
	 * �����û���
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public String addPtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;
	
    /**
     * �����û���
     * @param usergroupvos
     * @throws CpbBusinessException
     */
	public void addPtUserGroupVOWithPk(CpUserGroupVO[] usergroupvos) throws CpbBusinessException;

	/**
	 * �����û���
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public void updatePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;

	/**
	 * ����pkɾ���û���
	 * @param pk_usergroup
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVO(String pk_usergroup) throws CpbBusinessException;

	/**
	 * ɾ���û���
	 * @param usergroupvo
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVO(CpUserGroupVO usergroupvo) throws CpbBusinessException;
	
	/**
	 * ɾ���û���
	 * @param usergroupvos
	 * @throws PortalServiceException
	 */
	public void deletePtUserGroupVOs(CpUserGroupVO[] usergroupvos) throws CpbBusinessException;
	
	public boolean isReference(CpUserGroupVO usergroupvo)throws CpbBusinessException;

	}