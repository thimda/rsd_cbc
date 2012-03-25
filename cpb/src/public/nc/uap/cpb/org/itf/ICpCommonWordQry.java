package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.vos.CpCommonWordVO;
/**
 * �������ѯ�ӿڡ�
 * 
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public interface ICpCommonWordQry {
	/**
	 * ȡ���û����õĳ�����
	 * 
	 * @param userPk
	 *            �û�����
	 * @return ������VO����
	 * @throws PwfmBusinessException
	 */
	CpCommonWordVO[] getUserCommonWord(String userPk);
	/**
	 * ȡ��ָ����Χ��ָ������ĳ�����
	 * 
	 * @param pkObj
	 *            ������������Χ��������
	 * @param scope
	 *            ��Χ
	 * @return ������VO
	 * @throws PwfmBusinessException
	 */
	CpCommonWordVO[] getCommonWordByScopeObj(String pkObj, int scope);
}
