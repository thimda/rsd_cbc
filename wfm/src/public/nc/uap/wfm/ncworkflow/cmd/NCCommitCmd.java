package nc.uap.wfm.ncworkflow.cmd;

import java.util.HashMap;

import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.uap.lfw.core.cmd.base.UifCommand;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.crud.ILfwCRUDService;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.pf.workflow.IPFActionName;
import nc.vo.sm.UserVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * nc���̵��ύ����
 * @author zhangxya
 *
 */
public class NCCommitCmd extends UifCommand {


	private HashMap eParam;
	
	public HashMap getEParam() {
		return eParam;
	}

	public void setEParam(HashMap param) {
		eParam = param;
	}
	
	@Override
	public void execute() {
		//�õ�ҳ�����л����Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//�õ���������
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			IFlowBizItf flowBizImpl = LfwPfUtil.getFlowBizImplByMdComp(flowTypePk, aggVo);
			String billStateColumn = flowBizImpl.getColumnName(IFlowBizItf.ATTRIBUTE_APPROVESTATUS);
			Integer state = (Integer) aggVo.getParentVO().getAttributeValue(billStateColumn);
			if(state == null)
				aggVo.getParentVO().setAttributeValue(billStateColumn, IBillStatus.FREE);
			//���ύִ��ǰִ���߼�
			if(!onBeforeVOSubmit(aggVo))
				return;
			UserVO[] users = LfwPfUtil.getAssignUsers(IPFActionName.SAVE, flowTypePk, aggVo, null, null);
			String[] assignPks = null;
			if(users != null && users.length > 0){
//					LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(LfwPfUtil.USERVOS, users);
//					//String commitMessage =widgetId + ":" + masterDsId + ":" + aggVoClazz + ":" + billType;
//					LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(LfwPfUtil.MASTERDS, masterDs);
//					LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(LfwPfUtil.DETAILDSS, detailDss);
//					LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(LfwPfUtil.AGGVO, aggVo);
//					LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(LfwPfUtil.BILLTYPE, billType);
//					String url = LfwRuntimeEnvironment.getCorePath() + "/uimeta.um?pageId=assign&model=nc.uap.lfw.pfinfoapprove.AssignPageModel";
//					getGlobalContext().showModalDialog(url, "�ύ", "600", "380", "commit", false, false);
			}
			else{
				commitSevice(aggVo, flowTypePk, assignPks);
			}
		}  catch (Exception e) {
			dealWithException(e);
		}
		updateButtons();
		//�ύ��ҵ��״̬��Ϊ�ύ̬
	}
	
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}
	
	/**
	 * �쳣�Ĵ�����
	 * @param e
	 */
	protected void dealWithException(Exception e) {
		//Logger.error(e, e);
		throw new LfwRuntimeException(e.getMessage());
	}

	/**
	 * ִ���ύ���������õ���״̬
	 * @param masterDs
	 * @param ser
	 * @param detailDss
	 * @param aggVo
	 * @param flowBizImpl
	 * @param assignPks
	 * @throws Exception
	 */
	public void commitSevice(AggregatedValueObject aggVo, String flowTypePk, String[] assignPks) throws Exception {
		LfwPfUtil.commit(aggVo, flowTypePk, null, eParam, assignPks);
	}
	
	protected ILfwCRUDService<SuperVO, AggregatedValueObject> getCrudService() {
		return CRUDHelper.getCRUDService();
	}
	
	/**
	 * �ύǰִ�в���,Ĭ�Ϸ���true���������false���򲻼���ִ��
	 * @param aggVo
	 * @throws Exception
	 */
	protected boolean onBeforeVOSubmit(AggregatedValueObject aggVo) throws Exception {
		return true;
	}
}
