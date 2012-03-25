package nc.uap.wfm.itf;
import java.util.List;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.MyWork;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 2010-12-17 下午04:20:41 limingf
 */
public interface IWfmProDefQry {
	/**
	 * 获取流程定义根据流程类型
	 * 
	 * @param flwTypePk
	 * @return
	 * @throws WfmServiceException
	 */
	public WfmProdefVO[] getProDefByFlwTypePk(String flwTypePk) throws WfmServiceException;
	/**
	 * 获取流程定义根据流程名称
	 * 
	 * @param flwTypePk
	 * @return
	 * @throws WfmServiceException
	 */
	public WfmProdefVO[] getProdefVOByName(String name) throws WfmServiceException;
	/**
	 * 获取指定的流程定义VO
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO getProDefVOByProDefPk(String proDefPk) throws WfmServiceException;
	/**
	 * 获取指定的流程定义VO
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO getProDefVOByFrmDefPk(String frmDefPk) throws WfmServiceException;
	/**
	 * 获取所有的流程定义VOS
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getProDefVOByProDefPks(String[] proDefPks) throws WfmServiceException;
	/**
	 * 获取所有流程定义
	 */
	WfmProdefVO[] getAllProDef() throws WfmServiceException;
	/**
	 * 获取我参与的流程
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyPrtptProDef(String userPk) throws WfmServiceException;
	/**
	 * 我发起的流程
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyStartptProDef(String userPk) throws WfmServiceException;
	/**
	 * 我监控的流程
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyWatchptProDef(String userPk) throws WfmServiceException;
	/**
	 * 获取我的所有待办类型列表及数量
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	List<MyWork> getMyUndoWork(String userPk) throws WfmServiceException;
	/**
	 * 获取我的所有待阅类型列表及数量
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	List<MyWork> getMyUnreadWork(String userPk) throws WfmServiceException;
	/**
	 * 我的代办工作数量
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	int getMyUndoWorkCount(String userPk) throws WfmServiceException;
	/**
	 * 我的代阅工作数量
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	int getMyUnreadWorkCount(String userPk) throws WfmServiceException;
}
