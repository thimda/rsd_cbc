package nc.uap.ctrl.tpl.qry.sysfunc;

import java.util.Date;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.individuation.property.pub.IndividuationManager;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.pubitf.accperiod.AccountCalendar;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;

import org.apache.commons.lang.StringUtils;

/**
 * 系统函数：当前期间(取个性化中心默认组织的主账簿的期间方案下当前时间对应的期间)
 * 创建日期:(2011-5-30)
 * @author jingli
 */
public class CurrentAccperiodFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "currentaccperiod" + TOKEN;

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
		"UPP_NewQryTemplate-0074")/* 当前期间 */;
	}

	public SFType getType() {
		return new SFType(IBDMetaDataIDConst.ACCPERIODMONTH);
	}

	public RefResultVO value() {
		RefResultVO result = new RefResultVO();
		try {
			String pk_org = IndividuationManager.getIndividualSetting(IDefaultSettingConst.PAGE_ID, false).getString(IDefaultSettingConst.DEFAULT_BIZ_UNIT);
			IUAPQueryBS qryService = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			if(StringUtils.isBlank(pk_org)){
				return result;
			}
			String sql = "select pk_accperiodscheme from org_setofbook a inner join org_accountingbook b on a.pk_setofbook = b.pk_setofbook where pk_relorg = '" + pk_org + "' and accounttype = 1";
			List list = (List) qryService.executeQuery(sql, new ColumnListProcessor());
			if(list!=null && list.size()>0){
				String pk_accperiodscheme = (String) list.get(0);
				
				AccountCalendar canlendar = AccountCalendar.getInstanceByPeriodScheme(pk_accperiodscheme);
				canlendar.setDate(new Date());
				AccperiodmonthVO currentMonth = canlendar.getMonthVO();
				if(currentMonth!=null){
					result.setRefPK(currentMonth.getPk_accperiodmonth());
					result.setRefCode(currentMonth.getYearmth());
					result.setRefName(currentMonth.getYearmth());
				}
			}
			
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
		}
		return result;
	}

}
