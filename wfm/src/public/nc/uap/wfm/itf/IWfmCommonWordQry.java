package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.vo.WfmCommonWordVO;


/**
 * �������ѯ�ӿڡ�
 * @author ybo
 * @version 2011-3-17
 * @since NCPortal6.0
 */
public interface IWfmCommonWordQry {
	
	/**
	 * ȡ���û����õĳ�����
	 * @param userPk �û�����
	 * @return ������VO����
	 * @throws WfmServiceException
	 */
	WfmCommonWordVO[] getUserCommonWord(String userPk) throws WfmServiceException;
	
	/**
	 * ȡ��ָ����Χ��ָ������ĳ�����
	 * @param pkObj ������������Χ��������
	 * @param scope ��Χ
	 * @return ������VO
	 * @throws WfmServiceException
	 */
	WfmCommonWordVO[] getCommonWordByScopeObj(String pkObj,int scope) throws WfmServiceException;
}
