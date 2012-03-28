package nc.uap.wfm.flowmonitor;

import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetAfterSelectCmd;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.wfm.utils.AppUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmProInsVO;
import nc.vo.pub.SuperVO;

/**
 * @author chenwl
 */
public class MainCtrl implements IController {
	private static final long serialVersionUID = 1L;

	/**
	 * 获得当前controller的view
	 * 
	 * @return
	 */
	public LfwWidget getView() {
		return AppLifeCycleContext.current().getWindowContext().getViewContext("main").getView();
	}

	/**
	 * 获得当前view下指定的dataset
	 * 
	 * @param dsid
	 * @return
	 */
	public Dataset getDataset(String dsid) {
		return getView().getViewModels().getDataset(dsid);
	}

	/**
	 * 根据业务类型查询流程实例
	 * 
	 * @param keys
	 */
	public void pluginplugin_flowtype2main(Map keys) {
		String flowTypePk = (String) AppUtil.getAppAttr(WfmFlwTypeVO.PK_FLWTYPE);
		System.out.println(flowTypePk);
		Dataset ds = getDataset("wfmproins_ds");
		String sql = "select * from wfm_proins pi,wfm_prodef pd where pd.pk_prodef = pi.pk_prodef and pd.flwtype = ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(flowTypePk);
		try {
			Object object = new PtBaseDAO().executeQuery(sql, parameter,new BeanListProcessor(WfmProInsVO.class));
			SuperVO[] vos = (SuperVO[]) ((List) object).toArray(new WfmProInsVO[0]);
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据选中的流程实例查询流程实例状态
	 * @param datasetEvent
	 */
	public void onAfterRowSelect_proins(DatasetEvent datasetEvent) {
		Dataset ds = datasetEvent.getSource();
		CmdInvoker.invoke(new UifDatasetAfterSelectCmd(ds.getId()));
	}
}
