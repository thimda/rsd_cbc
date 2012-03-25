package nc.uap.wfm.impl;
import java.util.List;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmMyQueryQry;
import nc.uap.wfm.vo.WfmQuerytemplateVO;
public class WfmMyQueryQry implements IWfmMyQueryQry {
	@SuppressWarnings("unchecked")
	public WfmQuerytemplateVO[] getQuerytemplateVOsByUserPk(String userPk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		try {
				String sql = "select a.* from pt_querytemplate a inner join pt_templaterole b on a.pk_querytemplate=b.pk_template inner join pt_roleuser c on b.pk_role=c.pk_role and c.pk_user=? order by a.ext0,a.ext1";
				SQLParameter parameter = new SQLParameter();
				parameter.addParam(userPk);
				List<WfmQuerytemplateVO> list = (List<WfmQuerytemplateVO>) baseDAO.executeQuery(sql, parameter, new BeanListProcessor(WfmQuerytemplateVO.class));
			if(list==null||list.size()==0){
				return null;
			}
			return list.toArray(new WfmQuerytemplateVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new WfmServiceException(e);
		}
	}
	public WfmQuerytemplateVO getQuerytemplateVOByPk(String templatePk) throws WfmServiceException {
		PtBaseDAO baseDAO = new PtBaseDAO();
		try {
			return (WfmQuerytemplateVO) baseDAO.retrieveByPK(WfmQuerytemplateVO.class, templatePk);
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new WfmServiceException(e);
		}
	}
}
