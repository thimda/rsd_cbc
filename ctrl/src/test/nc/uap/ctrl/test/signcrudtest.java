package nc.uap.ctrl.test;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.test.AbstractTestCase;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpSuperVOBill;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.portal.ctrl.office.data.SignHelper;
import nc.uap.portal.ctrl.office.data.sign.ISignQuery;
import nc.uap.portal.ctrl.office.data.sign.ServersignVO;
import nc.uap.portal.ctrl.office.data.sign.UsersignsVO;
import nc.vo.pub.lang.UFDateTime;

public class signcrudtest extends AbstractTestCase {

	//创建 serversign
	//ICpSuperVOBill
	//绑定用户
	//查询数据
	
	//创建
	public void testCreateSign(){
		try {
			LfwRuntimeEnvironment.setDatasource("design");
			ICpSuperVOBill supqry = NCLocator.getInstance().lookup(ICpSuperVOBill.class);
			//创建印章
			ServersignVO serversignvo = new ServersignVO();
			serversignvo.setName("测试印章");
			serversignvo.setCreateby("lisw");
			serversignvo.setCreatetime(new UFDateTime());
			serversignvo.setModifertime(new UFDateTime());
			serversignvo.setModifier("lisw");
			serversignvo.setNo("123456");
			
			String pk_sign =  supqry.insertSuperVO(serversignvo);
			
			//绑定用户
			ICpUserQry userqry = NCLocator.getInstance().lookup(ICpUserQry.class);
			CpUserVO uservo =  userqry.getUserByCode("grpadmin");
			
			UsersignsVO usersignvo = new  UsersignsVO();
			usersignvo.setPk_user(uservo.getCuserid());
			usersignvo.setPk_sign(pk_sign);
			
			supqry.insertSuperVO(usersignvo);
			
			ServersignVO[] signs = SignHelper.GetAllServersignVO();
			assertEquals(signs.length > 0,true);
			
			UsersignsVO[] signusers = SignHelper.GetAllSignusers(signs[0].getPk_sign());
			assertEquals(signusers.length> 0,true);
			
		} catch (CpbBusinessException e) {			
			e.printStackTrace();
		} catch (LfwBusinessException e) {
			e.printStackTrace();
		}
		
	}

	
}
