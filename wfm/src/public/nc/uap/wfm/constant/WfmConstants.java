package nc.uap.wfm.constant;
import nc.uap.lfw.core.ContextResourceUtil;
import nc.uap.wfm.dftimpl.DefaultFormDataBuilder;
import nc.uap.wfm.dftimpl.DefaultHumActHandler;
import nc.uap.wfm.dftimpl.DefaultLogicDecision;
import nc.uap.wfm.dftimpl.DefaultProDefExt;
import nc.uap.wfm.pagemeta.MyPrtptProcessPageModel;
import nc.uap.wfm.pagemeta.MyStartProcessPageModel;
import nc.uap.wfm.pagemeta.MyWatchProcessPageModel;
import nc.uap.wfm.pagemodel.FlwDataDispPageModel;
/**
 * @author tinchw
 */
public class WfmConstants {
	// 发布单据类型：新闻，发文，公告，流程
	public static final String PublishType = "PublishType";
	public static final String IsNotOpen = "isnotopen";
	public static final String IsDisPrintOrDiver = "isdisprintordiver";
	public static final String PublishType_News = "PublishType_News";// 新闻
	public static final String PublishType_Compdoc = "PublishType_Compdoc";// 发文
	public static final String PublishType_Notice = "PublishType_Notice";// 公告
	public static final String PublishType_Form = "PublishType_Form";// 表单
	public static final String ActorSgy_AppointActor = "AppointActor";// 指定参与者
	public static final String ActorSgy_ProcessStart = "PrcessStart";// 同流程启动者
	public static final String ActorSgy_PreActAssign = "PreActAssign";// 前一个活动指派
	public static final String ActorSgy_SelfDefActor = "SelfDefActor";
	public static final String ActorSgy_SameSomeActs = "SameSomeActs";// 同某几个活动节点的权限一样
	public static final String ActorSgy_SomeActAsign = "SomeActAsign";// 由某个活动节点指派的权限
	public static final String AllotSgy_Any = "Any";// 分配给一个人
	public static final String AllotSyg_All = "All";// 分配给所有人
	public static final String CompleteSgy_Occupy = "Occupy";// 抢占策略
	public static final String CompleteSgy_Countersign = "Countersign";// 会签策略
	public static final String CompleteSgy_ByCount = "ByCount";// 按数量完成策略
	public static final String CompleteSgy_ByPercent = "ByPercent";// 按百分比完成策略
	public static final String RejectSgy_PreviousHumAct = "PreviousHumAct";// 驳回到前一个节点
	public static final String RejectSgy_StartHumAct = "StartHumAct";// 驳回到开始活动
	public static final String RejectSgy_AllHumAct = "AllHumAct";// 驳回范围为所有节点
	public static final String RejectSgy_AppointHumAct = "AppointHumAct";// 驳回到指定节点
	public static final String OverTimeRemiand_Email = "Email";// 邮件提醒
	public static final String OverTimeRemiand_Message = "Message";// 消息提醒
	public static final String OverTimeSgy_Wait = "Wait";// 继续等待
	public static final String OverTimeSgy_Rollback = "Rollback";// 活动后退
	public static final String OverTimeSgy_Suspend = "Suspend";// 流程挂起
	public static final String OverTimeSgy_Cancel = "Cancel";// 流程取消
	public static final String OverTimeSgy_Transfer = "Transfer";// 工作移交
	public static final String SelectDept = "$SelectDept";// 用户在多个部门的时候,选择其中一个部门
	public static final String StartTaskPk = "pk_starttask";
	public static final String EventSrc = "eventsrc";
	public static final String EventSrc_Mail = "eventsrc_mail";
	public static final String EventSrc_MsgSys = "eventsrc_msgsys";
	public static final String EventSrc_SubFlow = "eventsrc_subflow";
	// 消息提醒方式
	public static final String RemindType_Mail = "mail";
	public static final String RemianType_Phone = "phone";
	public static final String RemianType_InnerMessage = "inner_message";
	public static final String LogicAnd = "And";
	public static final String LogicOr = "Or";
	public static final String LogicByCountAnd = "BycountAnd";
	public static final String ActionType_Audit = "Audit";
	public static final String ActionType_LeaderAudit = "Leader_Audit";
	public static final String ActionType_Normal = "Normal";
	public static final String ActionType_Cirulated = "Cirulated";
	public static final String ActionType = "actiontype";
	public static final String Str_Agree = "Agree";
	public static final String Str_UnAgree = "noagree";
	public static final String Str_Reject = "Reject";
	public static final String Str_Transmit = "Transmit";
	public static final String Str_Next = "Next";
	public static final String Unit_Day = "天";
	public static final String Unit_Hour = "时";
	public static final String Unit_Min = "分";
	public static final String Unit_Sec = "秒";
	public static final String FlwType_News = "News";
	public static final String FlwType_SendDocument = "SendDocument";
	public static final String FlwType_ReceiveDocument = "ReceiveDocument";
	public static final String FlwType_DynamicForm = "DynamicForm";
	// public static final String FrmDefPk = "frmdefpk";
	public static final String TaskPk = "taskpk";
	public static final String FrmTmpPk = "frmtmppk";
	public static final String PProInsPk = "pproinspk";
	public static final String Model = "model";
	public static final String Mode = "mode";
	public static final String Mode_Disp = "disp";
	public static final String Mode_Detail = "detail";
	public static final String ProInsPk = "proinspk";
	public static final String TokenId = "tokenId";
	public static final String MailAuditPath = "/audit/exe";
	// 各种类型单据的pagemodel，新闻，发文，公告，表单
	public static final String FlwDataDispModel = FlwDataDispPageModel.class.getName();
	// public static final String FlwDataDetailModel =
	// FlwDataDetailPageModel.class.getName();
	public static final String ExtAttrHandlerClass = DefaultProDefExt.class.getName();
	public static final String LogicDecisionClass = DefaultLogicDecision.class.getName();
	// 各种单据类型的明细pagemodel
	public static final String MyStartProcessModel = MyStartProcessPageModel.class.getName();
	public static final String MyWatchProcessModel = MyWatchProcessPageModel.class.getName();
	public static final String DefaultFormDataBuilder = DefaultFormDataBuilder.class.getName();
	public static final String DefaultHumActHandler = DefaultHumActHandler.class.getName();
	public static final String DefaultMyPrtptPageModel = MyPrtptProcessPageModel.class.getName();
	public static final String MasterFilePk = "masterfilepk";
	public static final String Operator = "operator";
	public static final String TransmitUserPk = "transmituserpk";
	public static final String AfterAddSignUserPks = "afteraddsignuserpks";
	public static final String Opinion = "text_opinion";
	public static final String Scratchpad = "text_pad";
	public static final String TextReject = "text_reject";
	public static final String MnuItmScratchpad = "scratchpad";
	public static final String Action = "text_action";
	public static final String Channel = "text_channel";
	public static final String MsgType = "text_msgtype";
	public static final String CommonWord = "common";
	public static final String Next = "next";
	public static final String Start = "start";
	public static final String Reject = "reject";
	public static final String Interim = "interim";
	public static final String AddSignManage = "addsignmanage";
	public static final String Deliver = "deliver";
	public static final String StartSubFrm = "startsub";
	public static final String HumActId = "port_id";
	public static final String PortId = "port_id";
	public static final String HumActName = "port_name";
	public static final String ProDefId = "prodef_id";
	public static final String ParentId = "parent_id";
	public static final String ProDefPk = "pk_prodef";
	public static final String PkProDef = "pk_prodef";
	public static final String PkFrmItm = "pk_frmitm";
	public static final String PkFlwFrm = "pk_flwfrm";
	public static final String PkFlwPrt = "pk_flwprt";
	public static final String IsAssign = "isassign";
	public static final String UserPks = "userpks";
	public static final String UserNames = "usernames";
	public static final String MyVisa = "myvisa";
	public static final String FrmInsPk = "frminspk";
	public static final String FrmFlwInfo = "frm_flwinfo";
	public static final String ComBoxCommon = "combox_common";
	public static final String ComBoxMyVisa = "combox_myvisa";
	public static final String ComBoxReject = "combox_reject";
	public static final String DsUsers = "ds_users";
	public static final String PkTask = "pk_task";
	public static final String PkUser = "pk_user";
	public static final String UserName = "username";
	public static final String PrimaryKey = "primarykey";
	public static final String FormVO = "$$$$$$$$FORMVO";
	public static final String FlwTypePk = "$$$$$$$$FLWTYPEPK";
	public static final String Task = "$$$$$$$$Task";
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
	public static final String StrChSubmit = "提交";
	public static final String StrChAudit = "审核";
	public static final String StrHandler = "处理";
	public static final String StrChLeaderAudit = "签批";
	public static final String CurrentItemId = "currentTabItemId";
	public static final String BtnOk = "btn_ok";
	public static final String BtnCancel = "btn_cancel";
	public static final String BtnInterim = "btn_interim";
	public static final String Session_AttachListName = "$Session_AttachListName";
	public static final String Session_AttachFileName = "$Session_AttachFileName";
	public static final String Session_SuitPrintTmp = "$Session_SuitPrintTmp";
	public static final String StrCountersign = "countersign";
	public static final String StrSignstart = "signstart";
	public static final String RootLinkChar = "root_";
	public static final String IsNotDispWord = "isnotdispword";
	public static final String IsNotDispAttach = "isnotdispattach";
	public static final String IsNotDispHistory = "isnotdisphistory";
	public static final String IsNotDispImage = "isnotdispimage";
	public static final String IsNotDispSubFrm = "isnotdispsubfrm";
	public static final String IsNotWrite = "isnotwrite";
	// public static final String IsNotDisp = "isnotdisp";
	public static final String IsNotReadonly = "isnotreadonly";
	public static final String IsNotRequired = "isnotrequired";
	public static final String Print = "print";
	public static final String SubmitAudit = "submitoraudit";
	public static final String Back = "back";
	public static final String BackUrl = "backurl";
	public static final String AddSign = "addsign";
	public static final String Sign = "sign";
	public static final String Transmit = "transmit";
	public static final String MainForm = "mainform";
	public static final String ActionValue = "actionvalue";
	public static final String MailTaskList = "$session_mailtasklist";
	public static final String StartUp = "startup";
	public static final String PUBVIEW_EXETASK = "pubview_exetask";
	public static final String PUBVIEW_ASSIGN = "pubview_assign";
	public static final String DsHUMACT = "ds_humact";
	public static final String GRID_ALLACTORS = "grid_allactors";
	public static final String GRIDSELECTEDS = "grid_selecteds";
	public static final String RightOne = "btn_right_one";
	public static final String RightAll = "btn_rigth_all";
	public static final String LeftOne = "btn_left_one";
	public static final String LeftAll = "btn_left_all";
	public static final String Ok = "btn_ok";
	public static final String Cancel = "btn_cancel";
	public static final String UserPk = "pk_user";
	public static final String StrTrue = "true";
	public static final String StrFalse = "false";
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
	/**
	 * 页签常量
	 */
	public static final String TabData = "tab_data";
	/**
	 * 页签项常量
	 */
	public static final String TabItmFormData = "itm_data";
	public static final String TabItmWord = "itm_master";
	public static final String TabItmAttach = "itm_attach";
	public static final String TabItmImage = "itm_image";
	public static final String TabItmHistory = "itm_history";
	public static final String TabItmSubFrm = "itm_detailformdata";
	public static final String TableItmDeliver = "itm_deliver";
	public static final String TablUndneTask = "itm_undnetask";
	public static final String TabCmpltTask = "itm_cmplttask";
	public static final String TabPlmntTask = "itm_plmnttask";
	public static final String TabSendTask = "itm_sendtask";
	public static final String TabUnReadTask = "itm_unreadtask";
	public static final String TabReadedTask = "itm_readedtask";
	public static final String TabEndedTask = "itm_endedtask";
	public static final String TablUndneProIns = "itm_undneproins";
	public static final String TabCmpltProIns = "itm_cmpltproins";
	/**
	 * 菜单常量
	 */
	public static final String MenuTask = "menu_task";
	public static final String StartMenu = "menu_start";
	public static final String MenuFlwFrm = "menu_flwfrm";
	public static final String MenuAttachList = "menu_attachlist";
	public static final String MenuAttachFile = "menu_attachfile";
	/**
	 * 菜单项常量
	 */
	public static final String MnuItmDel = "delete";
	public static final String MnuItmDetail = "detail";
	public static final String MnuItmRemind = "remind";
	public static final String MnuItmSave = "save";
	public static final String MnuItmModify = "modify";
	public static final String MnuItmCancel = "cancel";
	public static final String MnuItmAddSignMgr = "addsignmanage";
	public static final String MnuItmDeliver = "deliver";
	/**
	 * 数据集常量
	 */
	public static final String DsUndneTask = "ds_undnetask";
	public static final String DsCmpltTask = "ds_cmplttask";
	public static final String DsPlmntTask = "ds_plmnttask";
	public static final String DsUndneProIns = "ds_undneproins";
	public static final String DsCmpltProIns = "ds_cmpltproins";
	public static final String DsProDef = "ds_prodef";
	public static final String DsFlwInfo = "ds_flwinfo";
	public static final String DsAssignAct = "ds_assignact";
	public static final String DsSendTask = "ds_sendtask";
	public static final String DsUndeliverTask = "ds_undelivertask";
	public static final String DsDeliveredTask = "ds_deliveredtask";
	public static final String DsEndedTask = "ds_endedtask";
	public static final String DsNextHumAct = "ds_nexthumact";
	public static final String DsRejectHumAct = "ds_prehumact";
	public static final String DsAllActors = "ds_allactors";
	public static final String DsSelectedActors = "ds_selecteds";
	public static final String DsFrmItm = "ds_frmitm";
	public static final String DsFrmData = "ds_frmdata";
	public static final String DsTaskAttr = "ds_taskattr";
	public static final String DsFlwFrm = "ds_flwfrm";
	public static final String DsFlwPrt = "ds_flwprt";
	public static final String DsAttach = "ds_attach";
	public static final String DsAttachFile = "ds_attachfile";
	public static final String DsAttachList = "ds_attachlist";
	public static final String DsChildForm = "ds_childformdata";
	public static final String DsSubProDef = "ds_subprodef";
	public static final String DsConst = "ds_const";
	public static final String DsDate = "ds_date";
	public static final String DsCode = "ds_code";
	public static final String DsElem = "ds_elem";
	public static final String DsRule = "ds_rule";
	public static final String StrNull = "~";
	public static final String SuitPrintTmpFolderPath = ContextResourceUtil.getCurrentAppPath() + "suitprint/";
	public static final String WebTmpFilePath = ContextResourceUtil.getCurrentAppPath() + "/webtemp/html/nodes/";
	public static final String ScratchpadDefaultStr = "无便签信息";
	public static final String DsOutSubFlwData = "ds_outsubflwdata";
	public static final String GridOutSubFlwData = "grid_outsubflwdata";
	/**
	 * 加签人员范围
	 */
	public static final String ADDSIGN_RANGE = "addSignRange"; // 加签人员范围
	public static final String ADDSIGN_ALL = "All"; // 全部人员
	public static final String ADDSIGN_LEADER = "Leader"; // 集团领导
	public static final String ADDSIGN_DEPTMANAGER = "Deptmanager"; // 部门主要负责人
	public static final String ADDSIGN_LEADERANDDPETMANAGER = "LeaderAndDeptManager"; // 集团领导及主要部门负责人
	public static final String ADDSIGN_NOTLEADER = "NotLeader"; // 集团领导外的人员
	public static final String ADDSIGN_SELFDEPT = "SelfDept"; // 本部门人员
	public static final String ADDSIGN_DEPTJTLD = "001"; //
	public static final String ADDSIGN_USER = "addSignUser"; // 加签人
	public static final String WfmOperator_Agree = "agree";
	public static final String WfmOperator_UnAgree = "unagree";
	public static final String WfmOperator_Reject = "reject";
	public static final String WfmOperator_Transmit = "transmit";
	/**
	 * 
	 */
	public static final String FrmNumBillType_SerialCode = "SerialCode";
	public static final String FrmNumBillType_Const = "Const";
	public static final String FrmNumBillType_CurrentDate = "CurrentDate";
	public static final String FrmNumFillType_Pre = "fillpre";
	public static final String FrmNumFillType_After = "fillaft";
	public static final String FrmNumCodeType_Pre = "codepre";
	public static final String FrmNumCodeType_Aft = "codeaft";
	public static final String TabCtrlState[] = { "undne", "cmplt", "ended", "sended", "undeliver", "delivered" };
	public static final String PwfmFrmCat[][] = {
			{ "DynamicForm", "nc.portal.pwfm.frmmgr.FrmTreeItem_DynamicForm", "nc.portal.pwfm.frmdata.FrmDataUtil_DynamicForm", "nc.portal.pwfm.frmdata.FrmDataQry_DynamicForm",
					"nc.portal.pwfm.vo.DefaultFormVO" },
			{ "PublishNews", "nc.portal.cms.frmtreeitem.FrmTreeItem_PublishNews", "nc.portal.cms.frmdataqry.FrmDataUtil_PublishNews", "nc.portal.cms.frmdataqry.FrmDataQry_PublishNews",
					"nc.vo.pub.portlet.NewsVO" },
			{ "Compdoc", "nc.portal.cms.frmtreeitem.FrmTreeItem_Compdoc", "nc.portal.cms.frmdataqry.FrmDataUtil_Compdoc", "nc.portal.cms.frmdataqry.FrmDataQry_Compdoc", "nc.vo.pub.portlet.CompdocVO" },
			{ "Notice", "nc.portal.cms.frmtreeitem.FrmTreeItem_Notices", "nc.portal.cms.frmdataqry.FrmDataUtil_Notice", "nc.portal.cms.frmdataqry.FrmDataQry_Notice", "nc.vo.pub.portlet.NoticeVO" } };
}
