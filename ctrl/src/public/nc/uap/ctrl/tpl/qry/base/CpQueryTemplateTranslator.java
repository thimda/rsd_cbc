package nc.uap.ctrl.tpl.qry.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.md.MDBaseQueryFacade;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService;
import nc.uap.ctrl.tpl.qry.meta.ConditionVO;
import nc.uap.ctrl.tpl.qry.meta.FilterMeta;
import nc.uap.ctrl.tpl.qry.meta.IFilter;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.log.LfwLogger;

import org.apache.commons.lang.StringUtils;

public class CpQueryTemplateTranslator {
	private QtVO2MetaConvertor voConvertor = new QtVO2MetaConvertor();
	// ���ݿ�������ģ������(except �߼�����)
	private List<FilterMeta> allDBMetas = new ArrayList<FilterMeta>();
	
	// ���к�ѡ����,ֻ�Ǽ�¼��;���ڲ�ε�,ֻ��������һ��mockFiltermeta
	private List<FilterMeta> allCandidateMetas = new ArrayList<FilterMeta>();
	
	//�߼�����
	private List<FilterMeta> logicMetas = new ArrayList<FilterMeta>();
	
	private List<FilterMeta> fixMetas = new ArrayList<FilterMeta>();
	
	private List<FilterMeta> defaultMetas = new ArrayList<FilterMeta>();
	
	private List<FilterMeta> requiredMetas = new ArrayList<FilterMeta>();
//	private List<FilterMeta> candidateMetas = new ArrayList<FilterMeta>();
	
	// ���� �̶�����������������Ĭ������˳����ѡ������filterMeta
	private List<FilterMeta> sortedFiltemetas = new ArrayList<FilterMeta>();
	
	private Map<String, IFilter> fieldCodeFilterMap = new HashMap<String, IFilter>();
	
//	private Map<String, IFilter> fieldcode_filter_map = new HashMap<String, IFilter>();
	
	public CpQueryTemplateTranslator() {
	}

	public void loadData(CpQueryTemplateTotalVO totalVO) {
		//�����ﴦ��
		//translate(totalVO);
			
		ConditionVO[] fixConditionVOs = totalVO.getTempletVO().getFixConditions();
		CpQueryConditionVO[] vos = totalVO.getConditionVOs();

		if (vos != null) {
			for (CpQueryConditionVO vo : vos) {
				if (vo.getIfused() == null || !vo.getIfused().booleanValue())
					continue;
				FilterMeta tmp = voConvertor.convert(vo);
				allDBMetas.add(tmp);
			}
		}
		registerFixCondition(allDBMetas, fixConditionVOs);
		sortAllMetas();
	}
	

