package nc.uap.wfm.message;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.wfm.model.MessageStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * 任务消息发送
 * 
 * @author zhangxya
 * 
 */
public class TaskMessageSenderMgr {
	// 消息接收人
	public static String ReceiverUser = "ReceiverUser";
	// 流程名称
	public static String ProDefName = "ProDefName";
	// 活动名称
	public static String HumActName = "HumActName";
	// 单据标题
	public static String FrmTitle = "FrmTitle";
	// 任务开始时间
	public static String TaskStartDate = "TaskStartDate";
	// 发送人
	public static String SenderUser = "SenderUser";
	// 发送方式
	public static String SenderStrategy = "MessageStrategy";
	public static String FrontControlType = "FrontControlType";
	// HTML格式的邮件内容
	public static String HtmlContent = "FrmContent";
	// 附件名称
	public static String AttachFile = "AttachFile";
	// 任务类型
	public static String FlowType = "FlowType";
	/**
	 * 接受者邮件
	 */
	public static final String ReceiveEmails = "ReceiveEmails";
	/**
	 * 当前任务
	 */
	public static final String CurrentTask = "CurrentTask";
	/**
	 * 新创建任务信息发送
	 * 
	 * @param messageMap
	 */
	public static void sendTaskCreatedMessage(Map<String, Object> messageMap) {
		if (messageMap == null) {
			return;
		}
		MessageStrategy msg = (MessageStrategy) messageMap.get(TaskMessageSenderMgr.SenderStrategy);
		String senderType = msg.getCreatedMsg();
		if (senderType == null || senderType.length() == 0) {
			return;
		}
		String[] senderTypes = senderType.split(",");
		List<String> senderTypeList = Arrays.asList(senderTypes);
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskCreatedMessageSender.TaskCreated);
		if (exs == null || exs.size() == 0) {
			return;
		}
		for (PtExtension ex : exs) {
			String exId = ex.getId();
			if (!senderTypeList.contains(exId)) {
				continue;
			}
			String[] msgTypes = (String[]) messageMap.get(TaskMessageSenderMgr.FrontControlType);
			if (msgTypes == null || msgTypes.length == 0) {
				continue;
			}
			List<String> msgType = Arrays.asList(msgTypes);
			if (msgType != null && !msgType.contains(exId)) {
				continue;
			}
			TaskCreatedMessageSender taskCreatedService = null;
			try {
				taskCreatedService = (TaskCreatedMessageSender) ex.newInstance();
				taskCreatedService.sendTaskCteatedMessage(messageMap);
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 任务完成发送消息
	 * 
	 * @param messageMap
	 */
	public static void sendTaskCompletedMessage(Map<String, Object> messageMap) {
		if (messageMap == null) {
			return;
		}
		MessageStrategy msg = (MessageStrategy) messageMap.get(TaskMessageSenderMgr.SenderStrategy);
		String senderType = msg.getCompletedMsg();
		if (StringUtils.isEmpty(senderType)) {
			return;
		}
		String[] senderTypes = senderType.split(",");
		List<String> senderTypeList = Arrays.asList(senderTypes);
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskCompletedMessageSender.TaskCompleted);
		if (CollectionUtils.isNotEmpty(exs)) {
			for (PtExtension ex : exs) {
				String exId = ex.getId();
				if (senderTypeList.contains(exId)) {
					TaskCompletedMessageSender taskCompletedService = null;
					try {
						taskCompletedService = (TaskCompletedMessageSender) ex.newInstance();
					} catch (Exception e) {
						LfwLogger.error(e.getMessage(), e);
					}
					if (taskCompletedService != null) {
						taskCompletedService.sendTaskCompletedMessage(messageMap);
					}
				}
			}
		}
	}
	/**
	 * 任务超时消息
	 * 
	 * @param messageMap
	 */
	public static void sendOverTiemMessage(Map<String, Object> messageMap) {
		if (messageMap == null) {
			return;
		}
		MessageStrategy msg = (MessageStrategy) messageMap.get(TaskMessageSenderMgr.SenderStrategy);
		String senderType = msg.getOvertimeMsg();
		if (StringUtils.isEmpty(senderType)) {
			return;
		}
		String[] senderTypes = senderType.split(",");
		List<String> senderTypeList = Arrays.asList(senderTypes);
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskOverTimeMessageSender.TaskOvertime);
		if (CollectionUtils.isNotEmpty(exs)) {
			for (PtExtension ex : exs) {
				String exId = ex.getId();
				if (senderTypeList.contains(exId)) {
					TaskOverTimeMessageSender taskOverTimeService = null;
					try {
						taskOverTimeService = (TaskOverTimeMessageSender) ex.newInstance();
					} catch (Exception e) {
						LfwLogger.error(e.getMessage(), e);
					}
					taskOverTimeService.sendTaskOverTimeMessage(messageMap);
				}
			}
		}
	}
	/**
	 * 任务催办消息
	 * 
	 * @param messageMap
	 */
	public static void sendUergeMessage(Map<String, Object> messageMap) {
		if (messageMap == null) {
			return;
		}
		String senderType = (String) messageMap.get(TaskUregeMessageSender.MsgType);
		if (StringUtils.isEmpty(senderType)) {
			return;
		}
		String[] senderTypes = senderType.split(",");
		List<String> senderTypeList = Arrays.asList(senderTypes);
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskUregeMessageSender.TaskUrege);
		if (CollectionUtils.isNotEmpty(exs)) {
			for (PtExtension ex : exs) {
				String exId = ex.getId();
				if (senderTypeList.contains(exId)) {
					TaskUregeMessageSender taskOverTimeService = null;
					try {
						taskOverTimeService = (TaskUregeMessageSender) ex.newInstance();
					} catch (Exception e) {
						LfwLogger.error(e.getMessage(), e);
					}
					taskOverTimeService.sendTaskUregeMessage(messageMap);
				}
			}
		}
	}
}
