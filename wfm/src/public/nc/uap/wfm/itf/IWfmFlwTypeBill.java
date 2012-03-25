package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public interface IWfmFlwTypeBill {
	void saveOrUpdateFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException;
	void saveFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException;
	void updateFlwType(WfmFlwTypeVO flwTypeVo) throws WfmServiceException;
	void deleteFlwTypeByPk(String flwTypePk) throws WfmServiceException;
}
