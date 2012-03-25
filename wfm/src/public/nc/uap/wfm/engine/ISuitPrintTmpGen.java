package nc.uap.wfm.engine;

import nc.uap.wfm.model.FormData;
import nc.uap.wfm.vo.WfmSuitPrintVO;

public interface ISuitPrintTmpGen {
	String genTmpGenStringUrl(FormData formData, WfmSuitPrintVO suitPrintVo);
}
