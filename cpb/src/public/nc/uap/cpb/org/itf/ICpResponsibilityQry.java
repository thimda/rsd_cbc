package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpResponsibilityVO;

public interface ICpResponsibilityQry {
	public CpResponsibilityVO getResponsibilityVoByPk(String pk_responsibility) throws CpbBusinessException;
	public CpResponsibilityVO[] getResponsibilityVos(String pk_group,String code) throws CpbBusinessException;
	public CpResponsibilityVO[] getResponsibilityVos(String where) throws CpbBusinessException;


}
