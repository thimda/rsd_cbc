package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormDefinitionVO;


public interface IFrmDefBill {
	/**
	 * ���������
	 * @param frmDefVO
	 * @throws PdblBusinessException
	 */
	 void saveFrmDef(DblFormDefinitionVO frmDefVO) throws DblServiceException;
	/**
	 * ������߸��±�������
	 * @param frmDefVO
	 * @throws PdblBusinessException
	 */
	 void saveOrUpdateFrmDef(DblFormDefinitionVO frmDefVO) throws DblServiceException;
	/**
	 * ɾ��������
	 * @param pk
	 * @throws PdblBusinessException
	 */
	 void delFrmDefByPk(String frmDefPk) throws DblServiceException;
}
