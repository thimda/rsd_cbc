package nc.uap.ctrl.tpl.qry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.logging.Logger;
import nc.md.innerservice.MDQueryService;
import nc.md.model.IEnumValue;
import nc.md.model.MetaDataException;
import nc.md.model.type.IEnumType;
import nc.uap.ctrl.tpl.CpTplServiceFacility;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTranslator;
import nc.uap.ctrl.tpl.qry.base.QtVO2MetaConvertor;
import nc.uap.ctrl.tpl.qry.controller.AdvancedQueryController;
import nc.uap.ctrl.tpl.qry.meta.ConditionVO;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.ctrl.tpl.qry.meta.IFilterMeta;
import nc.uap.ctrl.tpl.qry.operator.IOperator;
import nc.uap.ctrl.tpl.systemplate.ICpSystemplateQryService;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.StaticComboData;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.RecursiveTreeLevel;
import nc.uap.lfw.core.comp.SimpleTreeLevel;
import nc.uap.lfw.core.comp.TreeLevel;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldSet;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.TreeNodeListener;
import nc.uap.lfw.core.event.conf.TreeRule;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.model.IWidgetContentProvider;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.core.refnode.NCRefNode;
import nc.uap.lfw.ncadapter.billtemplate.BillTemplateConst;
import nc.uap.lfw.ncadapter.billtemplate.ref.LfwNCRefUtil;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.bd.ref.IRefConst;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.query.IQueryConstants;
import net.sf.cglib.core.Predicate;

public class AdvancedQueryWidgetProvider implements IWidgetContentProvider {
	private static final String CONTROLLER_CLAZZ = AdvancedQueryController.class.getName();
	public static final String QUERY_DS_ID = "queryDataset";
	public static final String QUERY_CDS_ID = "queryConditionDataset";
	public static final String QUERY_CT_DS_ID = "queryConditionTreeDataset";
	public static final String SAVED_QC_DS_ID = "savedQueryCondition";
	
	public static final String QUERY_TEMPLATE_TREE_ID = "queryTemplateTree";
	public static final String ADVANCE_TREE_ID = "advanceTree";
	public static final String SAVED_TREE_ID = "savedTree";

	public static final String QUERY_TEMPLATE_ROOT_TEXT = "��ѡ��";
	public static final String ADVANCE_TREE_ROOT_TEXT = "�߼�ѡ��";
	public static final String SAVED_TREE_ROOT_TEXT = "��ѡ����";
	
	public static final String QUERY_DATA_KEY = "QUERY_DATA_KEY";
	public static final String TEMPLATE_INFO = "template_info";
	public static final String TEMPLATE_ID = "templateid";
	public static final String MAIN_METACLASS_ID = "mainmetaclassId";
	
//	private ICpQueryTemplateQryService queryTemplate;
	
	//��ѯģ���Ԫ����ID
	private String beanId;
	
	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	private QtVO2MetaConvertor voConvertor = new QtVO2MetaConvertor();
	// ���ݿ�������ģ������(except �߼�����)
//	private List<FilterMeta> allDBMetas = new ArrayList<FilterMeta>();

	//private List<FilterMeta> metaWithouFixConidition = new ArrayList<FilterMeta>();

	// ���к�ѡ����,ֻ�Ǽ�¼��;���ڲ�ε�,ֻ��������һ��mockFiltermeta
	private List<FilterMeta> allCandidateMetas = new ArrayList<FilterMeta>();

	// ���� �̶�����������������Ĭ������˳����ѡ������filterMeta
	private List<FilterMeta> sortedFiltemetas = new ArrayList<FilterMeta>();

	private Map<String, IFilter> fieldCodeFilterMap = new HashMap<String, IFilter>();

//	private List<NCRefNode> refNodeList = new ArrayList<NCRefNode>();
	
//	private List<ComboData> comboDataList = new ArrayList<ComboData>();
	
	private Map<String, IFilter> fieldcode_filter_map = new HashMap<String, IFilter>();
	
	private Dataset queryds;
	private Dataset querycds;
	private Dataset queryctreeds;
	private Dataset savedqcds;
	
	private TreeViewComp queryTreeComp;
	private TreeViewComp queryCTreeComp;
	private TreeViewComp savedTreeComp;
	
	private ButtonComp okButton;
	private ButtonComp cancelButton;
	
	public LfwWidget buildWidget(PageMeta pm, LfwWidget conf, Map<String, Object> paramMap, String currWidgetId) {
		LfwWidget widget = new LfwWidget();
		widget.setId(conf.getId());
		widget.setControllerClazz(CONTROLLER_CLAZZ);
		
		
		PlugoutDesc plugoutDesc = new PlugoutDesc();
		plugoutDesc.setId("queryNormalout");
		widget.addPlugoutDescs(plugoutDesc);
		
//		EventConf event = new EventConf();
//		event.setJsEventClaszz(DialogListener.class.getName());
//		event.setMethodName("onBeforeShow");
//		event.setName("beforeShow");
//		widget.addEventConf(event);
                
		
		createQueryds(widget);
		createCds(widget);
		createQueryctreeds(widget);
		createSavedqcds(widget);
		
		createQueryTreeComp(widget);
		createQueryCTreeComp(widget);
		createSavedTreeComp(widget);
		
		
		//���������ť
		//createSavedTreeToolBarComp(pm, widget);
		
//		// ��������Ϣ
//		String pWidgetId = null;
//		String pDatasetId = null;
//		if(!LfwRuntimeEnvironment.getMode().equals(WebConstant.MODE_DESIGN)){
//			pDatasetId = LfwRuntimeEnvironment.getWebContext().getParameter("pdsid");
//			pWidgetId = LfwRuntimeEnvironment.getWebContext().getParameter("pwid");
//			LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute("pwid", pWidgetId);
//			LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute("pdsid", pDatasetId);
//			
//			LfwRuntimeEnvironment.getWebContext().setAttribute("widgetId", widget.getId());
//		}
//		else{
//			pDatasetId = "main";
//			pWidgetId = "main";
//		}
//		
		createOkBt(widget);
		createCancelBt(widget);
		
//		loadData(paramMap, widget, conf);
		
		loadDataToComp(widget, conf);
		
		return widget;
	}
	
