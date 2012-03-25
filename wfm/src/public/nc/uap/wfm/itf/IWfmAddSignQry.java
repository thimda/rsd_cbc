package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmAddSignVO;
public interface IWfmAddSignQry {
	WfmAddSignVO[] getAddSignVoByTaskPk(String taskPk) throws WfmServiceException;
	WfmAddSignVO getAddSignVoByTaskPkAndTime(String taskPk, String addSignTime) throws WfmServiceException;
	WfmAddSignVO getAddSingVoByAddSignPk(String addSignPk) throws WfmServiceException;
	String getMaxStateTimeByTaskPk(String taskPk) throws WfmServiceException;
}
