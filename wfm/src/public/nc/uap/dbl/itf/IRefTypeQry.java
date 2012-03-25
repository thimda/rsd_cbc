package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblRefTypeVO;


public interface IRefTypeQry {
	DblRefTypeVO getRefTypeByPk(String refTypePk) throws DblServiceException;
	DblRefTypeVO[] getAllRefType() throws DblServiceException;
}
