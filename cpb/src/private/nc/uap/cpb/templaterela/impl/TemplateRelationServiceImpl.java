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
			LfwLogger.error("����ģ��-��֯vo����", e);
			throw new LfwRuntimeException("����ģ��-��֯vo����", e);
		}
	}

	@Override
	public void createTemplateRoleVO(CpTemplateRoleVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error("����ģ��-��ɫvo����", e);
			throw new LfwRuntimeException("����ģ��-��ɫvo����", e);
		}
	}

	@Override
	public void createTemplateUserVO(CpTemplateUserVO vo) {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(vo);
		} catch (DAOException e) {
			LfwLogger.error("����ģ��-�û�vo����", e);
			throw new LfwRuntimeException("����ģ��-�û�vo����", e);
		}
	}

}
