package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormDefinitionVO;

public interface IFrmDefQry {
	/**
	 * 根据PK获取表单定义
	 * @param pk_fromDefintion
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO getFrmDefByPk(String frmDefPk) throws DblServiceException;
	
	public DblFormDefinitionVO[] getFrmDefByPks(String[] frmDefPks) throws DblServiceException;
	
	/**
	 * 获取一个表单类别下面的所有表单定义信息
	 * @param pk_formCate
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getFrmDefsByFrmCaPk(String frmCatPk) throws DblServiceException;
	/**
	 * 获取所有表单定义的列表
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getAllFrmDef() throws DblServiceException;
	/**
	 * 获取表单定义根据角色PK
	 * @param rolePks
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormDefinitionVO[] getFrmDefsByRolePks(String[] rolePks) throws DblServiceException;
	DblFormDefinitionVO[] getFrmDefsByUserPk(String userPk) throws DblServiceException;
	DblFormDefinitionVO[] getMyPrtptFrmDefsUserPk(String userPk) throws DblServiceException;
}
