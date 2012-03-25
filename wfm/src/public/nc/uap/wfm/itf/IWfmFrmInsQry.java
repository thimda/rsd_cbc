package nc.uap.wfm.itf;

import java.util.List;
import java.util.Map;

import nc.uap.dbl.vo.DblFormItemVO;
import nc.uap.wfm.exception.WfmServiceException;

public interface IWfmFrmInsQry {
	/**
	 * 获取表单实例的数据
	 * 
	 * @param tablename
	 * @param pk_forminstance
	 * @return
	 * @throws WfmServiceException
	 */
	Map<String, String> getFrmInsByTabNameAndFrmInsPk(String tableName, String frmInsPk) throws WfmServiceException;
	public List<Map<String, String>> getFrmInsListByTableName(String tableName, int pageIndex, int pageSize) throws WfmServiceException;
	public int getFrmInsRecordsCountByTableName(String tableName) throws WfmServiceException;
	public int getOutFrmRecordsCountByTabNameAndPproInsPk(String tableName, String ppronInsPk) throws WfmServiceException;
	public List<Map<String, String>> getFrmInsListByTableName(String tableName) throws WfmServiceException;
	public List<Map<String, String>> getFrmInsListByTableNameAndPproInsPk(String tableName, int pageIndex, int pageSize, String ppronInsPk) throws WfmServiceException;
	List<Map<String, String>> getFrmInsListByCon(String tableName, String condition) throws WfmServiceException;
	List<Map<String, String>> getMyPrtptFrmInsListByTabName(String tableName, String userPk) throws WfmServiceException;
	List<Map<String, String>> getFrmInsListBySql(String sql) throws WfmServiceException;
	List<Map<String, String>> getFrmInsPkBypproInsPk(String pproInsPk) throws WfmServiceException;
	public Map<String, String> getFormItemByFormDefPk(String pk_formdefinition) throws WfmServiceException;
	public Map<String, DblFormItemVO> getFormItemsByFormDefPk(String pk_formdefinition) throws WfmServiceException;
	public List<Map<String, String>> getDynaRowDataByFrmInsPkAndTabName(String tabName, String frmInsPk) throws WfmServiceException;
}