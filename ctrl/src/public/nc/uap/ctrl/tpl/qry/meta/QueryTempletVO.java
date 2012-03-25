package nc.uap.ctrl.tpl.qry.meta;

/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product.                              *
 \***************************************************************/

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import nc.ui.querytemplate.filter.IFilter;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.querytree.QueryTree;
import nc.ui.querytemplate.querytree.QueryTree.FilterNode;
import nc.ui.querytemplate.querytree.QueryTree.QueryTreeNode;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDateTime;

/**
 * ��ѯģ��VO. ��������:(2001-6-14)
 * 
 * @author:������
 * 
 * �޸�����:2001-07-05 ��������:�޸��˱��ṹ,����ע�� �޸���:����
 */
public class QueryTempletVO extends CircularlyAccessibleValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ����������������Ӧ��ֻ����ѯģ���ڲ��߼�ʹ��
	 */
	
	/** ��ѯģ���ѯģʽ */
	public static final int QUERY_TYPE = 0;
	/** ��ѯģ������ģʽ */
	public static final int SETTING_TYPE = 1;
	
	// �̶�������ѯ��ʹ��ģʽ
	private int fixQueryTreeType = QUERY_TYPE;
	
	private String id;

	private String pkCorp;

	private String modelCode;

	private String modelName;

	// �������ܽڵ���룬���ڼ�¼��ģ����������һ���ڵ㣬�Ա����ڡ����ܽڵ�Ĭ��ģ����䡿�ڵ�ʹ��
	// �ڷ���ģ��ʱ��ģ�������Ĺ��ܽڵ��������˸�ģ�����Ǹ��ڵ�ı�ѡģ���������ʾ
	// ������֮�󣬷�����м�¼���Ǹ��ĸ����ܽڵ����(funcode)�����˸�ģ�壬��nodeCode���Ծ�û����
	private String nodeCode;

	private String description;

	private java.lang.String fixConditionString;

	private ConditionVO[] fixConditionVOs;

	private String resid;

	private String metaclass;
	
	// ����ѯģ��(ϵͳĬ��ģ��)ID
	private String parentid;
	
	/**
	 * �̶�������ѯ���������ɵ�XML�ַ���ѹ������ֽ�����<br>
	 * (û��ֱ��ʹ��QueryTree������Ϊ�˼�С��������)<br>
	 * ���洢�����ݿ��еĹ̶�������ѯ��ʵ���ϲ�û������Ч��<br>
	 * ֻ��Ϊ���ܹ���ǰ̨���ù���ʹ��ʱ�ܹ����ָ�����<br>
	 * ��������󹩲�ѯ�Ի���ʹ��ʱ�������ֹ���QueryTree�и����ڵ��ifFixCondition������Ϊtrue<br>
	 * @see nc.vo.pub.query.QueryTempletVO.getFixQueryTree4Query()
	 */
	private byte[] fixQueryTree;

	private Integer dr;

	private UFDateTime ts;

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	/**
	 * ʹ�������ֶν��г�ʼ���Ĺ�����.
	 * 
	 * ��������:(2001-6-14)
	 */
	public QueryTempletVO() {

	}

	/**
	 * ʹ���������г�ʼ���Ĺ�����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param ??fieldNameForMethod??
	 *            ����ֵ
	 */
	public QueryTempletVO(String newId) {

		// Ϊ�����ֶθ�ֵ:
		id = newId;
	}

	/**
	 * ����Object�ķ���,��¡���VO����.
	 * 
	 * ��������:(2001-6-14)
	 */
	public Object clone() {

		// ���ƻ������ݲ������µ�VO����:
		Object o = null;
		try {
			o = super.clone();
		} catch (Exception e) {
		}
		QueryTempletVO queryTemplet = (QueryTempletVO) o;

		// �������渴�Ʊ�VO�������������:

		return queryTemplet;
	}

	/**
	 * <p>
	 * ��Ҫ��һ��ѭ���з��ʵ����Ե���������.
	 * <p>
	 * ��������:(??Date??)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getAttributeNames() {

		return new String[] { "pk_corp", "model_code", "model_name",
				"node_code", "description", "resid", "ts", "dr", "metaclass" };
	}

	/**
	 * <p>
	 * ����һ�����������ַ��������Ե�ֵ.
	 * <p>
	 * ��������:(2002-10-10)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public Object getAttributeValue(String attributeName) {

		if (attributeName.equals("id")) {
			return id;
		} else if (attributeName.equals("pk_corp")) {
			return pkCorp;
		} else if (attributeName.equals("model_code")) {
			return modelCode;
		} else if (attributeName.equals("model_name")) {
			return modelName;
		} else if (attributeName.equals("node_code")) {
			return nodeCode;
		} else if (attributeName.equals("description")) {
			return description;
		} else if (attributeName.equals("resid")) {
			return resid;
		} else if (attributeName.equals("metaclass")) {
			return metaclass;
		} else if (attributeName.equals("parentid")) {
			return parentid;
		}
		return null;
	}

	/**
	 * ����description��Getter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ������ֵ�������ʾ����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ����.
	 */
	public String getEntityName() {

		return "QueryTemplet";
	}

	/**
	 * �˴����뷽��˵��. ��������:(2003-10-27 13:17:50)
	 * 
	 * @return java.lang.String
	 */
	public ConditionVO[] getFixConditions() {
		if (fixConditionVOs == null && fixConditionString != null)
			fixConditionVOs = ConditionVO.parseString(fixConditionString);
		return fixConditionVOs;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2003-10-27 13:17:50)
	 * 
	 * @return java.lang.String
	 */
	public String getFixConditionString() {
		if (fixConditionString == null && fixConditionVOs != null)
			fixConditionString = ConditionVO.toStringInfo(fixConditionVOs);
		return fixConditionString;
	}

	/**
	 * ����id��Getter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * ����modelCode��Getter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * ����modelName��Getter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * �������ܽڵ���룬���ڼ�¼��ģ����������һ���ڵ㣬�Ա����ڡ����ܽڵ�Ĭ��ģ����䡿�ڵ�ʹ��
	 * �ڷ���ģ��ʱ��ģ�������Ĺ��ܽڵ��������˸�ģ�����Ǹ��ڵ�ı�ѡģ���������ʾ
	 * ������֮�󣬷�����м�¼���Ǹ��ĸ����ܽڵ����(funcode)�����˸�ģ�壬��nodeCode���Ծ�û����
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * ����pkCorp��Getter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getPkCorp() {
		return pkCorp;
	}

	/**
	 * ���ض����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return id;
	}

	/**
	 * <p>
	 * �Բ���name���͵���������ֵ.
	 * <p>
	 * ��������:(2002-10-10)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public void setAttributeValue(String name, Object value) {

		try {
			if (name.equals("id")) {
				id = (String) value;
			} else if (name.equals("pk_corp")) {
				pkCorp = (String) value;
			} else if (name.equals("model_code")) {
				modelCode = (String) value;
			} else if (name.equals("model_name")) {
				modelName = (String) value;
			} else if (name.equals("node_code")) {
				nodeCode = (String) value;
			} else if (name.equals("description")) {
				description = (String) value;
			} else if (name.equals("resid")) {
				resid = (String) value;
			} else if (name.equals("metaclass")) {
				metaclass = (String) value;
			} else if (name.equals("parentid")) {
				parentid = (String) value;
			}
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"In QueryTempletVO.setAttributeValue()."
							+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
									.getStrByID("_Template",
											"UPP_Template-000517")/*
																	 * @res
																	 * "��ֵʱ����ת������!"
																	 */
							+ "name=" + name + " value=" + value + ".");
		}
	}

	/**
	 * ����description��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newDescribe
	 *            String
	 */
	public void setDescription(String newDescribe) {

		description = newDescribe;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2003-10-27 13:17:50)
	 * 
	 * @param newFixConditions
	 *            java.lang.String
	 */
	public void setFixConditions(ConditionVO[] newFixConditions) {
		fixConditionVOs = newFixConditions;
		fixConditionString = null;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2003-10-27 13:17:50)
	 * 
	 * @param newFixConditions
	 *            java.lang.String
	 */
	public void setFixConditionString(java.lang.String newFixConditionString) {
		fixConditionString = newFixConditionString;
		fixConditionVOs = null;
	}

	/**
	 * ����id��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newId
	 *            String
	 */
	public void setId(String newId) {

		id = newId;
	}

	/**
	 * ����modelCode��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newModelCode
	 *            String
	 */
	public void setModelCode(String newModelCode) {

		modelCode = newModelCode;
	}

	/**
	 * ����modelName��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newModelName
	 *            String
	 */
	public void setModelName(String newModelName) {

		modelName = newModelName;
	}

	/**
	 * ����nodeCode��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newNodeCode
	 *            String
	 */
	public void setNodeCode(String newNodeCode) {

		nodeCode = newNodeCode;
	}

	/**
	 * ����pkCorp��setter����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param newPkCorp
	 *            String
	 */
	public void setPkCorp(String newPkCorp) {

		pkCorp = newPkCorp;
	}

	/**
	 * ���ö����ʶ,����Ψһ��λ����.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @param id
	 *            String
	 */
	public void setPrimaryKey(String newId) {

		id = newId;
	}

	/**
	 * ��֤���������֮��������߼���ȷ��.
	 * 
	 * ��������:(2001-6-14)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ��,�׳� ValidationException,�Դ�����н���.
	 */
	public void validate() throws ValidationException {

		List<String> errFields = new ArrayList<String>(); // errFields record those null
												// fields that cannot be null.
		// ����Ƿ�Ϊ�������յ��ֶθ��˿�ֵ,�������Ҫ�޸��������ʾ��Ϣ:
		if (id == null) {
			errFields.add("id");
		}
		if (pkCorp == null) {
			errFields.add("pkCorp");
		}
		if (modelCode == null) {
			errFields.add("modelCode");
		}
		if (modelName == null) {
			errFields.add("modelName");
		}
		if (nodeCode == null) {
			errFields.add("nodeCode");
		}
		// construct the exception message:
		StringBuffer message = new StringBuffer();
		message.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
				"common", "MC11")/* @res "�����ֶβ���Ϊ��" */
				+ ":");
		if (errFields.size() > 0) {
			String[] temp = (String[]) errFields.toArray(new String[0]);
			message.append(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				message.append(",");
				message.append(temp[i]);
			}
			// throw the exception:
			throw new NullFieldException(message.toString());
		}
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String toString() {
		return getModelCode() + " " + getModelName();
	}

	public String getMetaclass() {
		return metaclass;
	}

	public void setMetaclass(String metaclass) {
		this.metaclass = metaclass;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	} 

    /**
     * �̶�������ѯ���������ɵ�XML�ַ���ѹ������ֽ�����
     */
    public byte[] getFixQueryTree() {
		return fixQueryTree;
	}

	public void setFixQueryTree(byte[] fixQueryTree) {
		this.fixQueryTree = fixQueryTree;
	}
	
	/**
	 * ���ع̶�������ѯ��ʹ��ģʽ
	 * 
	 * @see nc.vo.pub.query.QueryTempletVO.QUERY_TYPE
	 * @see nc.vo.pub.query.QueryTempletVO.SETTING_TYPE
	 */
	public int getFixQueryTreeType() {
		return fixQueryTreeType;
	}

	/**
	 * ���ù̶�������ѯ��ʹ��ģʽ
	 * <p>
	 * <strong>�����ϸ÷���ֻ���ɲ�ѯģ���ڲ�ʹ��</strong>
	 * @see nc.vo.pub.query.QueryTempletVO.QUERY_TYPE
	 * @see nc.vo.pub.query.QueryTempletVO.SETTING_TYPE
	 */
	public void setFixQueryTreeType(int fixQueryTreeType) {
		this.fixQueryTreeType = fixQueryTreeType;
	}

	/**
	 * ���ع̶�������ѯ������
	 */
	public QueryTree getFixQueryTreeObject() {
		return getFixQueryTreeType() == QUERY_TYPE ? getFixQueryTree4Query()
				: getFixQueryTree4Setting();
	}
	
	/**
	 * ���ع���ѯ�Ի����ѯʱʹ�õĹ̶�������<br>
	 * ������Ľڵ�������Ч�����ǲ��ɱ༭��
	 */
	private QueryTree getFixQueryTree4Query() {
		QueryTree tree = getFixQueryTree4Setting();
		if(tree == null) return null;
		QueryTreeNode root = (QueryTreeNode) tree.getRoot();
		Enumeration<?> e = root.preorderEnumeration();
		while (e.hasMoreElements()) {
			QueryTreeNode node = (QueryTreeNode) e.nextElement();
			if (node instanceof FilterNode) {
				IFilter filter = ((IFilter) node.getUserObject());
				((FilterMeta) filter.getFilterMeta()).setFixCondition(true);
			}
		}
		return tree;
	}
	
	/**
	 * ���ع���ѯģ�����ù���ʹ�õĹ̶�������<br>
	 * ������Ľڵ㲢û������Ч�����ǿɱ༭��
	 */
	public QueryTree getFixQueryTree4Setting() {
		return QueryTreeTranslator.translate(getFixQueryTree());
	}
	
	/**
	 * �Ƿ���ϵͳĬ��ģ��
	 */
	public boolean isSystem() {
		return "@@@@".equals(this.getPkCorp());
	}
	
	/**
	 * �Ƿ���Ԫ�������ɵ�
	 */
	public boolean isMetadata() {
		return getMetaclass() != null && getMetaclass().trim().length() != 0;
	}
}