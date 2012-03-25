package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpPwdSygVO;
/**
 * 密码安全策略 2011-5-31 上午11:09:09
 * 
 * @author limingf
 */
public interface ICpPwdSgyQry {
	/**
	 * 查询所有安全设置
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO[] getAllPasswordSecuritys() throws CpbBusinessException;
	/**
	 * 根据名称查询安全设置
	 * 
	 * @param name
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO getPtPasswordSecurityByName(String name) throws CpbBusinessException;
	/**
	 *根据pk查询安全设置
	 * 
	 * @param pk_passwordsecurity
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO getPtPasswordSecurityByPk(String pk_passwordsecurity) throws CpbBusinessException;
	/**
	 * 查询除指定pk外的所有安全设置
	 * 
	 * @param pk_passwordsecurity
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO[] getAllPtSecurityExcp(String pk_passwordsecurity) throws CpbBusinessException;
}