	public static final String SAVED_TREE_MENUBAR_ID = "savedTreeMenubar";

	
	/*��ѯ��������ť*/
	private MenubarComp savedTreeMenuBarComp;
	private void createSavedTreeToolBarComp(LfwWidget widget) {
		savedTreeMenuBarComp = new MenubarComp();
		savedTreeMenuBarComp.setId(SAVED_TREE_MENUBAR_ID);
		
		
		//�ύ����
		EventSubmitRule sr = new EventSubmitRule();
//		sr.setTabSubmit(true);
		WidgetRule wr = new WidgetRule();
		wr.setId(widget.getId());
		sr.addWidgetRule(wr);
		DatasetRule dsr = new DatasetRule();
		dsr.setId(SAVED_QC_DS_ID);
		dsr.setType(DatasetRule.TYPE_ALL_LINE);
		wr.addDsRule(dsr);
		DatasetRule dsr2 = new DatasetRule();
		dsr2.setId(QUERY_CDS_ID);
		dsr2.setType(DatasetRule.TYPE_ALL_LINE);
		wr.addDsRule(dsr2);
		
		
		//ɾ��
		MenuItem deleteItem = new MenuItem();
		deleteItem.setId("savedTreeDelete");
		deleteItem.setTip("ɾ��");
		String themeid= LfwRuntimeEnvironment.getThemeId();
		deleteItem.setImgIcon("/../../lfw/themes/" + themeid + "/images/querytemplate/delete.png");
		
//		MouseListener savedTreeDeleteMenuListener = new MouseListener();
//		savedTreeDeleteMenuListener.setId("saved_tree_delete_listener");
//		savedTreeDeleteMenuListener.setServerClazz(SavedTreeNodeMouseListener.class.getName());
//		
//		EventHandlerConf delEvent = MouseListener.getOnClickEvent();
//		delEvent.setOnserver(true);
//		delEvent.setSubmitRule(sr);
//		savedTreeDeleteMenuListener.addEventHandler(delEvent);
//		deleteItem.addListener(savedTreeDeleteMenuListener);
		
		//����
		MenuItem saveItem = new MenuItem();
		saveItem.setId("savedTreeSave");
		saveItem.setTip("����");
		saveItem.setImgIcon("/../../lfw/themes/" + themeid + "/images/querytemplate/save.png");
		
		
		EventConf saveEvent = new EventConf();
		saveEvent.setJsEventClaszz("nc.uap.lfw.core.event.conf.MouseListener");
		saveEvent.setOnserver(true);
		saveEvent.setSubmitRule(sr);
		saveEvent.setName("onclick");
		saveEvent.setMethodName("saveTreeNode");
		
		
		StringBuffer saveScript = new StringBuffer();
		saveScript.append("var ds = pageUI.getWidget('").append(widget.getId()).append("').getDataset('").append(SAVED_QC_DS_ID).append("');")
			.append("var selectedRow = ds.getSelectedRow();")
//			.append("if (selectedRow == null ) return;")
//			.append("if (selectedRow.state == DatasetRow.STATE_NEW){")
			.append("	if (!window.$c_conditionSavedialog) {")
			.append("       window.$c_conditionSavedialog = new InputDialogComp('saveText', '����Ի���', 0, 0, null, null, 100, function(){")
			.append("             	var ds = pageUI.getWidget('").append(widget.getId()).append("').getDataset('").append(SAVED_QC_DS_ID).append("');")
			.append("             	var saveName = window.$c_conditionSavedialog.getItem('conditionSaveText').getValue();")
			.append("             	if (saveName == ''){showMessageDialog('��ѯ�������Ʋ���Ϊ��!');return};")
//			.append("             	var selectedRow = ds.getSelectedRow();")
//			.append("             	if (selectedRow == null ) return;")
//			.append("             	selectedRow.setCellValue(ds.nameToIndex('name'),saveName);")
			.append("			  	var proxy = new ServerProxy(this.$TEMP_saved_tree_save_listener,'onclick',true);")
			.append("			  	if(typeof beforeCallServer != 'undefined')")
			.append("			  	beforeCallServer(proxy, 'saved_tree_save_listener', 'onclick','savedTreeSave');")
			.append("			  	proxy.addParam('save_name',saveName);")
			.append("				showDefaultLoadingBar();")
			.append("				return proxy.execute();")
			.append("             });")
			.append("       window.$c_conditionSavedialog.addItem('�����뱣����:', 'conditionSaveText', 'string', true, null); ")
			.append("    }")
			.append("    window.$c_conditionSavedialog.show();");
//			.append("}")
//			.append("else{")
//			.append("var proxy = new ServerProxy(this.$TEMP_saved_tree_save_listener,'onclick',true);")
//			.append("if(typeof beforeCallServer != 'undefined')")
//			.append("beforeCallServer(proxy, 'saved_tree_save_listener', 'onclick','savedTreeSave');")
//			.append("showDefaultLoadingBar();")
//			.append("return proxy.execute();}");
//		saveEvent.setScript(saveScript.toString());
//		saveEvent.setSubmitRule(sr);
//		savedTreeSaveMenuListener.addEventHandler(saveEvent);
//		saveItem.addListener(savedTreeSaveMenuListener);
//		
//		//������
//		MenuItem renameItem = new MenuItem();
//		renameItem.setId("savedTreeRename");
//		renameItem.setTip("������");
//		renameItem.setImgIcon("/../../lfw/themes/" + themeid + "/images/querytemplate/rename.png");
//		
//		MouseListener savedTreeRenameMenuListener = new MouseListener();
//		savedTreeRenameMenuListener.setId("saved_tree_rename_listener");
//		savedTreeRenameMenuListener.setServerClazz(SavedTreeNodeMouseListener.class.getName());
//		
//		EventHandlerConf renameEvent = MouseListener.getOnClickEvent();
//		renameEvent.setOnserver(true);
//		renameEvent.setSubmitRule(sr);
//		savedTreeRenameMenuListener.addEventHandler(renameEvent);
//		renameItem.addListener(savedTreeRenameMenuListener);
		
		
		
		savedTreeMenuBarComp.addMenuItem(deleteItem);
		savedTreeMenuBarComp.addMenuItem(saveItem);
	//	savedTreeMenuBarComp.addMenuItem(renameItem);

		widget.getViewMenus().addMenuBar(savedTreeMenuBarComp);
	}
	
	
//	private void loadData(Map<String, Object> paramMap, LfwWidget widget, LfwWidget conf) {
////		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
////		String appId = getAppId();
////		String nodeKey = getNodeKey();
////		String pk_org = ses.getPk_unit();
////		
////		CpTemplateParaVO paramVO = new CpTemplateParaVO();
////		paramVO.setPk_org(pk_org);
////		paramVO.setAppid(appId);
////		paramVO.setOperator(ses.getPk_user());
////		paramVO.setNodeKey(nodeKey);
////		String templateId = loadTemplateID(paramVO);
////
////		try {
////			CpQueryTemplateTotalVO totalVO = null;//getQueryTemplate().selectTotalData(templateId, pk_org);
////			if (totalVO == null)
////				throw new LfwRuntimeException("û���ҵ�ָ����ѯģ��,ģ��ID=" + templateId);
////			
////			//�õ���ѯģ���Ӧ��Ԫ����Id
////			beanId = totalVO.getTempletVO().getMetaclass();
////			
//////			//���ﴦ��һ�¸����ֶ�
//////			CpQueryTemplateTranslator.loadData(totalVO);
////			//����widget��unique_ts�ֶ�
////			widget.setExtendAttribute(LfwWidget.UNIQUE_TS, totalVO.getTempletVO().getTs().toString());
////
////			processQueryTempletTotalVO(totalVO);
////			
//////			if(!LfwRuntimeEnvironment.getMode().equals(WebConstant.MODE_DESIGN)) {
//////				LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(QUERY_DATA_KEY, totalVO);
//////				LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(TEMPLATE_INFO, templateInfo);
//////				LfwRuntimeEnvironment.getWebContext().getRequest().setAttribute(TEMPLATE_ID, templateInfo.getTemplateId());
//////				LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(TEMPLATE_ID, templateInfo.getTemplateId());
//////				LfwRuntimeEnvironment.getWebContext().getWebSession().setAttribute(MAIN_METACLASS_ID, totalVO.getTempletVO().getMetaclass());
//////			}
////			
////			ConditionVO[] fixConditionVOs = totalVO.getTempletVO().getFixConditions();
////			CpQueryConditionVO[] vos = totalVO.getConditionVOs();
////			
////			if (vos != null) {
////				for (CpQueryConditionVO vo : vos) {
////					if (vo.getIfused() == null || !vo.getIfused().booleanValue())
////						continue;
////					FilterMeta tmp = voConvertor.convert(vo);
////					allDBMetas.add(tmp);
////				}
////			}
////			registerFixCondition(allDBMetas, fixConditionVOs);
////			
////			Dataset queryDs = new Dataset();
////			queryDs.setId("");
////			initFilterMetas(allDBMetas, queryDs);
////		} 
////		catch (Exception e) {
////			throw new LfwRuntimeException("����ָ����ѯģ�����,ģ��ID=" + templateId);
////		}
//	}
	
//	private String getAppId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private String getNodeKey() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private void createQueryds(LfwWidget widget) {
		queryds = new Dataset();
		queryds.setId(QUERY_DS_ID);
		FieldSet fieldSet = new FieldSet();
		fieldSet.addField(createField("query_fieldId", "fieldId", "String", "�ֶ�Id", false));
		fieldSet.addField(createField("query_label", "label", "String", "��ʾ��ǩ", false));
		fieldSet.addField(createField("query_dataType", "dataType", "String", "��������", false));
		fieldSet.addField(createField("query_field", "field", "String", "��ӦVO�ֶ�", false));
		fieldSet.addField(createField("query_editorType", "editorType", "String", "�༭������", false));
		fieldSet.addField(createField("query_editorInfo", "editorInfo", "String", "�༭����˵����Ϣ", false));
		fieldSet.addField(createField("query_defaultValue", "defaultValue", "String", "ȱʡֵ��Ϣ", false));
		fieldSet.addField(createField("query_pfieldId", "pfieldId", "String", "��id", false));
		fieldSet.addField(createField("query_editorInfo2", "editorInfo2", "String", "�༭��������Ϣ", false));
		fieldSet.addField(createField("parent_field", "parentField", "String", "parentField", false));
		fieldSet.addField(createField("table_name", "tableName", "String", "tableName", false));
		
