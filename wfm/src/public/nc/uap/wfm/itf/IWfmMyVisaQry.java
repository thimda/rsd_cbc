package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmMyVisaVO;
public interface IWfmMyVisaQry {
	public WfmMyVisaVO getMyVisaByMyVisaPk(String myVisaPk);
	public WfmMyVisaVO[] getMyVisasByUserPk(String userPk);
}
