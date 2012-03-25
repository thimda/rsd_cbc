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
	 * 返回在特定集团、特定功能节点、特定查询模板中特定用户的所有查询方案
	 * <p>
	 * 这里返回的查询方案可能会存在重名现象，比如系统管理员新增的预置查询方案和用户的某个私人方案重名
	 * 
	 * @param pk_org
	 *            集团主键
	 * @param pk_template
	 *            查询模板ID
	 * @param userid
	 *            用户主键
	 */
	public QuerySchemeVO[] getQuerySchemeVOsBy(String pk_org, String pk_template, String userid) throws BusinessException;

	
 
}
