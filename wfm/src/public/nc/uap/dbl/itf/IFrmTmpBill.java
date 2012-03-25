package nc.uap.dbl.itf;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormTemplateVO;
public interface IFrmTmpBill {
	/**
	 * 删除模板
	 * @param pk
	 * @throws PdblBusinessException
	 */
	void delFrmTmpByPk(String frmTmpPk) throws DblServiceException;
	/**
	 * 保存模板
	 * @param formTemplateVO
	 * @throws PdblBusinessException
	 */
	void saveFrmTmp(DblFormTemplateVO frmTmpVO) throws DblServiceException;
	/**
	 * 启用模板
	 * @param pk
	 * @throws PdblBusinessException
	 */
	void enableFrmTmpByPk(String frmTmpPk) throws DblServiceException;
}
