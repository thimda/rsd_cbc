package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormTemplateVO;

public interface IFrmTmpQry {
	/**
	 * 获取表单模板
	 * @param pk
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormTemplateVO getFrmTmpByPk(String frmTmpPk) throws DblServiceException;
	/**
	 * 获取最大版本号
	 * @param frmDefPk
	 * @return
	 * @throws PdblBusinessException
	 */
	String getMaxVerByFrmtDefPk(String frmDefPk) throws DblServiceException;
	/**
	 * 根据表单定义获取启用的表单模板
	 * @param frmDefPK
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormTemplateVO getFrmTmpByFrmDefPk(String frmDefPK) throws DblServiceException;
	
	/**
	 * 获取所有启用的表单模板
	 * @return
	 * @throws PdblBusinessException
	 */
	public DblFormTemplateVO[] getFrmTmps() throws DblServiceException;	

}
