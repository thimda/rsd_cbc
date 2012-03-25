package nc.uap.wfm.cmd;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.utils.WfmFormInsUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.vo.pub.lang.UFDateTime;
/**
 * 创建流程实例命令
 * 
 * @author tianchw
 * 
 */
public class CreateProInsCmd extends AbstractCommand implements ICommand<ProIns> {
	public CreateProInsCmd() {
		super();
	}
	public ProIns execute() throws WfmServiceException {
		/**
		 * 启动流程实例
		 */
		ProDef proDef = PwfmContext.getCurrentBpmnSession().getProDef();
		ProIns proIns = proInsExe.createProIns(proDef);
		proInsExe.startProIns(proIns);
		/**
		 * 设置启动表单实例PK
		 */
		WfmFormInfoCtx formVo = PwfmContext.getCurrentBpmnSession().getFormVo();
		if (formVo != null) {
			String frmInsPk = formVo.getFrmInsPk();
			if (frmInsPk == null || frmInsPk.length() == 0) {
				frmInsPk = WfmFormInsUtil.genFrmInsDefalutPk();
				formVo.setAttributeValue(WfmConstants.FrmInsPk, frmInsPk);
			}
			proIns.setPk_startfrmins(frmInsPk);
		}
		/**
		 * 设置主流程实例PK
		 */
		ProIns pProIns = PwfmContext.getCurrentBpmnSession().getPProIns();
		proIns.setPproIns(pProIns);
		proIns.setPk_starttask(PwfmContext.getCurrentBpmnSession().getStartTaskPk());
		/**
		 * 把流程实例信息同步到数据库中
		 */
		proIns.asyn();
		/**
		 * 同步上下文信息
		 */
		PwfmContext.getCurrentBpmnSession().setProIns(proIns);
		/**
		 * 返回流程实例
		 */
		if(formVo!=null){
			formVo.setAttributeValue(formVo.getBillMakeUserField(), PwfmContext.getCurrentBpmnSession().getCurrentUserPk());
			formVo.setAttributeValue(formVo.getBillMakeDateField(), new UFDateTime());
			formVo.setAttributeValue(formVo.getFrmStateField(), ProIns.Run);
		}
		return proIns;
	}
}
