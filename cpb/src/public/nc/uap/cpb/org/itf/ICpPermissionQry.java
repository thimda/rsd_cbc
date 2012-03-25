package nc.uap.cpb.org.itf;

import java.util.List;
import java.util.Map;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.core.data.PaginationInfo;

public interface ICpPermissionQry { 
	public List<Map<String, Object>> getPermissionsByRole(String[] pk_roles,PaginationInfo pinfo) throws CpbBusinessException;
	public List<Map<String, Object>> getPermissionsByUser(String[] pk_users,PaginationInfo pg) throws CpbBusinessException;
	public List<Map<String, Object>> getPermissionsByFunc(String[] pk_funcs,PaginationInfo pg) throws CpbBusinessException;
	
}
