package nc.uap.wfm.engine;
import nc.uap.wfm.model.FormData;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.AbstractFormVO;
import nc.uap.wfm.vo.WfmSuitPrintVO;
/**
 * �״�ģ�嵥�ݵ��Զ���ӿ�
 * 
 * @author tianchw
 * 
 */
public interface IFormDataBuilder {
	FormData builder(WfmSuitPrintVO vo, AbstractFormVO formVo, Task task);
}
