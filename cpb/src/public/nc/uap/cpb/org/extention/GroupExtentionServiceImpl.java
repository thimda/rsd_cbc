package nc.uap.cpb.org.extention;
import java.util.Date;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpRoleGroupVO;
import nc.uap.cpb.org.vos.CpRoleVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.org.GroupVO;
import nc.vo.pub.lang.UFDate;

/**
 * ���Ź�����չʵ��
 * 2012-3-14 ����09:11:43
 * @author limingf
 *
 */
public class GroupExtentionServiceImpl implements ICpbExtentionService {
	public String acceptFunType() {
		return ICpbExtentionService.GROUPMANAGE;
	}
	public void afterAction(String functionType, String actionType, Object object) {
		if (actionType.equals(ICpbExtentionService.ADD)) {
			if (object instanceof GroupVO) {
				LfwLogger.error("#GroupExtentionServiceImpl afterAction��ʼ#");
				GroupVO group = (GroupVO) object;
				String pk_org = group.getPk_group();
				try{						
					//�½�Ĭ�Ͻ�ɫ��
					CpRoleGroupVO businessrolegroup = new CpRoleGroupVO();
					businessrolegroup.setGroupcode(CpRoleGroupVO.DEFAULT_ROLECODE);
					businessrolegroup.setGroupname(CpRoleGroupVO.DEFAULT_ROLENAME);
					businessrolegroup.setType(CpRoleGroupVO.ROLEGROUPTYPE_BUSINESS);
					businessrolegroup.setDatecreated(new UFDate(new Date()));
					businessrolegroup.setPk_org(pk_org);
					String pk_rolegroup = CpbServiceFacility.getCpRoleGroupBill().addPtRoleGroupVO(businessrolegroup);
				
					//����Ĭ�Ͻ�ɫ
					CpRoleVO businessrole = new CpRoleVO();
					businessrole.setRolecode(CpRoleVO.DEFAULT_ROLECODE);
					businessrole.setRolename(CpRoleVO.DEFAULT_ROLENAME);
					businessrole.setType(CpRoleVO.ROLETYPE_BUSINESS);
					businessrole.setPk_rolegroup(pk_rolegroup);
					businessrole.setPk_org(pk_org);
					CpbServiceFacility.getCpRoleBill().addPtRoleVO(businessrole);	
					
				} catch (CpbBusinessException e) {
					LfwLogger.error(e.getMessage(), e);
					throw new LfwRuntimeException(e.getMessage());
			}
				LfwLogger.error("#GroupExtentionServiceImpl afterAction����#");
			}
		}
		} 
	
	public void beforeAction(String functionType, String actionType, Object object) {
		if (actionType.equals(ICpbExtentionService.ADD)) {}
	}
}
