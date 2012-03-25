package nc.uap.cpb.org.resource;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpResourceVO;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.Dataset2SuperVOSerializer;
import nc.uap.lfw.core.uif.delegator.UifSaveDelegator;
import nc.vo.pub.SuperVO;

public class ResourceSaveDelegator extends UifSaveDelegator {
	private String widgetId;
	private String masterDsId;

	public ResourceSaveDelegator(String widgetId, String masterDsId, String[] detailDsIds, String aggVoClazz, boolean bodyNotNull) {
		super(widgetId, masterDsId, detailDsIds, aggVoClazz, bodyNotNull);
		this.widgetId = widgetId;
		this.masterDsId = masterDsId;
	}

	public void execute() {
		LfwWidget widget = this.getGlobalContext().getPageMeta().getWidget(widgetId);
		Dataset ds = widget.getViewModels().getDataset(masterDsId);
		Row[] rows = ds.getCurrentRowData().getRows();
		Dataset2SuperVOSerializer ser = new Dataset2SuperVOSerializer();
		SuperVO[] supervos = ser.serialize(ds, rows);
		int size = supervos == null ? 0 : supervos.length;
		CpResourceVO[] vos = new CpResourceVO[size];
		for (int i = 0; i < size; i++) {
			vos[i] = (CpResourceVO) supervos[i];
		}
		try {
			CpbServiceFacility.getCpResourceBill().update(vos);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage());
			throw new LfwRuntimeException("保存资源出错");
		}

	}

}
