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
 * nc������������
 * 
 * @author zhangxya
 * 
 */
public class NCBatchApproveCmd extends UifCommand {
	// �������
	private Object approveResult;
	@Override public void execute() {
		// �õ�ҳ�����л����Aggvo
		AggregatedValueObject[] aggVos = builderAggVO();
		// �õ���������
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		// ͬ��/��ͬ��״̬
		String operator = WfmTaskUtil.getOperator();
		// �������
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
		// ������Ϣ
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
			throw new LfwRuntimeException("���󵥾ݳ�������!");
		}
	}
	// ��������Ľ��
	protected Object getApproveResult() {
		return approveResult;
	}
	/**
	 * ����aggvo
	 * 
	 * @return
	 */
	public AggregatedValueObject[] builderAggVO() {
		return NCPfUtil.getWfmAggVOs();
	}
}
