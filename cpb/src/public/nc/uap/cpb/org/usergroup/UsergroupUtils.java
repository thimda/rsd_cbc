package nc.uap.cpb.org.usergroup;

import nc.uap.cpb.org.user.ICpUserConst;
import nc.uap.cpb.org.vos.CpUserGroupVO;
import nc.vo.uap.rbac.UserGroupVO;

public class UsergroupUtils {
	public static void convert(CpUserGroupVO cpusergroup,UserGroupVO ncusergroup){
		if(cpusergroup==null)
			cpusergroup = new CpUserGroupVO();
		cpusergroup.setOriginal(ICpUserConst.ORIGINAL_NC);
		cpusergroup.setPk_usergroup(ncusergroup.getPk_usergroup());
		cpusergroup.setNcpk(ncusergroup.getPk_usergroup());
		cpusergroup.setGroup_code(ncusergroup.getGroupcode());
		cpusergroup.setGroup_name(ncusergroup.getGroupname());
		cpusergroup.setPk_parent(ncusergroup.getParentid());
		cpusergroup.setCreateddate(ncusergroup.getCreationtime().getDate());
		cpusergroup.setCreateduser(ncusergroup.getCreator());
		cpusergroup.setDr(ncusergroup.getDr());
		cpusergroup.setPk_org(ncusergroup.getPk_org());
		cpusergroup.setPk_group(ncusergroup.getPk_group());
	}

}