	// ע��ģ�������еĹ̶��ֶ�,
	private void registerFixCondition(List<FilterMeta> metas,
			ConditionVO[] conditionVOs) {
		if (conditionVOs == null || metas == null)
			return;
		for (FilterMeta meta : metas) {
			for (ConditionVO conditionVO : conditionVOs) {
				if (conditionVO.getFieldCode().equals(meta.getFieldCode())) {
					fieldCodeFilterMap.put(meta.getFieldCode(), voConvertor
							.convertConditionVO(meta, conditionVO));
				}
			}
		}
	}
//	
//	/**
//	 * �����ģ�������Դ
//	 * @param vo
//	 */
//	 private static void translate(CpQueryTemplateTotalVO vo) {
//	        if (vo != null) {
//	        	Map<String, String> resIDToValueMap = null;
//	        	//��ѯ���еĶ�����Դ
////	        	IQueryTemplateMultilangQry multilangQry = NCLocator.getInstance().lookup(IQueryTemplateMultilangQry.class);
////	    		try {
////	    			resIDToValueMap = multilangQry.getMutilangOfTotalVO(vo);
////	        	} 
////	    		catch (BusinessException e) {
////	    			LfwLogger.error(e.getMessage(),e);
////	    			throw new BusinessRuntimeException(e.getMessage());
////	    		}
//	        	
//	        	//��������
//	        	CpQueryTemplateVO templetVO = vo.getTempletVO();
//	        	if(!StringUtils.isBlank(templetVO.getResid())){
//	        		templetVO.setModelname(resIDToValueMap.get(templetVO.getResid()));
//	        	}
//	        	//�����ѯ�����ӱ�
//	        	CpQueryConditionVO[] conditionVOs = vo.getConditionVOs();
//	        	if(conditionVOs == null || conditionVOs.length == 0){
//	        		return;
//	        	}
//	        	if(vo.getTempletVO().isMetadata()) {
//	        		String beanID = vo.getTempletVO().getMetaclass();
//					MDTranslator mdTranslator = new MDTranslator(beanID);
//					mdTranslator.setResIDToValueMap(resIDToValueMap);
//					mdTranslator.translate(vo);
//					
//	        	}else {
//	        		for(CpQueryConditionVO conVO : conditionVOs){
//	    				if(!StringUtils.isBlank(conVO.getResid())){
//	    					conVO.setFieldname(resIDToValueMap.get(conVO.getResid()));
//	    				}
//	    			}
////	        		VoTranslator.translate(new QueryConditionTranslator(vo));
//	        	}
////	            CpQueryConditionVO[] vos = vo.getConditionVOs();
////	            if(vos != null){
////	                String nodecode = ((QueryTempletVO) vo.getParentVO()).getNodeCode();
////	                for (int i=0;i<vos.length;i++)
////	                    vos[i].setNodecode(nodecode);
////	            }
//	        }
//	    }

//	private void initFilterMetas(List<FilterMeta> allMetas, Dataset queryDs) {
//
//		registerDefaultValue(allMetas);// ע��Filter��Ĭ��ֵ
//
//		allCandidateMetas.addAll(initCandidatePnlData(allMetas, queryDs));
//
//		// metaWithouFixConidition.addAll(getConditionalFilterMeta(allMetas,
//		// new Predicate() {
//		// public boolean evaluate(Object object) {// �õ��ǹ̶�����
//		// return !((FilterMeta) object).isFixCondition();
//		// }
//		// }));
//	}

//	// ע��Filter��Ĭ��ֵ,����й̶�ֵ���Թ̶�Ϊ׼
//	private void registerDefaultValue(List<FilterMeta> metas) {
//		for (FilterMeta meta : metas) {
//			IFilter filter = voConvertor.converDefaultMeta(meta);
//			if (filter != null && !isContainsDefaultValue(meta)) {
//				registerDafaultValue(meta, filter);
//			}
//		}
//	}

	private void sortAllMetas() {
		logicMetas = new ArrayList<FilterMeta>();
		fixMetas = new ArrayList<FilterMeta>();// �̶�������List
		defaultMetas = new ArrayList<FilterMeta>();// Ĭ����ʾ��List
		requiredMetas = new ArrayList<FilterMeta>();// �������List
		allCandidateMetas = new ArrayList<FilterMeta>();// ��ѡ������List,���Ѿ������õĴ�ѡ����

		// �������,��������ȥ
		for (FilterMeta meta : allDBMetas) {
			if (isCompositeMeta(meta))
				continue;// ���������Ч�ģ����߱����meta��������
			if (!meta.isCondition())
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
				// ������requiredMetas
				allCandidateMetas.add(meta);
				if (meta.isDefault())
					defaultMetas.add(meta);
			}
		}

		sortedFiltemetas.addAll(logicMetas);
		sortedFiltemetas.addAll(fixMetas);
		sortedFiltemetas.addAll(requiredMetas);
		sortedFiltemetas.addAll(defaultMetas);
	}

