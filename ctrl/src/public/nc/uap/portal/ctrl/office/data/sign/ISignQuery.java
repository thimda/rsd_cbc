package nc.uap.portal.ctrl.office.data.sign;

import nc.uap.lfw.core.exception.LfwBusinessException;

public interface ISignQuery {
	/**
	 * 获取全部印章
	 * @return
	 */
	public ServersignVO[] GetAllServerSigns() throws LfwBusinessException;
	
	/**
	 * 根据用户获取全部印章
	 * @param userpk
	 * @return
	 */
	public ServersignVO[] GetAllServerSignsByUser(String pk_user) throws LfwBusinessException;
	
	public void DeleteServerSign(String pk_sign)  throws LfwBusinessException;
	/**
	 * 根据主键获取印章信息
	 * @param pk_sign
	 * @return
	 */
	public ServersignVO GetServerSignByPK(String pk_sign) throws LfwBusinessException;
	/**
	 * 根据印章获取全部用户
	 * 
	 */
	public UsersignsVO[] GetAllUsersignBySign(String pk_sign) throws LfwBusinessException;
	
	/**
	 * 获取全部EKey
	 * @return
	 */
	public EkeyVO[] GetAllEKey() throws LfwBusinessException;
	
	/**
	 * 根据用户获取全部EKey
	 * @param pk_user
	 * @return
	 */
	public EkeyVO[] GetAllEKeyByUser(String pk_user) throws LfwBusinessException;
	
	/**
	 * 根据主键获取Ekey信息
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
