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
 * ϵͳ����������֯(ȡ���Ի�����Ĭ����֯)
 * ��������:(2011-4-11)
 * @author jingli
 */
public class MainOrgFunction implements ISysFunction {
	
	private final static String CODE = TOKEN + "mainorg" + TOKEN;
	
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return NCLangRes4VoTransl.getNCLangRes().getStrByID("_Template",
				"UPP_NewQryTemplate-0071")/* Ĭ������֯ */;
	}

	public SFType getType() {
		SFType sfType = new SFType(IOrgMetaDataIDConst.ORG);
		sfType.setSupportBeanList(getSupportBeanList());
		return sfType;
	}
	
	private List<String> getSupportBeanList(){
		List<String> supportBeanList = new ArrayList<String>();
		supportBeanList.add(IOrgMetaDataIDConst.CORP);//���˹�˾
		supportBeanList.add(IOrgMetaDataIDConst.HRORG);//������Դ
		supportBeanList.add(IOrgMetaDataIDConst.FINANCEORG);//������֯
		supportBeanList.add(IOrgMetaDataIDConst.FUNDORG);//�ʽ���֯
		supportBeanList.add(IOrgMetaDataIDConst.PURCHASEORG);//�ɹ���֯
		supportBeanList.add(IOrgMetaDataIDConst.SALESORG);//������֯
		supportBeanList.add(IOrgMetaDataIDConst.STOCKORG);//�����֯
		supportBeanList.add(IOrgMetaDataIDConst.TRAFFICORG);//������֯
		supportBeanList.add(IOrgMetaDataIDConst.QCCENTER);//�ʼ���֯
		supportBeanList.add(IOrgMetaDataIDConst.ASSETORG);//�ʲ���֯
		supportBeanList.add(IOrgMetaDataIDConst.MAINTAINORG);//ά����֯
		supportBeanList.add(IOrgMetaDataIDConst.LIABILITYCENTER);//��������
		supportBeanList.add(IOrgMetaDataIDConst.ITEMORG);//��Ŀ
		supportBeanList.add(IOrgMetaDataIDConst.PLANBUDGET);//Ԥ��
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
