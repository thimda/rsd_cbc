package nc.uap.cpb.org.user;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
public class CpbUserRelaCtrl {
	public void cpbUserRelaBtnOk(MouseEvent<ButtonComp> e) {}
	public void cpbUserRelaBtnCancel(MouseEvent<ButtonComp> e) {}
	public void cpbUserRelaOrgDsLoad(DatasetEvent se) {
	// String groupPk = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
	// try {
	// OrgVO[] vos =
	// CpbServiceFacility.getCpOrgQry().getAllOrgVOSByGroupIDAndOrgTypes(groupPk,
	// new String[] { IOrgConst.CORPORGTYPE, IOrgConst.DEPTORGTYPE }, false);
	// new SuperVO2DatasetSerializer().serialize(vos, se.getSource(),
	// Row.STATE_NORMAL);
	// postProcessRowSelect(se.getSource());
	// } catch (BusinessException e) {
	// throw new LfwRuntimeException(e.getMessage());
	// }
	}
}
