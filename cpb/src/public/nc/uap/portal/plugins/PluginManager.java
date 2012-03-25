package nc.uap.portal.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.IPtPluginQryService;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.constant.CacheKeys;
import nc.uap.portal.plugins.exception.PluginValidateException;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.plugins.model.PtExtensionPoint;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.IllegalClassException;

/**
 * Plugin管理类
 * 
 * @author licza
 * @since 2010年9月9日15:58:50
 */
public class PluginManager {
	private static PluginManager pm;

	private PluginManager() {
		IPtPluginQryService pluginQry = NCLocator.getInstance().lookup(IPtPluginQryService.class);
		try {
			// 数据库中扩展
			PtExtension[] exArr = pluginQry.getAllExtension();
			PtExtensionPoint[] exPointArr = pluginQry.getAllExtensionPoint();
			if (!ArrayUtils.isEmpty(exPointArr)) {
				for (PtExtensionPoint exp : exPointArr) {
					put(exp);
				}
			}
			if (!ArrayUtils.isEmpty(exArr)) {
				for (PtExtension exa : exArr) {
					put(exa);
				}
			}
		} catch (CpbBusinessException e) {
			LfwLogger.error("Plugin 初始化失败!", e);
		}
	}
	/**
	 * 刷新PluginManager缓存
	 */ 
	public void refresh(){
		getPluginCache().remove(P_EXTENSIONPOINT);
		getPluginCache().remove(P_EXTENSION_REL);
		getPluginCache().remove(P_EXTENSION);
		pm = new PluginManager();
	}

	private static final String P_EXTENSIONPOINT = "extension-point";
	private static final String P_EXTENSION_REL = "extension_rel";
	private static final String P_EXTENSION = "extension";

	private ILfwCache getPluginCache() {
		String ds = LfwRuntimeEnvironment.getDatasource();
		return LfwCacheManager.getStrongCache(CacheKeys.PLUGIN_CACHE, ds);
	}

	/**
	 * 将扩展点存入缓存
	 * 
	 * @param point
	 */
	@SuppressWarnings("unchecked")
	public void put(PtExtensionPoint point) {
		Map<String, PtExtensionPoint> points = (Map<String, PtExtensionPoint>) getPluginCache().get(P_EXTENSIONPOINT);
		if (points == null) {
			points = new ConcurrentHashMap<String, PtExtensionPoint>();
		}
		if (!points.containsKey(point.getPoint())) {
			points.put(point.getPoint(), point);
		}
		getPluginCache().put(P_EXTENSIONPOINT, points);
	}

	/**
	 * 将扩展存入缓存
	 * 
	 * @param ex
	 */
	@SuppressWarnings("unchecked")
	public void put(PtExtension ex) {
		if(ex == null)
			return;
		if(ex.getIsactive() != null && !ex.getIsactive().booleanValue())
			return;
		// 缓存一份扩展点-扩展
		Map<String, List<PtExtension>> pointCache = (Map<String, List<PtExtension>>) getPluginCache().get(P_EXTENSION_REL);
		if (pointCache == null) {
			pointCache = new ConcurrentHashMap<String, List<PtExtension>>();
		}
		String pointId = ex.getPoint();
		if(pointId == null)
			return;
		List<PtExtension> exList = pointCache.get(pointId);
		if (exList == null) {
			exList = new ArrayList<PtExtension>();
		}
		if (!exList.contains(ex)) {
			exList.add(ex);
		}
		pointCache.put(pointId, exList);
		getPluginCache().put(P_EXTENSION_REL, pointCache);
		// 缓存一份扩展名-扩展
		Map<String, PtExtension> pointValue = (Map<String, PtExtension>) getPluginCache().get(P_EXTENSION);
		if (pointValue == null) {
			pointValue = new ConcurrentHashMap<String, PtExtension>();
		}
		pointValue.put(ex.getId(), ex);
		getPluginCache().put(P_EXTENSION, pointValue);
	}

	/**
	 * 得到一个扩展点
	 * 
	 * @param point
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PtExtensionPoint getExPoint(String point) {
		Map<String, PtExtensionPoint> pointCache = (Map<String, PtExtensionPoint>) getPluginCache().get(P_EXTENSIONPOINT);
		if (pointCache != null && (pointCache.containsKey(point))) {
			return pointCache.get(point);
		}
		return null;
	}

	/**
	 * 得到扩展集合
	 * 
	 * @param pointId 扩展点ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PtExtension> getExtensions(String pointId) {
		Map<String, List<PtExtension>> expCache = (Map<String, List<PtExtension>>) getPluginCache().get(P_EXTENSION_REL);
		if (expCache == null) {
			return Collections.emptyList();
		} else {
			List<PtExtension> exs = expCache.get(pointId);
			return exs == null ? new ArrayList<PtExtension>() : exs;
		}
	}

	/**
	 * 得到扩展
	 * 
	 * @param extensionId
	 * @return
	 * @throws PluginValidateException
	 */
	@SuppressWarnings("unchecked")
	public PtExtension getExtension(String extensionId) throws CpbBusinessException {
		if (extensionId == null) {
			throw new IllegalArgumentException("扩展ID为空!");
		}
		Map<String, PtExtension> exCache = (Map<String, PtExtension>) getPluginCache().get(P_EXTENSION);
		if (exCache == null) {
			exCache = new ConcurrentHashMap<String, PtExtension>();
			getPluginCache().put(P_EXTENSION, exCache);
		}
		PtExtension ex = exCache.get(extensionId);
		if (ex == null)
			return null;
		valdator(ex);
		return ex;
	}

	/**
	 * 获得一个管理实例
	 * 
	 * @return PluginManager
	 */
	public synchronized static PluginManager newIns() {
		if (pm == null) {
			pm = new PluginManager();
		}
		return pm;
	}

	/**
	 * 验证扩展有效性
	 * 
	 * @param ex
	 * @throws PortalServiceException
	 */
	@SuppressWarnings("unchecked")
	private void valdator(PtExtension ex) throws CpbBusinessException {
		Boolean valt = true;
		try {
			PtExtensionPoint point = getExPoint(ex.getPoint());
			/**
			 * 没有扩展点
			 */
			if(point == null)
				valt = false;
			else{
				Class itfClazz = Class.forName(point.getClassname());
				String exClazz = ex.getClassname();
				Class cfgClazz = Class.forName(exClazz);
				if (!itfClazz.isAssignableFrom(cfgClazz)) {
					LfwLogger.error("Plugin " + ex.getTitle() + " validate fail:Class not a instanse of inteface");
					valt = false;
				}
			}
		} catch (ClassNotFoundException e) {
			LfwLogger.error("Plugin " + ex.getTitle() + " validate fail:Class not found");
			valt = false;
		}
		if (!valt) {
			throw new IllegalClassException("Plugin " + ex.getTitle() + " validate fail");
		}
	}

}
