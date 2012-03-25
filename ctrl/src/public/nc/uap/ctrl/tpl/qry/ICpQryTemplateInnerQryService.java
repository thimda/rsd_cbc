package nc.uap.ctrl.tpl.qry;

import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeVO;
import nc.vo.pub.BusinessException;

public interface ICpQryTemplateInnerQryService {
	public CpQueryConditionVO[] getQueryConditions(String pk_template) throws TplBusinessException;

	public CpQueryTemplateTotalVO getQueryTotalVO(String pk_template) throws TplBusinessException;

	public String getQueryTemplatePkByNode(String nodeCode) throws TplBusinessException;
	
	/**
	 * �������ض����š��ض����ܽڵ㡢�ض���ѯģ�����ض��û������в�ѯ����
	 * <p>
	 * ���ﷵ�صĲ�ѯ�������ܻ�����������󣬱���ϵͳ����Ա������Ԥ�ò�ѯ�������û���ĳ��˽�˷�������
	 * 
	 * @param pk_org
	 *            ��������
	 * @param pk_template
	 *            ��ѯģ��ID
	 * @param userid
	 *            �û�����
	 */
	public QuerySchemeVO[] getQuerySchemeVOsBy(String pk_org, String pk_template, String userid) throws BusinessException;

	
 
}
