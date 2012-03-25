package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpResourceVO;

/**
 * 2010-10-20 ����11:30:44 limingf
 */

public interface ICpResourceBill {
	public CpResourceVO getResourceByoriginalid(String originalid) throws CpbBusinessException;

	public void deleteResourceByOriginalid(String originalid) throws CpbBusinessException;

	/**
	 * ͬ����Դ
	 * 
	 * @param pk_org
	 * @throws PortalServiceException
	 */
	public void resourceSynchronize(String pk_org) throws CpbBusinessException;

	/**
	 * ͬ��������Դ
	 * 
	 * @param pk_group
	 * @throws PortalServiceException
	 */
	public void groupResourcSyn(String pk_group) throws CpbBusinessException;

	/**
	 * ����һ����Դ
	 * 
	 * @param vos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] add(CpResourceVO[] vos) throws CpbBusinessException;

	/**
	 * ����һ����Դ
	 * 
	 * @param vos
	 * @return
	 * @throws PortalServiceException
	 */
	public void update(CpResourceVO[] vos) throws CpbBusinessException;

	/**
	 * ɾ��һ����Դ
	 * 
	 * @param originalid
	 * @throws PortalServiceException
	 */
	public void deleteResourceByOriginalids(String[] originalid) throws CpbBusinessException;

	/**
	 * ɾ��һ����Դ
	 * 
	 * @param originalid
	 * @throws PortalServiceException
	 */
	public void deleteResourceVos(CpResourceVO[] vos) throws CpbBusinessException;
}
