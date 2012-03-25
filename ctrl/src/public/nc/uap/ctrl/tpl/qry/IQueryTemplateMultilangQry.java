package nc.uap.ctrl.tpl.qry;

import java.util.Map;

import nc.uap.ctrl.tpl.qry.meta.QueryTempletTotalVO;
import nc.vo.pub.BusinessException;

/**
 * 查询模板多语查询接口
 * 创建日期:(2011-8-1)
 * @author jingli
 */
public interface IQueryTemplateMultilangQry {
	
	/**
	 * 批量查询功能节点下的多语资源
	 * @param nodecode
	 * @param resIDs
	 * @return
	 * @throws BusinessException
	 * 创建日期:(2011-8-1)
	 */
	public Map<String, String> getMutilangByResIDs(String nodecode,String[] resIDs) throws BusinessException;
	
	/**
	 * @param totalVO
	 * @return
	 * @throws BusinessException
	 * 创建日期:(2011-8-1)
	 */
	public Map<String, String> getMutilangOfTotalVO(QueryTempletTotalVO totalVO) throws BusinessException;
	
}
