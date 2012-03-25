package nc.uap.dbl.exception;

import nc.uap.lfw.core.exception.LfwRuntimeException;

/**
 * 
 * 2011-8-29 ÉÏÎç10:32:01
 * @author limingf
 *
 */
public class DblServerRuntimeException extends LfwRuntimeException {
	private static final long serialVersionUID = 8774863645419572267L;

	public DblServerRuntimeException(String message, String hint,
			Throwable cause) {
		super(message, hint, cause);
	}

	public DblServerRuntimeException(String message, String hint) {
		super(message, hint);
	}

	public DblServerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DblServerRuntimeException(String message) {
		super(message);
	}

	public DblServerRuntimeException(Throwable cause) {
		super(cause);
	}

}
