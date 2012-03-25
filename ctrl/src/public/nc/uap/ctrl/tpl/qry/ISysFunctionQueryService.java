package nc.uap.ctrl.tpl.qry;

import nc.vo.pub.BusinessException;

/**
 * 查询模板系统函数查询服务
 * 
 */
public interface ISysFunctionQueryService {

	/**
	 * 返回已注册的系统函数类名数组
	 */
	public String[] getRegisteredFunctionsClassName() throws BusinessException;
}