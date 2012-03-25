package nc.uap.wfm.model;
import java.util.ArrayList;
import java.util.List;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.dftimpl.DefaultHumActHandler;
import org.apache.commons.lang.StringUtils;
/**
 * �û��
 * 
 * @author tianchw
 * 
 */
public class HumAct extends Activity {
	public static final String ActionType_Normal = "Normal"; // ���������
	public static final String ActionType_Deliver = "Deliver";// ���Ļ����
	public static final String ActionType_Hndertake = "Undertake";// �а�����
	public static final String ActionType_Major = "Major";
	public static final String ActionType_Assist = "Assist";
	private static final long serialVersionUID = -3344147219315831233L;
	/**
	 * �ִ���߲���
	 */
	private ActorStrategy actorStrategy;
	/**
	 * Э�������
	 */
	private AssistStrategy assistStrategy;
	/**
	 * ��������
	 */
	private AllotStrategy allotStrategy;
	/**
	 * ���ɲ���
	 */
	private CompleteStrategy completeStrategy;
	/**
	 * ����ز���
	 */
	private RejectStrategy rejectStrategy;
	/**
	 * �����
	 */
	private MessageStrategy messageStrategy;
	/**
	 * ��������
	 */
	private String actionType;
	/**
	 * �Ƿ������ӡ
	 */
	private String allowPrint;
	/**
	 * �Ƿ����ת��
	 */
	private String allowTransmit;
	/**
	 * �Ƿ�����ͬ�����޶�
	 */
	private String allowResDept;
	/**
	 * �Ƿ�����༭���
	 */
	private String allowEditOpinion;
	/**
	 * ����Ƿ����
	 */
	private String opinionNeed;
	/**
	 * �Ƿ�����߰�
	 */
	private String allowUrge;
	/**
	 * �Ƿ������ǩ
	 */
	private String allowAddSign;
	/**
	 * �Ƿ�����ǰһ��ָ��
	 */
	private String allowPreAssign;
	/**
	 * ָ�ɵ�ʲô�Ƿ������ѡ
	 */
	private String preAssignMultiSelect;
	/**
	 * �Ƿ�������
	 */
	private String allowDeliver;
	/**
	 * �Ƿ��������ͨ��
	 */
	private String allowChanel;
	/**
	 * ����������������
	 */
	private String subProDefPks;
	/**
	 * ���̱�����
	 */
	private String pk_formdefinition;
	/**
	 * ��ڵ㴦����
	 */
	private String delegatorClass;
	/**
	 * ��������1
	 * 
	 */
	private String extAttr0;
	/**
	 * ��������2
	 */
	private String extAttr1;
	/**
	 * ��������3
	 */
	private String extAttr2;
	/**
	 * ���ѷ�ʽ
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
