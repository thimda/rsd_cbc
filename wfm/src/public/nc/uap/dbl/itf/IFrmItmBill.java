package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormItemVO;


public interface IFrmItmBill {
	/**
	 * �������
	 * @param list
	 * @throws PdblBusinessException
	 */
	void saveFrmItm(DblFormItemVO[] frmItms)  throws DblServiceException;
	/**
	 * ɾ���������ģ��PK
	 * @param pk_formtemplate
	 * @throws PdblBusinessException
	 */
	void delFrmItmsByFrmTmpPk(String frmTmpPk) throws DblServiceException;


}
