package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeBill;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.cpb.org.vos.CpAppsTypeVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * 管理节点增加、修改实现类
 * 
 * 
 */ 
public class CpAppsNodeBill implements ICpAppsNodeBill {
	/**
	 * 增加一个Node
	 * 
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String add(CpAppsNodeVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String pk = dao.insertVOWithPK(vo);
			return pk;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}
	
	

	/**
	 * 批量处理管员节点类型
	 * 
	 * @param appVO
	 * @throws CpbBusinessException
	 */
	public void saveGroup(CpAppsTypeVO appVO) throws CpbBusinessException {
//		IPortalServerService groupQry = PortalServiceUtil.getPortalServerService();
//		PtGroupVO[] groups = groupQry.getAllGroups();
//		appVO.setParentid(appVO.getPk_appstype());
//		appVO.setPk_appstype(null);
//		BreedSuperVO<CpAppsTypeVO> cs = new BreedSuperVO<CpAppsTypeVO>();
//		List<CpAppsTypeVO> appVOs = cs.breed(appVO, groups);
//		add(appVOs.toArray(new CpAppsTypeVO[0]));
	}

	/**
	 * 清理已经删除的管员节点类型
	 * 
	 * @param appVO
	 * @throws CpbBusinessException
	 */
	public void clearGroup() throws CpbBusinessException {
		// 删除的
//		String bedelappstype = "delete FROM pt_appstype WHERE pk_group!='*' and pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_group )";
//		String bedelcategory = "delete FROM pt_appscategory WHERE pk_group!='*' and pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_group )";
//		String bedelappsnode = "delete FROM pt_appsnode WHERE pk_group!='*' and pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_group )";
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			dao.executeUpdate(bedelappstype);
//			dao.executeUpdate(bedelcategory);
//			dao.executeUpdate(bedelappsnode);
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
	}