		//����ǲ��������ʵֵ
		fieldSet.addField(createField("refpkValue", "refpkValue", "String", "������ʵ��ʾֵ", true));
		
		queryds.setFieldSet(fieldSet);
		
		queryds.setLazyLoad(false);
		
		EventConf event = new EventConf();
		event.setJsEventClaszz("nc.uap.lfw.core.event.conf.DatasetListener");
		event.setOnserver(true);
		event.setName("onDataLoad");
		event.setMethodName("onQueryDsLoad");
		queryds.addEventConf(event);
		
		widget.getViewModels().addDataset(queryds);
	}

	private void createCds(LfwWidget widget) {
		querycds = new Dataset();
		querycds.setId(QUERY_CDS_ID);
		FieldSet fieldSet = new FieldSet();
		fieldSet.addField(createField("query_condition_id", "id", "String", "��ʶ", false));
		fieldSet.addField(createField("query_condition_label", "label", "String", "�����ֶ�", false));
		fieldSet.addField(createField("query_condition_condition", "condition", "String", "��������", false));
		fieldSet.addField(createField("query_condition_value", "value", "String", "������Ӧֵ", false));
		fieldSet.addField(createField("query_condition_parentId", "parentId", "String", "���ڵ��ʶ", false));
		fieldSet.addField(createField("query_condition_editorType", "editorType", "String", "��������Ӧ�ı༭������", true));
		fieldSet.addField(createField("query_condition_dataType", "dataType", "String", "��������Ӧ��ֵ����������", true));
		fieldSet.addField(createField("query_condition_editorInfo", "editorInfo", "String", "�༭�������͵ĸ�����Ϣ", true));
		fieldSet.addField(createField("query_condition_field", "field", "String", "��Ӧ��field�ֶ�", true));
		fieldSet.addField(createField("query_condition_type", "fieldType", "String", "�ֶ�����(����0���̶�1��Ĭ��2����ѡ3)", true));
		fieldSet.addField(createField("query_condition_trueid", "query_condition_trueid", "String", "�ֶ���ʵID", true));
		fieldSet.addField(createField("parent_field", "parentField", "String", "parentField", true));
		fieldSet.addField(createField("tableName", "tableName", "String", "tableName", true));
		fieldSet.addField(createField("refpk", "refpk", "String", "������ʵpk����ֶ�", true));
		fieldSet.addField(createField("query_condition_is", "query_condition_is", "String", "�Ƿ����߼�����1", true));
		querycds.setFieldSet(fieldSet);
		
		querycds.setLazyLoad(false);
		
//		DatasetListener listener = new DatasetListener();
//		listener.setId("querycds_listener");
////		listener.setServerClazz(QueryConditionDatasetListener.class.getName());
//		
//		EventHandlerConf event = DatasetListener.getOnDataLoadEvent();
//		event.setOnserver(true);
//		
//		listener.addEventHandler(event);
//		
//		querycds.addListener(listener);
		
		EventConf event = new EventConf();
		event.setJsEventClaszz("nc.uap.lfw.core.event.conf.DatasetListener");
		event.setOnserver(true);
		event.setName("onDataLoad");
		event.setMethodName("onQueryConditionLoad");
		querycds.addEventConf(event);
		
		widget.getViewModels().addDataset(querycds);
	}

	private void createQueryctreeds(LfwWidget widget) {
		queryctreeds = new Dataset();
		queryctreeds.setId(QUERY_CT_DS_ID);
		FieldSet fieldSet = new FieldSet();
		fieldSet.addField(createField("query_condition_id", "id", "String", "��ʶ", false));
		fieldSet.addField(createField("query_condition_label", "label", "String", "�����ֶ�", false));
		fieldSet.addField(createField("query_condition_condition", "condition", "String", "��������", false));
		fieldSet.addField(createField("query_condition_value", "value", "String", "������Ӧֵ", false));
		fieldSet.addField(createField("query_condition_parentId", "parentId", "String", "���ڵ��ʶ", false));
		fieldSet.addField(createField("query_condition_editorType", "editorType", "String", "��������Ӧ�ı༭������", true));
		fieldSet.addField(createField("query_condition_dataType", "dataType", "String", "��������Ӧ��ֵ����������", true));
		fieldSet.addField(createField("query_condition_editorInfo", "editorInfo", "String", "�༭�������͵ĸ�����Ϣ", true));
		fieldSet.addField(createField("query_condition_field", "field", "String", "��Ӧ��field�ֶ�", true));
		fieldSet.addField(createField("query_condition_type", "fieldType", "String", "�ֶ�����(����0���̶�1��Ĭ��2����ѡ3)", true));
		fieldSet.addField(createField("query_condition_trueid", "query_condition_trueid", "String", "�ֶ���ʵID", true));
		fieldSet.addField(createField("parent_field", "parentField", "String", "parentField", true));
		fieldSet.addField(createField("tableName", "tableName", "String", "tableName", true));
		fieldSet.addField(createField("refpk", "refpk", "String", "������ʵpk����ֶ�", true));
		fieldSet.addField(createField("query_condition_is", "query_condition_is", "String", "�Ƿ����߼�����1", true));
		queryctreeds.setFieldSet(fieldSet);
		
		queryctreeds.setLazyLoad(false);
		
//		DatasetListener listener = new DatasetListener();
//		listener.setId("queryctreeds_listener");
////		listener.setServerClazz(QueryCTreeDatasetListener.class.getName());
//		
//		EventHandlerConf event = DatasetListener.getOnDataLoadEvent();
//		event.setOnserver(true);
//		
//		listener.addEventHandler(event);
//		
//		queryctreeds.addListener(listener);
		
		
		EventConf event = new EventConf();
		event.setJsEventClaszz("nc.uap.lfw.core.event.conf.DatasetListener");
		event.setOnserver(true);
		event.setName("onDataLoad");
		event.setMethodName("onAdvConditionLoad");
		queryctreeds.addEventConf(event);
		
		widget.getViewModels().addDataset(queryctreeds);
	}

	private void createSavedqcds(LfwWidget widget) {
		savedqcds = new Dataset();
		savedqcds.setId(SAVED_QC_DS_ID);
		FieldSet fieldSet = new FieldSet();
		fieldSet.addField(createField("id", "id", "String", "�ڵ���", false));
		fieldSet.addField(createField("guid", "guid", "String", "�ڵ�ȫ��Ψһ���", false));
		fieldSet.addField(createField("parentguid", "parentguid", "String", "���ڵ���", false));
		fieldSet.addField(createField("display", "display", "String", "�ڵ���ʾ����", false));
		fieldSet.addField(createField("kind", "kind", "String", "�ڵ�����", false));
		fieldSet.addField(createField("note", "note", "String", "��������", true));
		fieldSet.addField(createField("isdefault", "isdefault", "String", "�Ƿ�Ĭ��", true));
		savedqcds.setFieldSet(fieldSet);
		
		savedqcds.setLazyLoad(false);
		
//		DatasetListener listener = new DatasetListener();
//		listener.setId("savedqcds_listener");
//		listener.setServerClazz(SavedConditionDatasetListener.class.getName());
		
//		EventHandlerConf event = DatasetListener.getOnDataLoadEvent();
//		event.setOnserver(true);
//		
//		listener.addEventHandler(event);
//		
//		savedqcds.addListener(listener);
		
		EventConf event = new EventConf();
		event.setJsEventClaszz("nc.uap.lfw.core.event.conf.DatasetListener");
		event.setOnserver(true);
		event.setName("onDataLoad");
		event.setMethodName("onSavedConditionLoad");
		savedqcds.addEventConf(event);
		
		widget.getViewModels().addDataset(savedqcds);
	}
	
	private Field createField(String id, String fieldId, String dataType, String i18nName, boolean nullAble) {
		Field field = new Field(id);
		field.setField(fieldId);
		field.setDataType(dataType);
		field.setI18nName(i18nName);
		field.setNullAble(nullAble);
		return field;
	}
	
	private void createQueryTreeComp(LfwWidget widget) {
		queryTreeComp = new TreeViewComp();
		queryTreeComp.setId(QUERY_TEMPLATE_TREE_ID);
		queryTreeComp.setText(QUERY_TEMPLATE_ROOT_TEXT);
		queryTreeComp.setI18nName("query_optional");
		queryTreeComp.setLangDir("lfw");
		queryTreeComp.setWithRoot(false);
		TreeLevel level = new SimpleTreeLevel();
		level.setId("queryTemplateTreeLevel");
		level.setMasterKeyField("query_fieldId");
		level.setDataset(QUERY_DS_ID);
		level.setLabelFields("query_label");
		queryTreeComp.setTopLevel(level);

		EventSubmitRule sr = new EventSubmitRule();
		sr.setTabSubmit(true);
		
		WidgetRule wr = new WidgetRule();
		wr.setId(widget.getId());
		sr.addWidgetRule(wr);
		DatasetRule dsr = new DatasetRule();
		dsr.setId(QUERY_DS_ID);
		dsr.setType(DatasetRule.TYPE_CURRENT_LINE);
		wr.addDsRule(dsr);
				
		EventConf rowSelEvent = new EventConf();
		rowSelEvent.setJsEventClaszz("nc.uap.lfw.core.event.conf.TreeNodeListener");
		rowSelEvent.setOnserver(true);
		rowSelEvent.setSubmitRule(sr);
		rowSelEvent.setName("ondbclick");
		rowSelEvent.setMethodName("ondbclick");
		LfwParameter param = new LfwParameter();
		param.setName("treeNodeEvent");
		rowSelEvent.addParam(param);
		
		//�����˫���¼�
		queryTreeComp.addEventConf(rowSelEvent);
		widget.getViewComponents().addComponent(queryTreeComp);
	}
	
	private void createQueryCTreeComp(LfwWidget widget) {
		queryCTreeComp = new TreeViewComp();
		queryCTreeComp.setId(ADVANCE_TREE_ID);
		queryCTreeComp.setText(ADVANCE_TREE_ROOT_TEXT);
		RecursiveTreeLevel level = new RecursiveTreeLevel();
		level.setId("advanceTreeLevel");
		level.setMasterKeyField("query_condition_id");
		level.setRecursiveKeyField("query_condition_id");
		level.setRecursivePKeyField("query_condition_parentId");
		level.setDataset(QUERY_CT_DS_ID);
		level.setLabelFields("query_condition_label");
		queryCTreeComp.setTopLevel(level);
		queryCTreeComp.setDragEnable(true);
		queryCTreeComp.setWithRoot(false);
		
		// ���ӽڵ���ק�¼�
		TreeNodeListener listener = new TreeNodeListener();
		listener.setId("advance_tree_node_listener");
//		listener.setServerClazz(QueryAdvanceTreeNodeListener.class.getName());
		EventSubmitRule sr = new EventSubmitRule();
		sr.setTabSubmit(true);
		
		WidgetRule wr = new WidgetRule();
		wr.setId(widget.getId());
		sr.addWidgetRule(wr);
		DatasetRule dsr = new DatasetRule();
		dsr.setId(QUERY_CT_DS_ID);
		//TODO 
		dsr.setType(DatasetRule.TYPE_ALL_LINE);
		wr.addDsRule(dsr);
		
		TreeRule treeRule = new TreeRule();
		treeRule.setId(ADVANCE_TREE_ID);
		//TODO 
		treeRule.setType(TreeRule.TREE_ALL);
		wr.addTreeRule(treeRule);
		
		// ��ק�¼�
		EventHandlerConf dragEvent = TreeNodeListener.getOnDragEndEvent();
		dragEvent.setOnserver(true);
		dragEvent.setSubmitRule(sr);
		
		// ����¼����ı��ļ���Ϊ and �� or
		EventHandlerConf clickEvent = TreeNodeListener.getOnClickEvent();
		clickEvent.setOnserver(true);
		clickEvent.setSubmitRule(sr);
		
		listener.addEventHandler(dragEvent);
		listener.addEventHandler(clickEvent);
		
		queryCTreeComp.addListener(listener);
		
		widget.getViewComponents().addComponent(queryCTreeComp);
		
	}
	
	private void createSavedTreeComp(LfwWidget widget) {
		savedTreeComp = new TreeViewComp();
		savedTreeComp.setId(SAVED_TREE_ID);
		savedTreeComp.setText(SAVED_TREE_ROOT_TEXT);
		savedTreeComp.setWithRoot(false);
		RecursiveTreeLevel level = new RecursiveTreeLevel();
		level.setId("savedTreeLevel");
		level.setMasterKeyField("id");
		level.setRecursiveKeyField("guid");
		level.setRecursivePKeyField("parentguid");
		level.setDataset(SAVED_QC_DS_ID);
		level.setLabelFields("display");
		savedTreeComp.setTopLevel(level);

		//TODO
		// ���ѱ���Ĳ�ѯ��Ϣ
		TreeNodeListener listener = new TreeNodeListener();
		listener.setId("saved_tree_node_listener");
//		listener.setServerClazz(SavedTreeNodeListener.class.getName());
		EventSubmitRule sr = new EventSubmitRule();
		sr.setTabSubmit(true);
		
		WidgetRule wr = new WidgetRule();
		wr.setId(widget.getId());
		sr.addWidgetRule(wr);
		DatasetRule dsr1 = new DatasetRule();
		dsr1.setId(SAVED_QC_DS_ID);
		dsr1.setType(DatasetRule.TYPE_CURRENT_LINE);
		wr.addDsRule(dsr1);
		DatasetRule dsr2 = new DatasetRule();
		dsr2.setId(QUERY_CT_DS_ID);
		dsr2.setType(DatasetRule.TYPE_ALL_LINE);
		wr.addDsRule(dsr2);
		
		// ��ȡ��WidgetId
		String pWidgetId = null;
		if(!LfwRuntimeEnvironment.getMode().equals(WebConstant.MODE_DESIGN)){
			pWidgetId = LfwRuntimeEnvironment.getWebContext().getParameter("pwid");
//			pWidgetId = (String) LfwRuntimeEnvironment.getWebContext().getWebSession().getAttribute("pwid");
		}
		else{
			pWidgetId = "main";
		}
		
		// ���ύ����
		EventSubmitRule psr = new EventSubmitRule();
		WidgetRule pwr = new WidgetRule();
		pwr.setId(pWidgetId);
		psr.addWidgetRule(pwr);
		sr.setParentSubmitRule(psr);
		
		EventHandlerConf event = TreeNodeListener.getOnDbclickEvent();
		event.setOnserver(true);
		event.setSubmitRule(sr);
//		String script = "templateTreeSaveConditionOndbclick(treeNodeMouseEvent.node);";
//		event.setScript(script);
		
		listener.addEventHandler(event);
		
		savedTreeComp.addListener(listener);
		widget.getViewComponents().addComponent(savedTreeComp);
	}
	
	private void createOkBt(LfwWidget widget) {
		okButton = new ButtonComp();
		okButton.setId("okBt");
		okButton.setText("ȷ��");
		okButton.setI18nName("AbstractReferencePageModel-000000");
		okButton.setLangDir("lfw");
		
		
		
		EventSubmitRule submitRule = new EventSubmitRule();
		
		WidgetRule widgetRule = new WidgetRule();
		widgetRule.setId("main");
		submitRule.addWidgetRule(widgetRule);

		//�ύqueryConditionDataset������������
		DatasetRule dsr = new DatasetRule();
		dsr.setId(QUERY_CDS_ID);
		dsr.setType(DatasetRule.TYPE_ALL_LINE);
		widgetRule.addDsRule(dsr);

		EventConf buttonOkEvent = new EventConf();
		buttonOkEvent.setJsEventClaszz("nc.uap.lfw.core.event.conf.MouseListener");
		buttonOkEvent.setOnserver(true);
		buttonOkEvent.setSubmitRule(submitRule);
		buttonOkEvent.setName("onclick");
		//ȷ��ִ�к���
		buttonOkEvent.setMethodName("onOk");
		okButton.addEventConf(buttonOkEvent);
		
		
//		okButton.setWidth("60");
//		okButton.setHeight("22");
//		okButton.setAlign("left");
		
//		MouseListener okListener = new MouseListener();
//		okListener.setId("okListener");
////		okListener.setServerClazz(UifQueryTemplateMouseListener.class.getName());
//		
//		EventHandlerConf clickEvent = MouseListener.getOnClickEvent();
//		clickEvent.setAsync(true);
//		
//		clickEvent.setOnserver(true);
//		EventSubmitRule submitRule = new EventSubmitRule();
//		submitRule.setCardSubmit(true);
//		submitRule.setTabSubmit(true);
//		
//		WidgetRule wr = new WidgetRule();
//		wr.setId(widget.getId());
//		submitRule.addWidgetRule(wr);
//		
////		DatasetRule dsRule1 = new DatasetRule();
////		dsRule1.setId(QUERY_DS_ID);
////		dsRule1.setType(DatasetRule.TYPE_ALL_LINE);
////		submitRule.getWidgetRules().get(WIDGET_ID).addDsRule(dsRule1);
//		DatasetRule dsRule2 = new DatasetRule();
//		dsRule2.setId(QUERY_CDS_ID);
//		dsRule2.setType(DatasetRule.TYPE_ALL_LINE);
//		wr.addDsRule(dsRule2);
//		DatasetRule dsRule3 = new DatasetRule();
//		dsRule3.setId(QUERY_CT_DS_ID);
//		dsRule3.setType(DatasetRule.TYPE_ALL_LINE);
//		wr.addDsRule(dsRule3);
////		DatasetRule dsRule4 = new DatasetRule();
////		dsRule4.setId(SAVED_QC_DS_ID);
////		dsRule4.setType(DatasetRule.TYPE_ALL_LINE);
////		submitRule.getWidgetRules().get(WIDGET_ID).addDsRule(dsRule4);
//		
//		EventSubmitRule psr = new EventSubmitRule();
//		WidgetRule pwr = new WidgetRule();
//		pwr.setId(pWidgetId);
//		psr.addWidgetRule(pwr);
//		submitRule.setParentSubmitRule(psr);
//		clickEvent.setSubmitRule(submitRule);
//		okListener.addEventHandler(clickEvent);
//		okButton.addListener(okListener);
		
		widget.getViewComponents().addComponent(okButton);
	}
	
	private void createCancelBt(LfwWidget widget) {
		cancelButton = new ButtonComp();
		cancelButton.setId("cancelBt");
		cancelButton.setText("ȡ��");
		cancelButton.setI18nName("AbstractReferencePageModel-000001");
		cancelButton.setLangDir("lfw");

		
		
		EventSubmitRule submitRule = new EventSubmitRule();
		EventConf buttonOkEvent = new EventConf();
		buttonOkEvent.setJsEventClaszz("nc.uap.lfw.core.event.conf.MouseListener");
		buttonOkEvent.setOnserver(true);
		buttonOkEvent.setSubmitRule(submitRule);
		buttonOkEvent.setName("onclick");
		//ȡ��ִ�к���
		buttonOkEvent.setMethodName("onCancel");
		cancelButton.addEventConf(buttonOkEvent);
		
		
		
		widget.getViewComponents().addComponent(cancelButton);
	}
