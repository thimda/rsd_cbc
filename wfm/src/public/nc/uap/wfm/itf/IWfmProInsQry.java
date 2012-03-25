package nc.uap.wfm.itf;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.vo.WfmProInsVO;
public interface IWfmProInsQry {
	/**
	 * ��ȡ����ʵ������
	 * @param proInsPk
	 * @return
	 * @throws WfmServiceException
	 */
	ProIns getProInsByPk(String proInsPk) throws WfmServiceException;
	/**
	 * ��ȡ����������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProIns(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ��������������ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProIns(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�ҽ�����������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByDay(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�ҽ���������������ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByDay(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�����һ����������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByWeek(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�����һ��������������ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByWeek(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�����һ������������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUnDneProInsByMonth(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ�����һ����������������ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByMonth(String userPk) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ�����������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndUndneProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException;
	/**
	 * ��ȡ��ָ��ʱ�����������û����ɵ�����
	 * @param userPk
	 * @return
	 * @throws WfmServiceException
	 */
	WfmProInsVO[] getMyStartAndCmpltProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException;
	WfmProInsVO[] getSubProInsByProInsPk(String proInsPk) throws WfmServiceException;
}
