package nc.uap.cpb.org.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.itf.uap.rbac.IUserManageQuery_C;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.appendproduct.AppendProductConfig;
import nc.vo.sm.UserVO;
import org.apache.commons.beanutils.BeanUtils;
public class CpUserQry implements ICpUserQry { 
	public CpUserVO[] getAllUserByCondition(Class<?> voClass, String condition) throws CpbBusinessException {
		String sql = "select * from cp_user where " + condition;
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO[] getAllUsers() throws CpbBusinessException {
		String sql = "select * from cp_user";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp) throws CpbBusinessException {
		String sql = "select a.* from cp_user a, pt_usercorp b where a.pk_user = b.pk_user and b.pk_corp = ? order by a.ext0, a.ext1";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_corp);
		return CpbUtil.returnArray(this.queryList(sql, param));
	}
	public CpUserVO[] getAllUserByPkDept(String pk_dept) throws CpbBusinessException {
		String sql = "select a.* from cp_user a, pt_userdept b where a.pk_user = b.pk_user and b.pk_dept = ?  order by a.ext0, a.ext1";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_dept);
		return CpbUtil.returnArray(this.queryList(sql, param));
	}
	public CpUserVO[] getAllUsersExcep(String pk_user, String pk_group) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.pk_user <> ? and p.pk_group = ? order by p.ext0, p.ext1";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_user);
		parameter.addParam(pk_group);
		return CpbUtil.returnArray(this.queryList(sql, parameter));
	}
	public CpUserVO[] getCorpUsersExcep(String pk_corp, String pk_user) throws CpbBusinessException {
		String sql = "select * from cp_user where pk_corp = ? and usertype = 1 and (disused is null or disused = 'N') and pk_user <> ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_corp);
		param.addParam(pk_user);
		return CpbUtil.returnArray(this.queryList(sql, param));
	}
	public CpUserVO[] getUserByCondition(String value) throws CpbBusinessException {
		String sql = "select * from cp_user where cuserid like '" + "%" + value + "%" + "' or user_name like '" + "%" + value + "' or staffcode like '" + "%" + value + "%" + "' or nickname like '"
				+ "%" + value + "%" + "'";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO getUserByCode(String userId) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.user_code = '" + userId + "'";
		CpUserVO userVo = CpbUtil.returnValue(this.queryList(sql, null));
		return userVo;
	}
	public CpUserVO getGlobalUserByCode(String userId) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.user_code = '" + userId + "'";
		CpUserVO userVo = CpbUtil.returnValue(this.queryList(sql, null));
		if (userVo == null) {
			try {
				UserVO ncUserVo = NCLocator.getInstance().lookup(IUserManageQuery.class).findUserByCode(userId, AppendProductConfig.getInstance().getNCPortalDsName());				
				if(ncUserVo==null)
					return null;
				userVo = new CpUserVO();
				BeanUtils.copyProperties(userVo, ncUserVo);
			} catch (Exception e) {
				throw new LfwRuntimeException(e.getMessage(), e);
			}
		}
		return userVo;
	}
	public CpUserVO getUserByCodeWithGroupAdmin(String userId) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.user_code = '" + userId + "'";
		CpUserVO userVo = CpbUtil.returnValue(this.queryList(sql, null));
		if (userVo == null) {
			try {
				UserVO ncUserVo = NCLocator.getInstance().lookup(IUserManageQuery.class).findUserByCode(userId, AppendProductConfig.getInstance().getNCPortalDsName());
				if (ncUserVo == null) {
					return null;
				}
				if(ncUserVo.getUser_type() != CpUserVO.USER_TYPE_GROUP_ADM)
					return null;
				userVo = new CpUserVO();
				BeanUtils.copyProperties(userVo, ncUserVo);
			} catch (Exception e) {
				throw new LfwRuntimeException(e.getMessage(), e);
			}
		}
		return userVo;
	}
	public CpUserVO getUserByOrigiPk(String original_pk) throws CpbBusinessException {
		String sql = "select * from cp_user where original_pk = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(original_pk);
		return CpbUtil.returnValue(this.queryList(sql, param));
	}
	public CpUserVO getUserByPk(String pk_user) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.cuserid = '" + pk_user + "'";
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	public CpUserVO[] getUserByPwdlevelCode(String pwdlevelcode) throws CpbBusinessException {
		String sql = "select * from cp_user where pwdlevelcode = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pwdlevelcode);
		return CpbUtil.returnArray(this.queryList(sql, param));
	}
	public CpUserVO[] getUserByUserGroup(String pk_usergroup) throws CpbBusinessException {
		String sql = "select * from cp_user where pk_usergroupforcreate = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_usergroup);
		return CpbUtil.returnArray(this.queryList(sql, param));
	}
	public CpUserVO getUserByUsername(String username) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.username = '" + username + "'";
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	public String[] getUserPks(String pk_group) throws CpbBusinessException {
		String sql = "select pk_user from cp_user where pk_group = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_group);
		return CpbUtil.returnArray(CpbUtil.queryList(sql, null, String.class));
	}
	public CpUserVO[] getUserByPkGroup(String pk_group) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.pk_group = '" + pk_group + "'";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO[] getUserByPkorg(String pk_org) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.pk_org = '" + pk_org + "'";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO[] getAllUserByPkRole(String pk_role) throws CpbBusinessException {
		String sql = "select a.* from cp_user as a inner join cp_roleuser as b on a.pk_user=b.pk_user and b.pk_role='" + pk_role + "' order by a.ext0, a.ext1";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp, String systemCode) throws CpbBusinessException {
		String sql = "select * from cp_user where pk_corp = ? and original =?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk_corp);
		parameter.addParam(systemCode);
		parameter.addParam(systemCode);
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	public CpUserVO getUserBySerialNo(String serialno) throws CpbBusinessException {
		String sql = "select * from cp_user p where p.loginserialno = '" + serialno + "'";
		return CpbUtil.returnValue(this.queryList(sql, null));
	}
	protected List<CpUserVO> queryList(String sql, SQLParameter parameter) throws CpbBusinessException {
		return CpbUtil.queryList(sql, parameter, CpUserVO.class);
	}
	public CpUserVO[] getUserByPkS(String[] pk_users) throws CpbBusinessException {
		if (pk_users == null || pk_users.length == 0) {
			return null;
		}
		String str = CpbUtil.joinQryArrays(pk_users);
		String sql = "select * from cp_user p  where p.cuserid in(" + str + ")";
		return CpbUtil.returnArray(this.queryList(sql, null));
	}
	@SuppressWarnings("unchecked") public String[] getUserPksByRoles(String[] pk_roles) throws CpbBusinessException {
		if (pk_roles == null || pk_roles.length == 0) {
			return null;
		}
		String str = CpbUtil.joinQryArrays(pk_roles);
		String sql = "select p.pk_user from cp_userrole p  inner join cp_user u on p.pk_user=u.cuserid " + "where p.pk_role in(" + str + ")";
		PtBaseDAO dao = new PtBaseDAO();
		List<Object[]> list = null;
		try {
			list = (List<Object[]>) dao.executeQuery(sql, new ArrayListProcessor());
		} catch (DAOException e) {
			throw new LfwRuntimeException(e.getMessage());
		}
		if (list == null || list.size() == 0) {
			return null;
		}
		String[] strAray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strAray[i] = (String) list.get(i)[0];
		}
		return strAray;
	}
	public CpUserVO[] getAllUserByPkDept(String pk_dept, PaginationInfo pg) throws CpbBusinessException {
		String sql = "select a.* from cp_user a, pt_userdept b where a.pk_user = b.pk_user and b.pk_dept = '" + pk_dept + "'";
		String orderBy = "a.ext0, a.ext1";
		try {
			SuperVO[] vos = CRUDHelper.getCRUDService().queryVOs(sql, CpUserVO.class, pg, orderBy, null);
			return CpbUtil.convert(vos, CpUserVO.class);
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public CpUserVO[] getAllUsersByPkCorp(String pk_corp, PaginationInfo pg) throws CpbBusinessException {
		String sql = "select a.* from cp_user a, cp_usercorp b where a.pk_user = b.pk_user and b.pk_corp = '" + pk_corp + "'";
		String orderBy = "a.ext0, a.ext1";
		try {
			SuperVO[] vos = CRUDHelper.getCRUDService().queryVOs(sql, CpUserVO.class, pg, orderBy, null);
			return CpbUtil.convert(vos, CpUserVO.class);
		} catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public CpUserVO[] getUserByWhere(String where) throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_user  where " + where;
		List<CpUserVO> list = null;
		try {
			list = (List<CpUserVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpUserVO.class));
			if (list == null || list.size() < 1)
				return new CpUserVO[] {};
			return list.toArray(new CpUserVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@Override public UserVO getNCUserByUserPk(String userPk) throws CpbBusinessException {
		try {
			return NCLocator.getInstance().lookup(IUserManageQuery_C.class).getUser(userPk);
		} catch (BusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
