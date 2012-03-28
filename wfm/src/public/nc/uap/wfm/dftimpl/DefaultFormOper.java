package nc.uap.wfm.dftimpl;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.pagemodel.FlwDataDispPageModel;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.uap.wfm.vo.WfmTaskVO;
import nc.vo.pub.SuperVO;
public class DefaultFormOper implements IWfmFormOper {
	public WfmFormInfoCtx save(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx) {
		return frmInfoCtx;
	}
	public WfmFormInfoCtx update(WfmFormInfoCtx frmInfoCtx, WfmFlowInfoCtx flwInfoCtx) {
		return frmInfoCtx;
	}
	public String getUrlByTask(Task task) {
		if (task == null) {
			return null;
		}
		CpAppsNodeVO appNodeVo = WfmTaskUtil.getCpAppNodeVo(task);
		String url = appNodeVo.getUrl();
		if (url == null || url.length() == 0) {
			throw new LfwRuntimeException("无法找到对应的URL");
		}
		if (url.lastIndexOf("?") == -1) {
			url = url + "?";
		}
		if (url.indexOf("model") == -1) {
			url = url + "model=" + FlwDataDispPageModel.class.getName();
		}
		url = url + "&" + WfmConstants.WfmUrlConst_TaskPk + "=" + task.getPk_task();
		return url;
	}
	public ProDef getProDefByFlowType(WfmFlwTypeVO flowTypeVo) {
		try {
			WfmProdefVO[] proDefVos = WfmServiceFacility.getProDefQry().getProDefByFlwTypePk(flowTypeVo.getPk_flwtype());
			if (proDefVos == null || proDefVos.length == 0) {
				return null;
			}
			return ProDefsContainer.getProDef(proDefVos[0]);
		} catch (Exception e) {
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	public String getDetailUrlByTask(Task task) {
		return null;
	}
	public void afterDeleteProDef(ProDef proDef) {}
	public void afterSaveOrUpdateProDef(ProDef proDef) {}
	public String getExtAttrSettingUrl(HumAct humAct) {
		return null;
	}
	public String getHanlderUrlByTask(Task task) {
		return null;
	}
	@SuppressWarnings("unchecked") public Class<SuperVO>[] getBizMetaDataDesc(WfmFlwTypeVO flowTypeVo) {
		return new Class[] { WfmTaskVO.class };
	}
}
