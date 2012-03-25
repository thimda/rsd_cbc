package nc.uap.cpb.org.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.page.LimitSQLBuilder;
import nc.jdbc.framework.page.SQLBuilderFactory;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpPermissionQry;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.log.LfwLogger;

public class CpPermissionQry implements ICpPermissionQry {

	@Override
	public List<Map<String, Object>> getPermissionsByRole(String[] pk_roles,PaginationInfo pg) throws CpbBusinessException {
		if(pk_roles==null||pk_roles.length<1){
			return new ArrayList<Map<String, Object>>();
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<pk_roles.length;i++){
			sb.append("'").append(pk_roles[i]).append("'");
			if(i!=pk_roles.length-1)
				sb.append(","); 
		}
//		String sql = "select aa.rolecode  rolecode,aa.rolename rolename,cc.code respcode,cc.name respname,ee.id funccode,ee.title funcname,ee.type functype " +
//				"from cp_role aa,cp_roleresp bb, cp_responsibility cc ,cp_resp_func dd,cp_appsnode ee " +
//				"where aa.pk_role in("+sb.toString()+") and aa.pk_role=bb.pk_role and bb.pk_responsibility=cc.pk_responsibility " +
//				" and cc.pk_responsibility=dd.pk_responsibility and dd.busi_pk=ee.pk_appsnode";
		String sql = "select aa.rolecode rolecode,aa.rolename rolename,cc.code respcode,cc.name respname,ee.id funccode,ee.title funcname,ee.type functype " +
				" from cp_role aa left join cp_roleresp bb  on (aa.pk_role=bb.pk_role) left join cp_responsibility cc on(bb.pk_responsibility=cc.pk_responsibility )" +
				" left join cp_resp_func dd on(cc.pk_responsibility=dd.pk_responsibility) left join cp_appsnode ee on (dd.busi_pk=ee.pk_appsnode) " +
				" where aa.pk_role in("+sb.toString()+")";
		return getPermissionsBySql(sql,pg);
	}
	
	@Override
	public List<Map<String, Object>> getPermissionsByFunc(String[] pk_funcs,PaginationInfo pg)
			throws CpbBusinessException {
		if(pk_funcs==null||pk_funcs.length<1){
			return new ArrayList<Map<String, Object>>();
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<pk_funcs.length;i++){
			sb.append("'").append(pk_funcs[i]).append("'");
			if(i!=pk_funcs.length-1)
				sb.append(","); 
		}
		String sql = "select aa.id funccode,aa.title funcname,aa.type functype,cc.code respcode,cc.name respname,ee.rolecode  rolecode,ee.rolename rolename" +
				" from cp_appsnode aa left join cp_resp_func bb  on (aa.pk_appsnode=bb.busi_pk) left join cp_responsibility cc on(bb.pk_responsibility=cc.pk_responsibility )" +
				" left join cp_roleresp dd on(cc.pk_responsibility=dd.pk_responsibility) left join cp_role ee on (dd.pk_role=ee.pk_role) " +
				" where aa.pk_appsnode in("+sb.toString()+")";
		return getPermissionsBySql(sql,pg);
	}

	@Override
	public List<Map<String, Object>> getPermissionsByUser(String[] pk_users,PaginationInfo pg)
			throws CpbBusinessException {
		if(pk_users==null||pk_users.length<1){
			return new ArrayList<Map<String, Object>>();
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<pk_users.length;i++){
			sb.append("'").append(pk_users[i]).append("'");
			if(i!=pk_users.length-1)
				sb.append(","); 
		}
		String sql = "select uu.user_code usercode,uu.user_name username, aa.rolecode rolecode,aa.rolename rolename,cc.code respcode,cc.name respname," +
				" ee.id funccode,ee.title funcname,ee.type functype from cp_user uu left join cp_userrole ur on(uu.cuserid=ur.pk_user) left join" +
				" cp_role aa on (ur.pk_role=aa.pk_role) left join cp_roleresp bb on (aa.pk_role=bb.pk_role) left join cp_responsibility cc " +
				" on (bb.pk_responsibility=cc.pk_responsibility) left join cp_resp_func dd on (cc.pk_responsibility=dd.pk_responsibility) left join" +
				" cp_appsnode ee on (dd.busi_pk=ee.pk_appsnode) where uu.cuserid in("+sb.toString()+")";
		return getPermissionsBySql(sql,pg);
	}
	
	private List<Map<String, Object>> getPermissionsBySql(String sql,PaginationInfo pg) throws CpbBusinessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		 
		 PersistenceManager pm = null;
		 try {		 
			 pm = PersistenceManager.getInstance();		
			 JdbcSession ses = pm.getJdbcSession();
			 if(pg == null || pg.getPageSize() == -1){
					list = (List<Map<String, Object>>) ses.executeQuery(sql, new MapListProcessor());
			 }
			else{
				int index = pg.getPageIndex();
				int pageSize = pg.getPageSize();
				LimitSQLBuilder builder = SQLBuilderFactory.getInstance().createLimitSQLBuilder(pm.getDBType());
				String countSql = "select count(1) as c from (" + sql + ") as allcount";
				sql = builder.build(sql, index + 1, pageSize);
				Map obj = (Map) ses.executeQuery(countSql, new MapProcessor());
				list = (List<Map<String, Object>>) ses.executeQuery(sql, new MapListProcessor());
				pg.setRecordsCount((Integer)obj.get("c"));
			}
		 } catch (DbException e) {
			    LfwLogger.error(e.getMessage(), e.getCause());
				throw new CpbBusinessException(e);
			}
		 finally {
				if (pm != null)
					pm.release();
			}
		if(list==null)
			return new ArrayList<Map<String, Object>>();
		return list;
	}
}
