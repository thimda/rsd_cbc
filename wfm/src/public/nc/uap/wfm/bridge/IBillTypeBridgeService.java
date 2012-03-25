package nc.uap.wfm.bridge;

import java.util.Map;

import nc.uap.dbl.exception.DblServiceException;

/**
 * ���������Žӷ������ε������͵�������ϵ
 * @author dengjt
 *
 */
public interface IBillTypeBridgeService {
	/**
	 * ��ȡ�ڲ�ͬ�豸�ϣ���Ӧ�������͵��ݵ�չ������
	 * @param billType  ��������
	 * @param deviceType �豸���ͱ�ʶ������������Լ��
	 * @param messageMap  ������Ϣ,��ϢKEY��ο�  TaskMessageManger
	 * @return
	 * @throws PortalServiceException
	 */
	public String getViewContent(String billType, String deviceType, Map<String, Object> messageMap) throws DblServiceException;
}
