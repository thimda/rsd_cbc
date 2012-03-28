package nc.uap.wfm.ncworkflow.cmd;

import java.util.HashMap;

import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * 收回
 * @author zhangxya
 *
 */
public class NCReCallCmd extends UifCommand {
	
	//回收操作类型
	private String recallAction;
	
	//参数信息，用户保存是否含有流程
	HashMap<String, Object> eParam;
	
	public NCReCallCmd(String recallAction, HashMap<String, Object> eParam) {
		this.recallAction = recallAction;
	}

	@Override
	public void execute() {
		//得到页面序列化后的Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			LfwPfUtil.reCallFlow(recallAction, aggVo, flowTypePk, null, eParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("流程收回操作出现异常!");
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
