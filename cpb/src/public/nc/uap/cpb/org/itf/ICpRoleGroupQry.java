package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleGroupVO;

/**
 * ��ɫ���ѯ�ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleGroupQry {
	/**
	 * ��ѯ���н�ɫ��
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getAllRoleGroups() throws CpbBusinessException;

	/**
	 * ����pk��ѯ��ɫ��
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByPk(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * ���ݽ�ɫ������ѯ��ɫ��
	 * 
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByGroupCode(String groupcode) throws CpbBusinessException;

	/**
	 * ���ݽ�ɫ������ѯ��ɫ��
	 * 
	 * @param groupname
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO getRoleGroupByGroupName(String groupname) throws CpbBusinessException;

	/**
	 * ��ѯĳһ��˾�µĽ�ɫ��
	 * 
	 * @param pk_corp
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkcorp(String pk_corp) throws CpbBusinessException;
	
	public CpRoleGroupVO[] getRoleGroupByParent(String pk_parent) throws CpbBusinessException;
	
	/**
	 * ����where������ѯ��ɫ��
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpRoleGroupVO[] getRoleGroupVos(String where) throws CpbBusinessException;

	/**
	 * ��ѯ���Ž�ɫ��
	 * 
	 * @param pk_group
	 * @param type
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group, String type) throws CpbBusinessException;

	/**
	 * ��ѯ���Ž�ɫ��
	 * 
	 * @param pk_group
	 * @param type
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkgroup(String pk_group) throws CpbBusinessException;

	/**
	 * ���ݶ��pk��ѯ������ɫ��
	 * 
	 * @param pk_rolegroups
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkS(String[] pk_rolegroups) throws CpbBusinessException;

	/**
	 * ��ѯ��ȥpk������н�ɫ��
	 * 
	 * @param pk_rolegroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupExc(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * ��ѯ��֯��ɫ��
	 * 
	 * @param pk_orgs
	 * @return
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] getRoleGroupByPkorgs(String[] pk_orgs) throws CpbBusinessException;
}
