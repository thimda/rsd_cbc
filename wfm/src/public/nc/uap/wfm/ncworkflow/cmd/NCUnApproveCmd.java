package nc.uap.wfm.ncworkflow.cmd;

import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * 反审
 * @author zhangxya
 *
 */
public class NCUnApproveCmd extends UifCommand {

	@Override
	public void execute() {
		//得到页面序列化后的Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			LfwPfUtil.unapprove(aggVo, flowTypePk, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
	
	}
	
	
	/**
	 * 构建aggvo
	 * @return
	 */
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}

}
