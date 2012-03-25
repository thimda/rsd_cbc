package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.vo.org.OrgVO;


public interface ICpOrgQry {
	/**
	 * 查询当前集团下的组织参照
	 * @return
	 * @throws CpbBusinessException
	 */
	public OrgVO[] getReferenceOrgs() throws CpbBusinessException;
	
	
	}