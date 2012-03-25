package nc.uap.wfm.exception;



import nc.uap.lfw.core.exception.LfwBusinessException;
/**
 * 流程引擎的异常类
 * @author tianchw
 *
 */
public class WfmServiceException extends LfwBusinessException {
	private static final long serialVersionUID = -8310978166848209429L;

	public WfmServiceException() {
		super();
	}

	public WfmServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WfmServiceException(String message) {
		super(message);
	}

	public WfmServiceException(Throwable cause) {
		super(cause);
	}

}
