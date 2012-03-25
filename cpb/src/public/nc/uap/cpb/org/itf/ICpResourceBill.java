package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpResourceVO;

/**
 * 2010-10-20 上午11:30:44 limingf
 */

public interface ICpResourceBill {
	public CpResourceVO getResourceByoriginalid(String originalid) throws CpbBusinessException;

	public void deleteResourceByOriginalid(String originalid) throws CpbBusinessException;

	/**
	 * 同步资源
	 * 
	 * @param pk_org
	 * @throws PortalServiceException
	 */
	public void resourceSynchronize(String pk_org) throws CpbBusinessException;

	/**
	 * 同步集团资源
	 * 
	 * @param pk_group
	 * @throws PortalServiceException
	 */
	public void groupResourcSyn(String pk_group) throws CpbBusinessException;

	/**
	 * 新增一组资源
	 * 
	 * @param vos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] add(CpResourceVO[] vos) throws CpbBusinessException;

	/**
	 * 更新一组资源
	 * 
	 * @param vos
	 * @return
	 * @throws PortalServiceException
	 */
	public void update(CpResourceVO[] vos) throws CpbBusinessException;

	/**
	 * 删除一组资源
	 * 
	 * @param originalid
	 * @throws PortalServiceException
	 */
	public void deleteResourceByOriginalids(String[] originalid) throws CpbBusinessException;

	/**
	 * 删除一组资源
	 * 
	 * @param originalid
	 * @throws PortalServiceException
	 */
	public void deleteResourceVos(CpResourceVO[] vos) throws CpbBusinessException;
}
