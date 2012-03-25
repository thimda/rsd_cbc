package nc.uap.wfm.itf;import nc.uap.wfm.exception.WfmServiceException;import nc.uap.wfm.exception.WfmValidateException;import nc.uap.wfm.vo.WfmFormInfoCtx;;
public interface IWfmFrmInsBill {
	public void save(WfmFormInfoCtx formVO);
	public void update(WfmFormInfoCtx formVO);
	public void validate(WfmFormInfoCtx formVo,String devicePk) throws WfmValidateException;
	public void updataFormData(String sql) throws WfmServiceException;
}
