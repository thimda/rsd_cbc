package nc.uap.portal.ctrl.office.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.file.FileManager;
import nc.uap.portal.ctrl.office.data.IOfficeFileQuery;
import nc.uap.portal.ctrl.office.data.OfficeFileUserVO;
import nc.uap.portal.ctrl.office.data.OfficeFileVO;


public class OfficeFileQuery implements IOfficeFileQuery{

	@Override
	public List<OfficeFileVO> queryByCondition(String filetype,String doctype) {
		
		List<OfficeFileVO> files= new ArrayList<OfficeFileVO>();
		PtBaseDAO dao = new PtBaseDAO();
		String condition = "";
		if(filetype != null && !filetype.equals("")){
			condition = "filetype = '" + filetype + "'";
		}
		if(doctype != null && !doctype.equals("")){
			if(!condition.equals("")){
				condition += " and ";
			}
			condition += " doctype = '" + doctype + "'";
		}
		try {
			@SuppressWarnings("unchecked")
			Collection filecol = dao.retrieveByClause(OfficeFileVO.class, condition);
			if(null != filecol){
				for(Object  obj : filecol){
					files.add((OfficeFileVO)obj);
				}
			}
		} catch (DAOException e) {
			LfwLogger.error("select OfficeFileVO error");
			LfwLogger.error(e);
		}		
		return files;
	}

	@Override
	public String insertData(OfficeFileVO file) throws LfwRuntimeException {
		PtBaseDAO dao = new PtBaseDAO();
		String pk = "";
		try {
				String pk_file = file.getPk_file();
			if(pk_file == null || pk_file.equals(""))				
				pk = dao.insertVO(file);
			else
			{
				OfficeFileVO tempfile =   queryByPK(pk_file);
				if(null != tempfile){
					pk = pk_file;
					dao.updateVO(file);
				}
				else
					pk = dao.insertVOWithPK(file);
			}
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e);
		}
		return pk;
	}

	@Override
	public OfficeFileVO queryByPK(String pk) throws LfwRuntimeException {
		PtBaseDAO dao = new PtBaseDAO();
		OfficeFileVO file;
		try {
			file = (OfficeFileVO)dao.retrieveByPK(OfficeFileVO.class, pk);
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e);
		}
		return file;
	}

	@Override
	public void deleteBypks(String[] pks) throws LfwRuntimeException {
		if(null != pks){
			PtBaseDAO dao = new PtBaseDAO();
			FileManager manager = new FileManager();
			for(String pk : pks){
				OfficeFileVO vo = queryByPK(pk);
				if(null != vo){
					try {						
						manager.delete(vo.getFileurl());
						dao.deleteByPK(OfficeFileVO.class, pk);				
					} catch (DAOException e) {
						LfwLogger.error(e);
						throw new LfwRuntimeException("delete OfficeFileVO error," + e.getMessage(),e);
					} catch (Exception e) {
						LfwLogger.error(e);
						throw new LfwRuntimeException("delete OfficeFileVO error," + e.getMessage(),e);
					}
				}
			}
		}		
	}

	@Override
	public void deleteBypk(String pk)  throws LfwRuntimeException {
		OfficeFileVO vo = queryByPK(pk);
		if(null != vo){
			PtBaseDAO dao = new PtBaseDAO();
			try {
				FileManager manager = new FileManager();
				manager.delete(vo.getFileurl());
				dao.deleteByPK(OfficeFileVO.class, pk);				
			} catch (DAOException e) {
				LfwLogger.error(e);
				throw new LfwRuntimeException("delete OfficeFileVO error," + e.getMessage(),e);
			} catch (Exception e) {
				LfwLogger.error(e);
				throw new LfwRuntimeException("delete OfficeFileVO error," + e.getMessage(),e);
			}
		}
	}

	@Override
	public String insertOfficeUserData(OfficeFileUserVO file)
			throws LfwRuntimeException {
		PtBaseDAO dao = new PtBaseDAO();
		String pk = "";
		try {
			String pk_fileuser = file.getPk_fileuser();
			if(pk_fileuser == null || pk_fileuser.equals(""))
			{
				pk = dao.insertVO(file);				
			}			
			else
			{
				
				OfficeFileUserVO tempfile =   queryOfficeUserBypk(pk_fileuser);
				if(null != tempfile){
					pk = pk_fileuser;
					dao.updateVO(file);
				}
				else
					pk = dao.insertVOWithPK(file);
			}
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e);
		}
		return pk;
	}

	@Override
	public List<OfficeFileUserVO> queryOfficeUserByfilepk(String filePK)
			throws LfwRuntimeException {
		String condition = "pk_file ='" + filePK + "'";
		List<OfficeFileUserVO> files = new ArrayList<OfficeFileUserVO>();
		PtBaseDAO dao = new PtBaseDAO();
		try {
			@SuppressWarnings("unchecked")
			Collection filecol = dao.retrieveByClause(OfficeFileUserVO.class, condition);
			if(null != filecol){
				for(Object  obj : filecol){
					files.add((OfficeFileUserVO)obj);
				}
			}
		} catch (DAOException e) {
			LfwLogger.error("select OfficeFileVO error");
			LfwLogger.error(e);
		}		
		return files;		
	}

	@Override
	public OfficeFileUserVO queryOfficeUserBypk(String pk)
			throws LfwRuntimeException {
		PtBaseDAO dao = new PtBaseDAO();
		OfficeFileUserVO file;
		try {
			file = (OfficeFileUserVO)dao.retrieveByPK(OfficeFileUserVO.class, pk);
		} catch (DAOException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e);
		}
		return file;
	}
}
