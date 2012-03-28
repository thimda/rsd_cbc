package nc.uap.wfm.ncworkflow.cmd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;
/**
 * nc流程审批操作
 * 
 * @author zhangxya
 * 
 */
public class NCBatchApproveCmd extends UifCommand {
	// 审批结果
	private Object approveResult;
	@Override public void execute() {
		// 得到页面序列化后的Aggvo
		AggregatedValueObject[] aggVos = builderAggVO();
		// 得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		// 同意/不同意状态
		String operator = WfmTaskUtil.getOperator();
		// 审批意见
		String opinion = WfmTaskUtil.getOpinion();
		String approveValue = null;
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator))
			approveValue = "Y";
		else if (WfmConstants.WfmOperator_UnAgree.equalsIgnoreCase(operator))
			approveValue = "N";
		else if (WfmConstants.WfmOperator_Reject.equalsIgnoreCase(operator)) {
			approveValue = "R";
		}
		List<Map<String, String>> approveList = new ArrayList<Map<String, String>>();
		// 审批信息
		Map<String, String> approveInfoMap = null;
		for (int i = 0; i < aggVos.length; i++) {
			approveInfoMap = new HashMap<String, String>();
			approveInfoMap.put(LfwPfUtil.APPROVEINFO, approveValue);
			approveInfoMap.put(LfwPfUtil.APPROVEMESSAGE, opinion);
			approveList.add(approveInfoMap);
		}
		try {
			approveResult = LfwPfUtil.processBatchAction(aggVos, flowTypePk, approveList.toArray(), null);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("批审单据出现问题!");
		}
	}
	// 返回批审的结果
	protected Object getApproveResult() {
		return approveResult;
	}
	/**
	 * 构建aggvo
	 * 
	 * @return
	 */
	public AggregatedValueObject[] builderAggVO() {
		return NCPfUtil.getWfmAggVOs();
	}
}
