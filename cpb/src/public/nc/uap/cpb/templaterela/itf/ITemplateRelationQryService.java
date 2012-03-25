package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.ctrl.tpl.exp.TplBusinessException;

public interface ITemplateRelationQryService {
	/**
	 * 根据条件查询模板—用户关系表
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateUserVO[] getTemplateUserVOsByCondition(String sqlWhere);
	/**
	 * 根据条件查询模板—组织关系表
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateOrgVO[] getTemplateOrgVOsByCondition(String sqlWhere);
	/**
	 * 根据条件查询模板—角色关系表
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateRoleVO[] getTemplateRoleVOsByCondition(String sqlWhere);
	
	/**
	 * 根据用户pk查询用户有权限的模板pk
	 * @param user
	 * @return
	 * @throws TplBusinessException
	 */
	public String  getTemplatePkByUser(String user, String templatetype) throws TplBusinessException;

}
