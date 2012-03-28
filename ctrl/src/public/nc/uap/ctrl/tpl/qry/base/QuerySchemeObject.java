package nc.uap.ctrl.tpl.qry.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ��ѯ����BLOB����
 * <p>
 * ���л�ʱ�����з�ϵͳ��ѯ��������(���Զ���Ķ���)
 * <p>����ͨ������ķ�ʽ���뵽�������У�
 * <p><blockquote><pre>
 * String key = "myobjectKey";
 * Object value = new String("myobjectValue");
 * putOtherCondition(key, value);
 * </pre></blockquote><p>
 * �����л�ʱ��ͨ������ĵķ�ʽȡ���Զ�����������
 * <p><blockquote><pre>
 * String key = "myobjectKey";
 * Object value = getOtherCondition(key);
 * </pre></blockquote><p>
 * 
 * @author ����ΰ
 * 
 * �������ڣ�2010-10-20
 */
public class QuerySchemeObject implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853080459046065628L;
	
	/** Key�����ز�ѯ���� */
	public static final String KEY_DESCRIPTION = "description";
	/** Key�����ز�ѯ����(QueryResult) */
	public static final String KEY_QUERY_RESULT = "queryresult";
	/** Key�����ز�ѯ��ʽ��Ϣ */
	public static final String KEY_QUERY_TYPE = "querytype";
	/** Key������NormalCondition */
	public static final String KEY_NORMAL_CONDITION = "normalcondition";
	/** Key������LogicalCondition */
	public static final String KEY_LOGICAL_CONDITION = "logicalcondition";
	
	private Map<String, Object> map;
	
	// �Ƿ������Ʒ������ڱ������ڲ����г־û�������QuerySchemeVO��ȡ�����Բ�����
	private transient boolean isComplete;
	// �Ƿ��ǿ��ٲ�ѯ�������ڱ������ڲ����г־û�������QuerySchemeVO���ø�����
	private transient boolean isQuickQS;
	
	public QuerySchemeObject() {
		super();
	}
	
	/**
	 * �Ƿ������Ʒ���
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * �����Ƿ������Ʒ���
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * �Ƿ��ǿ��ٲ�ѯ����
	 */
	public boolean isQuickQS() {
		return isQuickQS;
	}

	/**
	 * �����Ƿ��ǿ��ٲ�ѯ����
	 */
	public void setQuickQS(boolean isQuickQS) {
		this.isQuickQS = isQuickQS;
	}

	/**
	 * �洢���������Զ������������map
	 * <p>KEY���Զ�����������ı�ʶ
	 * <p>VALUE���Զ�����������
	 */
	private Map<String, Object> getMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * �����Զ�����������ı�ʶ���ض���
	 * 
	 * @param key
	 *            �Զ�����������ı�ʶ
	 */
	public Object get(String key) {
		return getMap().get(key);
	}
	
	/**
	 * �洢�Զ�����������
	 * 
	 * @param key
	 *            �Զ�����������ı�ʶ
	 * @param value
	 *            �Զ�����������
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