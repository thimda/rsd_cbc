package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
/**
 * 命令服务实现类
 * @author tianchw
 *
 */
public class CommandService implements ICommandService {
	public <T> T execute(ICommand<T> command) throws WfmServiceException {
		return command.execute();
	}
}
