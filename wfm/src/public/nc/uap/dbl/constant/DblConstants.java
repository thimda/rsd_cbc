package nc.uap.dbl.constant;
/**
 * 
 */
public class DblConstants {
	/**
	 * 动态表单中控件的属性
	 */
	public static final String BaseDataFlag = "basedatalabel";
	public static final String isYes = "Y";
	public static final String IsNotDispaly = "isnotdisplay";
	public static final String IsNotEditable = "isnoteditable";
	public static final String IsNotEnabled = "isnotenabled";
	public static final String ActivityMark = "activitymark";
	public static final String IsNotRequried = "isnotrequired";
	public static final String IsNotCheck = "isnotcheck";
	public static final String CheckFormatter = "checkformatter";
	public static final String IsNotRich = "isnotrich";
	public static final String IsNotMulti = "isnotmulti";
	public static final String DefValue = "defvalue";
	public static final String Cols = "cols";
	public static final String Rows = "rows";
	public static final String OccupyDomian = "name";
	public static final String FillMode = "fillmode";
	public static final String LengthRange = "lengthrange";
	public static final String MinLen = "minlen";
	public static final String MaxLen = "maxlen";
	public static final String Height = "height";
	public static final String Width = "width";
	public static final String Size = "size";
	public static final String NameZH = "namezh";
	public static final String Type = "type";
	public static final String Value = "value";
	public static final String TabName = "tabname";
	public static final String FieldType = "fieldtype";
	public static final String RefType = "reftype";
	public static final String GroupID = "groupid";
	public static final String StrLabel = "slabel";
	public static final String LabelandValue = "labelandvalue";
	public static final String IsNotTemp = "isnottemp";
	public static final String EnvVar = "envvar";
	public static final String EnvVarClass = "envvarclass";
	public static final String RenderClass = "renderclass";
	public static final String ContainerType = "containertype";
	public static final String ContainerId = "container_id";
	public static final String ServerClass = "serverclass";
	public static final String OrderStr = "orderstr";
	public static final String Name = "name";
	/**
	 * 动态表单中表格类型
	 */
	public static final String ContainerType_Attach = "Attach";
	public static final String ContainerType_SelfTable = "SelfTable";
	public static final String ContainerType_DynaRow = "DynaRow";
	/**
	 * 动态表单中的控件类型
	 */
	public static final String Component_Text = "text";
	public static final String Component_Checkbox = "checkbox";
	public static final String Component_Radio = "radio";
	public static final String Component_Hidden = "hidden";
	public static final String Component_TextArea = "textarea";
	public static final String Component_Select = "select";
	public static final String Component_Table = "table";
	public static final String Component_File = "file";
	public static final String Component_Ref = "Reference";
	public static final String SPLITTER = ",";
	public static final String InitFormVersion = "version_1.0";
	public static final String DefaultTableFieldType = "varchar";
	public static final String AuditControlName = "audit";
	public static final String StrVersion = "version";
	public static final String CharAcross = "_";
	/**
	 * 表结构调整过程中的动态行表的主表外键名称
	 */
	public static final String Pk_FrmIns = "pk_frmins";
	public static final String TempTableSuffix = "_TEMP";
	/**
	 * 动态表单中的ca
	 */
	public static final String FrmInsPk = "frminspk";
	public static final String FrmDefPk = "frmdefpk";
	public static final String FrmTmpPk = "frmtmppk";
	public static final String FrmCatPk = "frmcatpk";
	/**
	 * 动态表单中生成的运行表单和查看表单的文件路径
	 */
	public static final String Path_FrmTmpDisp = "frmtmpdisps";
	public static final String Path_FrmTmpView = "frmtmpviews";
	public static final String Path_FrmTmpPrint = "frmtmpprints";
	public static final String TemplateFilePath = "formTemplate.vm";
	/**
	 * 动态表单中的前缀信息
	 */
	public static final String PdblTabPkPrefix = "pk_";
	public static final String PdblPrefix = "pdbl_";
	public static final String PdblRadioPrefix = PdblPrefix + "radio_";
	public static final String PdblCheckboxPrefix = PdblPrefix + "checkbox_";
	public static final String PdblSelectPrefix = PdblPrefix + "select_";
	public static final String PdblGridPrefix = PdblPrefix + "grid_";
	public static final String PdblToolBarPrefix = PdblPrefix + "toolbar_";
	public static final String PdblDsPrefix = PdblPrefix + "ds_";
	public static final String PdblListenerPrefix = PdblPrefix + "listener_";
	public static final String ToolBarAddItmPrefix = PdblToolBarPrefix + "add_";
	public static final String ToolBarDeleteItmPrefix = PdblToolBarPrefix + "delete_";
	public static final String ToolBarUpItmPrefix = PdblToolBarPrefix + "up_";
	public static final String ToolBarDownItmPrefix = PdblToolBarPrefix + "down_";
	public static final String ToolBarUpFile = PdblToolBarPrefix + "up_file_";
	public static final String ToolBarOpenFile = PdblToolBarPrefix + "open_file_";
	public static final String ToolBarDeleteFile = PdblToolBarPrefix + "delete_file_";
	public static final String ToolBarDowLoadFile = PdblToolBarPrefix + "download_file_";
	public static final String DynaRowDsLoadListenerPrefix = PdblListenerPrefix + "dyanrow_dsload_";
	public static final String DynaRowOperatListenerPrefix = PdblListenerPrefix + "dynarow_operat_";
	public static final String AttachDsLoadListenerPrefix = PdblListenerPrefix + "attach_dsload_";
	public static final String AttachOperatListenerPrefix = PdblListenerPrefix + "attach_operat_";
	public static final String SelfTableDsLoadListenerPrefix = PdblListenerPrefix + "selftable_dsload_";
	/**
	 * 动态表单中环境变量的默认类型
	 */
	public static final String EnvVarType_CreateFrmUser = "createfrmuser";
	public static final String EnvVarType_CreateFrmDate = "createfrmdate";
	public static final String EnvVarType_CreateFrmDept = "createfrmdept";
	public static final String EvnVarType_SelfDefinition = "selfdefinition";
	/**
	 * 动态表单中的字段类型
	 */
	public static final String FieldType_String = "string";
	public static final String FieldType_Integer = "integer";
	public static final String FieldType_Decimal = "decimal";
	public static final String FieldType_Date = "date";
	public static final String fieldType_DateTime = "datetime";
	/**
	 * 动态表单中附件数据集中的字段名称
	 */
	public static final String PdblAttachPk = "pdbl_attach_pk";
	public static final String PdblAttachFileName = "pdbl_attach_filename";
	public static final String PdblAttachFileSize = "pdbl_attach_filensize";
	public static final String PdblAttachCreator = "pdbl_attach_creator";
	public static final String PdblAttachUploadTime = "pdbl_attach_uploadtime";
	public static final String[][] PdblAttachTableFiled = { { DblConstants.PdblAttachFileName, "文件名称" }, { DblConstants.PdblAttachPk, "文件标识" }, { DblConstants.PdblAttachFileSize, "文件大小" },
	{ DblConstants.PdblAttachCreator, "上传用户" }, { DblConstants.PdblAttachUploadTime, "上传时间" } };
	/**
	 * 动态表单中的默认渲染类
	 */
	public static final String DefalutPageMeta = "nc.portal.pdbl.pagemodel.FrmTmpDispPageModel";
	/**
	 * 新闻默认渲染类
	 */
	public static final String NewsDefaultPageMeta = "nc.portal.pdbl.pagemodel.NewsDispPageModel";
	/**
	 * 发文默认处理类
	 */
	public static final String CompdocDefaultPageMeta = "nc.portal.pdbl.pagemodel.CompdocDispPageModel";
	/**
	 * 公告默认处理类
	 */
	public static final String NoticeDefaultPageMeta = "nc.portal.pdbl.pagemodel.NoticeDispPageModel";
	/**
	 * 自定义异步交互中的参数名称
	 */
	public static final String Parameters = "parameters";
	public static final String Exception = "exception";
	public static final String Encoding = "UTF-8";
	public static final String RefType_User = "refuser";
	public static final String RefType_Dept = "refdept";
	/**
	 * 附件类型
	 */
	public static final String AttachFileType_Master = "0";
	public static final String AttachFileType_Attach = "1";
	public static final String StrTrue = "true";
	public static final String StrFalse = "false";
	/**
	 * 表单默认字段
	 */
	public static final String MakeFormDate = "sys_billmakedate";
	public static final String MakeFormUser = "sys_billmakeuser";
	public static final String MakeFormNumb = "sys_billmakenumb";
	public static final String MakeBillDept = "sys_billmakedept";
	public static final String FrmAuditUser = "sys_frmaudituser";
	public static final String FrmAuditDate = "sys_frmauditdate";
	public static final String FormState = "sys_formstate";
	public static final String FormDisabled = "formdisabled";
	public static final String PdblSysVarAttr[][] = { { DblConstants.MakeFormNumb, "制单号", "nc.portal.pdbl.pagemodel.SysVarMakeBillNumb" },
	{ DblConstants.MakeFormUser, "制单人", "nc.portal.pdbl.pagemodel.SysVarMakeBillUser" }, { DblConstants.MakeFormDate, "制单日期", "nc.portal.pdbl.pagemodel.SysVarMakeBillDate" },
	{ DblConstants.MakeBillDept, "制单部门", "nc.portal.pdbl.pagemodel.SysVarMakeBillDept" }, { DblConstants.FrmAuditUser, "审核人", "nc.portal.pdbl.pagemodel.SysVarFrmAuditUser" },
	{ DblConstants.FrmAuditDate, "审核日期", "nc.portal.pdbl.pagemodel.SysVarFrmAuditDate" }, { DblConstants.FrmAuditDate, "单据状态", "nc.portal.pdbl.pagemodel.SysVarFormState" } };
	public static final String PdblDefaultAttr[] = { DblConstants.MakeFormNumb, DblConstants.MakeFormUser, DblConstants.MakeFormDate, DblConstants.MakeBillDept, DblConstants.FrmAuditUser,
	DblConstants.FrmAuditDate, DblConstants.FormState, DblConstants.FormDisabled };
}
