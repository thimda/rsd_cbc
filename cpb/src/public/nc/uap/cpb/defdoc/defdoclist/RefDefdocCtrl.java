package nc.uap.cpb.defdoc.defdoclist;

import java.util.List;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.ctx.WindowContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Parameter;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.core.serializer.impl.List2DatasetSerializer;
import nc.uap.lfw.reference.ReferenceConstant;
import nc.uap.lfw.reference.app.AppReferenceController;
import nc.uap.lfw.reference.base.ILfwRefModel;
import nc.uap.lfw.reference.base.RefResult;
import nc.uap.lfw.reference.base.TreeGridRefModel;
import nc.uap.lfw.reference.base.TwoTreeGridRefModel;
import nc.uap.lfw.reference.base.TwoTreeRefModel;
import nc.uap.lfw.reference.nc.NcAdapterGridRefModel;
import nc.uap.lfw.reference.util.LfwRefUtil;
import nc.uap.lfw.util.JsURLDecoder;
import nc.ui.bd.ref.model.DefdocDefaultRefModel;

public class RefDefdocCtrl extends AppReferenceController{
	
	private static final String RELATION_WHERE_SQL = "relationWhereSql";
	
	@Override
	protected void processTreeSelWherePart(Dataset ds, RefNode rfnode,
			ILfwRefModel refModel) {
//			String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
//			String relationWhereSql = "1=1";
//			refModel.addWherePart(relationWhereSql);
//		super.processTreeSelWherePart(ds, rfnode, refModel);
	}
	public void processSelfWherePart(Dataset ds, RefNode rfnode,
			String filterValue, ILfwRefModel refModel) {
//		String pk_defdoclist = (String) getCurrentWinCtx().getAppAttribute("pk_defdoclist");
//		String relationWhereSql = "1=1";
//		refModel.addWherePart(relationWhereSql);
	}
	private WindowContext getCurrentWinCtx(){
	    return AppLifeCycleContext.current().getApplicationContext()
						.getCurrentWindowContext();
	  }
	
