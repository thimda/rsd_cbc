package nc.uap.cpb.templaterela.impl;

import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.uap.cpb.org.util.SecurityUtil;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.cpb.templaterela.itf.ITemplateRelationQryService;
import nc.uap.cpb.templaterela.vo.CpTemplateOrgVO;
import nc.uap.cpb.templaterela.vo.CpTemplateRoleVO;
import nc.uap.cpb.templaterela.vo.CpTemplateUserVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.stylemgr.vo.UwTemplateVO;

import org.apache.commons.lang.StringUtils;

public class TemplateRelationQryServiceImpl implements
		ITemplateRelationQryService {

	@Override
	public CpTemplateOrgVO[] getTemplateOrgVOsByCondition(String sqlWhere) {
		PtBaseDAO dao = new PtBaseDAO();
		CpTemplateOrgVO[] vos = null;
		try {
			vos = (CpTemplateOrgVO[]) dao.queryByCondition(
					CpTemplateOrgVO.class, sqlWhere);
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
			vos = (CpTemplateRoleVO[]) dao.queryByCondition(
					CpTemplateRoleVO.class, sqlWhere);
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
			vos = (CpTemplateUserVO[]) dao.queryByCondition(
					CpTemplateUserVO.class, sqlWhere);
		} catch (DAOException e) {
			LfwLogger.error("查询模板-用户表出错", e);
			throw new LfwRuntimeException("查询模板-用户表出错", e);
		}
		return vos;
	}

	@Override
	public String getTemplatePkByUser(CpUserVO user, String funcode)
			throws LfwBusinessException {
		String pk_user = user.getCuserid();
		String pk_org = user.getPk_org();
		String roles = StringUtils
				.join(SecurityUtil.getRolePks(pk_user), "','");

		String sql = "SELECT aa.pk_template,tuser.pk_user,trole.pk_role,torg.pk_org FROM uw_template aa " +
				"left  JOIN cp_templateuser tuser  ON aa.pk_template = tuser.pk_template " +
				"left JOIN cp_templaterole trole  ON aa.pk_template = trole.pk_template  " +
				"left JOIN cp_templateorg torg  ON aa.pk_template = torg.pk_template  " +
				"WHERE aa.pk_funcnode IN (SELECT pk_appsnode FROM cp_appsnode WHERE id = '"+funcode+"')  AND  ( " +
				"tuser.pk_user = '"+ pk_user +"' or  " +
				"trole.pk_role  in('"+ roles +"') or  " +
				"torg.pk_org = '"+ pk_org +"' )";
		
		PtBaseDAO dao = new PtBaseDAO();
		String[] pk = new String[2];
		try {
			List<Map<String, ?>> list = (List<Map<String, ?>>) dao.executeQuery(sql, new MapListProcessor());
			if(list != null && !list.isEmpty()){
				for(int i = 0; i < list.size(); i++){
					Map<String, ?> rs = list.get(i);
					String pk_template = (String) rs.get("pk_template");
					String tuser = (String)rs.get("pk_user");
					if(tuser != null){
						return pk_template;
					}
					String trole = (String)rs.get("pk_role");
					if(trole != null && pk[0] != null){
						pk[0] = pk_template;
					}
					String torg = (String)rs.get("pk_org");
					if(torg != null && pk[1] != null){
						pk[1] = pk_template;
					}
				}
				return pk[0] != null ? pk[0] : pk[1]; 
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return null;
	}

}
