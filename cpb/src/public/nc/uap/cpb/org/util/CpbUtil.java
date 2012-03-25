package nc.uap.cpb.org.util;
import java.lang.reflect.Array;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.org.orgmodel.IUserAdminGroupService;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.org.GroupVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.sm.UserVO;
import org.apache.commons.beanutils.BeanUtils;
public class CpbUtil {
	public static GroupVO[] getGroupAdminByUserPk(String cuserid) {
		GroupVO[] groupVos = null;
		try {
			groupVos = NCLocator.getInstance().lookup(IUserAdminGroupService.class).queryGroupsByAdminUser(cuserid);;
			groupVos = CpbServiceFacility.getCpGroupQry().queryAllGroupVOs();
		} catch (BusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return groupVos;
	}
	public static String getCntUserPk() {
		return LfwRuntimeEnvironment.getLfwSessionBean() == null ? null : LfwRuntimeEnvironment.getLfwSessionBean().getPk_user();
	}
	public static UserVO convert(CpUserVO userVo) {
		if (userVo == null) {
			return null;
		}
		UserVO smUserVo = new UserVO();
		try {
			BeanUtils.copyProperties(smUserVo, userVo);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return null;
	}
	public static CpUserVO convert(UserVO userVo) {
		if (userVo == null) {
			return null;
		}
		CpUserVO smUserVo = new CpUserVO();
		try {
			BeanUtils.copyProperties(smUserVo, userVo);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
		return smUserVo;
	}
	@SuppressWarnings("unchecked") public static <M> List<M> queryList(String sql, SQLParameter parameter, Class<M> clazz) {
		PtBaseDAO dao = new PtBaseDAO();
		List<M> list = null;
		try {
			list = (List<M>) dao.executeQuery(sql, parameter, new BeanListProcessor(clazz));
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e);
		}
		return list;
	}
	@SuppressWarnings("unchecked") public static <M> M[] returnArray(List<M> list) {
		if (list == null || list.size() == 0) {
			return null;
		
		}
		return list.toArray((M[]) Array.newInstance(list.get(0).getClass(), 0));
	}
	public static <M> M returnValue(List<M> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	public static String joinQryArrays(String str[]) {
		if (str == null || str.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int length = str.length;
		for (int i = 0; i < length; i++) {
			sb.append("'").append(str[i]).append("'");
			if (i == length - 1) {
				break;
			} else {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	@SuppressWarnings("unchecked") public static <M> M[] convert(SuperVO[] superVos, Class<M> clazz) {
		if (superVos == null || superVos.length == 0) {
			return null;
		}
		int length = superVos.length;
		M[] objects = (M[]) Array.newInstance(clazz, length);
		for (int i = 0; i < length; i++) {
			objects[i] = (M) superVos[i];
		}
		return objects;
	}
}
