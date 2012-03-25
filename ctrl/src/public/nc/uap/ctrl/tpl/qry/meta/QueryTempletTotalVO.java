package nc.uap.ctrl.tpl.qry.meta;

import nc.vo.pub.AggregatedValueObject;

/**
 * ��ѯģ�������VO. ��������:(2002-5-22)
 * 
 * @author:����
 */
public class QueryTempletTotalVO extends AggregatedValueObject {
	private static final long serialVersionUID = -3113235620198393485L;

	private QueryTempletVO templet = null;
	private QueryConditionVO[] conditions = null;

	/**
	 * �˴����뷽��˵��. ��������:(2003-02-13 9:46:31)
	 */
	public QueryTempletTotalVO() {
	}

	/**
	 * �˴����뷽��˵��. ��������:(2002-05-22 10:43:47)
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
	 * �˴����뷽��˵��. ��������:(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public nc.vo.pub.CircularlyAccessibleValueObject[] getChildrenVO() {
		return conditions;
	}

	/**
	 * �����ӱ��VO����. ��������:(2002-05-22 10:26:34)
	 * 
	 * @return nc.vo.pub.query.QueryConditionVO[]
	 */
	public QueryConditionVO[] getConditionVOs() {
		return conditions;
	}

	/**
	 * ������ֵ�������ʾ����.
	 * 
	 * ��������:(2001-2-15 14:18:08)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ����.
	 */
	public java.lang.String getEntityName() {
		return "QueryTempletTotalVO";
	}

	/**
	 * �˴����뷽��˵��. ��������:(01-3-20 17:32:28)
	 * 
	 * @return nc.vo.pub.ValueObject
	 */
	public nc.vo.pub.CircularlyAccessibleValueObject getParentVO() {
		return templet;
	}

	/**
	 * ���������VO. ��������:(2002-05-22 10:26:34)
	 * 
	 * @return nc.vo.pub.query.QueryTempletVO
	 */
	public QueryTempletVO getTempletVO() {
		return templet;
	}

	/**
	 * �˴����뷽��˵��. ��������:(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public void setChildrenVO(
			nc.vo.pub.CircularlyAccessibleValueObject[] children) {

		conditions = (QueryConditionVO[]) children;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2002-05-22 10:42:45)
	 * 
	 * @param conditionVOs
	 *            nc.vo.pub.query.QueryConditionVO[]
	 */
	public void setConditionVOs(QueryConditionVO[] conditionVOs) {
		conditions = conditionVOs;
	}

	/**
	 * �˴����뷽��˵��. ��������:(01-3-20 17:32:28)
	 * 
	 * @return nc.vo.pub.ValueObject
	 */
	public void setParentVO(nc.vo.pub.CircularlyAccessibleValueObject parent) {
		templet = (QueryTempletVO) parent;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2002-05-22 10:42:11)
	 * 
	 * @param templetVO
	 *            nc.vo.pub.query.QueryTempletVO
	 */
	public void setTemplet(QueryTempletVO templetVO) {
		templet = templetVO;
	}

	/**
	 * ��֤���������֮��������߼���ȷ��.
	 * 
	 * ��������:(2001-2-15 11:47:35)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ��,�׳� ValidationException,�Դ�����н���.
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