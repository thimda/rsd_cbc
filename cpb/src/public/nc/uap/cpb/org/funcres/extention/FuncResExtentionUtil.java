package nc.uap.cpb.org.funcres.extention;

import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.vos.CpFuncResVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;

/**
 * 功能资源扫描扩展点公共实现类
 * 
 */
public class FuncResExtentionUtil {

	public static CpFuncResVO[] getFuncRes() {
		 //得到功能资源加载扩展点
		 List<PtExtension> exs = PluginManager.newIns().getExtensions(IFuncResExtentionService.FUNCRES_SCAN);
		 if (exs == null || exs.size() == 0) {
			 return null;
		 }
		 List<CpFuncResVO> funcres = new ArrayList<CpFuncResVO>();
		 for (PtExtension ex : exs) {
			 String exId = ex.getId();		
			 String exName = ex.getTitle();
			 IFuncResExtentionService service = null;
			 try {
				service = (IFuncResExtentionService)ex.newInstance();
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
			}
			if(service == null)
				continue;
			CpFuncResVO[] vos = service.getFuncResVos();
			if(vos==null)
				continue;
			if(service instanceof nc.uap.cpb.org.funcres.extention.FuncnodeExtentionServiceImpl)
				for(CpFuncResVO tmp:vos){
					funcres.add(tmp);
				}
			else {
				CpFuncResVO root = new CpFuncResVO();
				root.setCode(exId);
				root.setName(exName);
				root.setType(service.getFuncResType());
				root.setPk_funcres(exId);
				root.setPk_parent(null);
				funcres.add(root);			
				for(CpFuncResVO tmp:vos){
					if(tmp.getPk_parent()==null||"".equals(tmp.getPk_parent()))
						tmp.setPk_parent(root.getPk_funcres());
					funcres.add(tmp);
				}
			}
		 }
		 return funcres.toArray(new CpFuncResVO[0]);
	}
}
