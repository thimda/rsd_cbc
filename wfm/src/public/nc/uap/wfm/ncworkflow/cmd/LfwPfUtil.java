package nc.uap.wfm.ncworkflow.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pf.pub.PfDataCache;
import nc.itf.uap.pf.IPFConfig;
import nc.itf.uap.pf.IWorkflowAdmin;
import nc.itf.uap.pf.IWorkflowMachine;
import nc.itf.uap.pf.IplatFormEntry;
import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.md.MDBaseQueryFacade;
import nc.md.data.access.NCObject;
import nc.md.model.IComponent;
import nc.md.model.MetaDataException;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.ui.trade.businessaction.IPFACTION;
import nc.vo.pf.change.PfUtilBaseTools;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.pf.AssignableInfo;
import nc.vo.pub.pf.PfAddInfo;
import nc.vo.pub.pf.workflow.IPFActionName;
import nc.vo.pub.workflownote.WorkflownoteVO;
import nc.vo.sm.UserVO;
import nc.vo.uap.pf.OrganizeUnit;
import nc.vo.uap.pf.PfProcessBatchRetObject;
import nc.vo.wfengine.definition.IApproveflowConst;
import nc.vo.wfengine.pub.WFTask;
import nc.vo.wfengine.pub.WfTaskType;

/**
 * 轻量级流程中心类
 * @author gd 2010-4-22
 * @version NC6.0
 * @since NC6.0
 */
public class LfwPfUtil {
	/**
	 * 审批变量如果审批则true反之false;
	 */
	private static boolean m_checkFlag = true;

	// 当前单据类型
	private static String m_currentBillType = null;

	/** 当前审批节点的审批结果 */
	private static int m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;

	private static boolean m_isOk = false;

	/** 判断当前动作是否执行成功 */
	private static boolean m_isSuccess = true;

	/** 源单据类型 */
	private static String m_sourceBillType = null; 
	
	private static AggregatedValueObject m_tmpRetVo = null;

	private static AggregatedValueObject[] m_tmpRetVos = null;
	
	public static String ASSIGNPKS = "$assignPks";
	
	public static String APPROVEINFO = "$approveinfo";
	
	//驳回到某个活动的id
	public static String REJECTACTIVITYID = "$rejectactivityid";
	
	public static String APPROVEMESSAGE= "$approvemessage";
	
	public static String USERVOS = "$uservos";
	
	public static String AGGVO = "$aggvo";
	
	public static String MASTERDS = "masterDs";
	
	public static String DETAILDSS = "detailDss";
	
	public static String PARENTWIDGETID = "parentWidgetId";
	
	public static String BILLTYPE = "$billtype";
	
	//指派中所用变量
	public static String LEFTDS = "leftds";
	public static String RIGTHDS = "rightds";
	public static String USERNAME = "user_code";
	public static String USERCODE = "user_name";
	public static String CUSERID = "cuserid";
	//指派widget
	public static String ASSINWIDGET = "assign";
	//流程widget
	public static String WORKFLOWWIDGET = "workflow";
	//主widget
	public static String MAINWIDGET = "main";

	// 单据自制标志
	public static boolean makeFlag = false;

	private LfwPfUtil() {
	}
	
	/**
	 * 流程的"保存"操作
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @throws Exception
	 */
	public static void write(AggregatedValueObject billVO, String billType, Object userObj, String[] pk_users) throws Exception {
		runAction(IPFACTION.WRITE, billType, billVO, userObj, null, null, null, pk_users);
	}
	
