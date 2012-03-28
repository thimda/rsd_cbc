package nc.uap.wfm.ncworkflow;

import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.event.DialogEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.ncworkflow.cmd.LfwPfUtil;
import nc.uap.wfm.ncworkflow.cmd.NCPfUtil;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * nc流程图显示的ctrl
 * @author zhangxya
 *
 */
public class NCFlowImageCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void show_beforeShow(DialogEvent dialogEvent) {
		//得到页面序列化后的Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		IFlowBizItf flowBizImpl = LfwPfUtil.getFlowBizImplByMdComp(flowTypePk, aggVo);
		//单据id
		String billIdValue = flowBizImpl.getBillId();
		//交易类型
		String transType = flowBizImpl.getTranstype();
		LfwWidget widget = AppUtil.getWidget("pubview_ncworkflow");
		ImageComp imageComp = (ImageComp) widget.getViewComponents().getComponent("wfimage");
		//imageComp.setImage1("/wfimg?billType=" + flowTypePk + "&billId=" + billIdValue + "&"+ NCApproveConstant.TRANSTYPE + "=" + transType);
		//imageComp.setImage2("/wfimg?billType=" + flowTypePk + "&billId=" + billIdValue + "&"+ NCApproveConstant.TRANSTYPE + "=" + transType);
	}
	
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}
	
}
