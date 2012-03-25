package nc.uap.portal.ctrl.office.data;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.sign.EkeyUserVo;
import nc.uap.portal.ctrl.office.data.sign.EkeyVO;
import nc.uap.portal.ctrl.office.data.sign.ISignQuery;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;
import nc.uap.portal.ctrl.office.data.sign.SignlogVO;
import nc.uap.portal.ctrl.office.data.sign.UsersignsVO;

public class SignHelper {
	//印章类
	/**
	 * 获取所有服务器印章 
	 * @return
	 * @throws LfwBusinessException 
	 */
	public static ServersignVO[] GetAllServersignVO() throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetAllServerSigns();
	}
	
	/**
	 * 获取指定用户所有印章
	 * @param pk_user
	 * @return
	 * @throws LfwBusinessException
	 */
	public static ServersignVO[] GetAllServersignVOByUser(String pk_user) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetAllServerSignsByUser(pk_user);
	}
	/**
	 * 
	 * @param pk_sign
	 * @return
	 * @throws LfwBusinessException
	 */
	public static ServersignVO GetServerSignByPK(String pk_sign) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetServerSignByPK(pk_sign);
	}
	public static void DeleteServerSign(String pk_sign) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		qry.DeleteServerSign(pk_sign);
	}
	/**
	 * 获取当前印章所有绑定用户
	 * @param pk_sign
	 * @return
	 * @throws LfwBusinessException
	 */
	public static UsersignsVO[] GetAllSignusers(String pk_sign) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetAllUsersignBySign(pk_sign);
	} 
	

	//EKEY类操作
	/**
	 * 获取全部EKey VO
	 * @return
	 * @throws LfwBusinessException
	 */
	public static EkeyVO[] GetAllEkeyVO() throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetAllEKey();
	}
	public static EkeyVO GetEkeyByPK(String pk_ekey) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetEkeyByPK(pk_ekey);
	}
	
	/**
	 * 根据用户获取全部EKey
	 * @param pk_user
	 * @return
	 * @throws LfwBusinessException
	 */
	public static EkeyVO[] GetAllEkeyVOByUser(String pk_user) throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class); 
		return qry.GetAllEKeyByUser(pk_user);
	}
	
	public static CpUserVO GetUserByEkey(String pk_ekey)throws LfwBusinessException{
		ISignQuery qry = NCLocator.getInstance().lookup(ISignQuery.class);
		EkeyUserVo[] ekeyusers = qry.GetAllUserByEkey(pk_ekey);
		if(null != ekeyusers && ekeyusers.length > 0){
			ICpUserQry userqry = NCLocator.getInstance().lookup(ICpUserQry.class);
			return userqry.getUserByPk(ekeyusers[0].getPk_user());
		}
		else
			return null;
	}
	
}
