package nc.uap.ctrl.tpl.qry.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询方案BLOB对象
 * <p>
 * 序列化时，所有非系统查询条件对象(即自定义的对象)
 * <p>必须通过下面的方式加入到本对象中：
 * <p><blockquote><pre>
 * String key = "myobjectKey";
 * Object value = new String("myobjectValue");
 * putOtherCondition(key, value);
 * </pre></blockquote><p>
 * 反序列化时，通过下面的的方式取回自定义条件对象：
 * <p><blockquote><pre>
 * String key = "myobjectKey";
 * Object value = getOtherCondition(key);
 * </pre></blockquote><p>
 * 
 * @author 刘晨伟
 * 
 * 创建日期：2010-10-20
 */
public class QuerySchemeObject implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853080459046065628L;
	
	/** Key：返回查询描述 */
	public static final String KEY_DESCRIPTION = "description";
	/** Key：返回查询条件(QueryResult) */
	public static final String KEY_QUERY_RESULT = "queryresult";
	/** Key：返回查询方式信息 */
	public static final String KEY_QUERY_TYPE = "querytype";
	/** Key：返回NormalCondition */
	public static final String KEY_NORMAL_CONDITION = "normalcondition";
	/** Key：返回LogicalCondition */
	public static final String KEY_LOGICAL_CONDITION = "logicalcondition";
	
	private Map<String, Object> map;
	
	// 是否是完善方案，在本对象内不进行持久化处理，由QuerySchemeVO读取该属性并保存
	private transient boolean isComplete;
	// 是否是快速查询方案，在本对象内不进行持久化处理，由QuerySchemeVO设置该属性
	private transient boolean isQuickQS;
	
	public QuerySchemeObject() {
		super();
	}
	
	/**
	 * 是否是完善方案
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * 设置是否是完善方案
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * 是否是快速查询方案
	 */
	public boolean isQuickQS() {
		return isQuickQS;
	}

	/**
	 * 设置是否是快速查询方案
	 */
	public void setQuickQS(boolean isQuickQS) {
		this.isQuickQS = isQuickQS;
	}

	/**
	 * 存储所有其他自定义条件对象的map
	 * <p>KEY：自定义条件对象的标识
	 * <p>VALUE：自定义条件对象
	 */
	private Map<String, Object> getMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * 根据自定义条件对象的标识返回对象
	 * 
	 * @param key
	 *            自定义条件对象的标识
	 */
	public Object get(String key) {
		return getMap().get(key);
	}
	
	/**
	 * 存储自定义条件对象
	 * 
	 * @param key
	 *            自定义条件对象的标识
	 * @param value
	 *            自定义条件对象
	 */
	public void put(String key, Object value) {
		if (value instanceof Serializable) {
			getMap().put(key, value);
		} else {
			throw new RuntimeException("object must be serializable.");
		}
	}
	
	public Object getQueryResult() {
		return get(KEY_QUERY_RESULT);
	}

	public void setQueryResult(Object queryresult) {
		put(KEY_QUERY_RESULT, queryresult);
	}
	
	public Object getQueryType() {
		return get(KEY_QUERY_TYPE);
	}

	public void setQueryType(Object querytype) {
		put(KEY_QUERY_TYPE, querytype);
	}

	public Object getNormalCondition() {
		return get(KEY_NORMAL_CONDITION);
	}

	public void setNormalCondition(Object normalCondition) {
		put(KEY_NORMAL_CONDITION, normalCondition);
	}

	public Object getLogicCondition() {
		return get(KEY_LOGICAL_CONDITION);
	}

	public void setLogicCondition(Object logicCondition) {
		put(KEY_LOGICAL_CONDITION, logicCondition);
	}
	
	public String[] getUserDefineKeys(){
		List<String> userDefineKeyList = new ArrayList<String>();
		Set<String> keySet = getMap().keySet();
		if(keySet!=null){
			List<String> qsObjKeys = getQsObjKeys();
			for(String key : keySet){
				if(!qsObjKeys.contains(key)){
					userDefineKeyList.add(key);
				}
			}
		}
		return userDefineKeyList.toArray(new String[0]);
	}

	private List<String> getQsObjKeys() {
		List<String> qsObjKeys = new ArrayList<String>();
		qsObjKeys.add(KEY_DESCRIPTION);
		qsObjKeys.add(KEY_QUERY_RESULT);
		qsObjKeys.add(KEY_LOGICAL_CONDITION);
		qsObjKeys.add(KEY_NORMAL_CONDITION);
		qsObjKeys.add(KEY_QUERY_TYPE);
		return qsObjKeys;
	}
}