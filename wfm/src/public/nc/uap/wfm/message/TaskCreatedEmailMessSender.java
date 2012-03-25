package nc.uap.wfm.message;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cache.ILfwCache;
import nc.uap.lfw.core.cache.LfwCacheManager;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.bridge.IBillFormService;
import nc.uap.wfm.bridge.IWfmBillTypeQry;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.WfmMailMsgVO;

/**
 * 新建任务的邮件信息发送实现
 * 
 * @author zhangxya
 * 
 */
public class TaskCreatedEmailMessSender implements TaskCreatedMessageSender {

	@SuppressWarnings("unchecked")
	@Override
	public void sendTaskCteatedMessage(Map<String, Object> messageMap) {
		ILfwCache lfwcache = LfwCacheManager.getStrongCache(WfmConstants.MailTaskList, "");
		Object obj = lfwcache.get(WfmConstants.MailTaskList);
		ConcurrentLinkedQueue<WfmMailMsgVO> mailTasklist;
		if (obj == null) {
			mailTasklist = new ConcurrentLinkedQueue<WfmMailMsgVO>();
			lfwcache.put(WfmConstants.MailTaskList, mailTasklist);
		} else {
			mailTasklist = (ConcurrentLinkedQueue<WfmMailMsgVO>) obj;

		}

		WfmMailMsgVO temp = createMailMsg(messageMap);
		if (temp != null) {
			mailTasklist.add(temp);
		}
	}

	public WfmMailMsgVO createMailMsg(Map<String, Object> messageMap) {
		WfmMailMsgVO msg = new WfmMailMsgVO();
		Task task = (Task) messageMap.get(TaskMessageSenderMgr.CurrentTask);
		String mail_smtp_host = LfwRuntimeEnvironment.getModelServerConfig().getConfigValue("mail_smtp_host");
		String mail_send_addr = LfwRuntimeEnvironment.getModelServerConfig().getConfigValue("mail_send_addr");
		String mail_user_name = LfwRuntimeEnvironment.getModelServerConfig().getConfigValue("mail_user_name");
		String mail_user_pwd = LfwRuntimeEnvironment.getModelServerConfig().getConfigValue("mail_user_pwd");
		String mail_send_name = LfwRuntimeEnvironment.getModelServerConfig().getConfigValue("mail_send_name");

		msg.setSmtpHost(mail_smtp_host);
		msg.setFromAddr(mail_send_addr);
		msg.setUserName(mail_user_name);
		msg.setPassword(mail_user_pwd);
		msg.setSenderName(mail_send_name);
		String pk_executer = (String) messageMap.get(TaskMessageSenderMgr.ReceiverUser);
		CpUserVO user = null;
		try {
			user = CpbServiceFacility.getCpUserQry().getUserByPk(pk_executer);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
		if (user == null) {
			return null;
		}
		String receiverEmails = null;// lm --- user.getusere
		if ("~".equals(receiverEmails)||"".equals(receiverEmails)){
			return null;
		}
		if(receiverEmails==null||"".equalsIgnoreCase(receiverEmails)){
			return null;
		}
		msg.setReceiverEmails(receiverEmails);
		String subject = task.getExt1();
		if (subject == null) {
			subject = task.getHumActIns().getHumAct().getName();
		}
		if(LfwLogger.isDebugEnabled()){
			LfwLogger.debug("邮件服务器：" + mail_smtp_host + "邮件地址：" + mail_send_addr + "邮件地址：" + mail_send_addr + "邮件密码：" + mail_user_pwd + "邮件发送给：" + receiverEmails);
		}
		msg.setSubject(subject);
		String taskType = (String) messageMap.get(TaskMessageSenderMgr.FlowType);
		String htmlContent = null;
		try {
			taskType = (String) messageMap.get(TaskMessageSenderMgr.FlowType);
			IWfmBillTypeQry btQry = NCLocator.getInstance().lookup(IWfmBillTypeQry.class);
			IBillFormService bfService = btQry.getBillFormService(taskType);
			htmlContent = bfService.getViewContent("", task);
			StringBuffer sb = new StringBuffer();
			sb.append(htmlContent);
			msg.setSb(sb);
			return msg;
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}

	}
}