	/**
	 * 流程"提交"操作
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	public static Object commit(AggregatedValueObject billVO, String billType, Object userObj, HashMap eParam, String[] pk_users) throws Exception {
		setBillMaker(billType, billVO);
		Object al = (Object) runAction(IPFACTION.COMMIT, billType, billVO, userObj, null, null, eParam, pk_users);
		return al;
	}
	
	/**
	 * 流程"审批"操作
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	public static Object approve(AggregatedValueObject billVO, String billType, Object userObj, String[] pk_users) throws Exception {
		// 设置审核人和审核日期
		setCheckManAndDate(billType, billVO);
		return (Object) runAction(IPFACTION.APPROVE, billType, billVO, userObj, null, null, null, pk_users);
	}
	
	/**
	 * 流程"弃审"操作
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	public static Object unapprove(AggregatedValueObject billVO, String billType, Object userObj, String[] pk_users) throws Exception {
		setBillMaker(billType, billVO);
		return (Object) runAction(IPFACTION.UNAPPROVE, billType, billVO, userObj, null, null, null, pk_users);
	}
	
	
	
	/**
	 * 收回操作
	 * @param billVO
	 * @param billType
	 * @param uesrObj
	 * @return
	 * @throws Exception
	 */
	public static Object reCallFlow(String actionType, AggregatedValueObject billVO, String billType, Object userObj, HashMap eParam) throws Exception{
		if(actionType == null)
			actionType = IPFACTION.RECALL;
		return (Object) runAction(actionType, billType, billVO, userObj, null, null, eParam,  null);
	}
	
	
	/**
	 * 改派
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	public static void appintWorkitem(AggregatedValueObject billVO, String billType, Object userObj, String userID) throws Exception{
		IWorkflowAdmin workflowAdmin = NCLocator.getInstance().lookup(IWorkflowAdmin.class);
		String billId = billVO.getParentVO().getPrimaryKey();
		String checkman = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		WorkflownoteVO worknoteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(IPFACTION.APPROVE, billType, billVO, null);
		if(worknoteVO.getPk_checkflow() == null)
			throw new LfwRuntimeException("没有可以改派的单据!");
		workflowAdmin.appointWorkitem(billId, worknoteVO.getPk_checkflow(), checkman, userID);
	}
	
	/**
	 * 加签
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @param userID
	 * @throws Exception
	 */
	public static void addSign(AggregatedValueObject billVO, String billType, Object userObj, List userList) throws Exception{
		IWorkflowAdmin workflowAdmin = NCLocator.getInstance().lookup(IWorkflowAdmin.class);
		WorkflownoteVO worknoteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(IPFACTION.APPROVE, billType, billVO, null);
		worknoteVO.setExtApprovers(userList);
		workflowAdmin.addApprover(worknoteVO);
	}
	
	
	/**
	 * 添加参数
	 * @param eParam
	 * @param paramKey
	 * @param value
	 * @return
	 */
	private static HashMap putParam(HashMap eParam, String paramKey, Object value) {
		if(eParam == null) {
			eParam = new HashMap();
		}
		eParam.put(paramKey, value);
		return eParam;
	}

	
	/**
	 * 执行审核动作
	 * @param billVOs
	 * @param billType
	 * @param userObjs
	 * @param eParam
	 * @return
	 */
	public static Object processBatchAction(AggregatedValueObject[] billVOs, String billType, Object[] userObjs, HashMap eParam) throws Exception{
		return processBatch(IPFACTION.APPROVE, billVOs, billType, userObjs, eParam);
	}
	/**
	 * 批审
	 * @param actionCode
	 * @param billVOs
	 * @param billType
	 * @param userObjs
	 * @param eParam
	 * @return
	 * @throws Exception
	 */
	public static Object processBatch(String actionCode, AggregatedValueObject[] billVOs, String billType, Object[] userObjs, HashMap eParam) throws Exception{
		// 2.查看扩展参数，是否要流程交互处理
		WorkflownoteVO workflownote = null;//(WorkflownoteVO)getParamFromMap(eParam, PfUtilBaseTools.PARAM_WORKNOTE);
//		if(workflownote == null){
		long start = System.currentTimeMillis();
		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
			if (paramNoflow == null && paramSilent == null) {
				//XXX:guowl,2010-5,批审时，审批处理对话框上只显示批准、不批准、驳回，故传入一个参数用于甄别
				//如果传人的VO数组长度>1，调用批审
				if (billVOs != null && billVOs.length > 1) {
					eParam = putParam(eParam, PfUtilBaseTools.PARAM_BATCH, PfUtilBaseTools.PARAM_BATCH);
				}
				if (PfUtilBaseTools.isSaveAction(actionCode, billType) || PfUtilBaseTools.isApproveAction(actionCode, billType)) {
					//审批流交互处理
					workflownote = actionAboutApproveflow(actionCode, billType, billVOs[0], eParam);
					setAssign(workflownote, null, userObjs);
					if (!m_isSuccess)
						return null;
				} else if (PfUtilBaseTools.isStartAction(actionCode, billType) || PfUtilBaseTools.isSignalAction(actionCode, billType)) {
					//工作流交互处理
					workflownote = actionAboutWorkflow(actionCode, billType, billVOs[0], eParam);
					if (!m_isSuccess)
						return null;
				}
//				putParam(eParam, PfUtilBaseTools.PARAM_WORKNOTE, workflownote);
			}
		//}
			
		// 3.后台批处理动作
		Logger.debug("*后台动作批处理 开始");
		Object retObj = NCLocator.getInstance().lookup(IplatFormEntry.class).processBatch(actionCode,
				billType, workflownote, billVOs, userObjs, eParam);
		Logger.debug("*单据动作批处理 结束=" + (System.currentTimeMillis() - start) + "ms");
		
		return (PfProcessBatchRetObject)retObj;
	}
	
	
	
	/**
	 * 设置审核人和审批日期
	 * @param vo
	 */
	private static void setCheckManAndDate(String billType, AggregatedValueObject aggVo)
	{
		IFlowBizItf flowBizImpl = getFlowBizImplByMdComp(billType, aggVo);
		if(flowBizImpl != null){
			// 放入审批人
			//flowBizImpl.setApprover(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			// 放入审批时间
//			if(LfwRuntimeEnvironment.getLfwSessionBean() instanceof NcSessionBean){
//				NcSessionBean ncSessionBean = (NcSessionBean) LfwRuntimeEnvironment.getLfwSessionBean();
//				if(ncSessionBean.getChageTime() != null){
//					flowBizImpl.setApproveDate(new UFDateTime(ncSessionBean.getChageTime()));
//					UserExit.getInstance().setBizDateTime(new UFDateTime(ncSessionBean.getChageTime()).getMillis());
//				}
//				else{
//					flowBizImpl.setApproveDate(new UFDateTime());
//					UserExit.getInstance().setBizDateTime(new UFDateTime().getMillis());
//				}
//			}
//			else if(LfwRuntimeEnvironment.getLfwSessionBean() != null){
//				UserExit.getInstance().setBizDateTime(new UFDateTime().getMillis());
//			}
		}
		
	}
	
	/**
	 * 设置制单人
	 * @param billType
	 */
	private static void setBillMaker(String billType, AggregatedValueObject aggVo)
	{
		IFlowBizItf flowBizImpl = getFlowBizImplByMdComp(billType, aggVo);
		if(flowBizImpl != null)
		{
			flowBizImpl.setBillMaker(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			flowBizImpl.setBilltype(billType);
		}
	}
	
	/**
	 * 根据单据类型获取元数据组件
	 * @param billType
	 * @return
	 */
	public static IComponent getMdCompByBillType(String billType) 
	{
		BilltypeVO typeVo = PfDataCache.getBillType(billType);
		String componentName = typeVo.getComponent();
		if(componentName == null)
			throw new LfwRuntimeException("根据单据类型" + billType + "未获得元数据组件!");
		
		try {
			return MDBaseQueryFacade.getInstance().getComponentByName(componentName);
		} catch (MetaDataException e) {
			Logger.error(e, e);
			throw new LfwRuntimeException(e);
		}
	}
	
	/**
	 * 获取元数据主实体所实现的流程业务实现类
	 * @param mdComp
	 * @return
	 */
	public static IFlowBizItf getFlowBizImplByMdComp(String billType, AggregatedValueObject aggvo)
	{
		IComponent comp = getMdCompByBillType(billType);
		NCObject ncobj = NCObject.newInstance(comp.getPrimaryBusinessEntity(), aggvo);
		IFlowBizItf flowItf = ncobj.getBizInterface(IFlowBizItf.class);
		return flowItf;
	}

	/**
	 * 提交单据时,需要的指派信息
	 * <li>只有"SAVE","EDIT"动作才调用
	 */
	private static WorkflownoteVO checkOnSave(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO worknoteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class)
				.checkWorkFlow(actionName, billType, billVo, hmPfExParams);

		if (worknoteVO != null) {
			// 得到可指派的输入数据
			Vector assignInfos = worknoteVO.getTaskInfo().getAssignableInfos();
			if (assignInfos != null && assignInfos.size() > 0) {
				// 显示指派对话框并收集实际指派信息
//				DispatchDialog dd = new DispatchDialog(parent);
//				dd.initByWorknoteVO(worknoteVO);
//				int iClose = dd.showModal();
//				if (iClose == UIDialog.ID_CANCEL)
//					dlgResult.push(new Integer(iClose));
			}
		}
		return worknoteVO;
	}
	
