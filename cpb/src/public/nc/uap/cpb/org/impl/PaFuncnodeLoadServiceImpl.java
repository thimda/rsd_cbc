package nc.uap.cpb.org.impl;

import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpAppsNodeVO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.pa.itf.IPaFuncnodeLoadService;
import nc.uap.lfw.pa.pamgr.PaFuncNodeProxyVO;
import nc.vo.pub.lang.UFBoolean;

public class PaFuncnodeLoadServiceImpl  implements IPaFuncnodeLoadService{
	
	public List<PaFuncNodeProxyVO> getAllFucnodeVO(){
		CpAppsNodeVO[] nodes = null;
		try {
			nodes = CpbServiceFacility.getPortalManagerAppService().getAllNodes();
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		if(nodes==null)
			return null;
		List<PaFuncNodeProxyVO> pafunclist = new ArrayList<PaFuncNodeProxyVO>();
		for(CpAppsNodeVO node:nodes){
			PaFuncNodeProxyVO vo = new PaFuncNodeProxyVO();
			vo.setActiveflag(node.getActiveflag());
			vo.setI18nname(node.getI18nname());
			vo.setId(node.getId());
			vo.setPk_appscategory(node.getPk_appscategory());
			vo.setPk_appsnode(node.getPk_appsnode());
			vo.setTitle(node.getTitle());
			//vo.setPk_group(node.getPk_group());
			vo.setUrl(node.getUrl());
			vo.setSpecialflag(node.getSpecialflag());
			pafunclist.add(vo);
		}
		return pafunclist;
	}

	@Override
	public List<PaFuncNodeProxyVO> getFucnodeVOBySpecial(UFBoolean flag) {
		CpAppsNodeVO[] nodes = null;
		try {
			nodes = CpbServiceFacility.getPortalManagerAppService().getNodeBySpecial(flag);
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		if(nodes==null)
			return null;
		List<PaFuncNodeProxyVO> pafunclist = new ArrayList<PaFuncNodeProxyVO>();
		for(CpAppsNodeVO node:nodes){
			PaFuncNodeProxyVO vo = new PaFuncNodeProxyVO();
			vo.setActiveflag(node.getActiveflag());
			vo.setI18nname(node.getI18nname());
			vo.setId(node.getId());
			vo.setPk_appscategory(node.getPk_appscategory());
			vo.setPk_appsnode(node.getPk_appsnode());
			vo.setTitle(node.getTitle());
			//vo.setPk_group(node.getPk_group());
			vo.setUrl(node.getUrl());
			vo.setSpecialflag(node.getSpecialflag());
			pafunclist.add(vo);
		}
		return pafunclist;
	}
	
}
