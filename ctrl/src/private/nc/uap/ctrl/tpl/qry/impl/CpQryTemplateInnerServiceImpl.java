package nc.uap.ctrl.tpl.qry.impl;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerService;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpQryTemplateInnerServiceImpl implements
		ICpQryTemplateInnerService {

	@Override
	public void initConditons(String pk_template, CpQueryConditionVO[] conditions)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
//			List<CpQueryConditionVO> list = (List<CpQueryConditionVO>) dao.retrieveByClause(CpQueryConditionVO.class, "pkTemplet='" + pk_template + "'");
//			Iterator<CpQueryConditionVO> it = list.iterator();
			dao.deleteByClause(CpQueryConditionVO.class, "pk_query_template='" + pk_template + "'");
			dao.insertVOArray(conditions);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void initTemplate(CpQueryTemplateVO templateVo)
			throws TplBusinessException {
		try {
//			SystemplateVO sysVo = new SystemplateVO();
			BaseDAO dao = new BaseDAO();
//			dao.insertVO(sysVo);
			dao.insertVO(templateVo);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteTemplate(String pk_template) throws TplBusinessException {
	}

	@Override
	public void updateTemplate(CpQueryTemplateVO templateVo)
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
