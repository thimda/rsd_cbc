package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpSysinitVO;

/**
 * 参数设置查询服务
 * 
 * @author chenwl
 * 
 */
public interface ICpSysinitQry {
	
	/**
	 * 根据pk查询
	 * @param pk_sysinit
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO getSysinitByPk(String pk_sysinit) throws CpbBusinessException;
	
	/**
	 * 根据参数编码和所属组织查询
	 * @param initcode
	 * @param pk_org
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO getSysinitByCodeAndPkorg(String initcode,String pk_org) throws CpbBusinessException;
	
	/**
	 * 根据参数所属组织查询
	 * @param pk_org
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO[] getSysinitByPkorg(String pk_org) throws CpbBusinessException;
	
	/**
	 * 根据where条件查询
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO[] getSysinitByWhere(String where) throws CpbBusinessException;
}
