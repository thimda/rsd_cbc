package nc.uap.ctrl.tpl.impl;

import java.util.List;

import nc.bs.logging.Logger;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.ctrl.tpl.exp.TplBusinessException;
import nc.uap.ctrl.tpl.qry.CpTemplateParaVO;
import nc.uap.ctrl.tpl.systemplate.ICpSystemplateQryService;
import nc.vo.pub.pftemplate.SystemplateVO;
import nc.vo.pub.template.TemplateRuntimeException;

public class CpSystemplateQryServiceImpl implements ICpSystemplateQryService {

	@Override
	public String getTemplateId(CpTemplateParaVO paramVO) throws TplBusinessException {
		String templateId = doGetTemplateId(paramVO);
		if (templateId == null)
			throw new TplBusinessException("ģ�����ñ���δע��ģ������" + ":" + paramVO.getTempstyle() + ","
					+ "��֯" + ":" + paramVO.getPk_org()
					+ "������"
					+ ":"
					+ paramVO.getOperator()
					+ "���ܽڵ�"
					+ ":" + paramVO.getAppid());
		return templateId;
	}

	private String doGetTemplateId(CpTemplateParaVO paramVO) {
		PersistenceManager persist = null;
		try {
			persist = PersistenceManager.getInstance();
			JdbcSession jdbc = persist.getJdbcSession();

			String baseSql = "select templateid, operator, pk_org, nodekey from cp_systemplate "
					+ "where appid = ? and tempstyle = ? ";

			/* STEP1:��ȡ�û��������õ�ģ���ȱʡ��ģ��(����������������) */
			SQLParameter userParam = new SQLParameter();
			StringBuffer userSqlBuf = new StringBuffer();
			userSqlBuf.append(baseSql);

			String nodeKey = paramVO.getNodeKey();
			if (nodeKey != null)
				userSqlBuf.append("and nodekey = ? ");
			else{
				userSqlBuf.append("and isnull(nodekey,'~')='~' ");
			}

			userSqlBuf.append(" and operator=?)");

			userParam.addParam(paramVO.getAppid());
			userParam.addParam(paramVO.getTempstyle());

			if (nodeKey != null)
				userParam.addParam(nodeKey);
			userParam.addParam(paramVO.getOperator());
			
			List<SystemplateVO> userTemplateList = (List<SystemplateVO>) jdbc.executeQuery(userSqlBuf
					.toString(), userParam, new BeanListProcessor(SystemplateVO.class));
			if(userTemplateList.size() > 0)
				return userTemplateList.get(0).getTemplateid();
			return null;
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new TemplateRuntimeException(e.getSQLState() + e.getMessage());
		} finally {
			if (persist != null)
				persist.release();
		}
	}

}
