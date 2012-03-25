package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;

public interface ITemplateRelationService {
	/**
	 * 创建模板-组织vo
	 * @param vo
	 */
	public void createTemplateOrgVO(CpTemplateOrgVO vo);
	
	/**
	 * 创建模板-角色vo
	 * @param vo
	 */
	public void createTemplateRoleVO(CpTemplateRoleVO vo);
	
	/**
	 * 创建模板-用户vo
	 * @param vo
	 */
	public void createTemplateUserVO(CpTemplateUserVO vo);

}
