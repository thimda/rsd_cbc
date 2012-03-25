package nc.uap.wfm.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmCacheKeys;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmWorkDayBill;
import nc.uap.wfm.itf.IWfmWorkDayQry;
import nc.uap.wfm.utils.BaseUtil;
import nc.uap.wfm.vo.WfmVacationVO;
import nc.uap.wfm.vo.WfmWeekendVO;
/**
 * 工作日查询服务实现
 * 
 * @author licza
 * 
 */
public class WfmWorkDayQry implements IWfmWorkDayQry {
	@SuppressWarnings("unchecked") public WfmVacationVO[] _getHolidays() {
		WfmVacationVO[] vs = new WfmVacationVO[0];
		PtBaseDAO d = new PtBaseDAO();
		try {
			List l = (List) d.retrieveByClause(WfmVacationVO.class, " type = 0");
			if (!BaseUtil.isNull(l)) {
				vs = (WfmVacationVO[]) l.toArray(new WfmVacationVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error("获得假期时出现异常", e);
		}
		return vs;
	}
	@SuppressWarnings("unchecked") public WfmVacationVO[] _getSpecialWorkDay() {
		WfmVacationVO[] vs = new WfmVacationVO[0];
		PtBaseDAO d = new PtBaseDAO();
		try {
			List l = (List) d.retrieveByClause(WfmVacationVO.class, " type = 1");
			if (!BaseUtil.isNull(l)) {
				vs = (WfmVacationVO[]) l.toArray(new WfmVacationVO[0]);
			}
		} catch (DAOException e) {
			LfwLogger.error("获得假期时出现异常", e);
		}
		return vs;
	}
	public Integer[] _getWeekend() {
		WfmWeekendVO cfg = null;
		List<Integer> wklist = new ArrayList<Integer>();
		try {
			cfg = getWeekendProp();
			String[] days = cfg.getWeekendday().split(",");
			if (!BaseUtil.isNull(days)) {
				for (String day : days) {
					if (BaseUtil.isNumbic(day))
						wklist.add(Integer.parseInt(day));
				}
			}
		} catch (Exception e) {
			LfwLogger.error("获得周末的配置出错!", e);
		}
		return wklist.toArray(new Integer[0]);
	}
	@SuppressWarnings("unchecked") @Override public WfmWeekendVO getWeekendProp() throws WfmServiceException {
		try {
			PtBaseDAO dao = new PtBaseDAO();
			Collection coll = dao.retrieveAll(WfmWeekendVO.class);
			if (coll != null && !coll.isEmpty()) {
				WfmWeekendVO vo = (WfmWeekendVO) coll.toArray()[0];
				return vo;
			} else {
				IWfmWorkDayBill wds = NCLocator.getInstance().lookup(IWfmWorkDayBill.class);
				WfmWeekendVO vo = new WfmWeekendVO();
				vo.setWeekendday("6,7");
				return wds.add(vo);
			}
		} catch (Exception e) {
			throw new WfmServiceException(e.getMessage(), e);
		}
	}
	/**
	 * 初始化缓存
	 */
	public void initCache() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(WfmCacheKeys.PORTAL_WORK_DAY_CACHE, dsName);
		cache.put(WfmVacationVO.HOLIDAYS, _getHolidays());
		cache.put(WfmVacationVO.SPECIALWORKDAY, _getSpecialWorkDay());
		cache.put(WfmVacationVO.WEEKEND, _getWeekend());
	}
	@Override public WfmVacationVO[] getHolidays() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(WfmCacheKeys.PORTAL_WORK_DAY_CACHE, dsName);
		return (WfmVacationVO[]) cache.get(WfmVacationVO.HOLIDAYS);
	}
	@Override public WfmVacationVO[] getSpecialWorkDay() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(WfmCacheKeys.PORTAL_WORK_DAY_CACHE, dsName);
		return (WfmVacationVO[]) cache.get(WfmVacationVO.SPECIALWORKDAY);
	}
	@Override public Integer[] getWeekend() {
		String dsName = LfwRuntimeEnvironment.getDatasource();
		ILfwCache cache = LfwCacheManager.getStrongCache(WfmCacheKeys.PORTAL_WORK_DAY_CACHE, dsName);
		return (Integer[]) cache.get(WfmVacationVO.WEEKEND);
	}
}
