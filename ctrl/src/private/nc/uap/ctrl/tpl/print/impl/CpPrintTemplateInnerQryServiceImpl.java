package nc.uap.ctrl.tpl.print.impl;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateTotalVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpPrintTemplateInnerQryServiceImpl implements
		ICpPrintTemplateInnerQryService {

	@SuppressWarnings("unchecked")
	@Override
	public CpPrintConditionVO[] getPrintConditions(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			List<CpPrintConditionVO> list = (List<CpPrintConditionVO>) dao.retrieveByClause(CpPrintConditionVO.class, "pk_print_template='" + pk_template + "'");
			return list.toArray(new CpPrintConditionVO[0]);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException("查找模板:" + pk_template + "，条件失败");
		}
	}

	/* (non-Javadoc)
	 * @see nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerQryService#getPrintTotalVO(java.lang.String)
	 */
	@Override
	public CpPrintTemplateTotalVO getPrintTotalVO(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			CpPrintTemplateVO template = (CpPrintTemplateVO) dao.retrieveByPK(CpPrintTemplateVO.class, pk_template);
			if(template == null)
				return null;
			CpPrintConditionVO[] conditions = getPrintConditions(pk_template);
			CpPrintTemplateTotalVO totalVO = new CpPrintTemplateTotalVO();
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
	public String getPrintTemplatePkByNode(String nodeCode)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			Object[] objs = (Object[]) dao.executeQuery("select pk_print_template from cp_print_template where nodecode='"+nodeCode+"'", new ArrayProcessor());
			if(objs == null || objs.length == 0)
				return null;
			return (String) objs[0];
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
	}

	@Override
	public CpPrintTemplateVO getPrintTemplateVO(String pk_template)
			throws TplBusinessException {
		// TODO Auto-generated method stub
		try{
			BaseDAO dao = new BaseDAO();
			CpPrintTemplateVO template = (CpPrintTemplateVO) dao.retrieveByPK(CpPrintTemplateVO.class, pk_template);	
			return template;
		}catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
		
	}

	@Override
	public CpPrintTemplateVO[] getPrintTemplates(String nodecode)
			throws TplBusinessException {
		// TODO Auto-generated method stub
		try{
			BaseDAO dao = new BaseDAO();
			List<CpPrintTemplateVO> list = (List<CpPrintTemplateVO>) dao.retrieveByClause(CpPrintTemplateVO.class, "nodecode='"+nodecode+"'");	
			return list.toArray(new CpPrintTemplateVO[0]);
		}catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
	}

}
