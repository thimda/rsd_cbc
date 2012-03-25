package nc.uap.cpb.org.user;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.event.MouseEvent;
public class CpbUserQuryCtrl {
	public void cpbUserQuryBtnOk(MouseEvent<ButtonComp> e) {
	// LfwWidget mainwidget =
	// this.getGlobalContext().getPageMeta().getWidget(UserMgrConstants.Widget_Main);
	// Dataset dsUserRole =
	// mainwidget.getViewModels().getDataset(UserMgrConstants.Ds_UserRole);
	// dsUserRole.clear();
	// Dataset dsUserGroupRole =
	// mainwidget.getViewModels().getDataset(UserMgrConstants.Ds_UserGroupRole);
	// dsUserGroupRole.clear();
	// LfwWidget widget =
	// getGlobalContext().getPageMeta().getWidget(getWidgetId());
	// Dataset ds = widget.getViewModels().getDataset(getDataset());
	// Row row = ds.getSelectedRow();
	// String clazz = ds.getVoMeta();
	// SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
	// LfwWidget pwidget =
	// getGlobalContext().getPageMeta().getWidget(getPWidgetId());
	// Dataset userds =
	// pwidget.getViewModels().getDataset(UserMgrConstants.Ds_User);
	// // Ä¬ÈÏÌõ¼þ
	// ReferenceComp groupText = (ReferenceComp)
	// pwidget.getViewComponents().getComponent(UserMgrConstants.Text_Group);
	// String pk_group = groupText.getValue();
	// Dataset dsOrgs =
	// pwidget.getViewModels().getDataset(UserMgrConstants.Ds_Orgs);
	// Row corpRow = dsOrgs.getSelectedRow();
	// Map<String, Object> whereMap = new HashMap<String, Object>();
	// if (pk_group != null && !"".equals(pk_group)) {
	// whereMap.put(CpUserVO.PK_GROUP, pk_group);
	// }
	// if (corpRow != null) {
	// whereMap.put(CpUserVO.PK_ORG,
	// corpRow.getValue(dsOrgs.nameToIndex(OrgVO.PK_ORG)));
	// }
	// String user_code = (String)
	// row.getValue(ds.nameToIndex(CpUserVO.USER_CODE));
	// String username = (String)
	// row.getValue(ds.nameToIndex(CpUserVO.USER_NAME));
	// if (user_code != null) {
	// whereMap.put(CpUserVO.USER_CODE, user_code);
	// }
	// if (username != null) {
	// whereMap.put(CpUserVO.USER_NAME, username);
	// }
	// onDataLoad(vo, userds, whereMap, true);
	// widget.setVisible(false);
	}
	public void cpbUserQuryBtnCancel(MouseEvent<ButtonComp> e) {}
}
