package nc.uap.cpb.org.pubview.mode;

import java.util.HashMap;
import java.util.Map;

import nc.itf.org.IOrgConst;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.vo.org.OrgVO;

public class ModeOrgHelper {
	public static String ModeFilter_AttID = "ModeOrg_Filter_Att";
	public static String Mode_MainOrg_AppID = "Mode_MainOrg"; 
	/**
	 * 
	 * @param curuserpk 用户
	 * @param curGrouppk 集团
	 * @param filter
	 * @return
	 * @throws LfwBusinessException 
	 */
	public static OrgVO[] GetOrg(String curuserpk,String curGrouppk,ModeOrgFilter filter) throws LfwBusinessException{
		OrgVO[] orgs = null;
		String wheresql = "( 1=1 ";
		if(curGrouppk != null && !curGrouppk.equals(""))
			wheresql += " and pk_group = '" + curGrouppk + "' " ;
		//集团管理员是组织合集,不做权限过滤
		if(!filter.isIsgrpadmin()){
			if(filter.isIsfilterSecurity()){
				wheresql += " and pk_org in ( select distinct pk_org from cp_roleorg  a left join cp_userrole b on a.pk_role = b.pk_role where b.pk_user = '"+ curuserpk +"') ";
			}
		}
		wheresql += " ) ";
		if(filter.isIsneedGlobal()){
			wheresql += " or pk_group ='" + IOrgConst.GLOBEORG +"'";
		}
		//全部组织,//权限过滤		
		//集团过滤
		PaginationInfo pinfo = new PaginationInfo();
		Map<String, Object> extMap = new HashMap<String, Object>();
		
		orgs =CRUDHelper.getCRUDService().queryVOs(wheresql, OrgVO.class,pinfo, "",extMap);
		
		return orgs;
	}	
}
