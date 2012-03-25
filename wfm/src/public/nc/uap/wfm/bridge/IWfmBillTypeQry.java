package nc.uap.wfm.bridge;


/**
 * 
 * @author dengjt
 * 单据类型查询服务
 *
 */
public interface IWfmBillTypeQry {
	/**
	 * 根据单据类型获取对应的服务
	 * @return
	 */
	public IBillFormService getBillFormService(String billType);
}
