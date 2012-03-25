package nc.uap.cpb.org.impl;
import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.util.SecurityUtil;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.lang.StringUtils;
/**
 * 管理节点查找实现类
 * 
 * 
 */
public class CpAppsNodeQry implements ICpAppsNodeQry {
	/**
	 * 查找所有的功能节点目录
	 * 
	 * @param appTypeId
	 * @param groupPk
	 * @throws PortalServiceException
	 */
	@SuppressWarnings("unchecked") public CpAppsCategoryVO[] getAllCategory(String pk_module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appscategory where  pk_module=?";
		parameter.addParam(pk_module);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsCategoryVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") public CpAppsCategoryVO[] getAllCategory() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from cp_appscategory";
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsCategoryVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user,String pk_appscategory) throws CpbBusinessException{
		PtBaseDAO dao = new PtBaseDAO();
		String[] rolepks = SecurityUtil.getRolePks(pk_user); 
		
		String roles = StringUtils.join(rolepks,"','");
		
		SQLParameter parameter = new SQLParameter();
		String sql = "select aa.* from cp_appsnode aa  left join cp_resp_func bb  on (bb.busi_pk=aa.pk_appsnode) " +
		"left join  cp_responsibility cc on (cc.pk_responsibility=bb.pk_responsibility) " +
		"left join cp_roleresp dd on (cc.pk_responsibility=dd.pk_responsibility)" +
		"left join cp_role ee on (ee.pk_role=dd.pk_role)" +
		"left join cp_userrole ff on (ff.pk_role=ee.pk_role) where ff.pk_role in ('"+roles+"') and a.pk_appscategory=?";
		parameter.addParam(pk_appscategory);
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	public CpAppsNodeVO[] getNodeWithPermission(String pk_user) throws CpbBusinessException{
		PtBaseDAO dao = new PtBaseDAO();
		String[] rolepks = SecurityUtil.getRolePks(pk_user);
		
		String roles = StringUtils.join(rolepks,"','");
		
		SQLParameter parameter = new SQLParameter();
		String sql = "select aa.* from cp_appsnode aa  left join cp_resp_func bb  on (bb.busi_pk=aa.pk_appsnode) " +
				"left join  cp_responsibility cc on (cc.pk_responsibility=bb.pk_responsibility) " +
				"left join cp_roleresp dd on (cc.pk_responsibility=dd.pk_responsibility)" +
				"left join cp_role ee on (ee.pk_role=dd.pk_role)" +
				"left join cp_userrole ff on (ff.pk_role=ee.pk_role) where ff.pk_role in ('"+roles+"')";
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	
	public CpAppsNodeVO[] getAllNodesByType(String type) throws CpbBusinessException{
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode where  type =? ";
		parameter.addParam(type);
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return new CpAppsNodeVO[]{};
	}
	
	@SuppressWarnings("unchecked") 
	public CpAppsNodeVO[] getNodeByCategory(String appsCategory) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode where  pk_appscategory=? ";
		parameter.addParam(appsCategory);
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	/**
	 * 查找类别
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked") public CpAppsCategoryVO getCategory(String categoryId, String parentId, String module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appscategory where  categoryid=? and parentid=?  AND module=? ";
		parameter.addParam(categoryId);
		parameter.addParam(parentId);
		parameter.addParam(module);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	/**
	 * 查找类别
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked") public CpAppsCategoryVO getRootCategory(String categoryId, String appsId, String module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "SELECT * FROM cp_appscategory WHERE pk_appstype=? AND  categoryid=?  AND module=?  AND parentid ='*'";
		parameter.addParam(appsId);
		parameter.addParam(categoryId);
		parameter.addParam(module);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	/**
	 * 查找管理节点
	 * 
	 * @param appId
	 * @return
	 */
	@SuppressWarnings("unchecked") public CpAppsNodeVO getNodeById(String nodeId) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode where id=?";
		parameter.addParam(nodeId);
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
//	public CpAppsCategoryVO[] getAppsCategorysByGroup(String pk_group) throws CpbBusinessException{
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		String sql = "select * from cp_appscategory where  pk_group=?";
//		parameter.addParam(pk_group);
//		try {
//			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
//			if (list != null && !list.isEmpty()) {
//				return list.toArray(new CpAppsCategoryVO[0]);
//			}
//		} catch (DAOException e) {
//			Logger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//		return null;
//	}
	
//	public CpAppsNodeVO[] getNodesByGroup(String pk_group) throws CpbBusinessException{
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		String sql = "select * from cp_appsnode where pk_group = ?";
//		parameter.addParam(pk_group);
//		try {
//			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
//			if (list != null && !list.isEmpty()) {
//				return list.toArray(new CpAppsNodeVO[0]);
//			}
//		} catch (DAOException e) {
//			Logger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//		return null;
//	}
	
	@Override public CpAppsNodeVO[] getNodeBySpecial(UFBoolean flag) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode where specialflag=?";
		parameter.addParam(flag.toString());
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	// @Override public CpAppsTypeVO getAppType(String appId) throws
	// CpbBusinessException {
	// return getAppType(appId, "*");
	// }
//	@SuppressWarnings("unchecked") 
//	public CpAppsTypeVO getAppType(String appId, String pk_group) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		String sql = "select * from cp_appstype where  appsid=? and pk_group=?";
//		parameter.addParam(appId);
//		parameter.addParam(pk_group);
//		try {
//			List<CpAppsTypeVO> list = (List<CpAppsTypeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsTypeVO.class));
//			if (list != null && !list.isEmpty()) {
//				return list.get(0);
//			}
//		} catch (DAOException e) {
//			Logger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//		return null;
//	}
	@SuppressWarnings("unchecked") @Override public String[] getAppsCategoryGroup(String categoryid, String module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "SELECT pk_appscategory   FROM pt_appscategory   WHERE pk_group!='*' AND categoryid=?  AND module=?   ORDER BY pk_group";
		parameter.addParam(categoryid);
		parameter.addParam(module);
		try {
			List<String> list = (List<String>) dao.executeQuery(sql, parameter, new ColumnListProcessor(1));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new String[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	// @SuppressWarnings("unchecked")
	// @Override
	// public String[] getAppsTypeGroup(String appsTypeId) throws
	// CpbBusinessException {
	// PtBaseDAO dao = new PtBaseDAO();
	// SQLParameter parameter = new SQLParameter();
	// String sql =
	// "SELECT pk_appstype   FROM pt_appstype   WHERE pk_group!='*' AND appsid=? ORDER BY pk_group";
	// parameter.addParam(appsTypeId);
	// try {
	// List<String> list = (List<String>) dao.executeQuery(sql, parameter, new
	// ColumnListProcessor(1));
	// if (list != null && !list.isEmpty()) {
	// return list.toArray(new String[0]);
	// }
	// } catch (DAOException e) {
	// Logger.error(e.getMessage(), e.getCause());
	// throw new CpbBusinessException(e);
	// }
	// return null;
	// }
	@SuppressWarnings("unchecked") @Override public String[] getDeriveCategory(String categoryPK) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "SELECT pk_appscategory FROM   pt_appscategory WHERE prototypeid=?";
		parameter.addParam(categoryPK);
		try {
			List<String> list = (List<String>) dao.executeQuery(sql, parameter, new ColumnListProcessor(1));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new String[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@Override public String getParentCategory(String categoryPK, String module, String categoryid) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "SELECT pk_appscategory FROM   pt_appscategory WHERE prototypeid=? and module=?  ";
		parameter.addParam(categoryPK);
		parameter.addParam(module);
		try {
			String list = (String) dao.executeQuery(sql, parameter, new ColumnProcessor(1));
			return list;
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
	@SuppressWarnings("unchecked") @Override public CpAppsCategoryVO[] getAppsCategoryByParent(String pk_parent) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appscategory where  pk_parent=?";
		parameter.addParam(pk_parent);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsCategoryVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") @Override public CpAppsCategoryVO getCategoryByPk(String pk_category) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appscategory where  pk_category=? ";
		parameter.addParam(pk_category);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") @Override public CpAppsCategoryVO[] getAppsCategoryByModule(String pk_module) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appscategory where  pk_module=?";
		parameter.addParam(pk_module);
		try {
			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsCategoryVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") @Override public CpAppsNodeVO[] getNodeByPks(String[] pk_funnodes) throws CpbBusinessException {
		if (pk_funnodes == null || pk_funnodes.length < 1) {
			return new CpAppsNodeVO[] {};
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pk_funnodes.length; i++) {
			sb.append("'").append(pk_funnodes[i]).append("'");
			if (i != pk_funnodes.length - 1)
				sb.append(",");
		}
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode where  pk_appsnode in(" + sb.toString() + ")";
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return new CpAppsNodeVO[] {};
	}
	@SuppressWarnings("unchecked") @Override public CpAppsNodeVO[] getAllNodes() throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter parameter = new SQLParameter();
		String sql = "select * from cp_appsnode";
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, parameter, new BeanListProcessor(CpAppsNodeVO.class));
			if (list != null && !list.isEmpty()) {
				return list.toArray(new CpAppsNodeVO[0]);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return null;
	}
	@SuppressWarnings("unchecked") public CpAppsNodeVO[] getNodeByUser(String appsCategory, String pk_user, int resourceType) throws CpbBusinessException {
		List<String> fpks = new ArrayList<String>();
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter param = new SQLParameter();
		param.addParam(appsCategory);
		String sql = "select * from cp_appsnode where pk_appscategory = ? and  nodeid in " + "('" + StringUtils.join(fpks.toArray(new String[0]), "','") + "')";
		try {
			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.executeQuery(sql, param, new BeanListProcessor(CpAppsNodeVO.class));
			return list.toArray(new CpAppsNodeVO[0]);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
	public CpAppsNodeVO getNodeByPk(String pk_funnode) throws CpbBusinessException {
		CpAppsNodeVO[] vos = this.getNodeByPks(new String[] { pk_funnode });
		if(vos==null||vos.length==0){
			return null;
		}
		return vos[0];
	}

	@Override
	public CpAppsNodeVO[] getAppsNodeVos(String where)
			throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_appsnode  where "+where;
		List<CpAppsNodeVO> list = null;
		try {
			list = (List<CpAppsNodeVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpAppsNodeVO.class));
			if (list == null || list.size() < 1)
				return new CpAppsNodeVO[] {};
			return list.toArray(new CpAppsNodeVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public CpAppsCategoryVO[] getAppsCategoryVos(String where)
			throws CpbBusinessException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		String sql = "select * from cp_appscategory  where "+where;
		List<CpAppsCategoryVO> list = null;
		try {
			list = (List<CpAppsCategoryVO>) baseDAO.executeQuery(sql, new BeanListProcessor(CpAppsCategoryVO.class));
			if (list == null || list.size() < 1)
				return new CpAppsCategoryVO[] {};
			return list.toArray(new CpAppsCategoryVO[list.size()]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
}