	/**
	 * 单据启动工作流时,需要的指派信息
	 * <li>包括选择后继活动参与者、选择后继分支转移
	 */
	private static WorkflownoteVO checkOnStart(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO wfVo = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(
				actionName, billType, billVo, hmPfExParams);

//		if (wfVo != null) {
//			// 得到可指派的信息
//			Vector assignInfos = wfVo.getTaskInfo().getAssignableInfos();
//			Vector tSelectInfos = wfVo.getTaskInfo().getTransitionSelectableInfos();
//			if (assignInfos.size() > 0 || tSelectInfos.size() > 0) {
//				// 显示指派对话框并收集实际指派信息
//				WFStartDispatchDialog wfdd = new WFStartDispatchDialog(parent, wfVo, billVo);
//				int iClose = wfdd.showModal();
//				if (iClose == UIDialog.ID_CANCEL)
//					dlgResult.push(new Integer(iClose));
//			}
//		}
		return wfVo;
	}
	
	/**
	 * 检查当前单据是否处于工作流程中或工作流的审批子流程中，并进行交互
	 */
	private static WorkflownoteVO checkWorkitemWhenSignal(String actionCode,
			String billType, AggregatedValueObject billVo, HashMap hmPfExParams) throws BusinessException {
//		WorkflownoteVO noteVO = null;
//		UIDialog dlg = null;
//		try {
//			//检查当前用户的工作流工作项+审批子流程工作项
//			noteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(actionCode,
//					billType, billVo, hmPfExParams);
//			if (noteVO == null) {
//				//m_checkFlag = true;
//				return noteVO;
//			} else {
//				//XXX:guowl+,检查是否弹出交互界面
//				if(!isExchange(noteVO.getTaskInfo().getTask())) {
//					m_checkFlag = true;
//					return noteVO;
//				}
//				
//				if (noteVO.getWorkflow_type() == WorkflowTypeEnum.SubWorkApproveflow.getIntValue()) 
//				{
//					//工作流的审批子流程
//					//dlg = new ApproveWorkitemAcceptDlg(parent, noteVO, true);
//				} 
//				//else
//					//工作流或工作子流程
//					//dlg = new WorkflowWorkitemAcceptDlg(parent, noteVO, billVo);
//
//				if (dlg.showModal() == UIDialog.ID_OK) {
//					// 返回处理后的工作项
//					m_checkFlag = true;
//				} else {
//					// 用户取消
//					m_checkFlag = false;
//					noteVO = null;
//				}
//			}
//		} finally {
//			if (dlg != null) {
//				nc.ui.pub.beans.UIComponentUtil.removeAllComponentRefrence(dlg);
//			}
//		}
//		return noteVO;
		return null;
	}
//	
//	private static boolean isExchange(WFTask task)throws BusinessException {
//		try {
//			BasicWorkflowProcess wf = PfDataCache.getWorkflowProcess(task.getWfProcessDefPK());
//			return CoreUtilities.isExchange(wf.findActivityByID(task.getActivityID()));
//		} catch (XPDLParserException e) {
//			Logger.error(e.getMessage(), e);
//			throw new BusinessException(e);
//		}
//	}
	
//	/**
//	 * 检查当前单据是否处于审批流程中，并进行交互
//	 */
	private static WorkflownoteVO checkWorkitemWhenApprove(String actionName,
			String billType, AggregatedValueObject billVo, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO noteVO = null;
		//ApproveWorkitemAcceptDlg dlg = null;
		try {
			noteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(actionName,
					billType, billVo, hmPfExParams);
			if (noteVO == null) {
				m_checkFlag = true;
				return noteVO;
			} else {
//				dlg = new ApproveWorkitemAcceptDlg(parent, noteVO, false);
//				if (dlg.showModal() == UIDialog.ID_OK) { // 如果用户审批
//					// 返回审批的工作项
//					m_checkFlag = true;
//					//wfVo = clientWorkFlow.getWorkFlow();
//				} else { // 用户不审批
//					m_checkFlag = false;
//					noteVO = null;
//				}
			}
		} finally {
//			if (dlg != null) {
//				nc.ui.pub.beans.UIComponentUtil.removeAllComponentRefrence(dlg);
//			}
		}
		return noteVO;
	}
//	
//	/**
//	 * 参照来源单据，用于
//	 * <li>1.获取来源单据的查询对话框，并查询出来源单据
//	 * <li>2.显示来源单据，并进行选择
//	 * <li>3.获取选择的来源单据
//	 * 
//	 * @param srcBillType  参照制单 选择的来源单据类型
//	 * @param pk_group
//	 * @param userId
//	 * @param currBillOrTranstype 当前单据或交易类型
//	 * @param parent
//	 * @param userObj
//	 * @param srcBillId 如果为空，则直接进入参照上游单据界面
//	 */
//	public static void childButtonClicked(String srcBillType, String pk_group, String userId,
//			String currBillOrTranstype, Container parent, Object userObj, String srcBillId) {
//
//		makeFlag = false;
//		if (srcBillType.toUpperCase().equals("MAKEFLAG")) {
//			Logger.debug("******自制单据******");
//			makeFlag = true;
//			return;
//		}
//
//		Logger.debug("******参照来源单据******");
//		try {
//			String funNode = PfUIDataCache.getBillType(srcBillType).getNodecode();
//			if (funNode == null || funNode.equals(""))
//				throw new PFBusinessException("请注册单据" + srcBillType + "的功能节点号");
//
//			//查询交换信息，以便得到一些参照制单的定制信息
//			String src_qrytemplate = null;
//			String src_billui = null; // 来源单据的显示UI类
//			String src_nodekey = null; // 用于查询来源单据的查询模板的节点标识
//			ExchangeVO changeVO = PfUtilBaseTools.queryMostSuitableExchangeVO(srcBillType,
//					currBillOrTranstype, null, PfUtilUITools.getLoginGroup(), null);
//			if (changeVO != null) {
//				src_qrytemplate = changeVO.getSrc_qrytemplate();
//				src_qrytemplate = src_qrytemplate == null ? null : src_qrytemplate.trim();
//				src_billui = changeVO.getSrc_billui();
//				src_nodekey = changeVO.getSrc_nodekey();
//			}
//			// a.获取来源单据的查询对话框，即为m_condition赋值
//			IBillReferQuery qcDLG = null;
//			if (StringUtil.isEmptyWithTrim(src_qrytemplate)) {
//				Logger.debug("使用来源单据类型对应节点使用的查询模板对话框");
//				src_qrytemplate = PfUtilUITools.getTemplateId(ITemplateStyle.queryTemplate, pk_group,
//						funNode, userId, src_nodekey);
//				//qcDLG = setConditionClient(src_qrytemplate, parent, userId, funNode, pk_group);
//			} else if (src_qrytemplate.startsWith("<")) {
//				Logger.debug("使用产品组定制的来源单据查询对话框");
//				src_qrytemplate = src_qrytemplate.substring(1, src_qrytemplate.length() - 1);
////				qcDLG = loadUserQuery(parent, src_qrytemplate, pk_group, userId, funNode,
////						currBillOrTranstype, srcBillType, src_nodekey, userObj);
//			} else {
//				Logger.debug("使用注册的查询模板的查询对话框");
//				//qcDLG = setConditionClient(src_qrytemplate, parent, userId, funNode, pk_group);
//			}
//
//			if (srcBillId == null) {
//				//b.显示来源单据的查询对话框
//				if (qcDLG.showModal() == UIDialog.ID_OK) {
//					// c.显示来源单据，并进行选择
//					refBillSource(pk_group, funNode, userId, currBillOrTranstype, parent, userObj,
//							srcBillType, src_qrytemplate, src_billui, src_nodekey, null, qcDLG);
//
//				} else {
//					m_isOk = false;
//					return;
//				}
//			} else {
//				//b'.直接显示来源单据
//				refBillSource(pk_group, funNode, userId, currBillOrTranstype, parent, userObj, srcBillType,
//						src_qrytemplate, src_billui, src_nodekey, srcBillId, qcDLG);
//				return;
//			}
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			MessageDialog.showErrorDlg(parent, "错误", "参照上游单据出现异常=" + ex.getMessage());
//		}
//	}
//	
//	/**
//	 * 参照制单时，显示来源单据；并进行VO交换
//	 */
//	private static void refBillSource(String pk_group, String funNode, String pkOperator,
//			String currBillOrTranstype, Container parent, Object userObj, String billType,
//			String strQueryTemplateId, String src_billui, String srcNodekey, String sourceBillId,
//			IBillReferQuery qcDLG) throws Exception {
//		// 获取主表的关键字段
//		String pkField = PfUtilBaseTools.findPkField(billType);
//		if (pkField == null || pkField.trim().length() == 0)
//			throw new PFBusinessException("无法通过单据类型获取单据实体的主表PK字段");
//		String whereString = null;
//		if (sourceBillId == null) {
//			whereString = qcDLG.getWhereSQL();
//		} else
//			whereString = pkField + "='" + sourceBillId + "'";
//
//		//载入来源单据展现对话框，并显示
//		BillSourceVar bsVar = new BillSourceVar();
//		bsVar.setBillType(billType);
//		bsVar.setCurrBillOrTranstype(currBillOrTranstype);
//		bsVar.setFunNode(funNode);
//		bsVar.setNodeKey(srcNodekey);
//		bsVar.setPk_group(pk_group);
//		bsVar.setPkField(pkField);
//		bsVar.setQryTemplateId(strQueryTemplateId);
//		bsVar.setUserId(pkOperator);
//		bsVar.setUserObj(userObj);
//		bsVar.setWhereStr(whereString);
//
//		AbstractBillSourceDLG bsDlg = null;
//		if (!StringUtil.isEmptyWithTrim(src_billui)) {
//			Logger.debug("产品组定制的来源单据展现对话框，必须继承自" + AbstractBillSourceDLG.class.getName());
//			//bsDlg = loadCustomBillSourceDLG(src_billui, bsVar, parent);
//		} else {
//			Logger.debug("使用通用的来源单据展现对话框");
//			bsDlg = new BillSourceDLG(parent, bsVar);
//		}
//
//		bsDlg.setQueyDlg(qcDLG); //放入查询模板对话框
//		// 加载模版
//		bsDlg.addBillUI();
//		// 加载数据
//		bsDlg.loadHeadData();
//
//		if (bsDlg.showModal() == UIDialog.ID_OK) {
//			m_sourceBillType = billType;
//			m_currentBillType = currBillOrTranstype;
//			m_tmpRetVo = bsDlg.getRetVo();
//			m_tmpRetVos = bsDlg.getRetVos();
//			m_isOk = true;
//		} else {
//			m_isOk = false;
//		}
//	}
	
