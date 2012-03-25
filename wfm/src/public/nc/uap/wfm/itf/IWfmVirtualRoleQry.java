package nc.uap.wfm.itf;

import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmVirtualRoleVO;

/**
 * �����ɫ��ѯ
 * 2011-4-26 ����09:06:24 
 * @author limingf
 */
public interface IWfmVirtualRoleQry {
	/**
	 * ��ѯ���н�ɫ
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getAllRoles() throws WfmServiceException;

	
	/**
	 * ����pk��ѯ��ɫ
	 * 
	 * @param pk_role
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getRoleByPk(String[] pk_roles) throws WfmServiceException;
	
		
	/**
	 * ���ݽ�ɫ�����ѯ��ɫ
	 * 
	 * @param rolecode
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByCode(String code) throws WfmServiceException;
	/**
	 * ���ݽ�ɫ���Ʋ�ѯ��ɫ
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByName(String name) throws WfmServiceException;
	
	/**
	 * ���ݽ�ɫ���Ʋ�ѯ��ɫ
	 * @param rolename
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO getRoleByName(String pk_group,String name) throws WfmServiceException;
	
	/**
	 * ���ݼ���pk��ѯ��ɫ
	 * @param pk_group
	 * @return
	 * @throws PortalServiceException
	 */
	public WfmVirtualRoleVO[] getRoleByGroup(String pk_group) throws WfmServiceException;
	
}
