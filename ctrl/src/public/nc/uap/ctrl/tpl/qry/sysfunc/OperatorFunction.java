package nc.uap.ctrl.tpl.qry.sysfunc;

import java.lang.reflect.Method;
import java.util.List;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.logging.Logger;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.sm.UserVO;

import org.apache.commons.lang.StringUtils;

/**
 * 系统函数：操作员
 * 
 * @author 刘晨伟
 *
 * 创建日期：2009-12-3
 */
public class OperatorFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "operator" + TOKEN;

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
				"UPP_NewQryTemplate-0069")/* 当前操作员 */;
	}

	public SFType getType() {
		return new SFType(IBDMetaDataIDConst.USER);
	}

	@SuppressWarnings("unchecked")
	public RefResultVO value() {
		boolean isInServer = RuntimeEnv.getInstance().isRunningInServer();
		RefResultVO result = new RefResultVO();
		String pk_operator = null;
		try{
			if (isInServer) {
				pk_operator = InvocationInfoProxy.getInstance().getUserId();
				if(!StringUtils.isBlank(pk_operator)){
					String sql = "select cuserid,user_code,user_name from sm_user where cuserid = '" + pk_operator + "'";
					IUAPQueryBS service = NCLocator.getInstance().lookup(IUAPQueryBS.class);
					List<Object> list = (List<Object>)service.executeQuery(sql, new ArrayListProcessor());
					Object[] obj = list == null || list.size() == 0 ? null : (Object[])list.get(0);
					if(obj != null){
						result.setRefPK(pk_operator);
						result.setRefCode((String)obj[1]);
						result.setRefName((String)obj[2]);
					}
					return result;
				}
			} else {
				// 因为WorkbenchEnvironment位于Client包，所以只能采用反射
				Class c = Class.forName("nc.desktop.ui.WorkbenchEnvironment");
				Method getInstance = c.getMethod("getInstance", (Class[]) null);
				Object obj = getInstance.invoke(null, (Object[]) null);
				Method getLoginUser = c.getMethod("getLoginUser",(Class[]) null);
				UserVO vo = (UserVO) getLoginUser.invoke(obj, (Object[]) null);
				pk_operator = vo.getCuserid();
				result.setRefCode(vo.getUser_code());
				result.setRefName(vo.getUser_name());
				result.setRefPK(pk_operator);
				return result;
			}
		}catch(Exception e){
			Logger.error(e.getMessage(),e);
		}
		
		return result;
	}
}