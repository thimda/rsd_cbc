package nc.uap.wfm.contanier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import nc.bs.framework.common.NCLocator;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.dftimpl.DefaultFormOper;
import nc.uap.wfm.engine.IWfmFormOper;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.facility.WfmServiceFacility;
import nc.uap.wfm.itf.IWfmFlwTypeQry;
import nc.uap.wfm.itf.IWfmProDefQry;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.parse.ProcessParser;
import nc.uap.wfm.utils.WfmClassUtil;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import nc.uap.wfm.vo.WfmProdefVO;
/**
 * 流程定义容器 只能有一个实例
 * 
 * @author tianchw
 * 
 */
public class ProDefsContainer {
	private static final String seperator = "_";
	private static ProDefsContainer instance = null;
	private static Map<String, ProDef> map = new ConcurrentHashMap<String, ProDef>();
	synchronized private static ProDefsContainer getInstance() {
		if (instance == null) {
			instance = new ProDefsContainer();
		}
		return instance;
	}
	private static ProDef getProDefByPkAndId(String proDefPk, String proDefId) {
		ProDefsContainer.getInstance();
		String key = proDefPk + seperator + proDefId;
		ProDef proDef = map.get(key);
		if (proDef == null) {
			WfmProdefVO vo = null;
			try {
				vo = NCLocator.getInstance().lookup(IWfmProDefQry.class).getProDefVOByProDefPk(proDefPk);
			} catch (WfmServiceException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
			return getProDef(vo);
		} else {
			return proDef;
		}
	}
	public static Map<String, ProDef> getProDefs() {
		return map;
	}
	public static ProDef getProDef(WfmProdefVO vo) {
		ProDef proDef = null;
		if (vo == null) {
			return null;
		}
		proDef = map.get(vo.getPk_prodef() + seperator + vo.getId());
		if (proDef == null) {
			proDef = ProDefsContainer.parseProDef(vo);
		}
		return proDef;
	}
	public static ProDef parseProDef(WfmProdefVO vo) {
		ProDef proDef = null;
		ProcessParser parse = ProcessParser.getInstance();
		try {
			proDef = parse.parse(vo.getProcessstr());
			proDef.setPk_prodef(vo.getPk_prodef());
			proDef.setPk_startfrm(vo.getPk_startfrm());
			proDef.setServerClass(vo.getServerclass());
			WfmFlwTypeVO flwTypeVo = NCLocator.getInstance().lookup(IWfmFlwTypeQry.class).getFlwTypeVoByPk(vo.getFlwtype());
			proDef.setFlwtype(flwTypeVo);
		} catch (WfmServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		ProDefsContainer.recursPutProDef(proDef);
		return proDef;
	}
	private static void recursPutProDef(ProDef proDef) {
		String proDefPk = proDef.getPk_prodef();
		String proDefId = proDef.getId();
		map.put(proDefPk + seperator + proDefId, proDef);
		Map<String, ProDef> subMap = proDef.getSubPros();
		if (subMap.size() != 0) {
			Set<String> keys = subMap.keySet();
			Iterator<String> iter = keys.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				ProDef sub = subMap.get(key);
				sub.setPk_prodef(proDefPk);
				ProDefsContainer.recursPutProDef(sub);
			}
		}
	}
	private static String genContanierKey(String proDefPk, String proDefId) {
		return proDefPk + seperator + proDefId;
	}
	/**
	 * 流程定义定位根据流程定义ID和PK
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	public static ProDef getByProDefPkAndId(String proDefPk, String proDefId) {
		return ProDefsContainer.getProDefByPkAndId(proDefPk, proDefId);
	}
	/**
	 * 流程定义定位根据发起单据
	 * 
	 * @param startFrmDefPk
	 * @return
	 */
	public static ProDef getProDefByFlowTypePk(String flowTypePk) {
		ProDefsContainer.getInstance();
		ProDef proDef = null;
		WfmFlwTypeVO flowTypeVo = WfmServiceFacility.getFlwTypeQry().getFlwTypeVoByPk(flowTypePk);
		String serverClass = flowTypeVo.getServerclass();
		if (serverClass == null || serverClass.length() == 0) {
			serverClass = DefaultFormOper.class.getName();
		}
		IWfmFormOper formOper = (IWfmFormOper) WfmClassUtil.loadClass(serverClass);
		proDef = formOper.getProDefByFlowType(flowTypeVo);
		if (proDef == null) {
			return null;
		}
		return proDef;
	}
	/**
	 * 构造流程定义
	 * 
	 * @param startFrmDefPk
	 * @return
	 */
	public static void builder(String proDefPk, String proDefId) {
		ProDefsContainer.getProDefByPkAndId(proDefPk, proDefId);
	}
	/**
	 * 消毁流程定义
	 * 
	 * @param proDefPk
	 * @param proDefId
	 * @return
	 */
	public static void destory(String proDefPk, String proDefId) {
		map.remove(ProDefsContainer.genContanierKey(proDefPk, proDefId));
	}
}
