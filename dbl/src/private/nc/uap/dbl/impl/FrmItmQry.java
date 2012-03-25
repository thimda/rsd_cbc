package nc.uap.dbl.impl;
import java.util.List;
import java.util.Map;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmItmQry;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;
public class FrmItmQry implements IFrmItmQry {

	@Override
	public DblFormItemVO[] getAttachFileFrmItms(DblFormItemVO[] frmItms)
			throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DblFormItemVO[] getCurrentFrmItms(DblFormTemplateVO frmTmpVO)
			throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<DblFormItemVO>> getDynaRowMap(
			DblFormItemVO[] frmItms) throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DblFormItemVO[] getNormalFrmItms(DblFormItemVO[] frmItms)
			throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, DblFormItemVO> getNormalFrmItmsMap(
			DblFormItemVO[] frmItms) throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DblFormItemVO[] getOldFrmItmsByFrmTmpPk(String frmTmpPK)
			throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<DblFormItemVO, List<DblFormItemVO>> getSelfTableMap(
			DblFormItemVO[] formItems) throws DblServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