//
//	protected void processQueryTempletTotalVO(CpQueryTemplateTotalVO totalVO) {
//		
//	}
//
	public void loadDataToComp(LfwWidget widget, LfwWidget conf) {
		addQueryDsDataToComp(widget, conf);
	}

	private void addQueryDsDataToComp(LfwWidget widget, LfwWidget conf) {
		
		
		CpQueryTemplateTranslator loader = new CpQueryTemplateTranslator();
		CpQueryTemplateTotalVO totalVO = loader.getQueryTotalVO(null, "1019020201");
		loader.loadData(totalVO);
			
		
		CpQueryConditionVO[] vos = totalVO.getConditionVOs();
		
		if (vos != null) {
			for (CpQueryConditionVO vo : vos) {
				if (vo.getIfused() == null || !vo.getIfused().booleanValue())
					continue;
				FilterMeta tmp = voConvertor.convert(vo);
				allCandidateMetas.add(tmp);
			}
		}
		// ��ȡ���е�items
		Set<FilterMeta> metaSet = new HashSet<FilterMeta>();
		metaSet.addAll(allCandidateMetas);
		metaSet.addAll(sortedFiltemetas);
		//����Ȩ��
		String dataPowerOperation_Code = null;
		//��ѯģ���Ӧ��Ԫ����,Ϊ�˵õ���ѯģ����ֶζ�Ӧ������Ȩ��
		
			
		Iterator<FilterMeta> it = metaSet.iterator();
		while (it.hasNext()) {
			FilterMeta meta = it.next();
			String id = meta.getFieldCode().replaceAll("\\.", "_");
			
			switch(meta.getDataType()){
				case IQueryConstants.UFREF:
					String refCode = meta.getValueEditorDescription();
					
					NCRefNode refNode = new NCRefNode();
					refNode.setId(id + "_refNode");
					refNode.setRefcode(refCode);
					AbstractRefModel model = LfwNCRefUtil.getRefModel(refCode);
					if(model == null)
						continue;
					Integer refType = LfwNCRefUtil.getRefType(model);
					String refPath = "reference/";
					switch(refType){
						case IRefConst.GRID:
							refPath += "refgrid.jsp";
							break;
						case IRefConst.GRIDTREE:
							refPath += "refgridtree.jsp";
							break;
						case IRefConst.TREE:
						default:
							refPath += "reftree.jsp";
					}
					refNode.setPath(refPath);
					//refNode.setPageMeta(pageMeta);
					String dsId = BillTemplateConst.REF_MASTER_DS;
//					refNode.setRelationId(dsId + "..." + refType + "...pageMeta");
					//д��ds
					refNode.setWriteDs(QUERY_CDS_ID);
					refNode.setReadDs(dsId);
					
					if(model.getRefNameField().indexOf(".") != -1)
						refNode.setReadFields(model.getRefNameField().split("\\.")[1].replaceAll("\\.", "_") + "," + model.getPkFieldCode().split("\\.")[1].replaceAll("\\.", "_"));
					else
						refNode.setReadFields(model.getRefNameField().replaceAll("\\.", "_") + "," + model.getPkFieldCode().replaceAll("\\.", "_"));
					//����д���ֶ�
					refNode.setWriteFields("query_condition_value,refpk");
					refNode.setI18nName(refCode);
					refNode.setMultiSel(true);
					
					refNode.setPagemeta("reference");
										
					//�õ����õ�Ԫ���ݵ�Attribute
//					IAttribute attribute = bean.getAttributeByPath(meta.getFieldCode());
////					//�������Ȩ��
//					if(attribute != null){
//						dataPowerOperation_Code = attribute.getAccessPowerGroup();
//						if(dataPowerOperation_Code != null)
//							refNode.setExtendAttribute(RefNodeGenerator.DataPowerOperation_Code, dataPowerOperation_Code);
//					}
					widget.getViewModels().addRefNode(refNode);
					
//					refNodeList.add(refNode);
					
					break;
				case IQueryConstants.DATE:
					
					break;
				case IQueryConstants.BOOLEAN:
					StaticComboData bcd = new StaticComboData();
					bcd.setId("comb_" + meta.getFieldCode().replaceAll("\\.", "_") + "_value");
					CombItem cItem = new CombItem();
//					cItem.setI18nName("");
//					cItem.setValue("");
//					bcd.addCombItem(cItem);
					
					cItem = new CombItem();
					cItem.setI18nName(NCLangRes4VoTransl.getNCLangRes().getStrByID("lfw", "QueryTemplateDsHandler-000002")/*��*/);
					//cItem.setI18nName("��");
					cItem.setValue("Y");
					bcd.addCombItem(cItem);
//					
//					
					cItem = new CombItem();
					cItem.setI18nName(NCLangRes4VoTransl.getNCLangRes().getStrByID("lfw", "QueryTemplateDsHandler-000003")/*��*/);
					//cItem.setI18nName("��");
					cItem.setValue("N");
					bcd.addCombItem(cItem);
					
					widget.getViewModels().addComboData(bcd);
					
//					comboDataList.add(bcd);
					
					break;
				case IQueryConstants.USERCOMBO:
					String valueStr = meta.getValueEditorDescription();
					if(valueStr != null){
						StaticComboData cd = new StaticComboData();
						cd.setId("comb_" + meta.getFieldCode().replaceAll("\\.", "_") + "_value");
						
						CombItem item = new CombItem();
						item.setI18nName("");
						item.setText("");
						item.setValue("");
						cd.addCombItem(item);

						
						if(valueStr.startsWith("I,")){
							valueStr = valueStr.substring(2);
							String[] values = valueStr.split(",");
							for (int i = 0; i < values.length; i++) {
								item = new CombItem();
								item.setI18nName(values[i]);
								item.setValue("" + i);
								cd.addCombItem(item);
							}
						}
						else if(valueStr.startsWith("LX,")){
							valueStr = valueStr.substring(3);
							String[] valuesPair = valueStr.split(",");
							for (int i = 0; i < valuesPair.length; i++) {
								String pair = valuesPair[i];
								String[] values = pair.split("=");
								item = new CombItem();
								item.setI18nName(values[0]);
								item.setValue(values[1]);
								cd.addCombItem(item);
							}
						}
						else if(valueStr.startsWith("IX,")){
							valueStr = valueStr.substring(3);
							String[] valuesPair = valueStr.split(",");
							for (int i = 0; i < valuesPair.length; i++) {
								String pair = valuesPair[i];
								String[] values = pair.split("=");
								item = new CombItem();
								item.setI18nName(values[0]);
								item.setValue(values[1]);
								cd.addCombItem(item);
							}
						}
						else if(valueStr.startsWith("IM,") || valueStr.startsWith("SM,")){
							valueStr = valueStr.substring(3);
							try {
								IEnumType enumType = MDQueryService.lookupMDInnerQueryService().getEnumTypeByID(valueStr);
								if(enumType != null){
									List<IEnumValue> values = enumType.getEnumValues();
									Iterator<IEnumValue> vit = values.iterator();
									while(vit.hasNext()){
										IEnumValue value = vit.next();
										item = new CombItem();
										item.setI18nName(value.getName());
										item.setValue(value.getValue());
										cd.addCombItem(item);
									}
								}
							} catch (MetaDataException e) {
								throw new LfwRuntimeException(e.getMessage(), e);
							}
						}else if(valueStr.startsWith("SX,")){
							valueStr = valueStr.substring(3);
							String[] valuesPair = valueStr.split(",");
							for (int i = 0; i < valuesPair.length; i++) {
								String pair = valuesPair[i];
								String[] values = pair.split("=");
								item = new CombItem();
								item.setI18nName(values[0]);
								item.setValue(values[1]);
								cd.addCombItem(item);
							}
						}
						widget.getViewModels().addComboData(cd);
						
//						comboDataList.add(cd);
					}
					
					break;
			}
			
			IOperator[] ops = (IOperator[]) meta.getOperators();
			StaticComboData cd = new StaticComboData();
			// �����¹�����Id
			cd.setId("comb_" + meta.getFieldCode().replaceAll("\\.", "_"));
			if (ops != null) {
				for(IOperator op : ops) {
					CombItem item = new CombItem();
					item.setI18nName(op.toString());
					String operatorValue = op.getOperatorCode();
//					if(operatorValue.equals("<"))
//						operatorValue = "&lt;";
//					else if(operatorValue.equals("<="))
//						operatorValue = "&lt;=";
					item.setValue(operatorValue);
					cd.addCombItem(item);
				}
			}

			widget.getViewModels().addComboData(cd);
			
//			comboDataList.add(cd);
		}
	}
	
