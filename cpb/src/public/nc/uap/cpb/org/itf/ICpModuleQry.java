package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpModuleVO;

public interface ICpModuleQry { 
	public CpModuleVO[] getAllModules() throws CpbBusinessException;
	public CpModuleVO getModuleById(String id) throws CpbBusinessException;
}
