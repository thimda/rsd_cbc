package nc.uap.dbl.exception;

import nc.vo.pub.BusinessException;

/**
 * 
 * 2011-8-29 ионГ10:32:06
 * @author limingf
 *
 */
public class DblServiceException extends BusinessException {

	private static final long serialVersionUID = -4851851740319982044L;

	public DblServiceException() {
		super();
	}

	public DblServiceException(String message) {
		//Thread.currentThread().getContextClassLoader().getResourceAsStream(name)
		super(message);
	}

	public DblServiceException(Throwable cause) {
		super(cause);
	}

	public DblServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
