package nc.uap.ctrl.tpl.qry.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.sysfunc.ISysFunctionQueryService;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.uap.ctrl.tpl.qry.sysfunc.AccountingBookFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.BusinessOperatorFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.CurrentAccperiodFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.DateFormularFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.DateFormularFunctionFactory;
import nc.uap.ctrl.tpl.qry.sysfunc.FirstAccperiodFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.ISysFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.MainOrgFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.OperatorFunction;
import nc.uap.ctrl.tpl.qry.sysfunc.SysFuncVO;
import nc.vo.bd.pub.BDCacheFactory;
import nc.vo.bd.pub.BDCacheMiscUtil;
import nc.vo.cache.ICache;
import nc.vo.pub.BusinessException;

import org.apache.commons.lang.StringUtils;

/**
 * ϵͳ����������
 * <p>
 * ������ͳһ�����ѯģ���ϵͳ����
 * 
 * @author ����ΰ
 *
 * �������ڣ�2010-4-1
 */
public class SysFunctionManager {
	
	/** ������ÿ������Դ��Ӧһ���������� */
	private static Map<String, SysFunctionManager> dsName_Instance_Map = new ConcurrentHashMap<String, SysFunctionManager>();
	
	/** ������ */
	private static volatile Object lockObject = new Object();
	
	private static final String QTSYSFUNCTION = "QT_SYSFUNCTION";
	
	public static SysFunctionManager getInstance() {
		String dsName = BDCacheMiscUtil.getCurrentDatasourceName();
		SysFunctionManager instance = (SysFunctionManager) dsName_Instance_Map.get(dsName);
		if (instance == null) {
			synchronized (lockObject) {
				instance = (SysFunctionManager) dsName_Instance_Map.get(dsName);
				if (instance == null) {
					instance = new SysFunctionManager();
					dsName_Instance_Map.put(dsName, instance);
				}
			}
		}
		return instance;
	}

	/** default_className_mapʹ�õ�Ĭ��key */
	private static final String DEFAULT_KEY = "default";
	
	private Map<String, List<String>> default_className_map;
	
	/* ϵͳ�����б� */
	private static List<ISysFunction> systemFunctions = new ArrayList<ISysFunction>();

	static {
		systemFunctions.addAll(DateFormularFunctionFactory.createDefaultFunctions());
		systemFunctions.add(new OperatorFunction());
		systemFunctions.add(new BusinessOperatorFunction());
		systemFunctions.add(new MainOrgFunction());
		systemFunctions.add(new AccountingBookFunction());
		systemFunctions.add(new FirstAccperiodFunction());
		systemFunctions.add(new CurrentAccperiodFunction());
	}
	
	@SuppressWarnings("unchecked")
	private SysFunctionManager() {
		ICache cache = BDCacheFactory.getCacheWithFileStratery(QTSYSFUNCTION);
		default_className_map = BDCacheFactory.getTableVersionSensitiveMap(cache, 
				new String[]{SysFuncVO.getDefaultTableName()});
	}
	
	/**
	 * �������е�Ԥ�ú���
	 */
	public List<ISysFunction> getAllFunctions() {
		List<ISysFunction> fucntions = new ArrayList<ISysFunction>();
		fucntions.addAll(systemFunctions);
		List<ISysFunction> customFunctions = getCustomFunctions();
		if(customFunctions!=null && customFunctions.size()>0){
			fucntions.addAll(customFunctions);
		}
		return fucntions;
	}
	
	private List<ISysFunction> getCustomFunctions() {
		List<String> classNames = default_className_map.get(DEFAULT_KEY);
		if(classNames == null){
			initCustomFunctionNames();
		}
		classNames = default_className_map.get(DEFAULT_KEY);
		if(classNames!=null && classNames.size()>0){
			List<ISysFunction> list = new ArrayList<ISysFunction>();
			for(String className : classNames){
				try{
					list.add((ISysFunction) Class.forName(className).newInstance());
				} catch (InstantiationException e) {
    				e.printStackTrace();
    			} catch (IllegalAccessException e) {
    				e.printStackTrace();
    			} catch (ClassNotFoundException e) {
    				e.printStackTrace();
    			}
			}
			return list;
		}
		return null;
	}

	/**
	 * �����Զ���ĺ���
	 */
	private void initCustomFunctionNames() {
		List<String> classNames = default_className_map.get(DEFAULT_KEY);
		if(classNames == null) {
    		synchronized (lockObject) {
    			default_className_map.clear();
    			classNames = querySysFuncClassNames();
    			default_className_map.put(DEFAULT_KEY, classNames);
    		}
		} 
	}
	
	private List<String> querySysFuncClassNames() {
		String[] classNames = null;
		try {
			classNames = NCLocator.getInstance().lookup(
					ISysFunctionQueryService.class).getRegisteredFunctionsClassName();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return Arrays.asList(classNames == null ? new String[0] : classNames);
	}
	
	/**
	 * �������е�Ԥ��ϵͳ����
	 */
	public List<ISysFunction> getFunctionsByType(SFType sfType) {
		List<ISysFunction> typeFunctions = new ArrayList<ISysFunction>();
		List<ISysFunction> allFunctions = getAllFunctions();
		//���ڷ�Ԫ���ݵĲ�ѯģ�壬�������͵Ĳ�ѯ����ʹ��ϵͳ�����ķ�Χ�ǣ����еķ��������͵�ϵͳ����
		if(sfType.getType()==SFType.TYPE_REF && StringUtils.isBlank(sfType.getRefBeanID())){
			for(ISysFunction function : allFunctions){
				if(function.getType().getType()==SFType.TYPE_REF){
					typeFunctions.add(function);
				}
			}
		}else{
			for (ISysFunction function : allFunctions) {
				if(sfType.equals(function.getType()) || function.getType().getSupportBeanList().contains(sfType.getRefBeanID())){
					typeFunctions.add(function);
				}
			}
		}
		
		return typeFunctions;
	}
	
	/**
	 * ���ݺ������뷵���亯��ֵ
	 * 
	 * @param expression
	 *            ��������
	 */
	public RefResultVO calculate(String expression) {
		//����ʲ��Ļ�������null���ʽĪ����������⣬��ʱ�ȼ���
		if(StringUtils.isBlank(expression)){
			return new RefResultVO();
		}
		for (ISysFunction function : getAllFunctions()) {
			if (function.getCode().equals(expression)) {
				return function.value();
			}
		}
		// ����Ϊ�����õ����ں�������#day(7)#
		return new DateFormularFunction(expression, null).value();
	}
	
	/**
	 * ���ݺ������뷵���亯������
	 * 
	 * @param code
	 *            ��������
	 */
	public String getNameByCode(String code) {
		for (ISysFunction function : getAllFunctions()) {
			if (function.getCode().equals(code)) {
				return function.getName();
			}
		}
		return null;
	}
	
	/**
	 * ���ݺ������Ʒ����亯������
	 * 
	 * @param name
	 *            ��������
	 */
	public String getCodeByName(String name) {
		for (ISysFunction function : getAllFunctions()) {
			if (function.getName().equals(name)) {
				return function.getCode();
			}
		}
		return null;
	}
}