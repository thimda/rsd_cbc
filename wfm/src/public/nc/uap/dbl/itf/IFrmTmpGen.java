package nc.uap.dbl.itf;

import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.vo.DblFormTemplateVO;
import nc.uap.wfm.model.Task;

public interface IFrmTmpGen {
	void genDispTmp(DblFormTemplateVO frmTmpVo, String proDefPk, String proDefId, String portId,String devicePk) throws DblServiceException;

	String genViewTmp(Task task, String proDefPk, String proDefId) throws DblServiceException;

	String genPrintTmp(Task task, String proDefPk, String proDefId) throws DblServiceException;

	String genAuditTmp(Task task, String proDefPk, String proDefId) throws DblServiceException;
}
