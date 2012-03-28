package nc.uap.wfm.impl;

import java.util.HashSet;
import java.util.Set;

import nc.bs.dao.DAOException;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.convert.BeanConvert;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.itf.IWfmHumActInsQry;
import nc.uap.wfm.model.HumActIns;
import nc.uap.wfm.vo.WfmHumActInsVO;
import nc.vo.pub.SuperVO;

import org.apache.commons.lang.StringUtils;

public class WfmHumActInsQry implements IWfmHumActInsQry {
	public HumActIns getHumActInsByPk(String humActInsPk) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return null;
		}
		WfmHumActInsVO humActInsVO = this.getHumActInsVOByPk(humActInsPk);
		HumActIns humActIns = BeanConvert.toHumActIns(humActInsVO);
		String parentPk = humActInsVO.getPk_parent();
		if (StringUtils.isNotBlank(parentPk)) {
			WfmHumActInsVO parentVO = this.getHumActInsVOByPk(parentPk);
			if(parentVO!=null){
				HumActIns parent = BeanConvert.toHumActIns(parentVO);
				humActIns.setParent(parent);	
			}
		}
		Set<HumActIns> subHumActIns = this.getSubHumActInsByParentPk(humActInsPk);
		humActIns.setSubHumActIns(subHumActIns);
		return humActIns;
	}

	public WfmHumActInsVO getHumActInsVOByPk(String humActInsPk) throws WfmServiceException {
		if (humActInsPk == null || humActInsPk.length() == 0) {
			return null;
		}
		WfmHumActInsVO[] vos = null;
		WfmHumActInsVO humActInsVO = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmHumActInsVO[]) dao.queryByCondition(WfmHumActInsVO.class, "pk_humactins='" + humActInsPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		if (vos != null && vos.length == 1) {
			humActInsVO = vos[0];
		}
		return humActInsVO;
	}

	public Set<HumActIns> getSubHumActInsByParentPk(String parentPk) throws WfmServiceException {
		if (parentPk == null || parentPk.length() == 0) {
			return null;
		}
		WfmHumActInsVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmHumActInsVO[]) dao.queryByCondition(WfmHumActInsVO.class, "pk_parent='" + parentPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		Set<HumActIns> subHumActIns = null;
		if (vos != null && vos.length != 0) {
			subHumActIns = new HashSet<HumActIns>();
			for (int i = 0; i < vos.length; i++) {
				HumActIns temp = BeanConvert.toHumActIns(vos[i]);
				subHumActIns.add(temp);
			}
		}
		return subHumActIns;
	}

	@Override
	public Set<HumActIns> getHumActInsesByProInsPk(String proInsPk) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0) {
			return null;
		}
		Set<HumActIns> humActInses = null;
		WfmHumActInsVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmHumActInsVO[]) dao.queryByCondition(WfmHumActInsVO.class, "pk_proins='" + proInsPk + "'");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		if (vos != null && vos.length != 0) {
			humActInses = new HashSet<HumActIns>();
			for (int i = 0; i < vos.length; i++) {
				HumActIns tmp = BeanConvert.toHumActIns(vos[i]);
				humActInses.add(tmp);
			}
		}
		return humActInses;
	}

	@Override
	public HumActIns getHumActInsByProInsPkAndPrtId(String proInsPk, String prtId) throws WfmServiceException {
		if (proInsPk == null || proInsPk.length() == 0 || prtId == null || prtId.length() == 0) {
			return null;
		}
		SuperVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = dao.queryByCondition(WfmHumActInsVO.class, "pk_proins='" + proInsPk + "' and humact_id='" + prtId + "' order by ts desc");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		HumActIns humActIns = null;
		if (vos != null && vos.length >= 1) {
			WfmHumActInsVO humActInsVO = (WfmHumActInsVO) vos[0];
			humActIns = this.getHumActInsByPk(humActInsVO.getPk_humactins());
		}
		return humActIns;
	}

	public WfmHumActInsVO[] getHumActInsesByRootProInsPk(String rootInsPk) throws WfmServiceException {
		if (rootInsPk == null || rootInsPk.length() == 0) {
			return null;
		}
		WfmHumActInsVO[] vos = null;
		PtBaseDAO dao = new PtBaseDAO();
		try {
			vos = (WfmHumActInsVO[]) dao.queryByCondition(WfmHumActInsVO.class, "pk_rootproins='" + rootInsPk + "' order by ts");
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new WfmServiceException(e.getMessage());
		}
		return vos;
	}
}
