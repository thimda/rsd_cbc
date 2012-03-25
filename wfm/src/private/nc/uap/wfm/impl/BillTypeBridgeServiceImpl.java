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
 * 单据类型桥接器实现
 * 
 * @author dengjt
 * 
 */
public class BillTypeBridgeServiceImpl implements IBillTypeBridgeService {
	/**
	 * 获取对应设备的展现内容
	 */
	public String getViewContent(String billType, String deviceType, Map<String, Object> messageMap) throws DblServiceException {
		// 获取目标用户信息
		String pk_executor = (String) messageMap.get(TaskMessageSenderMgr.ReceiverUser);
		CpUserVO user = null;
		try {
			user = CpbServiceFacility.getCpUserQry().getUserByPk(pk_executor);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage());
		}
		if (user == null)
			throw new DblServiceException("没有找到目标用户:" + pk_executor);
		// 获取邮箱信息
		String receiverEmails = null;
		if (receiverEmails == null)
			throw new DblServiceException("目标用户没有配置邮件信息:" + receiverEmails);
		// 当前任务
		Task task = (Task) messageMap.get(TaskMessageSenderMgr.CurrentTask);
		// 任务类型
		String taskType = (String) messageMap.get(TaskMessageSenderMgr.FlowType);
		IWfmBillTypeQry btQry = NCLocator.getInstance().lookup(IWfmBillTypeQry.class);
		IBillFormService bfService = btQry.getBillFormService(taskType);
		String htmlContent = bfService.getViewContent(deviceType, task);
		return htmlContent;
	}
}
