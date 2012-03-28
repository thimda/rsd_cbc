package nc.uap.wfm.impl;
import java.util.HashSet;
import java.util.Set;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.constant.WfmConstants;
import nc.uap.wfm.convert.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmProInsQry;
import nc.uap.wfm.model.ProIns;
import nc.uap.wfm.vo.WfmProInsVO;
import nc.vo.pub.lang.UFDate;

import org.apache.commons.lang.StringUtils;
public class WfmProInsQry implements IWfmProInsQry {
	public ProIns getProInsByPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		WfmProInsVO proInsVO = this.getProInsVOByPk(proInsPk);
		ProIns proIns = BeanConvert.toProIns(proInsVO);
		if (StringUtils.isNotBlank(proInsVO.getPk_parent())) {
			WfmProInsVO parentVO = this.getProInsVOByPk(proInsVO.getPk_parent());
			if (parentVO != null) {
				ProIns parent = BeanConvert.toProIns(parentVO);
				proIns.setParent(parent);
			}
		}
		if (StringUtils.isNotBlank(proInsVO.getPk_pproins())) {
			WfmProInsVO pproInsVo = this.getProInsVOByPk(proInsVO.getPk_pproins());
			if (pproInsVo != null) {
				ProIns pproIns = BeanConvert.toProIns(pproInsVo);
				proIns.setPproIns(pproIns);
			}
		}
		Set<ProIns> subProIns = this.getSubProInsByParentPk(proInsPk);
		proIns.setSubProIns(subProIns);
		return proIns;
	}
	public WfmProInsVO getProInsVOByPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(proInsPk)) {
			return null;
		}
		WfmProInsVO proInsVO = null;
		WfmProInsVO[] vos = this.getProInsVOsByWhere("pk_proins='" + proInsPk + "'");
		if (vos != null && vos.length == 1) {
			proInsVO = vos[0];
		}
		return proInsVO;
	}
	public Set<ProIns> getSubProInsByParentPk(String parentPk) throws WfmServiceException {
		if (parentPk == null || parentPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(parentPk)) {
			return null;
		}
		WfmProInsVO[] vos = this.getProInsVOsByWhere("pk_parent='" + parentPk + "'");
		Set<ProIns> subProIns = null;
		if (vos != null && vos.length != 0) {
			subProIns = new HashSet<ProIns>();
			for (int i = 0; i < vos.length; i++) {
				ProIns temp = BeanConvert.toProIns(vos[i]);
				subProIns.add(temp);
			}
		}
		return subProIns;
	}
	@Override
	public WfmProInsVO[] getMyStartAndCmpltProIns(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state='" + ProIns.End + "'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndUnDneProIns(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state<>'" + ProIns.End + "'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndUnDneProInsByDay(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state<> '" + ProIns.End + "' and startdate between'" + new UFDate(true).toString() + "' and '" + new UFDate(false).toLocalString()
		+ " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndCmpltProInsByDay(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state= '" + ProIns.End + "' and startdate between'" + new UFDate(true).toString() + "' and '" + new UFDate(false).toLocalString()
		+ " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndUnDneProInsByWeek(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		UFDate now = new UFDate(true);
		UFDate before = now.getDateBefore(7);
		String where = "pk_starter='" + userPk + "' and state<> '" + ProIns.End + "' and startdate between'" + before.toString() + "' and '" + new UFDate(false).toLocalString() + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndCmpltProInsByWeek(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		UFDate now = new UFDate(true);
		UFDate before = now.getDateBefore(7);
		String where = "pk_starter='" + userPk + "' and state= '" + ProIns.End + "' and startdate between'" + before.toString() + "' and '" + new UFDate(false).toLocalString() + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndUnDneProInsByMonth(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		UFDate now = new UFDate(true);
		UFDate before = now.getDateBefore(31);
		String where = "pk_starter='" + userPk + "' and state<> '" + ProIns.End + "' and startdate between'" + before.toString() + "' and '" + new UFDate(false).toLocalString() + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndCmpltProInsByMonth(String userPk) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		UFDate now = new UFDate(true);
		UFDate before = now.getDateBefore(31);
		String where = "pk_starter='" + userPk + "' and state= '" + ProIns.End + "' and startdate between'" + before.toString() + "' and '" + new UFDate(false).toLocalString() + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	public WfmProInsVO[] getProInsVOsByWhere(String where) throws WfmServiceException {
		WfmProInsVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmProInsVO[]) dao.queryByCondition(WfmProInsVO.class, where);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
	@Override
	public WfmProInsVO[] getMyStartAndCmpltProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state= '" + ProIns.End + "' and startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	@Override
	public WfmProInsVO[] getMyStartAndUndneProInsByDate(String userPk, String startDate, String endDate) throws WfmServiceException {
		if (userPk == null || userPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(userPk)) {
			return null;
		}
		String where = "pk_starter='" + userPk + "' and state<> '" + ProIns.End + "' and startdate between'" + startDate + " 00:00:00' and '" + endDate + " 24:00:00'";
		return this.getProInsVOsByWhere(where);
	}
	public WfmProInsVO[] getSubProInsByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0 || WfmConstants.StrNull.equalsIgnoreCase(proInsPk)) {
			return null;
		}
		String where = "pk_pproins='" + proInsPk + "'";
		return this.getProInsVOsByWhere(where);
	}
}
