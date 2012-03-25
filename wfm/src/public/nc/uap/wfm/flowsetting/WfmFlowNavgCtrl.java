package nc.uap.wfm.flowsetting;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlwCatVO;
/**
 * @author chouhl
 */
public class WfmFlowNavgCtrl implements IController {
	private static final long serialVersionUID = 1L;
	public void wfmFlowCateOnLoad(DataLoadEvent se) {
		Dataset dsMainFlowCate = se.getSource();
		WfmFlwCatVO[] vos = WfmServiceFacility.getFlowCateQry().getAllFlowCate();
		new SuperVO2DatasetSerializer().serialize(vos, dsMainFlowCate, Row.STATE_NORMAL);
	}
	public void wfmFlowOnAfterRow(DatasetEvent se) {
		AppUtil.addAppAttr("operator", "onload");
		new UifPlugoutCmd("navg", "flownavg_pluginout").execute();
	}
}
