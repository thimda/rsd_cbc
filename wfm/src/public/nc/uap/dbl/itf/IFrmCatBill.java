package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormCategoryVO;

/**
 * 
 * 2011-8-29 ����10:31:01
 * @author limingf
 *
 */
public interface IFrmCatBill {
	/**
	 * �����������Ϣ
	 * @param frmCatVO
	 * @throws PdblBusinessException
	 */
	void saveOrUpdate(DblFormCategoryVO frmCatVO) throws DblServiceException;
	/**
	 * ɾ��������
	 * @param frmCatByPk
	 * @throws PdblBusinessException
	 */
	void delFrmCatByPk(String frmCatByPk) throws DblServiceException;
	
	
	void delFrmCatByGroupPk(String[] groupPks) throws DblServiceException;
}