	/**
	 * 返回 当前审批节点的处理结果 lj+ 2005-1-20
	 */
	public static int getCurrentCheckResult() {
		return m_iCheckResult;
	}

	/**
	 * 返回 用户选择的VO
	 */
	public static AggregatedValueObject getRetOldVo() {
		return m_tmpRetVo;
	}

	/**
	 * 返回 用户选择VO数组.
	 */
	public static AggregatedValueObject[] getRetOldVos() {
		return m_tmpRetVos;
	}

//	/**
//	 * 返回 用户选择的VO或交换过的VO
//	 * 
//	 * @return
//	 */
//	public static AggregatedValueObject getRetVo() {
//		try {
//			// 需要进行VO交换
//			m_tmpRetVo = PfUtilUITools.runChangeData(m_sourceBillType, m_currentBillType, m_tmpRetVo);
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			throw new PFRuntimeException("VO交换错误：" + ex.getMessage(), ex);
//		}
//		return m_tmpRetVo;
//	}

//	/**
//	 * 返回 用户选择的VO或交换过的VO
//	 * 
//	 * @return
//	 */
//	private static AggregatedValueObject[] changeVos(AggregatedValueObject[] vos) {
//		AggregatedValueObject[] tmpRetVos = null;
//
//		try {
//			tmpRetVos = PfUtilUITools.runChangeDataAry(m_sourceBillType, m_currentBillType, vos);
//		} catch (BusinessException ex) {
//			Logger.error(ex.getMessage(), ex);
//			throw new PFRuntimeException("VO交换错误：" + ex.getMessage(), ex);
//		}
//
//		return tmpRetVos;
//	}
//
//	/**
//	 * 返回 用户选择VO数组或交换过的VO数组
//	 * 
//	 * @return
//	 */
//	public static AggregatedValueObject[] getRetVos() {
//		// 需要进行VO交换
//		m_tmpRetVos = changeVos(m_tmpRetVos);
//		return m_tmpRetVos;
//	}

