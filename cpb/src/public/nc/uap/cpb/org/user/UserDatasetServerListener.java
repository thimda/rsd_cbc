package nc.uap.cpb.org.user;

import nc.bs.logging.Logger;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.ctx.WidgetContext;
import nc.uap.lfw.core.event.deft.DefaultDatasetServerListener;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.SuperVO;

/**
 * 用户数据加载类，主要进行用户行选中后的数据处理
 * 2011-6-29 下午03:38:18 
 * @author limingf
 */
public class UserDatasetServerListener extends DefaultDatasetServerListener {

	public UserDatasetServerListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	} 


	/**
	 * 行选中方法，处理主子关系等通用逻辑
	 */
	@Override
	public void onAfterRowSelect(DatasetEvent e) {
		Dataset masterDs = (Dataset) e.getSource();
		//获取触发行
		Row masterSelecteRow = masterDs.getSelectedRow();
		if(masterSelecteRow == null)
			return;
		WidgetContext widgetCtx = getCurrentContext();
		LfwWidget widget = widgetCtx.getWidget();
		//获取数据集主子关系，根据主子关系进行子数据集加载
		DatasetRelations dsRels = widget.getViewModels().getDsrelations();
		if(dsRels != null)
		{
			DatasetRelation[] masterRels = dsRels.getDsRelations(masterDs.getId());
			for (int i = 0; i < masterRels.length; i++) {
				//获取子对应的外键值，并设置到VO条件中
				DatasetRelation dr = masterRels[i];
				Dataset detailDs = widget.getViewModels().getDataset(dr.getDetailDataset());
				String masterKey = dr.getMasterKeyField();
				String detailFk = dr.getDetailForeignKey();
				String keyValue = (String) masterSelecteRow.getValue(masterDs.getFieldSet().nameToIndex(masterKey));
				
				//标识是否是新主行
				boolean isNewMaster = false;
				if(keyValue == null){
					isNewMaster = true;
					keyValue = masterSelecteRow.getRowId();
				}
				//设置子表当前数据块（外键对应值)
				detailDs.setCurrentKey(keyValue);
				RowSet rowset = detailDs.getRowSet(keyValue, true);
				//默认获取第一页
				PaginationInfo pinfo = rowset.getPaginationInfo();
				pinfo.setPageIndex(0);
				
				String clazz = detailDs.getVoMeta();
				SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
				
				if(!isNewMaster)
					vo.setAttributeValue(detailFk, keyValue);
				
				//进一步进行查询条件处理
				String wherePart = postProcessRowSelectVO(vo, detailDs);
				//处理子ds的orderby
				postOrderPart(detailDs);
				String orderPart = (String) detailDs.getExtendAttributeValue("ORDER_PART");
				try {
					SuperVO[] vos = queryChildVOs(pinfo, vo, wherePart, isNewMaster, orderPart);
					pinfo.setRecordsCount(pinfo.getRecordsCount());
					new SuperVO2DatasetSerializer().serialize(vos, detailDs, Row.STATE_NORMAL);
					postProcessChildRowSelect(detailDs);
					/**
					 * add by licza
					 * 子数据集控制页面状态
					 */
					setPageState(detailDs);
				} 
				catch (LfwBusinessException exp) {
					Logger.error(exp.getMessage(), exp);
					throw new LfwRuntimeException("查询对象出错," + exp.getMessage() + ",ds id:" + detailDs.getId(), "查询过程出现错误");


				}
			}
		}
		setPageState(masterDs);
 }
	
	protected String postProcessRowSelectVO(SuperVO vo, Dataset ds) {
		return ds.getLastCondition();
	}

}