package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpRespFuncVO;
import nc.uap.cpb.org.vos.CpResponsibilityVO;

public interface ICpResponsibilityBill {
	public String[] add(CpResponsibilityVO[] vos) throws CpbBusinessException;
	public void update(CpResponsibilityVO[] vos) throws CpbBusinessException;
	public String[] addRespFuncVos(CpRespFuncVO[] vos) throws CpbBusinessException;
	public void delAllRespFuncVos(String pk_responsibility) throws CpbBusinessException;
	public void delResponsibilityVo(CpResponsibilityVO vo) throws CpbBusinessException;
}
