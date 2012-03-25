package nc.uap.ctrl.tpl.qry.meta;

import nc.vo.pub.AggregatedValueObject;

/**
 * 查询模板的主子VO. 创建日期:(2002-5-22)
 * 
 * @author:刘丽
 */
public class QueryTempletTotalVO extends AggregatedValueObject {
	private static final long serialVersionUID = -3113235620198393485L;

	private QueryTempletVO templet = null;
	private QueryConditionVO[] conditions = null;

	/**
	 * 此处插入方法说明. 创建日期:(2003-02-13 9:46:31)
	 */
	public QueryTempletTotalVO() {
	}

	/**
	 * 此处插入方法说明. 创建日期:(2002-05-22 10:43:47)
	 * 
	 * @param templetVO
	 *            nc.vo.pub.query.QueryTempletVO
	 * @param conditionVOs
	 *            nc.vo.pub.query.QueryConditionVO[]
	 */
	public QueryTempletTotalVO(QueryTempletVO templetVO,
			QueryConditionVO[] conditionVOs) {
		super();
		setTemplet(templetVO);
		setConditionVOs(conditionVOs);
	}

	/**
	 * 此处插入方法说明. 创建日期:(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public nc.vo.pub.CircularlyAccessibleValueObject[] getChildrenVO() {
		return conditions;
	}

	/**
	 * 返回子表的VO数组. 创建日期:(2002-05-22 10:26:34)
	 * 
	 * @return nc.vo.pub.query.QueryConditionVO[]
	 */
	public QueryConditionVO[] getConditionVOs() {
		return conditions;
	}

	/**
	 * 返回数值对象的显示名称.
	 * 
	 * 创建日期:(2001-2-15 14:18:08)
	 * 
	 * @return java.lang.String 返回数值对象的显示名称.
	 */
	public java.lang.String getEntityName() {
		return "QueryTempletTotalVO";
	}

	/**
	 * 此处插入方法说明. 创建日期:(01-3-20 17:32:28)
	 * 
	 * @return nc.vo.pub.ValueObject
	 */
	public nc.vo.pub.CircularlyAccessibleValueObject getParentVO() {
		return templet;
	}

	/**
	 * 返回主表的VO. 创建日期:(2002-05-22 10:26:34)
	 * 
	 * @return nc.vo.pub.query.QueryTempletVO
	 */
	public QueryTempletVO getTempletVO() {
		return templet;
	}

	/**
	 * 此处插入方法说明. 创建日期:(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public void setChildrenVO(
			nc.vo.pub.CircularlyAccessibleValueObject[] children) {

		conditions = (QueryConditionVO[]) children;
	}

	/**
	 * 此处插入方法说明. 创建日期:(2002-05-22 10:42:45)
	 * 
	 * @param conditionVOs
	 *            nc.vo.pub.query.QueryConditionVO[]
	 */
	public void setConditionVOs(QueryConditionVO[] conditionVOs) {
		conditions = conditionVOs;
	}

	/**
	 * 此处插入方法说明. 创建日期:(01-3-20 17:32:28)
	 * 
	 * @return nc.vo.pub.ValueObject
	 */
	public void setParentVO(nc.vo.pub.CircularlyAccessibleValueObject parent) {
		templet = (QueryTempletVO) parent;
	}

	/**
	 * 此处插入方法说明. 创建日期:(2002-05-22 10:42:11)
	 * 
	 * @param templetVO
	 *            nc.vo.pub.query.QueryTempletVO
	 */
	public void setTemplet(QueryTempletVO templetVO) {
		templet = templetVO;
	}

	/**
	 * 验证对象各属性之间的数据逻辑正确性.
	 * 
	 * 创建日期:(2001-2-15 11:47:35)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                如果验证失败,抛出 ValidationException,对错误进行解释.
	 */
	public void validate() throws nc.vo.pub.ValidationException {
	}

	public Object clone() {
		QueryTempletTotalVO vo = new QueryTempletTotalVO();
		QueryTempletVO qtvo = this.getTempletVO();
		if (qtvo != null) {
			vo.setTemplet((QueryTempletVO) qtvo.clone());
		}
		QueryConditionVO[] qcvos = this.getConditionVOs();
		if (qcvos != null) {
			QueryConditionVO[] vos = new QueryConditionVO[qcvos.length];
			for (int i = 0; i < qcvos.length; i++) {
				vos[i] = (QueryConditionVO) qcvos[i].clone();
			}
			vo.setConditionVOs(vos);
		}
		return vo;
	}

	public String toString() {
		return getTempletVO().getModelCode() + " "
				+ getTempletVO().getModelName();
	}
}