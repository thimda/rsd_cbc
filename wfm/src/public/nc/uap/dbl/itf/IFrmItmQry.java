package nc.uap.dbl.itf;

import java.util.List;
import java.util.Map;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;

public interface IFrmItmQry {
	/**
	 * 解析出表单模板中的FormItemVO
	 * @param formTemplateVO
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getCurrentFrmItms(DblFormTemplateVO frmTmpVO) throws DblServiceException;
	/**
	 * 剔除动态行的中的FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getNormalFrmItms(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * 剔除动态行的中的FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<String,DblFormItemVO> getNormalFrmItmsMap(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * 获取附件中的FormItemVO
	 * @param frmItms
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getAttachFileFrmItms(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * 获取动态行中的FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<String, List<DblFormItemVO>> getDynaRowMap(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * 获取动态行中的FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<DblFormItemVO, List<DblFormItemVO>> getSelfTableMap(DblFormItemVO[] formItems) throws DblServiceException;
	/**
	 * 获取持久化到数据库中的FormItemVO
	 * @param formTempPK
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getOldFrmItmsByFrmTmpPk(String frmTmpPK) throws DblServiceException;
}
