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
 * 查询模板VO. 创建日期:(2001-6-14)
 * 
 * @author:刘东成
 * 
 * 修改日期:2001-07-05 更新内容:修改了表结构,添加注释 修改人:刘丽
 */
public class QueryTempletVO extends CircularlyAccessibleValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 这两个常量理论上应该只供查询模板内部逻辑使用
	 */
	
	/** 查询模板查询模式 */
	public static final int QUERY_TYPE = 0;
	/** 查询模板设置模式 */
	public static final int SETTING_TYPE = 1;
	
	// 固定条件查询树使用模式
	private int fixQueryTreeType = QUERY_TYPE;
	
	private String id;

	private String pkCorp;

	private String modelCode;

	private String modelName;

	// 所属功能节点编码，用于记录本模板隶属于哪一个节点，以便于在【功能节点默认模板分配】节点使用
	// 在分配模板时，模板所属的功能节点编码决定了该模板在那个节点的备选模板参照中显示
	// 而分配之后，分配表中记录的是给哪个功能节点编码(funcode)分配了该模板，而nodeCode属性就没用了
	private String nodeCode;

	private String description;

	private java.lang.String fixConditionString;

	private ConditionVO[] fixConditionVOs;

	private String resid;

	private String metaclass;
	
	// 父查询模板(系统默认模板)ID
	private String parentid;
	
	/**
	 * 固定条件查询树对象生成的XML字符串压缩后的字节数组<br>
	 * (没有直接使用QueryTree对象是为了减小网络流量)<br>
	 * 被存储到数据库中的固定条件查询树实际上并没有锁定效果<br>
	 * 只是为了能够供前台设置工具使用时能够简便恢复对象<br>
	 * 如果本对象供查询对话框使用时，必须手工将QueryTree中各个节点的ifFixCondition属性设为true<br>
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
	 * 使用主键字段进行初始化的构造子.
	 * 
	 * 创建日期:(2001-6-14)
	 */
	public QueryTempletVO() {

	}

	/**
	 * 使用主键进行初始化的构造子.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param ??fieldNameForMethod??
	 *            主键值
	 */
	public QueryTempletVO(String newId) {

		// 为主键字段赋值:
		id = newId;
	}

	/**
	 * 根类Object的方法,克隆这个VO对象.
	 * 
	 * 创建日期:(2001-6-14)
	 */
	public Object clone() {

		// 复制基类内容并创建新的VO对象:
		Object o = null;
		try {
			o = super.clone();
		} catch (Exception e) {
		}
		QueryTempletVO queryTemplet = (QueryTempletVO) o;

		// 你在下面复制本VO对象的所有属性:

		return queryTemplet;
	}

	/**
	 * <p>
	 * 需要在一个循环中访问的属性的名称数组.
	 * <p>
	 * 创建日期:(??Date??)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getAttributeNames() {

		return new String[] { "pk_corp", "model_code", "model_name",
				"node_code", "description", "resid", "ts", "dr", "metaclass" };
	}

	/**
	 * <p>
	 * 根据一个属性名称字符串该属性的值.
	 * <p>
	 * 创建日期:(2002-10-10)
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
	 * 属性description的Getter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 返回数值对象的显示名称.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return java.lang.String 返回数值对象的显示名称.
	 */
	public String getEntityName() {

		return "QueryTemplet";
	}

	/**
	 * 此处插入方法说明. 创建日期:(2003-10-27 13:17:50)
	 * 
	 * @return java.lang.String
	 */
	public ConditionVO[] getFixConditions() {
		if (fixConditionVOs == null && fixConditionString != null)
			fixConditionVOs = ConditionVO.parseString(fixConditionString);
		return fixConditionVOs;
	}

	/**
	 * 此处插入方法说明. 创建日期:(2003-10-27 13:17:50)
	 * 
	 * @return java.lang.String
	 */
	public String getFixConditionString() {
		if (fixConditionString == null && fixConditionVOs != null)
			fixConditionString = ConditionVO.toStringInfo(fixConditionVOs);
		return fixConditionString;
	}

	/**
	 * 属性id的Getter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 属性modelCode的Getter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * 属性modelName的Getter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * 所属功能节点编码，用于记录本模板隶属于哪一个节点，以便于在【功能节点默认模板分配】节点使用
	 * 在分配模板时，模板所属的功能节点编码决定了该模板在那个节点的备选模板参照中显示
	 * 而分配之后，分配表中记录的是给哪个功能节点编码(funcode)分配了该模板，而nodeCode属性就没用了
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * 属性pkCorp的Getter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getPkCorp() {
		return pkCorp;
	}

	/**
	 * 返回对象标识,用来唯一定位对象.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return id;
	}

	/**
	 * <p>
	 * 对参数name对型的属性设置值.
	 * <p>
	 * 创建日期:(2002-10-10)
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
																	 * "赋值时类型转换错误!"
																	 */
							+ "name=" + name + " value=" + value + ".");
		}
	}

	/**
	 * 属性description的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newDescribe
	 *            String
	 */
	public void setDescription(String newDescribe) {

		description = newDescribe;
	}

	/**
	 * 此处插入方法说明. 创建日期:(2003-10-27 13:17:50)
	 * 
	 * @param newFixConditions
	 *            java.lang.String
	 */
	public void setFixConditions(ConditionVO[] newFixConditions) {
		fixConditionVOs = newFixConditions;
		fixConditionString = null;
	}

	/**
	 * 此处插入方法说明. 创建日期:(2003-10-27 13:17:50)
	 * 
	 * @param newFixConditions
	 *            java.lang.String
	 */
	public void setFixConditionString(java.lang.String newFixConditionString) {
		fixConditionString = newFixConditionString;
		fixConditionVOs = null;
	}

	/**
	 * 属性id的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newId
	 *            String
	 */
	public void setId(String newId) {

		id = newId;
	}

	/**
	 * 属性modelCode的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newModelCode
	 *            String
	 */
	public void setModelCode(String newModelCode) {

		modelCode = newModelCode;
	}

	/**
	 * 属性modelName的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newModelName
	 *            String
	 */
	public void setModelName(String newModelName) {

		modelName = newModelName;
	}

	/**
	 * 属性nodeCode的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newNodeCode
	 *            String
	 */
	public void setNodeCode(String newNodeCode) {

		nodeCode = newNodeCode;
	}

	/**
	 * 属性pkCorp的setter方法.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param newPkCorp
	 *            String
	 */
	public void setPkCorp(String newPkCorp) {

		pkCorp = newPkCorp;
	}

	/**
	 * 设置对象标识,用来唯一定位对象.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @param id
	 *            String
	 */
	public void setPrimaryKey(String newId) {

		id = newId;
	}

	/**
	 * 验证对象各属性之间的数据逻辑正确性.
	 * 
	 * 创建日期:(2001-6-14)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                如果验证失败,抛出 ValidationException,对错误进行解释.
	 */
	public void validate() throws ValidationException {

		List<String> errFields = new ArrayList<String>(); // errFields record those null
												// fields that cannot be null.
		// 检查是否为不允许空的字段赋了空值,你可能需要修改下面的提示信息:
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
				"common", "MC11")/* @res "下列字段不能为空" */
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
     * 固定条件查询树对象生成的XML字符串压缩后的字节数组
     */
    public byte[] getFixQueryTree() {
		return fixQueryTree;
	}

	public void setFixQueryTree(byte[] fixQueryTree) {
		this.fixQueryTree = fixQueryTree;
	}
	
	/**
	 * 返回固定条件查询树使用模式
	 * 
	 * @see nc.vo.pub.query.QueryTempletVO.QUERY_TYPE
	 * @see nc.vo.pub.query.QueryTempletVO.SETTING_TYPE
	 */
	public int getFixQueryTreeType() {
		return fixQueryTreeType;
	}

	/**
	 * 设置固定条件查询树使用模式
	 * <p>
	 * <strong>理论上该方法只能由查询模板内部使用</strong>
	 * @see nc.vo.pub.query.QueryTempletVO.QUERY_TYPE
	 * @see nc.vo.pub.query.QueryTempletVO.SETTING_TYPE
	 */
	public void setFixQueryTreeType(int fixQueryTreeType) {
		this.fixQueryTreeType = fixQueryTreeType;
	}

	/**
	 * 返回固定条件查询树对象
	 */
	public QueryTree getFixQueryTreeObject() {
		return getFixQueryTreeType() == QUERY_TYPE ? getFixQueryTree4Query()
				: getFixQueryTree4Setting();
	}
	
	/**
	 * 返回供查询对话框查询时使用的固定条件树<br>
	 * 这棵树的节点有锁定效果，是不可编辑的
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
	 * 返回供查询模板设置工具使用的固定条件树<br>
	 * 这棵树的节点并没有锁定效果，是可编辑的
	 */
	public QueryTree getFixQueryTree4Setting() {
		return QueryTreeTranslator.translate(getFixQueryTree());
	}
	
	/**
	 * 是否是系统默认模板
	 */
	public boolean isSystem() {
		return "@@@@".equals(this.getPkCorp());
	}
	
	/**
	 * 是否是元数据生成的
	 */
	public boolean isMetadata() {
		return getMetaclass() != null && getMetaclass().trim().length() != 0;
	}
}