package nc.uap.wfm.cmd;

import nc.uap.wfm.exception.WfmServiceException;

/**
 * �������ӿ�
 * @author tianchw
 *
 */
public interface ICommandService {
	<T> T execute(ICommand<T> command) throws WfmServiceException;
}
