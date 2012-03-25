package nc.uap.cpb.org.itf;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpUserGroupUserVO;
public interface ICpUserGroupUserQry {
	CpUserGroupUserVO[] getUserGroupUserVosByUserPk(String userPk) throws CpbBusinessException;
}
