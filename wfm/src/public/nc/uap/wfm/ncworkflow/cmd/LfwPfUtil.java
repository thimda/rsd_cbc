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
 * ����������������
 * @author gd 2010-4-22
 * @version NC6.0
 * @since NC6.0
 */
public class LfwPfUtil {
	/**
	 * �����������������true��֮false;
	 */
	private static boolean m_checkFlag = true;

	// ��ǰ��������
	private static String m_currentBillType = null;

	/** ��ǰ�����ڵ��������� */
	private static int m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;

	private static boolean m_isOk = false;

	/** �жϵ�ǰ�����Ƿ�ִ�гɹ� */
	private static boolean m_isSuccess = true;

	/** Դ�������� */
	private static String m_sourceBillType = null; 
	
	private static AggregatedValueObject m_tmpRetVo = null;

	private static AggregatedValueObject[] m_tmpRetVos = null;
	
	public static String ASSIGNPKS = "$assignPks";
	
	public static String APPROVEINFO = "$approveinfo";
	
	//���ص�ĳ�����id
	public static String REJECTACTIVITYID = "$rejectactivityid";
	
	public static String APPROVEMESSAGE= "$approvemessage";
	
	public static String USERVOS = "$uservos";
	
	public static String AGGVO = "$aggvo";
	
	public static String MASTERDS = "masterDs";
	
	public static String DETAILDSS = "detailDss";
	
	public static String PARENTWIDGETID = "parentWidgetId";
	
	public static String BILLTYPE = "$billtype";
	
	//ָ�������ñ���
	public static String LEFTDS = "leftds";
	public static String RIGTHDS = "rightds";
	public static String USERNAME = "user_code";
	public static String USERCODE = "user_name";
	public static String CUSERID = "cuserid";
	//ָ��widget
	public static String ASSINWIDGET = "assign";
	//����widget
	public static String WORKFLOWWIDGET = "workflow";
	//��widget
	public static String MAINWIDGET = "main";

	// �������Ʊ�־
	public static boolean makeFlag = false;

	private LfwPfUtil() {
	}
	
	/**
	 * ���̵�"����"����
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @throws Exception
	 */
	public static void write(AggregatedValueObject billVO, String billType, Object userObj, String[] pk_users) throws Exception {
		runAction(IPFACTION.WRITE, billType, billVO, userObj, null, null, null, pk_users);
	}
	
	/**
	 * ����"�ύ"����
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
	 * ����"����"����
	 * @param billVO
	 * @param billType
	 * @param userObj
	 * @return
	 * @throws Exception
	 */
	public static Object approve(AggregatedValueObject billVO, String billType, Object userObj, String[] pk_users) throws Exception {
		// ��������˺��������
		setCheckManAndDate(billType, billVO);
		return (Object) runAction(IPFACTION.APPROVE, billType, billVO, userObj, null, null, null, pk_users);
	}
	
	/**
	 * ����"����"����
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
	 * �ջز���
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
	 * ����
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
			throw new LfwRuntimeException("û�п��Ը��ɵĵ���!");
		workflowAdmin.appointWorkitem(billId, worknoteVO.getPk_checkflow(), checkman, userID);
	}
	
	/**
	 * ��ǩ
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
	 * ��Ӳ���
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
	 * ִ����˶���
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
	 * ����
	 * @param actionCode
	 * @param billVOs
	 * @param billType
	 * @param userObjs
	 * @param eParam
	 * @return
	 * @throws Exception
	 */
	public static Object processBatch(String actionCode, AggregatedValueObject[] billVOs, String billType, Object[] userObjs, HashMap eParam) throws Exception{
		// 2.�鿴��չ�������Ƿ�Ҫ���̽�������
		WorkflownoteVO workflownote = null;//(WorkflownoteVO)getParamFromMap(eParam, PfUtilBaseTools.PARAM_WORKNOTE);
//		if(workflownote == null){
		long start = System.currentTimeMillis();
		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
			if (paramNoflow == null && paramSilent == null) {
				//XXX:guowl,2010-5,����ʱ����������Ի�����ֻ��ʾ��׼������׼�����أ��ʴ���һ�������������
				//������˵�VO���鳤��>1����������
				if (billVOs != null && billVOs.length > 1) {
					eParam = putParam(eParam, PfUtilBaseTools.PARAM_BATCH, PfUtilBaseTools.PARAM_BATCH);
				}
				if (PfUtilBaseTools.isSaveAction(actionCode, billType) || PfUtilBaseTools.isApproveAction(actionCode, billType)) {
					//��������������
					workflownote = actionAboutApproveflow(actionCode, billType, billVOs[0], eParam);
					setAssign(workflownote, null, userObjs);
					if (!m_isSuccess)
						return null;
				} else if (PfUtilBaseTools.isStartAction(actionCode, billType) || PfUtilBaseTools.isSignalAction(actionCode, billType)) {
					//��������������
					workflownote = actionAboutWorkflow(actionCode, billType, billVOs[0], eParam);
					if (!m_isSuccess)
						return null;
				}
//				putParam(eParam, PfUtilBaseTools.PARAM_WORKNOTE, workflownote);
			}
		//}
			
		// 3.��̨��������
		Logger.debug("*��̨���������� ��ʼ");
		Object retObj = NCLocator.getInstance().lookup(IplatFormEntry.class).processBatch(actionCode,
				billType, workflownote, billVOs, userObjs, eParam);
		Logger.debug("*���ݶ��������� ����=" + (System.currentTimeMillis() - start) + "ms");
		
		return (PfProcessBatchRetObject)retObj;
	}
	
	
	
