package nc.uap.wfm.ncworkflow.cmd;

import java.util.HashMap;

import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;

/**
 * �ջ�
 * @author zhangxya
 *
 */
public class NCReCallCmd extends UifCommand {
	
	//���ղ�������
	private String recallAction;
	
	//������Ϣ���û������Ƿ�������
	HashMap<String, Object> eParam;
	
	public NCReCallCmd(String recallAction, HashMap<String, Object> eParam) {
		this.recallAction = recallAction;
	}

	@Override
	public void execute() {
		//�õ�ҳ�����л����Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//�õ���������
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			LfwPfUtil.reCallFlow(recallAction, aggVo, flowTypePk, null, eParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("�����ջز��������쳣!");
		}
	}
	
	
	/**
	 * ����aggvo
	 * @return
	 */
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}

}
