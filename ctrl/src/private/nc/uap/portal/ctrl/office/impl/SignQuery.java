package nc.uap.portal.ctrl.office.impl;

import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.file.FileManager;
import nc.uap.portal.ctrl.office.data.sign.EkeyUserVo;
import nc.uap.portal.ctrl.office.data.sign.EkeyVO;
import nc.uap.portal.ctrl.office.data.sign.ISignQuery;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;
import nc.uap.portal.ctrl.office.data.sign.UsersignsVO;

public class SignQuery implements ISignQuery {

	@Override
	public ServersignVO[] GetAllServerSigns() throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (ServersignVO[]) dao.queryByCondition(ServersignVO.class, "");
		} catch (DAOException e) {
			throw new LfwBusinessException(e); 
		}
	}

	@Override
	public ServersignVO[] GetAllServerSignsByUser(String pk_user)
			throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String sql = "select a.* from uapcp_serversign a,uapcp_usersigns b where a.pk_sign = b.pk_sign and b.pk_user = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			return(ServersignVO[])dao.executeQuery(sql, parameter, new BeanListProcessor(ServersignVO.class));
			
		} catch (DAOException e) {
			throw new LfwBusinessException(e); 
		}
	}

	@Override
	public ServersignVO GetServerSignByPK(String pk_sign)
			throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (ServersignVO)dao.retrieveByPK(ServersignVO.class, pk_sign);
		} catch (DAOException e) {			
			throw new LfwBusinessException(e);
		}
	}

	public void DeleteServerSign(String pk_sign)  throws LfwBusinessException{
		PtBaseDAO dao = new PtBaseDAO();
		try {
			ServersignVO vo = (ServersignVO) dao.retrieveByPK(ServersignVO.class, pk_sign);
			if(vo != null){
				String whrstr = " pk_sign = '" + pk_sign +"'";			
				dao.deleteByClause(UsersignsVO.class, whrstr);
				dao.deleteVO(vo);
				FileManager.getSystemFileManager().delete(vo.getPk_lfwfile());
			}
		} catch (DAOException e) {			
			throw new LfwBusinessException(e);
		} catch (Exception e) {
			throw new LfwBusinessException(e);
		}
	}
	
	@Override
	public UsersignsVO[] GetAllUsersignBySign(String pk_sign) throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String whrstr = " pk_sign = '" + pk_sign +"'";
		try {
			return (UsersignsVO[])dao.queryByCondition(UsersignsVO.class, whrstr);
		} catch (DAOException e) {			
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public EkeyVO[] GetAllEKey() throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (EkeyVO[]) dao.queryByCondition(EkeyVO.class, "");
		} catch (DAOException e) {
			throw new LfwBusinessException(e); 
		}
	}

	@Override
	public EkeyVO[] GetAllEKeyByUser(String pk_user)
			throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String sql = "select a.* from uapcp_ekey a,uapcp_ekeyuser b where a.pk_ekey = b.pk_ekey and b.pk_user = ?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk_user);
			return(EkeyVO[])dao.executeQuery(sql, parameter, new BeanListProcessor(EkeyVO.class));
			
		} catch (DAOException e) {
			throw new LfwBusinessException(e); 
		}
	}

	@Override
	public EkeyVO GetEkeyByPK(String pk_sign) throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			return (EkeyVO)dao.retrieveByPK(EkeyVO.class, pk_sign);
		} catch (DAOException e) {			
			throw new LfwBusinessException(e);
		}
	}

	@Override
	public EkeyUserVo[] GetAllUserByEkey(String pk_ekey)
			throws LfwBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		String whrstr = " pk_ekey = '" + pk_ekey +"'";
		try {
			return (EkeyUserVo[])dao.queryByCondition(EkeyUserVo.class, whrstr);
		} catch (DAOException e) {			
			throw new LfwBusinessException(e);
		}
	}
}
