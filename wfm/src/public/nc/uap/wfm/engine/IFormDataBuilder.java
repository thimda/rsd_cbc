package nc.uap.wfm.engine;
import nc.uap.wfm.model.FormData;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.AbstractFormVO;
import nc.uap.wfm.vo.WfmSuitPrintVO;
/**
 * 套打模板单据的自定义接口
 * 
 * @author tianchw
 * 
 */
public interface IFormDataBuilder {
	FormData builder(WfmSuitPrintVO vo, AbstractFormVO formVo, Task task);
}
