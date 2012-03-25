package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.ctrl.tpl.exp.TplBusinessException;

public interface ITemplateRelationQryService {
	/**
	 * ����������ѯģ�塪�û���ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateUserVO[] getTemplateUserVOsByCondition(String sqlWhere);
	/**
	 * ����������ѯģ�塪��֯��ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateOrgVO[] getTemplateOrgVOsByCondition(String sqlWhere);
	/**
	 * ����������ѯģ�塪��ɫ��ϵ��
	 * @param sqlWhere
	 * @return
	 */
	public CpTemplateRoleVO[] getTemplateRoleVOsByCondition(String sqlWhere);
	
	/**
	 * �����û�pk��ѯ�û���Ȩ�޵�ģ��pk
	 * @param user
	 * @return
	 * @throws TplBusinessException
	 */
	public String  getTemplatePkByUser(String user, String templatetype) throws TplBusinessException;

}
