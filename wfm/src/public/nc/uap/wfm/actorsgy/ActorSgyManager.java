package nc.uap.wfm.actorsgy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.engine.IHumActHandler;
import nc.uap.wfm.engine.ISelfDefActors;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmAssignActorsQry;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.itf.IWfmVirtualRoleQry;
import nc.uap.wfm.model.ActorStrategy;
import nc.uap.wfm.model.AssistStrategy;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.model.Task;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmAssignActorsVO;
import nc.uap.wfm.vo.WfmFormInfoCtx;
import nc.uap.wfm.vo.WfmVirtualRoleVO;
import org.apache.commons.lang.StringUtils;
/**
 * 参与者策略的管理器
 * 
 * @author tianchw
 * 
 */
public class ActorSgyManager implements IActorSgy {
	private static ActorSgyManager instance = null;
	private ActorSgyManager() {};
	synchronized public static ActorSgyManager getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new ActorSgyManager();
		}
		return instance;
	}
	/**
	 * 获取活动的参与者执行范围
	 * 
	 * @param formVo
	 * @param proIns
	 * @param humAct
	 * @param parentTask
	 * @return
	 */
	public String[] getActorsRange(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task pTask) {
		return this.getNormalActors(formVo, proIns, humAct, pTask);
	}
	/**
	 * 获取用户指定的参与者
	 * 
	 * @param formVo
	 * @param proIns
	 * @param humAct
	 * @param parentTask
	 * @return
	 */
	public String[] getRuntimeActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task pTask) {
		IHumActHandler humActHandler = (IHumActHandler) WfmClassUtil.loadClass(humAct.getDelegatorClass());
		if (humActHandler.isAssign(pTask, humAct)) {
			return this.getAssignActors(formVo, proIns, humAct, pTask);
		} else {
			return this.getActorsRange(formVo, proIns, humAct, pTask);
		}
	}
	/**
	 * 获取指派参与者
	 * 
	 * @return
	 */
	public String[] getAssignActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		IWfmAssignActorsQry assignActorQry = NCLocator.getInstance().lookup(IWfmAssignActorsQry.class);
		ProIns rootProIns = null;
		try {
			rootProIns = this.getRootProIns(proIns);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		WfmAssignActorsVO[] vos = null;
		if (proIns == null) {
			return null;
		}
		ProDef proDef = proIns.getProDef();
		if (rootProIns == null) {
			vos = assignActorQry.getAssignActors(proIns.getPk_proins(), proDef.getId(), humAct.getId());
		} else {
			vos = assignActorQry.getAssignActors(rootProIns.getPk_proins(), proDef.getId(), humAct.getId());
		}
		if (vos == null || vos.length == 0) {
			return null;
		}
		String str[] = new String[vos.length];
		for (int i = 0; i < vos.length; i++) {
			str[i] = vos[i].getPk_user();
		}
		return str;
	}
	/**
	 * 获取正常活动的参与者
	 * 
	 * @return
	 */
	public String[] getNormalActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		ActorStrategy actSgy = humAct.getActorStrategy();
		String users[] = null;
		if (StringUtils.isNotBlank(actSgy.getUsers())) {
			users = actSgy.getUsers().split(",");
		}
		String roles[] = null;
		if (StringUtils.isNotBlank(actSgy.getRoles())) {
			roles = actSgy.getRoles().split(",");
		}
		String depts[] = null;
		if (StringUtils.isNotBlank(actSgy.getDepts())) {
			depts = actSgy.getDepts().split(",");
		}
		Set<String> set = new HashSet<String>();
		String allUsers[][] = new String[4][];
		allUsers[0] = users;
		allUsers[1] = getUsersByRoles(roles);
		allUsers[2] = getUsersByDepts(depts);
		String selfDefClass = actSgy.getSelfDefClass();
		if (StringUtils.isNotBlank(selfDefClass)) {
			allUsers[3] = ((ISelfDefActors) WfmClassUtil.loadClass(selfDefClass)).getActors(formVo, proIns, humAct, parentTask);
		}
		String virtualRole = actSgy.getVirtualRole();
		if (StringUtils.isNotBlank(virtualRole)) {
			try {
				WfmVirtualRoleVO[] vos = NCLocator.getInstance().lookup(IWfmVirtualRoleQry.class).getRoleByPk(new String[] { virtualRole });
				if (vos != null && vos.length != 0) {
					String virRoleClass = vos[0].getServiceclass();
					IVirtualRoleActor virtualRoleActor = (IVirtualRoleActor) WfmClassUtil.loadClass(virRoleClass);
					allUsers[4] = virtualRoleActor.getActors(formVo, proIns, humAct, parentTask);
				}
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
		for (int i = 0; i < allUsers.length; i++) {
			if (allUsers[i] != null && allUsers[i].length != 0) {
				for (int j = 0; j < allUsers[i].length; j++) {
					set.add(allUsers[i][j]);
				}
			}
		}
		String[] userPks = set.toArray(new String[0]);
		if (userPks == null || userPks.length == 0) {
			return null;
		}
		CpUserVO[] userVos = null;
		try {
			userVos = CpbServiceFacility.getCpUserQry().getUserByPkS(userPks);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (userVos == null || userVos.length == 0) {
			return null;
		}
		CpUserVO userVo = null;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < userVos.length; i++) {
			userVo = userVos[i];
			if (!humAct.isNotResDept() || (humAct.isNotResDept())) {
				list.add(userVo.getCuserid());
			}
		}
		return list.toArray(new String[0]);
	}
	/**
	 * 获取主办参与者
	 * 
	 * @return
	 */
	public String[] getMajorActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		return this.getNormalActors(formVo, proIns, humAct, parentTask);
	}
	/**
	 * 获取协办参与者
	 * 
	 * @return
	 */
	public String[] getAssistActors(WfmFormInfoCtx formVo, ProIns proIns, HumAct humAct, Task parentTask) {
		AssistStrategy actSgy = humAct.getAssistStrategy();
		String users[] = null;
		if (StringUtils.isNotBlank(actSgy.getUsers())) {
			users = actSgy.getUsers().split(",");
		}
		String roles[] = null;
		if (StringUtils.isNotBlank(actSgy.getRoles())) {
			roles = actSgy.getRoles().split(",");
		}
		String depts[] = null;
		if (StringUtils.isNotBlank(actSgy.getDepts())) {
			depts = actSgy.getDepts().split(",");
		}
		Set<String> set = new HashSet<String>();
		String allUsers[][] = new String[4][];
		allUsers[0] = users;
		allUsers[1] = getUsersByRoles(roles);
		allUsers[2] = getUsersByDepts(depts);
		String selfDefClass = actSgy.getSelfDefClass();
		if (StringUtils.isNotBlank(selfDefClass)) {
			allUsers[3] = ((ISelfDefActors) WfmClassUtil.loadClass(selfDefClass)).getActors(formVo, proIns, humAct, parentTask);
		}
		String virtualRole = actSgy.getVirtualRole();
		if (StringUtils.isNotBlank(virtualRole)) {
			try {
				WfmVirtualRoleVO[] vos = NCLocator.getInstance().lookup(IWfmVirtualRoleQry.class).getRoleByPk(new String[] { virtualRole });
				if (vos != null && vos.length != 0) {
					String virRoleClass = vos[0].getServiceclass();
					IVirtualRoleActor virtualRoleActor = (IVirtualRoleActor) WfmClassUtil.loadClass(virRoleClass);
					allUsers[4] = virtualRoleActor.getActors(formVo, proIns, humAct, parentTask);
				}
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
		for (int i = 0; i < allUsers.length; i++) {
			if (allUsers[i] != null && allUsers[i].length != 0) {
				for (int j = 0; j < allUsers[i].length; j++) {
					set.add(allUsers[i][j]);
				}
			}
		}
		String[] userPks = set.toArray(new String[0]);
		if (userPks == null || userPks.length == 0) {
			return null;
		}
		CpUserVO[] userVos = null;
		try {
			userVos = CpbServiceFacility.getCpUserQry().getUserByPkS(userPks);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		if (userVos == null || userVos.length == 0) {
			return null;
		}
		CpUserVO userVo = null;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < userVos.length; i++) {
			userVo = userVos[i];
			if (!humAct.isNotResDept() || (humAct.isNotResDept())) {
				list.add(userVo.getCuserid());
			}
		}
		return list.toArray(new String[0]);
	}
	/**
	 * 根据角色获取用户Pk的集合
	 * 
	 * @param roles
	 * @return
	 */
	public String[] getUsersByRoles(String[] roles) {
		if (roles == null || roles.length == 0) {
			return null;
		}
		try {
			String userPks[] = CpbServiceFacility.getCpUserQry().getUserPksByRoles(roles);
			return userPks;
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	/***
	 * 获取流程根流程实例
	 * 
	 * @param srcProIns
	 * @return
	 * @throws WfmServiceException
	 */
	public ProIns getRootProIns(ProIns srcProIns) throws WfmServiceException {
		if (srcProIns == null) {
			return null;
		}
		IWfmProInsQry proInsQry = NCLocator.getInstance().lookup(IWfmProInsQry.class);
		srcProIns = proInsQry.getProInsByPk(srcProIns.getPk_proins());
		while (srcProIns.getParent() != null) {
			srcProIns = srcProIns.getParent();
			srcProIns = proInsQry.getProInsByPk(srcProIns.getPk_proins());
		}
		return srcProIns;
	}
	public String[] getUsersByDepts(String[] depts) {
		return null;
	}
	public String[] getUsersByGroups(String[] groups) {
		return null;
	}
}
