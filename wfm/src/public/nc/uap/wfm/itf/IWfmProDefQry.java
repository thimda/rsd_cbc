package nc.uap.wfm.itf;
import java.util.List;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.MyWork;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 2010-12-17 ����04:20:41 limingf
 */
public interface IWfmProDefQry {
	/**
	 * ��ȡ���̶��������������
	 * 
	 * @param flwTypePk
	 * @return
	 * @throws WfmServiceException
	 */
	public WfmProdefVO[] getProDefByFlwTypePk(String flwTypePk) throws WfmServiceException;
	/**
	 * ��ȡ���̶��������������
	 * 
	 * @param flwTypePk
	 * @return
	 * @throws WfmServiceException
	 */
	public WfmProdefVO[] getProdefVOByName(String name) throws WfmServiceException;
	/**
	 * ��ȡָ�������̶���VO
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO getProDefVOByProDefPk(String proDefPk) throws WfmServiceException;
	/**
	 * ��ȡָ�������̶���VO
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO getProDefVOByFrmDefPk(String frmDefPk) throws WfmServiceException;
	/**
	 * ��ȡ���е����̶���VOS
	 * 
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getProDefVOByProDefPks(String[] proDefPks) throws WfmServiceException;
	/**
	 * ��ȡ�������̶���
	 */
	WfmProdefVO[] getAllProDef() throws WfmServiceException;
	/**
	 * ��ȡ�Ҳ��������
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyPrtptProDef(String userPk) throws WfmServiceException;
	/**
	 * �ҷ��������
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyStartptProDef(String userPk) throws WfmServiceException;
	/**
	 * �Ҽ�ص�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProdefVO[] getMyWatchptProDef(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�ҵ����д��������б�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	List<MyWork> getMyUndoWork(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�ҵ����д��������б�����
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	List<MyWork> getMyUnreadWork(String userPk) throws WfmServiceException;
	/**
	 * �ҵĴ��칤������
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	int getMyUndoWorkCount(String userPk) throws WfmServiceException;
	/**
	 * �ҵĴ��Ĺ�������
	 * 
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	int getMyUnreadWorkCount(String userPk) throws WfmServiceException;
}
