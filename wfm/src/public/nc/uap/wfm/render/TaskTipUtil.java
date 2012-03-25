package nc.uap.wfm.render;

import nc.uap.wfm.model.Task;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.vo.WfmTaskVO;

public class TaskTipUtil {
	public static String getTipStrByTaskVo(WfmTaskVO taskVo) {
		StringBuffer sb = new StringBuffer();
		if (Task.CreateType_Deliver.equalsIgnoreCase(taskVo.getCreatetype())) {
			sb.append("����ʱ��:" + taskVo.getStartdate() + "\n");
			if (taskVo.getIsnotexe().booleanValue()) {
				sb.append("ǩ��ʱ��:" + taskVo.getSigndate() + "\n");
				sb.append("��ʾ�ˣ�" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
			} else {
				sb.append("��ǰ��ʾ�ˣ�" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_owner())).append("\n");
			}
		} else {
			if (taskVo.getIsnotexe().booleanValue()) {
				if (taskVo.getSigndate() != null) {
					sb.append("ǩ��ʱ��:" + taskVo.getSigndate() + "\n");
				}
				if (Task.FinishType_Reject.equalsIgnoreCase(taskVo.getFinishtype())) {
					sb.append("�˻�ʱ�䣺" + taskVo.getEnddate() + "\n");
					sb.append("�˻��ˣ�" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
				} else {
					sb.append("�ύʱ�䣺" + taskVo.getEnddate() + "\n");
					sb.append("�ύ�ˣ�" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_executer())).append("\n");
				}
			} else {
				if (taskVo.getSigndate() == null) {
					sb.append("��ʼʱ��:" + taskVo.getStartdate() + "\n");
				} else {
					sb.append("ǩ��ʱ��:" + taskVo.getSigndate() + "\n");
				}
				sb.append("��ǰִ���ˣ�" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_owner())).append("\n");
			}
		}
		return sb.toString();
	}
}
