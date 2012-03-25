package nc.uap.wfm.logger;
import nc.bs.logging.Logger;
public class WfmLogger {
	public static final String LOGGER_TYPE = "WFM";
	public static void error(String msg, Throwable t) {
		Logger.init(WfmLogger.LOGGER_TYPE);
		Logger.error(msg, t);
		Logger.reset();
	}
	public static void error(String msg) {
		WfmLogger.error(msg, null);
	}
	public static void error(Throwable e) {
		Logger.error(e.getMessage(), e);
	}
	public static void debug(String msg) {
		Logger.init(WfmLogger.LOGGER_TYPE);
		Logger.debug(msg);
		Logger.reset();
	}
	public static void info(String msg) {
		Logger.init(WfmLogger.LOGGER_TYPE);
		Logger.info(msg);
		Logger.reset();
	}
	public static boolean isDebugEnabled() {
		return Logger.isDebugEnabled();
	}
	public static boolean isInfoEnabled() {
		return Logger.isInfoEnabled();
	}
	public static boolean isWarnEnabled() {
		return Logger.isWarnEnabled();
	}
}
