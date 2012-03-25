package nc.uap.wfm.bridge;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.AbstractFormVO;
/**
 * ����������õķ���ӿ�,ÿ�ֵ�������Ӧ��Ӧһ�ַ���ʵ��. �������ͬ��NC�ı�׼��������ֱ�ӵ�����á�
 * 
 * @author dengjt
 * 
 */
public interface IBillFormService {
	/**
	 * ��ȡ��Ӧ�豸�ϵ�ֻ��Ԥ������
	 * 
	 * @param deviceType
	 * @return
	 * @throws PortalServiceException
	 */
	public String getViewContent(String deviceType, Task task) throws DblServiceException;
	/**
	 * ����ύʱ��UI��װ��Ӧ�ĵ���VO
	 * 
	 * @return
	 */
	public AbstractFormVO getFormVOFromUI();
	/**
	 * ���ִ�е���ʱ��Ĭ��PageModel��
	 * 
	 * @return
	 */
	public String getPageModelClazz(Task task);
	/**
	 * ��÷��𵥾�ʱ��Ĭ��PageModel��
	 * 
	 * @return
	 */
	public String getStartPageModelClazz(ProDef proDef);
	/**
	 * ��ȡ������ϸ��ȾPageModel��
	 * 
	 * @param task
	 * @return
	 */
	public String getDetailPageModelClazz(Task task);
	/**
	 * ��ȡ���ݷ�����
	 * 
	 * @return
	 */
	public IWfmFormOper getWfService();
	/**
	 * ������Ϣ���ĵĵ���ģ��
	 */
	public String genMsgCenterFrmTmp(Task task);
}
