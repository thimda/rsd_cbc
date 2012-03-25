package nc.uap.cpb.org.extention;


/**
 * 组织管理的扩展点
 *
 */
public interface ICpbExtentionService {

	//组织管理扩展点
	public static final String UAPCPMANAGE = "uapcpmanage";
	// 组织管理插件
	//集团
	public static final String GROUPMANAGE = "groupmanage";
	
	//部门
	public static final String  DEPTMANAGE = "deptmanage";
	
	//角色
	public static final String ROLEMANAGE = "rolemanage";
	
	//角色组
	public static final String ROLEGROUPMANAGE = "rolegroupmanage";
	
	//用户组
	public static final String USERGROUPMANAGE = "usergroupmanage";
	
	//用户
	public static final String USERMANAGE = "usermanage";
	
	//actiontype的类型
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String DELETE = "delete";
	
	//用户管理动作类型
	/**用户关联角色*/
	public static final String USER_RELATE_ROLE = "user_relate_role";
	/**用户删除关联角色*/
	public static final String USER_DELETE_ROLE = "user_delete_role";
	
	//角色管理动作类型
	/**角色关联用户*/
	public static final String ROLE_RELATE_USER = "role_relate_user";
	/**角色删除关联用户*/
	public static final String ROLE_DELETE_USER = "role_delete_user";
	
	//用户组动作类型
	/**用户组关联角色*/
	public static final String USERGROUP_RELATE_ROLE = "usergroup_relate_role";
	/**用户组删除关联角色*/
	public static final String USERGROUP_DELETE_ROLE = "usergroup_delete_role";
	
	/**部门关联角色*/
	public static final String DEPT_RELATE_ROLE = "dept_relate_role";
	/**部门删除关联角色*/
	public static final String DEPT_DELETE_ROLE = "dept_delete_role";

	/**
	 *和业务关联的逻辑后处理扩展逻辑
	 * @param functionType：业务类型：如集团、组织、角色
	 * @param actionType：动作类型：如增加、删除、修改
	 */
	public void afterAction(String functionType, String actionType, Object object);
	
	
	/**
	 * 和业务关联的逻辑前处理逻辑
	 * @param functionType：业务类型：如集团、组织、角色
	 * @param actionType：动作类型：如增加、删除、修改
	 * @param object：业务关联本身的对象
	 */
	public void beforeAction(String functionType, String actionType, Object object);
	
	/**
	 * 可以接受的业务类型
	 * @return
	 */
	public String acceptFunType();
	
}
