package nc.uap.wfm.model;
import java.util.ArrayList;
import java.util.List;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.dftimpl.DefaultHumActHandler;
import org.apache.commons.lang.StringUtils;
/**
 * 用户活动
 * 
 * @author tianchw
 * 
 */
public class HumAct extends Activity {
	public static final String ActionType_Normal = "Normal"; // 正常活动类型
	public static final String ActionType_Deliver = "Deliver";// 传阅活动类型
	public static final String ActionType_Hndertake = "Undertake";// 承办活动类型
	public static final String ActionType_Major = "Major";
	public static final String ActionType_Assist = "Assist";
	private static final long serialVersionUID = -3344147219315831233L;
	/**
	 * 活动执行者策略
	 */
	private ActorStrategy actorStrategy;
	/**
	 * 协办参与者
	 */
	private AssistStrategy assistStrategy;
	/**
	 * 活动分配策略
	 */
	private AllotStrategy allotStrategy;
	/**
	 * 活动完成策略
	 */
	private CompleteStrategy completeStrategy;
	/**
	 * 活动驳回策略
	 */
	private RejectStrategy rejectStrategy;
	/**
	 * 活动策略
	 */
	private MessageStrategy messageStrategy;
	/**
	 * 动作类型
	 */
	private String actionType;
	/**
	 * 是否允许打印
	 */
	private String allowPrint;
	/**
	 * 是否可以转发
	 */
	private String allowTransmit;
	/**
	 * 是否允许同部门限定
	 */
	private String allowResDept;
	/**
	 * 是否允许编辑意见
	 */
	private String allowEditOpinion;
	/**
	 * 意见是否必填
	 */
	private String opinionNeed;
	/**
	 * 是否允许催办
	 */
	private String allowUrge;
	/**
	 * 是否允许加签
	 */
	private String allowAddSign;
	/**
	 * 是否允许前一步指派
	 */
	private String allowPreAssign;
	/**
	 * 指派的什么是否允许多选
	 */
	private String preAssignMultiSelect;
	/**
	 * 是否允许传阅
	 */
	private String allowDeliver;
	/**
	 * 是否允许快速通道
	 */
	private String allowChanel;
	/**
	 * 允许启动的子流程
	 */
	private String subProDefPks;
	/**
	 * 流程表单定义
	 */
	private String pk_formdefinition;
	/**
	 * 活动节点处理类
	 */
	private String delegatorClass;
	/**
	 * 扩张属性1
	 * 
	 */
	private String extAttr0;
	/**
	 * 扩张属性2
	 */
	private String extAttr1;
	/**
	 * 扩张属性3
	 */
	private String extAttr2;
	/**
	 * 提醒方式
	 */
	private String remindType = WfmConstants.RemindType_Mail;
	public ActorStrategy getActorStrategy() {
		return actorStrategy;
	}
	public void setActorStrategy(ActorStrategy actorStrategy) {
		this.actorStrategy = actorStrategy;
	}
	public AllotStrategy getAllotStrategy() {
		return allotStrategy;
	}
	public void setAllotStrategy(AllotStrategy allotStrategy) {
		this.allotStrategy = allotStrategy;
	}
	public CompleteStrategy getCompleteStrategy() {
		return completeStrategy;
	}
	public void setCompleteStrategy(CompleteStrategy completeStrategy) {
		this.completeStrategy = completeStrategy;
	}
	public RejectStrategy getRejectStrategy() {
		return rejectStrategy;
	}
	public void setRejectStrategy(RejectStrategy rejectStrategy) {
		this.rejectStrategy = rejectStrategy;
	}
	public MessageStrategy getMessageStrategy() {
		return messageStrategy;
	}
	public void setMessageStrategy(MessageStrategy messageStrategy) {
		this.messageStrategy = messageStrategy;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	public String getAllowPrint() {
		return allowPrint;
	}
	public void setAllowPrint(String allowPrint) {
		this.allowPrint = allowPrint;
	}
	public String getSubProDefPks() {
		return subProDefPks;
	}
	public void setSubProDefPks(String subProDefPks) {
		this.subProDefPks = subProDefPks;
	}
	public String getAllowTransmit() {
		return allowTransmit;
	}
	public void setAllowTransmit(String allowTransmit) {
		this.allowTransmit = allowTransmit;
	}
	public String getOpinionNeed() {
		return opinionNeed;
	}
	public void setOpinionNeed(String opinionNeed) {
		this.opinionNeed = opinionNeed;
	}
	public String getPreAssignMultiSelect() {
		return preAssignMultiSelect;
	}
	public void setPreAssignMultiSelect(String preAssignMultiSelect) {
		this.preAssignMultiSelect = preAssignMultiSelect;
	}
	public ProDef[] getSubProDefs() {
		String proDefPks = this.getSubProDefPks();
		if (proDefPks == null || proDefPks.length() == 0) {
			return null;
		}
		String[] str = proDefPks.split("\\,");
		List<ProDef> proDefs = new ArrayList<ProDef>();
		ProDef proDef = null;
		for (int i = 0; i < str.length; i++) {
			proDef = ProDefsContainer.getByProDefPkAndId(str[i], "");
			proDefs.add(proDef);
		}
		return proDefs.toArray(new ProDef[0]);
	}
	public boolean isNotTramsmit() {
		String flag = this.getAllowTransmit();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotPrint() {
		String flag = this.getAllowPrint();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotAddSign() {
		String flag = this.getAllowAddSign();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotPreAssign() {
		String flag = this.getAllowPreAssign();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotDeliver() {
		String flag = this.getAllowDeliver();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotResDept() {
		String flag = this.getAllowResDept();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotEditOpinion() {
		String flag = this.getAllowEditOpinion();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotUrge() {
		String flag = this.getAllowUrge();
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotChannel() {
		String flag = this.getAllowChanel();
		if (flag == null || flag.length() == 0) {
			flag = "false";
		}
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotMulti() {
		String flag = this.getPreAssignMultiSelect();
		if (flag == null || flag.length() == 0) {
			flag = "false";
		}
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotNeedOpinion() {
		String flag = this.getOpinionNeed();
		if (flag == null || flag.length() == 0) {
			flag = "false";
		}
		if (DblConstants.StrTrue.equalsIgnoreCase(flag)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNotReject() {
		RejectStrategy sgy = this.getRejectStrategy();
		String str = sgy.getIsNotReject();
		if (StringUtils.isNotBlank(str)) {
			if (DblConstants.StrTrue.equalsIgnoreCase(str))
				return true;
			else
				return false;
		}
		return true;
	}
	public String getAllowAddSign() {
		return allowAddSign;
	}
	public void setAllowAddSign(String allowAddSign) {
		this.allowAddSign = allowAddSign;
	}
	public String getAllowPreAssign() {
		return allowPreAssign;
	}
	public void setAllowPreAssign(String allowPreAssign) {
		this.allowPreAssign = allowPreAssign;
	}
	public String getDelegatorClass() {
		if (this.delegatorClass == null || this.delegatorClass.length() == 0) {
			delegatorClass = DefaultHumActHandler.class.getName();
		}
		return delegatorClass;
	}
	public void setDelegatorClass(String delegatorClass) {
		this.delegatorClass = delegatorClass;
	}
	public String getAllowDeliver() {
		return allowDeliver;
	}
	public void setAllowDeliver(String allowDeliver) {
		this.allowDeliver = allowDeliver;
	}
	public String getAllowResDept() {
		return allowResDept;
	}
	public void setAllowResDept(String allowResDept) {
		this.allowResDept = allowResDept;
	}
	public String getAllowEditOpinion() {
		return allowEditOpinion;
	}
	public void setAllowEditOpinion(String allowEditOpinion) {
		this.allowEditOpinion = allowEditOpinion;
	}
	public String getAllowUrge() {
		return allowUrge;
	}
	public void setAllowUrge(String allowUrge) {
		this.allowUrge = allowUrge;
	}
	public String getPk_formdefinition() {
		if (pk_formdefinition == null || pk_formdefinition.length() == 0 || "null".equalsIgnoreCase(pk_formdefinition)) {
			return null;
		}
		return pk_formdefinition;
	}
	public void setPk_formdefinition(String pk_formdefinition) {
		this.pk_formdefinition = pk_formdefinition;
	}
	public String getAllowChanel() {
		return allowChanel;
	}
	public void setAllowChanel(String allowChanel) {
		this.allowChanel = allowChanel;
	}
	public String getExtAttr0() {
		return extAttr0;
	}
	public void setExtAttr0(String extAttr0) {
		this.extAttr0 = extAttr0;
	}
	public String getExtAttr1() {
		return extAttr1;
	}
	public void setExtAttr1(String extAttr1) {
		this.extAttr1 = extAttr1;
	}
	public String getExtAttr2() {
		return extAttr2;
	}
	public void setExtAttr2(String extAttr2) {
		this.extAttr2 = extAttr2;
	}
	public AssistStrategy getAssistStrategy() {
		return assistStrategy;
	}
	public void setAssistStrategy(AssistStrategy assistStrategy) {
		this.assistStrategy = assistStrategy;
	}
}
