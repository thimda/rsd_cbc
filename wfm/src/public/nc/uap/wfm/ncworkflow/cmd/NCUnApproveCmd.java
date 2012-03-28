package nc.uap.wfm.ncworkflow.cmd;

import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * ����
 * @author zhangxya
 *
 */
public class NCUnApproveCmd extends UifCommand {

	@Override
	public void execute() {
		//�õ�ҳ�����л����Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//�õ���������
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			LfwPfUtil.unapprove(aggVo, flowTypePk, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
	
	}
	
	
	/**
	 * ����aggvo
	 * @return
	 */
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}

}
