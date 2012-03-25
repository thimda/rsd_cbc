package nc.uap.ctrl.tpl.print.impl;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.print.ICpPrintTemplateInnerService;
import nc.uap.ctrl.tpl.print.base.CpPrintConditionVO;
import nc.uap.ctrl.tpl.print.base.CpPrintTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpPrintTemplateInnerServiceImpl implements ICpPrintTemplateInnerService {

	@Override
	public void initConditons(String pk_template, CpPrintConditionVO[] conditions)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			dao.deleteByClause(CpPrintConditionVO.class, "pk_print_template='" + pk_template + "'");
			if(null!=conditions){
				dao.insertVOArray(conditions);
			}
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void initTemplate(CpPrintTemplateVO templateVo)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			dao.insertVO(templateVo);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteTemplate(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			dao.deleteByPK(CpPrintTemplateVO.class, pk_template);
			dao.deleteByClause(CpPrintConditionVO.class, "pk_print_template='"+pk_template+"'");
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void updateTemplate(CpPrintTemplateVO templateVo)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			dao.updateVO(templateVo);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

}
