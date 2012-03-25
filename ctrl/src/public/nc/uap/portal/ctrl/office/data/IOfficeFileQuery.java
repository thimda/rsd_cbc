package nc.uap.portal.ctrl.office.data;

import java.util.List;

import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;



/**
 * 模板  查询接口
 * @author lisw
 *
 */
public interface IOfficeFileQuery {
	public  List<OfficeFileVO> queryByCondition(String filetype,String doctype);
	public OfficeFileVO queryByPK(String pk) throws LfwRuntimeException;
	public String insertData(OfficeFileVO file) throws LfwRuntimeException;
	public void deleteBypks(String[] pks)  throws LfwRuntimeException;
	public void deleteBypk(String pk)  throws LfwRuntimeException;
	
	public String insertOfficeUserData(OfficeFileUserVO file) throws LfwRuntimeException;
	public List<OfficeFileUserVO> queryOfficeUserByfilepk(String filePK) throws LfwRuntimeException;
	public OfficeFileUserVO queryOfficeUserBypk(String pk) throws LfwRuntimeException;
}