//	public void addConditionDsDataToComp() {
//		
//		Iterator<FilterMeta> it = sortedFiltemetas.iterator();
//		while (it.hasNext()) {
//			FilterMeta meta = it.next();
//			IFilter filter = fieldCodeFilterMap.get(meta.getFieldCode());
//			if (!meta.isCondition() || meta.isDefault()) {
//				Row row = querycds.getEmptyRow();
//				String id = meta.getFieldCode().replaceAll("\\.", "_");
//				row.setString(0, id);
//				row.setString(1, meta.getFieldName());
//				String operatorValue = null;
//				if(filter != null)
//					operatorValue = filter.getOperator().getOperatorCode();
//				else{
//					IOperator[] operators = meta.getOperators();
//					operatorValue = operators[0].getOperatorCode();
//					for(int i = 0; i < operators.length; i++) {
//						if(operators[i].getOperatorCode().equals("=")){
//							operatorValue = "=";
//							break;
//						}
//					}
//				}
//				//String operatorValue = filter == null ? meta.getOperators()[0].getOperatorCode() : filter.getOperator().getOperatorCode();
////				if(operatorValue.equals("<"))
////					operatorValue = "&lt;";
////				else if(operatorValue.equals("<="))
////					operatorValue = "&lt;=";
//				row.setString(2, operatorValue);
//				String defaultValue = meta.getDefaultValue();
//				row.setString(3, defaultValue);
//				String editorType;
//				StringBuffer extInfo = new StringBuffer();
//				switch(meta.getDataType()){
//					case IQueryConstants.STRING:
//						editorType = EditorTypeConst.STRINGTEXT;
//						break;
//					case IQueryConstants.INTEGER:
//						editorType = EditorTypeConst.INTEGERTEXT;
//						break;
//					case IQueryConstants.DECIMAL:
//						editorType = EditorTypeConst.DECIMALTEXT;
//						break;
//					case IQueryConstants.DATE:
//						editorType = EditorTypeConst.DATETEXT;
//						break;
//					case IQueryConstants.BOOLEAN:
//						editorType = EditorTypeConst.COMBODATA;
//						break;
//					case IQueryConstants.UFREF:
//						editorType = EditorTypeConst.REFERENCE;
//						String refId = meta.getFieldCode().replaceAll("\\.", "_") + "_refNode";
//						extInfo.append(refId);
//						row.setString(13, defaultValue);
//						break;
//						
//					case IQueryConstants.USERCOMBO:
//						editorType = EditorTypeConst.COMBODATA;
//						break;
//					default:
//						editorType = EditorTypeConst.STRINGTEXT;
//				}
//				
//				row.setString(5, editorType);
//				row.setString(6, "String");
//				row.setString(7, extInfo.toString());
//				//row.setString(10, extInfo.toString());
//				row.setString(8, meta.getFieldCode());
//				//�����ʵID
//				row.setString(10, id);
//				String fieldType = "3";
//				String logicType = "0";
//				if (!meta.isCondition())
//					logicType = "1";
//				if (meta.isRequired())
//					fieldType = "0";
//				else if (meta.isFixCondition())
//					fieldType = "1";
//				else if (meta.isDefault())
//					fieldType = "2";
//				row.setString(9, fieldType);
//				row.setString(14, logicType);
////				querycds.getRowSet().addRow(row);
//				Row newRow = (Row) row.clone();
//				newRow.setString(4, DatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
////				queryctreeds.getRowSet().addRow(newRow);
//			}
//		}
//		/*�߼����ĸ��ڵ�*/
//		Row rootRow = null;//queryctreeds.getEmptyRowAndAddToDs();
//		rootRow.setValue(0, DatasetConstant.QUERY_TEMPLATE_DEFAULT_ROOTPARENTID);
//		rootRow.setValue(1,"$#$");
//		rootRow.setValue(3,"and");
//		//queryConditionTreeDs.getRowSet().addRow((Row) rootRow.clone());
//	}

	
//	public void addSavedConditionDataToComp() {
//		String SQLStr = getLoadSQL();
//		if(SQLStr == null)
//			return;
//		IBizObjStorage storage = (IBizObjStorage) NCLocator.getInstance().lookup(
//				IBizObjStorage.class.getName());
//		// ���ҳ����ݿ�������SQL���������нڵ㡣
//		try{
//			ObjectNodeVO[] objNodeVOs = storage.loadAllObjectNodeData(null, STORAGE_CLASS_NAME, SQLStr);
//			if(objNodeVOs != null){
//				for(ObjectNodeVO obj : objNodeVOs){
//					Row row = queryctreeds.getEmptyRowAndAddToDs();
//					row.setString(0, obj.getId());
//					row.setString(1, obj.getGuid());
//					row.setString(2, obj.getParentguid());
//					row.setString(3, obj.getDisplay());
//					row.setString(4, obj.getKind());
//					row.setString(5, obj.getNote());
//				}
//			}
//		}
//		//���쳣��������׳�
//		catch(BusinessException e){
//			Logger.error(e);
//		}
//		
//	}

