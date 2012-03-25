package nc.uap.wfm.utils;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAddSignQry;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.vo.WfmTaskVO;
import org.apache.commons.lang.StringUtils;
public class BeforeAddSignUtil {
	public static String getMaxAddSignTimes(Task task) {
		return BeforeAddSignUtil.getMaxAddSignTimes(task.getPk_task());
	}
	public static String getMaxAddSignTimes(String taskPk) {
		String maxTime = "0";
		try {
			maxTime = NCLocator.getInstance().lookup(IWfmAddSignQry.class).getMaxStateTimeByTaskPk(taskPk);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return String.valueOf(maxTime);
	}
	public static String getAddSingMsg(WfmTaskVO taskVo) {
		StringBuffer buf = new StringBuffer();
		buf.append("【会签日期】:" + taskVo.getEnddate());
		buf.append("\n");
		buf.append("【会签人】:" + WfmTransUtil.getUserNameByUserPk(taskVo.getPk_owner()));
		buf.append("\n");
		buf.append("【会签内容】:");
		if (StringUtils.isNotBlank(taskVo.getOpinion())) {
			buf.append(taskVo.getOpinion());
		}
		buf.append("\n");
		return buf.toString();
	}
}
