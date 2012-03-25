package nc.uap.wfm.message;
import java.net.URLEncoder;
import java.util.Map;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ITaskExtHandler;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * 新建任务手机消息发送
 * 
 * @author zhangxya
 * 
 */
public class TaskCreatedPhoneMessSender implements TaskCreatedMessageSender {
	public void sendTaskCteatedMessage(Map<String, Object> messageMap) {
		try {
			Task task = (Task) messageMap.get(TaskMessageSenderMgr.CurrentTask);
			if (task == null) {
				return;
			}
			HumAct humAct = task.getHumActIns().getHumAct();
			String serverClass = humAct.getDelegatorClass();
			IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(serverClass);
			String taskExtClazz = humActHandler.getTaskExtClazz();
			ITaskExtHandler taskExthandler = (ITaskExtHandler) WfmClassUtil.loadClass(taskExtClazz);
			String content = taskExthandler.getPhoneMsg(task);
			String phoneName = null;// lm --userVo.getMobile();
			phoneName = "";
			if (phoneName == null || phoneName.length() == 0) {
				return;
			}
			if (LfwLogger.isDebugEnabled()) {
				LfwLogger.debug("短信接收的手机号：" + phoneName);
			}
			HttpClient httpClient = new HttpClient();
			String url = "http://192.168.1.60/ISendSMS.jsp?MobilePhones=" + phoneName;
			url = url + "&MessageFlag=20090805160727250&ModuleName=vip&ExNumber=9002&Priority=1";
			url = url + "&Content=" + URLEncoder.encode(content, "gb2312");
			PostMethod postMethod = new PostMethod(url);
			httpClient.executeMethod(postMethod);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
	}
}
