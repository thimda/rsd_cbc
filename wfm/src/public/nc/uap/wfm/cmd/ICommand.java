package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
/**
 * ÃüÁî½Ó¿Ú
 * @author tianchw
 *
 * @param <T>
 */
public interface ICommand<T> {
	 T execute() throws WfmServiceException;
}
