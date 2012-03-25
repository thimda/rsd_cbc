package nc.uap.cpb.org.exception;

import nc.uap.lfw.core.exception.LfwBusinessException;

/**
 * cpbµƒ“Ï≥£¿‡
 * 
 * @author tianchw
 * 
 */
public class CpbBusinessException extends LfwBusinessException {
	private static final long serialVersionUID = -8310978166848209429L;

	public CpbBusinessException() {
		super();
	}

	public CpbBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public CpbBusinessException(String message) {
		super(message);
	}

	public CpbBusinessException(Throwable cause) {
		super(cause);
	}

}
