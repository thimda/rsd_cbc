package nc.uap.cpb.templaterela.impl;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.uap.cpb.org.util.SecurityUtil;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.cpb.templaterela.itf.ITemplateRelationQryService;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;

import org.apache.commons.lang.StringUtils;

public class TemplateRelationQryServiceImpl implements
		ITemplateRelationQryService {

	@Override
	public CpTemplateOrgVO[] getTemplateOrgVOsByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		CpTemplateOrgVO[] vos = null;
		try {
			vos = (CpTemplateOrgVO[]) dao.queryByCondition(CpTemplateOrgVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("查询模板—组织表出错", e);
			throw new LfwRuntimeException("查询模板—组织表出错", e);
		}
		return vos;
	}

	@Override
	public CpTemplateRoleVO[] getTemplateRoleVOsByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		CpTemplateRoleVO[] vos = null;
		try {
			vos = (CpTemplateRoleVO[]) dao.queryByCondition(CpTemplateRoleVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("查询模板—角色表出错", e);
			throw new LfwRuntimeException("查询模板—角色表出错", e);
		}
		return vos;
	}

	@Override
	public CpTemplateUserVO[] getTemplateUserVOsByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		CpTemplateUserVO[] vos = null;
		
		try {
			vos = (CpTemplateUserVO[]) dao.queryByCondition(CpTemplateUserVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("查询模板-用户表出错", e);
			throw new LfwRuntimeException("查询模板-用户表出错", e);
		}
		return vos;
	}
	
	
	@Override
	public String getTemplatePkByUser(String pk_user, String templatetype) throws TplBusinessException {
		BaseDAO dao = new BaseDAO();
		//直接根据用户pk查询用户关联模板
		String sqluser = "select pk_template from cp_templateuser where pk_user = ? and templatetype = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_user);
		param.addParam(templatetype);
		String pk_template = null;
		try {
			Object[] objs = (Object[]) dao.executeQuery(sqluser, param, new ArrayProcessor());
			if(objs != null)
				pk_template =  (String) objs[0];
			
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		if(pk_template != null)
			return pk_template;
		//根据角色查询用户关联的模板
		String [] pk_roles = SecurityUtil.getRolePks(pk_user);
		String pk_role = StringUtils.join(pk_roles, "','");
		if(pk_role != null){
			String sqlrole = "select pk_template from cp_templaterole where  templatetype = '" + templatetype + "' and pk_role in ('" + pk_role + "')";
			try {
				Object[] objs = (Object[]) dao.executeQuery(sqlrole, new ArrayProcessor());
				if(objs != null)
					pk_template =  (String) objs[0];
			} catch (DAOException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e);
			}
			
		}
		return pk_template;
	}

}
