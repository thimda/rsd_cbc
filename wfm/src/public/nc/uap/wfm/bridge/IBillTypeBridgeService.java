package nc.uap.wfm.bridge;

import java.util.Map;

import nc.uap.dbl.exception.DblServiceException;

/**
 * 单据类型桥接服务，屏蔽单据类型的依赖关系
 * @author dengjt
 *
 */
public interface IBillTypeBridgeService {
	/**
	 * 获取在不同设备上，对应单据类型单据的展现内容
	 * @param billType  单据类型
	 * @param deviceType 设备类型标识，此类型自行约定
	 * @param messageMap  内容信息,信息KEY请参考  TaskMessageManger
	 * @return
	 * @throws PortalServiceException
	 */
	public String getViewContent(String billType, String deviceType, Map<String, Object> messageMap) throws DblServiceException;
}
