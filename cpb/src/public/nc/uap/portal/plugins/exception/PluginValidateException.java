package nc.uap.portal.plugins.exception;

import nc.uap.lfw.core.exception.LfwBusinessException;

public class PluginValidateException extends LfwBusinessException {
	private static final long serialVersionUID = 3628887858995628087L;

	public PluginValidateException(String s) {
		super(s);
	}

	public PluginValidateException(Throwable s) {
		super(s);
	}

	public PluginValidateException(String s, Throwable c) {
		super(s, c);
	}
}
