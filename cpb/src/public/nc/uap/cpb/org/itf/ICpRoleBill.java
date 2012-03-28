package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRoleVO;
/**
 * 角色操作接口
 * @author zhangxya
 *
 */
public interface ICpRoleBill {
	/**
	 * 插入角色
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public String addCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * 批量增加角色
	 * @param rolevos
	 * @return
	 * @throws PortalServiceException
	 */
	public String[] addCpRoleVOs(CpRoleVO[] rolevos) throws CpbBusinessException;
	/**
	 * 更新角色
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void updateCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * 根据pk删除角色
	 * @param pk_role
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(String pk_role) throws CpbBusinessException;
	/**
	 * 删除角色
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(CpRoleVO rolevo) throws CpbBusinessException;
	/**
	 * 删除角色
	 * @param rolevo
	 * @throws PortalServiceException
	 */
	public void deleteCpRoleVO(CpRoleVO[] rolevos) throws CpbBusinessException;
}