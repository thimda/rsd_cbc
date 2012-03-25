package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormCategoryVO;

/**
 * 
 * 2011-8-29 上午10:31:01
 * @author limingf
 *
 */
public interface IFrmCatBill {
	/**
	 * 保存表单分类信息
	 * @param frmCatVO
	 * @throws PdblBusinessException
	 */
	void saveOrUpdate(DblFormCategoryVO frmCatVO) throws DblServiceException;
	/**
	 * 删除表单分类
	 * @param frmCatByPk
	 * @throws PdblBusinessException
	 */
	void delFrmCatByPk(String frmCatByPk) throws DblServiceException;
	
	
	void delFrmCatByGroupPk(String[] groupPks) throws DblServiceException;
}