	/**
	 * 增量新集团的管员节点类型
	 * 
	 * @param appVO
	 * @throws CpbBusinessException
	 */
	public void incremGroup(CpAppsTypeVO appVO) throws CpbBusinessException {
//		// 要增加的集团
//		String sql = "SELECT pk_group FROM pt_group WHERE pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_appstype  where parentid=?)";
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		try {
//			parameter.addParam(appVO.getPk_appstype());
//			List<String> list = (List<String>) dao.executeQuery(sql, parameter, new ColumnListProcessor(1));
//			BreedSuperVO<CpAppsTypeVO> bs = new BreedSuperVO<CpAppsTypeVO>();
//			appVO.setParentid(appVO.getPk_appstype());
//			appVO.setPk_appstype(null);
//			List<CpAppsTypeVO> appVOs = bs.breed(appVO, list.toArray(new String[0]));
//			add(appVOs.toArray(new CpAppsTypeVO[0]));
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
	}

	/**
	 * 增量新集团的管理员节点目录
	 * 
	 * @param cateVO
	 * @throws CpbBusinessException
	 */
	public void incremGroup(CpAppsCategoryVO cateVO) throws CpbBusinessException {
//		String sql = "SELECT pk_group FROM pt_group WHERE pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_appscategory   where prototypeid=? )";
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		try {
//			parameter.addParam(cateVO.getPk_appscategory());
//			List<String> list = (List<String>) dao.executeQuery(sql, parameter, new ColumnListProcessor(1));
//			if (CollectionUtils.isEmpty(list))
//				return;
//			cateVO.setPrototypeid(cateVO.getPk_appscategory());
//			cateVO.setPk_appscategory(null);
//			BreedSuperVO<CpAppsCategoryVO> bs = new BreedSuperVO<CpAppsCategoryVO>();
//			List<CpAppsCategoryVO> cateVos = bs.breed(cateVO, list.toArray(new String[0]));
//			if (CollectionUtils.isNotEmpty(cateVos)) {
//				if (StringUtils.equals(cateVO.getParentid(), "*")) {
//					// 从apptype表中查
//					Object[][] objArr = getDeriveAppsType(cateVO.getPk_appstype());
//					if(objArr!=null) {
//					for (int i = 0; i < cateVos.size(); i++) {
//						Object[] currInfo = selectInfo(cateVos.get(i).getPk_group(), objArr);
//						if (currInfo != null && currInfo.length > 0)
//							cateVos.get(i).setPk_appstype((String) currInfo[1]);
//					}
//					}
//				} else {
//					Object[][] objArr = getDeriveCategorys(cateVO.getParentid());
//					if(objArr!=null) {
//					for (int i = 0; i < cateVos.size(); i++) {
//						Object[] currInfo = selectInfo(cateVos.get(i).getPk_group(), objArr);
//						if (currInfo != null && currInfo.length > 1) {
//							cateVos.get(i).setPk_appstype((String) currInfo[1]);
//							cateVos.get(i).setParentid((String) currInfo[2]);
//						}
//					}
//					}
//				}
//			}
//			add(cateVos.toArray(new CpAppsCategoryVO[0]));
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
	}

	/**
	 * 增量新集团的管理节点
	 * 
	 * @param cateVO
	 * @throws CpbBusinessException
	 */
	public void incremGroup(CpAppsNodeVO cateVO) throws CpbBusinessException {
//		String sql = "SELECT pk_group FROM pt_group WHERE pk_group NOT IN (SELECT DISTINCT pk_group FROM pt_appsnode   where nodeid=? and module=? )";
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		try {
//			parameter.addParam(cateVO.getNodeid());
//			parameter.addParam(cateVO.getModule());
//			List<String> list = (List<String>) dao.executeQuery(sql, parameter, new ColumnListProcessor(1));
//			if (CollectionUtils.isEmpty(list))
//				return;
//			cateVO.setPk_appsnode(null);
//			BreedSuperVO<CpAppsNodeVO> bs = new BreedSuperVO<CpAppsNodeVO>();
//			List<CpAppsNodeVO> cateVos = bs.breed(cateVO, list.toArray(new String[0]));
//			if (CollectionUtils.isNotEmpty(cateVos)) {
//				Object[][] objArr = getDeriveCategorys(cateVO.getPk_appscategory());
//				if(objArr!=null) {
//					for (int i = 0; i < cateVos.size(); i++) {
//						Object[] currInfo = selectInfo(cateVos.get(i).getPk_group(), objArr);
//						cateVos.get(i).setPk_appscategory((String) currInfo[2]);
//					}
//				}
//			}
//			add(cateVos.toArray(new CpAppsNodeVO[0]));
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
	}
	
	/**
	 * 同步功能节点
	 * @param pk_group
	 * @throws CpbBusinessException
	 */
	public void sync(String pk_group) throws CpbBusinessException{
		LfwLogger.debug("===PtPortalManagerAppsServiceImpl#sync=== pk_group: " + pk_group);
//		syncAppsType(pk_group);
//		syncAppsCategory(pk_group);
//		syncAppsNode(pk_group);
	}
	
	/**
	 * 同步功能节点类型
	 * @param pk_group
	 * @throws CpbBusinessException
	 */
//	@SuppressWarnings("unchecked")
//	private void syncAppsType(String pk_group) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			List<CpAppsTypeVO> list = (List<CpAppsTypeVO>) dao.retrieveByClause(CpAppsTypeVO.class, " parentid ='*' and pk_group ='*' and pk_appstype NOT IN (SELECT parentid FROM pt_appstype WHERE pk_group ='"+pk_group+"') ") ;
// 			if (CollectionUtils.isNotEmpty(list)) {
//				List<CpAppsTypeVO> vos = new ArrayList<CpAppsTypeVO>();
//				for(CpAppsTypeVO pg : list){
//					CpAppsTypeVO vo = (CpAppsTypeVO)pg.clone();
//					vo.setPk_group(pk_group);
//					vo.setParentid(pg.getPk_appstype());
//					vo.setPk_appstype(null);
// 					vos.add(vo);
//				}
//				dao.insertVOs(vos.toArray(new CpAppsTypeVO[0]));
//  			}
//		} catch (Exception e) {
//			LfwLfwLogger.error(e.getMessage(), e);
//			throw new CpbBusinessException("集团:"+pk_group+"Portal页面资源同步错误!");
//		}
//	}
	
	/**
	 * 同步功能节点分类
	 * @param pk_group
	 * @throws CpbBusinessException
	 */
//	@SuppressWarnings("unchecked")
//	private void syncAppsCategory(String pk_group) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.retrieveByClause(CpAppsCategoryVO.class, " pk_group ='*' and ISNULL(prototypeid, '~') = '~' and pk_appscategory NOT IN (SELECT prototypeid FROM pt_appscategory WHERE pk_group ='"+pk_group+"') ") ;
// 			if (CollectionUtils.isNotEmpty(list)) {
//				List<CpAppsCategoryVO> vos = new ArrayList<CpAppsCategoryVO>();
//				for(CpAppsCategoryVO pg : list){
//					CpAppsCategoryVO vo = (CpAppsCategoryVO)pg.clone();
//					vo.setPk_group(pk_group);
//					vo.setPrototypeid(pg.getPk_appscategory());
//					vo.setPk_appscategory(null);
//					vo.setPk_appstype(getAppsTypePK(pk_group, pg.getPk_appstype()));
// 					vos.add(vo);
//				}
//				CpAppsCategoryVO [] voarrya = vos.toArray(new CpAppsCategoryVO[0]);
//				dao.insertVOs(voarrya);
//				/**
//				 * 同步父目录
//				 */
//				syncParentCategroy(voarrya);
//  			}
//		} catch (Exception e) {
//			LfwLfwLogger.error(e.getMessage(), e);
//			throw new CpbBusinessException("集团:"+pk_group+"功能节点同步错误!");
//		}
//	}
	
	/**
	 *  同步父目录
	 * @param pk_group
	 * @param cate
	 * @throws CpbBusinessException 
	 */
//	private void syncParentCategroy(CpAppsCategoryVO [] voarrya) throws CpbBusinessException{PtBaseDAO dao = new PtBaseDAO();
//	try {
//		List<CpAppsCategoryVO> restoreCateList = new ArrayList<CpAppsCategoryVO>();
//		for(CpAppsCategoryVO   vo : voarrya){
//			String parentid = vo.getParentid();
//			String newParentid = findNewCodeByPrototypeid(voarrya, parentid);
//			if(!StringUtils.equals(parentid, newParentid)){
//				vo.setParentid(newParentid);
//				restoreCateList.add(vo);
//			}
//		}
//		dao.updateVOArray(restoreCateList.toArray(new CpAppsCategoryVO[0]));
//	} catch (Exception e) {
//		LfwLfwLogger.error(e.getMessage(), e);
//		throw new CpbBusinessException("同步父目录错误!");
//	}
//	}
//	/**
//	 * 获得当前编码
//	 * @param voarrya
//	 * @param prototypeid
//	 * @return
//	 */
//	private static String findNewCodeByPrototypeid(CpAppsCategoryVO [] voarrya,String prototypeid){
//		if(prototypeid == null || prototypeid.equals("*"))
//			return prototypeid;
//		if(voarrya != null && voarrya.length >0){
//			for(CpAppsCategoryVO vo : voarrya){
//				if(vo.getPrototypeid().equals(prototypeid))
//					return vo.getPk_appscategory();
//			}
//		}
//		return null;
//	}
	
//	/**
//	 * 获得功能节点类型编码
//	 * @param pk_group
//	 * @param parentid
//	 * @return
//	 * @throws CpbBusinessException
//	 */
//	private String getAppsTypePK(String pk_group,String parentid) throws CpbBusinessException{
//		if(parentid != null){
//			PtBaseDAO dao = new PtBaseDAO();
//			try {
//				List<CpAppsTypeVO> list = (List<CpAppsTypeVO>) dao.retrieveByClause(CpAppsTypeVO.class, "  pk_group ='"+pk_group+"' and parentid  ='"+parentid+"' ") ;
//	 			if (CollectionUtils.isNotEmpty(list)) {
//	 				return list.get(0).getPk_appstype();
//	  			}
//			} catch (Exception e) {
//				LfwLfwLogger.error(e.getMessage(), e);
//				throw new CpbBusinessException("集团:"+pk_group+"Portal页面资源同步错误!");
//			}
//		}
//		return null;
//	}
//	
//	/**
//	 * 同步功能节点
//	 * @param pk_group
//	 * @throws CpbBusinessException
//	 */
//	@SuppressWarnings("unchecked")
//	private void syncAppsNode(String pk_group) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			List<String> list = (List<String>) dao.executeQuery("select DISTINCT module from pt_appsnode ", new ColumnListProcessor("module"));
// 			if (CollectionUtils.isNotEmpty(list)) {
// 				for(String module : list){
// 					try {
// 	 					syncAppsNode(pk_group, module);
//					} catch (Exception e) {
//						LfwLfwLogger.error("sync appsnode fail! pkgroup:"+pk_group +" module:"+module);
//					}
//				}
//  			}
//		} catch (Exception e) {
//			LfwLfwLogger.error(e.getMessage(), e);
//			throw new CpbBusinessException("集团:"+pk_group+"功能节点同步错误!");
//		}
//	}
	
//	/**
//	 * 同步功能节点
//	 * @param pk_group
//	 * @param module
//	 * @throws CpbBusinessException
//	 */
//	@SuppressWarnings("unchecked")
//	private void syncAppsNode(String pk_group,String module) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			List<CpAppsNodeVO> list = (List<CpAppsNodeVO>) dao.retrieveByClause(CpAppsNodeVO.class, " pk_group ='*'  and module = '"+module+"' and nodeid NOT IN (SELECT nodeid FROM pt_appsnode WHERE pk_group ='"+pk_group+"' and module = '"+module+"') ") ;
// 			if (CollectionUtils.isNotEmpty(list)) {
//				List<CpAppsNodeVO> vos = new ArrayList<CpAppsNodeVO>();
//				for(CpAppsNodeVO pg : list){
//					CpAppsNodeVO vo = (CpAppsNodeVO)pg.clone();
//					vo.setPk_group(pk_group);
//					vo.setPk_appsnode(null);
//					vo.setPk_appscategory(getCurrentCategroryPK(pk_group, pg.getPk_appscategory()));
// 					vos.add(vo);
//				}
//				dao.insertVOs(vos.toArray(new CpAppsNodeVO[0]));
//  			}
//		} catch (Exception e) {
//			LfwLfwLogger.error(e.getMessage(), e);
//			throw new CpbBusinessException("集团:"+pk_group+"功能节点同步错误!");
//		}
//	}
//	
//	/**
//	 * 获得该集团的分类编码
//	 * @param pk_group
//	 * @param prototypeid
//	 * @return
//	 * @throws CpbBusinessException
//	 */
//	private String getCurrentCategroryPK(String pk_group,String prototypeid) throws CpbBusinessException{
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			List<CpAppsCategoryVO> list = (List<CpAppsCategoryVO>) dao.retrieveByClause(CpAppsCategoryVO.class, " pk_group ='"+pk_group+"'  and prototypeid = '"+prototypeid+"' ") ;
// 			if (CollectionUtils.isNotEmpty(list)) {
// 				return list.get(0).getPk_appscategory();
// 			}
// 		}catch (Exception e) {
// 				LfwLfwLogger.error(e.getMessage(), e);
// 				throw new CpbBusinessException("集团:"+pk_group+"Portal页面资源同步错误!");
// 		}
// 		return null;	
//	}
	
//	/**
//	 * 根据原型获得集团下的管理节点分类
//	 * 
//	 * @param categoryPK
//	 * @return
//	 * @throws CpbBusinessException
//	 */
//	private Object[][] getDeriveCategorys(String categoryPK) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		String sql = "SELECT pk_group,pk_appstype,pk_appscategory FROM   pt_appscategory WHERE prototypeid=?";
//		parameter.addParam(categoryPK);
//		try {
//			List<Object[]> list = (List<Object[]>) dao.executeQuery(sql, parameter, new ArrayListProcessor());
//			if (list != null && !list.isEmpty()) {
//				return list.toArray(new Object[0][0]);
//			}
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//		return null;
//	}

//	/**
//	 * 获得上级节点目录的信息
//	 * 
//	 * @param pk_group
//	 * @param o
//	 * @return
//	 */
//	private Object[] selectInfo(String pk_group, Object[][] o) {
//		if (o != null && o.length > 0) {
//			for (Object[] _o : o) {
//				if (_o != null && _o.length > 0) {
//					if (_o[0].equals(pk_group)) {
//						return _o;
//					}
//				}
//			}
//		}
//		return null;
//	}

//	/**
//	 * 得到恰当的管理节点类型
//	 * 
//	 * @param appsTypePK
//	 * @return
//	 * @throws CpbBusinessException
//	 */
//	private Object[][] getDeriveAppsType(String appsTypePK) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		SQLParameter parameter = new SQLParameter();
//		String sql = "SELECT pk_group,pk_appstype FROM   pt_appstype WHERE parentid=?";
//		parameter.addParam(appsTypePK);
//		try {
//			List<Object[]> list = (List<Object[]>) dao.executeQuery(sql, parameter, new ArrayListProcessor());
//			if (list != null && !list.isEmpty()) {
//				return list.toArray(new Object[0][0]);
//			}
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//		return null;
//	}

	/**
	 * 增加一个NodeCategory
	 * 
	 * @param vo
	 * @return
	 * @throws CpbBusinessException
	 */
	public String add(CpAppsCategoryVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String pk = dao.insertVOWithPK(vo);
			incremGroup(vo);
			return pk;
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}

	/**
	 * 更新一个Node
	 * 
	 * @param vo
	 * @throws CpbBusinessException
	 */
	public void update(CpAppsNodeVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}

	}

	/**
	 * 更新一个 Category
	 * 
	 * @param vo
	 * @throws CpbBusinessException
	 */
	public void update(CpAppsCategoryVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}

	}

	

	
//	@Override
//	public String add(CpAppsTypeVO vo) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			String pk= dao.insertVO(vo);
//			incremGroup(vo);
//			return pk;
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//	}
//
//	@Override
//	public void update(CpAppsTypeVO vo) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			dao.updateVO(vo);
//			String sql = "update  pt_appstype set i18nname=? ,title =?   WHERE appsid=? and parentid=?";
//			
//			SQLParameter param = new SQLParameter();
//			param.addParam(vo.getI18nname());
//			param.addParam(vo.getTitle());
//			param.addParam(vo.getAppsid());
//			param.addParam(vo.getPk_appstype());
//			dao.executeUpdate(sql, param);
//			incremGroup(vo);
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//	}
//	
//
//	@Override
//	public String[] add(CpAppsTypeVO[] vo) throws CpbBusinessException {
//		PtBaseDAO dao = new PtBaseDAO();
//		try {
//			String[] pks = dao.insertVOs(vo);
//			return pks;
//		} catch (DAOException e) {
//			LfwLogger.error(e.getMessage(), e.getCause());
//			throw new CpbBusinessException(e);
//		}
//	}

	@Override
	public void add(CpAppsNodeVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public String[] add(CpAppsCategoryVO[] vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return dao.insertVOs(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
	}


	@Override
	public String delNode(CpAppsNodeVO vo) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return vo.getPk_appsnode();
	}

	@Override
	public String delNode(String pk_appsnode) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpAppsNodeVO.class, pk_appsnode);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return pk_appsnode;
	}


 
	@Override
	public String delAppsCategory(CpAppsCategoryVO vo)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(vo);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return vo.getPk_appscategory();
	}

	@Override
	public String delAppsCategory(String pk_category)
			throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteByPK(CpAppsNodeVO.class, pk_category);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e.getCause());
			throw new CpbBusinessException(e);
		}
		return pk_category;
	}
}
