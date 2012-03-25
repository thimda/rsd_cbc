package nc.uap.cpb.org.responsibility;

import nc.bs.logging.Logger;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.vo.pub.SuperVO;

public class SuperVOSaveDelegator {
	/**
	 * 保存，更新前根据where条件检查vo是否重复
	 * @param vo
	 */
	public void checkDupliVO(SuperVO vo,String opeStatus,String where) {
		try {
			SuperVO[] respvos = CpbServiceFacility.getCpSuperVOQry().getSuperVOs(vo, where);
			if (respvos == null || respvos.length<1)
				return;
			if (RespConstant.ADD_OPERATE.equals(opeStatus) || (RespConstant.EDIT_OPERATE.equals(opeStatus) && !respvos[0].getPrimaryKey().equals(vo.getPrimaryKey())))
				throw new LfwRuntimeException("职责编码/职责名称已经存在,请重新输入!");
		} catch (CpbBusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
	}

}
