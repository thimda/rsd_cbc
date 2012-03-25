package nc.uap.wfm.dftimpl;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nc.uap.wfm.engine.IFormDataBuilder;
import nc.uap.wfm.model.FormData;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.vo.AbstractFormVO;
import nc.uap.wfm.vo.DefaultFormVO;
import nc.uap.wfm.vo.WfmSuitPrintVO;
public class DefaultFormDataBuilder implements IFormDataBuilder {
	@Override public FormData builder(WfmSuitPrintVO vo, AbstractFormVO formVo, Task task) {
		Map<String, String> pageFrmIns = ((DefaultFormVO) formVo).getProp();
		Set<String> keys = pageFrmIns.keySet();
		Iterator<String> iter = keys.iterator();
		String key = null;
		String value = null;
		while (iter.hasNext()) {
			key = iter.next();
			value = pageFrmIns.get(key);
			if (value == null) {
				value = "";
			}
			pageFrmIns.put(key, value);
		}
		FormData formData = new FormData();
		formData.setFrmIns(pageFrmIns);
		return formData;
	}
}
