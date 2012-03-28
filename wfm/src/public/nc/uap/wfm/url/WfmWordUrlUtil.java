package nc.uap.wfm.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.convert.ModelBuilder;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAttachPurviewQry;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmAttachPurviewVO;

public class WfmWordUrlUtil {
	
	public static String getWordUrl(String corePath, Task task, ProDef proDef, String wordPk) {
		String portId = null;
		if (task == null) {
			portId = WfmTaskUtil.getStratPort(proDef).getId();
		} else {
			portId = task.getHumActIns().getHumAct().getId();
		}
		IWfmAttachPurviewQry attachQry = NCLocator.getInstance().lookup(IWfmAttachPurviewQry.class);
		WfmAttachPurviewVO attachVo = attachQry.getAttachPurview(proDef.getPk_prodef(), proDef.getId(), portId, WfmConstants.AttachPurview_WrodFile_Enable);
		boolean isWrite = true;
		boolean isTrackRevisions = true;
		boolean isOpen = true;
		if (attachVo == null) {
			isWrite = true;
		} else {
			isWrite = attachVo.getIsstartup().booleanValue();
		}
		if (task == null) {
			isWrite = true;
			isTrackRevisions = false;
			isOpen = true;
		} else {
			if (portId.equalsIgnoreCase(WfmTaskUtil.getStratPort(proDef).getId())) {
				isWrite = true;
				isTrackRevisions = false;
				isOpen = true;
				if (Task.CreateType_Reject.equalsIgnoreCase(task.getCreateType())) {
					isTrackRevisions = true;
					isOpen = false;
				}
			} else {
				isTrackRevisions = true;
				isOpen = false;
				if (Task.CreateType_BeforeAddSign.equalsIgnoreCase(task.getCreateType())) {
					isWrite = true;
				}
				if (Task.State_BeforeAddSignSend.equalsIgnoreCase(task.getState())) {
					isWrite = false;
				}
			}
		}
		String occupymsg = "";
		if (task != null) {
			HumActIns humActIns = null;
			try {
				humActIns = ModelBuilder.builder(task.getHumActIns());
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			Set<Task> tasks = humActIns.getTasks();
			if (tasks != null && tasks.size() > 1) {
				Task tmpTask = null;
				for (Iterator<Task> iter = tasks.iterator(); iter.hasNext();) {
					tmpTask = iter.next();
					if ("Y".equalsIgnoreCase(tmpTask.getExt8())) {
						String ownerPk = tmpTask.getPk_owner();
						String currentPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
						if (ownerPk.equalsIgnoreCase(currentPk)) {
							continue;
						}
						isWrite = false;
						String userName = WfmTransUtil.getUserNameByUserPk(ownerPk);
						occupymsg = "该正文正在被" + userName + "编辑,将以只读方式打开!";
						break;
					}
				}
			}
		}
		String url = "";
		if (isWrite) {
			url = corePath + "/word.jsp?url=" + wordPk + "&autoSave=true&saveTTL=1800000";
			if (isTrackRevisions) {
				url = url + "&TrackRevisions=true";
			} else {
				url = url + "&TrackRevisions=false";
			}
			if (isOpen) {
				url = url + "&canopen=true";
			} else {
				url = url + "&canopen=false";
			}
		} else {
			url = corePath + "/word.jsp?url=" + wordPk + "&readonly=true&canopen=false";
		}
		try {
			occupymsg = URLEncoder.encode(URLEncoder.encode(occupymsg, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		url = url + "&occupymsg=" + occupymsg;
		return url;
	}
}
