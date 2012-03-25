package nc.uap.wfm.itf;
import nc.uap.wfm.vo.WfmFlwTypeVO;
public interface IWfmFlwTypeQry {
	WfmFlwTypeVO getFlwTypeVoByPk(String flwTypePk);
	WfmFlwTypeVO[] getFlwTypeVoByPks(String[] flwTypePks);
	WfmFlwTypeVO getFlwTypeVoByPageId(String pageId);
	WfmFlwTypeVO[] getSubFlwTypeByParentPk(String parentPk);
	WfmFlwTypeVO[] getFlwTypeVosByFlowCatePk(String flowCatePk);
}
