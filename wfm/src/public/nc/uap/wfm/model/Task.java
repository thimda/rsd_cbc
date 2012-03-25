package nc.uap.wfm.model;
import java.io.Serializable;
import java.util.Set;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import org.apache.commons.beanutils.BeanUtils;
public class Task extends AbstractObservable implements Cloneable, Serializable {
	public static final String FinishType_Reject = "FinishType_Reject";// �˻����
	public static final String FinishType_Deliver = "FinishType_Deliver";// �������
	public static final String FinishType_Normal = "FinishType_Normal";// �������
	public static final String FinishType_Tramsmit = "FinishType_Tramsmit";// ת�����
	public static final String FinishType_SignStart = "FinishType_SignStart";// ǩ�����
	public static final String FinishType_Countersign = "FinishType_Countersign";// ��ǩ���
	public static final String CreateType_Normal = "CreateType_Normal";// ��������
	public static final String CreateType_AfterAddSign = "CreateType_AfterAddSign";// ���ǩ����
	public static final String CreateType_BeforeAddSign = "CreateType_BeforeAddSign";// ���ǩ����
	public static final String CreateType_Tramsmit = "CreateType_Tramsmit";// ת������
	public static final String CreateType_Deliver = "CreateType_Deliver";// ���Ĳ���
	public static final String CreateType_Reject = "CreateType_Reject";// �˻ز���
	public static final String State_Run = "State_Run";// ����
	public static final String State_End = "State_End";// �Ѱ�
	public static final String State_Suspended = "State_Suspended";// ����
	public static final String State_UnRead = "State_UnRead";// ����
	public static final String State_Readed = "State_Readed";// ����
	public static final String State_Plmnt = "State_Plmnt";// ִ����
	public static final String State_BeforeAddSignSend = "State_BeforeAddSignSend";// ��ǩ����
	public static final String State_BeforeAddSignPlmnt = "State_BeforeAddSignPlmnt";// ��ǩ��
	public static final String State_BeforeAddSignCmplt = "State_BeforeAddSignCmplt";// ��ǩ���
	public static final String State_BeforeAddSignStop = "State_BeforeAddSignStop";// ��ǩֹͣ
	public static final String HandlerPiece_UnRead = "HandlerPiece_UnRead";// δ����
	public static final String HandlerPiece_Readed = "HandlerPiece_Readed";// ������
	public static final String HandlerPiece_Rejected = "HandlerPiece_Rejected";// /�˻ؼ�
	public static final String ActionType_Normal = "ActionType_Normal";
	public static final String ActionType_Deliver = "ActionType_Deliver";
	public static final String ActionType_Major = "ActionType_Major";
	public static final String ActionType_Assist = "ActionType_Assist";
	private static final long serialVersionUID = 1L;
	/**
	 * �������ʶ��������ʱ�ҵ���һ����ڵ�
	 */
	private Task parent;
	/**
	 * ���̶���
	 */
	private ProDef proDef;
	/**
	 * ����ʵ��
	 */
	private ProIns proIns;
	/**
	 * ������ʵ��
	 */
	private ProIns rootProIns;
	/**
	 * ���������ڵĻʵ��
	 */
	private HumActIns humActIns;
	/**
	 * ������
	 */
	private Set<Task> subTasks;
	/**
	 * �����ʶ
	 */
	private String pk_task;
	/**
	 * ������
	 */
	private String pk_frmdef;
	/**
	 * ��ʵ��
	 */
	private String pk_frmins;
	/**
	 * ����Ĵ�����
	 */
	private String pk_creater;
	/**
	 * �����ӵ����
	 */
	private String pk_owner;
	/**
	 * �����ӵ�в���
	 */
	private String pk_ownerdept;
	/**
	 * �����ִ����
	 */
	private String pk_executer;
	/**
	 * ����Ĵ�����
	 */
	private String pk_agenter;
	/**
	 * ����ĸ�����
	 */
	private String pk_assigner;
	/**
	 * ����ǩ�±�ʶ
	 */
	private String pk_myvisa;
	/**
	 * ��ʶ�����Ƿ�ִ��
	 */
	private UFBoolean isnotexe;
	/**
	 * ��ʶ�����Ƿ�ͨ����ͨ������ͨ����
	 */
	private UFBoolean isnotpas;
	/**
	 * ��ɷ�ʽ
	 */
	private String finishType;
	/**
	 * ������ʽ
	 */
	private String createType;
	/**
	 * ���̿�ʼ����
	 */
	private UFDate proInsStartDate;
	/**
	 * ����ʼ����
	 */
	private UFDate startDate;
	/**
	 * �����������
	 */
	private UFDate endDate;
	/**
	 * ����ǩ��ʱ��
	 */
	private UFDate signDate;
	/**
	 * �������ʱ��
	 */
	private String standingTime;
	/**
	 * ��������
	 */
	private String opinion;
	/**
	 * ����ı�ǩ
	 */
	private String scratchpad;
	/**
	 * ��ʶ����״̬
	 */
	private String state;
	/**
	 * ������
	 */
	private String handlepiece;
	/**
	 * �������ȼ���
	 */
	private String priority;
	/**
	 * ��������
	 */
	private WfmFlwTypeVO flowType;
	private String actionType;
	/**
	 * ��ǩ����
	 */
	private String addSignTimes;
	/**
	 * �Ƿ��ǽ�������ʵ��������
	 */
	private UFBoolean isNotEnded;
	/**
	 * ��������
	 */
	private String ext0;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	private String ext6;
	private String ext7;
	private String ext8;
	private String ext9;
	public String getPk_task() {
		return pk_task;
	}
	public void setPk_task(String pk_task) {
		this.pk_task = pk_task;
	}
	public Task getParent() {
		return parent;
	}
	public void setParent(Task parent) {
		this.parent = parent;
	}
	public HumActIns getHumActIns() {
		return humActIns;
	}
	public void setHumActIns(HumActIns humActIns) {
		this.humActIns = humActIns;
	}
	public String getPk_creater() {
		return pk_creater;
	}
	public void setPk_creater(String pk_creater) {
		this.pk_creater = pk_creater;
	}
	public String getPk_owner() {
		return pk_owner;
	}
	public void setPk_owner(String pk_owner) {
		this.pk_owner = pk_owner;
	}
	public String getPk_executer() {
		return pk_executer;
	}
	public void setPk_executer(String pk_executer) {
		this.pk_executer = pk_executer;
	}
	public String getPk_agenter() {
		return pk_agenter;
	}
	public void setPk_agenter(String pk_agenter) {
		this.pk_agenter = pk_agenter;
	}
	public UFBoolean getIsnotexe() {
		return isnotexe;
	}
	public void setIsnotexe(UFBoolean isnotexe) {
		this.isnotexe = isnotexe;
	}
	public UFBoolean getIsnotpas() {
		return isnotpas;
	}
	public void setIsnotpas(UFBoolean isnotpas) {
		this.isnotpas = isnotpas;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UFDate getStartDate() {
		return startDate;
	}
	public void setStartDate(UFDate startDate) {
		this.startDate = startDate;
	}
	public UFDate getEndDate() {
		return endDate;
	}
	public void setEndDate(UFDate endDate) {
		this.endDate = endDate;
	}
	public Set<Task> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(Set<Task> subTasks) {
		this.subTasks = subTasks;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public ProIns getProIns() {
		return proIns;
	}
	public void setProIns(ProIns proIns) {
		this.proIns = proIns;
	}
	public ProDef getProDef() {
		return proDef;
	}
	public void setProDef(ProDef proDef) {
		this.proDef = proDef;
	}
	public String getPk_frmins() {
		return pk_frmins;
	}
	public void setPk_frmins(String pk_frmins) {
		this.pk_frmins = pk_frmins;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPk_frmdef() {
		return pk_frmdef;
	}
	public void setPk_frmdef(String pk_frmdef) {
		this.pk_frmdef = pk_frmdef;
	}
	public String getPk_myvisa() {
		return pk_myvisa;
	}
	public void setPk_myvisa(String pk_myvisa) {
		this.pk_myvisa = pk_myvisa;
	}
	public ProIns getRootProIns() {
		return rootProIns;
	}
	public void setRootProIns(ProIns rootProIns) {
		this.rootProIns = rootProIns;
	}
	public Task clone() {
		Task task = new Task();
		try {
			BeanUtils.copyProperties(task, this);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("��¡ʧ��");
		}
		return task;
	}
	public String getPk_assigner() {
		return pk_assigner;
	}
	public void setPk_assigner(String pk_assigner) {
		this.pk_assigner = pk_assigner;
	}
	public String getFinishType() {
		return finishType;
	}
	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}
	public String getExt0() {
		return ext0;
	}
	public void setExt0(String ext0) {
		this.ext0 = ext0;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	public String getExt6() {
		return ext6;
	}
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	public String getExt7() {
		return ext7;
	}
	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}
	public String getExt8() {
		return ext8;
	}
	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}
	public String getExt9() {
		return ext9;
	}
	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}
	public String getCreateType() {
		return createType;
	}
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getStandingTime() {
		return standingTime;
	}
	public void setStandingTime(String standingTime) {
		this.standingTime = standingTime;
	}
	public String getScratchpad() {
		return scratchpad;
	}
	public void setScratchpad(String scratchpad) {
		this.scratchpad = scratchpad;
	}
	public String getAddSignTimes() {
		return addSignTimes;
	}
	public void setAddSignTimes(String addSignTimes) {
		this.addSignTimes = addSignTimes;
	}
	public String getPk_ownerdept() {
		return pk_ownerdept;
	}
	public void setPk_ownerdept(String pk_ownerdept) {
		this.pk_ownerdept = pk_ownerdept;
	}
	public UFDate getSignDate() {
		return signDate;
	}
	public void setSignDate(UFDate signDate) {
		this.signDate = signDate;
	}
	public String getHandlepiece() {
		return handlepiece;
	}
	public void setHandlepiece(String handlepiece) {
		this.handlepiece = handlepiece;
	}
	public UFDate getProInsStartDate() {
		return proInsStartDate;
	}
	public void setProInsStartDate(UFDate proInsStartDate) {
		this.proInsStartDate = proInsStartDate;
	}
	public UFBoolean getIsNotEnded() {
		return isNotEnded;
	}
	public void setIsNotEnded(UFBoolean isNotEnded) {
		this.isNotEnded = isNotEnded;
	}
	public WfmFlwTypeVO getFlowType() {
		return flowType;
	}
	public void setFlowType(WfmFlwTypeVO flowType) {
		this.flowType = flowType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
