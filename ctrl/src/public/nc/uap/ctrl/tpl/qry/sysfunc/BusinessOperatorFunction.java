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

/**
 * 系统函数：业务人员(取当前用户对应的人员)
 * 创建日期:(2011-4-11)
 * @author jingli
 */
public class BusinessOperatorFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "busioperator" + TOKEN;

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
				"UPP_NewQryTemplate-0070")/* 当前业务员 */;
	}

	public SFType getType() {
		return new SFType(IBDMetaDataIDConst.PSNDOC);
	}

	@SuppressWarnings("unchecked")
	public RefResultVO value() {
		RefResultVO result = new RefResultVO();
		try {
			boolean isInServer = RuntimeEnv.getInstance().isRunningInServer();
			String userId = null;
			if (isInServer) {
				userId = InvocationInfoProxy.getInstance().getUserId();
			} else {
				// 因为WorkbenchEnvironment位于Client包，所以只能采用反射
				Class c = Class.forName("nc.desktop.ui.WorkbenchEnvironment");
				Method getInstance = c.getMethod("getInstance", (Class[]) null);
				Object obj = getInstance.invoke(null, (Object[]) null);
				Method getLoginUser = c.getMethod("getLoginUser",(Class[]) null);
				UserVO vo = (UserVO) getLoginUser.invoke(obj, (Object[]) null);
				userId = vo.getCuserid();
			}
			IUAPQueryBS qryService = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			String sql = "select pk_psndoc,code,name from bd_psndoc where pk_psndoc in(select pk_base_doc from sm_user where base_doc_type = 0 and cuserid = '" + userId + "')";
			List list = (List) qryService.executeQuery(sql, new ArrayListProcessor());
			if(list != null && list.size() >0){
				Object[] obj = (Object[]) list.get(0);
				result.setRefPK((String)obj[0]);
				result.setRefCode((String)obj[1]);
				result.setRefName((String)obj[2]);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
		return result;
	}

}
