package nc.uap.wfm.ncworkflow.cmd;

import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.AppUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * nc流程工具类
 * @author zhangxya
 *
 */
public class NCPfUtil {
	
	/**
	 * 获取aggvo
	 * @return
	 */
	public static AggregatedValueObject getWfmAggVO() {
		AggregatedValueObject aggVO = (AggregatedValueObject) AppUtil.getCntAppCtx().getAppAttribute(WfmConstants.FormVO);
		return aggVO;
	}
}
