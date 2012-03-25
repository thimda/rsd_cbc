package nc.uap.cpb.org.user;
import java.util.Map;
import nc.bs.logging.Logger;
import nc.login.vo.INCUserTypeConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.exception.LfwValidateException;
import nc.uap.lfw.core.model.plug.TranslatedRow;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.uif.delegator.DefaultDataValidator;
import nc.uap.lfw.core.uif.delegator.IDataValidator;
import nc.uap.wfm.utils.AppUtil;
import nc.vo.pub.lang.UFDateTime;
public class CpbUserEditCtrl {
	public void cpbUserEditBtnOk(MouseEvent<ButtonComp> e) {
		new UifPlugoutCmd("edit", "useredit_pluginout").execute();
	}
	public void cpbUserEditBtnCancel(MouseEvent<ButtonComp> e) {
		AppUtil.getCntAppCtx().getCurrentWindowContext().closeViewDialog("edit");
	}
	protected void checkDupliUserVO(CpUserVO userVo) {
		String userId = userVo.getUser_code();
		try {
			CpUserVO userVO = CpbServiceFacility.getCpUserQry().getUserByCode(userId);
			if (userVO == null) {
				return;
			}
			if (userVo.getCuserid() == null || userVO.getCuserid() != null && !userVO.getCuserid().equals(userVo.getCuserid())) {
				throw new LfwRuntimeException("用户编码已经存在,请重新输入!");
			}
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}
	protected void checkDupliUserVO1(CpUserVO userVo) {
		String userId = userVo.getCuserid();
		try {
			CpUserVO oldUserVo = CpbServiceFacility.getCpUserQry().getUserByPk(userId);
			if (oldUserVo == null) {
				return;
			} else {
				if (!oldUserVo.getUser_code().equalsIgnoreCase(userVo.getUser_code())) {
					this.checkDupliUserVO(userVo);
				}
			}
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}
	protected void doValidate(Dataset masterDs) throws LfwValidateException {
		IDataValidator validator = new DefaultDataValidator();
		validator.validate(masterDs, new LfwWidget());
	}
	public void pluginuseredit_pluginin(Map<String, Object> map) {
		LfwWidget widgetEdit = AppUtil.getWidget(UserMgrConstants.Widget_Edit);
		Dataset dsUser = widgetEdit.getViewModels().getDataset(UserMgrConstants.Ds_User);
		String operator = (String) AppUtil.getAppAttr("operator");
		Row emptyRow = dsUser.getEmptyRow();
		if ("add".equalsIgnoreCase(operator)) {
			String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
			dsUser.clear();
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.PK_ORG), LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit());
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.PK_GROUP), groupPk);
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.CREATIONTIME), new UFDateTime());
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.CREATOR), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.USER_TYPE), INCUserTypeConstant.USER_TYPE_USER);
			emptyRow.setValue(dsUser.nameToIndex("enablestate"), ICpUserConst.ENABLESTATE_ACTIVE);
			//身份类型为员工
			ComboData combo = widgetEdit.getViewModels().getComboData("combo_cp_user_base_doc_type");
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.BASE_DOC_TYPE), combo.getAllCombItems()[0].getValue());
			dsUser.setCurrentKey(Dataset.MASTER_KEY);
			dsUser.addRow(emptyRow);
			dsUser.setSelectedIndex(dsUser.getRowIndex(emptyRow));
			dsUser.setEnabled(true);
		} else if ("update".equalsIgnoreCase(operator)) {
			setValues(emptyRow, dsUser, map);
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.MODIFIER), LfwRuntimeEnvironment.getLfwSessionBean().getUser_name());
			emptyRow.setValue(dsUser.nameToIndex(CpUserVO.MODIFIEDTIME), new UFDateTime());
			emptyRow.setState(Row.STATE_UPDATE);
			dsUser.setCurrentKey(Dataset.MASTER_KEY);			
			dsUser.addRow(emptyRow);
			dsUser.setSelectedIndex(dsUser.getRowIndex(emptyRow));
			dsUser.setEnabled(true);
		}
	}
	private void setValues(Row row, Dataset ds, Map<String, Object> map) {
		TranslatedRow r = (TranslatedRow) map.get("selectedrow");
		String[] keys = r.getKeys();
		for (String key : keys) {
			row.setValue(ds.nameToIndex(key), r.getValue(key));
		}
	}
}
