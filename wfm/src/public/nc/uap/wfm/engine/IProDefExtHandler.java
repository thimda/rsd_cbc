package nc.uap.wfm.engine;
import java.util.Map;
import nc.uap.wfm.model.ProDef;
/**
 * 流程定义扩展接口
 * 
 * @author tianchw
 * 
 */
public interface IProDefExtHandler {
	/**
	 * 流程对应的流水号（单据流程号）生成规则扩张类 对应接口类（nc.uap.wfm.engine.IFrmNumBillGen）
	 * 
	 * @return
	 */
	String getFrmNumBillServerClass();
	/**
	 * 属性迁移扩张配置（单据属性到任务属性）
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getExtAttr(ProDef proDef, String frmDefPk);
	/**
	 * 任务表头展现的扩展属性配置
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getExtAttrName(ProDef proDef, String frmDefPk);
	/**
	 * 任务查询扩展配置
	 * 
	 * @param proDef
	 * @param frmDefPk
	 * @return
	 */
	public Map<String, String> getQryTaskAttr(ProDef proDef, String frmDefPk);
	/**
	 * 代办任务的扩展PageModel
	 * 
	 * @param frmDefPk
	 * @return
	 */
	public String getMyPrtptPageModel(String frmDefPk);
}
