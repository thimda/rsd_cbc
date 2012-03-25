package nc.uap.ctrl.tpl.gz.impl;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.gz.ICpGzTemplateInnerQryService;
import nc.uap.ctrl.tpl.gz.base.CpGzConditionVO;
import nc.uap.ctrl.tpl.gz.base.CpGzTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpGzTemplateInnerQryServiceImpl implements
		ICpGzTemplateInnerQryService {

	@Override
	public CpGzConditionVO[] getGzConditions(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			List<CpGzConditionVO> list = (List<CpGzConditionVO>) dao.retrieveByClause(CpGzConditionVO.class, "pk_gz_template='" + pk_template + "'");
			return list.toArray(new CpGzConditionVO[0]);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException("查找模板:" + pk_template + "，条件失败");
		}
	}

	/* (non-Javadoc)
	 * @see nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService#getQueryTotalVO(java.lang.String)
	 */
	@Override
	public CpGzTemplateTotalVO getQueryTotalVO(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			CpQueryTemplateVO template = (CpQueryTemplateVO) dao.retrieveByPK(CpQueryTemplateVO.class, pk_template);
			if(template == null)
				return null;
			CpGzConditionVO[] conditions = getGzConditions(pk_template);
			CpGzTemplateTotalVO totalVO = new CpGzTemplateTotalVO();
			totalVO.setParentVO(template);
			totalVO.setChildrenVO(conditions);
			return totalVO;
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException("查询模板内容出错，PK=" + pk_template);
		}
	}

	@Override
	public String getGzTemplatePkByNode(String nodeCode)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			Object[] objs = (Object[]) dao.executeQuery("select pk_query_template from cp_query_template where nodecode='" + nodeCode + "'", new ArrayProcessor());
			if(objs == null || objs.length == 0)
				return null;
			return (String) objs[0];
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
	}

}
