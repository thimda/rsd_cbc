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
 * 系统函数管理器
 * <p>
 * 单例，统一管理查询模板的系统函数
 * 
 * @author 刘晨伟
 *
 * 创建日期：2010-4-1
 */
public class SysFunctionManager {
	
	/** 单例，每个数据源对应一个单例对象 */
	private static Map<String, SysFunctionManager> dsName_Instance_Map = new ConcurrentHashMap<String, SysFunctionManager>();
	
	/** 锁对象 */
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

	/** default_className_map使用的默认key */
	private static final String DEFAULT_KEY = "default";
	
	private Map<String, List<String>> default_className_map;
	
	/* 系统函数列表 */
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
	 * 返回所有的预置函数
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
	 * 读入自定义的函数
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
	 * 返回所有的预置系统函数
	 */
	public List<ISysFunction> getFunctionsByType(SFType sfType) {
		List<ISysFunction> typeFunctions = new ArrayList<ISysFunction>();
		List<ISysFunction> allFunctions = getAllFunctions();
		//对于非元数据的查询模板，参照类型的查询条件使用系统函数的范围是：所有的非日期类型的系统函数
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
	 * 根据函数编码返回其函数值
	 * 
	 * @param expression
	 *            函数编码
	 */
	public RefResultVO calculate(String expression) {
		//解决资产的环境出现null表达式莫名其妙的问题，暂时先加上
		if(StringUtils.isBlank(expression)){
			return new RefResultVO();
		}
		for (ISysFunction function : getAllFunctions()) {
			if (function.getCode().equals(expression)) {
				return function.value();
			}
		}
		// 解释为不常用的日期函数，如#day(7)#
		return new DateFormularFunction(expression, null).value();
	}
	
	/**
	 * 根据函数编码返回其函数名称
	 * 
	 * @param code
	 *            函数编码
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
	 * 根据函数名称返回其函数编码
	 * 
	 * @param name
	 *            函数名称
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