package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormCategoryVO;

/**
 * 
 * 2011-8-29 ����10:31:29
 * @author limingf
 *
 */
public interface IFrmCatQry {
	/**
	 * ��ȡ���б�����
	 * @return
	 * @throws PdblBusinessException
	 */
	 DblFormCategoryVO[] getAllFrmCat() throws DblServiceException;
	 DblFormCategoryVO[] getFrmCatsByGroupPk(String groupPk) throws DblServiceException;
}
