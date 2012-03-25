package nc.uap.portal.ctrl.office.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONSerializer;
import org.json.MarshallException;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Param;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.constant.Keys;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.LfwJsonSerializer;
import nc.uap.portal.ctrl.office.core.DefaultBookMarkLoader;
import nc.uap.portal.ctrl.office.core.IBookMarkLoader;
import nc.uap.portal.ctrl.office.core.OfficeBookMarkVO;
import nc.uap.portal.ctrl.office.core.OfficeTempeteHelper;
import nc.uap.portal.ctrl.office.core.OfficeTempleteFileVo;
import nc.uap.portal.ctrl.office.data.IOfficeFileQuery;

/*
 * office ²Ù×÷Àà
 * @author lisw
 */
@Servlet(path = "/office")
public class OfficeAction extends BaseAction  {
	@Action(url = "/loadTemplete")
	public void loadTemplete(@Param(name = "filetype") String filetype) throws IOException {
		PrintWriter out = this.response.getWriter();
		try
		{
			List<OfficeTempleteFileVo> filelist = OfficeTempeteHelper.GetTempleteFile(filetype,"0");
			if(filelist != null)
			{
				String obj = LfwJsonSerializer.getInstance().toJSON(filelist);
				out.print(obj);
			}
		}
		catch(Exception ex)
		{
			LfwLogger.error(ex);
			String error = "{err:"+ex.getMessage()+"}";
			out.print(error);
		}
	}
	@Action(url = "/loadbookmark")
	public void LoadBookMark(@Param(name = "classname") String classname) throws IOException
	{
		IBookMarkLoader iloader = null;
		if(classname != null && classname != "")
		{
			Class cla = null;
			try {
				cla = Class.forName(classname);
			} catch (ClassNotFoundException e) {
				LfwLogger.error(e);
			}
			if(cla == null)
				cla = DefaultBookMarkLoader.class;
			if(cla != null)
				try {
					iloader = (IBookMarkLoader)cla.newInstance();
				} catch (InstantiationException e) {
					LfwLogger.error(e);
				} catch (IllegalAccessException e) {
					LfwLogger.error(e);
				}
		}
		if(iloader == null)
			iloader = new DefaultBookMarkLoader();

		List<OfficeBookMarkVO> list = iloader.LoadBookMarks();
		
		PrintWriter out;
		
		out = this.response.getWriter();
		try {
			if(list != null)
			{
				String obj;
				obj = LfwJsonSerializer.getInstance().toJSON(list);
				out.print(obj);
			}
		} catch (MarshallException e) {
			LfwLogger.error(e);
			String error = "{err:"+e.getMessage()+"}";
			out.print(error);
		}
	}

	@Action(url = "/getnewpk")
	public void getNewPK() throws IOException {
		String datasource =  nc.uap.lfw.core.LfwRuntimeEnvironment.getDatasource();
		String  pk = PtBaseDAO.generatePK(datasource); 
		PrintWriter out = this.response.getWriter();
		out.print(pk);
	}
	@Action(url = "/deletesign")
	public void deletesign(@Param(name = "pk") String pk) throws IOException{
		IOfficeFileQuery qry = NCLocator.getInstance().lookup(IOfficeFileQuery.class);
		PrintWriter out = this.response.getWriter();
		try{
			qry.deleteBypk(pk);
			out.print("success");
		}
		catch(LfwRuntimeException exp){
			LfwLogger.error(exp);
			out.print(exp);
		}
	}
}