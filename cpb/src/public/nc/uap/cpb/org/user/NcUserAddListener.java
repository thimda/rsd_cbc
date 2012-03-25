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
public class NcUserAddListener implements IBusinessListener {
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
			//ֻ�����������Ա���ŵ���
			if(ncUserVO.getBase_doc_type() == null || ncUserVO.getBase_doc_type() != 0)
				continue;
			//��ݲ�Ϊ�ղŵ���
			if(ncUserVO.getPk_base_doc() == null)
				continue;
			CpUserVO userVo = CpbUtil.convert(ncUserVO);
			userVo.setNcpk(ncUserVO.getCuserid());
			userVo.setOriginal(ICpUserConst.ORIGINAL_NC);
			try {
				//����Ѿ�������ͬ��������ݣ�����ԭpk���������ݸ���
				CpUserVO olduservo = CpbServiceFacility.getCpUserQry().getUserByCode(ncUserVO.getUser_code());
				if(olduservo!=null){
					userVo.setCuserid(olduservo.getCuserid());
					CpbServiceFacility.getCpUserBill().updateCpUserVO(olduservo);
				}
				else CpbServiceFacility.getCpUserBill().addCpUserVOWithPk(userVo);
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getMessage());
			}
		}
	}
}
