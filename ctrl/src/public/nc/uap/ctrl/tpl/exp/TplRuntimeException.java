package nc.uap.ctrl.tpl.exp;

import nc.vo.pub.BusinessRuntimeException;

public class TplRuntimeException extends BusinessRuntimeException {
	private static final long serialVersionUID = 9130570143582423916L;

	public TplRuntimeException() {
		super();
	}

	public TplRuntimeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public TplRuntimeException(String msg) {
		super(msg);
	}

}
