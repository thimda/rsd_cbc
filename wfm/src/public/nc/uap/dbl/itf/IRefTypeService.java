package nc.uap.dbl.itf;

import nc.uap.lfw.core.refnode.RefNode;

public interface IRefTypeService {
	public RefNode getRefNodeByRefTypePk(String refTypePk,String fieldId);
	public String getShowValueByValue(String value);
}
