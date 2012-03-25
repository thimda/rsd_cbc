package nc.uap.ctrl.tpl.qry.sysfunc;

import java.util.ArrayList;
import java.util.List;

import nc.bs.logging.Logger;
import nc.itf.org.IOrgMetaDataIDConst;
import nc.pubitf.setting.defaultdata.OrgSettingAccessor;
import nc.uap.ctrl.tpl.qry.meta.RefResultVO;
import nc.uap.ctrl.tpl.qry.meta.SFType;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.org.OrgQueryUtil;
import nc.vo.org.OrgVO;
import nc.vo.org.util.OrgPubUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 系统函数：主组织(取个性化中心默认组织)
 * 创建日期:(2011-4-11)
 * @author jingli
 */
public class MainOrgFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "mainorg" + TOKEN;
	
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
				"UPP_NewQryTemplate-0071")/* 默认主组织 */;
	}

	public SFType getType() {
		SFType sfType = new SFType(IOrgMetaDataIDConst.ORG);
		sfType.setSupportBeanList(getSupportBeanList());
		return sfType;
	}
	
	private List<String> getSupportBeanList(){
		List<String> supportBeanList = new ArrayList<String>();
		supportBeanList.add(IOrgMetaDataIDConst.CORP);//法人公司
		supportBeanList.add(IOrgMetaDataIDConst.HRORG);//人力资源
		supportBeanList.add(IOrgMetaDataIDConst.FINANCEORG);//财务组织
		supportBeanList.add(IOrgMetaDataIDConst.FUNDORG);//资金组织
		supportBeanList.add(IOrgMetaDataIDConst.PURCHASEORG);//采购组织
		supportBeanList.add(IOrgMetaDataIDConst.SALESORG);//销售组织
		supportBeanList.add(IOrgMetaDataIDConst.STOCKORG);//库存组织
		supportBeanList.add(IOrgMetaDataIDConst.TRAFFICORG);//物流组织
		supportBeanList.add(IOrgMetaDataIDConst.QCCENTER);//质检组织
		supportBeanList.add(IOrgMetaDataIDConst.ASSETORG);//资产组织
		supportBeanList.add(IOrgMetaDataIDConst.MAINTAINORG);//维修组织
		supportBeanList.add(IOrgMetaDataIDConst.LIABILITYCENTER);//利润中心
		supportBeanList.add(IOrgMetaDataIDConst.ITEMORG);//项目
		supportBeanList.add(IOrgMetaDataIDConst.PLANBUDGET);//预算
		return supportBeanList;
	}

	public RefResultVO value() {
		RefResultVO result = new RefResultVO();
		String pk_org = null;
		try {
			pk_org = OrgSettingAccessor.getDefaultOrgUnit();
			if(StringUtils.isBlank(pk_org)){
				return result;
			}
			OrgVO[] orgVOs = OrgQueryUtil.queryOrgVOByPks(new String[]{pk_org});
			if(orgVOs != null && orgVOs.length>0){
				result.setRefCode(orgVOs[0].getCode());
				result.setRefName(OrgPubUtil.getOrgNameByMultiLang(orgVOs[0]));
			}
			result.setRefPK(pk_org);
		} catch (Exception e) {
			Logger.error(e.getMessage(),e);
		}
		return result;
	}

}
