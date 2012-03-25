package nc.uap.dbl.itf;

import java.util.List;
import java.util.Map;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.dbl.vo.DblFormTemplateVO;

public interface IFrmItmQry {
	/**
	 * ��������ģ���е�FormItemVO
	 * @param formTemplateVO
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getCurrentFrmItms(DblFormTemplateVO frmTmpVO) throws DblServiceException;
	/**
	 * �޳���̬�е��е�FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getNormalFrmItms(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * �޳���̬�е��е�FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<String,DblFormItemVO> getNormalFrmItmsMap(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * ��ȡ�����е�FormItemVO
	 * @param frmItms
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getAttachFileFrmItms(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * ��ȡ��̬���е�FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<String, List<DblFormItemVO>> getDynaRowMap(DblFormItemVO[] frmItms) throws DblServiceException;
	/**
	 * ��ȡ��̬���е�FormItemVO
	 * @param formItems
	 * @return
	 * @throws PdblBusinessException
	 */
	Map<DblFormItemVO, List<DblFormItemVO>> getSelfTableMap(DblFormItemVO[] formItems) throws DblServiceException;
	/**
	 * ��ȡ�־û������ݿ��е�FormItemVO
	 * @param formTempPK
	 * @return
	 * @throws PdblBusinessException
	 */
	DblFormItemVO[] getOldFrmItmsByFrmTmpPk(String frmTmpPK) throws DblServiceException;
}
