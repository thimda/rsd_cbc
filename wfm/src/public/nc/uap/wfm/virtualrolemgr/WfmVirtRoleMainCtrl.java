package nc.uap.wfm.virtualrolemgr;
import java.util.Map;
import nc.uap.cpb.org.querycmd.QueryCmd;
import nc.uap.lfw.core.bm.ButtonStateManager;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifAddCmd;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifDelCmd;
import nc.uap.lfw.core.cmd.UifSaveCmd;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.vo.LfwExAggVO;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmVirtualRoleVO;
import nc.vo.pub.AggregatedValueObject;
public class WfmVirtRoleMainCtrl {
	public void addVirtRole(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "add");
		new UifAddCmd("virroleds").execute();
	}
	public void updateVirtRole(MouseEvent<MenuItem> e) {
		AppUtil.addAppAttr("operator", "update");
		AppUtil.getWidget("main").getViewModels().getDataset("virroleds").setEnabled(true);
		ButtonStateManager.updateButtons();
	}
	public void deleteVirtRole(MouseEvent<MenuItem> e) {
		new UifDelCmd("virroleds", LfwExAggVO.class.getName()).execute();
		new UifDatasetLoadCmd("virroleds").execute();
	}
	public void cancelVirtRole(MouseEvent<MenuItem> e) {
		new UifDatasetLoadCmd("virroleds").execute();
	}
	public void saveVirtRole(MouseEvent<MenuItem> e) {
		new UifSaveCmd("virroleds", null, LfwExAggVO.class.getName(), true) {
			protected void onBeforeVOSave(AggregatedValueObject aggVo) {
				WfmVirtualRoleVO wfmViraRoleVo = (WfmVirtualRoleVO) aggVo.getParentVO();
				if (wfmViraRoleVo.getName() == null || wfmViraRoleVo.getName().length() == 0) {
					throw new LfwRuntimeException("虚拟角色名称不能为空");
				}
				if (wfmViraRoleVo.getServiceclass() == null || wfmViraRoleVo.getServiceclass().length() == 0) {
					throw new LfwRuntimeException("服务类不能为空");
				}
				if ("add".equalsIgnoreCase((String) AppUtil.getAppAttr("operator"))) {
					try {
						WfmVirtualRoleVO oldViraRoleVo = WfmServiceFacility.getVirtualRoleQry().getRoleByName(wfmViraRoleVo.getName());
						if (oldViraRoleVo != null) {
							throw new LfwRuntimeException("虚拟角色名称重复");
						}
					} catch (WfmServiceException e1) {
						LfwLogger.error(e1.getMessage(), e1);
						throw new LfwRuntimeException(e1.getMessage());
					}
				}
			}
		}.execute();
	}
	public void wfmVirtRoleOnLoad(DataLoadEvent se) {
		new UifDatasetLoadCmd("virroleds").execute();
	}
	public void pluginsimpleQuery_plugin(Map<Object,Object> keys) {
		FromWhereSQL whereSql = (FromWhereSQL) keys.get("whereSql");
		String wheresql = whereSql.getWhere();
		QueryCmd cmd = new QueryCmd("main", "virroleds", wheresql);
		cmd.excute();
	}
	public void wfmVirtRole_onAfterRowSelect(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
}
