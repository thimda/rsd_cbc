package nc.uap.portal.ctrl.office.controller;

import java.util.Iterator;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.lfw.core.md.util.LfwMdUtil;
import nc.md.innerservice.MDQueryService;
import nc.md.model.IBusinessEntity;
import nc.md.model.MetaDataException;
import nc.uap.lfw.core.common.DatasetConstant;
import nc.uap.lfw.core.common.ExtAttrConstants;
import nc.uap.lfw.core.comp.ExcelColumn;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.EmptyRow;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.WidgetContext;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.IOperatorState;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.uif.listener.IBodyInfo;
import nc.uap.lfw.core.uif.listener.MultiBodyInfo;
import nc.uap.lfw.md.LfwTabcodeItf;
import nc.uap.lfw.util.LfwClassUtil;
import nc.vo.pub.SuperVO;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;


public class ExcelDemoMainCtrl {
	/**
	 * 增加列
	*/
	public void Addcolumn_onclick(  MouseEvent mouseEvent){
		Dataset ds = AppLifeCycleContext.current().getWindowContext().getWindow().getWidget("main").getViewModels()
		.getDataset("pt_user");
		Field field = new Field();
		
		field.setField("testcolumn");
		field.setId("testcolumn");		
		field.setDataType("String");
		field.setDefaultValue("");
		
		ds.adjustField(field);
		
		RowSet[] rowsets = ds.getRowSets();
		if(null != rowsets){
			for(RowSet rowset: rowsets){
				RowData[] rowdatas = rowset.getRowDatas();
				for(RowData rowdata : rowdatas){
					for(Row row: rowdata.getRows()){
						
						if(row.getClass().equals(EmptyRow.class)) continue;
						row.addColumn();
						row.setValue(ds.nameToIndex("testcolumn"), "test");
					}
				}
			}
		}
		ExcelComp excel = (ExcelComp) AppLifeCycleContext.current().getWindowContext().getWindow().getWidget("main").getViewComponents()
			.getComponent("test");
		if(null != excel){
			ExcelColumn col = new ExcelColumn();
			col.setDataType("String");
			col.setField("testcolumn");
			col.setI18nName("测试列");
			col.setId("testcol");
			col.setWidth(20);
			excel.addColumn(col,true);
		}
	}
	
