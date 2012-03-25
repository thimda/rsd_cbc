package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleGroupVO;

/**
 * 角色组基本操作接口
 * 
 * @author zhangxya
 * 
 */
public interface ICpRoleGroupBill {
	/**
	 * 初始化组织角色组
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public CpRoleGroupVO[] initRoleGroupVos(String pk_org) throws CpbBusinessException;
	/**
	 * 插入角色组
	 * 
	 * @param uservo
	 * @throws PortalServiceException
	 */
	public String addPtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 * 批量插入角色组
	 * 
	 * @param rolegroupvos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] addPtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException;

	/**
	 * 更新角色组
	 * 
	 * @param rolegroupvo
	 * @throws PortalServiceException
	 */
	public void updatePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 *根据pk 删除角色组
	 * 
	 * @param pk_rolegroup
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVO(String pk_rolegroup) throws CpbBusinessException;

	/**
	 * 删除角色组
	 * 
	 * @param rolegroupvo
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVO(CpRoleGroupVO rolegroupvo) throws CpbBusinessException;

	/**
	 * 删除角色组
	 * 
	 * @param rolegroupvos
	 * @throws PortalServiceException
	 */
	public void deletePtRoleGroupVOs(CpRoleGroupVO[] rolegroupvos) throws CpbBusinessException;
}
