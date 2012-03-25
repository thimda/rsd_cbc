package nc.uap.portal.ctrl.office.data.sign;

import nc.uap.lfw.core.exception.LfwBusinessException;

public interface IsignLogQuery {
	/**
	 * ��ȡ���е���־
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getAllSignLogs() throws LfwBusinessException;
	/**
	 * ������������
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO getSignLogByPK(String pk) throws LfwBusinessException;
	/**
	 * ����where��������
	 * @param strWhere
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getSignLogsByCondition(String strWhere)
			throws LfwBusinessException;
	/**
	 * ���������ֶβ�ѯ
	 * @param strWhere
	 * @param fields
	 * @return
	 * @throws LfwBusinessException
	 */
	public SignlogVO[] getSignLogsByCondition(String strWhere, String[] fields)
			throws LfwBusinessException;
	/**
	 * ����  �����ǿ������ԭ������
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertSignLogVOWithPK(SignlogVO vo) throws LfwBusinessException;
	/**
	 * ������  �����ǿ������ԭ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertSignLogVOWithPKs(SignlogVO[] vos)
			throws LfwBusinessException;
	/**
	 * ����
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	public String insertSignLogVO(SignlogVO vo) throws LfwBusinessException;
	/**
	 * ������
	 * @param vos
	 * @return
	 * @throws LfwBusinessException
	 */
	public String[] insertSignLogVOs(SignlogVO[] vos) throws LfwBusinessException;
}
