package nc.uap.wfm.ncworkflow.cmd;
import java.util.HashMap;
import java.util.Map;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;
/**
 * nc审批命令
 * 
 * @author zhangxya
 * 
 */
public class NCApproveCmd extends UifCommand {
	public static String APPROVESELTEXT = "approveSelText";
	public static String ASSIGNBUTTON = "assignButton";
	public static String APPROVEMESSAGE = "approveMessage";
	public static String APPROVEUSERS = "approveusers";
	@Override public void execute() {
		// 审批动作
		dealApprove();
		// 其他处理
		dealOthers();
		// 关闭审批对话框
	}
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}
	/**
	 * 处理审批动作
	 */
	protected void dealApprove() {
		// 得到页面序列化后的Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		// 得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		// 审批同意/不同意/驳回
		String operator = WfmTaskUtil.getOperator();
		// 审批意见
		String opinion = WfmTaskUtil.getOpinion();
		Map approveInfoMap = new HashMap<String, String>();
		String approveValue = null;
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator))
			approveValue = "Y";
		else if (WfmConstants.WfmOperator_UnAgree.equalsIgnoreCase(operator))
			approveValue = "N";
		else if (WfmConstants.WfmOperator_Reject.equalsIgnoreCase(operator)) {
			approveValue = "R";
		}
		// 指派人
		String[] pk_users = (String[]) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("AssignPKUsers");
		// 执行审批操作
		try {
			approveInfoMap.put(LfwPfUtil.APPROVEINFO, approveValue);
			approveInfoMap.put(LfwPfUtil.APPROVEMESSAGE, opinion);
			if (!onBeforApprove(aggVo)) {
				return;
			}
			// 执行审批动作
			LfwPfUtil.approve(aggVo, flowTypePk, approveInfoMap, pk_users);
		} catch (Exception e1) {
			dealWithException(e1);
		}
	}
	/**
	 * 异常处理类
	 * 
	 * @param e1
	 */
	protected void dealWithException(Exception e1) {
	// Logger.error(e1, e1);
	// throw new LfwRuntimeException(e1.getMessage());
	}
	/**
	 * 审批前的校验
	 * 
	 * @return
	 */
	protected boolean onBeforApprove(AggregatedValueObject aggVo) throws Exception {
		return true;
	}
	/**
	 * 其他额外处理
	 */
	protected void dealOthers() {};
}
