package nc.uap.dbl.impl;

import java.util.List;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.dbl.exception.DblServiceException;
import nc.uap.dbl.itf.IFrmCatBill;
import nc.uap.dbl.vo.DblFormCategoryVO;
import nc.uap.lfw.core.log.LfwLogger;

public class FrmCatBill implements IFrmCatBill {
	
	@Override
	public void saveOrUpdate(DblFormCategoryVO frmCatVo) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			String frmCatPk = frmCatVo.getPk_formcategory();
			if (frmCatPk == null || frmCatPk.length() == 0) {
				//PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_ADD, frmCatVo);
				dao.insertVO(frmCatVo);
				//PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_ADD, frmCatVo);
			} else {
				//PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_MODIFY, frmCatVo);
				dao.updateVO(frmCatVo);
				//PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_MODIFY, frmCatVo);
			}
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delFrmCatByPk(String frmCatByPk) throws DblServiceException {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from pdb_formcategory a where a.pk_parent='" + frmCatByPk + "'";
		try {
			//PdblExtentionUtil.notifybeforeAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_DELETE, null);
			List<DblFormCategoryVO> list = (List<DblFormCategoryVO>) dao.executeQuery(sql, new BeanListProcessor(DblFormCategoryVO.class));
			if (list == null || list.size() == 0) {
				sql = "delete from pdb_formcategory  where pk_formcategory='" + frmCatByPk + "'";
				dao.executeUpdate(sql);
			} else {
				throw new DblServiceException("该节点下面有子节点，不允许删除");
			}
			//PdblExtentionUtil.notifyAfterAction(IPdblExtentionService.POINTID, IPdblExtentionService.FRMCATMGR_DELETE, null);
		} catch (DAOException e) {
			Logger.error(e.getMessage(), e);
			throw new DblServiceException(e.getMessage(), e);
		}
	}
	@Override
	public void delFrmCatByGroupPk(String[] groupPks) throws DblServiceException {
		if(groupPks==null||groupPks.length==0){
			return;
		}
		 StringBuffer buffer=new StringBuffer();
		 for(int i=0;i<groupPks.length;i++){
			 buffer.append("'").append(groupPks[i]).append(",");
		 }
		 if(buffer.length()>0){
			 buffer.setLength(buffer.length()-1);
		 }
		
        PtBaseDAO dao=new PtBaseDAO();
        try {
        	 String sql="";
    		 sql="delete  from pdb_formtemplate  where pk_formdefinition in ("
    				 +"select pk_formdefinition from pdb_formdefinition a where a.pk_formcategory "
    				 +"in (select a.pk_formcategory from pdb_formcategory a where a.pk_group in ("+buffer.toString()+")))";
			dao.executeUpdate(sql);
			sql="delete from pdb_formdefinition  where pk_formcategory"
				  +"in (select a.pk_formcategory from pdb_formcategory a where a.pk_group in ("+buffer.toString()+"))";
			dao.executeUpdate(sql);
			sql="delete  from pdb_formcategory  where pk_group in ("+buffer.toString()+")";
			dao.executeUpdate(sql);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new DblServiceException(e);
		}
		
	

	
		
		
	}
}
