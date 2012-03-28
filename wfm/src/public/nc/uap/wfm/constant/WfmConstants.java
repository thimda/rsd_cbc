package nc.uap.wfm.constant;
import nc.uap.wfm.dftimpl.DefaultFormDataBuilder;
import nc.uap.wfm.dftimpl.DefaultHumActHandler;
import nc.uap.wfm.dftimpl.DefaultLogicDecision;
import nc.uap.wfm.dftimpl.DefaultProDefExt;
import nc.uap.wfm.pagemodel.FlwDataDispPageModel;
/**
 * @author tinchw
 */
public class WfmConstants {
	public static final String StrTrue = "true";
	public static final String StrFalse = "false";
	public static final String StrCountersign = "countersign";
	public static final String StrSignstart = "signstart";
	/**
	 * 流程平台指派常量
	 */
	public static final String HumActId = "port_id";
	public static final String PortId = "port_id";
	public static final String HumActName = "port_name";
	public static final String ProDefId = "prodef_id";
	public static final String ParentId = "parent_id";
	public static final String ProDefPk = "pk_prodef";
	public static final String PkProDef = "pk_prodef";
	public static final String IsAssign = "isassign";
	public static final String UserPks = "userpks";
	public static final String UserNames = "usernames";
	public static final String FrmInsPk = "frminspk";
	public static final String DsUsers = "ds_users";
	public static final String PkUser = "pk_user";
	public static final String UserName = "username";
	public static final String UserPk = "pk_user";
	/**
	 * 流程平台默认实现
	 */
	public static final String FlwDataDispModel = FlwDataDispPageModel.class.getName();
	public static final String ExtAttrHandlerClass = DefaultProDefExt.class.getName();
	public static final String LogicDecisionClass = DefaultLogicDecision.class.getName();
	public static final String DefaultFormDataBuilder = DefaultFormDataBuilder.class.getName();
	public static final String DefaultHumActHandler = DefaultHumActHandler.class.getName();
	/**
	 * 流程平台执行终端
	 */
	public static final String EventSrc = "eventsrc";
	public static final String EventSrc_Mail = "eventsrc_mail";
	public static final String EventSrc_MsgSys = "eventsrc_msgsys";
	public static final String EventSrc_SubFlow = "eventsrc_subflow";
	/**
	 * 流程平台动作类型
	 */
	public static final String ActionType_Audit = "Audit";
	public static final String ActionType_LeaderAudit = "Leader_Audit";
	public static final String ActionType_Normal = "Normal";
	public static final String ActionType_Cirulated = "Cirulated";
	public static final String ActionType = "actiontype";
	/**
	 * 流程平台参与者策略
	 */
	public static final String ActorSgy_AppointActor = "AppointActor";// 指定参与者
	public static final String ActorSgy_ProcessStart = "PrcessStart";// 同流程启动者
	public static final String ActorSgy_PreActAssign = "PreActAssign";// 前一个活动指派
	public static final String ActorSgy_SelfDefActor = "SelfDefActor";
	public static final String ActorSgy_SameSomeActs = "SameSomeActs";// 同某几个活动节点的权限一样
	public static final String ActorSgy_SomeActAsign = "SomeActAsign";// 由某个活动节点指派的权限
	/**
	 * 流程平台分配者策略
	 */
	public static final String AllotSgy_Any = "Any";// 分配给一个人
	public static final String AllotSyg_All = "All";// 分配给所有人
	/**
	 * 流程平台完成策略
	 */
	public static final String CompleteSgy_Occupy = "Occupy";// 抢占策略
	public static final String CompleteSgy_Countersign = "Countersign";// 会签策略
	public static final String CompleteSgy_ByCount = "ByCount";// 按数量完成策略
	public static final String CompleteSgy_ByPercent = "ByPercent";// 按百分比完成策略
	/**
	 * 流程平台驳回策略
	 */
	public static final String RejectSgy_PreviousHumAct = "PreviousHumAct";// 驳回到前一个节点
	public static final String RejectSgy_StartHumAct = "StartHumAct";// 驳回到开始活动
	public static final String RejectSgy_AllHumAct = "AllHumAct";// 驳回范围为所有节点
	public static final String RejectSgy_AppointHumAct = "AppointHumAct";// 驳回到指定节点
	/**
	 * 流程平台超时提醒方式
	 */
	public static final String OverTimeRemiand_Email = "Email";// 邮件提醒
	public static final String OverTimeRemiand_Message = "Message";// 消息提醒
	/**
	 * 流程平台超时动作
	 */
	public static final String OverTimeSgy_Wait = "Wait";// 继续等待
	public static final String OverTimeSgy_Rollback = "Rollback";// 活动后退
	public static final String OverTimeSgy_Suspend = "Suspend";// 流程挂起
	public static final String OverTimeSgy_Cancel = "Cancel";// 流程取消
	public static final String OverTimeSgy_Transfer = "Transfer";// 工作移交
	public static final String Unit_Day = "天";
	public static final String Unit_Hour = "时";
	public static final String Unit_Min = "分";
	public static final String Unit_Sec = "秒";
	/**
	 * 流程平台聚合逻辑
	 */
	public static final String LogicAnd = "And";
	public static final String LogicOr = "Or";
	public static final String LogicByCountAnd = "BycountAnd";
	/**
	 * 附件权限常量
	 */
	public static final String AttachPurview_AttachList_Add = "AttachPurview_AttachList_Add";
	public static final String AttachPurview_AttachList_Delete = "AttachPurview_AttachList_Delete";
	public static final String AttachPurview_AttachList_Modify = "AttachPurview_AttachList_Modify";
	public static final String AttachPurview_AttachFile_Upload = "AttachPurview_AttachFile_Upload";
	public static final String AttachPurview_AttachFile_Download = "AttachPurview_AttachFile_Download";
	public static final String AttachPurview_AttachFile_Modify = "AttachPurview_AttachFile_Modify";
	public static final String AttachPurview_AttachFile_Delete = "AttachPurview_AttachFile_Delete";
	public static final String AttachPurview_AttachFile_Brower = "AttachPurview_AttachFile_Brower";
	public static final String AttachPurview_WrodFile_Enable = "AttachPurview_WrodFile_Enable";
	public static final String StrNull = "~";
	/**
	 * 流程平台操作常量
	 */
	public static final String WfmOperator_Agree = "agree";
	public static final String WfmOperator_UnAgree = "noagree";
	public static final String WfmOperator_Reject = "reject";
	public static final String WfmOperator_Transmit = "transmit";
	public static final String WfmOperator_Deliver = "deliver";
	public static final String WfmOperator_AfterAddSign = "afterAddSign";
	public static final String WfmOperator_BeforeAddSign = "beforeAddSign";
	/**
	 * 流程平台后加签常量
	 */
	public static final String WfmAfterAddSignUserNames = "afteraddsignusernames";
	public static final String WfmAfterAddSignUserValue = "afteraddsignuserpks";
	public static final String WfmAfterAddSignOpinion = "afteraddsignopinion";
	/**
	 * 流程平台公共view
	 */
	public static final String WfmPubView_ExeTask = "pubview_exetask";
	public static final String WfmPubView_History = "pubview_history";
	public static final String WfmPubView_AfterAddSign = "pubview_afteraddsign";
	public static final String WfmPubView_Assign = "pubview_assign";
	public static final String WfmPubView_CommonWord = "pubview_commonword";
	public static final String WfmPubView_Deliver = "pubview_deliver";
	public static final String WfmPubView_FlowImg = "pubview_flowimg";
	public static final String WfmPubView_FlowType = "pubview_flowtype";
	/**
	 * 流程平台view
	 */
	public static final String WidgetMain = "main";
	public static final String WidgetUerge = "uerge";
	public static final String WidgetAssign = "assign";
	public static final String WidgetUsers = "users";
	public static final String WidgetQuery = "query";
	public static final String WidtetStart = "start";
	public static final String WidgetSign = "sign";
	public static final String WidgetSpad = "spad";
	public static final String WidgetCode = "code";
	public static final String WidgetConst = "const";
	public static final String WidgetDate = "date";
	/**
	 * 流程平台组建常量
	 */
	public static final String WfmComp_Operator = "text_exeaction";
	public static final String WfmComp_Logic = "text_logic";
	public static final String WfmComp_Opinion = "text_opinion";
	public static final String WfmComp_BeforeAddSignUser = "text_beforeaddsignuser";
	public static final String WfmComp_BeforeAddSignOpinion = "text_beforeaddsignopinion";
	public static final String WfmComp_TransOpinion = "text_tranopinion";
	public static final String WfmComp_TransUser = "text_transmituser";
	public static final String WfmComp_BtnOk = "btn_ok";
	public static final String WfmComp_BtnCancel = "btn_cancel";
	public static final String WfmComp_GRID_ALLACTORS = "grid_allactors";
	public static final String WfmComp_GRID_SELECTEDS = "grid_selecteds";
	public static final String WfmComp_RightOne = "btn_right_one";
	public static final String WfmComp_RightAll = "btn_rigth_all";
	public static final String WfmComp_LeftOne = "btn_left_one";
	public static final String WfmComp_LeftAll = "btn_left_all";
	/**
	 * 流程平台中AppAttr中的常量
	 */
	public static final String WfmAppAttr_FormInFoCtx = "$$$$$$$$FORMINFOCTX";
	public static final String WfmAppAttr_Task = "$$$$$$$$Task";
	public static final String WfmAppAttr_FolwTypePk = "$$$$$$$$FLOWTYPEPK";
	public static final String WfmAppAttr_TaskPk = "$$$$$$$$TaskPk";
	public static final String WfmAppAttr_ExeAction = "$$$$$$$$EXEACTION";
	public static final String WfmAppAttr_Opinion = "$$$$$$$$Opinion";
	public static final String WfmAppAttr_TransmitUserPk = "$$$$$$$$transmituserpk";
	public static final String WfmAppAttr_AfterAddSignUserPks = "$$$$$$$$afteraddsignuserpks";
	/**
	 * 流程平台中URL中的常量
	 */
	public static final String WfmUrlConst_TaskPk = "taskPk";
	public static final String WfmUrlConst_FlowTypePk = "flowTypePk";
	public static final String WfmUrlConst_Model = "model";
	/**
	 * 
	 */
	public static final String WfmDataset_DsHUMACT = "ds_humact";
	public static final String WfmDataset_DsSelectedActors = "ds_selecteds";
	public static final String WfmDataset_DsAllActors = "ds_allactors";
	public static final String WfmDataset_DsConst = "ds_const";
	public static final String WfmDataset_DsDate = "ds_date";
	public static final String WfmDataset_DsCode = "ds_code";
	public static final String WfmDataset_DsElem = "ds_elem";
	public static final String WfmDataset_DsRule = "ds_rule";
	/**
	 * 流程平台缓冲常量
	 */
	public static final String WfmCache_MailTaskList = "$cache_mailtasklist";
	/**
	 * 流程平台消息提醒方式
	 */
	public static final String RemindType_Mail = "mail";
	public static final String RemianType_Phone = "phone";
	public static final String RemianType_InnerMessage = "inner_message";
	/**
	 * 流程平台制单规则常量
	 */
	public static final String FrmNumBillType_SerialCode = "SerialCode";
	public static final String FrmNumBillType_Const = "Const";
	public static final String FrmNumBillType_CurrentDate = "CurrentDate";
	public static final String FrmNumFillType_Pre = "fillpre";
	public static final String FrmNumFillType_After = "fillaft";
	public static final String FrmNumCodeType_Pre = "codepre";
	public static final String FrmNumCodeType_Aft = "codeaft";
	public static final String TabCtrlState[] = { "undne", "cmplt", "ended", "sended", "undeliver", "delivered" };
}