	/**
	 * ��������˺���������
	 * @param vo
	 */
	private static void setCheckManAndDate(String billType, AggregatedValueObject aggVo)
	{
		IFlowBizItf flowBizImpl = getFlowBizImplByMdComp(billType, aggVo);
		if(flowBizImpl != null){
			// ����������
			//flowBizImpl.setApprover(LfwRuntimeEnvironment.getLfwSessionBean().getPk_user());
			// ��������ʱ��
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
	 * �����Ƶ���
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
	 * ���ݵ������ͻ�ȡԪ�������
	 * @param billType
	 * @return
	 */
	public static IComponent getMdCompByBillType(String billType) 
	{
		BilltypeVO typeVo = PfDataCache.getBillType(billType);
		String componentName = typeVo.getComponent();
		if(componentName == null)
			throw new LfwRuntimeException("���ݵ�������" + billType + "δ���Ԫ�������!");
		
		try {
			return MDBaseQueryFacade.getInstance().getComponentByName(componentName);
		} catch (MetaDataException e) {
			Logger.error(e, e);
			throw new LfwRuntimeException(e);
		}
	}
	
	/**
	 * ��ȡԪ������ʵ����ʵ�ֵ�����ҵ��ʵ����
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
	 * �ύ����ʱ,��Ҫ��ָ����Ϣ
	 * <li>ֻ��"SAVE","EDIT"�����ŵ���
	 */
	private static WorkflownoteVO checkOnSave(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO worknoteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class)
				.checkWorkFlow(actionName, billType, billVo, hmPfExParams);

		if (worknoteVO != null) {
			// �õ���ָ�ɵ���������
			Vector assignInfos = worknoteVO.getTaskInfo().getAssignableInfos();
			if (assignInfos != null && assignInfos.size() > 0) {
				// ��ʾָ�ɶԻ����ռ�ʵ��ָ����Ϣ
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
	 * ��������������ʱ,��Ҫ��ָ����Ϣ
	 * <li>����ѡ���̻�����ߡ�ѡ���̷�֧ת��
	 */
	private static WorkflownoteVO checkOnStart(String actionName, String billType,
			AggregatedValueObject billVo, Stack dlgResult, HashMap hmPfExParams) throws BusinessException {
		WorkflownoteVO wfVo = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(
				actionName, billType, billVo, hmPfExParams);

//		if (wfVo != null) {
//			// �õ���ָ�ɵ���Ϣ
//			Vector assignInfos = wfVo.getTaskInfo().getAssignableInfos();
//			Vector tSelectInfos = wfVo.getTaskInfo().getTransitionSelectableInfos();
//			if (assignInfos.size() > 0 || tSelectInfos.size() > 0) {
//				// ��ʾָ�ɶԻ����ռ�ʵ��ָ����Ϣ
//				WFStartDispatchDialog wfdd = new WFStartDispatchDialog(parent, wfVo, billVo);
//				int iClose = wfdd.showModal();
//				if (iClose == UIDialog.ID_CANCEL)
//					dlgResult.push(new Integer(iClose));
//			}
//		}
		return wfVo;
	}
	
	/**
	 * ��鵱ǰ�����Ƿ��ڹ��������л������������������У������н���
	 */
	private static WorkflownoteVO checkWorkitemWhenSignal(String actionCode,
			String billType, AggregatedValueObject billVo, HashMap hmPfExParams) throws BusinessException {
//		WorkflownoteVO noteVO = null;
//		UIDialog dlg = null;
//		try {
//			//��鵱ǰ�û��Ĺ�����������+���������̹�����
//			noteVO = NCLocator.getInstance().lookup(IWorkflowMachine.class).checkWorkFlow(actionCode,
//					billType, billVo, hmPfExParams);
//			if (noteVO == null) {
//				//m_checkFlag = true;
//				return noteVO;
//			} else {
//				//XXX:guowl+,����Ƿ񵯳���������
//				if(!isExchange(noteVO.getTaskInfo().getTask())) {
//					m_checkFlag = true;
//					return noteVO;
//				}
//				
//				if (noteVO.getWorkflow_type() == WorkflowTypeEnum.SubWorkApproveflow.getIntValue()) 
//				{
//					//������������������
//					//dlg = new ApproveWorkitemAcceptDlg(parent, noteVO, true);
//				} 
//				//else
//					//����������������
//					//dlg = new WorkflowWorkitemAcceptDlg(parent, noteVO, billVo);
//
//				if (dlg.showModal() == UIDialog.ID_OK) {
//					// ���ش����Ĺ�����
//					m_checkFlag = true;
//				} else {
//					// �û�ȡ��
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
//	 * ��鵱ǰ�����Ƿ������������У������н���
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
//				if (dlg.showModal() == UIDialog.ID_OK) { // ����û�����
//					// ���������Ĺ�����
//					m_checkFlag = true;
//					//wfVo = clientWorkFlow.getWorkFlow();
//				} else { // �û�������
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
//	 * ������Դ���ݣ�����
//	 * <li>1.��ȡ��Դ���ݵĲ�ѯ�Ի��򣬲���ѯ����Դ����
//	 * <li>2.��ʾ��Դ���ݣ�������ѡ��
//	 * <li>3.��ȡѡ�����Դ����
//	 * 
//	 * @param srcBillType  �����Ƶ� ѡ�����Դ��������
//	 * @param pk_group
//	 * @param userId
//	 * @param currBillOrTranstype ��ǰ���ݻ�������
//	 * @param parent
//	 * @param userObj
//	 * @param srcBillId ���Ϊ�գ���ֱ�ӽ���������ε��ݽ���
//	 */
//	public static void childButtonClicked(String srcBillType, String pk_group, String userId,
//			String currBillOrTranstype, Container parent, Object userObj, String srcBillId) {
//
//		makeFlag = false;
//		if (srcBillType.toUpperCase().equals("MAKEFLAG")) {
//			Logger.debug("******���Ƶ���******");
//			makeFlag = true;
//			return;
//		}
//
//		Logger.debug("******������Դ����******");
//		try {
//			String funNode = PfUIDataCache.getBillType(srcBillType).getNodecode();
//			if (funNode == null || funNode.equals(""))
//				throw new PFBusinessException("��ע�ᵥ��" + srcBillType + "�Ĺ��ܽڵ��");
//
//			//��ѯ������Ϣ���Ա�õ�һЩ�����Ƶ��Ķ�����Ϣ
//			String src_qrytemplate = null;
//			String src_billui = null; // ��Դ���ݵ���ʾUI��
//			String src_nodekey = null; // ���ڲ�ѯ��Դ���ݵĲ�ѯģ��Ľڵ��ʶ
//			ExchangeVO changeVO = PfUtilBaseTools.queryMostSuitableExchangeVO(srcBillType,
//					currBillOrTranstype, null, PfUtilUITools.getLoginGroup(), null);
//			if (changeVO != null) {
//				src_qrytemplate = changeVO.getSrc_qrytemplate();
//				src_qrytemplate = src_qrytemplate == null ? null : src_qrytemplate.trim();
//				src_billui = changeVO.getSrc_billui();
//				src_nodekey = changeVO.getSrc_nodekey();
//			}
//			// a.��ȡ��Դ���ݵĲ�ѯ�Ի��򣬼�Ϊm_condition��ֵ
//			IBillReferQuery qcDLG = null;
//			if (StringUtil.isEmptyWithTrim(src_qrytemplate)) {
//				Logger.debug("ʹ����Դ�������Ͷ�Ӧ�ڵ�ʹ�õĲ�ѯģ��Ի���");
//				src_qrytemplate = PfUtilUITools.getTemplateId(ITemplateStyle.queryTemplate, pk_group,
//						funNode, userId, src_nodekey);
//				//qcDLG = setConditionClient(src_qrytemplate, parent, userId, funNode, pk_group);
//			} else if (src_qrytemplate.startsWith("<")) {
//				Logger.debug("ʹ�ò�Ʒ�鶨�Ƶ���Դ���ݲ�ѯ�Ի���");
//				src_qrytemplate = src_qrytemplate.substring(1, src_qrytemplate.length() - 1);
////				qcDLG = loadUserQuery(parent, src_qrytemplate, pk_group, userId, funNode,
////						currBillOrTranstype, srcBillType, src_nodekey, userObj);
//			} else {
//				Logger.debug("ʹ��ע��Ĳ�ѯģ��Ĳ�ѯ�Ի���");
//				//qcDLG = setConditionClient(src_qrytemplate, parent, userId, funNode, pk_group);
//			}
//
//			if (srcBillId == null) {
//				//b.��ʾ��Դ���ݵĲ�ѯ�Ի���
//				if (qcDLG.showModal() == UIDialog.ID_OK) {
//					// c.��ʾ��Դ���ݣ�������ѡ��
//					refBillSource(pk_group, funNode, userId, currBillOrTranstype, parent, userObj,
//							srcBillType, src_qrytemplate, src_billui, src_nodekey, null, qcDLG);
//
//				} else {
//					m_isOk = false;
//					return;
//				}
//			} else {
//				//b'.ֱ����ʾ��Դ����
//				refBillSource(pk_group, funNode, userId, currBillOrTranstype, parent, userObj, srcBillType,
//						src_qrytemplate, src_billui, src_nodekey, srcBillId, qcDLG);
//				return;
//			}
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			MessageDialog.showErrorDlg(parent, "����", "�������ε��ݳ����쳣=" + ex.getMessage());
//		}
//	}
//	
//	/**
//	 * �����Ƶ�ʱ����ʾ��Դ���ݣ�������VO����
//	 */
//	private static void refBillSource(String pk_group, String funNode, String pkOperator,
//			String currBillOrTranstype, Container parent, Object userObj, String billType,
//			String strQueryTemplateId, String src_billui, String srcNodekey, String sourceBillId,
//			IBillReferQuery qcDLG) throws Exception {
//		// ��ȡ����Ĺؼ��ֶ�
//		String pkField = PfUtilBaseTools.findPkField(billType);
//		if (pkField == null || pkField.trim().length() == 0)
//			throw new PFBusinessException("�޷�ͨ���������ͻ�ȡ����ʵ�������PK�ֶ�");
//		String whereString = null;
//		if (sourceBillId == null) {
//			whereString = qcDLG.getWhereSQL();
//		} else
//			whereString = pkField + "='" + sourceBillId + "'";
//
//		//������Դ����չ�ֶԻ��򣬲���ʾ
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
//			Logger.debug("��Ʒ�鶨�Ƶ���Դ����չ�ֶԻ��򣬱���̳���" + AbstractBillSourceDLG.class.getName());
//			//bsDlg = loadCustomBillSourceDLG(src_billui, bsVar, parent);
//		} else {
//			Logger.debug("ʹ��ͨ�õ���Դ����չ�ֶԻ���");
//			bsDlg = new BillSourceDLG(parent, bsVar);
//		}
//
//		bsDlg.setQueyDlg(qcDLG); //�����ѯģ��Ի���
//		// ����ģ��
//		bsDlg.addBillUI();
//		// ��������
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
	 * ���� ��ǰ�����ڵ�Ĵ����� lj+ 2005-1-20
	 */
	public static int getCurrentCheckResult() {
		return m_iCheckResult;
	}

	/**
	 * ���� �û�ѡ���VO
	 */
	public static AggregatedValueObject getRetOldVo() {
		return m_tmpRetVo;
	}

	/**
	 * ���� �û�ѡ��VO����.
	 */
	public static AggregatedValueObject[] getRetOldVos() {
		return m_tmpRetVos;
	}

//	/**
//	 * ���� �û�ѡ���VO�򽻻�����VO
//	 * 
//	 * @return
//	 */
//	public static AggregatedValueObject getRetVo() {
//		try {
//			// ��Ҫ����VO����
//			m_tmpRetVo = PfUtilUITools.runChangeData(m_sourceBillType, m_currentBillType, m_tmpRetVo);
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			throw new PFRuntimeException("VO��������" + ex.getMessage(), ex);
//		}
//		return m_tmpRetVo;
//	}

//	/**
//	 * ���� �û�ѡ���VO�򽻻�����VO
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
//			throw new PFRuntimeException("VO��������" + ex.getMessage(), ex);
//		}
//
//		return tmpRetVos;
//	}
//
//	/**
//	 * ���� �û�ѡ��VO����򽻻�����VO����
//	 * 
//	 * @return
//	 */
//	public static AggregatedValueObject[] getRetVos() {
//		// ��Ҫ����VO����
//		m_tmpRetVos = changeVos(m_tmpRetVos);
//		return m_tmpRetVos;
//	}

	/**
	 * �ж��û��Ƿ����ˣ�ȡ������ť
	 * 
	 * @return boolean leijun+
	 */
	public static boolean isCanceled() {
		return !m_checkFlag;
	}

	/**
	 * ���� ���յ����Ƿ������ر�
	 * 
	 * @return boolean
	 */
	public static boolean isCloseOK() {
		return m_isOk;
	}

	/**
	 * ���� ��ǰ���ݶ���ִ���Ƿ�ɹ�
	 * 
	 * @return boolean
	 */
	public static boolean isSuccess() {
		return m_isSuccess;
	}

//	/**
//	 * �����Ʒ��ע�����Դ������ʾ�Ի���
//	 */
//	private static AbstractBillSourceDLG loadCustomBillSourceDLG(String src_billui,
//			BillSourceVar bsVar, Container parent) {
//		Class c = null;
//
//		try {
//			c = Class.forName(src_billui);
//			Class[] argCls = new Class[] { Container.class, BillSourceVar.class };
//			Object[] args = new Object[] { parent, bsVar };
//			// ʵ�������ι��췽��
//			Constructor cc = c.getConstructor(argCls);
//			return (AbstractBillSourceDLG) cc.newInstance(args);
//		} catch (Exception ex) {
//			Logger.error(ex.getMessage(), ex);
//			MessageDialog.showErrorDlg(parent, "����", "�����Ʒ��ע�����Դ������ʾ�Ի��� �����쳣��" + ex.getMessage());
//		}
//		return null;
//	}

//	private static IBillReferQuery loadUserQuery(Container parent, String src_qrytemplate,
//			String pk_group, String userId, String FunNode, String currBillOrTranstype,
//			String sourceBillType, String nodeKey, Object userObj) {
//
//		Class c = null;
//		try {
//			// ���ж��Ƿ�Ϊ�²�ѯģ��UI������
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
//			//�Բ�ѯģ��Ի����һЩ���Ƴ�ʼ��
//			if (retObj instanceof IinitQueryData) {
//				((IinitQueryData) retObj).initData(pk_group, userId, FunNode, currBillOrTranstype,
//						sourceBillType, nodeKey, userObj);
//			}
//
//			return (IBillReferQuery) retObj;
//		} catch (NoSuchMethodException e) {
//			Logger.warn("�Ҳ����²�ѯģ��UI�Ĺ��췽���������ж��Ƿ����ϲ�ѯģ��Ĺ��췽��", e);
//			try {
//				// Ӧ��Ϊ�ϲ�ѯģ��UI������
//				Object retObj = c.getConstructor(new Class[] { Container.class }).newInstance(
//						new Object[] { parent });
//				//�Բ�ѯģ��Ի����һЩ���Ƴ�ʼ��
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

		// 2.�鿴��չ�������Ƿ�Ҫ���̽�������
		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
		if (paramNoflow == null && paramSilent == null) {
			//��Ҫ��������
			if (PfUtilBaseTools.isSaveAction(actionCode, billOrTranstype) || PfUtilBaseTools.isApproveAction(actionCode, billOrTranstype)) {
				//��������������
				//dengjt_ע��
				worknoteVO = actionAboutApproveflow(actionCode, billOrTranstype, billvo, eParam);
				/****��ָ����Ϣ��ӵ�������**********/
				setAssign(worknoteVO, pk_users, userObj);
				/**************/
				if (!m_isSuccess)
					return null;
			} else if (PfUtilBaseTools.isStartAction(actionCode, billOrTranstype)
					|| PfUtilBaseTools.isSignalAction(actionCode, billOrTranstype)) {
				//������������
				worknoteVO = actionAboutWorkflow(actionCode, billOrTranstype, billvo, eParam);
				if (!m_isSuccess)
					return null;
			}
		}

		//����û��ڽ�������ʱ��ѡ���ǩ���߸��ɣ���������������@2009-5
		if (worknoteVO != null
				&& (worknoteVO.getTaskInfo().getTask().isAddApprover() || worknoteVO.getTaskInfo()
						.getTask().isAppoint())) {
			m_isSuccess = false;
			return null;
		}
//		if (worknoteVO == null) {
//			//��鲻����������̨�����ٴμ��
//			if (eParam == null)
//				eParam = new HashMap<String, String>();
//			if (paramSilent == null)
//				eParam.put(PfUtilBaseTools.PARAM_NOTE_CHECKED, PfUtilBaseTools.PARAM_NOTE_CHECKED);
//		}

		// 4.��ִ̨�ж���
		Object retObj = null;

		Logger.debug("*��̨�������� ��ʼ");
		long start2 = System.currentTimeMillis();
		IplatFormEntry iIplatFormEntry = (IplatFormEntry) NCLocator.getInstance().lookup(
				IplatFormEntry.class.getName());
		retObj = iIplatFormEntry.processAction(actionCode, billOrTranstype, worknoteVO, billvo,
				userObj, eParam);
		Logger.debug("*��̨�������� ����=" + (System.currentTimeMillis() - start2) + "ms");

		m_isSuccess = true;

		// 5.���ض���ִ��
		//retObjRun(parent, retObj);
		Logger.debug("*���ݶ������� ����=" + (System.currentTimeMillis() - start) + "ms");

		return retObj;
	}
	
	
	/**
	 * ָ����Ϣ��������������
	 * @param wfvo
	 * @param pk_users
	 * @param userObject
	 * @return
	 */
	public static void setAssign(WorkflownoteVO wfvo,String[] pk_users, Object userObject){
		if(wfvo == null)
			return;
		//����ָ����Ϣ
		if(pk_users != null){
			Vector<AssignableInfo> assInfos = wfvo.getTaskInfo().getAssignableInfos();
			for(Iterator<AssignableInfo> assit = assInfos.iterator();assit.hasNext();){
				AssignableInfo assInfo = assit.next();
				//if(key.equals(assInfo.getActivityDefId())){
					//�����
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
		//����������Ϣ
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
								//������в�����Ϣ�������ػID����
								wfvo.getTaskInfo().getTask().setJumpToActivity(rejectActivityId);
								wfvo.getTaskInfo().getTask().setBackToFirstActivity(false);
							}
						}
						else			
							//Ĭ�϶��ǲ��ص��Ƶ���
							wfvo.getTaskInfo().getTask().setBackToFirstActivity(true);
					}
					//���ص�ʱ��approveresult��ֵ�ó�R
					wfvo.setApproveresult("R");
				}
			}
			String message = (String) messageMap.get(LfwPfUtil.APPROVEMESSAGE);
			if(message != null)
				wfvo.setChecknote(message);
		}
	}
	
	
	/**
	 * ��������صĽ�������
	 * @throws BusinessException 
	 */
	private static WorkflownoteVO actionAboutWorkflow(String actionName,
			String billType, AggregatedValueObject billvo, HashMap eParam) throws BusinessException {
		WorkflownoteVO worknoteVO = null;

		if (PfUtilBaseTools.isStartAction(actionName, billType)) {
			Logger.debug("*��������=" + actionName + "����鹤����");
			Stack dlgResult = new Stack();
			worknoteVO = checkOnStart(actionName, billType, billvo, dlgResult, eParam);
			if (dlgResult.size() > 0) {
				m_isSuccess = false;
				Logger.debug("*�û�ָ��ʱ�����ȡ������ֹͣ����������");
			}
		} 
		else if (PfUtilBaseTools.isSignalAction(actionName, billType)) {
			Logger.debug("*ִ�ж���=" + actionName + "����鹤����");
			// ���õ����Ƿ��ڹ�������
			worknoteVO = checkWorkitemWhenSignal(actionName, billType, billvo, eParam);
			if (worknoteVO != null) {
				if ("Y".equals(worknoteVO.getApproveresult())) {
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;
				} else if("R".equals(worknoteVO.getApproveresult())) {
					//���ڲ��ص�������result��"R"�ĲŴ���
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
				Logger.debug("*�û�����������ʱ�����ȡ������ִֹͣ�й�����");
			}
		}
		return worknoteVO;
	}
//	
//	/**
//	 * ��������صĽ�������
//	 * @throws BusinessException 
//	 */
	private static WorkflownoteVO actionAboutApproveflow(String actionName,
			String billType, AggregatedValueObject billvo, HashMap eParam) throws BusinessException {
		WorkflownoteVO worknoteVO = null;

		if (PfUtilBaseTools.isSaveAction(actionName, billType)) {
			Logger.debug("*�ύ����=" + actionName + "�����������");
			// ���Ϊ�ύ������������Ҫ�ռ��ύ�˵�ָ����Ϣ������ͳһ�������� lj@2005-4-8
			Stack dlgResult = new Stack();
			worknoteVO = checkOnSave(IPFActionName.SAVE, billType, billvo, dlgResult, eParam);
			if (dlgResult.size() > 0) {
				m_isSuccess = false;
				Logger.debug("*�û�ָ��ʱ�����ȡ������ֹͣ����");
			}
		} else if (PfUtilBaseTools.isApproveAction(actionName, billType)) {
			Logger.debug("*��������=" + actionName + "�����������");
			// ���õ����Ƿ����������У����ռ������˵�������Ϣ
			worknoteVO = checkWorkitemWhenApprove(actionName, billType, billvo, eParam);
			if (worknoteVO != null) {
				if ("Y".equals(worknoteVO.getApproveresult())) {
					m_iCheckResult = IApproveflowConst.CHECK_RESULT_PASS;
				} else if("R".equals(worknoteVO.getApproveresult())) {
					//���ڲ��ص�������result��"R"�ĲŴ���
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
				Logger.debug("*�û�����ʱ�����ȡ������ֹͣ����");
			}
		}
		return worknoteVO;
	}
	
	
	/**
	 * ��ȡָ����Ϣ
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
			// ��ʾָ�ɶԻ����ռ�ʵ��ָ����Ϣ
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
	 * ���ʱ,��Ҫ��ָ����Ϣ
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
//	 * ǰ̨���ݶ���������API���㷨���£�
//	 * <li>1.����ִ��ǰ��ʾ�Լ���ǰ��������û�ȡ�����򷽷�ֱ�ӷ���
//	 * <li>2.�鿴��չ�������ж��Ƿ���Ҫ��������ش������Ϊ�ύ�������ҵ���VO������ֻ��һ�ŵ���ʱ������Ҫ�ռ��ύ�˵�ָ����Ϣ��
//	 * ���Ϊ��������������Ե�һ�ŵ��ݿ�����Ҫ�ռ������˵�������Ϣ
//	 * <li>3.��ִ̨���������������ض���ִ�н���� 
//	 * 
//	 * @param parent ������
//	 * @param actionCode �������룬����"SAVE"
//	 * @param billOrTranstype �������ͣ��������ͣ�PK
//	 * @param voAry ���ݾۺ�VO����
//	 * @param userObjAry �û��Զ����������
//	 * @param strBeforeUIClass ǰ̨��ǰ������
//	 * @param eParam ��չ����
//	 * @return ����������ķ��ؽ��
//	 * @throws Exception
//	 * @since 5.5
//	 */
//	public static Object[] runBatch(String actionCode, String billOrTranstype,
//			AggregatedValueObject[] voAry, Object[] userObjAry, String strBeforeUIClass, HashMap eParam)
//			throws Exception {
//		Logger.debug("*���ݶ��������� ��ʼ");
//		long start = System.currentTimeMillis();
//		m_isSuccess = true;
//
//		// 1.����ִ��ǰ��ʾ�Լ���ǰ����
//		boolean isContinue = beforeProcessBatchAction(parent, actionCode, billOrTranstype, voAry,
//				userObjAry, strBeforeUIClass);
//		if (!isContinue) {
//			Logger.debug("*����ִ��ǰ��ʾ�Լ���ǰ��������");
//			m_isSuccess = false;
//			return null;
//		}
//
//		// 2.�鿴��չ�������Ƿ�Ҫ���̽�������
//		WorkflownoteVO worknoteVO = null;
//		Object paramNoflow = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_NOFLOW);
//		Object paramSilent = eParam == null ? null : eParam.get(PfUtilBaseTools.PARAM_SILENTLY);
//		if (paramNoflow == null && paramSilent == null) {
//			if (PfUtilBaseTools.isSaveAction(actionCode) || PfUtilBaseTools.isApproveAction(actionCode)) {
//				//��������������XXX:ֻ��Ե�һ�ŵ���VO��
//				worknoteVO = actionAboutApproveflow(actionCode, billOrTranstype, voAry[0], eParam);
//				if (!m_isSuccess)
//					return null;
//			} else if (PfUtilBaseTools.isSignalAction(actionCode)) {
//				//��������������XXX:ֻ��Ե�һ�ŵ���VO��
//				worknoteVO = actionAboutWorkflow(parent, actionCode, billOrTranstype, voAry[0], eParam);
//				if (!m_isSuccess)
//					return null;
//			}
//		}
//
//		// 3.��̨��������
//		Logger.debug("*��̨���������� ��ʼ");
//		long start2 = System.currentTimeMillis();
//		Object retObj = NCLocator.getInstance().lookup(IplatFormEntry.class).processBatch(actionCode,
//				billOrTranstype, worknoteVO, voAry, userObjAry, eParam);
//		Logger.debug("*��̨���������� ����=" + (System.currentTimeMillis() - start2) + "ms");
//		m_isSuccess = true;
//		Logger.debug("*���ݶ��������� ����=" + (System.currentTimeMillis() - start) + "ms");
//		return (Object[]) retObj;
//	}

//	/**
//	 * ����ִ��ǰ��ʾ�Լ���ǰ����
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
	 * ����ĳ�����ݻ������Ϳ�ʹ�õġ������������˵���Ϣ
	 * <li>�������ơ���Դ����
	 * 
	 * @param billtype
	 * @param transtype ���û�н������ͣ��ɿ� 
	 * @param pk_group ĳ����PK
	 * @param userId ĳ�û�PK
	 * @param includeBillType �Ƿ�����������͵���Դ������Ϣ��ֻ����transtype�ǿյ����
	 * @return
	 * @throws BusinessException 
	 */
	public static PfAddInfo[] retAddInfo(String billtype, String transtype, String pk_group,
			String userId, boolean includeBillType) throws BusinessException {

		return NCLocator.getInstance().lookup(IPFConfig.class).retAddInfo(billtype, transtype,
				pk_group, userId, includeBillType);
	}

	/**
	 * ����ĳ���û���ĳ�����ݻ������ͣ���ĳ��֯�� ��������ҵ������
	 * @param billtype
	 * @param transtype ���û�н������ͣ��ɿ� 
	 * @param pk_org ĳ��֯PK
	 * @param userId ĳ�û�PK
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
//	 * ����ĳ���ݻ����������õ�ĳ����������е��ݶ���
//	 * 
//	 * @param billOrTranstype ���ݻ�������
//	 * @param actiongroupCode ���������
//	 * @return
//	 */
//	public static PfUtilBillActionVO[] getActionsOfActiongroup(String billOrTranstype,
//			String actiongroupCode) {
//		//��õ�������(��������)�ĵ�������PK
//		billOrTranstype = PfUtilBaseTools.getRealBilltype(billOrTranstype);
//		PfUtilBillActionVO[] billActionVos = (PfUtilBillActionVO[]) PfUIDataCache
//				.getButtonByBillAndGrp(billOrTranstype, actiongroupCode);
//		return billActionVos;
//	}
//
//	/**
//	 * ����һ����ѯ�Ի��򣬲�Ϊ�����ò�ѯģ��
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
//	 * ���ݵ������ͺ�ILfwExAggVOת��ΪaggVo
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