//	private ICpQueryTemplateQryService getQueryTemplate() {
//		if (queryTemplate == null)
//			queryTemplate = (ICpQueryTemplateQryService) NCLocator.getInstance().lookup(
//					ICpQueryTemplateQryService.class.getName());
//		return queryTemplate;
//	}
	
	// ע��ģ�������еĹ̶��ֶ�,
	private void registerFixCondition(List<FilterMeta> metas,
			ConditionVO[] conditionVOs) {
		if (conditionVOs == null || metas == null)
			return;
		for (FilterMeta meta : metas) {
			for (ConditionVO conditionVO : conditionVOs) {
				if (conditionVO.getFieldCode().equals(meta.getFieldCode())) {
					fieldCodeFilterMap.put(meta.getFieldCode(), voConvertor.convertConditionVO(
									meta, conditionVO));
				}
			}
		}
	}

	/**
	 * ����ģ��ID
	 * 
	 * @return
	 */
	private String loadTemplateID(CpTemplateParaVO paraVo) {
//		CpTemplateParaVO paraVo = new CpTemplateParaVO();
//		paraVo.setTempstyle(ITemplateStyle.queryTemplate);
//		paraVo.getPk_org(templateInfo.getPk_Org());
//		paraVo.setOrgType(templateInfo.getOrgType());
//		paraVo.setFunNode(templateInfo.getFunNode());
//		paraVo.setOperator(templateInfo.getUserid());
//		paraVo.setBusiType(templateInfo.getBusiType());
//		paraVo.setNodeKey(templateInfo.getNodekey());
		ICpSystemplateQryService qryService = CpTplServiceFacility.getSystemplateQryService();
		String templateid = null;
		try {
			templateid = qryService.getTemplateId(paraVo);
		} 
		catch (BusinessException e) {
			Logger.error("exception when find the template id ", e);
		}
		return templateid;
	}

	private void initFilterMetas(List<FilterMeta> allMetas, Dataset queryDs) {

		registerDefaultValue(allMetas);// ע��Filter��Ĭ��ֵ

		allCandidateMetas.addAll(initCandidatePnlData(allMetas, queryDs));

//		metaWithouFixConidition.addAll(getConditionalFilterMeta(allMetas,
//				new Predicate() {
//					public boolean evaluate(Object object) {// �õ��ǹ̶�����
//						return !((FilterMeta) object).isFixCondition();
//					}
//				}));
	}

	// ע��Filter��Ĭ��ֵ,����й̶�ֵ���Թ̶�Ϊ׼
	private void registerDefaultValue(List<FilterMeta> metas) {
		for (FilterMeta meta : metas) {
			IFilter filter = voConvertor.converDefaultMeta(meta);
			if (filter != null && !isContainsDefaultValue(meta)) {
				registerDafaultValue(meta, filter);
			}
		}
	}

	private boolean isCompositeMeta(FilterMeta meta) {
//		if (templateInfo.getComposeVos() == null)
//			return false;
//		List<CompositeMetaVO> compList = templateInfo.getComposeVos();
//		for (CompositeMetaVO compsemetavo : compList) {
//			if (compsemetavo.getActiveFieldCode().equals(meta.getFieldCode())) {
//				compsemetavo.setActiveFiltermeta(meta);
//				return true;
//			}
//		}
		return false;
	}

	private List<FilterMeta> initCandidatePnlData(List<FilterMeta> allMetas,
			Dataset queryDs) {
		return initCandidatePnlData(allMetas, new Predicate() {
			public boolean evaluate(Object object) {
				return !isCompositeMeta((FilterMeta) object);
			}
		});
	}

	private List<FilterMeta> initCandidatePnlData(List<FilterMeta> allMetas,
			Predicate predicate) {

		// DefaultMutableTreeNode root
		// =(DefaultMutableTreeNode)getModel().getRoot();
		
		List<FilterMeta> logicMetas = new ArrayList<FilterMeta>();
		List<FilterMeta> fixMetas = new ArrayList<FilterMeta>();// �̶�������List
		List<FilterMeta> defaultMetas = new ArrayList<FilterMeta>();// Ĭ����ʾ��List
		List<FilterMeta> requiredMetas = new ArrayList<FilterMeta>();// �������List

		List<FilterMeta> candidateMetas = new ArrayList<FilterMeta>();// ��ѡ������List,���Ѿ������õĴ�ѡ����

		// �������,��������ȥ
		for (FilterMeta meta : allMetas) {
			if (!predicate.evaluate(meta))
				continue;// ���������Ч�ģ����߱����meta��������
			if(!meta.isCondition())
				logicMetas.add(meta);
			// �̶�����
			else if (meta.isFixCondition()) {
				fixMetas.add(meta);
			}

				
			// ��������
			else if (meta.isRequired()) {
				requiredMetas.add(meta);
			} 
			else {
				//������requiredMetas
				candidateMetas.add(meta);
				if (meta.isDefault())
					defaultMetas.add(meta);
			}
		}

		// setModel(new DefaultTreeModel(root));

		sortedFiltemetas.addAll(logicMetas);
		sortedFiltemetas.addAll(fixMetas);
		sortedFiltemetas.addAll(requiredMetas);
		sortedFiltemetas.addAll(defaultMetas);

		// DispatchCandidateListener(ICandidateListener.INIT, sortedFiltemetas);

		return candidateMetas;

	}
	
	
