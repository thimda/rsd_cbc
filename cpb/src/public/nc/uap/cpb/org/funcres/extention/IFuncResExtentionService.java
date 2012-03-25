package nc.uap.cpb.org.funcres.extention;

import nc.uap.cpb.org.vos.CpFuncResVO;


/**
 * 功能资源扫描扩展点
 *
 */
public interface IFuncResExtentionService {

	//功能资源扫描扩展点
	public static final String FUNCRES_SCAN = "funcresmanage";
	
	//资源类型
	//功能节点
	public static final int TYPE_FUNC = 1;
	//portal页面
	public static final int TYPE_PORTALPAGE = 2;
	//portlet
	public static final int TYPE_PORTLET = 3;
	//......
	
	public int getFuncResType();
	
	public CpFuncResVO[] getFuncResVos();
	
}
