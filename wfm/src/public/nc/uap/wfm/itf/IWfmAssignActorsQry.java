package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmAssignActorsVO;
public interface IWfmAssignActorsQry {
	WfmAssignActorsVO[] getAssignActors(String rootProInsPk, String proDefId, String portId);
}
