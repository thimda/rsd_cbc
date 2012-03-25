package nc.uap.wfm.bridge;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.AbstractFormVO;
/**
 * 单据适配调用的服务接口,每种单据类型应对应一种服务实现. 这个服务不同于NC的标准服务，它是直接的类调用。
 * 
 * @author dengjt
 * 
 */
public interface IBillFormService {
	/**
	 * 获取对应设备上的只读预览内容
	 * 
	 * @param deviceType
	 * @return
	 * @throws PortalServiceException
	 */
	public String getViewContent(String deviceType, Task task) throws DblServiceException;
	/**
	 * 审核提交时从UI组装对应的单据VO
	 * 
	 * @return
	 */
	public AbstractFormVO getFormVOFromUI();
	/**
	 * 获得执行单据时的默认PageModel类
	 * 
	 * @return
	 */
	public String getPageModelClazz(Task task);
	/**
	 * 获得发起单据时的默认PageModel类
	 * 
	 * @return
	 */
	public String getStartPageModelClazz(ProDef proDef);
	/**
	 * 获取流程明细渲染PageModel类
	 * 
	 * @param task
	 * @return
	 */
	public String getDetailPageModelClazz(Task task);
	/**
	 * 获取单据服务类
	 * 
	 * @return
	 */
	public IWfmFormOper getWfService();
	/**
	 * 生成消息中心的单据模板
	 */
	public String genMsgCenterFrmTmp(Task task);
}
