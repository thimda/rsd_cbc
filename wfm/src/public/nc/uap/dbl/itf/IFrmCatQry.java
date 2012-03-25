package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormCategoryVO;

/**
 * 
 * 2011-8-29 上午10:31:29
 * @author limingf
 *
 */
public interface IFrmCatQry {
	/**
	 * 获取所有表单分类
	 * @return
	 * @throws PdblBusinessException
	 */
	 DblFormCategoryVO[] getAllFrmCat() throws DblServiceException;
	 DblFormCategoryVO[] getFrmCatsByGroupPk(String groupPk) throws DblServiceException;
}
