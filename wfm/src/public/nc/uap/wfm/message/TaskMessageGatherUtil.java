package nc.uap.wfm.message;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.MessageStrategy;
import nc.uap.wfm.model.Task;
public class TaskMessageGatherUtil {
	/**
	 * �ռ�������Ϣ
	 * 
	 * @param task
	 * @return
	 */
	public static Map<String, Object> getMessageMap(Task task, String userPk) {
		if (task == null) {
			return null;
		}
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put(TaskMessageSenderMgr.CurrentTask, task);
		HumActIns humActIns = task.getHumActIns();
		HumAct humAct = humActIns.getHumAct();
		MessageStrategy message = humAct.getMessageStrategy();
		if (message == null) {
			return null;
		}
		messageMap.put(TaskMessageSenderMgr.SenderStrategy, message);
		messageMap.put(TaskMessageSenderMgr.ReceiverUser, userPk);
		messageMap.put(TaskMessageSenderMgr.FlowType, task.getFlowType());
		return messageMap;
	}
	/**
	 * �õ��½�������Ϣ��������
	 * 
	 * @return
	 */
	public static PtExtension[] getTaskCreatedSenderType() {
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskCreatedMessageSender.TaskCreated);
		return (PtExtension[]) exs.toArray(new PtExtension[0]);
	}
	/**
	 * " �õ���ʱ�������Ϣ��������
	 * 
	 * @return
	 */
	public static PtExtension[] getTaskOverTimeSenderType() {
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskOverTimeMessageSender.TaskOvertime);
		return (PtExtension[]) exs.toArray(new PtExtension[0]);
	}
	/**
	 * �õ�����������Ϣ��������
	 * 
	 * @return
	 */
	public static PtExtension[] getTaskCompletedSenderType() {
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskCompletedMessageSender.TaskCompleted);
		return (PtExtension[]) exs.toArray(new PtExtension[0]);
	}
	/**
	 * �õ��߰����Ϣ��������
	 * 
	 * @return
	 */
	public static PtExtension[] getTaskUregeSenderType() {
		List<PtExtension> exs = PluginManager.newIns().getExtensions(TaskUregeMessageSender.TaskUrege);
		return (PtExtension[]) exs.toArray(new PtExtension[0]);
	}
}
