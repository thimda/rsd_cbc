package nc.uap.wfm.itf;

import nc.uap.wfm.vo.WfmVirtualRoleVO;

/**
 * ÐéÄâ½ÇÉ«²éÑ¯ 2011-4-26 ÉÏÎç09:06:24
 * 
 * @author limingf
 */
public interface IWfmVirtualRoleBill {
	public void deleteWfmVirtRoleByPk(String virtRolePk);
	public void saveWfmVirtRole(WfmVirtualRoleVO vo);
}
