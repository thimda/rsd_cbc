package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmBeforeAddSignVO;
public interface IWfmAddSignQry {
	WfmBeforeAddSignVO[] getAddSignVoByTaskPk(String taskPk) throws WfmServiceException;
	WfmBeforeAddSignVO getAddSignVoByTaskPkAndTime(String taskPk, String addSignTime) throws WfmServiceException;
	WfmBeforeAddSignVO getAddSingVoByAddSignPk(String addSignPk) throws WfmServiceException;
	String getMaxStateTimeByTaskPk(String taskPk) throws WfmServiceException;
}
