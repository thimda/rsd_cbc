package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
/**
 * ����ӿ�
 * @author tianchw
 *
 * @param <T>
 */
public interface ICommand<T> {
	 T execute() throws WfmServiceException;
}
