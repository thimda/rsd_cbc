package nc.uap.cpb.templaterela.itf;

import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;

public interface ITemplateRelationService {
	/**
	 * ����ģ��-��֯vo
	 * @param vo
	 */
	public void createTemplateOrgVO(CpTemplateOrgVO vo);
	
	/**
	 * ����ģ��-��ɫvo
	 * @param vo
	 */
	public void createTemplateRoleVO(CpTemplateRoleVO vo);
	
	/**
	 * ����ģ��-�û�vo
	 * @param vo
	 */
	public void createTemplateUserVO(CpTemplateUserVO vo);

}
