package nc.uap.ctrl.tpl.qry;

import nc.vo.pub.BusinessException;

/**
 * ��ѯģ��ϵͳ������ѯ����
 * 
 */
public interface ISysFunctionQueryService {

	/**
	 * ������ע���ϵͳ������������
	 */
	public String[] getRegisteredFunctionsClassName() throws BusinessException;
}