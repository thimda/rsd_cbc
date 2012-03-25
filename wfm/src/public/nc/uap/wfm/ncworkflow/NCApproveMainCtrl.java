package nc.uap.wfm.ncworkflow;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.pf.IPFWorkflowQry;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.pub.BusinessException;
import nc.vo.pub.msg.MessageVO;
import nc.vo.pub.workflownote.WorkflownoteVO;
import nc.vo.wfengine.definition.WorkflowTypeEnum;

/**
 * NC流程审批执行类
 * @author zhangxya
 *
 */
public class NCApproveMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 审批历史加载类
	 * @param se
	 */
	public void onFlowHistroyDsDataload(DataLoadEvent se) {
		//WebContext context = LfwRuntimeEnvironment.getWebContext();
		String billId = null;
		//(String) context.getWebSession().getAttribute(NCApproveConstant.BILLID);
		String billType = null;
		//(String) context.getWebSession().getAttribute(NCApproveConstant.BILLTYPE);
		Dataset ds = (Dataset) se.getSource();
		try {
			//getMainQryServie().queryHistoryMessage(billId, billType);
			WorkflownoteVO[] wfHistory = NCLocator.getInstance().lookup(IPFWorkflowQry.class).queryWorkitems(billId, billType, WorkflowTypeEnum.Approveflow.getIntValue(), 0);
				for (int i = 0; i < wfHistory.length; i++) {
				WorkflownoteVO note = wfHistory[i];
				//翻译消息内容
				note.setMessagenote(MessageVO.getMessageNoteAfterI18N(note.getMessagenote()));
			}
			new SuperVO2DatasetSerializer().serialize(wfHistory, ds, Row.STATE_NORMAL);
		} 
		catch (BusinessException e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
		}
	} 
	

}