	/**
	 * 判断用户是否点击了＂取消＂按钮
	 * 
	 * @return boolean leijun+
	 */
	public static boolean isCanceled() {
		return !m_checkFlag;
	}

	/**
	 * 返回 参照单据是否正常关闭
	 * 
	 * @return boolean
	 */
	public static boolean isCloseOK() {
		return m_isOk;
	}

	/**
	 * 返回 当前单据动作执行是否成功
	 * 
	 * @return boolean
	 */
	public static boolean isSuccess() {
		return m_isSuccess;
	}

//	/**
//	 * 载入产品组注册的来源单据显示对话框
//	 */
//	private static AbstractBillSourceDLG loadCustomBillSourceDLG(String src_billui,
//			BillSourceVar bsVar, Container parent) {
//		Class c = null;
//
//		try {
//			c = Class.forName(src_billui);
//			Class[] argCls = new Class[] { Container.class, BillSourceVar.class };
//			Object[] args = new Object[] { parent, bsVar };
//			// 实例化带参构造方法
//			Constructor cc = c.getConstructor(argCls);
//			return (AbstractBillSourceDLG) cc.newInstance(args);
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			MessageDialog.showErrorDlg(parent, "错误", "载入产品组注册的来源单据显示对话框 发生异常：" + ex.getMessage());
//		}
//		return null;
//	}

//	private static IBillReferQuery loadUserQuery(Container parent, String src_qrytemplate,
//			String pk_group, String userId, String FunNode, String currBillOrTranstype,
//			String sourceBillType, String nodeKey, Object userObj) {
//
//		Class c = null;
//		try {
//			// 先判定是否为新查询模板UI的子类
//			String qtId = PfUtilUITools.getTemplateId(ITemplateStyle.queryTemplate, pk_group, FunNode,
//					userId, nodeKey);
//			TemplateInfo ti = new TemplateInfo();
//			ti.setTemplateId(qtId);
//			ti.setPk_Org(pk_group);
//			ti.setUserid(userId);
//			ti.setCurrentCorpPk(pk_group);
//
//			c = Class.forName(src_qrytemplate);
//			Object retObj = c.getConstructor(new Class[] { Container.class, TemplateInfo.class })
//					.newInstance(new Object[] { parent, ti });
//			//对查询模版对话框的一些定制初始化
//			if (retObj instanceof IinitQueryData) {
//				((IinitQueryData) retObj).initData(pk_group, userId, FunNode, currBillOrTranstype,
//						sourceBillType, nodeKey, userObj);
//			}
//
//			return (IBillReferQuery) retObj;
//		} catch (NoSuchMethodException e) {
//			Logger.warn("找不到新查询模板UI的构造方法，继续判定是否有老查询模板的构造方法", e);
//			try {
//				// 应该为老查询模板UI的子类
//				Object retObj = c.getConstructor(new Class[] { Container.class }).newInstance(
//						new Object[] { parent });
//				//对查询模版对话框的一些定制初始化
//				if (retObj instanceof IinitQueryData) {
//					((IinitQueryData) retObj).initData(pk_group, userId, FunNode, currBillOrTranstype,
//							sourceBillType, nodeKey, userObj);
//				}
//				return (IBillReferQuery) retObj;
//			} catch (Exception ex) {
//				Logger.error(ex.getMessage(), ex);
//			}
//		} catch (Exception e) {
//			Logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
	
