package nc.uap.wfm.facility;
import nc.bs.framework.common.NCLocator;
import nc.uap.wfm.itf.IWfmAddSignBill;
import nc.uap.wfm.itf.IWfmAddSignQry;
import nc.uap.wfm.itf.IWfmAgentBill;
import nc.uap.wfm.itf.IWfmAgentQry;
import nc.uap.wfm.itf.IWfmAssignActorsBill;
import nc.uap.wfm.itf.IWfmAssignActorsQry;
import nc.uap.wfm.itf.IWfmCommonWordBill;
import nc.uap.wfm.itf.IWfmCommonWordQry;
import nc.uap.wfm.itf.IWfmFlowAgentBill;
import nc.uap.wfm.itf.IWfmFlowAgentQry;
import nc.uap.wfm.itf.IWfmFlowCateBill;
import nc.uap.wfm.itf.IWfmFlowCateQry;
import nc.uap.wfm.itf.IWfmFlwTypeBill;
import nc.uap.wfm.itf.IWfmFlwTypeQry;
import nc.uap.wfm.itf.IWfmFrmNumElemBill;
import nc.uap.wfm.itf.IWfmFrmNumElemQry;
import nc.uap.wfm.itf.IWfmFrmNumRuleBill;
import nc.uap.wfm.itf.IWfmFrmNumRuleQry;
import nc.uap.wfm.itf.IWfmHumActInsBill;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.itf.IWfmMyVisaBill;
import nc.uap.wfm.itf.IWfmMyVisaQry;
import nc.uap.wfm.itf.IWfmPageAttachBill;
import nc.uap.wfm.itf.IWfmPageAttachQry;
import nc.uap.wfm.itf.IWfmProDefBill;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.itf.IWfmProInsBill;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.itf.IWfmProInsStateBill;
import nc.uap.wfm.itf.IWfmProInsStateQry;
import nc.uap.wfm.itf.IWfmTaskBill;
import nc.uap.wfm.itf.IWfmTaskQry;
import nc.uap.wfm.itf.IWfmTaskTokenQry;
import nc.uap.wfm.itf.IWfmVirtualRoleBill;
import nc.uap.wfm.itf.IWfmVirtualRoleQry;
public class WfmServiceFacility {
	public static <T> T lookup(Class<T> clazz) {
		return NCLocator.getInstance().lookup(clazz);
	}
	public static IWfmProDefQry getProDefQry() {
		return WfmServiceFacility.lookup(IWfmProDefQry.class);
	}
	public static IWfmProDefBill getProDefBill() {
		return WfmServiceFacility.lookup(IWfmProDefBill.class);
	}
	public static IWfmFlwTypeBill getFlwTypeBill() {
		return WfmServiceFacility.lookup(IWfmFlwTypeBill.class);
	}
	public static IWfmFlwTypeQry getFlwTypeQry() {
		return WfmServiceFacility.lookup(IWfmFlwTypeQry.class);
	}
	/**
	 * 任务查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmTaskQry getTaskQry() {
		return WfmServiceFacility.lookup(IWfmTaskQry.class);
	}
	public static IWfmTaskBill getTaskBill() {
		return WfmServiceFacility.lookup(IWfmTaskBill.class);
	}
	/**
	 * 活动实例查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmHumActInsQry getHumActInsQry() {
		return WfmServiceFacility.lookup(IWfmHumActInsQry.class);
	}
	public static IWfmHumActInsBill getHumActInsBill() {
		return WfmServiceFacility.lookup(IWfmHumActInsBill.class);
	}
	/**
	 * 流程实例查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmProInsQry getProInsQry() {
		return WfmServiceFacility.lookup(IWfmProInsQry.class);
	}
	public static IWfmProInsBill getProInsBill() {
		return WfmServiceFacility.lookup(IWfmProInsBill.class);
	}
	/**
	 * 流程实例状态查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmProInsStateQry getProInsStateQry() {
		return WfmServiceFacility.lookup(IWfmProInsStateQry.class);
	}
	public static IWfmProInsStateBill getProInsStateBill() {
		return WfmServiceFacility.lookup(IWfmProInsStateBill.class);
	}
	/**
	 * 指派参与者查询和操作服务
	 */
	public static IWfmAssignActorsQry getAssignActorsQry() {
		return WfmServiceFacility.lookup(IWfmAssignActorsQry.class);
	}
	public static IWfmAssignActorsBill getAssignActorsBill() {
		return WfmServiceFacility.lookup(IWfmAssignActorsBill.class);
	}
	/**
	 * 加签查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmAddSignQry getAddSignQry() {
		return WfmServiceFacility.lookup(IWfmAddSignQry.class);
	}
	public static IWfmAddSignBill getAddSignBill() {
		return WfmServiceFacility.lookup(IWfmAddSignBill.class);
	}
	/**
	 * 任务代办查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmAgentQry getAgentQry() {
		return WfmServiceFacility.lookup(IWfmAgentQry.class);
	}
	public static IWfmAgentBill getAgentBill() {
		return WfmServiceFacility.lookup(IWfmAgentBill.class);
	}
	/**
	 * 常用语查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmCommonWordQry getCommonWordQry() {
		return WfmServiceFacility.lookup(IWfmCommonWordQry.class);
	}
	public static IWfmCommonWordBill getCommonWordBill() {
		return WfmServiceFacility.lookup(IWfmCommonWordBill.class);
	}
	/**
	 * 电子签章查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmMyVisaQry getMyVisaQry() {
		return WfmServiceFacility.lookup(IWfmMyVisaQry.class);
	}
	public static IWfmMyVisaBill getMyVisaBill() {
		return WfmServiceFacility.lookup(IWfmMyVisaBill.class);
	}
	/**
	 * 任务令牌查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmTaskTokenQry getTaskTokenQry() {
		return WfmServiceFacility.lookup(IWfmTaskTokenQry.class);
	}
	public static IWfmTaskBill getTaskTokenBill() {
		return WfmServiceFacility.lookup(IWfmTaskBill.class);
	}
	/**
	 * 虚拟角色查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmVirtualRoleQry getVirtualRoleQry() {
		return WfmServiceFacility.lookup(IWfmVirtualRoleQry.class);
	}
	public static IWfmVirtualRoleBill getVirtualRoleBill() {
		return WfmServiceFacility.lookup(IWfmVirtualRoleBill.class);
	}
	/**
	 * 纸质附件查询和操作服务
	 * 
	 * @return
	 */
	public static IWfmPageAttachQry getPageAttachQry() {
		return WfmServiceFacility.lookup(IWfmPageAttachQry.class);
	}
	public static IWfmPageAttachBill getPageAttachBill() {
		return WfmServiceFacility.lookup(IWfmPageAttachBill.class);
	}
	/**
	 * 流程分类的查询与分析
	 * 
	 * @return
	 */
	public static IWfmFlowCateQry getFlowCateQry() {
		return WfmServiceFacility.lookup(IWfmFlowCateQry.class);
	}
	public static IWfmFlowCateBill getFlowCateBill() {
		return WfmServiceFacility.lookup(IWfmFlowCateBill.class);
	}
	public static IWfmFlowAgentQry getFlowAgentQry() {
		return WfmServiceFacility.lookup(IWfmFlowAgentQry.class);
	}
	public static IWfmFlowAgentBill getFlowAgentBill() {
		return WfmServiceFacility.lookup(IWfmFlowAgentBill.class);
	}
	public static IWfmFrmNumRuleBill getFrmNumRuleBill() {
		return WfmServiceFacility.lookup(IWfmFrmNumRuleBill.class);
	}
	public static IWfmFrmNumRuleQry getFrmNumRuleQry() {
		return WfmServiceFacility.lookup(IWfmFrmNumRuleQry.class);
	}
	public static IWfmFrmNumElemQry getFrmNumElemQry() {
		return WfmServiceFacility.lookup(IWfmFrmNumElemQry.class);
	}
	public static IWfmFrmNumElemBill getFrmNumElemBill() {
		return WfmServiceFacility.lookup(IWfmFrmNumElemBill.class);
	}
}
