package nc.uap.wfm.itf;
public interface IWfmFrmInsBill {
	public void save(WfmFormInfoCtx formVO);
	public void update(WfmFormInfoCtx formVO);
	public void validate(WfmFormInfoCtx formVo,String devicePk) throws WfmValidateException;
	public void updataFormData(String sql) throws WfmServiceException;
}