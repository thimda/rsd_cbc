package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.stylemgr.vo.UwTemplateVO;

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
	 * �����û��͹��ܽڵ�����ȡģ��Pk
	 * @param user
	 * @param funcode
	 * @return
	 * @throws LfwBusinessException
	 */
	String getTemplatePkByUser(CpUserVO user, String funcode) throws LfwBusinessException;
	
}