	public static Object runAction(String actionCode, String billOrTranstype,
			AggregatedValueObject billvo, Object userObj, String strBeforeUIClass,
			AggregatedValueObject checkVo, HashMap eParam, String[] pk_users) throws BusinessException {
		//PfUtilClient.runAction(null, actionCode, billOrTranstype, billvo, userObj, strBeforeUIClass, checkVo, eParam);
		
		long start = System.currentTimeMillis();
		//m_isSuccess = true;

		if (checkVo == null) {
			checkVo = billvo;
		}

		WorkflownoteVO worknoteVO = null;

		// 2.查看扩展参数，是否要流程交互处理
		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
		if (paramNoflow == null && paramSilent == null) {
			//需要交互处理
			if (PfUtilBaseTools.isSaveAction(actionCode, billOrTranstype) || PfUtilBaseTools.isApproveAction(actionCode, billOrTranstype)) {
				//审批流交互处理
				//dengjt_注释
				worknoteVO = actionAboutApproveflow(actionCode, billOrTranstype, billvo, eParam);
				/****加指派信息添加到上下文**********/
				setAssign(worknoteVO, pk_users, userObj);
				/**************/
				if (!m_isSuccess)
					return null;
			} else if (PfUtilBaseTools.isStartAction(actionCode, billOrTranstype)
					|| PfUtilBaseTools.isSignalAction(actionCode, billOrTranstype)) {
				//工作流互处理
				worknoteVO = actionAboutWorkflow(actionCode, billOrTranstype, billvo, eParam);
				if (!m_isSuccess)
					return null;
			}
		}

		//如果用户在交互处理时候选择加签或者改派，则无需驱动流程@2009-5
		if (worknoteVO != null
				&& (worknoteVO.getTaskInfo().getTask().isAddApprover() || worknoteVO.getTaskInfo()
						.getTask().isAppoint())) {
			m_isSuccess = false;
			return null;
		}
//		if (worknoteVO == null) {
//			//检查不到工作项，则后台无需再次检查
//			if (eParam == null)
//				eParam = new HashMap<String, String>();
//			if (paramSilent == null)
//				eParam.put(PfUtilBaseTools.PARAM_NOTE_CHECKED, PfUtilBaseTools.PARAM_NOTE_CHECKED);
//		}

		// 4.后台执行动作
		Object retObj = null;

		Logger.debug("*后台动作处理 开始");
		long start2 = System.currentTimeMillis();
		IplatFormEntry iIplatFormEntry = (IplatFormEntry) NCLocator.getInstance().lookup(
				IplatFormEntry.class.getName());
		retObj = iIplatFormEntry.processAction(actionCode, billOrTranstype, worknoteVO, billvo,
				userObj, eParam);
		Logger.debug("*后台动作处理 结束=" + (System.currentTimeMillis() - start2) + "ms");

		m_isSuccess = true;

		// 5.返回对象执行
		//retObjRun(parent, retObj);
		Logger.debug("*单据动作处理 结束=" + (System.currentTimeMillis() - start) + "ms");

		return retObj;
	}
	
	
	/**
	 * 指派信息或审批不起作用
	 * @param wfvo
	 * @param pk_users
	 * @param userObject
	 * @return
	 */
	public static void setAssign(WorkflownoteVO wfvo,String[] pk_users, Object userObject){
		if(wfvo == null)
			return;
		//设置指派信息
		if(pk_users != null){
			Vector<AssignableInfo> assInfos = wfvo.getTaskInfo().getAssignableInfos();
			for(Iterator<AssignableInfo> assit = assInfos.iterator();assit.hasNext();){
				AssignableInfo assInfo = assit.next();
				//if(key.equals(assInfo.getActivityDefId())){
					//先清除
					assInfo.getAssignedOperatorPKs().clear();
					assInfo.getOuAssignedUsers().clear();
					Map<String,OrganizeUnit> tmpMap = new HashMap<String,OrganizeUnit>();
					String[] oper = assInfo.getOperatorPKs().toArray(new String[0]);
					OrganizeUnit[] tmpOU = assInfo.getOuUsers().toArray(new OrganizeUnit[0]);
					for(int k=0; k < oper.length; k++){
						tmpMap.put(oper[k], tmpOU[k]);
					}
					for(String pk_user : pk_users){
						String[] oper2 = assInfo.getOperatorPKs().toArray(new String[0]);
						for(int m=0; m<oper2.length; m++){
							if(pk_user.equals(oper2[m])){
								assInfo.getAssignedOperatorPKs().add(pk_user);
								assInfo.getOuAssignedUsers().add(tmpMap.get(pk_user));
							}
						}
					}
				//}
			}
		}
		//设置审批信息
		Map messageMap = null;
		if(userObject instanceof Object[]){
			Object[] objs = (Object[]) userObject;
			messageMap = (Map) objs[0];
		}
		else
			messageMap = (Map) userObject;
		
		if(messageMap != null){
			String approveValue = (String) messageMap.get(LfwPfUtil.APPROVEINFO);
			if(approveValue != null){
				if(approveValue.equals("Y")){
					wfvo.setApproveresult("Y");
				}
				else if(approveValue.equals("N"))
					wfvo.setApproveresult("N");
				else if(approveValue.equals("R")){
					if(wfvo.getTaskInfo().getTask() != null) {
						wfvo.getTaskInfo().getTask().setTaskType(
								WfTaskType.Backward.getIntValue());
						if(messageMap.get(LfwPfUtil.REJECTACTIVITYID) != null){
							String rejectActivityId = (String) messageMap.get(LfwPfUtil.REJECTACTIVITYID);
							if(rejectActivityId != null){
								//如果含有驳回信息，将驳回活动ID设置
								wfvo.getTaskInfo().getTask().setJumpToActivity(rejectActivityId);
								wfvo.getTaskInfo().getTask().setBackToFirstActivity(false);
							}
						}
						else			
							//默认都是驳回到制单人
							wfvo.getTaskInfo().getTask().setBackToFirstActivity(true);
					}
					//驳回的时将approveresult的值置成R
					wfvo.setApproveresult("R");
				}
			}
			String message = (String) messageMap.get(LfwPfUtil.APPROVEMESSAGE);
			if(message != null)
				wfvo.setChecknote(message);
		}
	}
	
	
	/**
	 * 工作流相关的交互处理
	 * @throws BusinessException 
	 */
	private static WorkflownoteVO actionAboutWorkflow(String actionName,
			String billType, AggregatedValueObject billvo, HashMap eParam) throws BusinessException {
		WorkflownoteVO worknoteVO = null;

		if (PfUtilBaseTools.isStartAction(actionName, billType)) {
			Logger.debug("*启动动作=" + actionName + "，检查工作流");
			Stack dlgResult = new Stack();
			worknoteVO = checkOnStart(actionName, billType, billvo, dlgResult, eParam);
			if (dlgResult.size() > 0) {
				m_isSuccess = false;
				Logger.debug("*用户指派时点击了取消，则停止启动工作流");
			}
		} 
		else if (PfUtilBaseTools.isSignalAction(actionName, billType)) {
			Logger.debug("*执行动作=" + actionName + "，检查工作流");
			// 检查该单据是否处于工作流中
			worknoteVO = checkWorkitemWhenSignal(actionName, billType, billvo, eParam);
			if (worknoteVO != null) {
				if ("Y".equals(worknoteVO.getApproveresult())) {
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;
				} else if("R".equals(worknoteVO.getApproveresult())) {
					//现在驳回单独处理，result是"R"的才处理
					WFTask currTask = worknoteVO.getTaskInfo().getTask();
					if (currTask != null && currTask.getTaskType() == WfTaskType.Backward.getIntValue()) {
						if (currTask.isBackToFirstActivity())
							m_iCheckResult = IApproveflowConst.CHECK_RESULT_REJECT_FIRST;
						else
							m_iCheckResult = IApproveflowConst.CHECK_RESULT_REJECT_LAST;
					}
				} else
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_NOPASS;
			}
			
			//
			
			else if (!m_checkFlag) {
				m_isSuccess = false;
				Logger.debug("*用户驱动工作流时点击了取消，则停止执行工作流");
			}
		}
		return worknoteVO;
	}
//	
//	/**
//	 * 审批流相关的交互处理
//	 * @throws BusinessException 
//	 */
	private static WorkflownoteVO actionAboutApproveflow(String actionName,
			String billType, AggregatedValueObject billvo, HashMap eParam) throws BusinessException {
		WorkflownoteVO worknoteVO = null;

		if (PfUtilBaseTools.isSaveAction(actionName, billType)) {
			Logger.debug("*提交动作=" + actionName + "，检查审批流");
			// 如果为提交动作，可能需要收集提交人的指派信息，这里统一动作名称 lj@2005-4-8
			Stack dlgResult = new Stack();
			worknoteVO = checkOnSave(IPFActionName.SAVE, billType, billvo, dlgResult, eParam);
			if (dlgResult.size() > 0) {
				m_isSuccess = false;
				Logger.debug("*用户指派时点击了取消，则停止送审");
			}
		} else if (PfUtilBaseTools.isApproveAction(actionName, billType)) {
			Logger.debug("*审批动作=" + actionName + "，检查审批流");
			// 检查该单据是否处于审批流中，并收集审批人的审批信息
			worknoteVO = checkWorkitemWhenApprove(actionName, billType, billvo, eParam);
			if (worknoteVO != null) {
				if ("Y".equals(worknoteVO.getApproveresult())) {
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;
				} else if("R".equals(worknoteVO.getApproveresult())) {
					//现在驳回单独处理，result是"R"的才处理
					WFTask currTask = worknoteVO.getTaskInfo().getTask();
					if (currTask != null && currTask.getTaskType() == WfTaskType.Backward.getIntValue()) {
						if (currTask.isBackToFirstActivity())
							m_iCheckResult = IApproveflowConst.CHECK_RESULT_REJECT_FIRST;
						else
							m_iCheckResult = IApproveflowConst.CHECK_RESULT_REJECT_LAST;
					}
				} else
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_NOPASS;
			}else if (!m_checkFlag) {
				m_isSuccess = false;
				Logger.debug("*用户审批时点击了取消，则停止审批");
			}
		}
		return worknoteVO;
	}
	
	
	/**
	 * 获取指派信息
	 * @param actionName
	 * @param billType
	 * @param billVo
	 * @param dlgResult
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	public static UserVO[] getAssignUsers(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException{
		List<UserVO> userList = new ArrayList<UserVO>();
		UserVO[] userVOs = null;
		setBillMaker(billType, billVo);
		WorkflownoteVO pfutiVO  = null;
		if(PfUtilBaseTools.isApproveAction(actionName, billType))
			pfutiVO = checkOnApproveSave(actionName, billType, billVo, dlgResult, hmPfExParams);
		else if(PfUtilBaseTools.isSaveAction(actionName, billType))
			pfutiVO = checkOnSave(actionName, billType, billVo, dlgResult, hmPfExParams);
		if(pfutiVO == null)
			return null;
		Vector assignInfos = pfutiVO.getTaskInfo().getAssignableInfos();
		IUserManageQuery manage = NCLocator.getInstance().lookup(IUserManageQuery.class);
		if (assignInfos != null && assignInfos.size() > 0) {
			for (int i = 0; i < assignInfos.size(); i++) {
				AssignableInfo assign = (AssignableInfo) assignInfos.get(i);
				userVOs = NCLocator.getInstance().lookup(IUserManageQuery.class).findUserByIDs(assign.getOperatorPKs().toArray(new String[0]));
				for (int j = 0; j < userVOs.length; j++) {
					userList.add(userVOs[j]);
				}
			}
			// 显示指派对话框并收集实际指派信息
//			DispatchDialog dd = new DispatchDialog(parent);
//			dd.initByWorknoteVO(worknoteVO);
//			int iClose = dd.showModal();
//			if (iClose == UIDialog.ID_CANCEL)
//				dlgResult.push(new Integer(iClose));
		}
//		
		return userVOs;
	}
	
	
	/**
	 * 审核时,需要的指派信息
	 * @param actionName
	 * @param billType
	 * @param billVo
	 * @param dlgResult
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	public static WorkflownoteVO checkOnApproveSave(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO worknoteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(actionName, billType, billVo, hmPfExParams);
		return worknoteVO;
	}
	
//	
//	/**
//	 * 前台单据动作批处理API，算法如下：
//	 * <li>1.动作执行前提示以及事前处理，如果用户取消，则方法直接返回
//	 * <li>2.查看扩展参数，判断是否需要审批流相关处理。如果为提交动作，且单据VO数组中只有一张单据时可能需要收集提交人的指派信息；
//	 * 如果为审批动作，则针对第一张单据可能需要收集审批人的审批信息
//	 * <li>3.后台执行批动作。并返回动作执行结果。 
//	 * 
//	 * @param parent 父窗体
//	 * @param actionCode 动作编码，比如"SAVE"
//	 * @param billOrTranstype 单据类型（或交易类型）PK
//	 * @param voAry 单据聚合VO数组
//	 * @param userObjAry 用户自定义对象数组
//	 * @param strBeforeUIClass 前台事前处理类
//	 * @param eParam 扩展参数
//	 * @return 动作批处理的返回结果
//	 * @throws Exception
//	 * @since 5.5
//	 */
//	public static Object[] runBatch(String actionCode, String billOrTranstype,
//			AggregatedValueObject[] voAry, Object[] userObjAry, String strBeforeUIClass, HashMap eParam)
//			throws Exception {
//		Logger.debug("*单据动作批处理 开始");
//		long start = System.currentTimeMillis();
//		m_isSuccess = true;
//
//		// 1.动作执行前提示以及事前处理
//		boolean isContinue = beforeProcessBatchAction(parent, actionCode, billOrTranstype, voAry,
//				userObjAry, strBeforeUIClass);
//		if (!isContinue) {
//			Logger.debug("*动作执行前提示以及事前处理，返回");
//			m_isSuccess = false;
//			return null;
//		}
//
//		// 2.查看扩展参数，是否要流程交互处理
//		WorkflownoteVO worknoteVO = null;
//		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
//		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
//		if (paramNoflow == null && paramSilent == null) {
//			if (PfUtilBaseTools.isSaveAction(actionCode) || PfUtilBaseTools.isApproveAction(actionCode)) {
//				//审批流交互处理（XXX:只针对第一张单据VO）
//				worknoteVO = actionAboutApproveflow(actionCode, billOrTranstype, voAry[0], eParam);
//				if (!m_isSuccess)
//					return null;
//			} else if (PfUtilBaseTools.isSignalAction(actionCode)) {
//				//工作流交互处理（XXX:只针对第一张单据VO）
//				worknoteVO = actionAboutWorkflow(parent, actionCode, billOrTranstype, voAry[0], eParam);
//				if (!m_isSuccess)
//					return null;
//			}
//		}
//
//		// 3.后台批处理动作
//		Logger.debug("*后台动作批处理 开始");
//		long start2 = System.currentTimeMillis();
//		Object retObj = NCLocator.getInstance().lookup(IplatFormEntry.class).processBatch(actionCode,
//				billOrTranstype, worknoteVO, voAry, userObjAry, eParam);
//		Logger.debug("*后台动作批处理 结束=" + (System.currentTimeMillis() - start2) + "ms");
//		m_isSuccess = true;
//		Logger.debug("*单据动作批处理 结束=" + (System.currentTimeMillis() - start) + "ms");
//		return (Object[]) retObj;
//	}

//	/**
//	 * 动作执行前提示以及事前处理
//	 */
//	private static boolean beforeProcessBatchAction(Container parent, String actionName,
//			String billType, AggregatedValueObject[] voAry, Object[] userObjAry, String strBeforeUIClass)
//			throws Exception {
//
//		ActionClientParams acp = new ActionClientParams();
//		acp.setUiBeforeClz(strBeforeUIClass);
//		acp.setUiContainer(parent);
//		acp.setActionCode(actionName);
//		acp.setBillType(billType);
//		acp.setBillEntity(voAry);
//		acp.setUserObject(userObjAry);
//
//		return PfUtilUITools.beforeAction(acp);
//	}

