package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.stylemgr.vo.UwTemplateVO;

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
	 * 根据用户和功能节点编码获取模板Pk
	 * @param user
	 * @param funcode
	 * @return
	 * @throws LfwBusinessException
	 */
	String getTemplatePkByUser(CpUserVO user, String funcode) throws LfwBusinessException;
	
}
