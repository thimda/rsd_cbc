package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserRoleVO;
public interface ICpUserRoleQry {
	public CpUserRoleVO[] getPtRoleUserByPkuser(String pk_user) throws CpbBusinessException;
	public CpUserRoleVO[] getPtRoleUserByPkRole(String pk_role) throws CpbBusinessException;
}
