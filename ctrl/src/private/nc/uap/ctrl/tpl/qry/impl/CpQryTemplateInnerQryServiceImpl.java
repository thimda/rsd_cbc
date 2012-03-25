package nc.uap.ctrl.tpl.qry.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService;
import nc.uap.ctrl.tpl.qry.base.CpQueryConditionVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateTotalVO;
import nc.uap.ctrl.tpl.qry.base.CpQueryTemplateVO;
import nc.uap.ctrl.tpl.qry.base.QuerySchemeVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;

public class CpQryTemplateInnerQryServiceImpl implements
		ICpQryTemplateInnerQryService {

	
	@Override
	public CpQueryConditionVO[] getQueryConditions(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			List<CpQueryConditionVO> list = (List<CpQueryConditionVO>) dao.retrieveByClause(CpQueryConditionVO.class, "pk_query_template='" + pk_template + "'");
			return list.toArray(new CpQueryConditionVO[0]);
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException("����ģ��:" + pk_template + "������ʧ��");
		}
	}

	/* (non-Javadoc)
	 * @see nc.uap.ctrl.tpl.qry.ICpQryTemplateInnerQryService#getQueryTotalVO(java.lang.String)
	 */
	@Override
	public CpQueryTemplateTotalVO getQueryTotalVO(String pk_template) throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			CpQueryTemplateVO template = (CpQueryTemplateVO) dao.retrieveByPK(CpQueryTemplateVO.class, pk_template);
			if(template == null)
				return null;
			CpQueryConditionVO[] conditions = getQueryConditions(pk_template);
			CpQueryTemplateTotalVO totalVO = new CpQueryTemplateTotalVO();
			totalVO.setParentVO(template);
			totalVO.setChildrenVO(conditions);
			return totalVO;
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException("��ѯģ�����ݳ���PK=" + pk_template);
		}
	}

	@Override
	public String getQueryTemplatePkByNode(String nodeCode)
			throws TplBusinessException {
		try {
			BaseDAO dao = new BaseDAO();
			Object[] objs = (Object[]) dao.executeQuery("select pk_query_template from cp_query_template where nodecode='" + nodeCode + "'", new ArrayProcessor());
			if(objs == null || objs.length == 0)
				return null;
			return (String) objs[0];
		} 
		catch (DAOException e) {
			LfwLogger.error(e);
			throw new TplBusinessException(e.getMessage());
		}
	}

	@Override
	public QuerySchemeVO[] getQuerySchemeVOsBy(String pk_org, String pk_template, String userid) throws BusinessException {
		if (isEmpty(pk_template)) 
			return null;
		String where = constructWhereSQL(pk_org, pk_template, userid);
		
		BaseDAO dao = new BaseDAO();
		Collection<QuerySchemeVO> results = dao.retrieveByClause(QuerySchemeVO.class, where);
		if(isEmpty(results)) 
			return null;
		filter(results);
		// ��ΪԤ�÷���û��pk_org��cuserid����������������һ���Ա��ض��û�ʹ��
		setPk_orgAndUserid4PreparedScheme(results, pk_org, userid);
		return results.toArray(new QuerySchemeVO[0]);
	}
	
	private void setPk_orgAndUserid4PreparedScheme(
			Collection<QuerySchemeVO> qsvos, String pk_org, String userid) {
		for (QuerySchemeVO qsvo : qsvos) {
			if (qsvo.isPrepared()) {
				qsvo.setPk_org(pk_org);
				qsvo.setCuserid(userid);
			}
		}
	}
	
	/**
	 * ���˲�ѯ����VO
	 * 1���Ѿ���������Ԥ�÷���
	 * 2���Ѿ�ɾ���ķ���
	 * �ڶ���֮���Բ���SQL����������Ϊ���������˽�˷����Ѿ����û�ɾ����
	 * ������Ӧ��Ԥ�÷��������ټ��أ��������������˵�˳���ǹ̶��ġ�
	 */
	private void filter(Collection<QuerySchemeVO> qsvos) {
		filterPreparedSchemesAlreadyDerived(qsvos);
		filterDeletedSchemes(qsvos);
	}

	private void filterPreparedSchemesAlreadyDerived(Collection<QuerySchemeVO> qsvos) {
		Iterator<QuerySchemeVO> it = qsvos.iterator();
		while (it.hasNext()) {
			QuerySchemeVO qsvo = it.next();
			if(qsvo.isPrepared() && isAlreadyDerived(qsvo,qsvos)) {
				it.remove();
			}
		}
	}

	private boolean isAlreadyDerived(QuerySchemeVO preparedScheme,Collection<QuerySchemeVO> qsvos) {
		for (QuerySchemeVO qsvo : qsvos) {
			if (preparedScheme.getPk_queryscheme().equals(qsvo.getParentid())) {
				return true;
			}
		}
		return false;
	}

	private void filterDeletedSchemes(Collection<QuerySchemeVO> qsvos) {
		Iterator<QuerySchemeVO> it = qsvos.iterator();
		while (it.hasNext()) {
			QuerySchemeVO qsvo = it.next();
			if (qsvo.isDeleted()) {
				it.remove();
			}
		}
	}
	
	private static <T> boolean isEmpty(Collection<T> c) {
		return c == null || c.size() == 0;
	}
	
	private String constructWhereSQL(String pk_org,
			String pk_template, String userid) {
		// pk_template = 'a' AND funcode = 'b' AND (pk_org ='~' or pk_org = 'c') AND (cuserid ='~' or cuserid = 'd')
		StringBuilder where = new StringBuilder();
		where.append("pk_template ='")
		.append(pk_template);
		if (isEmpty(pk_org)) {
			where.append(" AND pk_org ='~' ");
		} else {
			where.append(" AND (pk_org ='~' or pk_org = '").append(pk_org).append("') ");
		}
		if (isEmpty(userid)) {
			where.append(" AND cuserid ='~' ");
		} else {
			where.append(" AND (cuserid ='~' or cuserid = '").append(userid).append("') ");
		}
		where.append(" order by sequenc desc");
		
		return where.toString();
	}
	
	private static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

}
