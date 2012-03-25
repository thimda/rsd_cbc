package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormDefinitionVO;

public interface IFrmDefQry {
	/**
	 * ����PK��ȡ������
	 * @param pk_fromDefintion
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO getFrmDefByPk(String frmDefPk) throws DblServiceException;
	
	public DblFormDefinitionVO[] getFrmDefByPks(String[] frmDefPks) throws DblServiceException;
	
	/**
	 * ��ȡһ���������������б�������Ϣ
	 * @param pk_formCate
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getFrmDefsByFrmCaPk(String frmCatPk) throws DblServiceException;
	/**
	 * ��ȡ���б�������б�
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getAllFrmDef() throws DblServiceException;
	/**
	 * ��ȡ��������ݽ�ɫPK
	 * @param rolePks
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getFrmDefsByRolePks(String[] rolePks) throws DblServiceException;
	DblFormDefinitionVO[] getFrmDefsByUserPk(String userPk) throws DblServiceException;
	DblFormDefinitionVO[] getMyPrtptFrmDefsUserPk(String userPk) throws DblServiceException;
}