	/**
	 * 返回某个单据或交易类型可使用的“新增”下拉菜单信息
	 * <li>包括自制、来源单据
	 * 
	 * @param billtype
	 * @param transtype 如果没有交易类型，可空 
	 * @param pk_group 某集团PK
	 * @param userId 某用户PK
	 * @param includeBillType 是否包括单据类型的来源单据信息，只用于transtype非空的情况
	 * @return
	 * @throws BusinessException 
	 */
	public static PfAddInfo[] retAddInfo(String billtype, String transtype, String pk_group,
			String userId, boolean includeBillType) throws BusinessException {

		return NCLocator.getInstance().lookup(IPFConfig.class).retAddInfo(billtype, transtype,
				pk_group, userId, includeBillType);
	}

	/**
	 * 返回某个用户对某个单据或交易类型，在某组织下 可启动的业务流程
	 * @param billtype
	 * @param transtype 如果没有交易类型，可空 
	 * @param pk_org 某组织PK
	 * @param userId 某用户PK
	 * @return
	 * @throws BusinessException
	 */
	public static String retBusitypeCanStart(String billtype, String transtype, String pk_org,
			String userId) throws BusinessException {

		return NCLocator.getInstance().lookup(IPFConfig.class).retBusitypeCanStart(billtype, transtype,
				pk_org, userId);
	}
//
//	/**
//	 * 返回某单据或交易类型配置的某动作组的所有单据动作
//	 * 
//	 * @param billOrTranstype 单据或交易类型
//	 * @param actiongroupCode 动作组编码
//	 * @return
//	 */
//	public static PfUtilBillActionVO[] getActionsOfActiongroup(String billOrTranstype,
//			String actiongroupCode) {
//		//获得单据类型(或交易类型)的单据类型PK
//		billOrTranstype = PfUtilBaseTools.getRealBilltype(billOrTranstype);
//		PfUtilBillActionVO[] billActionVos = (PfUtilBillActionVO[]) PfUIDataCache
//				.getButtonByBillAndGrp(billOrTranstype, actiongroupCode);
//		return billActionVos;
//	}
//
//	/**
//	 * 构造一个查询对话框，并为其设置查询模板
//	 * 
//	 * @param templateId
//	 * @param parent
//	 * @param isRelationCorp
//	 * @param pkOperator
//	 * @param funNode
//	 */
//	private static IBillReferQuery setConditionClient(String templateId, Container parent,
//			final String pkOperator, final String funNode, String pkCorp) {
//		TemplateInfo ti = new TemplateInfo();
//		ti.setTemplateId(templateId);
//		ti.setPk_Org(pkCorp);
//		ti.setUserid(pkOperator);
//		ti.setCurrentCorpPk(pkCorp);
//		ti.setFunNode(funNode);
//
//		QueryConditionDLG qcDlg = new QueryConditionDLG(parent, ti);
//
//		qcDlg.setVisibleNormalPanel(false);
//		return qcDlg;
//	}
	
//	/**
//	 * 根据单据类型和ILfwExAggVO转换为aggVo
//	 * @param billType
//	 * @param aggVo
//	 * @return
//	 */
//	public static AggregatedValueObject getBusiAggVo(String billType, IExAggVO aggVo)
//	{
//		IComponent comp = getMdCompByBillType(billType);
//		IBusinessEntity entity = comp.getPrimaryBusinessEntity();
//		AggregatedValueObject busiAggVo = (AggregatedValueObject) entity.getBeanStyle().newInstance(null);
//		if(busiAggVo instanceof IExAggVO)
//		{
//			IExAggVO trueBusiAggVo = (IExAggVO)busiAggVo;
//			trueBusiAggVo.setParentVO(aggVo.getParentVO());
//			String[] childCodes = aggVo.getTableCodes();
//			if(childCodes != null)
//			{
//				for (int i = 0; i < childCodes.length; i++)
//					trueBusiAggVo.setTableVO(childCodes[i], aggVo.getta(childCodes[i]));
//			}
//		}
//		else
//		{
//			busiAggVo.setParentVO(aggVo.getParentVO());
//			busiAggVo.setChildrenVO(busiAggVo.getChildrenVO());
//		}
//		return busiAggVo;
//	}
	
	public static IplatFormEntry getIplatFormEntry()
	{
		return (IplatFormEntry) NCLocator.getInstance().lookup(IplatFormEntry.class.getName());
	}
	
	public static String getCurrentDate()
	{
		return LfwRuntimeEnvironment.getLfwSessionBean().getLoginDate().toString();
	}
}
