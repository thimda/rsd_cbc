package nc.uap.portal.ctrl.office.data.sign;

import nc.uap.lfw.core.exception.LfwBusinessException;

public interface ISignQuery {
	/**
	 * ��ȡȫ��ӡ��
	 * @return
	 */
	public ServersignVO[] GetAllServerSigns() throws LfwBusinessException;
	
	/**
	 * �����û���ȡȫ��ӡ��
	 * @param userpk
	 * @return
	 */
	public ServersignVO[] GetAllServerSignsByUser(String pk_user) throws LfwBusinessException;
	
	public void DeleteServerSign(String pk_sign)  throws LfwBusinessException;
	/**
	 * ����������ȡӡ����Ϣ
	 * @param pk_sign
	 * @return
	 */
	public ServersignVO GetServerSignByPK(String pk_sign) throws LfwBusinessException;
	/**
	 * ����ӡ�»�ȡȫ���û�
	 * 
	 */
	public UsersignsVO[] GetAllUsersignBySign(String pk_sign) throws LfwBusinessException;
	
	/**
	 * ��ȡȫ��EKey
	 * @return
	 */
	public EkeyVO[] GetAllEKey() throws LfwBusinessException;
	
	/**
	 * �����û���ȡȫ��EKey
	 * @param pk_user
	 * @return
	 */
	public EkeyVO[] GetAllEKeyByUser(String pk_user) throws LfwBusinessException;
	
	/**
	 * ����������ȡEkey��Ϣ
	 * @param pk_sign
	 * @return
	 */
	public EkeyVO GetEkeyByPK(String pk_sign)throws LfwBusinessException;
	/**
	 * 
	 * @param pk_ekey
	 * @return
	 */
	public EkeyUserVo[] GetAllUserByEkey(String pk_ekey)throws LfwBusinessException;
}
