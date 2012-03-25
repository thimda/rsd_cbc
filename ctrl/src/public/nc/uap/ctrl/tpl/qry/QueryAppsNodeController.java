package nc.uap.ctrl.tpl.qry;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.vo.pub.SuperVO;

/**
 * 功能节点和功能节点分类加载类
 * @author zhangxya
 *
 */
public class QueryAppsNodeController {
	public void onDataLoad_ds_appscategory(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()));
	}

	public void onDataLoad_ds_appsNode(DataLoadEvent dataLoadEvent) {
		Dataset ds = dataLoadEvent.getSource();
		CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()) {
			protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				String values = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_VALUES);
				 String pk_module = values;
				 String where = " pk_appscategory = '"+pk_module+"'";
				 return where;
			}
		});
	}

}
