package nc.uap.wfm.cmd;
import nc.bs.framework.common.NCLocator;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.dbl.vo.DblFormDefinitionVO;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.context.InterimProInsInfoCtx;
import nc.uap.wfm.context.PwfmContext;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFrmNumBillGen;
import nc.uap.wfm.engine.IProDefExtHandler;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmFrmNumRuleQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.DefaultFormVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmFrmNumRuleVO;
import nc.vo.pub.lang.UFDate;
public class GenFrmNumBillCmd extends AbstractCommand implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		WfmFormInfoCtx formVo = (WfmFormInfoCtx) PwfmContext.getCurrentBpmnSession().getFormVo();
		Task task = PwfmContext.getCurrentBpmnSession().getTask();
		ProDef proDef = null;
		if (task == null) {
			proDef = PwfmContext.getCurrentBpmnSession().getProDef();
		} else {
			proDef = task.getProDef();
		}
		WfmFlowInfoCtx flwInfCtx = PwfmContext.getCurrentBpmnSession().getFlwInfoCtx();
		if (formVo instanceof DefaultFormVO) {
			DefaultFormVO dynamicFormVo = (DefaultFormVO) formVo;
			String frmNumBillField = null;
			String frmNumBillValue = dynamicFormVo.getProp().get(frmNumBillField);
			/**
			 * 首先做一下制单号同步
			 */
			dynamicFormVo.getProp().put(DblConstants.MakeFormNumb, frmNumBillValue);
			frmNumBillValue = dynamicFormVo.getProp().get(DblConstants.MakeFormNumb);
			/**
			 * 首先考虑扩展类制作的流水号
			 */
			if (frmNumBillValue == null || frmNumBillValue.length() == 0) {
				String serverClass = proDef.getServerClass();
				if (serverClass != null && serverClass.length() != 0) {
					IProDefExtHandler proDefExt = (IProDefExtHandler) WfmClassUtil.loadClass(serverClass);
					serverClass = proDefExt.getFrmNumBillServerClass();
					if (serverClass != null && serverClass.length() != 0) {
						IFrmNumBillGen frmNumBillGen = (IFrmNumBillGen) WfmClassUtil.loadClass(serverClass);
						/**
						 * 暂存的要看是不是前置编码
						 */
						if (flwInfCtx instanceof InterimProInsInfoCtx) {
							if (frmNumBillGen.isBefore(null, task == null ? null : task.getPk_task())) {
								frmNumBillValue = frmNumBillGen.getValue(null, task == null ? null : task.getPk_task());
							}
						} else {
							frmNumBillValue = frmNumBillGen.getValue(null, task == null ? null : task.getPk_task());
						}
						dynamicFormVo.getProp().put(DblConstants.MakeFormNumb, frmNumBillValue);
					}
				}
			}
			/**
			 * 再考虑生成配置编码生成的流水号
			 */
			if (frmNumBillValue == null || frmNumBillValue.length() == 0) {
				WfmFrmNumRuleVO frmNumRuleVo = NCLocator.getInstance().lookup(IWfmFrmNumRuleQry.class).getFrmNumRuleVoByProDefPkAndId(proDef.getPk_prodef(), proDef.getId());
				if (frmNumRuleVo != null) {
					if (flwInfCtx instanceof InterimProInsInfoCtx && WfmConstants.FrmNumCodeType_Pre.equalsIgnoreCase(frmNumRuleVo.getCodetype())) {
						frmNumBillValue = NCLocator.getInstance().lookup(IFrmNumBillGen.class).genFrmNumBillByFrmDefPk_RequiresNew(null);
					} else {
						frmNumBillValue = NCLocator.getInstance().lookup(IFrmNumBillGen.class).genFrmNumBillByFrmDefPk_RequiresNew(null);
					}
					dynamicFormVo.getProp().put(DblConstants.MakeFormNumb, frmNumBillValue);
				}
			}
			/**
			 * 最后考虑自动生成的流水号
			 */
			if (frmNumBillValue == null || frmNumBillValue.length() == 0) {
				DblFormDefinitionVO frmDefVo = null; // (DblFormDefinitionVO)PwfmUtil.getFrmDefVo(frmDefPk);
				if (frmDefVo == null) {
					frmNumBillValue = new UFDate().toLocalString();
					dynamicFormVo.getProp().put(DblConstants.MakeFormNumb, frmNumBillValue);
				} else {
					frmNumBillValue = frmDefVo.getName();
					dynamicFormVo.getProp().put(DblConstants.MakeFormNumb, frmNumBillValue);
				}
			}
			dynamicFormVo.getProp().put(frmNumBillField, frmNumBillValue);
			return null;
		}
		return null;
	}
}
