package nc.uap.ctrl.tpl.exp;

import nc.vo.pub.BusinessException;

public class TplBusinessException extends BusinessException {
	private static final long serialVersionUID = -2922527654507234488L;

	public TplBusinessException() {
		super();
	}

	public TplBusinessException(String s, String errorCode) {
		super(s, errorCode);
	}

	public TplBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public TplBusinessException(String s) {
		super(s);
	}

	public TplBusinessException(Throwable cause) {
		super(cause);
	}

}
