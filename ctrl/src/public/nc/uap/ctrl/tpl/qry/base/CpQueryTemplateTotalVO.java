package nc.uap.ctrl.tpl.qry.base;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * 
 * 单子表/单表头/单表体聚合VO
 *
 * 创建日期:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
@nc.vo.annotation.AggVoInfo(parentVO = "nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO")
public class CpQueryTemplateTotalVO extends AggregatedValueObject {
	private CpQueryTemplateVO parentVO = null;
	private CpQueryConditionVO[] conditionVos = null;
	@Override
	public CircularlyAccessibleValueObject[] getChildrenVO() {
		return conditionVos;
	}
	@Override
	public CircularlyAccessibleValueObject getParentVO() {
		return parentVO;
	}
	@Override
	public void setChildrenVO(CircularlyAccessibleValueObject[] children) {
		this.conditionVos = (CpQueryConditionVO[]) children;
	}
	@Override
	public void setParentVO(CircularlyAccessibleValueObject parent) {
		this.parentVO = (CpQueryTemplateVO) parent;
	}
	public CpQueryTemplateVO getTempletVO() {
		return parentVO;
	}
	public CpQueryConditionVO[] getConditionVOs() {
		return conditionVos;
	}
}
