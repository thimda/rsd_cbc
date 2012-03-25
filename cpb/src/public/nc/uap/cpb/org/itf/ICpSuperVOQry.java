package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.vo.pub.SuperVO;

public interface ICpSuperVOQry {
	public SuperVO[] getSuperVOs(SuperVO vo,String where) throws CpbBusinessException;
	
	
}
