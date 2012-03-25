package nc.uap.wfm.exetask;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.TextAreaComp;
import nc.uap.lfw.core.comp.text.ComboBoxComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.LinkEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.utils.WfmTaskUtil;
/**
 * @author chouhl
 */
public class ExeTaskMainCtrl implements IController {
	private static final long serialVersionUID = 1L;
	private String taskPk;
	private String flowTypePk;
	public void btnok_click(MouseEvent<ButtonComp> mouseEvent) {
		this.initParam();
		this.addAppAttr();
		new UifPlugoutCmd(WfmConstants.PUBVIEW_EXETASK, "wf_pluginout").execute();
	}
	private void addAppAttr() {
		AppUtil.addAppAttr(WfmConstants.FlwTypePk, flowTypePk);
		AppUtil.addAppAttr(WfmConstants.TaskPk, taskPk);
		AppUtil.addAppAttr(WfmConstants.Operator, this.getOperator());
		AppUtil.addAppAttr(WfmConstants.Opinion, this.getOpinion());
		AppUtil.addAppAttr(WfmConstants.TransmitUserPk, this.getTransmitUserPk());
		AppUtil.addAppAttr(WfmConstants.AfterAddSignUserPks, this.getAfterAddSignUserPk());
	}
	/**
	 * ��ȡ�����Ķ���
	 * 
	 * @return
	 */
	protected String getOperator() {
		LfwWidget view_exetask = WfmTaskUtil.getExeTaskView();
		TextComp text_exeaction = (TextComp) view_exetask.getViewComponents().getComponent("text_exeaction");
		String value = text_exeaction.getValue();
		return value;
	}
	/**
	 * ��ȡ�����Ķ���
	 * 
	 * @return
	 */
	protected String getOpinion() {
		LfwWidget view_exetask = WfmTaskUtil.getExeTaskView();
		TextComp text_opinion = (TextComp) view_exetask.getViewComponents().getComponent("text_opinion");
		String value = text_opinion.getValue();
		return value;
	}
	protected String getTransmitUserPk() {
		LfwWidget view_exetask = WfmTaskUtil.getExeTaskView();
		TextComp text_transmituser = (TextComp) view_exetask.getViewComponents().getComponent("text_transmituser");
		String value = text_transmituser.getValue();
		return value;
	}
	protected String getAfterAddSignUserPk() {
		LfwWidget view_exetask = WfmTaskUtil.getExeTaskView();
		TextComp text_addSignuser = (TextComp) view_exetask.getViewComponents().getComponent("text_afteraddsign");
		String value = text_addSignuser.getValue();
		return value;
	}
	/**
	 * ��ʼ������ֵ
	 */
	protected void initParam() {
		taskPk = (String) AppUtil.getAppAttr(WfmConstants.TaskPk);
		flowTypePk = (String) AppUtil.getAppAttr(WfmConstants.FlwTypePk);
	}
	public void textValueChanged(TextEvent textEvent) {
		this.initParam();
		this.addAppAttr();
		String operator = WfmTaskUtil.getOperator();
		String str = null;
		if (WfmConstants.WfmOperator_Agree.equalsIgnoreCase(operator) || "noagree".equalsIgnoreCase(operator) || WfmConstants.WfmOperator_Reject.equalsIgnoreCase(operator)
				|| "stop".equalsIgnoreCase(operator)) {
			str = "window.pageUI.getWidget('pubview_exetask').getCard('card1').setPage(0)";
		}
		if (WfmConstants.WfmOperator_Transmit.equalsIgnoreCase(operator)) {
			str = "window.pageUI.getWidget('pubview_exetask').getCard('card1').setPage(1)";
		}
		if ("befaddsign".equalsIgnoreCase(operator)) {
			LfwWidget widget = AppUtil.getWidget("pubview_exetask");
			Dataset dsDataset = widget.getViewModels().getDataset("ds_beforeaddsign");
			dsDataset.clear();
			Row row1 = dsDataset.getEmptyRow();
			row1.setValue(0, "��һ��");
			dsDataset.addRow(row1);
			Row row2 = dsDataset.getEmptyRow();
			row2.setValue(0, "�ڶ���");
			dsDataset.addRow(row2);
			Row row3 = dsDataset.getEmptyRow();
			dsDataset.addRow(row3);
			row3.setValue(0, "������");
			dsDataset.setRowSelectIndex(0);
			dsDataset.setEnabled(true);
			str = "window.pageUI.getWidget('pubview_exetask').getCard('card1').setPage(2)";
		}
		AppUtil.getCntAppCtx().addExecScript(str);
	}
	public void historyClick(LinkEvent linkEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("pubview_history", "600", "430", "������ʷ");
	}
	public void afterAddSignClick(LinkEvent linkEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("pubview_afteraddsign", "600", "430", "���ǩ");
	}
	public void deliverClick(LinkEvent linkEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("pubview_deliver", "600", "430", "����");
	}
	public void flowImgClick(LinkEvent linkEvent) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().popView("pubview_flowimg", "730", "430", "���̽���");
	}
	public void commonword1_valueChanged(TextEvent textEvent) {
		LfwWidget widget = AppUtil.getWidget("pubview_exetask");
		TextAreaComp textComp = (TextAreaComp) widget.getViewComponents().getComponent("text_opinion");
		ComboBoxComp comBoxComp = (ComboBoxComp) textEvent.getSource();
		String value = comBoxComp.getValue();
		String oldValue = textComp.getValue();
		value = oldValue + value;
		textComp.setValue(value);
	}
	public void commonword2_valueChanged(TextEvent textEvent) {
		LfwWidget widget = AppUtil.getWidget("pubview_exetask");
		TextAreaComp textComp = (TextAreaComp) widget.getViewComponents().getComponent("text_tranopinion");
		ComboBoxComp comBoxComp = (ComboBoxComp) textEvent.getSource();
		String value = comBoxComp.getValue();
		String oldValue = textComp.getValue();
		value = oldValue + value;
		textComp.setValue(value);
	}
	public void commonword3_valueChanged(TextEvent textEvent) {
		LfwWidget widget = AppUtil.getWidget("pubview_exetask");
		TextAreaComp textComp = (TextAreaComp) widget.getViewComponents().getComponent("text_addsignopinion");
		ComboBoxComp comBoxComp = (ComboBoxComp) textEvent.getSource();
		String value = comBoxComp.getValue();
		String oldValue = textComp.getValue();
		value = oldValue + value;
		textComp.setValue(value);
	}
}