	@Override
	public void onDataLoad(DataLoadEvent e) {
		String widgetId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("widgetId");
		String refNodeId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("refNodeId");
		PageMeta parentPm = LfwRuntimeEnvironment.getWebContext().getParentPageMeta();
		RefNode rfnode = (RefNode) parentPm.getWidget(widgetId).getViewModels().getRefNode(refNodeId);
		ILfwRefModel refModel = LfwRefUtil.getRefModel(rfnode);
		if(refModel instanceof NcAdapterGridRefModel){
			CPDefdocDefaultRefModel cpRefModel = new CPDefdocDefaultRefModel();
			cpRefModel.setTableName(((NcAdapterGridRefModel)refModel).getNcModel().getTableName());
			if(((NcAdapterGridRefModel)refModel).getNcModel().getStrPatch() == null || ((NcAdapterGridRefModel)refModel).getNcModel().getStrPatch().trim().length() == 0){
				cpRefModel.setStrPatch("*");
			}else{
				cpRefModel.setStrPatch(((NcAdapterGridRefModel)refModel).getNcModel().getStrPatch());
			}
			
			((NcAdapterGridRefModel)refModel).setNcModel(cpRefModel);
		}
		Dataset ds = (Dataset) e.getSource();
		String filterValue = ds.getReqParameters().getParameterValue("filterValue");
		if(filterValue!=null){
			filterValue = JsURLDecoder.decode(filterValue, "UTF-8");
		}

		if(ds.getId().equals(ReferenceConstant.MASTER_DS_ID)){
			ViewContext widgetCtx = AppLifeCycleContext.current().getViewContext();
			LfwWidget widget = widgetCtx.getView();
			List<List<Object>> v = null;
			PaginationInfo pInfo = ds.getCurrentRowSet().getPaginationInfo();
			
			// 根据参照关联关系产生的查询条件语句
			Parameter parameter = ds.getReqParameter(RELATION_WHERE_SQL);
			if (null != parameter) {
				String relationWhereSql = parameter.getValue();
				if (relationWhereSql != null && !"".equals(relationWhereSql))
					refModel.addWherePart(relationWhereSql);
			}
			
			processSelfWherePart(ds, rfnode, filterValue, refModel);
			
			//将上次查询条件加入到where条件中
//			String wherePart = refModel.getWherePart();
//			if(ds.getLastCondition() != null){
//				if(wherePart == null)
//					wherePart = ds.getLastCondition();
//				else
//					wherePart += " and " + ds.getLastCondition();
//				refModel.setWherePart(wherePart);
//			}
			
			if (filterValue == null || filterValue.equals("")) {
				DatasetRelations dsRels = widget.getViewModels().getDsrelations();
				if (dsRels != null) {
					String parentDsId = dsRels.getMasterDsByDetailDs(ds.getId());
					if (parentDsId != null) {  // 为子DS
						Dataset parentDs = widget.getViewModels().getDataset(parentDsId);
						Row row = parentDs.getSelectedRow();
						if (row != null) {  // 主DS有选中行
							DatasetRelation dr = dsRels.getDsRelation(parentDsId, ds.getId());
							String masterKey = dr.getMasterKeyField();
							String keyValue = (String) row.getValue(parentDs.getFieldSet().nameToIndex(masterKey));
							
							if(keyValue == null)
								keyValue = row.getRowId();
							ds.setCurrentKey(keyValue);
							RowSet rowset = ds.getRowSet(keyValue, true);
							PaginationInfo pinfo = rowset.getPaginationInfo();
							pinfo.setPageIndex(0);
							
							if(refModel instanceof TreeGridRefModel){
								TreeGridRefModel treeRefModel = (TreeGridRefModel) refModel;
								treeRefModel.setClassJoinValue(keyValue);
								RefResult rr = treeRefModel.getRefData(-1);
								pInfo.setRecordsCount(rr.getTotalCount());
								v = rr.getData();
								new List2DatasetSerializer().serialize(keyValue, pinfo, v, ds);
								return;
							}
							else if(refModel instanceof TwoTreeRefModel){
								TwoTreeRefModel treeRefModel = (TwoTreeRefModel) refModel;
								//treeRefModel.setClassJoinValue(keyValue);
								String where = treeRefModel.getDocJoinField() + "= '" + keyValue + "'";
								treeRefModel.setWherePart(where);
								RefResult rr = treeRefModel.getRefData(-1);
								pInfo.setRecordsCount(rr.getTotalCount());
								v = rr.getData();
								new List2DatasetSerializer().serialize(keyValue, pinfo, v, ds);
								return;
							}
						}
					}
				}
				
				int pageIndex = pInfo.getPageIndex();
				RefResult rd = refModel.getRefData(pageIndex);
				if(rd != null){
					pInfo.setRecordsCount(rd.getTotalCount());
					v = rd.getData();
				}
				//new List2DatasetSerializer().serialize(ds.getCurrentKey(), pInfo, v, ds);
			}
			else{
				RefResult rd = refModel.getFilterRefData(filterValue, pInfo.getPageIndex());
				pInfo.setRecordsCount(rd.getTotalCount());
				v = rd.getData();
			}
			new List2DatasetSerializer().serialize(ds.getCurrentKey(), pInfo, v, ds);
		}
		//处理参照是2级树的情况
		else if(ds.getId().equals(ReferenceConstant.MASTER_DS_ID + "_tree_1")){
			if(refModel instanceof TwoTreeGridRefModel){
				TwoTreeGridRefModel tgModel = (TwoTreeGridRefModel) refModel;
				processSelfWherePart(ds, rfnode, filterValue, tgModel);
				List<List<Object>> v = tgModel.getTopClassData();
				new List2DatasetSerializer().serialize(ds.getCurrentKey(), null, v, ds);
			}
			if(refModel instanceof TwoTreeRefModel){
				TwoTreeRefModel tgModel = (TwoTreeRefModel) refModel;
				processSelfWherePart(ds, rfnode, filterValue, tgModel);
				List<List<Object>> v = tgModel.getTopClassData();
				new List2DatasetSerializer().serialize(ds.getCurrentKey(), null, v, ds);
			}
		}
		else if(ds.getId().equals("rightGridDs")){
			Dataset wds = parentPm.getWidget(widgetId).getViewModels().getDataset(rfnode.getWriteDs());
			Row row = wds.getFocusRow();
			if (row == null)
				row = wds.getSelectedRow();
			String writeFieldStr = rfnode.getWriteFields();
			String[] writeFields = writeFieldStr.split(",");
			String valuePK = (String) row.getValue(wds.nameToIndex(writeFields[0]));
			if( valuePK == null)
				return;
			String[] valuePKs = valuePK.split(",");
			String valueName = (String) row.getValue(wds.nameToIndex(writeFields[1]));
			String[] valueNames = valueName.split(",");
			for (int i = 0; i < valuePKs.length; i++) {
				String valPk = valuePKs[i];
				Row newRow = ds.getEmptyRow();
				ds.addRow(newRow);
				newRow.setValue(0, valPk);
				String valName = valueNames[i];
				newRow.setValue(1, valName);
			}
		}
		else {
			TreeGridRefModel tgModel = (TreeGridRefModel) refModel;
			//根据参数设置VO条件
			String keys = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_KEYS);
			if(keys != null && !keys.equals("")){
				String values = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_VALUES);
				String wherePart = (keys + " = '" + values + "'");
				tgModel.setClassWherePart(wherePart);
			}
			
			processSelfWherePart(ds, rfnode, filterValue, refModel);
			
			List<List<Object>> v = tgModel.getClassData();
			new List2DatasetSerializer().serialize(ds.getCurrentKey(), null, v, ds);
		}
	}
}
