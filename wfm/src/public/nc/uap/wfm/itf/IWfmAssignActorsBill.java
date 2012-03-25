package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public interface IWfmAssignActorsBill {
	/**
	 * 保存任务的指派信息
	 * @param actors
	 * @throws WfmServiceException
	 */
	void saveAssignActors(WfmAssignActorsVO[] actors) throws WfmServiceException;
	/**
	 * 删除这个流程实例的指派信息
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	boolean deleteByRootProInsPk(String rootInsPk) throws WfmServiceException;
	/**
	 * 任务完成后，删除自己的指派信息
	 * @param rootInsPk
	 * @param proDefId
	 * @param portId
	 */
	public void deleteAssignActors(String rootInsPk, String proDefId, String portId);
}