//	private boolean isContainsDefaultValue(IFilterMeta meta) {
//		return fieldcode_filter_map.containsKey(meta.getFieldCode());
//	}
//
//	private void registerDafaultValue(IFilterMeta meta, IFilter filter) {
//		fieldcode_filter_map.put(meta.getFieldCode(), filter);
//	}

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
	

	public List<FilterMeta> getAllCandidateMetas() {
		return allCandidateMetas;
	}

	public List<FilterMeta> getSortedFiltemetas() {
		return sortedFiltemetas;
	}

	public Map<String, IFilter> getFieldCodeFilterMap() {
		return fieldCodeFilterMap;
	}
	
	 private static class MDTranslator {

			private IBean bean;
			
			private Map<String, String> resIDToValueMap = null;
			
			public MDTranslator(String beanID) {
				this.bean = getBeanByID(beanID);
				
			}

			public void translate(CpQueryTemplateTotalVO vo) {
				CpQueryConditionVO[] qcvos = vo == null ? null : vo.getConditionVOs();
				if (qcvos == null) {
					return;
				}
				for (CpQueryConditionVO qcvo : qcvos) {
					//1��ȡ��ѯ���������resID��2��resIDΪ��ʱȡԪ���������õ�resID��3�����Ͼ�ȡ����ֵʱ��ʾ��ѯ�����ϵ�fieldName
					String resID = qcvo.getResid();
					
					String displayName = null;
					if(!StringUtils.isBlank(resID)){
						if(resIDToValueMap != null){
							displayName = resIDToValueMap.get(resID);
						}
					}else{
						IAttribute attribute = getAttribute(qcvo.getFieldcode());
						if(attribute!=null){
							resID = attribute.getResID();
							if(!StringUtils.isBlank(resID)){
								displayName = resIDToValueMap.get(resID);
							}
						}
					}
					if(!StringUtils.isBlank(displayName)){
						qcvo.setFieldname(displayName);
					}
				}
			}
			
			
			private IAttribute getAttribute(String attributePath) {
				return bean.getAttributeByPath(attributePath);
			}

			private static IBean getBeanByID(String beanID) {
				IBean bean = null;
				try {
					bean = MDBaseQueryFacade.getInstance().getBeanByID(beanID);
				} catch (MetaDataException e) {
					throw new RuntimeException("����ID��ȡBeanʧ��. ID=" + beanID);
				}
				return bean;
			}

			public void setResIDToValueMap(Map<String, String> resIDToValueMap) {
				this.resIDToValueMap = resIDToValueMap;
			}
		}

	public List<FilterMeta> getDefaultMetas() {
		return defaultMetas;
	}

	public void setDefaultMetas(List<FilterMeta> defaultMetas) {
		this.defaultMetas = defaultMetas;
	}

	public List<FilterMeta> getRequiredMetas() {
		return requiredMetas;
	}

	public void setRequiredMetas(List<FilterMeta> requiredMetas) {
		this.requiredMetas = requiredMetas;
	}
	
	public CpQueryTemplateTotalVO getQueryTotalVO(String pkuser, String queryNode) {
		ICpQryTemplateInnerQryService qryService = NCLocator.getInstance()
				.lookup(ICpQryTemplateInnerQryService.class);
		try {
			String pk_template = getTemplateId(queryNode);
			if (pk_template == null)
				return null;
			CpQueryTemplateTotalVO totalVO = qryService
					.getQueryTotalVO(pk_template);
			return totalVO;
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			return null;
		}
	}
	
	
	public CpQueryTemplateTotalVO getQueryTotalVOByPk(String pk_tempalte) {
		ICpQryTemplateInnerQryService qryService = NCLocator.getInstance()
				.lookup(ICpQryTemplateInnerQryService.class);
		try {
			CpQueryTemplateTotalVO totalVO = qryService.getQueryTotalVO(pk_tempalte);
			return totalVO;
		} catch (TplBusinessException e) {
			LfwLogger.error(e);
			return null;
		}
	}


	protected String getTemplateId(String queryNode) {
		AppLifeCycleContext ctx = AppLifeCycleContext.current();
		if (ctx == null) {
			return null;
		}
		ICpQryTemplateInnerQryService qryService = NCLocator.getInstance().lookup(ICpQryTemplateInnerQryService.class);
		String pk_user = LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
		try {
			String pk_template = qryService.getQueryTemplatePkByNode(pk_user, queryNode);
			return pk_template;
		} 
		catch (TplBusinessException e) {
			LfwLogger.error(e);
			return null;
		}
	}
	
}
