package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpSysinitVO;

/**
 * �������ò�ѯ����
 * 
 * @author chenwl
 * 
 */
public interface ICpSysinitQry {
	
	/**
	 * ����pk��ѯ
	 * @param pk_sysinit
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO getSysinitByPk(String pk_sysinit) throws CpbBusinessException;
	
	/**
	 * ���ݲ��������������֯��ѯ
	 * @param initcode
	 * @param pk_org
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO getSysinitByCodeAndPkorg(String initcode,String pk_org) throws CpbBusinessException;
	
	/**
	 * ���ݲ���������֯��ѯ
	 * @param pk_org
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO[] getSysinitByPkorg(String pk_org) throws CpbBusinessException;
	
	/**
	 * ����where������ѯ
	 * @param where
	 * @return
	 * @throws CpbBusinessException
	 */
	public CpSysinitVO[] getSysinitByWhere(String where) throws CpbBusinessException;
}
