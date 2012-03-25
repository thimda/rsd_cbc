package nc.uap.cpb.templaterela.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.cpb.templaterela.itf.ITemplateRelationService;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;

public class TemplateRelationServiceImpl implements ITemplateRelationService {

	@Override
	public void createTemplateOrgVO(CpTemplateOrgVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error("创建模板-组织vo出错", e);
			throw new LfwRuntimeException("创建模板-组织vo出错", e);
		}
	}

	@Override
	public void createTemplateRoleVO(CpTemplateRoleVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error("创建模板-角色vo出错", e);
			throw new LfwRuntimeException("创建模板-角色vo出错", e);
		}
	}

	@Override
	public void createTemplateUserVO(CpTemplateUserVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error("创建模板-用户vo出错", e);
			throw new LfwRuntimeException("创建模板-用户vo出错", e);
		}
	}

}
