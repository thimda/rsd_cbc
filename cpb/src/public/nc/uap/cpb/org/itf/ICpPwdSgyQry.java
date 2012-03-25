package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpPwdSygVO;
/**
 * ���밲ȫ���� 2011-5-31 ����11:09:09
 * 
 * @author limingf
 */
public interface ICpPwdSgyQry {
	/**
	 * ��ѯ���а�ȫ����
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO[] getAllPasswordSecuritys() throws CpbBusinessException;
	/**
	 * �������Ʋ�ѯ��ȫ����
	 * 
	 * @param name
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO getPtPasswordSecurityByName(String name) throws CpbBusinessException;
	/**
	 *����pk��ѯ��ȫ����
	 * 
	 * @param pk_passwordsecurity
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO getPtPasswordSecurityByPk(String pk_passwordsecurity) throws CpbBusinessException;
	/**
	 * ��ѯ��ָ��pk������а�ȫ����
	 * 
	 * @param pk_passwordsecurity
	 * @return
	 * @throws PortalServiceException
	 */
	public CpPwdSygVO[] getAllPtSecurityExcp(String pk_passwordsecurity) throws CpbBusinessException;
}
