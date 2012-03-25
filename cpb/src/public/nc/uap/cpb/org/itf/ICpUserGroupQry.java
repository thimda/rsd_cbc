package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupVO;

/**
 * �û����ѯ�ӿ�
 * 
 * @author zhangxya
 * 
 */
public interface ICpUserGroupQry {
	/**
	 * ��ѯ�����û���
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getAllUserGroups() throws CpbBusinessException;
	
	/**
	 * ����where������ѯ�û���
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserGroupVO[] getUserGroupVos(String where) throws CpbBusinessException;

	/**
	 * ����pk��ѯ�û���
	 * 
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByPk(String pk_usergroup) throws CpbBusinessException;
	
	/**
	 * ���ݼ���pk��ѯ�û���
	 * 
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getUserGroupByGroupPk(String pk_group) throws CpbBusinessException;
	
	/**
	 * ��ѯĳ���û����µĵ�һ�����û���
	 * @param pk_parent
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpUserGroupVO[] getUserGroupByParent(String pk_parent) throws CpbBusinessException;

	/**
	 * �����û���Id��ѯ�û���
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupCode(String groupcode) throws CpbBusinessException;
	/**
	 * ����pk_org,�û���Id��ѯ�û���
	 * @param groupcode
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupCode(String pk_org,String groupcode) throws CpbBusinessException;
	/**
	 * �����û������Ʋ�ѯ�û���
	 * 
	 * @param groupname
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO getUserGroupByGroupName(String groupname) throws CpbBusinessException;

	/**
	 * ���ݶ���û���pk��ѯ�����û���
	 * @param pk_usergroups
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getUserGroupByPkS(String[] pk_usergroups) throws CpbBusinessException;
	
	/**
	 * ��ѯ������ȥpk_usergroup��������û���
	 * @param pk_usergroup
	 * @return
	 * @throws PortalServiceException
	 */
	public CpUserGroupVO[] getAllUserGroupExcp(String pk_usergroup) throws CpbBusinessException;
}