	private String loadPk = null;
	private static final String OPEN_BILL_ID = "openBillId";
	public void onload(nc.uap.lfw.core.event.DataLoadEvent se) {
		Dataset ds = se.getSource();
		String clazz = ds.getVoMeta();
		if(clazz == null)
			return;
		SuperVO vo = (SuperVO) LfwClassUtil.newInstance(clazz);
		//根据参数设置VO条件
		String keys = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_KEYS);
		if(keys != null && !keys.equals("")){
			String values = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_VALUES);
			String[] keyStrs = keys.split(",");
			String[] valueStrs = values.split(",");
			for (int i = 0; i < keyStrs.length; i++) {
				vo.setAttributeValue(keyStrs[i], valueStrs[i]);
			}
		}
		
		//如果前台设置进主键参数，则表示加载此单据，需设置主键条件（前台传回来的）
		String pk = ds.getReqParameters().getParameterValue(OPEN_BILL_ID);
		if(pk != null){
			vo.setPrimaryKey(pk);
			loadPk = pk;
		}
		
		//进一步处理查询条件
		String wherePart = postProcessQueryVO(vo, ds);
		//处理order by 条件
		postOrderPart(ds);
		String orderPart = (String) ds.getExtendAttributeValue("ORDER_PART");
		try {
			PaginationInfo pinfo = ds.getCurrentRowSet().getPaginationInfo();
			SuperVO[] vos = null;
			if(orderPart == null || orderPart.equals(""))
				vos = queryVOs(pinfo, vo, wherePart);
			else
				vos = queryVOs(pinfo, vo, wherePart, orderPart);
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);
			postProcessRowSelect(ds);
		} 
		catch (LfwBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException("查询对象出错," + e.getMessage() + ",ds id:" + ds.getId(), "查询过程出现错误");
		}
	}
	/**
	 * 真正执行查询，可在必要时重写此方法
	 * @param pinfo
	 * @param vo
	 * @param wherePart
	 * @return
	 * @throws LfwBusinessException
	 */
	protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart, String orderPart) throws LfwBusinessException {
		SuperVO[] vos =  CRUDHelper.getCRUDService().queryVOs(vo, pinfo, wherePart, null, orderPart);
		return vos;
	}
	
	/**
	 * 真正执行查询，可在必要时重写此方法
	 * @param pinfo
	 * @param vo
	 * @param wherePart
	 * @return
	 * @throws LfwBusinessException
	 */
	protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo, String wherePart) throws LfwBusinessException {
		return queryVOs(pinfo, vo, wherePart, null);
	}

	/**
	 * 设置查询后行选中，特殊界面不能设置默认选中需覆盖,默认不选中
	 * @param ds
	 */
	protected void postProcessRowSelect(Dataset ds) {
		if(loadPk != null && ds.getCurrentRowCount() > 0){
			ds.setSelectedIndex(0);
		}
	}

	/**
	 * 进一步设置查询条件，可根据需要重写此方法。
	 * 支持在VO中设置条件及通过返回值设置条件双重方式。如 vo.setAttribute("name", "test"),或者返回 name='test'的条件子句。
	 * 本方法会同时取2种设置方式的合集
	 * @param vo
	 * @param ds
	 * @return
	 */
	protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
		return ds.getLastCondition();
	}
	
	/**
	 * 添加orderby
	 * 例如：order by ts 
	 * @return
	 */
	protected void postOrderPart(Dataset ds) {
		ds.setExtendAttribute("ORDER_PART", null);
	}


	

	protected void postProcessChildRowSelect(Dataset ds) {
		if(ds.getCurrentRowCount() > 0)
			ds.setRowSelectIndex(0);
	}

	/**
	*	判断此Ds是否实现了nc.uap.lfw.md.LfwTabcodeItf业务接口,如果实现了这个接口，则需要将TabCode作为数据的区分
	*/
	private void processTabCode(LfwWidget widget, Dataset detailDs, SuperVO vo) {
		Object datasetMetaId = detailDs.getExtendAttributeValue(ExtAttrConstants.DATASET_METAID);
		if(datasetMetaId != null){
			String metaId = datasetMetaId.toString();
			try {
				IBusinessEntity entity = MDQueryService.lookupMDQueryService().getBusinessEntityByFullName(metaId);
				boolean tabCodeItf = false;
				if(entity != null)
					tabCodeItf = entity.isImplementBizInterface(LfwTabcodeItf.class.getName());
				if(tabCodeItf){
					String tabcode = LfwMdUtil.getMdItfAttr(entity, LfwTabcodeItf.class.getName(), "tabcode");
					if(tabcode == null || tabcode.equals(""))
						throw new LfwRuntimeException("Dataset:" + detailDs.getId() + "实现了nc.uap.lfw.md.LfwTabcodeItf业务接口，但没有设置属性对照");
					IBodyInfo bodyInfo = (IBodyInfo) widget.getExtendAttributeValue(LfwWidget.BODYINFO);
					if(bodyInfo != null){
						if(bodyInfo instanceof MultiBodyInfo){
							MultiBodyInfo multiBodyInfo = (MultiBodyInfo) bodyInfo;
							Map<String, String> tabDsMap = multiBodyInfo.getItemDsMap();
							for (Iterator<String> itwd = tabDsMap.keySet().iterator(); itwd.hasNext();) {
								String tabId = (String) itwd.next();
								if(tabDsMap.get(tabId).equals(detailDs.getId())){
									vo.setAttributeValue(tabcode, tabId);
									break;
								}
							}
						}
					}
				}
			} 
			catch (MetaDataException e1) {
				LfwLogger.error(e1.getMessage(), e1);
			}
		}
	}

	
	/**
	 * 
	 * @param pinfo
	 * @param vo
	 * @param wherePart 
	 * @param isNewMaster 主表是否新增行
	 * @return
	 * @throws LfwBusinessException
	 */
	protected SuperVO[] queryChildVOs(PaginationInfo pinfo, SuperVO vo, String wherePart, boolean isNewMaster) throws LfwBusinessException {
		if(!isNewMaster){
			SuperVO[] vos = CRUDHelper.getCRUDService().queryVOs(vo, pinfo, wherePart, null, null);
			return vos;
		}
		return null;
	}
	
	
	protected SuperVO[] queryChildVOs(PaginationInfo pinfo, SuperVO vo, String wherePart, boolean isNewMaster, String orderPart) throws LfwBusinessException {
		if(!isNewMaster){
			SuperVO[] vos = CRUDHelper.getCRUDService().queryVOs(vo, pinfo, wherePart, null, orderPart);
			return vos;
		}
		return null;
	}
	
	protected String postProcessRowSelectVO(SuperVO vo, Dataset ds) {
		return ds.getLastCondition();
	}
}
