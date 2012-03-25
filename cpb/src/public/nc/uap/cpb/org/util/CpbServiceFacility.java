package nc.uap.cpb.org.util;
import nc.bs.framework.common.NCLocator;
import nc.itf.org.ICorpQryService;
import nc.itf.org.IDeptQryService;
import nc.itf.org.IGroupQryService;
import nc.itf.org.IOrgUnitQryService;
import nc.itf.org.orgmodel.IUserAdminGroupService;
import nc.uap.cpb.org.itf.ICpAppsNodeBill;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.itf.ICpMenuBill;
import nc.uap.cpb.org.itf.ICpMenuQry;
import nc.uap.cpb.org.itf.ICpOrgQry;
import nc.uap.cpb.org.itf.ICpPermissionQry;
import nc.uap.cpb.org.itf.ICpPluginBill;
import nc.uap.cpb.org.itf.ICpPluginQry;
import nc.uap.cpb.org.itf.ICpResourceBill;
import nc.uap.cpb.org.itf.ICpResourceQry;
import nc.uap.cpb.org.itf.ICpResponsibilityBill;
import nc.uap.cpb.org.itf.ICpResponsibilityQry;
import nc.uap.cpb.org.itf.ICpRoleBill;
import nc.uap.cpb.org.itf.ICpRoleGroupBill;
import nc.uap.cpb.org.itf.ICpRoleGroupQry;
import nc.uap.cpb.org.itf.ICpRoleQry;
import nc.uap.cpb.org.itf.ICpRoleResourceBill;
import nc.uap.cpb.org.itf.ICpRoleResourceQry;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.cpb.org.itf.ICpSuperVOQry;
import nc.uap.cpb.org.itf.ICpUserBill;
import nc.uap.cpb.org.itf.ICpUserGroupBill;
import nc.uap.cpb.org.itf.ICpUserGroupQry;
import nc.uap.cpb.org.itf.ICpUserGroupRoleBill;
import nc.uap.cpb.org.itf.ICpUserGroupRoleQry;
import nc.uap.cpb.org.itf.ICpUserGroupUserBill;
import nc.uap.cpb.org.itf.ICpUserGroupUserQry;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.itf.ICpUserRoleBill;
import nc.uap.cpb.org.itf.ICpUserRoleQry;
/**
 * 
 * 2010-11-9 ����10:25:54
 * 
 * @author
 */
public class CpbServiceFacility {
	/**
	 * ��ȡ���Ź���Ա�������Ų�������ӿ�
	 * 
	 * @return
	 */
	public static IUserAdminGroupService getCpGrpAdminQry() {
		return (IUserAdminGroupService) NCLocator.getInstance().lookup(IUserAdminGroupService.class.getName());
	}
	/**
	 * ��ȡ���Ų�ѯ����ӿ�
	 * 
	 * @return
	 */
	public static IGroupQryService getCpGroupQry() {
		return (IGroupQryService) NCLocator.getInstance().lookup(IGroupQryService.class);
	}
	/**
	 * ��ȡ��֯��ѯ����ӿ�
	 * 
	 * @return
	 */
	public static IOrgUnitQryService getCpOrgQry() {
		return (IOrgUnitQryService) NCLocator.getInstance().lookup(IOrgUnitQryService.class.getName());
	}
	/**
	 * ��ȡ��֯���ղ�ѯ����ӿ�
	 * 
	 * @return
	 */
	public static ICpOrgQry getCpOrgRefefenceQry() {
		return NCLocator.getInstance().lookup(ICpOrgQry.class);
	}
	/**
	 * ��ȡ��˾��ѯ����ӿ�
	 * 
	 * @return
	 */
	public static ICorpQryService getCpCorpQry() {
		return (ICorpQryService) NCLocator.getInstance().lookup(ICorpQryService.class.getName());
	}
	/**
	 * ��ȡ���ŷ���ӿ�
	 * 
	 * @return
	 */
	public static IDeptQryService getCpDeptQry() {
		return (IDeptQryService) NCLocator.getInstance().lookup(IDeptQryService.class.getName());
	}
	
