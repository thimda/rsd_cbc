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
	 * ������
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
			col.setI18nName("������");
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
		//���ݲ�������VO����
		String keys = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_KEYS);
		if(keys != null && !keys.equals("")){
			String values = ds.getReqParameters().getParameterValue(DatasetConstant.QUERY_PARAM_VALUES);
			String[] keyStrs = keys.split(",");
			String[] valueStrs = values.split(",");
			for (int i = 0; i < keyStrs.length; i++) {
				vo.setAttributeValue(keyStrs[i], valueStrs[i]);
			}
		}
		
		//���ǰ̨���ý��������������ʾ���ش˵��ݣ�����������������ǰ̨�������ģ�
		String pk = ds.getReqParameters().getParameterValue(OPEN_BILL_ID);
		if(pk != null){
			vo.setPrimaryKey(pk);
			loadPk = pk;
		}
		
		//��һ�������ѯ����
		String wherePart = postProcessQueryVO(vo, ds);
		//����order by ����
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
			throw new LfwRuntimeException("��ѯ�������," + e.getMessage() + ",ds id:" + ds.getId(), "��ѯ���̳��ִ���");
		}
	}
	/**
	 * ����ִ�в�ѯ�����ڱ�Ҫʱ��д�˷���
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
	 * ����ִ�в�ѯ�����ڱ�Ҫʱ��д�˷���
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
	 * ���ò�ѯ����ѡ�У�������治������Ĭ��ѡ���踲��,Ĭ�ϲ�ѡ��
	 * @param ds
	 */
	protected void postProcessRowSelect(Dataset ds) {
		if(loadPk != null && ds.getCurrentRowCount() > 0){
			ds.setSelectedIndex(0);
		}
	}

	/**
	 * ��һ�����ò�ѯ�������ɸ�����Ҫ��д�˷�����
	 * ֧����VO������������ͨ������ֵ��������˫�ط�ʽ���� vo.setAttribute("name", "test"),���߷��� name='test'�������Ӿ䡣
	 * ��������ͬʱȡ2�����÷�ʽ�ĺϼ�
	 * @param vo
	 * @param ds
	 * @return
	 */
	protected String postProcessQueryVO(SuperVO vo, Dataset ds) {
		return ds.getLastCondition();
	}
	
	/**
	 * ���orderby
	 * ���磺order by ts 
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
	*	�жϴ�Ds�Ƿ�ʵ����nc.uap.lfw.md.LfwTabcodeItfҵ��ӿ�,���ʵ��������ӿڣ�����Ҫ��TabCode��Ϊ���ݵ�����
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
						throw new LfwRuntimeException("Dataset:" + detailDs.getId() + "ʵ����nc.uap.lfw.md.LfwTabcodeItfҵ��ӿڣ���û���������Զ���");
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
	 * @param isNewMaster �����Ƿ�������
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
