package nc.uap.cpb.org.impl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.itf.uap.rbac.userpassword.IUserPasswordChecker;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.generator.IdGenerator;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.extention.CpbExtentionUtil;
import nc.uap.cpb.org.extention.ICpbExtentionService;
import nc.uap.cpb.org.itf.ICpUserBill;
import nc.uap.cpb.org.user.ICpUserConst;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.framework.rsa.Encode;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.sm.UserVO;
import nc.vo.uap.rbac.userpassword.PasswordSecurityLevelFinder;
import nc.vo.uap.rbac.userpassword.PasswordSecurityLevelVO;
import nc.vo.uap.rbac.util.RbacUserPwdUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author zhangxya
 * 
 */
public class CpUserBill implements ICpUserBill {
	public void changeUserLanguage(String pk_user, String languageId) throws CpbBusinessException {
		try {
			PtBaseDAO baseDAO = new PtBaseDAO();
			String sql = "update cp_user set " + CpUserVO.CONTENTLANG + " = '" + languageId + "'" + " where cuserid = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			baseDAO.executeUpdate(sql, parameter);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public CpUserVO changeUserPwd(CpUserVO cpUserVO, String inputOldPwd, String inputNewPwd) throws BusinessException {
		String original = cpUserVO.getOriginal();
		UserVO ncuser = new UserVO();
		try {
			BeanUtils.copyProperties(ncuser, cpUserVO);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new BusinessException(e);
		} 
		String oldpwd = cpUserVO.getUser_password();
		String encodeoldpwd = RbacUserPwdUtil.getEncodedPassword(ncuser, inputOldPwd);
		if(!oldpwd.equals(encodeoldpwd)){
			throw new BusinessException("旧密码错误!");
		}
		//如果是nc用户，触发nc修改密码
		if(ICpUserConst.ORIGINAL_NC.equals(original)){
			PasswordSecurityLevelVO pwdLevel = PasswordSecurityLevelFinder.getPWDLV(ncuser);
			IUserPasswordChecker upchecher = (IUserPasswordChecker) NCLocator.getInstance().lookup(IUserPasswordChecker.class.getName());
			try {
				//更新密码
				upchecher.updateUPVO(ncuser.getCuserid(), inputNewPwd,pwdLevel);
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
				throw new BusinessException(e);
			}  
		//	return cpUserVO;
		}		
		String encodenewpwd = RbacUserPwdUtil.getEncodedPassword(ncuser, inputNewPwd);
		cpUserVO.setUser_password(encodenewpwd);
		//校验密码安全策略
		checkPwdLevel(cpUserVO);
		try {
			PtBaseDAO baseDAO = new PtBaseDAO();
			String sql = "update cp_user set user_password = ? where cuserid = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(encodenewpwd);
			parameter.addParam(cpUserVO.getCuserid());
			baseDAO.executeUpdate(sql, parameter);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
		return cpUserVO;
	}
	@Override
	public boolean checkPwdLevel(CpUserVO cpUserVO)throws BusinessException{
		UserVO ncuser = new UserVO();
		try {
			BeanUtils.copyProperties(ncuser, cpUserVO);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new BusinessException(e);
		} 
		PasswordSecurityLevelVO pwdLevel = PasswordSecurityLevelFinder.getPWDLV(ncuser);
		if(pwdLevel==null)
			return true;
		IUserPasswordChecker upchecher = (IUserPasswordChecker) NCLocator.getInstance().lookup(IUserPasswordChecker.class.getName());
		try {
			//验证密码安全策略
			upchecher.checkNewpassword(ncuser, ncuser.getUser_password(),pwdLevel, ncuser.getUser_type());
		} 
		catch (Exception be) {
			LfwLogger.error(be.getMessage(), be);
			throw new BusinessException("密码不符合安全策略!" + be.getMessage() );
		}
		return true;
	}
	public void changeUserTheme(String pk_user, String themeId) throws CpbBusinessException {
		try {
			PtBaseDAO baseDAO = new PtBaseDAO();
			String sql = "update cp_user set themeid = '" + themeId + "'" + " where cuserid = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			baseDAO.executeUpdate(sql, parameter);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deleteCpUserVO(String pk_user) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpUserVO.class, pk_user);
			//CpUserVO uservo = CpbServiceFacility.getCpUserQry().getUserByPk(pk_user);
			//deleteRelates(new CpUserVO[] { uservo });
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deleteCpUserVO(CpUserVO uservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(uservo);
			deleteRelates(new CpUserVO[] { uservo });
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public void deleteCpUserVOs(CpUserVO[] uservos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(uservos);
			deleteRelates(uservos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	@SuppressWarnings("unchecked") public CpUserVO getUser(String userId) throws BusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select *  from  cp_user where " + CpUserVO.CUSERID + " = ?";
		SQLParameter param = new SQLParameter();
		param.addParam(userId);
		List<CpUserVO> list = (List<CpUserVO>) dao.executeQuery(sql, param, new BeanListProcessor(CpUserVO.class));
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	public void updateUserPwd(String pk_user, String newPwd, UFDate passwordmodifydate) throws CpbBusinessException {
		try {
			PtBaseDAO baseDAO = new PtBaseDAO();
			String sql = "update cp_user set " + CpUserVO.USER_PASSWORD + " = '" + new Encode().encode(newPwd) + "',";
			sql = sql + CpUserVO.MODIFIEDTIME + " = '" + passwordmodifydate + "'" + " where pk_user = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			baseDAO.executeUpdate(sql, parameter);
		} catch (Exception e) {
			throw new CpbBusinessException(e);
		}
	}
	/**
	 * @param groupvos
	 * @throws CpbBusinessException
	 */
	private void deleteRelates(CpUserVO[] uservos) throws CpbBusinessException {
		String[] pk_users = new String[uservos.length];
		for (int i = 0; i < uservos.length; i++) {
			pk_users[i] = uservos[i].getCuserid();
		}
		CpbServiceFacility.getCpUserRoleBill().deletePtRoleUserByUserpks(pk_users);
	}
	
	
	private void initUserPassWord(CpUserVO userVO) throws CpbBusinessException {
		if(userVO == null) return;
		
		String oldPwd = userVO.getUser_password();
		if(StringUtils.isBlank(oldPwd)){
			IUserManageQuery userQry = NCLocator.getInstance().lookup(IUserManageQuery.class);
			String defaultPwd = null;
			try {
				defaultPwd = userQry.getUserDefaultPassword(null);
			} catch (BusinessException e) {
				LfwLogger.error(e.getMessage(), e);
			}
			UserVO ncUserVo = new UserVO();
			ncUserVo.setCuserid(userVO.getCuserid());
			ncUserVo.setUser_code(userVO.getUser_code());
			String pwd = null;
			try {
				pwd = RbacUserPwdUtil.getEncodedPassword(ncUserVo, defaultPwd);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				LfwLogger.error(e.getMessage(), e);
			}
			userVO.setUser_password(pwd);
		}
	}
	
	public String addCpUserVO(CpUserVO uservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			IdGenerator idGenerator = new SequenceGenerator();		
			uservo.setPrimaryKey(idGenerator.generate());
			//初始化用户密码
			initUserPassWord(uservo);
			String pk_user = dao.insertVO(uservo);
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE, ICpbExtentionService.ADD, pk_user);
			return pk_user;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public String addCpUserVOWithPk(CpUserVO uservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String pk_user = dao.insertVOWithPK(uservo);
			CpbExtentionUtil.notifyAfterAction(ICpbExtentionService.USERMANAGE, ICpbExtentionService.ADD, pk_user);
			return pk_user;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public void updateCpUserVO(CpUserVO uservo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(uservo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}