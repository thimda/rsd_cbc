package nc.uap.wfm.render;

import nc.uap.wfm.model.Task;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.vo.WfmTaskVO;

public class TaskTipUtil {
	public static String getTipStrByTaskVo(WfmTaskVO taskVo) {
		StringBuffer sb = new StringBuffer();
		if (Task.CreateType_Deliver.equalsIgnoreCase(taskVo.getCreatetype())) {
			sb.append("发送时间:" + taskVo.getStartdate() + "\n");
			if (taskVo.getIsnotexe().booleanValue()) {
				sb.append("签收时间:" + taskVo.getSigndate() + "\n");
				sb.append("阅示人：" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
			} else {
				sb.append("当前阅示人：" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_owner())).append("\n");
			}
		} else {
			if (taskVo.getIsnotexe().booleanValue()) {
				if (taskVo.getSigndate() != null) {
					sb.append("签收时间:" + taskVo.getSigndate() + "\n");
				}
				if (Task.FinishType_Reject.equalsIgnoreCase(taskVo.getFinishtype())) {
					sb.append("退回时间：" + taskVo.getEnddate() + "\n");
					sb.append("退回人：" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
				} else {
					sb.append("提交时间：" + taskVo.getEnddate() + "\n");
					sb.append("提交人：" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
				}
			} else {
				if (taskVo.getSigndate() == null) {
					sb.append("开始时间:" + taskVo.getStartdate() + "\n");
				} else {
					sb.append("签收时间:" + taskVo.getSigndate() + "\n");
				}
				sb.append("当前执行人：" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_owner())).append("\n");
			}
		}
		return sb.toString();
	}
}
