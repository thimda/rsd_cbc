package nc.uap.wfm.wfdesign;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.MouseServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.tags.DynamicCompUtil;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.vo.WfmProdefVO;
import nc.vo.pub.SuperVO;
public class SaveProDefMouserListener extends MouseServerListener<ButtonComp> {
	public SaveProDefMouserListener(LfwPageContext context, String widgetId) {
		super(context, widgetId);
	}
	public void onclick(MouseEvent<ButtonComp> e) {
		LfwWidget editWidget = this.getGlobalContext().getPageMeta().getWidget("edit");
		Dataset dsProDef = editWidget.getViewModels().getDataset("ds_prodef");
		Dataset2SuperVOSerializer<SuperVO> serialize = new Dataset2SuperVOSerializer<SuperVO>();
		SuperVO[] superVos = serialize.serialize(dsProDef, dsProDef.getSelectedRow());
		if (superVos == null || superVos.length == 0) {
			return;
		}
		SuperVO superVo = superVos[0];
		boolean flag = true;
		String strPk = superVo.getPrimaryKey();
		WfmProdefVO proDefVo = null;
		if (strPk == null || strPk.length() == 0) {
			flag = true;
		} else {
			try {
				proDefVo = WfmServiceFacility.getProDefQry().getProDefVOByProDefPk(strPk);
			} catch (WfmServiceException e1) {
				throw new LfwRuntimeException(e1.getMessage());
			}
			flag = false;
		}
		if (flag) {
			try {
				CpbServiceFacility.getCpSuperVOBill().insertSuperVO(superVo);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		} else {
			if (proDefVo != null) {
				((WfmProdefVO) superVo).setProcessstr(proDefVo.getProcessstr());
			}
			try {
				CpbServiceFacility.getCpSuperVOBill().updateSuperVO(superVo);
			} catch (CpbBusinessException e1) {
				LfwLogger.error(e1.getMessage(), e1);
				throw new LfwRuntimeException(e1.getMessage());
			}
		}
		Dataset dsMaimProDef = this.getGlobalContext().getPageMeta().getWidget("main").getViewModels().getDataset("ds_prodef");
		new DynamicCompUtil(this.getGlobalContext(), this.getGlobalContext().getWidgetContext("main")).refreshDataset(dsMaimProDef);
		editWidget.setVisible(false);
	}
}
