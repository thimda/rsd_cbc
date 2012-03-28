package nc.uap.ctrl.tpl.gz.impl;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.gz.ICpGzTemplateInnerService;
import nc.uap.ctrl.tpl.gz.base.CpGzConditionVO;
import nc.uap.ctrl.tpl.gz.base.CpGzTemplateVO;
import nc.uap.lfw.core.log.LfwLogger;

public class CpGzTemplateInnerServiceImpl implements ICpGzTemplateInnerService {

	@Override
	public void initConditons(String pk_template, CpGzConditionVO[] conditions)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
//			List<CpQueryConditionVO> list = (List<CpQueryConditionVO>) dao.retrieveByClause(CpQueryConditionVO.class, "pkTemplet='" + pk_template + "'");
//			Iterator<CpQueryConditionVO> it = list.iterator();
			dao.deleteByClause(CpGzConditionVO.class, "pk_gz_template='" + pk_template + "'");
			if(null!=conditions)			
				dao.insertVOArray(conditions);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void initTemplate(CpGzTemplateVO templateVo)
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
			dao.deleteByPK(CpGzTemplateVO.class, pk_template);
			dao.deleteByClause(CpGzConditionVO.class, "pk_gz_template='"+pk_template+"'");
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	
	}

	@Override
	public void updateTemplate(CpGzTemplateVO templateVo)
			throws TplBusinessException {
		BaseDAO dao = new BaseDAO();
		try {
			dao.updateVO(templateVo);
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage(), e);
		}
	}

}
