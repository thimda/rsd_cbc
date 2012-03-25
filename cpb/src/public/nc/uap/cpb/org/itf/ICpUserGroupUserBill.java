package nc.uap.cpb.org.itf;

import nc.uap.cpb.org.exception.CpbBusinessException;

public interface ICpUserGroupUserBill {
	void deleteUserGroupUserByUserGroupUserPk(String userGroupUserPk) throws CpbBusinessException;
}
