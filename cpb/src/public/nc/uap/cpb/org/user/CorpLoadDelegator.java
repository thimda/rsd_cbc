package nc.uap.cpb.org.user;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.SuperVO;

/**
 * 查询某集团下的组织
 * @author zhangxya
 *
 */
public class CorpLoadDelegator extends DefaultDatasetServerListener{

	@Override
	public void onAfterRowSelect(DatasetEvent e) {
		Dataset masterDs = (Dataset) e.getSource();
		//获取触发行
		Row masterSelecteRow = masterDs.getSelectedRow();
		Dataset detailDs = getCurrentContext().getWidget().getViewModels().getDataset("userds");
		String clazz = detailDs.getVoMeta();
		if(clazz == null)
			throw new LfwRuntimeException("用户vometa没有设置");
		SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
		if(masterSelecteRow != null){
			//选中组织pk
			String pk_org = (String) masterSelecteRow.getValue(masterDs.nameToIndex("pk_orgs"));
			detailDs.setCurrentKey(pk_org);
			RowSet rowset = detailDs.getRowSet(pk_org, true);
			PaginationInfo pinfo = rowset.getPaginationInfo();
			pinfo.setPageIndex(0);
			try{
				String wherePart = " pk_org like '%"+pk_org+"%' and usertype = 0";
				detailDs.setLastCondition(wherePart);
				SuperVO[] vos = queryVOs(pinfo, vo, wherePart);
				//PtUserVO[] users = PMngServiceFacility.getPtUserQryService().getUserByPkorg(pk_org);
				//pinfo.setRecordsCount(pinfo.getRecordsCount());
				new SuperVO2DatasetSerializer().serialize(vos, detailDs, Row.STATE_NORMAL);
			}catch (LfwBusinessException exp) {
				Logger.error(exp.getMessage(), exp);
				throw new LfwRuntimeException("查询对象出错," + exp.getMessage() + ",ds id:" + detailDs.getId(), "查询过程出现错误");
			}
		}
	}


	private String widgetId;
		
	protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
		ReferenceComp groupText = (ReferenceComp) getGlobalContext().getPageMeta().getWidget(widgetId).getViewComponents().getComponent("group_text");
		String pk_group = groupText.getValue();
		if(pk_group != null && !pk_group.equals(""))
			vo.setAttributeValue("pk_group", pk_group);
		return ds.getLastCondition();
	}


	public CorpLoadDelegator(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
		this.widgetId = widgetId;
		// TODO Auto-generated constructor stub
	}

}
