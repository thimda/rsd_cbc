package nc.uap.portal.ctrl.office.action;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.file.FileManager;
import nc.uap.lfw.file.IFileUploadExtender;
import nc.uap.lfw.file.itf.ILfwFileQryService;
import nc.uap.lfw.file.vo.LfwFileVO;
import nc.uap.portal.ctrl.office.core.OfficeFileConstStr;
import nc.uap.portal.ctrl.office.data.IOfficeFileQuery;
import nc.uap.portal.ctrl.office.data.OfficeFileUserVO;
import nc.uap.portal.ctrl.office.data.OfficeFileVO;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;
import nc.vo.pub.lang.UFDateTime;

public class SignUploadExtender implements IFileUploadExtender {

	private String[] rets = null;
	@Override
	public void extend(MultipartHttpServletRequest req, String filepk)
			throws LfwRuntimeException {
		ILfwFileQryService lfwfileqry = NCLocator.getInstance().lookup(ILfwFileQryService.class);		
		try {
			LfwFileVO lfwfile =  lfwfileqry.getFile(filepk);
			String signname = req.getParameter("formsignname");
			String usercode = req.getParameter("formusercode");
			String billitem = req.getParameter("billitem");
			String signno = req.getParameter("formsignno");
			String modify = LfwRuntimeEnvironment.getLfwSessionBean().getUser_code();
			
			ICpSuperVOBill supqry = NCLocator.getInstance().lookup(ICpSuperVOBill.class);
			
			//创建印章
			ServersignVO serversignvo = new ServersignVO();
			serversignvo.setName(signname);
			serversignvo.setCreateby(modify);
			serversignvo.setCreatetime(new UFDateTime());
			serversignvo.setModifertime(new UFDateTime());
			serversignvo.setModifier(modify);
			serversignvo.setNo(signno);
			serversignvo.setPk_lfwfile(filepk);
			if(billitem != null && !billitem.equals("")){
				serversignvo.setPk_sign(billitem);
			}
			String pk_sign =  supqry.mergeSuperVO(serversignvo);
			
			//修改文件保存状态
			FileManager.getSystemFileManager().billSaveComplete(billitem);
			
			rets = new String[]{filepk,pk_sign,usercode};
			
			
		} catch (LfwBusinessException e) {
			LfwLogger.error(e);
			throw new LfwRuntimeException(e);
		}
	}

	@Override
	public String[] getRetValues() {		
		return rets;
	}

}
