package nc.uap.cpb.org.impl;
import java.util.ArrayList;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpResourceBill;
import nc.uap.cpb.org.vos.CpResourceVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import org.apache.commons.collections.CollectionUtils;
public class CpResourceBill implements ICpResourceBill {
	public String[] add(CpResourceVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();		
		try {
			String[] pks = dao.insertVOs(vos);
			return pks;
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}
	public void deleteResourceByOriginalid(String originalid) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter params = new SQLParameter();
		params.addParam(originalid);
		try {
			dao.deleteByClause(CpResourceVO.class, " originalid = ?", params);
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}
	public void deleteResourceByOriginalids(String[] originalid) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		SQLParameter params = new SQLParameter();
		params.addParam(originalid);
		try {
			dao.deleteByClause(CpResourceVO.class, " originalid = ?", params);
		} catch (DAOException e) {
			throw new CpbBusinessException(e);
		}
	}
	public void deleteResourceVos(CpResourceVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
	}
	public CpResourceVO getResourceByoriginalid(String originalid) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from cp_resource p where p.originalid = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(originalid);
		CpResourceVO vo = null;
		try {
			vo = (CpResourceVO) dao.executeQuery(sql, parameter, new BeanProcessor(CpResourceVO.class));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		return vo;
	}
	public void groupResourcSyn(String pk_group) throws CpbBusinessException {
	// IPtPortalPageService pageService = PortalServiceUtil.getPageService();
	// IPtPortletService portletService = PortalServiceUtil.getPortletService();
	// IPtResourceService resourceService =
	// PMngServiceFacility.getResourceService();
	// //同步page
	// pageService.sync(pk_group);
	// //同步portlet
	// portletService.sync(pk_group);
	// //同步资源
	// resourceService.resourceSynchronize(pk_group);
	}
	@SuppressWarnings("unchecked") public void resourceSynchronize(String pk_group) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			List<CpResourceVO> list = (List<CpResourceVO>) dao.retrieveByClause(CpResourceVO.class,
					" pk_org ='00000000000000000000'  AND resourcetype != 3  AND  originalid NOT IN (SELECT originalid FROM pt_resource WHERE pk_group ='" + pk_group + "') ");
			if (CollectionUtils.isNotEmpty(list)) {
				List<CpResourceVO> vos = new ArrayList<CpResourceVO>();
				for (CpResourceVO pg : list) {
					CpResourceVO vo = (CpResourceVO) pg.clone();
					vo.setPk_group(pk_group);
					vo.setPk_resource(null);
					vos.add(vo);
				}
				dao.insertVOs(vos.toArray(new CpResourceVO[0]));
			}
		} catch (Exception e) {
			String msg = "集团:" + pk_group + "资源同步错误!";
			LfwLogger.error(msg, e);
			throw new CpbBusinessException(msg);
		}
	}
	public void update(CpResourceVO[] vos) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(vos);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new CpbBusinessException("资源保存失败");
		}
	}
}
