package nc.uap.dbl.itf;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormTemplateVO;
public interface IFrmTmpBill {
	/**
	 * ɾ��ģ��
	 * @param pk
	 * @throws PdblBusinessException
	 */
	void delFrmTmpByPk(String frmTmpPk) throws DblServiceException;
	/**
	 * ����ģ��
	 * @param formTemplateVO
	 * @throws PdblBusinessException
	 */
	void saveFrmTmp(DblFormTemplateVO frmTmpVO) throws DblServiceException;
	/**
	 * ����ģ��
	 * @param pk
	 * @throws PdblBusinessException
	 */
	void enableFrmTmpByPk(String frmTmpPk) throws DblServiceException;
}
