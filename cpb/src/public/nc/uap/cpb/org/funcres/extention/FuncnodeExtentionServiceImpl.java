package nc.uap.cpb.org.funcres.extention;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpAppsNodeQry;
import nc.uap.cpb.org.itf.ICpModuleQry;
import nc.uap.cpb.org.vos.CpAppsCategoryVO;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.cpb.org.vos.CpFuncResVO;
import nc.uap.cpb.org.vos.CpModuleVO;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * 功能节点扩展实现
 * 2012-1-13 上午09:19:09
 * @author limingf
 *
 */
public class FuncnodeExtentionServiceImpl implements IFuncResExtentionService {

	@Override
	public CpFuncResVO[] getFuncResVos() {
		List<CpFuncResVO> list = new ArrayList<CpFuncResVO>();
		CpModuleVO[] modules = null;
		try {
			modules = NCLocator.getInstance().lookup(ICpModuleQry.class).getAllModules();
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		for(CpModuleVO tmp:modules){
			CpFuncResVO vo = new CpFuncResVO();
			vo.setPk_busifunc("");
			vo.setCode(tmp.getId());
			vo.setName(tmp.getTitle());
			vo.setPk_funcres(tmp.getPk_module());
			vo.setPk_parent(tmp.getPk_parent());
			vo.setType(getFuncResType());
			list.add(vo);
		}
				
		CpAppsCategoryVO[] appscategorys = null;
		try {
			appscategorys = NCLocator.getInstance().lookup(ICpAppsNodeQry.class).getAllCategory();
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		for(CpAppsCategoryVO tmp:appscategorys){
			CpFuncResVO vo = new CpFuncResVO();
			vo.setPk_busifunc("");
			vo.setCode(tmp.getId());
			vo.setName(tmp.getTitle());
			vo.setPk_funcres(tmp.getPk_appscategory());
			if(tmp.getPk_parent()!=null&&!"".equals(tmp.getPk_parent())){
				vo.setPk_parent(tmp.getPk_parent());
			}
			else{
				vo.setPk_parent(tmp.getPk_module());
			}
			vo.setType(getFuncResType());
			list.add(vo);
		}
		
		String type = (String) AppLifeCycleContext.current().getApplicationContext().getCurrentWindowContext().getAppAttribute("type");
		
		CpAppsNodeVO[] nodes = null;
		try {
			nodes = NCLocator.getInstance().lookup(ICpAppsNodeQry.class).getAllNodesByType(type);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		for(CpAppsNodeVO tmp:nodes){
			CpFuncResVO vo = new CpFuncResVO();
			vo.setPk_busifunc(tmp.getPk_appsnode());
			vo.setCode(tmp.getId());
			vo.setName(tmp.getTitle());
			vo.setPk_funcres(tmp.getPk_appsnode());
			vo.setPk_parent(tmp.getPk_appscategory());
			vo.setType(getFuncResType());
			list.add(vo);
		}
		return list.toArray(new CpFuncResVO[0]);
	}

	@Override
	public int getFuncResType() {
		return IFuncResExtentionService.TYPE_FUNC;
	}

}
