package nc.uap.wfm.cmd;

import nc.uap.wfm.exception.WfmServiceException;

/**
 * 命令服务接口
 * @author tianchw
 *
 */
public interface ICommandService {
	<T> T execute(ICommand<T> command) throws WfmServiceException;
}
