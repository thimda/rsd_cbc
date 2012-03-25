package nc.uap.wfm.assign;
import java.util.List;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.actorsgy.ActorSgyManager;
import nc.uap.wfm.builder.WfmCommitFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmNextFlowInfoCtxBuilder;
import nc.uap.wfm.builder.WfmRejectFlowInfoCtxBuilder;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.contanier.ProDefsContainer;
import nc.uap.wfm.context.HumActInfoPageCtx;
import nc.uap.wfm.context.WfmFlowInfoCtx;
import nc.uap.wfm.engine.IFlowRequest;
import nc.uap.wfm.engine.IFlowResponse;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.rejectsgy.RejectSgyManager;
import nc.uap.wfm.runtime.NextHumActInfoUtil;
import nc.uap.wfm.runtime.RejectHumActInfoUtil;
import nc.uap.wfm.server.BizProcessServer;
import nc.uap.wfm.trans.WfmTransUtil;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import org.apache.commons.lang.StringUtils;
/**
 * @author chouhl
 */
public class AssignMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	protected String taskPk;
	protected String flowTypePk;
	/**
	 * 按钮点击后的执行动作
	 * 
	 * @param mouseEvent
	 */
	public void btnOk_onclick(MouseEvent<ButtonComp> mouseEvent) {
		this.initParam();
		String src = mouseEvent.getSource().getId();
		LfwWidget assignWidget = WfmTaskUtil.getAssignView();
		Dataset dsAssignAct = assignWidget.getViewModels().getDataset(WfmConstants.DsHUMACT);
		Dataset dsAllActors = assignWidget.getViewModels().getDataset(WfmConstants.DsAllActors);
		Dataset dsSelecteds = assignWidget.getViewModels().getDataset(WfmConstants.DsSelectedActors);
		Row nextHumActRow = dsAssignAct.getSelectedRow();
		String isAssign = (String) nextHumActRow.getValue(dsAssignAct.nameToIndex(WfmConstants.IsAssign));
		String proDefPk = (String) nextHumActRow.getValue(dsAssignAct.nameToIndex(WfmConstants.ProDefPk));
		String proDefId = (String) nextHumActRow.getValue(dsAssignAct.nameToIndex(WfmConstants.ProDefId));
		String portId = (String) nextHumActRow.getValue(dsAssignAct.nameToIndex(WfmConstants.HumActId));
		HumAct nextHumAct = (HumAct) ProDefsContainer.getByProDefPkAndId(proDefPk, proDefId).getPorts().get(portId);
		if (WfmConstants.RightOne.equalsIgnoreCase(src)) {
			if (WfmConstants.StrFalse.equalsIgnoreCase(isAssign)) {
				throw new LfwRuntimeException("该活动节点不需要指派");
			}
			Row selectedRow = dsAllActors.getSelectedRow();
			if (selectedRow == null) {
				return;
			}
			if (dsSelecteds.getCurrentRowData() != null) {
				Row[] allRightRow = dsSelecteds.getCurrentRowData().getRows();
				if (allRightRow != null && allRightRow.length >= 1) {
					if (!nextHumAct.isNotMulti()) {
						throw new LfwRuntimeException("只能指派一个参与者");
					}
				}
			}
			dsAllActors.removeRow(selectedRow);
			dsSelecteds.setCurrentKey(Dataset.MASTER_KEY);
			dsSelecteds.addRow(selectedRow);
			dsSelecteds.setRowSelectIndex(dsSelecteds.getRowIndex(selectedRow));
			this.setUserNameAndUserPks();
		} else if (WfmConstants.RightAll.equalsIgnoreCase(src)) {
			if (WfmConstants.StrFalse.equalsIgnoreCase(isAssign)) {
				throw new LfwRuntimeException("该活动节点不需要指派");
			}
			if (dsSelecteds.getCurrentRowData() != null) {
				Row[] allRightRow = dsSelecteds.getCurrentRowData().getRows();
				if (allRightRow != null && allRightRow.length >= 1) {
					if (!nextHumAct.isNotMulti()) {
						throw new LfwRuntimeException("只能指派一个参与者");
					}
				}
			}
			Row[] allLeftRows = dsAllActors.getCurrentRowData().getRows();
			if (allLeftRows != null && allLeftRows.length > 1) {
				if (!nextHumAct.isNotMulti()) {
					throw new LfwRuntimeException("只能指派一个参与者");
				}
			}
			dsSelecteds.setCurrentKey(Dataset.MASTER_KEY);
			if (allLeftRows == null || allLeftRows.length == 0) {
				return;
			}
			Row tmpRow = null;
			for (int i = 0; i < allLeftRows.length; i++) {
				tmpRow = allLeftRows[i];
				dsAllActors.removeRow(tmpRow);
				dsSelecteds.addRow(tmpRow);
			}
			this.setUserNameAndUserPks();
		} else if (WfmConstants.LeftOne.equalsIgnoreCase(src)) {
			if (WfmConstants.StrFalse.equalsIgnoreCase(isAssign)) {
				throw new LfwRuntimeException("该活动节点不需要指派");
			}
			Row selectedRow = dsSelecteds.getSelectedRow();
			if (selectedRow == null) {
				return;
			}
			dsSelecteds.removeRow(selectedRow);
			dsAllActors.setCurrentKey(Dataset.MASTER_KEY);
			dsAllActors.addRow(selectedRow);
			this.setUserNameAndUserPks();
		} else if (WfmConstants.LeftAll.equalsIgnoreCase(src)) {
			if (WfmConstants.StrFalse.equalsIgnoreCase(isAssign)) {
				throw new LfwRuntimeException("该活动节点需要指派");
			}
			Row[] allRows = dsSelecteds.getCurrentRowData().getRows();
			dsAllActors.setCurrentKey(Dataset.MASTER_KEY);
			if (allRows == null || allRows.length == 0) {
				return;
			}
			Row tmpRow = null;
			for (int i = 0; i < allRows.length; i++) {
				tmpRow = allRows[i];
				dsSelecteds.removeRow(tmpRow);
				dsAllActors.addRow(tmpRow);
			}
			this.setUserNameAndUserPks();
		} else if (WfmConstants.Ok.equalsIgnoreCase(src)) {
			WfmFlowInfoCtx flwInfoCtx = null;
			if ("agree".equalsIgnoreCase(this.getActionValue()) || "noagree".equalsIgnoreCase(this.getActionValue())) {
				if (taskPk == null || taskPk.length() == 0) {
					flwInfoCtx = new WfmCommitFlowInfoCtxBuilder(flowTypePk, taskPk).builderCommitFlwInfoCtx();
				} else {
					flwInfoCtx = new WfmNextFlowInfoCtxBuilder(flowTypePk, taskPk).builderNextFlwInfoCtx();
				}
			}
			if ("reject".equalsIgnoreCase(this.getActionValue())) {
				flwInfoCtx = new WfmRejectFlowInfoCtxBuilder(flowTypePk, taskPk).builderRejectFlwInfoCtx();
			}
			IFlowRequest request = BizProcessServer.createFlowRequest(WfmTaskUtil.getWfmFormInfoCtx(), flwInfoCtx);
			IFlowResponse response = BizProcessServer.createFlowResponse();
			BizProcessServer.exe(request, response);
			AppLifeCycleContext.current().getApplicationContext().addExecScript("alert('执行成功');");
			AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeView(WfmConstants.PUBVIEW_ASSIGN);
		} else if (WfmConstants.Cancel.equalsIgnoreCase(src)) {
			AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().closeView(WfmConstants.PUBVIEW_ASSIGN);
		}
	}
	public void setUserNameAndUserPks() {
		LfwWidget assignWidget = WfmTaskUtil.getAssignView();
		Dataset dsAssignAct = assignWidget.getViewModels().getDataset(WfmConstants.DsHUMACT);
		Dataset dsSelecteds = assignWidget.getViewModels().getDataset(WfmConstants.DsSelectedActors);
		Row nextHumActRow = dsAssignAct.getSelectedRow();
		if (nextHumActRow == null || dsSelecteds.getCurrentRowData() == null || dsSelecteds.getCurrentRowData().getRows() == null || dsSelecteds.getCurrentRowData().getRows().length == 0) {
			nextHumActRow.setValue(dsAssignAct.nameToIndex(WfmConstants.UserNames), "");
			nextHumActRow.setValue(dsAssignAct.nameToIndex(WfmConstants.UserPks), "");
			assignWidget.setVisible(false);
			return;
		}
		Row[] selectedRows = dsSelecteds.getCurrentRowData().getRows();
		StringBuffer userNames = new StringBuffer();
		StringBuffer userPks = new StringBuffer();
		for (int i = 0; i < selectedRows.length; i++) {
			Row tmpRow = selectedRows[i];
			String userName = (String) tmpRow.getValue(dsSelecteds.nameToIndex(WfmConstants.UserName));
			String userPk = (String) tmpRow.getValue(dsSelecteds.nameToIndex(WfmConstants.UserPk));
			if (StringUtils.isNotBlank(userPk) && StringUtils.isNotBlank(userName)) {
				userNames.append(userName).append(",");
				userPks.append(userPk).append(",");
			}
		}
		if (userNames.length() > 1) {
			userNames.deleteCharAt(userNames.length() - 1);
		}
		if (userPks.length() > 1) {
			userPks.deleteCharAt(userPks.length() - 1);
		}
		nextHumActRow.setValue(dsAssignAct.nameToIndex(WfmConstants.UserPks), userPks.toString());
		nextHumActRow.setValue(dsAssignAct.nameToIndex(WfmConstants.UserNames), userNames.toString());
	}
	/**
	 * 数据集加载的事件处理
	 * 
	 * @param mouseEvent
	 */
	public void dsHumAct_onDataload(DataLoadEvent se) {
		this.initParam();
		Dataset dsNextHumAct = se.getSource();
		dsNextHumAct.setCurrentKey(Dataset.MASTER_KEY);
		Task task = null;
		ProDef proDef = null;
		if (taskPk == null || taskPk.length() == 0) {
			proDef = ProDefsContainer.getProDefByFlowTypePk(flowTypePk);
		} else {
			task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			proDef = task.getProDef();
		}
		WfmFormInfoCtx formVo = WfmTaskUtil.getWfmFormInfoCtx();
		List<HumActInfoPageCtx> nextInfo = null;
		if ("agree".equalsIgnoreCase(this.getActionValue()) || "noagree".equalsIgnoreCase(this.getActionValue())) {
			if (task == null) {
				nextInfo = new NextHumActInfoUtil().getStartNextHumActInfo(proDef.getFlwtype().getPk_flwtype(), formVo, false);
			} else {
				nextInfo = new NextHumActInfoUtil().getNextHumActInfo(task, formVo, false);
			}
		}
		if ("reject".equalsIgnoreCase(this.getActionValue())) {
			nextInfo = new RejectHumActInfoUtil().getRejectHumActInfo(task);
		}
		if (nextInfo == null || nextInfo.size() == 0) {
			return;
		}
		int size = nextInfo.size();
		Row tmpRow = null;
		HumActInfoPageCtx pageInfo = null;
		for (int i = 0; i < size; i++) {
			pageInfo = nextInfo.get(i);
			if (pageInfo == null) {
				continue;
			}
			tmpRow = dsNextHumAct.getEmptyRow();
			tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.HumActId), pageInfo.getPortId());
			tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.HumActName), pageInfo.getPortName());
			tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.ProDefId), proDef.getId());
			tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.ProDefPk), proDef.getPk_prodef());
			tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.IsAssign), pageInfo.isAssign());
			if (!pageInfo.isAssign()) {
				tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.UserPks), pageInfo.getUserPks());
				tmpRow.setValue(dsNextHumAct.nameToIndex(WfmConstants.UserNames), pageInfo.getUserNames());
			}
			dsNextHumAct.addRow(tmpRow);
		}
		if (size > 0) {
			dsNextHumAct.setRowSelectIndex(0);
		}
	}
	/**
	 * 数据选中后的事件处理
	 * 
	 * @param se
	 */
	public void dsHumAct_onAfterRowSelected(DatasetEvent se) {
		this.initParam();
		LfwWidget assignView = WfmTaskUtil.getAssignView();
		Dataset dsAssignAct = assignView.getViewModels().getDataset(WfmConstants.DsHUMACT);
		Dataset dsSelecteds = assignView.getViewModels().getDataset(WfmConstants.DsSelectedActors);
		dsSelecteds.clear();
		Row focusRow = dsAssignAct.getSelectedRow();
		if (focusRow == null) {
			return;
		}
		String proDefId = (String) focusRow.getValue(dsAssignAct.nameToIndex(WfmConstants.ProDefId));
		String proDefPk = (String) focusRow.getValue(dsAssignAct.nameToIndex(WfmConstants.ProDefPk));
		if (proDefId == null || proDefId.length() == 0) {
			return;
		}
		ProDef proDef = ProDefsContainer.getByProDefPkAndId(proDefPk, proDefId);
		String humActId = (String) focusRow.getValue(dsAssignAct.nameToIndex(WfmConstants.HumActId));
		WfmFormInfoCtx formVo = WfmTaskUtil.getWfmFormInfoCtx();
		String actionValue = this.getActionValue();
		Dataset dsAllActors = assignView.getViewModels().getDataset(WfmConstants.DsAllActors);
		dsAllActors.clear();
		if ("agree".equalsIgnoreCase(actionValue) || "noagree".equalsIgnoreCase(actionValue)) {
			String[] actors = ActorSgyManager.getInstance().getActorsRange(formVo, null, proDef.getHumActs().get(humActId), null);
			if (actors == null || actors.length == 0) {
				return;
			}
			String userPk = null;
			Row tmpRow = null;
			for (int j = 0; j < actors.length; j++) {
				userPk = actors[j];
				tmpRow = dsAllActors.getEmptyRow();
				tmpRow.setValue(dsAllActors.nameToIndex(WfmConstants.PkUser), userPk);
				tmpRow.setValue(dsAllActors.nameToIndex(WfmConstants.UserName), WfmTransUtil.getUserNameByUserPk(userPk));
				dsAllActors.addRow(tmpRow);
			}
		}
		if ("reject".equalsIgnoreCase(actionValue)) {
			Task task = WfmTaskUtil.getTaskByTaskPk(taskPk);
			CpUserVO[] vos = RejectSgyManager.getInstance().getRejectUsersByTaskAndHumAct(task, proDef.getHumActs().get(humActId));
			if (vos == null || vos.length == 0) {
				return;
			}
			CpUserVO vo = null;
			Row row = null;
			for (int j = 0; j < vos.length; j++) {
				vo = vos[j];
				row = dsAllActors.getEmptyRow();
				row.setValue(dsAllActors.nameToIndex(WfmConstants.PkUser), vo.getCuserid());
				row.setValue(dsAllActors.nameToIndex(WfmConstants.UserName), vo.getUser_name());
				dsAllActors.addRow(row);
			}
		}
	}
	/**
	 * 获取操作的动作
	 * 
	 * @return
	 */
	protected String getActionValue() {
		LfwWidget view_exetask = WfmTaskUtil.getExeTaskView();
		TextComp text_exeaction = (TextComp) view_exetask.getViewComponents().getComponent("text_exeaction");
		String value = text_exeaction.getValue();
		return value;
	}
	/**
	 * 初始化变量值
	 */
	protected void initParam() {
		taskPk = (String) AppUtil.getAppAttr(WfmConstants.TaskPk);
		flowTypePk = (String) AppUtil.getAppAttr(WfmConstants.FlwTypePk);
		// flowTypePk = "0000Z7100000000091N0";
		taskPk = "0000AA1000000001WDGR";
	}
}
