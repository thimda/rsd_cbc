package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.vo.org.OrgVO;


public interface ICpOrgQry {
	/**
	 * ��ѯ��ǰ�����µ���֯����
	 * @return
	 * @throws CpbBusinessException
	 */
	public OrgVO[] getReferenceOrgs() throws CpbBusinessException;
	
	
	}