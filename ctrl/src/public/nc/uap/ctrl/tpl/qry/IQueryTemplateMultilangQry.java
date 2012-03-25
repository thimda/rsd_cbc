package nc.uap.ctrl.tpl.qry;

import java.util.Map;

import nc.uap.ctrl.tpl.qry.meta.QueryTempletTotalVO;
import nc.vo.pub.BusinessException;

/**
 * ��ѯģ������ѯ�ӿ�
 * ��������:(2011-8-1)
 * @author jingli
 */
public interface IQueryTemplateMultilangQry {
	
	/**
	 * ������ѯ���ܽڵ��µĶ�����Դ
	 * @param nodecode
	 * @param resIDs
	 * @return
	 * @throws BusinessException
	 * ��������:(2011-8-1)
	 */
	public Map<String, String> getMutilangByResIDs(String nodecode,String[] resIDs) throws BusinessException;
	
	/**
	 * @param totalVO
	 * @return
	 * @throws BusinessException
	 * ��������:(2011-8-1)
	 */
	public Map<String, String> getMutilangOfTotalVO(QueryTempletTotalVO totalVO) throws BusinessException;
	
}
