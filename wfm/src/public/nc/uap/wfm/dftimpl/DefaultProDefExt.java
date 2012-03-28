package nc.uap.wfm.dftimpl;
import java.util.HashMap;
import java.util.Map;
import nc.uap.dbl.constant.DblConstants;
import nc.uap.wfm.engine.IProDefExtHandler;
import nc.uap.wfm.model.ProDef;
public class DefaultProDefExt implements IProDefExtHandler {
	@Override public String getFrmNumBillServerClass() {
		return null;
	}
	public Map<String, String> getExtAttr(ProDef proDef, String frmDefPk) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ext0", DblConstants.MakeFormNumb);
		map.put("ext1", "title");
		map.put("ext2", DblConstants.MakeBillDept);
		map.put("ext3", "attachment");
		map.put("ext4", "emergency");
		map.put("ext5", "instruction");
		// map.put("ext6", "voperatorid");
		return map;
	}
	public Map<String, String> getExtAttrName(ProDef proDef, String frmDefPk) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ext0", "流水号");
		map.put("ext1", "标题");
		map.put("ext2", "部门");
		map.put("ext3", "附件");
		map.put("ext4", "缓急程度");
		map.put("ext5", "领导批示");
		// map.put("ext6", "制单人");
		return map;
	}
	public Map<String, String> getQryTaskAttr(ProDef proDef, String frmDefPk) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ext0", "流水号");
		map.put("ext1", "标题");
		map.put("ext4", "缓急程度");
		return map;
	}
	@Override public String getMyPrtptPageModel(String frmDefPk) {
		return null;
	}
}
