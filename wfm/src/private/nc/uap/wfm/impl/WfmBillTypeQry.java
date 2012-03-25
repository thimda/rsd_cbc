package nc.uap.wfm.impl;

import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.util.LfwClassUtil;
import nc.uap.wfm.bridge.IBillFormService;
import nc.uap.wfm.bridge.IWfmBillTypeQry;
import nc.uap.wfm.vo.WfmCpBillTypeVO;

public class WfmBillTypeQry implements IWfmBillTypeQry {
	private static final Map<String, WfmCpBillTypeVO> billTypeMap = new HashMap<String, WfmCpBillTypeVO>();
	static{		
//		CpBillTypeVO formType = new CpBillTypeVO();
//		formType.setCode(FORM_TYPE);
//		formType.setHandleclazz("nc.portal.pdbl.impl.CpFormServiceImpl");
//		billTypeMap.put(FORM_TYPE, formType);
	}
	@Override
	public IBillFormService getBillFormService(String billType) {
		WfmCpBillTypeVO vo = billTypeMap.get(billType);
		if(vo == null)
			return null;
		return (IBillFormService) LfwClassUtil.newInstance(vo.getHandleclazz());
	}

}
