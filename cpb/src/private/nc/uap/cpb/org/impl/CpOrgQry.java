package nc.uap.cpb.org.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpOrgQry;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.org.OrgVO;
import nc.vo.pub.lang.UFBoolean;

public class CpOrgQry implements ICpOrgQry {

	@Override
	public OrgVO[] getReferenceOrgs() throws CpbBusinessException {
		String pk_group = LfwRuntimeEnvironment.getLfwSessionBean().getPk_unit();
		String sql = "select * from org_orgs where pk_group = '" + pk_group + "'  and (isbusinessunit ='Y' or orgtype1 = 'Y') and (enablestate = 2)" ;
		
		PtBaseDAO baseDAO = new PtBaseDAO();
		List<OrgVO> list = null;
		try {
			list = (List<OrgVO>) baseDAO.executeQuery(sql, new BeanListProcessor(OrgVO.class));				
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new CpbBusinessException(e);
		}
		OrgVO[] orgvos = (list == null || list.size() == 0) ? null : list.toArray(new OrgVO[list.size()]);		
		if (orgvos == null || orgvos.length == 0)
			return new OrgVO[] {};		
		for (int i = 0; i < orgvos.length; i++) {
			//设置根业务单元的上级为其所属集团
			if (StringUtil.isEmpty(orgvos[i].getPk_fatherorg()) && UFBoolean.TRUE.equals(orgvos[i].getIsbusinessunit())) {
				orgvos[i].setPk_fatherorg(orgvos[i].getPk_group());
				break;
			}
		}
		return orgvos;
	}

}
