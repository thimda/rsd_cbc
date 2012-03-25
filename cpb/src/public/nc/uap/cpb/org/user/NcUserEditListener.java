package nc.uap.cpb.org.user;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.bs.businessevent.bd.BDCommonEvent.BDCommonUserObj;
import nc.login.vo.INCUserTypeConstant;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.util.CpbUtil;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.BusinessException;
import nc.vo.sm.UserVO;
public class NcUserEditListener implements IBusinessListener {
	@Override public void doAction(IBusinessEvent event) throws BusinessException {
		BDCommonEvent bevent = (BDCommonEvent) event;
		BDCommonUserObj ncUserVo = (BDCommonUserObj) bevent.getUserObject();
		ncUserVo.getNewObjects().getClass().getName();
		UserVO[] userVos = (UserVO[]) ncUserVo.getNewObjects();
		if (userVos == null || userVos.length == 0) {
			return;
		}
		for (int i = 0; i < userVos.length; i++) { 
			UserVO ncUserVO = userVos[i];
			if (!(INCUserTypeConstant.USER_TYPE_USER== ncUserVO.getUser_type())) {
				continue;
			}
			//只有身份类型是员工才继续
			if(ncUserVO.getBase_doc_type() == null || ncUserVO.getBase_doc_type() != 0)
				continue;
			//身份不为空才继续
			if(ncUserVO.getPk_base_doc() == null)
				continue;
			
			CpUserVO userVo = CpbUtil.convert(ncUserVO);
			userVo.setNcpk(ncUserVO.getCuserid());
			userVo.setOriginal(ICpUserConst.ORIGINAL_NC);
			try {
				//如果已经存在相同编码的数据......
				CpUserVO olduservo = CpbServiceFacility.getCpUserQry().getUserByCode(ncUserVO.getUser_code());
				//如果已经存在相同的用户
				if(olduservo!=null){
					//如果这用户是nc用户，覆盖更新
					if(olduservo.getCuserid().equals(userVo.getCuserid())){
						userVo.setCuserid(olduservo.getCuserid());
						CpbServiceFacility.getCpUserBill().updateCpUserVO(olduservo);
					}
					//如果已经存在的用户是协同用户，则阻止
					else if(olduservo.getCuserid().equals(userVo.getCuserid())){
						LfwLogger.error("协同里已经存在相同编码的用户，不允许重复，请修改!");
						throw new LfwRuntimeException("协同里已经存在相同编码的用户，不允许重复，请修改!");
					}
				}
				else CpbServiceFacility.getCpUserBill().updateCpUserVO(userVo);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
