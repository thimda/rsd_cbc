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
 * nc流程的提交动作
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
		//得到页面序列化后的Aggvo
		AggregatedValueObject aggVo = builderAggVO();
		//得到单据类型
		String flowTypePk = WfmTaskUtil.getFlowTypePk();
		try {
			IFlowBizItf flowBizImpl = LfwPfUtil.getFlowBizImplByMdComp(flowTypePk, aggVo);
			String billStateColumn = flowBizImpl.getColumnName(IFlowBizItf.ATTRIBUTE_APPROVESTATUS);
			Integer state = (Integer) aggVo.getParentVO().getAttributeValue(billStateColumn);
			if(state == null)
				aggVo.getParentVO().setAttributeValue(billStateColumn, IBillStatus.FREE);
			//在提交执行前执行逻辑
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
//					getGlobalContext().showModalDialog(url, "提交", "600", "380", "commit", false, false);
			}
			else{
				commitSevice(aggVo, flowTypePk, assignPks);
			}
		}  catch (Exception e) {
			dealWithException(e);
		}
		updateButtons();
		//提交后将业务状态置为提交态
	}
	
	public AggregatedValueObject builderAggVO() {
		return NCPfUtil.getWfmAggVO();
	}
	
	/**
	 * 异常的处理方法
	 * @param e
	 */
	protected void dealWithException(Exception e) {
		//Logger.error(e, e);
		throw new LfwRuntimeException(e.getMessage());
	}

	/**
	 * 执行提交操作并设置单据状态
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
	 * 提交前执行操作,默认返回true，如果返回false，则不继续执行
	 * @param aggVo
	 * @throws Exception
	 */
	protected boolean onBeforeVOSubmit(AggregatedValueObject aggVo) throws Exception {
		return true;
	}
}
