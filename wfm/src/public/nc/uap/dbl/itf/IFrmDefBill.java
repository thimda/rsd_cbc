package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormDefinitionVO;


public interface IFrmDefBill {
	/**
	 * 保存表单定义
	 * @param frmDefVO
	 * @throws PdblBusinessException
	 */
	 void saveFrmDef(DblFormDefinitionVO frmDefVO) throws DblServiceException;
	/**
	 * 保存或者更新保单定义
	 * @param frmDefVO
	 * @throws PdblBusinessException
	 */
	 void saveOrUpdateFrmDef(DblFormDefinitionVO frmDefVO) throws DblServiceException;
	/**
	 * 删除表单定义
	 * @param pk
	 * @throws PdblBusinessException
	 */
	 void delFrmDefByPk(String frmDefPk) throws DblServiceException;
}
