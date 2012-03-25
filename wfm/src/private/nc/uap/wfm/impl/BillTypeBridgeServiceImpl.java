package nc.uap.wfm.impl;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.bridge.IBillFormService;
import nc.uap.wfm.bridge.IBillTypeBridgeService;
import nc.uap.wfm.bridge.IWfmBillTypeQry;
import nc.uap.wfm.message.TaskMessageSenderMgr;
import nc.uap.wfm.model.Task;
/**
 * ���������Ž���ʵ��
 * 
 * @author dengjt
 * 
 */
public class BillTypeBridgeServiceImpl implements IBillTypeBridgeService {
	/**
	 * ��ȡ��Ӧ�豸��չ������
	 */
	public String getViewContent(String billType, String deviceType, Map<String, Object> messageMap) throws DblServiceException {
		// ��ȡĿ���û���Ϣ
		String pk_executor = (String) messageMap.get(TaskMessageSenderMgr.ReceiverUser);
		CpUserVO user = null;
		try {
			user = CpbServiceFacility.getCpUserQry().getUserByPk(pk_executor);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		if (user == null)
			throw new DblServiceException("û���ҵ�Ŀ���û�:" + pk_executor);
		// ��ȡ������Ϣ
		String receiverEmails = null;
		if (receiverEmails == null)
			throw new DblServiceException("Ŀ���û�û�������ʼ���Ϣ:" + receiverEmails);
		// ��ǰ����
		Task task = (Task) messageMap.get(TaskMessageSenderMgr.CurrentTask);
		// ��������
		String taskType = (String) messageMap.get(TaskMessageSenderMgr.FlowType);
		IWfmBillTypeQry btQry = NCLocator.getInstance().lookup(IWfmBillTypeQry.class);
		IBillFormService bfService = btQry.getBillFormService(taskType);
		String htmlContent = bfService.getViewContent(deviceType, task);
		return htmlContent;
	}
}