//	private String getLoadSQL() {
//		StringBuffer loadSelectSQL= new StringBuffer("select kind,guid,id,display,parentguid, ");
//		loadSelectSQL.append(StringUtil.getUnionStr(MyFavoritesNode.PRIVATE_FLDS, ",",""));
//		loadSelectSQL.append(" from pub_myfavorite where ");
//		//if(m_selfLoadSQL==null || m_selfLoadSQL.trim().length()==0) {
//			//<li>1.���Templateid��Ϊ��,���ܽڵ�žͲ���������,��Ϊ��������ǿ繦�ܽڵ��ģ��
//			//<li>2.����ͨ���Զ������õ�������Templateid�ǿ���Ϊ�յ�!��ʱ�����õ���node_code
//			//<li>3.�������TempinfoΪ�գ���鲻���κζ���			
//			
//		if(templateInfo == null) {
//			loadSelectSQL.append(" 1=0 ");
//		}
//		else {
//			String tmpSQLSegment ="";
//			tmpSQLSegment = templateInfo.getTemplateId()==null ? " templateid is null " : "templateid = '" + templateInfo.getTemplateId() + "'";			
//			loadSelectSQL.append(tmpSQLSegment);
//			
//			if(templateInfo.getTemplateId()==null) {//see <li>2
//				tmpSQLSegment = templateInfo.getFunNode()==null ? " node_code is null ":"node_code = '" + templateInfo.getFunNode() + "'";
//				loadSelectSQL.append(" and "+ tmpSQLSegment);			
//			}
//			
//			//�û�IDΪ�յ��ղؼУ�Ϊ��˾����һ�������				
//			tmpSQLSegment = templateInfo.getUserid()==null ? " cuserid is null ":"( cuserid is null or cuserid = '" + templateInfo.getUserid() + "')";
//			loadSelectSQL.append(" and "+ tmpSQLSegment);
//			
//			
//			//��֯PKΪ�յ��ղؼУ�Ϊ�繫˾����һ�������
//			tmpSQLSegment = templateInfo.getPk_Org()==null ? " pk_org is null ":"( pk_org is null or pk_org = '" + templateInfo.getPk_Org() + "')";
//			loadSelectSQL.append(" and "+ tmpSQLSegment);	
//			
//			//���orgtype==null���򲻿���֮.....����Ĭ�ϰ�orgtypeΪ�յ�Ҳ��ΪĬ�ϵĲ����
//			if(templateInfo.getOrgType()!=null){
//				tmpSQLSegment = "(orgtypecode is null or orgtypecode = '" + templateInfo.getOrgType()+"')";
//				loadSelectSQL.append(" and "+ tmpSQLSegment);
//			}
////				loadSelectSQL.append(" order by ts desc");
//		}			
////		}
////		else 
////			loadSelectSQL.append(m_selfLoadSQL);
//		
//		return loadSelectSQL.toString();
//	}

//	public List<NCRefNode> getRefNodeList() {
//		return refNodeList;
//	}

//	public void setRefNodeList(List<NCRefNode> refNodeList) {
//		this.refNodeList = refNodeList;
//	}
	
	private void registerDafaultValue(IFilterMeta meta, IFilter filter)
	{
		fieldcode_filter_map.put(meta.getFieldCode(), filter);
	}
	
	private boolean  isContainsDefaultValue(IFilterMeta meta){
		return fieldcode_filter_map.containsKey(meta.getFieldCode());
	}
//
//	public String getTemplateId() {
//		return templateInfo.getTemplateId();
//	}

}