	/**
	 * ��ȡ�û���������ӿ�
	 * 
	 * @return
	 */
	public static ICpUserQry getCpUserQry() {
		return (ICpUserQry) NCLocator.getInstance().lookup(ICpUserQry.class.getName());
	}
	public static ICpUserBill getCpUserBill() {
		return (ICpUserBill) NCLocator.getInstance().lookup(ICpUserBill.class.getName());
	}
	/**
	 * ��ȡ�û����������ӿ�
	 * 
	 * @return
	 */
	public static ICpUserGroupBill getCpUserGroupBill() {
		return (ICpUserGroupBill) NCLocator.getInstance().lookup(ICpUserGroupBill.class.getName());
	}
	public static ICpUserGroupQry getCpUserGroupQry() {
		return (ICpUserGroupQry) NCLocator.getInstance().lookup(ICpUserGroupQry.class.getName());
	}
	/**
	 * ��ȡ��ɫ��������ӿ�
	 * 
	 * @return
	 */
	public static ICpRoleBill getCpRoleBill() {
		return (ICpRoleBill) NCLocator.getInstance().lookup(ICpRoleBill.class.getName());
	}
	public static ICpRoleQry getCpRoleQry() {
		return (ICpRoleQry) NCLocator.getInstance().lookup(ICpRoleQry.class.getName());
	}
	/**
	 * ��ȡְ���������ӿ�
	 * 
	 * @return
	 */
	public static ICpResponsibilityBill getCpResponsibilityBill() {
		return (ICpResponsibilityBill) NCLocator.getInstance().lookup(ICpResponsibilityBill.class.getName());
	}
	public static ICpResponsibilityQry getCpResponsibilityQry() {
		return (ICpResponsibilityQry) NCLocator.getInstance().lookup(ICpResponsibilityQry.class.getName());
	}
	/**
	 * ��ȡ��ɫ���������ӿ�
	 * 
	 * @return
	 */
	public static ICpRoleGroupBill getCpRoleGroupBill() {
		return (ICpRoleGroupBill) NCLocator.getInstance().lookup(ICpRoleGroupBill.class.getName());
	}
	public static ICpRoleGroupQry getCpRoleGroupQry() {
		return (ICpRoleGroupQry) NCLocator.getInstance().lookup(ICpRoleGroupQry.class.getName());
	}
	/**
	 * ��ȡ�û���ɫ����ӿ�
	 * 
	 * @return
	 */
	public static ICpUserRoleQry getCpUserRoleQry() {
		return (ICpUserRoleQry) NCLocator.getInstance().lookup(ICpUserRoleQry.class.getName());
	}
	public static ICpUserRoleBill getCpUserRoleBill() {
		return (ICpUserRoleBill) NCLocator.getInstance().lookup(ICpUserRoleBill.class.getName());
	}
	/**
	 * ��ȡ�û����ɫ�����ӿ�
	 * 
	 * @return
	 */
	public static ICpUserGroupRoleBill getCpUserGroupRoleBill() {
		return (ICpUserGroupRoleBill) NCLocator.getInstance().lookup(ICpUserGroupRoleBill.class.getName());
	}
	public static ICpUserGroupRoleQry getCpUserGroupRoleQry() {
		return (ICpUserGroupRoleQry) NCLocator.getInstance().lookup(ICpUserGroupRoleQry.class.getName());
	}
	/**
	 * ��ȡ�û����û������ӿ�
	 * 
	 * @return
	 */
	public static ICpUserGroupUserBill getCpUserGroupUserBill() {
		return (ICpUserGroupUserBill) NCLocator.getInstance().lookup(ICpUserGroupUserBill.class.getName());
	}
	public static ICpUserGroupUserQry getCpUserGroupUserQry() {
		return (ICpUserGroupUserQry) NCLocator.getInstance().lookup(ICpUserGroupUserQry.class.getName());
	}
	/**
	 * ��ȡ��Դ����ӿ�
	 * 
	 * @return
	 */
	public static ICpResourceQry getCpResourceQry() {
		return (ICpResourceQry) NCLocator.getInstance().lookup(ICpResourceQry.class.getName());
	}
	public static ICpResourceBill getCpResourceBill() {
		return (ICpResourceBill) NCLocator.getInstance().lookup(ICpResourceBill.class.getName());
	}
	/**
	 * ��ȡ��Դ��ɫ����ӿ�
	 * 
	 * @return
	 */
	public static ICpRoleResourceQry getCpRoleResourceQry() {
		return (ICpRoleResourceQry) NCLocator.getInstance().lookup(ICpRoleResourceQry.class.getName());
	}
	public static ICpRoleResourceBill getCpRoleResourceBill() {
		return (ICpRoleResourceBill) NCLocator.getInstance().lookup(ICpRoleResourceBill.class.getName());
	}
	/**
	 * ��ȡsupervo�����ӿ�
	 * 
	 * @return
	 */
	public static ICpSuperVOBill getCpSuperVOBill() {
		return (ICpSuperVOBill) NCLocator.getInstance().lookup(ICpSuperVOBill.class.getName());
	}
	public static ICpSuperVOQry getCpSuperVOQry() {
		return (ICpSuperVOQry) NCLocator.getInstance().lookup(ICpSuperVOQry.class.getName());
	}
	/**
	 * ��ù��ܽڵ��������
	 * 
	 * @return
	 */
	 public static ICpAppsNodeBill getCpAppsNodeBill() {
		 return NCLocator.getInstance().lookup(ICpAppsNodeBill.class);
	 }
	public static ICpAppsNodeQry getPortalManagerAppService() {
		return (ICpAppsNodeQry) NCLocator.getInstance().lookup(ICpAppsNodeQry.class.getName());
	}
	
	public static ICpMenuQry getMenuQry() {
		return NCLocator.getInstance().lookup(ICpMenuQry.class);
	}
	public static ICpMenuBill getMenuService() {
		return NCLocator.getInstance().lookup(ICpMenuBill.class);
	}
	/**
	 * ��ò����ѯ����
	 * @return
	 */
	public static ICpPluginQry getPluginQryService(){
		return NCLocator.getInstance().lookup(ICpPluginQry.class);
	}
	/**
	 * ��ò������
	 * @return
	 */
	public static ICpPluginBill getPluginService(){
		return NCLocator.getInstance().lookup(ICpPluginBill.class);
	}
	/**
	 * ��ȡȨ�޲�ѯ����
	 * @return
	 */
	public static ICpPermissionQry getCpPermissionQryService(){
		return NCLocator.getInstance().lookup(ICpPermissionQry.class);
	}
}