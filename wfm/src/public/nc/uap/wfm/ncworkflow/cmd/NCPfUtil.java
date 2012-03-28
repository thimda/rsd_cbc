package nc.uap.wfm.ncworkflow.cmd;

import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.AppUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * nc���̹�����
 * @author zhangxya
 *
 */
public class NCPfUtil {
	
	/**
	 * ��ȡaggvo
	 * @return
	 */
	public static AggregatedValueObject getWfmAggVO() {
		AggregatedValueObject aggVO = (AggregatedValueObject) AppUtil.getCntAppCtx().getAppAttribute(WfmConstants.WfmAppAttr_FormInFoCtx);
		return aggVO;
	}
	
	
	/**
	 * ��ȡaggvos
	 * @return
	 */
	public static AggregatedValueObject[] getWfmAggVOs() {
		AggregatedValueObject[] aggVOs = (AggregatedValueObject[]) AppUtil.getCntAppCtx().getAppAttribute(WfmConstants.WfmAppAttr_FormInFoCtx);
		return aggVOs;
	}
}
