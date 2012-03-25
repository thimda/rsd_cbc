package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormTemplateVO;

public interface IFrmTmpQry {
	/**
	 * ��ȡ��ģ��
	 * @param pk
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormTemplateVO getFrmTmpByPk(String frmTmpPk) throws DblServiceException;
	/**
	 * ��ȡ���汾��
	 * @param frmDefPk
	 * @return
	 * @throws PdblBusinessException
	 */
	String getMaxVerByFrmtDefPk(String frmDefPk) throws DblServiceException;
	/**
	 * ���ݱ������ȡ���õı�ģ��
	 * @param frmDefPK
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormTemplateVO getFrmTmpByFrmDefPk(String frmDefPK) throws DblServiceException;
	
	/**
	 * ��ȡ�������õı�ģ��
	 * @return
	 * @throws PdblBusinessException
	 */
	public DblFormTemplateVO[] getFrmTmps() throws DblServiceException;	

}
