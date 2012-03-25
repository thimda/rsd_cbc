package nc.uap.cpb.log;

import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.pub.SuperVO;

public interface ICpLogQuery {
	/**
	 * ��ȡ���е���־
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getAllLogs(Class voClass) throws LfwBusinessException;
	/**
	 * ������������
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO getLogByPK(Class voClass,String pk) throws LfwBusinessException;
	/**
	 * ����where��������
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere)
			throws LfwBusinessException;
	/**
	 * ���������ֶβ�ѯ
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public SuperVO[] getLogsByCondition(Class voClass,String strWhere, String[] fields)
			throws LfwBusinessException;
	/**
	 * ����  �����ǿ������ԭ������
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertLogVOWithPK(SuperVO vo) throws LfwBusinessException;
	/**
	 * ������  �����ǿ������ԭ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertLogVOWithPKs(SuperVO[] vos)
			throws LfwBusinessException;
	/**
	 * ����
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertLogVO(SuperVO vo) throws LfwBusinessException;
	/**
	 * ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertLogVOs(SuperVO[] vos) throws LfwBusinessException;
	/**
	 * ����vo����������ݿ�
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public int updataVO(SuperVO vo) throws LfwBusinessException;
}
