package nc.uap.wfm.flowtype;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.itf.IWfmFlowCateQry;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlwCatVO;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public class FlowTypeCtrl {
	public void oneDsLoad(DataLoadEvent se) {
		IWfmFlowCateQry wfmFlowCatQry = NCLocator.getInstance().lookup(IWfmFlowCateQry.class);
		WfmFlwCatVO[] vos = wfmFlowCatQry.getAllFlowCate();
		Dataset dsFlowCate = se.getSource();
		new SuperVO2DatasetSerializer().serialize(vos, dsFlowCate, Row.STATE_NORMAL);
	}
	public void twoDsLoad(DataLoadEvent se) {
		new UifDatasetLoadCmd(se.getSource().getId()).execute();
		return;
	}
	public void twoDs_onAfterRowSelect(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		Row selectedRow = ds.getSelectedRow();
		String flowTypePk = (String) selectedRow.getValue(ds.nameToIndex(WfmFlwTypeVO.PK_FLWTYPE));
		AppUtil.addAppAttr("operator", "twoDs_onAfterRowSelect");
		AppUtil.addAppAttr(WfmFlwTypeVO.PK_FLWTYPE, flowTypePk);
		new UifPlugoutCmd("pubview_flowtype", "out_flowtype").execute();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
}
