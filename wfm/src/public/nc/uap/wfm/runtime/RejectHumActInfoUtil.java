package nc.uap.wfm.runtime;
import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.rejectsgy.RejectSgyManager;
import nc.uap.wfm.utils.WfmTaskUtil;
public class RejectHumActInfoUtil {
	/**
	 * 获取回退活动信息
	 * @param pktask
	 * @param formVo
	 * @return
	 */
	public List<HumActInfoPageCtx> getRejectHumActInfo(Task task) {
		HumAct[] humActs = RejectSgyManager.getInstance().getRejectRange(task);
		if (humActs == null || humActs.length == 0) {
			return null;
		}
		HumAct humAct = null;
		CpUserVO[] userVos = null;
		List<HumActInfoPageCtx> list = new ArrayList<HumActInfoPageCtx>();
		HumActInfoPageCtx ctx = null;
		for (int i = 0; i < humActs.length; i++) {
			humAct = humActs[i];
			userVos = RejectSgyManager.getInstance().getRejectUsersByTaskAndHumAct(task, humAct);
			ctx = new HumActInfoPageCtx();
			if (userVos == null || userVos.length == 0) {
				continue;
			}
			if (userVos.length == 1) {
				ctx.setAssign(false);
			} else {
				ctx.setAssign(true);
			}
			ctx.setPortId(humAct.getId());
			ctx.setPortName(humAct.getName());
			ctx.setUserPks(this.getUserPks(userVos));
			ctx.setUserNames(this.getUserNames(userVos));
			list.add(ctx);
		}
		return list;
	}
	public List<HumActInfoPageCtx> getRejectHumActInfo(String taskPk) {
		Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
		return this.getRejectHumActInfo(task);
	}
	private String getUserPks(CpUserVO[] userVos) {
		if (userVos == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		CpUserVO userVo = null;
		for (int i = 0; i < userVos.length; i++) {
			userVo = userVos[i];
			buffer.append(userVo.getCuserid()).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}
	private String getUserNames(CpUserVO[] userVos) {
		if (userVos == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		CpUserVO userVo = null;
		for (int i = 0; i < userVos.length; i++) {
			userVo = userVos[i];
			buffer.append(userVo.getUser_name()).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}
}
