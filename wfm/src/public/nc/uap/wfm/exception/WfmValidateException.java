package nc.uap.wfm.exception;

import nc.uap.wfm.exception.WfmServiceException;

public class WfmValidateException extends WfmServiceException {
	private static final long serialVersionUID = 4327268254447664952L;

	public WfmValidateException() {
		super();
	}

	public WfmValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public WfmValidateException(String message) {
		super(message);
	}

	public WfmValidateException(Throwable cause) {
		super(cause);
	}

}
