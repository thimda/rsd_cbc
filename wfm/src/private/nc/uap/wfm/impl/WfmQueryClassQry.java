package nc.uap.wfm.impl;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmQueryClassQry;
import nc.uap.wfm.vo.WfmQueryclassVO;
public class WfmQueryClassQry implements IWfmQueryClassQry {
	@Override
	public WfmQueryclassVO getQueryClassByPk(String queryclassPk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		try {
			return (WfmQueryclassVO) baseDAO.retrieveByPK(WfmQueryclassVO.class, queryclassPk);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public WfmQueryclassVO[] getExistsTemplateByPk(String userPk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		StringBuffer buffer = new StringBuffer();
		buffer
		.append("select a.* from wfm_queryclass a where exists ( select 1 from wfm_querytemplate b inner join wfm_templaterole c on b.pk_querytemplate=c.pk_template inner join cp_roleuser d on c.pk_role=d.pk_role and d.pk_user=? where a.pk_queryclass=b.pk_queryclass) order by a.ext0,a.ext1");
		try {
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(userPk);
			List<WfmQueryclassVO> list = (List<WfmQueryclassVO>) baseDAO.executeQuery(buffer.toString(), parameter, new BeanListProcessor(WfmQueryclassVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmQueryclassVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
	}
}
