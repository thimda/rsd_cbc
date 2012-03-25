package nc.uap.wfm.plugin;
import java.util.List;

import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
/**
 * 组织管理扩展点公共实现类
 * @author zhangxya
 *
 */
public class PdblExtentionUtil {
	public static void notifybeforeAction(String functionType, String actionType, Object object) {
		//得到组织管理扩展点
		List<PtExtension> exs = PluginManager.newIns().getExtensions(IPdblExtentionService.POINTID);
		if (exs == null || exs.size() == 0) {
			return;
		}
		IPdblExtentionService pdblExtService = null;
		String exId = null;
		try {
			for (PtExtension ex : exs) {
				exId = ex.getId();
				if (!exId.contains(functionType)) {
					continue;
				}
				pdblExtService = (IPdblExtentionService) ex.newInstance();
				String acctype = pdblExtService.acceptFunType();
				if (functionType != null && acctype != null && functionType.equals(acctype))
					pdblExtService.beforeAction(functionType, actionType, object);
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
	public static void notifyAfterAction(String functionType, String actionType, Object object) {
		List<PtExtension> exs = PluginManager.newIns().getExtensions(IPdblExtentionService.POINTID);
		if (exs == null || exs.size() == 0) {
			return;
		}
		IPdblExtentionService pdblExtService = null;
		String exId = null;
		try {
			for (PtExtension ex : exs) {
				exId = ex.getId();
				if (!exId.contains(functionType)) {
					continue;
				}
				pdblExtService = (IPdblExtentionService) ex.newInstance();
				String acctype = pdblExtService.acceptFunType();
				if (functionType != null && acctype != null && functionType.equals(acctype))
					pdblExtService.afterAction(functionType, actionType, object);
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
}
