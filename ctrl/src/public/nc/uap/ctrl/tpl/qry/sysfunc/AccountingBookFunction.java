package nc.uap.ctrl.tpl.qry.sysfunc;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.individuation.property.pub.IndividuationManager;
import nc.itf.org.IOrgMetaDataIDConst;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;

import org.apache.commons.lang.StringUtils;

/**
 * 系统函数：账簿(取个性化中心默认组织所对应的主账簿)
 * 创建日期:(2011-4-11)
 * @author jingli
 */
public class AccountingBookFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "accountingbook" + TOKEN;
	
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
				"UPP_NewQryTemplate-0072")/* 默认账簿 */;
	}

	public SFType getType() {
		return new SFType(IOrgMetaDataIDConst.ACCOUNTINGBOOK);
	}

	public RefResultVO value() {
		RefResultVO result = new RefResultVO();
		try {
			String pk_org = IndividuationManager.getIndividualSetting(IDefaultSettingConst.PAGE_ID, false).getString(IDefaultSettingConst.DEFAULT_BIZ_UNIT);
			IUAPQueryBS qryService = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			if(StringUtils.isBlank(pk_org)){
				return result;
			}
			String sql = "select pk_accountingbook,code,name from org_accountingbook where pk_relorg = '" + pk_org + "' and accounttype = 1";
			List list = (List) qryService.executeQuery(sql, new ArrayListProcessor());
			if(list!=null && list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				result.setRefPK((String)obj[0]);
				result.setRefCode((String)obj[1]);
				result.setRefName((String)obj[2]);
			}
			
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		return result;
	}

}
