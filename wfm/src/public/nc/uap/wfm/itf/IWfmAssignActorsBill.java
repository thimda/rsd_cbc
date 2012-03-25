package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public interface IWfmAssignActorsBill {
	/**
	 * ���������ָ����Ϣ
	 * @param actors
	 * @throws WfmServiceException
	 */
	void saveAssignActors(WfmAssignActorsVO[] actors) throws WfmServiceException;
	/**
	 * ɾ���������ʵ����ָ����Ϣ
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean deleteByRootProInsPk(String rootInsPk) throws WfmServiceException;
	/**
	 * ������ɺ�ɾ���Լ���ָ����Ϣ
	 * @param rootInsPk
	 * @param proDefId
	 * @param portId
	 */
	public void deleteAssignActors(String rootInsPk, String proDefId, String portId);
}
