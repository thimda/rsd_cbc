package nc.uap.wfm.ncworkflow.cmd;

import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * nc��������
 * @author zhangxya
 *
 */
public class NCApproveCmd extends UifCommand {

	public static String APPROVESELTEXT = "approveSelText";
	public static String ASSIGNBUTTON = "assignButton";
	public static String APPROVEMESSAGE = "approveMessage";
	public static String APPROVEUSERS = "approveusers";
	
	@Override
	public void execute() {
		//��������
		dealApprove();
		//��������
		dealOthers();
		//�ر������Ի���
	}
	
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}
	
	/**
	 * ������������
	 */
	 protected void dealApprove() {
		//�õ�ҳ�����л����Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//�õ���������
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		String operator = WfmTaskUtil.getOperator();
		Map approveInfoMap = new HashMap<String, String>();
		String approveValue = null;
		if (WfmConstants.Str_Agree.equalsIgnoreCase(operator))
			approveValue = "Y";
		else if(WfmConstants.Str_UnAgree.equalsIgnoreCase(operator))
			approveValue = "N";		
		else if (WfmConstants.Str_Reject.equalsIgnoreCase(operator)){
			approveValue = "R";
			String rejectActiveId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute(LfwPfUtil.REJECTACTIVITYID);
			if(rejectActiveId != null && !"".equals(rejectActiveId))
				approveInfoMap.put(LfwPfUtil.REJECTACTIVITYID, rejectActiveId);

		}
//		TextAreaComp messageArea = (TextAreaComp) widget.getViewComponents().getComponent(APPROVEMESSAGE);
//		String message = messageArea.getValue();
		//ָ����
		String[] pk_users = (String[]) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("AssignPKUsers");
		//ִ����������
		try {
			approveInfoMap.put(LfwPfUtil.APPROVEINFO, approveValue);
			//approveInfoMap.put(LfwPfUtil.APPROVEMESSAGE, message);
			if(!onBeforApprove(aggVo)){
				return;
			}
			//ִ����������
			LfwPfUtil.approve(aggVo, flowTypePk, approveInfoMap, pk_users);
		} catch (Exception e1) {
			dealWithException(e1);
		}
	}

	/**
	 * �쳣������
	 * @param e1
	 */
	 protected void dealWithException(Exception e1) {
//		Logger.error(e1, e1);
//		throw new LfwRuntimeException(e1.getMessage());
	}
	 
	/**
	 * ����ǰ��У��
	 * @return
	 */

	protected boolean onBeforApprove(AggregatedValueObject aggVo) throws Exception {	
		return true;
	}
	
	/**
	 * �������⴦��
	 */
	protected void dealOthers(){};


}
