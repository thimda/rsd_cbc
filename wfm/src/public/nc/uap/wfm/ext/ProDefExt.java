package nc.uap.wfm.ext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.engine.IProDefExtHandler;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.DefaultFormVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
public class ProDefExt {
	public static void handlerExtAttr(Task task, WfmFormInfoCtx cntFrmVo) {
		if (cntFrmVo instanceof DefaultFormVO) {}
	}
	public static IProDefExtHandler getExtAttrHanler(ProDef proDef) {
		String serverClass = proDef.getServerClass();
		if (serverClass == null || serverClass.length() == 0) {
			serverClass = WfmConstants.ExtAttrHandlerClass;
		}
		IProDefExtHandler handler = null;
		try {
			handler = (IProDefExtHandler) Class.forName(serverClass).newInstance();
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return handler;
	}
}
