package nc.uap.cpb.org.querycmd;

import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifDatasetLoadCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.vo.pub.SuperVO;

/**
 * 界面查询命令，查询结果绑定到给定view的指定ds上
 * 2012-3-9 上午09:46:56
 * @author limingf
 *
 */
public class QueryCmd { 
	private String viewId;
	private String dsId;
	private String whereSql;
	
	public QueryCmd(String viewId,String dsId,String whereSql){
		this.viewId = viewId;
		this.dsId = dsId;
		this.whereSql = whereSql;
	}
	public void excute(){
		 LfwWidget main = AppLifeCycleContext.current().getWindowContext().getViewContext(viewId).getView();
	      Dataset ds = main.getViewModels().getDataset(dsId);
	      final String where = buildWhere(initWhere(whereSql));
		  CmdInvoker.invoke(new UifDatasetLoadCmd(ds.getId()){			  
			  protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
				  ds.setLastCondition(where);
				  //重置分页，查询所有满足条件的数据
				  ds.getCurrentRowSet().getPaginationInfo().setPageIndex(0);
				  return where;
				}
		  });
	}
	
	protected String buildWhere(String whereSql){
		return whereSql;
	}
	
	private String initWhere(String whereSql){
		if(whereSql==null||"".equals(whereSql)){
			whereSql = " 1=1 ";
		  }
		return whereSql;
	}

}
