package nc.uap.cpb.org.extention;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.user.UserCacheUtil;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.log.LfwLogger;
/**
 * 用户组管理扩展实现
 * 2011-6-8 上午11:11:09 
 * @author limingf 
 */
public class UsergroupExtentionServiceImpl implements ICpbExtentionService {
	public String acceptFunType() {
		return ICpbExtentionService.USERGROUPMANAGE;
	}
	@Override
	public void afterAction(String functionType, String actionType, Object obj) {
		//用户组关联/删除角色，清空用户组下用户角色缓存
		if (actionType.equals(ICpbExtentionService.USERGROUP_RELATE_ROLE) || actionType.equals(ICpbExtentionService.USERGROUP_DELETE_ROLE)) {
			String pk_usergroup = (String) obj;
			CpUserVO[] users = null;
			try {
				users = CpbServiceFacility.getCpUserQry().getUserByUserGroup(pk_usergroup);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
			}
			UserCacheUtil.clearUserRolesCache(users);
		}
	}
	@Override
	public void beforeAction(String functionType, String actionType, Object object) {}
}
