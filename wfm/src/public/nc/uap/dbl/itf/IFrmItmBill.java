package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormItemVO;


public interface IFrmItmBill {
	/**
	 * 保存表单项
	 * @param list
	 * @throws PdblBusinessException
	 */
	void saveFrmItm(DblFormItemVO[] frmItms)  throws DblServiceException;
	/**
	 * 删除表单项根据模板PK
	 * @param pk_formtemplate
	 * @throws PdblBusinessException
	 */
	void delFrmItmsByFrmTmpPk(String frmTmpPk) throws DblServiceException;


}
